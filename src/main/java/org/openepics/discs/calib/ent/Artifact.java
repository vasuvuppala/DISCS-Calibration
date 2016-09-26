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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An uploaded file or an external URL
 * 
 * @author vuppala
 */
@Entity
@Table(name = "artifact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artifact.findAll", query = "SELECT a FROM Artifact a"),
    @NamedQuery(name = "Artifact.findById", query = "SELECT a FROM Artifact a WHERE a.id = :id")
    })
public class Artifact implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ext_resource")
    private Boolean externalResource = false; // true if resource is external (a link); false if it is an uploaded file
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    private String name;
    
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2048)
    @Column(name = "resource_id")
    private String resourceId; // URL or BlobStore file id
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version = 0;
    
    public Artifact() {
    }

    public Artifact(Integer id) {
        this.id = id;
    }

    /** Constructs a new artifact
     * @param name
     * @param resource
     * @param description the user specified description
     */
    public Artifact(String name, String description, String resource) {
        this.name = name;
        this.description = description;
        this.resourceId = resource;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getExternalResource() {
        return externalResource;
    }

    public void setExternalResource(Boolean externalResource) {
        this.externalResource = externalResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artifact)) {
            return false;
        }
        Artifact other = (Artifact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "[ " + id + " ]";
    }
}
