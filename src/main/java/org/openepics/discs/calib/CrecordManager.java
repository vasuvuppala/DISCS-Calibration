/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib;

import java.util.List;
import java.util.logging.Level;
import org.openepics.discs.calib.ent.*; 
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author vuppala
 */
@ManagedBean
@ViewScoped
public class CrecordManager {

    @EJB
    private CalibrationEJBLocal calibrationEJB;
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    
    private List<CalibrationRecord> crecords;
    
    private List<CalibrationRecord> filteredCRs;
       
    /**
     * Creates a new instance of CrecordManager
     */
    public CrecordManager() {
    }
    
    @PostConstruct
    private void init() {
        try {
        crecords = calibrationEJB.calibrationRecords();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Calibration Record Manager.");
            System.err.println(e);
        }
    }

    public List<CalibrationRecord> getCrecords() {
        return crecords;
    }

    public List<CalibrationRecord> getFilteredCRs() {
        return filteredCRs;
    }

    public void setFilteredCRs(List<CalibrationRecord> filteredCRs) {
        this.filteredCRs = filteredCRs;
    }
    
}
