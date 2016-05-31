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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.UserSession;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Stateless
public class AuditEJB {

    private static final Logger LOGGER = Logger.getLogger(AuditEJB.class.getName());
    
    @PersistenceContext
    private EntityManager em;
    @Inject UserSession userSession;
    
     // ---------------- Audit Records -------------------------  
    public List<AuditRecord> findAuditRecords() {
        List<AuditRecord> arecs;
        TypedQuery<AuditRecord> query;
        query = em.createNamedQuery("AuditRecord.findAll", AuditRecord.class);
        arecs = query.getResultList();
        LOGGER.log(Level.INFO, "Number of audit records: {0}", arecs.size());

        return arecs;
    }
  
    public AuditRecord findDAuditRecord(int id) {
        return em.find(AuditRecord.class, id);
    }
    
    // ----------- Audit record ---------------------------------------
    // public void makeAuditEntry(EntityType entType, EntityTypeOperation oper, String key, String entry) {
    public void makeAuditEntry(EntityType entType, EntityTypeOperation oper, String key, String entry) {
        AuditRecord arec = new AuditRecord();
        // AuditRecord arec = new AuditRecord(new Date(), oper, userSession.getUserId(), entry);
        
        arec.setEntityType(entType);
        arec.setOper(oper);
        arec.setEntityKey(key);
        arec.setUser(userSession.getUserId());
        arec.setEntry(entry);
               
        em.persist(arec);
    }
}
