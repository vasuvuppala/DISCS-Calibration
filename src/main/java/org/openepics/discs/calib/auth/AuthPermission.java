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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "auth_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthPermission.findAll", query = "SELECT a FROM AuthPermission a"),
    @NamedQuery(name = "AuthPermission.findByVersion", query = "SELECT a FROM AuthPermission a WHERE a.version = :version")})

public class AuthPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    
    @Basic(optional = false)
    @Column(name="resource")
    private AuthResource resource;
    
    @Basic(optional = false)
    @Column(name="auth_operation")
    private AuthOperation authOperation;
    
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private AuthRole role;

    public AuthPermission() {
    }

    public AuthPermission(Long privilegeId) {
        this.id = privilegeId;
    }

    public AuthPermission(Long privilegeId, int version) {
        this.id = privilegeId;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long privilegeId) {
        this.id = privilegeId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public AuthResource getResource() {
        return resource;
    }

    public void setResource(AuthResource resource) {
        this.resource = resource;
    }

    public AuthOperation getOperation() {
        return authOperation;
    }

    public void setOperation(AuthOperation operation) {
        this.authOperation = operation;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
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
        if (!(object instanceof AuthPermission)) {
            return false;
        }
        AuthPermission other = (AuthPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.AuthPermission[ id=" + id + " ]";
    }
    
}
