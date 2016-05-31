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
    
    private static final Logger LOGGER = Logger.getLogger(AppProperties.class.getName()); 
    @Resource(name="org.openepics.discs.calib.props")
    private Properties properties;
    
    // user preference names 
    public static final String UP_DEFAULT_GROUP = "DefaultGroup";
       
    // roles
    public static final String ROLE_GROUP_ADMIN = "GroupAdmin";
    public static final String ROLE_ADMIN = "Admin";
    
    // Blob store
    public static  final String BLOBSTORE_ROOT = "BlobStoreRoot";
    
    public void AppProperties(){       
    }
    
    public String getProperty(String name) {       
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        return properties.getProperty(name);
    }
}
