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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
import org.primefaces.event.SelectEvent;

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

    private List<Device> physicalComponents;
    private Device selectedDevice;
    private DevicePlus selectedEplus = DevicePlus.newInstance();
    private List<Device> filteredDevice;
    private List<Device> standards;
    // private String equipmentSerial;
    // inputs
    private CalibrationRecord inputCalibRecord;
    private Device[] inputStandards;
    private List<CalibrationMeasurement> inputMeasurements;
    private CalibrationMeasurement newMeasurement;
    private Date inputCalDate;
    private String inputCalNotes;
    private String selectedSerial;
    private Integer deviceId;

    // for artifacts
    private String uploadedFileName;

    /**
     * Creates a new instance of CalibrationManager
     */
    public CalibrationManager() {
    }

    @PostConstruct
    private void init() {
        try {
            physicalComponents = calibrationEJB.findDevices();
            standards = calibrationEJB.standards();
            resetInputs();
            // checkFlash();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not initialize Calibration Manager.");
            System.err.println(e);
        }
    }

    public void initialize() {
        if (deviceId == null) {
            selectedEplus = DevicePlus.newInstance(null);
        } else {
            selectedDevice = deviceEJB.findDevice(deviceId);
            selectedEplus = DevicePlus.newInstance(selectedDevice);
        }
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

//    private void checkFlash() {
//        /* check if serial number was passed from the previous view */
//        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//        String flashSerial = (String) context.getFlash().get("DeviceSerialNumber");
//
//        if (flashSerial == null || flashSerial.isEmpty()) {
//            Utility.showMessage(FacesMessage.SEVERITY_INFO, "No serial number passed from the previous view (Flash)", "Must have an non-null serial number");
//            LOGGER.log(Level.INFO, "No serial number passed from the previous view (Flash)");
//        } else {
//            selectedSerial = flashSerial;
//            selectedEquip = findDevice(selectedSerial);
//            if (selectedEquip == null) {
//                Utility.showMessage(FacesMessage.SEVERITY_ERROR, "No equipment found. Invalid serial number from previous view (Flash)", selectedSerial);
//                LOGGER.log(Level.INFO, "No equipment found. Invalid serial number from previous view (Flash): " + selectedSerial);
//            }
//            selectedEplus.init(selectedEquip);
//        }
//    }

    public List<Device> completeDevice(String query) {
        List<Device> suggestions = new ArrayList<>();

        for (Device p : physicalComponents) {
            if (p.getSerialNumber().contains(query)) {
                suggestions.add(p);
            }
        }

        return suggestions;
    }

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
            List<Artifact> artifacts = new ArrayList<>();
            for(InputArtifact iartifact: artifactManager.getEntities() ) {
                LOGGER.log(Level.INFO, "  artifact Name {0}", iartifact.getArtifact().getName());
                LOGGER.log(Level.INFO, "  artifact Resource ID {0}", iartifact.getArtifact().getResourceId());
                artifacts.add(iartifact.getArtifact());
            }
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

//    public Device findDevice(String serial) {
//        Device result = null;
//
//        for (Device p : physicalComponents) {
//            if (p.getSerialNumber().equalsIgnoreCase(serial)) {
//                result = p;
//                break;
//            }
//        }
//        return result;
//    }

//    public void handleSelect(SelectEvent event) {
//        for (Device p : physicalComponents) {
//            if (p.getSerialNumber().equalsIgnoreCase(selectedSerial)) {
//                selectedEquip = p;
//                break;
//            }
//        }
//        if (selectedEquip == null) {
//            LOGGER.log(Level.INFO, "Strangely selectedEquip is null");
//            Utility.showMessage(FacesMessage.SEVERITY_INFO, "No selected equipment", "This is strange, possibly a bug.");
//        }
//    }

    

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

    public List<Device> getDevices() {
        return physicalComponents;
    }

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
    
    
}
