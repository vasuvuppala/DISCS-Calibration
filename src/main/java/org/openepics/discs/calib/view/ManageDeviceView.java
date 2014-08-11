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
import javax.inject.Inject;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.DeviceEJB;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.UserSession;
import org.openepics.discs.calib.util.Utility;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@ViewScoped
public class ManageDeviceView implements Serializable {

    @EJB
    private DeviceEJB deviceEJB;
    @Inject UserSession userSession;
    
    private static final Logger logger = Logger.getLogger(ManageDeviceView.class.getName());
    // private List<Device> equipmentEnts; // list of equipment entities
    private List<Device> devices;
    private Device selectedDevice = null;
    private List<Device> filteredDevices;
    private boolean selectionMade = false;

    /**
     * Creates a new instance of DeviceManager
     */
    public ManageDeviceView() {
    }

    @PostConstruct
    private void init() {
        try {
            devices = deviceEJB.findDevices(userSession.getGroup());           
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Calibration Manager.");
            System.err.println(e);
        }
    }

    public void onAdd(ActionEvent event) {
        try {
            selectedDevice = new Device();
            // Utility.showMessage(FacesMessage.SEVERITY_INFO, "Device added", "");
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.toString());
            logger.log(Level.SEVERE, "ManageDeviceView.onAdd: " + e.toString());
        }
    }

    public void onSave(ActionEvent event) {
        try {
            selectedDevice.setDeviceGroup(userSession.getGroup()); // set the group from session
            Boolean add = selectedDevice.getDeviceId() == null; // add or edit?           
            selectedDevice = deviceEJB.saveDevice(selectedDevice);            
            if (add) {
                devices.add(selectedDevice);
            }
            
            // tell the client if the operation was a success 
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Device saved ", "");
            logger.log(Level.INFO, "ManageDeviceView.onSave: device id {0}", selectedDevice.getDeviceId());
            selectedDevice = null; 
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.toString());
            logger.log(Level.SEVERE, "ManageDeviceView.onSave: " + e);
        }
    }

    public void onDelete(ActionEvent event) {
        try {
            deviceEJB.deleteDevice(selectedDevice);
            devices.remove(selectedDevice);
            selectedDevice = null;
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

    public boolean isSelectionMade() {
        return selectionMade;
    }

    public void setSelectionMade(boolean selectionMade) {
        this.selectionMade = selectionMade;
    }
       
    public List<Device> getDevices() {
        return devices;
    }

    public Device getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    public List<Device> getFilteredDevices() {
        return filteredDevices;
    }

    public void setFilteredDevices(List<Device> filteredDevices) {
        this.filteredDevices = filteredDevices;
    }

}
