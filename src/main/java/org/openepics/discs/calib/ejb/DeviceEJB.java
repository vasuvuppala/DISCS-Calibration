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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.openepics.discs.calib.util.DevicePlus;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.util.EntityType;
import org.openepics.discs.calib.util.EntityTypeOperation;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Stateless
public class DeviceEJB {

    private static final Logger logger = Logger.getLogger(DeviceEJB.class.getName());
    @PersistenceContext(unitName = "org.openepics.discs.calibration")
    private EntityManager em;
    @EJB AuditEJB auditEJB; // ToDo: check about transactions cross EJBS and db connections

    /**
     * Finds all devices.
     *
     * @return A list of physical components
     * @see Device
     */
    public List<Device> findDevices() {
        List<Device> devs;
        TypedQuery<Device> queryComp;

        queryComp = em.createNamedQuery("Device.findAll", Device.class);
        devs = queryComp.getResultList();
        logger.log(Level.INFO, "Number of components: {0}", devs.size());

        return devs;
    }
   
    /**
     * Finds devices for a group.
     *
     * @return A list of physical components
     * @param group
     * @see Device
     */
    public List<Device> findDevices(DeviceGroup group) {
        if ( group == null ) {
            return findDevices();
        }
        
        List<Device> devs;
        TypedQuery<Device> queryComp;

        queryComp = em.createQuery("SELECT d FROM Device d WHERE d.deviceGroup = :group", Device.class)
                .setParameter("group", group);
        
        devs = queryComp.getResultList();
        logger.log(Level.INFO, "Number of devices for the given group: {0}", devs.size());

        return devs;
    }
    
    /**
     * Finds devices for a group.
     *
     * @return A list of physical components
     * @param group
     * @see Device
     */
    public List<DevicePlus> findDevicePlus(DeviceGroup group) {
        List<Device> components;
        List<DevicePlus> equips = new ArrayList<DevicePlus>();;

        components = findDevices(group);

        for (Device e : components) {
            DevicePlus ep = new DevicePlus();
            ep.init(e);
            equips.add(ep);
        }
        logger.log(Level.INFO, "Processed equipments: ", equips.size());

        return equips;
    }

    /**
     * Finds all standards.
     *
     * @return A list of physical components
     * @see Device
     */
    public List<Device> findStandards() {
        List<Device> components;
        TypedQuery<Device> queryComp;

        queryComp = em.createNamedQuery("Device.findByCalibStandard", Device.class).setParameter("calibStandard", true);
        components = queryComp.getResultList();
        logger.log(Level.INFO, "Number of standards: {0}", components.size());

        return components;
    }
    
    public Device findDevice(int id) {
        Device component;

        component = em.find(Device.class, id);
        return component;
    }

    public Device saveDevice(Device device) { 
        // device.setDateModified(new Date());
        // device.setModifiedBy("test");       
        device = em.merge(device);
        auditEJB.makeAuditEntry(EntityType.DEVICE, EntityTypeOperation.UPDATE, device.getSerialNumber(), "created or updated");
        return device;
        
    }
    
    public void deleteDevice(Device device) {
        Device ct = em.find(Device.class,device.getDeviceId());       
        em.remove(ct);    
        auditEJB.makeAuditEntry(EntityType.DEVICE, EntityTypeOperation.DELETE, device.getSerialNumber(), "deleted");
    }
}
