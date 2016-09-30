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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Severity;
import org.openepics.discs.calib.ejb.DeviceEJB;
import org.openepics.discs.calib.ent.Artifact;
import org.openepics.discs.calib.ent.CalibrationRecord;
import org.openepics.discs.calib.ent.Device;
import org.openepics.discs.calib.util.BlobStore;
import org.openepics.discs.calib.util.DevicePlus;
import org.openepics.discs.calib.util.Utility;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * State for Single Device Management view
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@ViewScoped
public class DeviceView implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(DeviceView.class.getName());

    @EJB
    private DeviceEJB deviceEJB;
    
    @Inject
    private BlobStore blobStore;
    @Inject 
    private ArtifactManager artifactManager;
    
    // request parameters
    private Integer deviceId; 
    
    // Device
    private DevicePlus selectedDevice;
    private Boolean newDevice = true; // is it a new device or an existing one?
    
    // Artifacts
    private Artifact selectedArtifact;   
    private InputAction inputActionAF;
    
    // calibration records
    private CalibrationRecord selectedCR;
    
    // for artifacts
    // private String uploadedFileName;

    /**
     * Creates a new instance of CalibrationManager
     */
    public DeviceView() {
    }

    // @PostConstruct
    public void initialize() {
        newDevice = false;
        if (deviceId == null) {
            selectedDevice = DevicePlus.newInstance(null);
            newDevice = true;
        } else {
            Device device = deviceEJB.findDevice(deviceId);
            if (device == null) {
                Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Invalid device id", "check the device id");  
                newDevice = true;
            } 
            selectedDevice = DevicePlus.newInstance(device);            
        }
    }
    
    public void resetInput() {
        selectedCR = null;
        selectedArtifact = null;
    }
    
    public void saveDevice() {
        
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
    
    /**
     * 
     */
    public void saveArtifact() {
        try {
            if (selectedDevice.getDevice().getArtifacts() == null) {
                selectedDevice.getDevice().setArtifacts(new ArrayList<>()); 
            }
            switch (inputActionAF) {
                case CREATE: 
                    selectedDevice.getDevice().getArtifacts().add(selectedArtifact);
                    deviceEJB.addArtifact(selectedDevice.getDevice(), selectedArtifact);
                    break;
                case DELETE: 
                    selectedDevice.getDevice().getArtifacts().remove(selectedArtifact);
                    deviceEJB.deleteArtifact(selectedDevice.getDevice(), selectedArtifact);
                    break;
                case UPDATE:
                    deviceEJB.updateArtifacts(selectedDevice.getDevice());
                    break;
                default:
                     LOGGER.log(Level.SEVERE, "Bug: Invalid InputAction");
                     break;                   
            }           
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error saving artifact", e.getMessage());
            LOGGER.log(Level.SEVERE, "save artifact {0}", e.getMessage());
            System.out.println(e);
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            // fileUploaded = false;
        } finally {

        }
    }
    
    // --- getters/setters

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public DevicePlus getSelectedDevice() {
        return selectedDevice;
    }

    public Artifact getSelectedArtifact() {
        return selectedArtifact;
    }

    public void setSelectedArtifact(Artifact selectedArtifact) {
        this.selectedArtifact = selectedArtifact;
    }

    public CalibrationRecord getSelectedCR() {
        return selectedCR;
    }

    public void setSelectedCR(CalibrationRecord selectedCR) {
        this.selectedCR = selectedCR;
    }

    public Boolean getNewDevice() {
        return newDevice;
    }

    public void setNewDevice(Boolean newDevice) {
        this.newDevice = newDevice;
    }

    public InputAction getInputActionAF() {
        return inputActionAF;
    }

    
}
