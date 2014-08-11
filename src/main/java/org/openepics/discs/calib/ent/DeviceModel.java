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
@Table(name = "device_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeviceModel.findAll", query = "SELECT d FROM DeviceModel d"),
    @NamedQuery(name = "DeviceModel.findByModelId", query = "SELECT d FROM DeviceModel d WHERE d.modelId = :modelId"),
    @NamedQuery(name = "DeviceModel.findByManufacturer", query = "SELECT d FROM DeviceModel d WHERE d.manufacturer = :manufacturer"),
    @NamedQuery(name = "DeviceModel.findByName", query = "SELECT d FROM DeviceModel d WHERE d.name = :name"),
    @NamedQuery(name = "DeviceModel.findByDescription", query = "SELECT d FROM DeviceModel d WHERE d.description = :description"),
    @NamedQuery(name = "DeviceModel.findByManualUri", query = "SELECT d FROM DeviceModel d WHERE d.manualUri = :manualUri"),
    @NamedQuery(name = "DeviceModel.findByManualName", query = "SELECT d FROM DeviceModel d WHERE d.manualName = :manualName"),
    @NamedQuery(name = "DeviceModel.findByCalibrationCycle", query = "SELECT d FROM DeviceModel d WHERE d.calibrationCycle = :calibrationCycle"),
    @NamedQuery(name = "DeviceModel.findByModifiedBy", query = "SELECT d FROM DeviceModel d WHERE d.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "DeviceModel.findByDateModified", query = "SELECT d FROM DeviceModel d WHERE d.dateModified = :dateModified"),
    @NamedQuery(name = "DeviceModel.findByVersion", query = "SELECT d FROM DeviceModel d WHERE d.version = :version")})
public class DeviceModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_id")
    private Integer modelId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "manual_uri")
    private String manualUri;
    @Size(max = 255)
    @Column(name = "manual_name")
    private String manualName;
    @Column(name = "calibration_cycle")
    private Integer calibrationCycle;
    @Size(max = 64)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "date_modified")
    @Temporal(TemporalType.DATE)
    private Date dateModified;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model")
    private List<Device> deviceList;

    public DeviceModel() {
    }

    public DeviceModel(Integer modelId) {
        this.modelId = modelId;
    }

    public DeviceModel(Integer modelId, String manufacturer, String name, int version) {
        this.modelId = modelId;
        this.manufacturer = manufacturer;
        this.name = name;
        this.version = version;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getManualUri() {
        return manualUri;
    }

    public void setManualUri(String manualUri) {
        this.manualUri = manualUri;
    }

    public String getManualName() {
        return manualName;
    }

    public void setManualName(String manualName) {
        this.manualName = manualName;
    }

    public Integer getCalibrationCycle() {
        return calibrationCycle;
    }

    public void setCalibrationCycle(Integer calibrationCycle) {
        this.calibrationCycle = calibrationCycle;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @XmlTransient
    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelId != null ? modelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceModel)) {
            return false;
        }
        DeviceModel other = (DeviceModel) object;
        if ((this.modelId == null && other.modelId != null) || (this.modelId != null && !this.modelId.equals(other.modelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.DeviceModel[ modelId=" + modelId + " ]";
    }
    
}
