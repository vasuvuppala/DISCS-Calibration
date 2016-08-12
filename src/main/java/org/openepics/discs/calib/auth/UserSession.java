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
package org.openepics.discs.calib.auth;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.openepics.discs.calib.ent.UserPreference;
import org.openepics.discs.calib.util.AppProperties;
import org.openepics.discs.calib.util.Utility;


/**
 * The session.
 * 
 * @author vuppala
 */
@Named
@SessionScoped
public class UserSession implements Serializable {
      
    @EJB
    private AuthEJB authEJB;
    
    @Inject 
    private AppProperties appProperties;
    
    private static final Logger logger = Logger.getLogger(UserSession.class.getName());

    private String userId; // user unique login name
    private AuthUser user; // the user record. ToDo:  keep this in the session?    
    private String currentTheme; // current GUI theme
   

    public UserSession() {
        
    }
    
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
            
            currentTheme = prefEJB.defaultThemeName();
            logger.log(Level.SEVERE, "UserSession: Deployment evnironment is {0}", appProperties.inProduction()? "Production": "Non-Production");           
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
    public void start(String userId, AuthRole role)  {
        this.userId = userId;
        
        user = authEJB.findUser(userId);
        
        if (user != null) {
            UserPreference pref = prefEJB.findPreference(user, PreferenceName.DEFAULT_THEME);
            if (pref != null) {
                currentTheme = pref.getPrefValue();
            }
            
        } else {
            logger.log(Level.WARNING, "User not defined in the Hour Log database: {0}", userId);
            Utility.showMessage(FacesMessage.SEVERITY_FATAL, "You are not registered as Hour Log user", "Please contact Hour Log administrator.");
        }
        // facility = facilityEJB.defaultFacility(user);      
        //logbook = facility.getOpsLogbook();
        if (currentTheme == null) {
            currentTheme = prefEJB.defaultThemeName();
        }
       
    }

    /**
     * Switch to a new facility
     *
     * @param facility
     */
    
    
    /**
     * end user session
     *
     * @author vuppala
     *
     */
    public void end() {
        userId = null;
        user = null;
        
    }

    // -- getters/setters 
    
    public String getUserId() {
        return userId;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(String currentTheme) {
        this.currentTheme = currentTheme;
    }

}
