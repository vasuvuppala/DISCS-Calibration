/*
 * This software is Copyright by the Board of Trustees of Michigan
 *  State University (c) Copyright 2014, 2015.
 *  
 *  You may use this software under the terms of the GNU public license
 *  (GPL). The terms of this license are described at:
 *    http://www.gnu.org/licenses/gpl.txt
 *  
 *  Contact Information:
 *       Facility for Rare Isotope Beam
 *       Michigan State University
 *       East Lansing, MI 48824-1321
 *        http://frib.msu.edu
 */
package org.openepics.discs.calib.auth;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.openepics.discs.calib.ent.UserPreference;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "sysuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthUser.findAll", query = "SELECT s FROM AuthUser s"),
    @NamedQuery(name = "AuthUser.findByUserId", query = "SELECT s FROM AuthUser s WHERE s.userId = :userId"),
    @NamedQuery(name = "AuthUser.findByFirstName", query = "SELECT s FROM AuthUser s WHERE s.firstName = :firstName"),
    @NamedQuery(name = "AuthUser.findByLastName", query = "SELECT s FROM AuthUser s WHERE s.lastName = :lastName"),
    @NamedQuery(name = "AuthUser.findByLoginId", query = "SELECT s FROM AuthUser s WHERE s.loginId = :loginId"),
    @NamedQuery(name = "AuthUser.findByNickName", query = "SELECT s FROM AuthUser s WHERE s.nickName = :nickName"),
    @NamedQuery(name = "AuthUser.findByDepartment", query = "SELECT s FROM AuthUser s WHERE s.department = :department"),
    @NamedQuery(name = "AuthUser.findByEmail", query = "SELECT s FROM AuthUser s WHERE s.email = :email"),
    @NamedQuery(name = "AuthUser.findByCurrentEmployee", query = "SELECT s FROM AuthUser s WHERE s.currentEmployee = :currentEmployee"),
    @NamedQuery(name = "AuthUser.findBySmsAddress", query = "SELECT s FROM AuthUser s WHERE s.smsAddress = :smsAddress")
    })
public class AuthUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private Integer userId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "first_name")
    private String firstName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "last_name")
    private String lastName;
    
    @Size(max = 32)
    @Column(name = "login_id")
    private String loginId;
    
    @Size(max = 16)
    @Column(name = "nick_name")
    private String nickName;
    
    @Size(max = 64)
    @Column(name = "department")
    private String department;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;
    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "current_employee")
    private boolean currentEmployee;
    
    @Size(max = 64)
    @Column(name = "sms_address")
    private String smsAddress;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    @Version
    private int version;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "user")
    private List<AuthUserRole> authUserRoles;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "user")
    private List<UserPreference> userPreferenceList;
    
    public AuthUser() {
    }

    public AuthUser(Integer userId) {
        this.userId = userId;
    }

    public AuthUser(Integer userId, String firstName, String lastName, boolean currentEmployee, int version) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.currentEmployee = currentEmployee;
        this.version = version;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public boolean getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(boolean currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

   

    public String getSmsAddress() {
        return smsAddress;
    }

    public void setSmsAddress(String smsAddress) {
        this.smsAddress = smsAddress;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

   
    @XmlTransient
//    @JsonIgnore
    public List<AuthUserRole> getAuthUserRoles() {
        return authUserRoles;
    }

    public void setAuthUserRoleList(List<AuthUserRole> authUserRoles) {
        this.authUserRoles = authUserRoles;
    }

    @XmlTransient
//    @JsonIgnore
    public List<UserPreference> getUserPreferenceList() {
        return userPreferenceList;
    }

    public void setUserPreferenceList(List<UserPreference> userPreferenceList) {
        this.userPreferenceList = userPreferenceList;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthUser)) {
            return false;
        }
        AuthUser other = (AuthUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.hourlog.ent.AuthUser[ userId=" + userId + " ]";
    }
    
}
