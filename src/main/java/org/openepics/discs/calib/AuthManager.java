/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author vuppala
 */
@ManagedBean
@RequestScoped
public class AuthManager implements Serializable {

    @EJB
    private CalibrationEJBLocal calibrationEJB;
    private static final Logger logger = Logger.getLogger("org.openepics.discs.calibration");
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;
    
    // private boolean authorized = false;
    /**
     * Creates a new instance of AuthManager
     */
    public AuthManager() {
    }

    public boolean isAuthorized() {
             
        if ( loginManager.isLoggedin() ) {    
            String user = loginManager.getUserid();
            return calibrationEJB.isAuthorized(user);
        } else {
            return false;
        }
    }
    
    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }      
}
