/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.discs.calib;

import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@ManagedBean
@ViewScoped
public class EquipConverter implements Converter {
@ManagedProperty(value = "#{calibrationManager}")
    private CalibrationManager calibrationManager;
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {                  
                Equipment equip = calibrationManager.findEquipment(submittedValue);
                return equip;  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));  
            }  
        }           
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return ((Equipment) value).getSerialNumber();  
        }  
    }  

    public void setCalibrationManager(CalibrationManager calibrationManager) {
        this.calibrationManager = calibrationManager;
    }  
    /**
     * Creates a new instance of EquipConverter
     */
    public EquipConverter() {
    }
}
