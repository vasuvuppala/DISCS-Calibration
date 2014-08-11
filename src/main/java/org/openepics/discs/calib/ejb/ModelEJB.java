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

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Stateless
public class ModelEJB {

    private static final Logger logger = Logger.getLogger(ModelEJB.class.getName());
    @PersistenceContext(unitName = "org.openepics.discs.calibration")
    private EntityManager em;

    /**
     * Finds all device models
     *
     * @return A list of physical components
     * @see Model
     */
    public List<DeviceModel> findModels() {
        List<DeviceModel> models;
        TypedQuery<DeviceModel> queryComp;

        queryComp = em.createNamedQuery("DeviceModel.findAll", DeviceModel.class);
        models = queryComp.getResultList();
        logger.log(Level.INFO, "Number of components: {0}", models.size());

        return models;
    }
   
    
    public DeviceModel findModel(int id) {
        DeviceModel model;

        model = em.find(DeviceModel.class, id);
        return model;
    }

    public DeviceModel saveModel(DeviceModel model) { 
        model.setDateModified(new Date());
        model.setModifiedBy("test");
        return em.merge(model);       
    }
    
    public void deleteModel(DeviceModel model) {
        DeviceModel ct = em.find(DeviceModel.class,model.getModelId());
        em.remove(ct);     
    }
}
