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
package org.openepics.discs.calib;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Stateless
public class CalibrationEJB implements CalibrationEJBLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    @PersistenceContext(unitName = "org.openepics.discs.calibration.punit")
    private EntityManager em;

    /**
     * Finds all physical components.
     *
     * @return A list of physical components
     * @see Equipment
     */
    @Override
    public List<Equipment> physicalComponents() {
        List<Equipment> components;
        TypedQuery<Equipment> queryComp;

        queryComp = em.createNamedQuery("Equipment.findAll", Equipment.class);
        components = queryComp.getResultList();
        logger.log(Level.INFO, "Number of components: {0}", components.size());

        return components;
    }

    /**
     * Finds all standards.
     *
     * @return A list of physical components
     * @see Equipment
     */
    @Override
    public List<Equipment> standards() {
        List<Equipment> components;
        TypedQuery<Equipment> queryComp;

        queryComp = em.createNamedQuery("Equipment.findByCalibStandard", Equipment.class).setParameter("calibStandard", true);
        components = queryComp.getResultList();
        logger.log(Level.INFO, "Number of standards: {0}", components.size());

        return components;
    }

    @Override
    public List<CalibrationRecord> calibrationRecords() {
        List<CalibrationRecord> crecs;
        TypedQuery<CalibrationRecord> queryComp;

        queryComp = em.createNamedQuery("CalibrationRecord.findAll", CalibrationRecord.class);
        crecs = queryComp.getResultList();
        logger.log(Level.INFO, "Number of components: {0}", crecs.size());

        return crecs;
    }
    
    /**
     * Finds physical components of a given type.
     *
     * @param sno Given serial number
     * @return A list of components
     * @see Equipment
     */
    /*
     @Override
     public List<Equipment> physicalComponents(String sno) {
     List<Equipment> components;
     CriteriaBuilder cb = em.getCriteriaBuilder();
     CriteriaQuery<Equipment> query = cb.createQuery(Equipment.class);
     Root<Equipment> comp = query.from(Equipment.class);
     query.where(cb.equal(comp.get(Equipment_.serialNumber), sno));

     TypedQuery<Equipment> q = em.createQuery(query);
     components = q.getResultList();
     if (components.size() > 1) {
     logger.log(Level.WARNING, "Found multiple components with serial number {0}", sno);
     }
     logger.log(Level.INFO, "Number of components: {0}", components.size());

     return components;
     }
     */
    /**
     * Finds physical components with a given id.
     *
     * @param id Given component id
     * @return A component
     * @see Equipment
     */
    @Override
    public Equipment physicalComponentById(int id) {
        Equipment component;

        component = em.find(Equipment.class, id);
        return component;
    }

    /**
     * Finds calibration record with a given id.
     *
     * @param id Given component id
     * @return A component
     * @see Equipment
     */
    @Override
    public CalibrationRecord calibrationRecordById(int id) {
        CalibrationRecord record;

        record = em.find(CalibrationRecord.class, id);
        return record;
    }

    /**
     * Finds calibration record with a given id.
     *
     * @param cr Given component id
     * @return A component
     * @see Equipment
     */
    // ToDo: improve this method. not well written
    @Override
    public CalibrationRecord addCalibRecord(CalibrationRecord cr, Equipment[] standards, List<CalibrationMeasurement> measurements) {
        CalibrationDevice cd;
        if (cr == null) {
            logger.log(Level.SEVERE, "CcalibrationRecord is null!");
            return cr;
        }
        em.persist(cr);
        em.flush();
        // em.refresh(cr); // ToDo: eliminate these by annotating CR ID by 'IDENTITY' (auto increment)
        /* 
         if (cr == null) {
         logger.log(Level.SEVERE, "CcalibrationRecord is null after em.persist!");
         }
         */
        // int crid = (Integer) em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(cr);
        Integer crid = cr.getCalibrationRecordId();
        logger.log(Level.SEVERE, " calibration record id: " + crid);

        for (Equipment p : standards) {
            if (p == null) {
                logger.log(Level.SEVERE, "standard is null!");
            }

            // logger.log(Level.SEVERE, " cr: " + cr.getCalibrationRecordId() + " pc: " + p.getEquipmentId());
            logger.log(Level.SEVERE, " cr id: " + crid + " pc: " + p.getPhysicalComponentId());

            cd = new CalibrationDevice(crid, p.getPhysicalComponentId());
            em.persist(cd);
            cd.setCalibrationRecord(cr);
            cd.setEquipment(p);
            cr.getCalibrationDeviceList().add(cd);
            p.getCalibrationDeviceList().add(cd);
            em.merge(p);
        }
        for (CalibrationMeasurement measurement : measurements) {
            measurement.getCalibrationMeasurementPK().setCalibrationRecordId(crid);
            measurement.setCalibrationRecord(cr);
            em.persist(measurement);
        }
        cr.setCalibrationMeasurementList(measurements);
        // update the equipment
        cr.getEquipment().getCalibrationRecordList().add(cr);
        em.merge(cr.getEquipment());

        return cr;
    }
    
    @Override
    public void deleteCalibRecord(Integer crid) throws Exception {
        CalibrationRecord cr;
        
        cr = em.find(CalibrationRecord.class, crid);
        if (cr == null) {
            logger.log(Level.SEVERE, "Calibration record from db is null!");
            throw new Exception("No calibration record with key found from db");
        }
       
        logger.log(Level.SEVERE, "Delete calibration record id: " + crid);

        for (CalibrationDevice cd : cr.getCalibrationDeviceList()) {
            logger.log(Level.INFO, "removing calibration device: " + cd.getEquipment().getSerialNumber());
            em.remove(cd);
        }
        for (CalibrationMeasurement measurement : cr.getCalibrationMeasurementList()) {
            logger.log(Level.INFO, "removing measurement: " + measurement.getCalibrationMeasurementPK().getStep());
            em.remove(measurement);
        }
        logger.log(Level.INFO, "removing calibration record from equipment: " + cr.getCalibrationRecordId());
        Equipment e = cr.getEquipment();
        e.getCalibrationRecordList().remove(cr);
        // em.merge(e);
        logger.log(Level.INFO, "removing calibration record from db: " + cr.getCalibrationRecordId());
        em.remove(cr);
    }
    
    // ToDo: of course this needs to be dramatically changed!
    @Override
    public boolean isAuthorized(String user) {
        String[] admins = {"drewyor", "vuppala"};
        // String[] admins = {"drewyor"};
        
        if ( user == null ) {
            return false;
        }
        for(String u: admins) {
            if (u.equals(user)) {
                logger.log(Level.INFO, user + "is authorized" );
                return true;
            }
        }
        return false;
    }
}
