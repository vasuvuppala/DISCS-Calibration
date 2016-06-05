/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.ent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "maint_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaintenanceRecord.findAll", query = "SELECT c FROM MaintenanceRecord c"),
    @NamedQuery(name = "MaintenanceRecord.findById", query = "SELECT c FROM MaintenanceRecord c WHERE c.id = :id")
})
public class MaintenanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "maint_date")
    @Temporal(TemporalType.DATE)
    private Date calibrationDate;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "performed_by")
    private String performedBy;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "notes")
    private String notes;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;  
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "device")
    private Device device;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "maint_artifact",
               joinColumns = @JoinColumn(name = "maint_record"),
               inverseJoinColumns = @JoinColumn(name = "artifact") 
    )
    private List<Artifact> artifacts;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(Integer id, Date calibrationDate, String performedBy, int version) {
        this.id = id;
        this.calibrationDate = calibrationDate;
        this.performedBy = performedBy;
        this.version = version;
    }

    public Integer getCalibrationRecordId() {
        return id;
    }

    public void setCalibrationRecordId(Integer calibrationRecordId) {
        this.id = calibrationRecordId;
    }

    public Date getCalibrationDate() {
        return calibrationDate;
    }

    public void setCalibrationDate(Date calibrationDate) {
        this.calibrationDate = calibrationDate;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @XmlTransient
    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifactList(List<Artifact> artifactList) {
        this.artifacts = artifactList;
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
        if (!(object instanceof MaintenanceRecord)) {
            return false;
        }
        MaintenanceRecord other = (MaintenanceRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationRecord[ calibrationRecordId=" + id + " ]";
    }
    
}
