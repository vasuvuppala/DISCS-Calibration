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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.ModelEJB;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.Utility;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@ViewScoped
public class ModelManager implements Serializable {

    @EJB
    private ModelEJB modelEJB;
    private static final Logger logger = Logger.getLogger(ModelManager.class.getName());
    
    private List<DeviceModel> models; 
    private DeviceModel selectedModel;
    private List<DeviceModel> filteredModels;
    private boolean selectionMade = false;
        
    public ModelManager() {
    }

    @PostConstruct
    private void init() {
        try {
            models = modelEJB.findModels();
            // equipmentEnts = calibrationEJB.physicalComponents();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Model Manager.");
            System.err.println(e);
        }
    }

    public DeviceModel findModel(int id) {
        DeviceModel result = null;

        for (DeviceModel p : models) {
            if (p.getModelId().equals(id)) {
                result = p;
                break;
            }
        }
        return result;
    }
    
    public void onAdd(ActionEvent event) {
        try {
            selectedModel = new DeviceModel();
            // Utility.showMessage(FacesMessage.SEVERITY_INFO, "Device added", "");
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage());
            logger.log(Level.SEVERE, "ManageDeviceView.onAdd: " + e.getMessage());
        }
    }

    public void onSave(ActionEvent event) {
        try {
            Boolean add = selectedModel.getModelId() == null; // add or edit?           
            selectedModel = modelEJB.saveModel(selectedModel);            
            if (add) {
                models.add(selectedModel);
            }
            
            // tell the client if the operation was a success so that it can hide 
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Device saved ", "");
            logger.log(Level.INFO, "ManageDeviceView.onSave: device id {0}", selectedModel.getModelId());
            selectedModel = null; 
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.toString());
            logger.log(Level.SEVERE, "ManageDeviceView.onSave: " + e);
        }
    }

    public void onDelete(ActionEvent event) {
        try {
            modelEJB.deleteModel(selectedModel);
            models.remove(selectedModel);
            selectedModel = null;
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Device deleted", "");
            // tell the client if the operation was a success so that it can hide
            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage());
            logger.log(Level.SEVERE, "ManageDeviceView.onDelete: " + e.getMessage());
        }
    }
    
    public void onSelect(SelectEvent event) {
        selectionMade = true;
    }
    
    public DeviceModel getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(DeviceModel selectedModel) {
        this.selectedModel = selectedModel;
    }

    public List<DeviceModel> getFilteredModels() {
        return filteredModels;
    }

    public void setFilteredModels(List<DeviceModel> filteredModels) {
        this.filteredModels = filteredModels;
    }

    public List<DeviceModel> getModels() {
        return models;
    }

    public boolean isSelectionMade() {
        return selectionMade;
    }
 
}
