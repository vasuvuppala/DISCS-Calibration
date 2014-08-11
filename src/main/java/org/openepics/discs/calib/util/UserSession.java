/*
 * This software is Copyright by the Board of Trustees of Michigan
 *  State University (c) Copyright 2013, 2014.
 *  
 *  You may use this software under the terms of the GNU public license
 *  (GPL). The terms of this license are described at:
 *    http://www.gnu.org/licenses/gpl.txt
 *  
 *  Contact Information:
 *       Facility for Rare Isotope Beam
 *       Michigan State University
 *       East Lansing, MI 48824-1321
 *        http://frib.msu.edu
 */
package org.openepics.discs.calib.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.UserEJB;
import org.openepics.discs.calib.ent.DeviceGroup;
import org.openepics.discs.calib.ent.Role;
import org.openepics.discs.calib.ent.Sysuser;

/**
 *
 * @author vuppala
 */
@Named
@SessionScoped
public class UserSession implements Serializable {

    @EJB
    private UserEJB userEJB;
    private static final Logger logger = Logger.getLogger(UserSession.class.getName());

    private String userId; // user id
    private String token;   // auth token
    private Role role; // current role
    private boolean loggedIn = false;
    private Sysuser user; // the employee record. ToDo:  keep this in the session?
    private DeviceGroup group; // current facility: ToDo: keep this in the session?   

    public void UserSession() {
    }

    /**
     * Initialize user session.
     *
     * @author vuppala
     *
     */
    @PostConstruct
    public void init() {
        try {
            // group = userEJB.defaultFacility(); // Default facility adn logbook  for users who are not logged in          
        } catch (Exception e) {
            logger.log(Level.SEVERE, "UserSession: Can not initialize: {0}", e.getMessage());
        }
    }

    /**
     * Start user session
     *
     * @author vuppala
     * @param userId User login id
     * @param role User role
     *
     */
    public void start(String userId, Role role) {
        this.userId = userId;
        this.role = role;
        user = userEJB.findUser(userId);
        group = userEJB.findDefaultGroup(user);
        loggedIn = true;
    }

    /**
     * end user session
     *
     * @author vuppala
     *
     */
    public void end() {
        userId = null;
        user = null;
        role = null;
        loggedIn = false;
        token = null;
        group = null;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Sysuser getSysuser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public DeviceGroup getGroup() {
        return group;
    }

}
