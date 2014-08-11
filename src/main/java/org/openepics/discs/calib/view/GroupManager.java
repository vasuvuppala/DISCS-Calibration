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

import org.openepics.discs.calib.ejb.CalibrationEJB;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@ViewScoped
public class GroupManager implements Serializable {

    @EJB
    private CalibrationEJB calibrationEJB;
    private static final Logger logger = Logger.getLogger(GroupManager.class.getName());
    
    private List<DeviceGroup> groups; 
    private DeviceGroup selectedGroup;
    private List<DeviceGroup> filteredGroups;
        
    public GroupManager() {
    }

    @PostConstruct
    private void init() {
        try {
            groups = calibrationEJB.findGroups();           
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Model Manager.");
            System.err.println(e);
        }
    }

    public DeviceGroup findGroup(int id) {
        DeviceGroup result = null;

        for (DeviceGroup p : groups) {
            if (p.getGroupId().equals(id)) {
                result = p;
                break;
            }
        }
        return result;
    }

    public DeviceGroup getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(DeviceGroup selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<DeviceGroup> getFilteredGroups() {
        return filteredGroups;
    }

    public void setFilteredGroups(List<DeviceGroup> filteredGroups) {
        this.filteredGroups = filteredGroups;
    }

    public List<DeviceGroup> getGroups() {
        return groups;
    }
    
    
}
