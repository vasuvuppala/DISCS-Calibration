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
package org.openepics.discs.calib.converter;

import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import org.openepics.discs.calib.ent.*;
import org.openepics.discs.calib.view.ModelManager;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
public class ModelConverter implements Converter {
    @Inject private ModelManager modelManager;
    private static final Logger logger = Logger.getLogger(ModelConverter.class.getName());
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {                  
                DeviceModel model = modelManager.findModel(Integer.parseInt(submittedValue));
                return model;  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "ModelConverter.getAsObject: Conversion Error", "Not a valid object"));  
            }  
        }           
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return ((DeviceModel) value).getModelId().toString();  
        }  
    }  

}
