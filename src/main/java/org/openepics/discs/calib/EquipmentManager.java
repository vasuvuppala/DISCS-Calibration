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
package org.openepics.discs.calib;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@ManagedBean
@ViewScoped
public class EquipmentManager implements Serializable {

    @EJB
    private CalibrationEJBLocal calibrationEJB;
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    private List<Equipment> equipments; // improper English but then this is not an essay 
    private Equipment selectedEquip;
    private List<Equipment> filteredEquipments;
    
    private CalibrationRecord selectedCR;

    /**
     * Creates a new instance of EquipmentManager
     */
    public EquipmentManager() {
    }

    @PostConstruct
    private void init() {
        try {
            equipments = calibrationEJB.physicalComponents();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Calibration Manager.");
            System.err.println(e);
        }
    }

    public String addCalibrationRecord(Equipment e) {
        String outcome = "addCalibration";

        if (e == null) {
            outcome = "home";
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Null equipment given", "Possibly a bug?");
        } else {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getFlash().put("EquipmentSerialNumber", e.getSerialNumber());
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
                equipments = calibrationEJB.physicalComponents(); //ToDo: Temporary. Replace with deletion of the calibration record from equipment list
                Utility.showMessage(FacesMessage.SEVERITY_INFO, "Calibration Record Deleted", "");
            }
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Something went terribly wrong", "Check server logs");
            logger.log(Level.SEVERE, "Error from EJB. ", e.getMessage());
        }
    }

    public Equipment getSelectedEquip() {
        return selectedEquip;
    }

    public void setSelectedEquip(Equipment selectedEquip) {
        this.selectedEquip = selectedEquip;
    }

    public List<Equipment> getFilteredEquipments() {
        return filteredEquipments;
    }

    public void setFilteredEquipments(List<Equipment> filteredEquipments) {
        this.filteredEquipments = filteredEquipments;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public CalibrationRecord getSelectedCR() {
        return selectedCR;
    }

    public void setSelectedCR(CalibrationRecord selectedCR) {
        this.selectedCR = selectedCR;
        
    }
    
}
