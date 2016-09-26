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
@Table(name = "device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d"),
    @NamedQuery(name = "Device.findByDeviceId", query = "SELECT d FROM Device d WHERE d.deviceId = :deviceId"),
    @NamedQuery(name = "Device.findBySerialNumber", query = "SELECT d FROM Device d WHERE d.serialNumber = :serialNumber"),
    @NamedQuery(name = "Device.findByDescription", query = "SELECT d FROM Device d WHERE d.description = :description"),
    @NamedQuery(name = "Device.findByLocation", query = "SELECT d FROM Device d WHERE d.location = :location"),
    @NamedQuery(name = "Device.findByCalibStandard", query = "SELECT d FROM Device d WHERE d.calibStandard = :calibStandard"),
    @NamedQuery(name = "Device.findByCustodian", query = "SELECT d FROM Device d WHERE d.custodian = :custodian"),
    @NamedQuery(name = "Device.findByCalibCycle", query = "SELECT d FROM Device d WHERE d.calibCycle = :calibCycle"),
    @NamedQuery(name = "Device.findByDateModified", query = "SELECT d FROM Device d WHERE d.dateModified = :dateModified"),
    @NamedQuery(name = "Device.findByActive", query = "SELECT d FROM Device d WHERE d.active = :active"),
    @NamedQuery(name = "Device.findByModifiedBy", query = "SELECT d FROM Device d WHERE d.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Device.findByVersion", query = "SELECT d FROM Device d WHERE d.version = :version")})
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id")
    private Integer deviceId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "serial_number")
    private String serialNumber;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    
    @Size(max = 64)
    @Column(name = "location")
    private String location;
    
    @Column(name = "calib_standard")
    private Boolean calibStandard;
    
    @Size(max = 32)
    @Column(name = "custodian")
    private String custodian;
    
    @Column(name = "calib_cycle")
    private Integer calibCycle;
    
    @Column(name = "date_modified")
    @Temporal(TemporalType.DATE)
    private Date dateModified;
    
    @Column(name = "active")
    private Boolean active;
    
    @Size(max = 64)
    @Column(name = "modified_by")
    private String modifiedBy;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "device")
    private List<CalibrationDevice> calibrationDeviceList;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "device")
    private List<CalibrationRecord> calibrationRecordList;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , mappedBy = "device")
    private List<MaintenanceRecord> maintRecordList;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name = "device_artifact",
               joinColumns = @JoinColumn(name = "device"),
               inverseJoinColumns = @JoinColumn(name = "artifact") 
    )
    private List<Artifact> artifacts;
    
//    @JoinColumn(name = "owner", referencedColumnName = "user_id")
//    @ManyToOne
//    private Sysuser owner;
    
    @JoinColumn(name = "device_group", referencedColumnName = "group_id")
    @ManyToOne(optional = false)
    private DeviceGroup deviceGroup;
    
    @JoinColumn(name = "model", referencedColumnName = "model_id")
    @ManyToOne(optional = false)
    private DeviceModel model;

    public Device() {
    }

    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Device(Integer deviceId, String serialNumber, String description, int version) {
        this.deviceId = deviceId;
        this.serialNumber = serialNumber;
        this.description = description;
        this.version = version;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getCalibStandard() {
        return calibStandard;
    }

    public void setCalibStandard(Boolean calibStandard) {
        this.calibStandard = calibStandard;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public Integer getCalibCycle() {
        return calibCycle;
    }

    public void setCalibCycle(Integer calibCycle) {
        this.calibCycle = calibCycle;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
    public List<CalibrationRecord> getCalibrationRecordList() {
        return calibrationRecordList;
    }

    public void setCalibrationRecordList(List<CalibrationRecord> calibrationRecordList) {
        this.calibrationRecordList = calibrationRecordList;
    }

//    public Sysuser getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Sysuser owner) {
//        this.owner = owner;
//    }

    public DeviceGroup getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(DeviceGroup deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public DeviceModel getModel() {
        return model;
    }

    public void setModel(DeviceModel model) {
        this.model = model;
    }

    public List<MaintenanceRecord> getMaintRecordList() {
        return maintRecordList;
    }

    public void setMaintRecordList(List<MaintenanceRecord> maintRecordList) {
        this.maintRecordList = maintRecordList;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.Device[ deviceId=" + deviceId + " ]";
    }
    
}
