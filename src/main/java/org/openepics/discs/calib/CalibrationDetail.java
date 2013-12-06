/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.discs.calib;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@ManagedBean(name = "calibrationDetail")
@RequestScoped
public class CalibrationDetail implements Serializable {

    @EJB
    private CalibrationEJBLocal calibrationEJB;    
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    
    private Equipment selectedEquipment;
    private CalibrationRecord selectedCalibrationRec;
    private int    calibrationRecID; // calibration record id
    
    /**
     * Creates a new instance of CalibrationDetail
     */
    public CalibrationDetail() {
    }
    
    public void viewHandler(ComponentSystemEvent event) {
        selectedCalibrationRec = calibrationEJB.calibrationRecordById(calibrationRecID);
        if ( selectedCalibrationRec == null ) {
            showMessage(FacesMessage.SEVERITY_ERROR, "invalid calirbation record id", "");
            return;
        }
        
        selectedEquipment = calibrationEJB.physicalComponentById(selectedCalibrationRec.getEquipment().getPhysicalComponentId());
        if ( selectedEquipment == null ) {
            showMessage(FacesMessage.SEVERITY_ERROR, "Strange missing physical component", "");
            return;
        }
    }

    public int getCalibrationRecID() {
        return calibrationRecID;
    }

    public void setCalibrationRecID(int calibrationRecID) {
        this.calibrationRecID = calibrationRecID;
    }

    public Equipment getSelectedEquipment() {
        return selectedEquipment;
    }

    public CalibrationRecord getSelectedCalibrationRec() {
        return selectedCalibrationRec;
    }
    
    
     // ToDo: Move it to a common utility class
    private void showMessage(FacesMessage.Severity severity, String summary, String message) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(severity, summary, message));
        // FacesMessage n = new FacesMessage();
    }
}
