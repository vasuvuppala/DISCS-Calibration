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

package org.openepics.discs.calib.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.openepics.discs.calib.util.UserSession;

/**
 *
 * @author vuppala
 */
@Stateless
public class AuthEJB {
    private static final Logger logger = Logger.getLogger(AuthEJB.class.getName());
    @PersistenceContext(unitName = "org.openepics.discs.calibration")
    private EntityManager em;
    @Inject
    private UserSession userSession;
    
    public boolean canAccessManageMenu() {
        logger.log(Level.INFO,"AuthEJB: checking create log auth for {0}", userSession.getUserId());
        return userSession.getSysuser() != null;
    }
    
    public boolean canEditLog() {
         return userSession.getSysuser() != null;
    }
   
    public boolean canChangeShift() {
         return userSession.getSysuser() != null;
    }
}
