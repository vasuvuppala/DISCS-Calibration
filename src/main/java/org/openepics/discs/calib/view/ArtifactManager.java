/*
 * This software is Copyright by the Board of Trustees of Michigan
 *  State University (c) Copyright 2013, 2014.
 *  
 *  You may use this software under the terms of the GNU public license
 *  (GPL). The terms of this license are described at:
 *    http://www.gnu.org/licenses/gpl.txt
 *  
 *  Contact Information:
 *       Facility for Rare Isotope Beam
 *       Michigan State University
 *       East Lansing, MI 48824-1321
 *        http://frib.msu.edu
 */

package org.openepics.discs.calib.view;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.openepics.discs.calib.ent.Artifact;
import org.openepics.discs.calib.util.BlobStore;
import org.openepics.discs.calib.util.Utility;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Description: State for Manage Process View
 *
 * Methods:
 * <p>
 * Init: to initialize the state
 * <p>
 * resetInput: reset all inputs on the view
 * <p>
 * onRowSelect: things to do when an item is selected
 * <p>
 * onAddCommand: things to do before adding an item
 * <p>
 * onEditCommand: things to do before editing an item
 * <p>
 * onDeleteCommand: things to do before deleting an item
 * <p>
 * saveXXXX: save the input or edited item
 * <p>
 * deleteXXXX: delete the selected item
 *
 * @author vuppala
 *
 */

@Named
@ViewScoped
public class ArtifactManager implements Serializable {         
    private static final Logger LOGGER = Logger.getLogger(ArtifactManager.class.getName());
    
    public static class InputArtifact implements Serializable {
        private Artifact artifact;
        private InputAction operation = InputAction.READ;
        
        public InputArtifact() {}

        public InputArtifact(Artifact artifact) {
            this.artifact = artifact;
        }
        
        public static InputArtifact newInstance() {
            InputArtifact artif = new InputArtifact();
            artif.artifact = new Artifact();
            
            return artif;
            
        }
        
        // ------
        public Artifact getArtifact() {
            return artifact;
        }

        public void setArtifact(Artifact artifact) {
            this.artifact = artifact;
        }

        public InputAction getOperation() {
            return operation;
        }

        public void setOperation(InputAction operation) {
            this.operation = operation;
        }      
    }

    @Inject
    private BlobStore blobStore;
    
    private List<InputArtifact> entities = new ArrayList<>();    
    private List<InputArtifact> filteredEntities;    
    private InputArtifact inputEntity;
    private InputArtifact selectedEntity;
    private InputAction inputAction;
    private boolean fileSelected = true;
   // private UploadedFile uploadedFile;
    private Artifact selectedArtifact;
       
    public ArtifactManager() {
    }
    
    // @PostConstruct
    public void init(List<Artifact> artifacts) {      
        // entities = lcEJB.findAllStatusTypes();     
        resetInput();
        if (null != artifacts) {
            entities = artifacts.stream().map(artf -> new InputArtifact(artf)).collect(Collectors.toList());
        }
    }
    
    private void resetInput() {                
        inputAction = InputAction.READ;
    }
    
    public void onRowSelect(SelectEvent event) {
        // inputRole = selectedRole;
        // Utility.showMessage(FacesMessage.SEVERITY_INFO, "Role Selected", "");
    }
    
    public void onAddCommand(ActionEvent event) {
        inputEntity = InputArtifact.newInstance();
        inputAction = InputAction.CREATE;       
    }
    
    public void onEditCommand(InputArtifact artifact) {
        selectedEntity = artifact;
        inputAction = InputAction.UPDATE;
    }
    
    public void onDeleteCommand(InputArtifact artifact) {
        selectedEntity = artifact;
        
        if (selectedEntity.operation == InputAction.DELETE) {
            inputAction = InputAction.READ;          
        } else {
            inputAction = InputAction.DELETE;
        }
        selectedEntity.setOperation(inputAction);
    }
    
//    public void onEditCommand(ActionEvent event) {
//        inputAction = InputAction.UPDATE;
//    }
//    
//    public void onDeleteCommand(ActionEvent event) {
//        inputAction = InputAction.DELETE;
//    }
    
    public void saveEntity() {
        try {                      
            if (inputAction == InputAction.CREATE) {
                // lcEJB.saveStatusType(inputEntity);
                inputEntity.setOperation(InputAction.CREATE);
                entities.add(inputEntity);                
            } else {
                // lcEJB.saveStatusType(selectedEntity);
                selectedEntity.setOperation(InputAction.UPDATE);
            }
            resetInput();
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Saved", "");
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Could not save ", e.getMessage());
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            System.out.println(e);
        }
    }
    
    public void deleteEntity() {
        try {
            // lcEJB.deleteStatusType(selectedEntity);
            // entities.remove(selectedEntity);
            selectedEntity.setOperation(InputAction.DELETE);
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Deletion successful", "You may have to refresh the page.");
            resetInput();
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Could not complete deletion", e.getMessage());
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            System.out.println(e);
        }
    }
    
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

            inputEntity.artifact.setName(uploadedFileName);
            inputEntity.artifact.setResourceId(fileId);
            
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
    
    
    public StreamedContent getDownloadedFile() {
        StreamedContent file = null;
        
        try {
            // return downloadedFile;
            if (selectedArtifact == null) {
                Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Selected artifact is null","");
                LOGGER.log(Level.INFO, "Selected artifact is null");
                return null;
            }
            
            LOGGER.log(Level.INFO, "Opening stream from repository: {0}",  selectedArtifact.getResourceId());
            LOGGER.log(Level.INFO, "download file name: {0} ", selectedArtifact.getName());
            InputStream istream = blobStore.retreiveFile(selectedArtifact.getResourceId());
            file = new DefaultStreamedContent(istream, "application/octet-stream", selectedArtifact.getName());

            // InputStream stream = new FileInputStream(pathName);                       
            // downloadedFile = new DefaultStreamedContent(stream, "application/octet-stream", "file.jpg"); //ToDo" replace with actual filename
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error: Downloading file", e.getMessage());
            LOGGER.log(Level.SEVERE, "Error in downloading the file");
            LOGGER.log(Level.SEVERE, e.toString());
        }
        
        return file;
    }
    
    //-- Getters/Setters 
    
    public InputAction getInputAction() {
        return inputAction;
    }

    public List<InputArtifact> getEntities() {
        return entities;
    }

    public List<InputArtifact> getFilteredEntities() {
        return filteredEntities;
    }

    public void setFilteredEntities(List<InputArtifact> filteredEntities) {
        this.filteredEntities = filteredEntities;
    }

    public InputArtifact getInputEntity() {
        return inputEntity;
    }

    public void setInputEntity(InputArtifact inputEntity) {
        this.inputEntity = inputEntity;
    }

    public InputArtifact getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(InputArtifact selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public boolean isFileSelected() {
        return fileSelected;
    }

    public void setFileSelected(boolean fileSelected) {
        this.fileSelected = fileSelected;
    }

    public Artifact getSelectedArtifact() {
        return selectedArtifact;
    }

    public void setSelectedArtifact(Artifact selectedArtifact) {
        this.selectedArtifact = selectedArtifact;
    }
}
