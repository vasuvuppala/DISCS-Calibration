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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.EntityType;
import org.openepics.discs.calib.util.EntityTypeOperation;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Stateless
public class CalibrationEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    @PersistenceContext(unitName = "org.openepics.discs.calibration")
    private EntityManager em;
    @EJB
    AuditEJB auditEJB; // ToDo: check about transactions cross EJBS and db connections

    /**
     * Finds all physical components.
     *
     * @return A list of physical components
     * @see Device
     */
    public List<Device> findDevices() {
        List<Device> components;
        TypedQuery<Device> queryComp;

        queryComp = em.createNamedQuery("Device.findAll", Device.class);
        components = queryComp.getResultList();
        logger.log(Level.INFO, "Number of components: {0}", components.size());

        return components;
    }

    /**
     * Finds all device groups.
     *
     * @return A list of device groups
     * @see Model
     */
    public List<DeviceGroup> findGroups() {
        List<DeviceGroup> groups;
        TypedQuery<DeviceGroup> queryComp;

        queryComp = em.createNamedQuery("DeviceGroup.findAll", DeviceGroup.class);
        groups = queryComp.getResultList();
        logger.log(Level.INFO, "Number of groups: {0}", groups.size());

        return groups;
    }

    /**
     * Finds all standards.
     *
     * @return A list of physical components
     * @see Device
     */
    public List<Device> standards() {
        List<Device> components;
        TypedQuery<Device> queryComp;

        queryComp = em.createNamedQuery("Device.findByCalibStandard", Device.class).setParameter("calibStandard", true);
        components = queryComp.getResultList();
        logger.log(Level.INFO, "Number of standards: {0}", components.size());

        return components;
    }

    public List<CalibrationRecord> calibrationRecords() {
        List<CalibrationRecord> crecs;
        TypedQuery<CalibrationRecord> queryComp;

        queryComp = em.createNamedQuery("CalibrationRecord.findAll", CalibrationRecord.class);
        crecs = queryComp.getResultList();
        logger.log(Level.INFO, "Number of components: {0}", crecs.size());

        return crecs;
    }

    // ToDo: remove. replace with deviceEJB.findDevice()
    public Device physicalComponentById(int id) {
        Device component;

        component = em.find(Device.class, id);
        return component;
    }

    /**
     * Finds calibration record with a given id.
     *
     * @param id Given component id
     * @return A component
     * @see Device
     */
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
     * @see Device
     */
    // ToDo: improve this method. not well written
    public CalibrationRecord addCalibRecord(CalibrationRecord cr, Device[] standards, List<CalibrationMeasurement> measurements) {
        //logger.info("CalibrationManager.addCalibRecord: entering");
        if (cr == null) {
            logger.log(Level.SEVERE, "CcalibrationRecord is null!");
            return cr;
        }
        em.persist(cr);

        for (Device p : standards) {
            if (p == null) {
                logger.log(Level.SEVERE, "standard is null!");
                continue;
            }
            Device dev = em.find(Device.class, p.getDeviceId());
            CalibrationDevice cd = new CalibrationDevice();
            cd.setDevice(dev);
            cd.setCalibrationRecord(cr);
            em.persist(cd);
            cr.getCalibrationDeviceList().add(cd);
        }

        for (CalibrationMeasurement measurement : measurements) {
            measurement.setCalibrationRecord(cr);
            em.persist(measurement);
        }

        cr.setCalibrationMeasurementList(measurements);
        // update the equipment
        cr.getDevice().getCalibrationRecordList().add(cr);
        em.merge(cr.getDevice());
        auditEJB.makeAuditEntry(EntityType.CALIBRATION_RECORD, EntityTypeOperation.CREATE, cr.getDevice().getSerialNumber(), "created calibration record");

        return cr;
    }

    public void deleteCalibRecord(Integer crid) throws Exception {
        CalibrationRecord cr;

        cr = em.find(CalibrationRecord.class, crid);
        if (cr == null) {
            logger.log(Level.SEVERE, "Calibration record from db is null!");
            throw new Exception("No calibration record with key found from db");
        }

        logger.log(Level.SEVERE, "Delete calibration record id: " + crid);

        for (CalibrationDevice cd : cr.getCalibrationDeviceList()) {
            logger.log(Level.INFO, "removing calibration device: " + cd.getDevice().getSerialNumber());
            em.remove(cd);
        }
        for (CalibrationMeasurement measurement : cr.getCalibrationMeasurementList()) {
            logger.log(Level.INFO, "removing measurement: " + measurement.getStep());
            em.remove(measurement);
        }
        logger.log(Level.INFO, "removing calibration record from equipment: " + cr.getCalibrationRecordId());
        Device e = cr.getDevice();
        e.getCalibrationRecordList().remove(cr);
        // em.merge(e);
        logger.log(Level.INFO, "removing calibration record from db: " + cr.getCalibrationRecordId());
        em.remove(cr);
        auditEJB.makeAuditEntry(EntityType.CALIBRATION_RECORD, EntityTypeOperation.DELETE, cr.getDevice().getSerialNumber(), "deleted calibration record");
    }
}
