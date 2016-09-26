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
@Table(name = "calibration_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalibrationRecord.findAll", query = "SELECT c FROM CalibrationRecord c"),
    @NamedQuery(name = "CalibrationRecord.findByCalibrationRecordId", query = "SELECT c FROM CalibrationRecord c WHERE c.calibrationRecordId = :calibrationRecordId"),
    @NamedQuery(name = "CalibrationRecord.findByCalibrationDate", query = "SELECT c FROM CalibrationRecord c WHERE c.calibrationDate = :calibrationDate"),
    @NamedQuery(name = "CalibrationRecord.findByPerformedBy", query = "SELECT c FROM CalibrationRecord c WHERE c.performedBy = :performedBy"),
    @NamedQuery(name = "CalibrationRecord.findByVersion", query = "SELECT c FROM CalibrationRecord c WHERE c.version = :version")})
public class CalibrationRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calibration_record_id")
    private Integer calibrationRecordId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "calibration_date")
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
    private int version = 0;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE} , mappedBy = "calibrationRecord")
    private List<CalibrationDevice> calibrationDeviceList;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE} , mappedBy = "calibrationRecord")
    private List<CalibrationMeasurement> calibrationMeasurementList;
    
    @JoinColumn(name = "device", referencedColumnName = "device_id")
    @ManyToOne(optional = false)
    private Device device;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name = "calib_artifact",
               joinColumns = @JoinColumn(name = "calib_record"),
               inverseJoinColumns = @JoinColumn(name = "artifact") 
    )
    private List<Artifact> artifacts;

    public CalibrationRecord() {
    }

    public CalibrationRecord(Integer calibrationRecordId) {
        this.calibrationRecordId = calibrationRecordId;
    }

    public CalibrationRecord(Integer calibrationRecordId, Date calibrationDate, String performedBy, int version) {
        this.calibrationRecordId = calibrationRecordId;
        this.calibrationDate = calibrationDate;
        this.performedBy = performedBy;
        this.version = version;
    }

    public Integer getCalibrationRecordId() {
        return calibrationRecordId;
    }

    public void setCalibrationRecordId(Integer calibrationRecordId) {
        this.calibrationRecordId = calibrationRecordId;
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

    @XmlTransient
    public List<CalibrationDevice> getCalibrationDeviceList() {
        return calibrationDeviceList;
    }

    public void setCalibrationDeviceList(List<CalibrationDevice> calibrationDeviceList) {
        this.calibrationDeviceList = calibrationDeviceList;
    }

    @XmlTransient
    public List<CalibrationMeasurement> getCalibrationMeasurementList() {
        return calibrationMeasurementList;
    }

    public void setCalibrationMeasurementList(List<CalibrationMeasurement> calibrationMeasurementList) {
        this.calibrationMeasurementList = calibrationMeasurementList;
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
        hash += (calibrationRecordId != null ? calibrationRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalibrationRecord)) {
            return false;
        }
        CalibrationRecord other = (CalibrationRecord) object;
        if ((this.calibrationRecordId == null && other.calibrationRecordId != null) || (this.calibrationRecordId != null && !this.calibrationRecordId.equals(other.calibrationRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationRecord[ calibrationRecordId=" + calibrationRecordId + " ]";
    }
    
}
