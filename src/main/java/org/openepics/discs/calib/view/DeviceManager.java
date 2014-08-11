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

import org.openepics.discs.calib.util.DevicePlus;
import org.openepics.discs.calib.ejb.CalibrationEJB;
import org.openepics.discs.calib.util.Utility;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.DeviceEJB;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.UserSession;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@ViewScoped
public class DeviceManager implements Serializable {

    @EJB
    private CalibrationEJB calibrationEJB;
    @EJB
    private DeviceEJB deviceEJB;
    @Inject UserSession userSession;
    
    private static final Logger logger = Logger.getLogger(DeviceManager.class.getName());
    // private List<Device> equipmentEnts; // list of equipment entities
    private List<DevicePlus> devices; 
    private DevicePlus selectedEquip;
    private List<DevicePlus> filteredDevices;
    private int numPastDue; // number of devices past due
    private int numDueSoon; // number of devices due soon

    private CalibrationRecord selectedCR;

    /**
     * Creates a new instance of DeviceManager
     */
    public DeviceManager() {
    }

    @PostConstruct
    private void init() {
        try {
            initDevice();
            // equipmentEnts = calibrationEJB.physicalComponents();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Calibration Manager.");
            System.err.println(e);
        }
    }

    private void initDevice() {
        numPastDue = numDueSoon = 0;
        //devices = calibrationEJB.equipmentPlus();
        devices = deviceEJB.findDevicePlus(userSession.getGroup());
        
        for (DevicePlus e : devices) {
            
            if (e.isDueSoon()) {
                numDueSoon++;
            }
            if (e.isPastDue()) {
                numPastDue++;
            }
        }
    }

    public String addCalibrationRecord(Device e) {
        String outcome = "addCalibration";

        if (e == null) {
            outcome = "home";
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Null device given", "Possibly a bug?");
        } else {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getFlash().put("DeviceSerialNumber", e.getSerialNumber());
            logger.log(Level.INFO, "Serial number is: ", e.getSerialNumber());
            // Utility.showMessage(FacesMessage.SEVERITY_INFO, "Serial number is:", e.getSerialNumber());
        }

        return outcome;
    }

    public void delCalibrationRecord(ActionEvent event) {
        try {
            if (selectedCR == null) {
                Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Null calibration record selected", "Possibly a bug?");
            } else {
                calibrationEJB.deleteCalibRecord(selectedCR.getCalibrationRecordId());
                // equipments = calibrationEJB.physicalComponents(); //ToDo: Temporary. Replace with deletion of the calibration record from equipment list
                initDevice(); // ToDo: replace with deletion from the list
                Utility.showMessage(FacesMessage.SEVERITY_INFO, "Calibration Record Deleted", "");
            }
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Something went terribly wrong", "Check server logs");
            logger.log(Level.SEVERE, "Error from EJB. ", e.getMessage());
        }
    }

    public DevicePlus getSelectedEquip() {
        return selectedEquip;
    }

    public void setSelectedEquip(DevicePlus selectedEquip) {
        this.selectedEquip = selectedEquip;
    }

    public List<DevicePlus> getFilteredDevices() {
        return filteredDevices;
    }

    public void setFilteredDevices(List<DevicePlus> filteredDevices) {
        this.filteredDevices = filteredDevices;
    }

    public List<DevicePlus> getDevices() {
        return devices;
    }

    public CalibrationRecord getSelectedCR() {
        return selectedCR;
    }

    public void setSelectedCR(CalibrationRecord selectedCR) {
        this.selectedCR = selectedCR;

    }

    public int getNumPastDue() {
        return numPastDue;
    }

    public int getNumDueSoon() {
        return numDueSoon;
    }

}
