/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.discs.calib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.openepics.discs.calib.ent.*; 
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@ManagedBean
@ViewScoped
public class CalibrationManager implements Serializable {

    @EJB
    private CalibrationEJBLocal calibrationEJB;
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;

    private List<Equipment> physicalComponents;
    private Equipment selectedEquip;
    private List<Equipment> filteredEquipment;
    private List<Equipment> standards;
    // private String equipmentSerial;
    // inputs
    private CalibrationRecord inputCalibRecord;
    private Equipment[] inputStandards;
    private List<CalibrationMeasurement> inputMeasurements;
    private CalibrationMeasurement newMeasurement;
    private Date inputCalDate;
    private String inputCalNotes;
    private String selectedSerial;

    /**
     * Creates a new instance of CalibrationManager
     */
    public CalibrationManager() {
    }

    @PostConstruct
    private void init() {
        try {
            physicalComponents = calibrationEJB.physicalComponents();
            standards = calibrationEJB.standards();
            this.resetInputs();
            this.checkFlash();          
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Calibration Manager.");
            System.err.println(e);
        }
    }

    private void resetInputs() {
        inputMeasurements = new ArrayList<CalibrationMeasurement>();
        newMeasurement = new CalibrationMeasurement(new CalibrationMeasurementPK());
        inputCalDate = null;
        inputCalNotes = null;
        inputStandards = null;
        selectedSerial = null;
        selectedEquip = null;
    }
    
    private void checkFlash() {
        /* check if serial number was passed from the previous view */
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String flashSerial = (String) context.getFlash().get("EquipmentSerialNumber");

        if (flashSerial == null || flashSerial.isEmpty()) {
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "No serial number passed from the previous view (Flash)", "Must have an non-null serial number");
            logger.log(Level.INFO, "No serial number passed from the previous view (Flash)");
        } else {
            selectedSerial = flashSerial;
            selectedEquip = findEquipment(selectedSerial);
            if (selectedEquip == null) {
                Utility.showMessage(FacesMessage.SEVERITY_ERROR, "No equipment found. Invalid serial number from previous view (Flash)", selectedSerial);
                logger.log(Level.INFO, "No equipment found. Invalid serial number from previous view (Flash): " + selectedSerial);
            }
        }
    }

    public List<Equipment> completeEquipment(String query) {
        List<Equipment> suggestions = new ArrayList<Equipment>();

        for (Equipment p : physicalComponents) {
            if (p.getSerialNumber().contains(query)) {
                suggestions.add(p);
            }
        }

        return suggestions;
    }

    private boolean validate() {
        if (selectedEquip == null) {
            logger.log(Level.INFO, "Invalid equipment.");
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Invalid equipment", "Please enter a valid serial number.");
            return false;
        }
        if (loginManager.getUserid() == null) { //ToDo: This should be checked at EJB layer. 
            logger.log(Level.INFO, "Not logged in. Cannot add calibration");
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "You are not logged in. Cannot Add Calibration Record.", "PLease sign in");
            return false;
        }
        return true;
    }

    public void saveCalibRecord() {
        CalibrationRecord cr = new CalibrationRecord();
        try {
            if (!this.validate()) {
                return;
            }
            cr.setCalibrationDate(inputCalDate);
            cr.setNotes(inputCalNotes);
            cr.setEquipment(selectedEquip);
            // cr.setCalibrationMeasurementList(inputMeasurements);
            cr.setPerformedBy(loginManager.getUserid());

            // logger.log(Level.INFO, "notes " + inputCalNotes);
            // Utility.showMessage(FacesMessage.SEVERITY_INFO, "Notes", inputCalNotes);
            // logger.log(Level.INFO, "date " + inputCalDate);
            if (inputStandards == null) {
                logger.log(Level.INFO, "No of standard selected");
                Utility.showMessage(FacesMessage.SEVERITY_INFO, "No standard selected", "You muist select a standard");
                return;
            }

            // logger.log(Level.INFO, "No of std: " + inputStandards.length);
            // Utility.showMessage(FacesMessage.SEVERITY_INFO, "No of std: ", " " + inputStandards.length);
            calibrationEJB.addCalibRecord(cr, inputStandards, inputMeasurements);
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Calibration was successfully added", "Success!");
            this.resetInputs();
        } catch (Exception e) {
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Error: someting went awfully wrong. Calibration not saved.", "Check server log");
        }

    }

    public void resetInput(ActionEvent event) {
        this.resetInputs();
    }

    public void addMeasurement(ActionEvent event) {
        if (newMeasurement.getCalibrationMeasurementPK() == null || newMeasurement.getFunctionTested() == null) {
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "Blank input", "Please enter values for all fields of a measurement.");
            return;
        }

        inputMeasurements.add(newMeasurement);
        newMeasurement = new CalibrationMeasurement(new CalibrationMeasurementPK());
        Utility.showMessage(FacesMessage.SEVERITY_INFO, "New measurement added", "");
    }

    public Equipment findEquipment(String serial) {
        Equipment result = null;

        for (Equipment p : physicalComponents) {
            if (p.getSerialNumber().equalsIgnoreCase(serial)) {
                result = p;
                break;
            }
        }
        return result;
    }

    public void handleSelect(SelectEvent event) {
        for (Equipment p : physicalComponents) {
            if (p.getSerialNumber().equalsIgnoreCase(selectedSerial)) {
                selectedEquip = p;
                break;
            }
        }
        if (selectedEquip == null) {
            logger.log(Level.INFO, "Strangely selectedEquip is null");
            Utility.showMessage(FacesMessage.SEVERITY_INFO, "No selected equipment", "This is strange, possibly a bug.");
        }
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public Equipment getSelectedEquip() {
        return selectedEquip;
    }

    public void setSelectedEquip(Equipment selectedEquip) {
        this.selectedEquip = selectedEquip;
    }

    public List<Equipment> getFilteredEquipment() {
        return filteredEquipment;
    }

    public void setFilteredEquipment(List<Equipment> filteredEquipment) {
        this.filteredEquipment = filteredEquipment;
    }

    public CalibrationRecord getInputCalibRecord() {
        return inputCalibRecord;
    }

    public void setInputCalibRecord(CalibrationRecord inputCalibRecord) {
        this.inputCalibRecord = inputCalibRecord;
    }

    public Equipment[] getInputStandards() {
        return inputStandards;
    }

    public void setInputStandards(Equipment[] inputStandards) {
        this.inputStandards = inputStandards;
    }

    public List<CalibrationMeasurement> getInputMeasurements() {
        return inputMeasurements;
    }

    public void setInputMeasurements(List<CalibrationMeasurement> inputMeasurements) {
        this.inputMeasurements = inputMeasurements;
    }

    public Date getInputCalDate() {
        return inputCalDate;
    }

    public void setInputCalDate(Date inputCalDate) {
        this.inputCalDate = inputCalDate;
    }

    public String getInputCalNotes() {
        return inputCalNotes;
    }

    public void setInputCalNotes(String inputCalNotes) {
        this.inputCalNotes = inputCalNotes;
    }

    public List<Equipment> getEquipments() {
        return physicalComponents;
    }

    public List<Equipment> getStandards() {
        return standards;
    }

    public String getSelectedSerial() {
        return selectedSerial;
    }

    public void setSelectedSerial(String selectedSerial) {
        this.selectedSerial = selectedSerial;
    }

    public CalibrationMeasurement getNewMeasurement() {
        return newMeasurement;
    }

    public void setNewMeasurement(CalibrationMeasurement newMeasurement) {
        this.newMeasurement = newMeasurement;
    }
}
