/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.view;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.openepics.discs.calib.ejb.AuditEJB;
import org.openepics.discs.calib.ent.AuditRecord;
import org.openepics.discs.calib.util.Utility;


/**
 *
 * @author vuppala
 */
@Named
@ViewScoped
public class AuditView {
    @EJB
    private AuditEJB auditEJB;
    private static final Logger logger = Logger.getLogger(AuditView.class.getName());
    
    private List<AuditRecord> objects;   
    private List<AuditRecord> filteredObjects;
    private AuditRecord selectedObject;
    /**
     * Creates a new instance of AuditManager
     */
    public AuditView() {
    }
    
    @PostConstruct
    public void init() {
        try {
            objects = auditEJB.findAuditRecords();          
        } catch (Exception e) {
            System.err.println(e.getMessage());
            logger.log(Level.SEVERE, "Cannot retrieve audit records");
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Error in getting audit records", " ");
        }
    }

    public List<AuditRecord> getObjects() {
        return objects;
    }

    public List<AuditRecord> getFilteredObjects() {
        return filteredObjects;
    }

    public void setFilteredObjects(List<AuditRecord> filteredObjects) {
        this.filteredObjects = filteredObjects;
    }
      
}
