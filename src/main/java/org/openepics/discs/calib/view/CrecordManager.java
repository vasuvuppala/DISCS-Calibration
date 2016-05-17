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
import org.openepics.discs.calib.ejb.CalibrationEJB;
import java.util.List;
import java.util.logging.Level;
import org.openepics.discs.calib.ent.*; 
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author vuppala
 */
@Named
@ViewScoped
public class CrecordManager implements Serializable {

    @EJB
    private CalibrationEJB calibrationEJB;
    private static final Logger logger = Logger.getLogger(CrecordManager.class.getName());
    
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
