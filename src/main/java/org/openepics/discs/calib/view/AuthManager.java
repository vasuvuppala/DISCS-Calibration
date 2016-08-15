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
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.openepics.discs.calib.ejb.UserEJB;
import org.openepics.discs.calib.ent.Role;
import org.openepics.discs.calib.util.AppProperties;
import org.openepics.discs.calib.util.UserSession;

/**
 *
 * @author vuppala
 */
@Named
@RequestScoped
public class AuthManager implements Serializable {
    @EJB
    private UserEJB userEJB;       
    @Inject UserSession userSession;
    private static final Logger logger = Logger.getLogger(AuthManager.class.getName());
    
    // private boolean authorized = false;
    /**
     * Creates a new instance of AuthManager
     */
    public AuthManager() {
    }

    public boolean canManageCalibrations() {          
        Role roleGrpAdm = userEJB.findRole(AppProperties.ROLE_GROUP_ADMIN);
        Role roleAdm = userEJB.findRole(AppProperties.ROLE_ADMIN);
        return userEJB.hasRole(userSession.getUser(),roleGrpAdm) || userEJB.hasRole(userSession.getUser(),roleAdm);
    }
    
    public boolean canManageDevices() {          
        Role roleGrpAdm = userEJB.findRole(AppProperties.ROLE_GROUP_ADMIN);
        Role roleAdm = userEJB.findRole(AppProperties.ROLE_ADMIN);
        return userEJB.hasRole(userSession.getUser(),roleGrpAdm) || userEJB.hasRole(userSession.getUser(),roleAdm);
    }
    
}
