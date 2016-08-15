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

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "auth_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthRole.findAll", query = "SELECT a FROM AuthRole a"),
    @NamedQuery(name = "AuthRole.findByName", query = "SELECT a FROM AuthRole a WHERE a.name = :name"),
    @NamedQuery(name = "AuthRole.findByDescription", query = "SELECT a FROM AuthRole a WHERE a.description = :description"),
    @NamedQuery(name = "AuthRole.findByVersion", query = "SELECT a FROM AuthRole a WHERE a.version = :version")})
public class AuthRole implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "role")
    private List<AuthPermission> authPermissions;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "role")
    private List<AuthUserRole> authUserRoles;

    public AuthRole() {
    }

    public AuthRole(Long roleId) {
        this.id = roleId;
    }

    public AuthRole(Long roleId, String roleName, String description, int version) {
        this.id = roleId;
        this.name = roleName;
        this.description = description;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @XmlTransient
//    @JsonIgnore
    public List<AuthPermission> getAuthPermissions() {
        return authPermissions;
    }

    public void setAuthPermissions(List<AuthPermission> authPermissions) {
        this.authPermissions = authPermissions;
    }

    @XmlTransient
//    @JsonIgnore
    public List<AuthUserRole> getAuthUserRoles() {
        return authUserRoles;
    }

    public void setAuthUserRoles(List<AuthUserRole> authUserRoleList) {
        this.authUserRoles = authUserRoleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthRole)) {
            return false;
        }
        AuthRole other = (AuthRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.AuthRole[ id=" + id + " ]";
    }
    
}
