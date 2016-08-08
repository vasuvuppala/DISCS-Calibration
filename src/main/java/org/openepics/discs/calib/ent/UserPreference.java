/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.ent;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.openepics.discs.calib.auth.AuthUser;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "user_preference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPreference.findAll", query = "SELECT u FROM UserPreference u"),
    @NamedQuery(name = "UserPreference.findById", query = "SELECT u FROM UserPreference u WHERE u.id = :id"),
    @NamedQuery(name = "UserPreference.findByPrefName", query = "SELECT u FROM UserPreference u WHERE u.prefName = :prefName"),
    @NamedQuery(name = "UserPreference.findByPrefValue", query = "SELECT u FROM UserPreference u WHERE u.prefValue = :prefValue")})
public class UserPreference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "pref_name")
    private String prefName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "pref_value")
    private String prefValue;
    
    @JoinColumn(name = "auth_user", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private AuthUser authUser;

    public UserPreference() {
    }

    public UserPreference(Integer id) {
        this.id = id;
    }

    public UserPreference(Integer id, String prefName, String prefValue) {
        this.id = id;
        this.prefName = prefName;
        this.prefValue = prefValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrefName() {
        return prefName;
    }

    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }

    public String getPrefValue() {
        return prefValue;
    }

    public void setPrefValue(String prefValue) {
        this.prefValue = prefValue;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser sysuser) {
        this.authUser = sysuser;
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
        if (!(object instanceof UserPreference)) {
            return false;
        }
        UserPreference other = (UserPreference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.UserPreference[ id=" + id + " ]";
    }
    
}
