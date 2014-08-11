/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.discs.calib.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.openepics.discs.calib.ent.Role;
import org.openepics.discs.calib.util.UserSession;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Named
@SessionScoped
public class LoginManager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(LoginManager.class.getName());
    private String userid;
    private String password;
    private boolean loggedin = false;
    private Role role;
    @Inject
    private UserSession userSession;

    /**
     * Creates a new instance of LoginManager
     */
    public LoginManager() {
    }

    @PostConstruct
    public void init() {
        // FacesContext context = FacesContext.getCurrentInstance();
        // originalURL = (String) context.getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        // logger.log(Level.INFO, "Forwarded from: " + originalURL);
    }

    public String onLogin() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            if (request.getUserPrincipal() == null) {
                request.login(userid, password);              
                // RequestContext.getCurrentInstance().addCallbackParam("loginSuccess", true); // For start view
                // context.getExternalContext().getSessionMap().put("user", inputUserID);             
                // if (originalURL != null) {
                //      context.getExternalContext().redirect(originalURL);
                // }
                loggedin = true;               
                userSession.start(userid, role);
                logger.log(Level.INFO, "Login successful for " + userid);
                showMessage(FacesMessage.SEVERITY_INFO, "You are logged in. Welcome!", userid);
            } else {
                showMessage(FacesMessage.SEVERITY_INFO, "You are already logged in!", userid);
            }
            // tell the client if the operation was a success 
            RequestContext.getCurrentInstance().addCallbackParam("success", true);
        } catch (ServletException e) {
            showMessage(FacesMessage.SEVERITY_ERROR, "Login Failed! Please try again. ", "Status: ");
            // RequestContext.getCurrentInstance().addCallbackParam("loginSuccess", false); // For start view
            logger.log(Level.INFO, "Login failed for " + userid);
            loggedin = false; 
            // tell the client if the operation was a success 
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
        } catch (Exception e) {
            showMessage(FacesMessage.SEVERITY_ERROR, "Cannot set user session! ", e.getMessage());
            logger.log(Level.INFO, "LoginManager error: {0}", e.getMessage());
            // tell the client if the operation was a success 
            RequestContext.getCurrentInstance().addCallbackParam("success", false);
        } finally {
            password = "xxxxxx"; // ToDo implement a better way destroy the password (from JVM)            
        }
        return null;
        // return originalURL;
    }

    public String onLogout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            loggedin = false;
            userid = null;
            userSession.end();
            showMessage(FacesMessage.SEVERITY_INFO, "You have been logged out.", "Thank you!");
        } catch (Exception e) {
            showMessage(FacesMessage.SEVERITY_ERROR, "Strangely, logout has failed", "That's odd!");
        } finally {
            
        }

        return "logout"; // ToDo: replace with null
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    private void showMessage(FacesMessage.Severity severity, String summary, String message) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(severity, summary, message));
        // FacesMessage n = new FacesMessage();
    }
}
