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
import org.openepics.discs.calib.util.Utility;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.CalibrationEJB;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@RequestScoped
public class CalibrationDetail implements Serializable {

    @EJB
    private CalibrationEJB calibrationEJB;    
    private static final Logger logger = Logger.getLogger(CalibrationDetail.class.getName());
    
    // private Equipment selectedEquipment;
    private CalibrationRecord selectedCalibrationRec;
    private final DevicePlus selectedEqp = new DevicePlus();
    private int    calibrationRecID; // calibration record id
    
    /**
     * Creates a new instance of CalibrationDetail
     */
    public CalibrationDetail() {
    }
    
    public void viewHandler(ComponentSystemEvent event) {
        selectedCalibrationRec = calibrationEJB.calibrationRecordById(calibrationRecID);
        if ( selectedCalibrationRec == null ) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "invalid calirbation record id", "");
            return;
        }
        
        Device selectedEquipment = calibrationEJB.physicalComponentById(selectedCalibrationRec.getDevice().getDeviceId());
        if ( selectedEquipment == null ) {
            Utility.showMessage(FacesMessage.SEVERITY_ERROR, "Strange missing physical component", "");
            return;
        }
        selectedEqp.init(selectedEquipment);
    }

    public int getCalibrationRecID() {
        return calibrationRecID;
    }

    public void setCalibrationRecID(int calibrationRecID) {
        this.calibrationRecID = calibrationRecID;
    }

    public CalibrationRecord getSelectedCalibrationRec() {
        return selectedCalibrationRec;
    }

    public DevicePlus getSelectedEqp() {
        return selectedEqp;
    }
       
}
