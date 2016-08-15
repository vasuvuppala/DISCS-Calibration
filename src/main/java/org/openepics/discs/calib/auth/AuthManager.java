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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.openepics.discs.calib.util.UserSession;


/**
 * An implementation for the AuthManager interface using local entities 
 * 
 * 
 * @author vuppala
 */
@Stateless
public class AuthManager  {

    @Inject
    private UserSession userSession;

    @PersistenceContext
    private EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(AuthManager.class.getName());

   
    public boolean isValidUser() {     
        return (userSession.getUser() != null);
    }
    
    public Boolean isAdmin() {
        return false;
    }
    /**
     * Can the user perform any of the operations on the resource
     *
     * @param user
     * @param operations
     * @param resource
     * @return
     */
    private boolean canPerform(AuthUser user, List<AuthOperation> operations, AuthResource resource) {
        if (user == null || resource == null || operations == null || operations.isEmpty()) {
            return false;
        }
        
        TypedQuery<AuthPermission> query = em.createQuery("SELECT p FROM AuthPermission p JOIN p.role.authUserRoleList r WHERE r.user = :user AND p.resource = :resource AND p.operation IN :operations", AuthPermission.class)
                .setParameter("user", user)
                .setParameter("operations", operations)
                .setParameter("resource", resource);
        List<AuthPermission> permissions = query.getResultList();
        return !permissions.isEmpty();
    }

 
    /**
     * Find a resource given its name
     * 
     * @param name
     * @return the resource
     */
    private AuthResource findResource(String name) {
        TypedQuery<AuthResource> query = em.createQuery("SELECT r FROM AuthResource r WHERE r.name = :name", AuthResource.class)
                .setParameter("name", name);
        List<AuthResource> resources = query.getResultList();
        if (resources == null || resources.isEmpty()) {
            LOGGER.log(Level.WARNING, "#findResource: cannot determine resource {0}", name);
            return null;
        }
        return resources.get(0);
    }

    /**
     * Convert a list of operation names to a list of AuthOperation entities
     * 
     * @param operations
     * @return list of AuthOperation records
     */
    private List<AuthOperation> toAuthOperations(List<AuthOperation> operations) {
        List<String> names = new ArrayList<>();

        for (AuthOperation op : operations) {
            names.add(op.toString());
        }

        TypedQuery<AuthOperation> query = em.createQuery("SELECT r FROM AuthOperation r WHERE r.name IN :names", AuthOperation.class)
                .setParameter("names", names);
        List<AuthOperation> resources = query.getResultList();
        if (resources == null || resources.isEmpty()) {
            LOGGER.log(Level.WARNING, "#findOperation: cannot find opertions: {0}", names);
            return null;
        }
        return resources;
    }

}
