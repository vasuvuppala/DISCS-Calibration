/*
 * This software is Copyright by the Board of Trustees of Michigan
 * State University (c) Copyright 2012.
 * 
 * You may use this software under the terms of the GNU public license
 *  (GPL). The terms of this license are described at:
 *       http://www.gnu.org/licenses/gpl.txt
 * 
 * Contact Information:
 *   Facilitty for Rare Isotope Beam
 *   Michigan State University
 *   East Lansing, MI 48824-1321
 *   http://frib.msu.edu
 * 
 */
package org.openepics.discs.calib.view;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.CalibrationEJB;
import org.openepics.discs.calib.ejb.DeviceEJB;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.BlobStore;
import org.openepics.discs.calib.util.DevicePlus;
import org.openepics.discs.calib.util.UserSession;
import org.openepics.discs.calib.util.Utility;
import org.openepics.discs.calib.view.ArtifactManager.InputArtifact;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Calibration Record Management view
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@ViewScoped
public class CalibrationManager implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(CalibrationManager.class.getName());

    @EJB
    private CalibrationEJB calibrationEJB;
    @EJB
    private DeviceEJB deviceEJB;

    @Inject
    private UserSession userSession;
    @Inject
    private BlobStore blobStore;
    @Inject 
    private ArtifactManager artifactManager;

    // private List<Device> physicalComponents;
    private Device selectedDevice;
    private DevicePlus selectedEplus = DevicePlus.newInstance();
    private List<Device> filteredDevice;
    private List<Device> standards;
   
    // request params
    private Integer deviceId;
    private Integer crecordId; // calibration record id
    
    // inputs
    private CalibrationRecord inputCalibRecord;
    private Device[] inputStandards;
    private List<CalibrationMeasurement> inputMeasurements;
    private CalibrationMeasurement newMeasurement;
    private Date inputCalDate;
    private String inputCalNotes;
    private String selectedSerial;
    

    // for artifacts
    private List<Artifact> artifacts;
    private Artifact selectedArtifact;
     private InputAction inputActionAF;
    // private String uploadedFileName;

    /**
     * Creates a new instance of CalibrationManager
     */
    public CalibrationManager() {
    }

    @PostConstruct
    private void init() {
        try {
            // physicalComponents = calibrationEJB.findDevices();
            standards = calibrationEJB.standards();
            resetInputs();
            // checkFlash();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not initialize Calibration Manager.");
            System.err.println(e);
        }
    }

    /**
     * initialize device and calibration record
     * 
     */
    public void initialize() {
        if (deviceId == null && crecordId == null) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Invalid device", "Device ID and Calibration Record ID are missing");
            return;
        }
        if (crecordId != null) {
            inputCalibRecord = calibrationEJB.calibrationRecordById(crecordId);
            if (inputCalibRecord == null) {
                Utility.showMessage(FacesMessage.SEVERITY_ERROR, "No such calibration record", "Invalid calibration record ID");
                return;
            }
            selectedDevice = inputCalibRecord.getDevice();
            artifacts = new ArrayList<>(inputCalibRecord.getArtifacts());
        } else {
            selectedDevice = deviceEJB.findDevice(deviceId);
            artifacts = new ArrayList<>();
        }
        selectedEplus = DevicePlus.newInstance(selectedDevice);
    }
    
    private void resetInputs() {
        inputMeasurements = new ArrayList<>();
        newMeasurement = new CalibrationMeasurement();
        inputCalDate = null;
        inputCalNotes = null;
        inputStandards = null;
        selectedSerial = null;
        selectedDevice = null;
    }


    /**
     * validate inputs
     * 
     * @return 
     */
    private boolean validate() {
//        if (selectedEquip == null) {
//            LOGGER.log(Level.INFO, "Invalid equipment.");
//            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Invalid equipment", "Please enter a valid serial number.");
//            return false;
//        }
        if (!userSession.isLoggedIn()) { //ToDo: This should be checked at EJB layer. 
            LOGGER.log(Level.INFO, "Not logged in. Cannot add calibration");
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "You are not logged in. Cannot Add Calibration Record.", "PLease sign in");
            return false;
        }

        if (inputStandards == null || inputStandards.length == 0) {
            LOGGER.log(Level.INFO, "No standard selected");
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "No standard selected", "You muist select a standard");
            return false;
        }
        return true;
    }

    public String saveCalibRecord() {
        String outcome = "device";
        
        CalibrationRecord cr = new CalibrationRecord();
        try {
            if (!this.validate()) {
                return null;
            }
            cr.setCalibrationDate(inputCalDate);
            cr.setNotes(inputCalNotes);
            cr.setDevice(selectedDevice);
            // cr.setCalibrationMeasurementList(inputMeasurements);
            cr.setPerformedBy(userSession.getUserId());
           
            LOGGER.log(Level.INFO, "Artifacts: ");
//            List<Artifact> artifacts = new ArrayList<>();
//            for(InputArtifact iartifact: artifactManager.getEntities() ) {
//                LOGGER.log(Level.INFO, "  artifact Name {0}", iartifact.getArtifact().getName());
//                LOGGER.log(Level.INFO, "  artifact Resource ID {0}", iartifact.getArtifact().getResourceId());
//                artifacts.add(iartifact.getArtifact());
//            }
            calibrationEJB.addCalibRecord(cr, inputStandards, inputMeasurements, artifacts);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Calibration was successfully added", "Success!");
            // this.resetInputs();
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Error: someting went awfully wrong. Calibration not saved.", "Check server log");
        }
        
        return outcome;
    }

    public void resetInput(ActionEvent event) {
        this.resetInputs();
    }

    public void addMeasurement(ActionEvent event) {
        //if (newMeasurement.getCalibrationMeasurementPK() == null || newMeasurement.getFunctionTested() == null) {
        if (newMeasurement.getFunctionTested() == null) {
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Blank input", "Please enter values for all fields of a measurement.");
            return;
        }

        inputMeasurements.add(newMeasurement);
        newMeasurement = new CalibrationMeasurement();
        Utility.showMessage(FacesMessage.SEVERITY_INFO, "New measurement added", "");
    }

    // Artifacts
    
    public void onAddAF() {
        selectedArtifact = new Artifact();
        inputActionAF = InputAction.CREATE;
    }
    
    public void onEditAF() {
       inputActionAF = InputAction.UPDATE;
    }
    
    public void onDeleteAF() {
        inputActionAF = InputAction.DELETE;
    }
    
    public void saveArtifact() {
        try {                      
            if (inputActionAF == InputAction.CREATE) {
                artifacts.add(selectedArtifact);                
            } 
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Artifact Saved", "");
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Could not save Artifact", e.getMessage());
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            System.out.println(e);
        }
    }
    /**
     * Move to Utility?
     * 
     * @param event 
     */
    public void handleFileUpload(FileUploadEvent event) {
        // Utility.showMessage(FacesMessage.SEVERITY_INFO, "Succesful", msg);
        LOGGER.log(Level.FINE, "Entering handleFileUpload");
        InputStream istream;

        try {
            UploadedFile uploadedFile = event.getFile();
            String uploadedFileName = uploadedFile.getFileName();
            istream = uploadedFile.getInputstream();
            // logger.log(Level.FINE,"Uploaded file name {0}", uploadedFileName);
            // Utility.showMessage(FacesMessage.SEVERITY_INFO, "File ", "Name: " + uploadedFileName);
            LOGGER.log(Level.FINE, "calling blobstore");
            String fileId = blobStore.storeFile(istream);

            // create an artifact

            selectedArtifact.setName(uploadedFileName);
            selectedArtifact.setResourceId(fileId);
            
            istream.close();
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "File uploaded", "Name: " + uploadedFileName);
            // ileUploaded = true;
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error Uploading file", e.getMessage());
            LOGGER.log(Level.SEVERE, "handleFileUpload {0}", e.getMessage());
            System.out.println(e);
            // fileUploaded = false;
        } finally {

        }
    }

    // getters and setters
    
    public List<Device> getFilteredDevice() {
        return filteredDevice;
    }

    public void setFilteredDevice(List<Device> filteredDevice) {
        this.filteredDevice = filteredDevice;
    }

    public CalibrationRecord getInputCalibRecord() {
        return inputCalibRecord;
    }

    public void setInputCalibRecord(CalibrationRecord inputCalibRecord) {
        this.inputCalibRecord = inputCalibRecord;
    }

    public Device[] getInputStandards() {
        return inputStandards;
    }

    public void setInputStandards(Device[] inputStandards) {
        this.inputStandards = inputStandards;
    }

    public List<CalibrationMeasurement> getInputMeasurements() {
        return inputMeasurements;
    }

    public void setInputMeasurements(List<CalibrationMeasurement> inputMeasurements) {
        this.inputMeasurements = inputMeasurements;
    }

    public Date getInputCalDate() {
        return inputCalDate;
    }

    public void setInputCalDate(Date inputCalDate) {
        this.inputCalDate = inputCalDate;
    }

    public String getInputCalNotes() {
        return inputCalNotes;
    }

    public void setInputCalNotes(String inputCalNotes) {
        this.inputCalNotes = inputCalNotes;
    }

//    public List<Device> getDevices() {
//        return physicalComponents;
//    }

    public List<Device> getStandards() {
        return standards;
    }

    public String getSelectedSerial() {
        return selectedSerial;
    }

    public void setSelectedSerial(String selectedSerial) {
        this.selectedSerial = selectedSerial;
    }

    public CalibrationMeasurement getNewMeasurement() {
        return newMeasurement;
    }

    public void setNewMeasurement(CalibrationMeasurement newMeasurement) {
        this.newMeasurement = newMeasurement;
    }

    public DevicePlus getSelectedEplus() {
        return selectedEplus;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Artifact getSelectedArtifact() {
        return selectedArtifact;
    }

    public void setSelectedArtifact(Artifact selectedArtifact) {
        this.selectedArtifact = selectedArtifact;
    }

    public InputAction getInputActionAF() {
        return inputActionAF;
    }

    public void setInputActionAF(InputAction inputActionAF) {
        this.inputActionAF = inputActionAF;
    }
       
}
