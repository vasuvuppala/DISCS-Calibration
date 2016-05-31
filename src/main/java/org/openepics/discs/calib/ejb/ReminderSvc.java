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
package org.openepics.discs.calib.ejb;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.openepics.discs.calib.ent.DeviceGroup;
import org.openepics.discs.calib.ent.Sysuser;
import org.openepics.discs.calib.util.DevicePlus;

/**
 *
 * @author vuppala
 */
@Stateless
public class ReminderSvc {

    @EJB
    private DeviceEJB deviceEJB;
    @EJB
    private CalibrationEJB calibrationEJB;
    @EJB
    private UserEJB userEJB;

    private static final Logger LOGGER = Logger.getLogger(ReminderSvc.class.getName());

    //ToDo: Make the following configuratble
    private static final String FROM_ADDRESS = "calibrations@frib.msu.edu";
    private static final String[] RECIPIENTS = {"calibrations@frib.msu.edu"};
    // private static final String SUBJECT = "Calibrations: Reminder";

    private boolean inProgress;

    public void ReminderSvc() {
        inProgress = false;
    }

    // ToDo: Make scheduling configurable.
     
    // @Schedule(second = "0", minute = "*/1", hour = "*", month = "*", year = "*")  
    @Schedule(second = "0", minute = "0", hour = "12", month = "*", year = "*") 
    public void runService() {
        try {
            if (inProgress) {
                LOGGER.severe("reminder service already running. skipping.");
                return;
            }
            inProgress = true;
            LOGGER.info("Proteus Calibration: Reminder service started. Time is " + new Date().toString());
            // Thread.sleep(120000L);
            // Find due calibrations for each group
            List<DeviceGroup> groups = calibrationEJB.findGroups();
            for (DeviceGroup group : groups) {
                String message = dueCalibrations(group);
                String[] recipients = findRecipients(group);
                if (recipients == null || recipients.length == 0) {
                    recipients = RECIPIENTS;
                }
                if (message == null || message.isEmpty()) {
                    LOGGER.info("Reminder service: no calibrations due for group " + group.getName());
                } else {
                    String subject = "Calibrations Reminder: For Group " + group.getName();
                    LOGGER.info("Sendind reminder to " + Arrays.toString(recipients));
                    sendMail(FROM_ADDRESS, recipients, subject, message);
                }
            }
            inProgress = false;
        } catch (Exception e) {
            LOGGER.severe("error in reminder svc");
        }
    }

    private  String[] findRecipients(DeviceGroup group) {       
        List<Sysuser> users = userEJB.reminderSubscribers(group); 
        String[] recipients = new String[users.size()];
        
        for (int i = 0; i < users.size(); i++) {
            recipients[i] = users.get(i).getEmail();
        }
        
        return recipients;
    }
    
    
    private String dueCalibrations(DeviceGroup group) {
        StringBuilder dueRep = new StringBuilder();
        StringBuilder dueSoon = new StringBuilder();
        StringBuilder pastDue = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        int numPastDue = 0;
        int numDueSoon = 0;
        List<DevicePlus> equipments = deviceEJB.findDevicePlus(group);

        for (DevicePlus e : equipments) {
            if (e.getDevice().getActive() && e.isDueSoon()) {
                numDueSoon++;
                dueSoon.append("   " + e.getDevice().getModel().getName() + "-" + e.getDevice().getModel().getManufacturer() + "-" + e.getDevice().getSerialNumber() + ". Due: " + df.format(e.getNextDueDate()));
                dueSoon.append(System.getProperty("line.separator"));
            }
            if (e.getDevice().getActive() && e.isPastDue()) {
                numPastDue++;
                pastDue.append("   " + e.getDevice().getModel().getName() + "-" + e.getDevice().getModel().getManufacturer() + "-" + e.getDevice().getSerialNumber() + ". Due: " + df.format(e.getNextDueDate()));
                pastDue.append(System.getProperty("line.separator"));
            }
        }

        if (numPastDue > 0) {
            dueRep.append("The following devices are past due for calibration: \r\n");
            dueRep.append(pastDue);
            dueRep.append(System.getProperty("line.separator"));
        }
        if (numDueSoon > 0) {
            dueRep.append("The following devices are due for calibration: \r\n");
            dueRep.append(dueSoon);
            dueRep.append(System.getProperty("line.separator"));
        }

        return dueRep.toString();
    }

    //ToDo: Make mail parameters configurable
    private void sendMail(String from, String[] to, String subject, String message) {
        try {
            Email email = new SimpleEmail();

            LOGGER.info("sending email ...");
            email.setHostName("exchange.nscl.msu.edu");
            email.setSmtpPort(25);
            // email.setSmtpPort(465);
            // email.setAuthenticator(new DefaultAuthenticator("iser", "xxxxxx"));
            // email.setSSLOnConnect(true);
            email.setTLS(true);
            // email.setDebug(true);
            email.setFrom(from);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);
            email.send();
        } catch (Exception e) {
            LOGGER.severe("error while sending email");
        }
    }
}
