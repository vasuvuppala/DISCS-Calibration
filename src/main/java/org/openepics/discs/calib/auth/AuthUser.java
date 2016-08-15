/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.auth;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.openepics.discs.calib.ent.Subscription;
import org.openepics.discs.calib.ent.UserGroup;
import org.openepics.discs.calib.ent.UserRole;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "auth_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthUser.findAll", query = "SELECT s FROM AuthUser s"),
    @NamedQuery(name = "AuthUser.findByUserId", query = "SELECT s FROM AuthUser s WHERE s.userId = :userId"),
    @NamedQuery(name = "AuthUser.countUsers", query = "SELECT COUNT(u) FROM AuthUser u"),
    @NamedQuery(name = "AuthUser.findByUniqueName", query = "SELECT s FROM AuthUser s WHERE s.uniqueName = :name")
    })
public class AuthUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer userId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "unique_name", unique=true)
    private String uniqueName;
    
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 128)
//    @Column(name = "name")
//    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "first_name")
    private String firstName;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 64)
    @Column(name = "email")
    private String email;
    
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "sysuser")
    private List<UserGroup> userGroupList;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "sysuser")
    private List<UserRole> userRoleList;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "sysuser")
    private List<Subscription> subscriptionList;
    
//    @OneToMany(mappedBy = "owner")
//    private List<Device> deviceList;
    
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "sysuser")
//    private List<UserPreference> userPreferenceList;

    public AuthUser() {
    }

    public AuthUser(Integer userId) {
        this.userId = userId;
    }

    public AuthUser(Integer userId, String uniqueName, String name, int version) {
        this.userId = userId;
        this.uniqueName = uniqueName;
        // this.name = name;
        this.version = version;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @XmlTransient
    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    @XmlTransient
    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @XmlTransient
    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

//    @XmlTransient
//    public List<Device> getDeviceList() {
//        return deviceList;
//    }
//
//    public void setDeviceList(List<Device> deviceList) {
//        this.deviceList = deviceList;
//    }

//    @XmlTransient
//    public List<UserPreference> getUserPreferenceList() {
//        return userPreferenceList;
//    }
//
//    public void setUserPreferenceList(List<UserPreference> userPreferenceList) {
//        this.userPreferenceList = userPreferenceList;
//    }

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
        return "org.openepics.discs.calib.ent.AuthUser[ userId=" + userId + " ]";
    }
    
}
