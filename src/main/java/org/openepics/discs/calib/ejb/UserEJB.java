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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.openepics.discs.calib.ent.DeviceGroup;
import org.openepics.discs.calib.ent.Role;
import org.openepics.discs.calib.ent.Sysuser;
import org.openepics.discs.calib.ent.UserRole;
import org.openepics.discs.calib.util.AppProperties;

/**
 *
 * @author vuppala
 */
@Stateless
public class UserEJB {

    private static final Logger logger = Logger.getLogger(UserEJB.class.getName());
    @PersistenceContext(unitName = "org.openepics.discs.calibration")
    private EntityManager em;

    /**
     * Finds user record given a user id.
     *
     * @author vuppala
     * @param userId Id of the desired user
     * @return User record for a given user id
     */
    public Sysuser findUser(String userId) {
        TypedQuery<Sysuser> query = em.createNamedQuery("Sysuser.findByUniqueName", Sysuser.class).setParameter("uniqueName", userId);
        List<Sysuser> users = query.getResultList();

        if (users == null || users.isEmpty()) {
            logger.log(Level.WARNING, "UserEJB: No employees found with id {0}", userId);
            return null;
        }

        if (users.size() > 1) {
            logger.log(Level.WARNING, "UserEJB: there are more than 1 users with the same login id {0}", userId);
        }
        return users.get(0);
    }

    /**
     * Finds users
     *
     * @author vuppala
     * @return all users
     */
    public List<Sysuser> findUsers() {
        List<Sysuser> users;
        TypedQuery<Sysuser> query = em.createNamedQuery("Sysuser.findAll", Sysuser.class);
        users = query.getResultList();
        logger.log(Level.INFO, "users found: " + users.size());
        return users;
    }
    
    /**
     * Finds users who are subscribed to reminders
     *
     * @author vuppala
     * @return all users
     */
    public List<Sysuser> reminderSubscribers(DeviceGroup group) {
        return findUsers();  //ToDo: send only those in the group and subscribers
    }

    /**
     * Find roles
     *
     * @author vuppala
     * @return all roles
     */
    public List<Role> findRoles() {
        List<Role> roles;
        TypedQuery<Role> query = em.createNamedQuery("Role.findAll", Role.class);
        roles = query.getResultList();
        logger.log(Level.INFO, "roles found: " + roles.size());
        return roles;
    }

    /**
     * Find roles
     *
     * @author vuppala
     * @param name
     * @return all roles
     */
    public Role findRole(String name) {
        List<Role> roles;
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name);
        roles = query.getResultList();
        
        if ( roles.isEmpty() ) {
            return null;
        } else {
            if (roles.size() > 1) {
                logger.log(Level.WARNING, "Multiple roles found for " + name);
            }
            return roles.get(0);
        }
    }
    
    /**
     * Find user roles
     *
     * @author vuppala
     * @return all user roles
     */
    public List<UserRole> findUserRoles() {
        List<UserRole> uroles;
        TypedQuery<UserRole> query = em.createNamedQuery("UserRole.findAll", UserRole.class);
        uroles = query.getResultList();
        logger.log(Level.INFO, "user roles found: " + uroles.size());
        return uroles;
    }
    
   

    /**
     * Find user default group
     *
     * @author vuppala
     * @return all user roles
     */
    public DeviceGroup findDefaultGroup(Sysuser user) {
        if (user == null ) {
            return null;
        }
        
        List<String> pvals;
        TypedQuery<String> query = em.createQuery("SELECT p.prefValue FROM UserPreference p WHERE p.sysuser = :user AND p.prefName = :name", String.class)
                .setParameter("user", user)
                .setParameter("name", AppProperties.UP_DEFAULT_GROUP);
        pvals = query.getResultList();
        
        if (pvals == null || pvals.isEmpty()) {
            logger.log(Level.WARNING, "No default group found for user " + user.getUniqueName());
            return findDefaultGroup();
        }
        logger.log(Level.INFO, "pref values found: " + pvals.size());
        if (pvals.size() > 1) {
            logger.log(Level.WARNING, "UserEJB.findDefaultGroup: there are more than 1 default groups for {0}", user.getUniqueName());
        }

        String prefValue = pvals.get(0);

        return findGroup(Integer.parseInt(prefValue));
    }

    /**
     * Find group
     *
     * @author vuppala
     * @param id group id
     * @return group
     */
    public DeviceGroup findGroup(int id) {
        return em.find(DeviceGroup.class, id);
    }

    /**
     * Find user default group
     *
     * @author vuppala
     * @return group
     */
    public DeviceGroup findDefaultGroup() {
        List<DeviceGroup> groups;
        TypedQuery<DeviceGroup> query = em.createQuery("SELECT g FROM DeviceGroup g", DeviceGroup.class)
                .setFirstResult(0)
                .setMaxResults(1);
        groups = query.getResultList();

        if (groups.isEmpty()) {
            logger.log(Level.WARNING, "No groups found");
            return null;
        } else {
            return groups.get(0);
        }
    }
    
    /**
     * Does user have given role?
     *
     * @author vuppala
     * @param user
     * @param role
     * @return true or false
     */
    public boolean hasRole(Sysuser user, Role role) {
        List<UserRole> uroles;
        TypedQuery<UserRole> query = em.createQuery("SELECT u FROM UserRole u WHERE u.sysuser = :user AND u.role = :role", UserRole.class)
                .setParameter("user", user)
                .setParameter("role", role);
        uroles = query.getResultList();

        if (uroles.isEmpty()) {         
            return false;
        } else {
            if (uroles.size() > 1) {
                logger.log(Level.WARNING, "UserEJB.hasRole: multiple user-roles");
            }
            return true;
        }
    }
}
