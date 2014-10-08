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

package org.openepics.discs.calib.util;

import java.util.Properties;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;

/**
 *
 * @author vuppala
 */
@Named
public class AppProperties {
    
    private static final Logger logger = Logger.getLogger(AppProperties.class.getName()); 
    @Resource(name="org.openepics.discs.calib.props")
    private Properties properties;
    
    // user preference names 
    public static final String UP_DEFAULT_GROUP = "DefaultGroup";
       
    // roles
    public static final String ROLE_GROUP_ADMIN = "GroupAdmin";
    public static final String ROLE_ADMIN = "Admin";
    
    // Blob store
    public static  final String BLOBSTORE_ROOT = "BlobStoreRoot";
    
    // Artifact
    public static  final String ARTIFACT_DOC = "file"; // ToDo: use enum instead
    public static  final String ARTIFACT_TR = "uri";  // ToDo: use enum instead
    
    public void AppProperties(){       
    }
    
    public String getProperty(String name) {       
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        return properties.getProperty(name);
    }

    /*
    public static Properties getProperties(String jndiName) {
    Properties properties = null;
    try {
    InitialContext context = new InitialContext();
    properties = (Properties) context.lookup(jndiName);
    context.close();
    } catch (NamingException e) {
    logger.log(Level.SEVERE,"Naming error occurred while initializing properties from JNDI.", e);
    return null;
    }
    return properties;
    }
     */
    
}
