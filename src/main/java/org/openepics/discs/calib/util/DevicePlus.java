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
package org.openepics.discs.calib.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openepics.discs.calib.ent.CalibrationRecord;
import org.openepics.discs.calib.ent.Device;

/**
 *
 * @author vuppala
 */
public class DevicePlus implements Serializable {

    private Device equipment;
    private Date lastServicedDate;
    private Date nextDueDate;
    private String dueStatus;
    private int  calibCycle;
    private static final String DUE_SOON = "DueSoon";
    private static final String NOT_DUE_SOON = "NotDueSoon";
    private static final String PAST_DUE = "PastDue";

    // ToDo: make this configurable
    private final static int DUE_SOON_PERIOD = 30; // number of days considered 'due soon'

    private DevicePlus() {
        
    }
    
     /** 
     * Factory 
     * 
     * @param device
     * @return 
     */
    public static DevicePlus newInstance(Device device) {
        DevicePlus devicePlus = new DevicePlus();   
        if (device == null) {
            return DevicePlus.newInstance();
        } 
        devicePlus.init(device);
              
        return devicePlus;
    }
    
    public static DevicePlus newInstance() {        
        DevicePlus devicePlus = new DevicePlus();   
        // devicePlus.equipment = new Device();
        
        return devicePlus;
    }
    
    /**
     * initialize device plus
     * 
     * @param e 
     */
    public void init(Device e) {       
        equipment = e;
        lastServicedDate = lastServiced(e);        
        calibCycle = e.getCalibCycle() == null || e.getCalibCycle() == 0 ? e.getModel().getCalibrationCycle() : e.getCalibCycle();
        nextDueDate = nextDue(e);
        dueStatus = nameStatus();
    }
    
    /**
     * compute last serviced date for a device
     * 
     * @param e
     * @return 
     */
    private Date lastServiced(Device e) {
        List<Date> calibDates = new ArrayList<>();

        for (CalibrationRecord cr : e.getCalibrationRecordList()) {
            calibDates.add(cr.getCalibrationDate());
        }

        return calibDates.isEmpty() ? null : Collections.max(calibDates);
    }

    /**
     * compute next due date
     * 
     * @param e
     * @return 
     */
    private Date nextDue(Device e) {
//        Date lsdate = lastServiced(e);
        Date lsdate = this.lastServicedDate;

        if (lsdate == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(lsdate);

        // int cycle = e.getCalibCycle() == 0 ? e.getDeviceModel().getCalibrationCycle() : e.getCalibCycle();

        cal.add(Calendar.MONTH, calibCycle);
        Date nddate = cal.getTime();

        return nddate;
    }

    /**
     * compute status of device
     * 
     * @return 
     */
    private String nameStatus() {
        String stat = NOT_DUE_SOON;
        Date curDate = new Date();

        DateTime curDt = new DateTime(curDate);
        DateTime dueDt = new DateTime(nextDueDate);

        if (nextDueDate == null) {
            return stat;
        }

        if (dueDt.isBefore(curDt)) {
            stat = PAST_DUE;
        } else {
            int dueDays = Days.daysBetween(curDt, dueDt).getDays();
            if (dueDays < DUE_SOON_PERIOD) {
                stat = DUE_SOON;
            }
        }
        return stat;
    }

    public boolean isDueSoon() {
        return DUE_SOON.equalsIgnoreCase(dueStatus);
    }

    public boolean isPastDue() {
        return PAST_DUE.equalsIgnoreCase(dueStatus);
    }

    // ---------- Getters/setters
    
    public Date getLastServicedDate() {
        return lastServicedDate;
    }

    public Date getNextDueDate() {
        return nextDueDate;
    }

    public Device getDevice() {
        return equipment;
    }

    public String getDueStatus() {
        return dueStatus;
    }

    public int getCalibCycle() {
        return calibCycle;
    }

}
