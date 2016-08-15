/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.ent;

import org.openepics.discs.calib.auth.AuthUser;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
    @NamedQuery(name = "UserRole.findByUserRoleId", query = "SELECT u FROM UserRole u WHERE u.userRoleId = :userRoleId"),
    @NamedQuery(name = "UserRole.findByCanDelegate", query = "SELECT u FROM UserRole u WHERE u.canDelegate = :canDelegate"),
    @NamedQuery(name = "UserRole.findByIsRoleManager", query = "SELECT u FROM UserRole u WHERE u.isRoleManager = :isRoleManager"),
    @NamedQuery(name = "UserRole.findByStartTime", query = "SELECT u FROM UserRole u WHERE u.startTime = :startTime"),
    @NamedQuery(name = "UserRole.findByEndTime", query = "SELECT u FROM UserRole u WHERE u.endTime = :endTime"),
    @NamedQuery(name = "UserRole.findByComment", query = "SELECT u FROM UserRole u WHERE u.comment = :comment"),
    @NamedQuery(name = "UserRole.findByVersion", query = "SELECT u FROM UserRole u WHERE u.version = :version")})
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_role_id")
    private Integer userRoleId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "canDelegate")
    private boolean canDelegate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "isRoleManager")
    private boolean isRoleManager;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "startTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "endTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role role;
    
    @JoinColumn(name = "sysuser", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private AuthUser sysuser;

    public UserRole() {
    }

    public UserRole(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserRole(Integer userRoleId, boolean canDelegate, boolean isRoleManager, Date startTime, Date endTime, int version) {
        this.userRoleId = userRoleId;
        this.canDelegate = canDelegate;
        this.isRoleManager = isRoleManager;
        this.startTime = startTime;
        this.endTime = endTime;
        this.version = version;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public boolean getCanDelegate() {
        return canDelegate;
    }

    public void setCanDelegate(boolean canDelegate) {
        this.canDelegate = canDelegate;
    }

    public boolean getIsRoleManager() {
        return isRoleManager;
    }

    public void setIsRoleManager(boolean isRoleManager) {
        this.isRoleManager = isRoleManager;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AuthUser getSysuser() {
        return sysuser;
    }

    public void setSysuser(AuthUser sysuser) {
        this.sysuser = sysuser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleId != null ? userRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.userRoleId == null && other.userRoleId != null) || (this.userRoleId != null && !this.userRoleId.equals(other.userRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.UserRole[ userRoleId=" + userRoleId + " ]";
    }
    
}
