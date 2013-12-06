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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "equipment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e"),
    @NamedQuery(name = "Equipment.findByPhysicalComponentId", query = "SELECT e FROM Equipment e WHERE e.physicalComponentId = :physicalComponentId"),
    @NamedQuery(name = "Equipment.findBySerialNumber", query = "SELECT e FROM Equipment e WHERE e.serialNumber = :serialNumber"),
    @NamedQuery(name = "Equipment.findByManufacturer", query = "SELECT e FROM Equipment e WHERE e.manufacturer = :manufacturer"),
    @NamedQuery(name = "Equipment.findByManufModel", query = "SELECT e FROM Equipment e WHERE e.manufModel = :manufModel"),
    @NamedQuery(name = "Equipment.findByManufSerialNumber", query = "SELECT e FROM Equipment e WHERE e.manufSerialNumber = :manufSerialNumber"),
    @NamedQuery(name = "Equipment.findByDescription", query = "SELECT e FROM Equipment e WHERE e.description = :description"),
    @NamedQuery(name = "Equipment.findByLocation", query = "SELECT e FROM Equipment e WHERE e.location = :location"),
    @NamedQuery(name = "Equipment.findByCalibStandard", query = "SELECT e FROM Equipment e WHERE e.calibStandard = :calibStandard"),
    @NamedQuery(name = "Equipment.findByDateLastServiced", query = "SELECT e FROM Equipment e WHERE e.dateLastServiced = :dateLastServiced"),
    @NamedQuery(name = "Equipment.findByCustodian", query = "SELECT e FROM Equipment e WHERE e.custodian = :custodian"),
    @NamedQuery(name = "Equipment.findByPurchaseOrder", query = "SELECT e FROM Equipment e WHERE e.purchaseOrder = :purchaseOrder"),
    @NamedQuery(name = "Equipment.findByCalibCycle", query = "SELECT e FROM Equipment e WHERE e.calibCycle = :calibCycle"),
    @NamedQuery(name = "Equipment.findByDateModified", query = "SELECT e FROM Equipment e WHERE e.dateModified = :dateModified"),
    @NamedQuery(name = "Equipment.findByActive", query = "SELECT e FROM Equipment e WHERE e.active = :active"),
    @NamedQuery(name = "Equipment.findByModifiedBy", query = "SELECT e FROM Equipment e WHERE e.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Equipment.findByVersion", query = "SELECT e FROM Equipment e WHERE e.version = :version")})
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "physical_component_id")
    private Integer physicalComponentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "serial_number")
    private String serialNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "manuf_model")
    private String manufModel;
    @Size(max = 64)
    @Column(name = "manuf_serial_number")
    private String manufSerialNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 64)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calib_standard")
    private boolean calibStandard;
    @Column(name = "date_last_serviced")
    @Temporal(TemporalType.DATE)
    private Date dateLastServiced;
    @Size(max = 32)
    @Column(name = "custodian")
    private String custodian;
    @Size(max = 64)
    @Column(name = "purchase_order")
    private String purchaseOrder;
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
    @OneToMany(mappedBy = "equipment")
    private List<CalibrationRecord> calibrationRecordList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipment")
    private List<CalibrationDevice> calibrationDeviceList;
    @JoinColumn(name = "model_id", referencedColumnName = "model_id")
    @ManyToOne(optional = false)
    private EquipmentModel equipmentModel;

    public Equipment() {
    }

    public Equipment(Integer physicalComponentId) {
        this.physicalComponentId = physicalComponentId;
    }

    public Equipment(Integer physicalComponentId, String serialNumber, String manufacturer, String manufModel, String description, boolean calibStandard, int version) {
        this.physicalComponentId = physicalComponentId;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.manufModel = manufModel;
        this.description = description;
        this.calibStandard = calibStandard;
        this.version = version;
    }

    public Integer getPhysicalComponentId() {
        return physicalComponentId;
    }

    public void setPhysicalComponentId(Integer physicalComponentId) {
        this.physicalComponentId = physicalComponentId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufModel() {
        return manufModel;
    }

    public void setManufModel(String manufModel) {
        this.manufModel = manufModel;
    }

    public String getManufSerialNumber() {
        return manufSerialNumber;
    }

    public void setManufSerialNumber(String manufSerialNumber) {
        this.manufSerialNumber = manufSerialNumber;
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

    public boolean getCalibStandard() {
        return calibStandard;
    }

    public void setCalibStandard(boolean calibStandard) {
        this.calibStandard = calibStandard;
    }

    public Date getDateLastServiced() {
        return dateLastServiced;
    }

    public void setDateLastServiced(Date dateLastServiced) {
        this.dateLastServiced = dateLastServiced;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
    public List<CalibrationRecord> getCalibrationRecordList() {
        return calibrationRecordList;
    }

    public void setCalibrationRecordList(List<CalibrationRecord> calibrationRecordList) {
        this.calibrationRecordList = calibrationRecordList;
    }

    @XmlTransient
    public List<CalibrationDevice> getCalibrationDeviceList() {
        return calibrationDeviceList;
    }

    public void setCalibrationDeviceList(List<CalibrationDevice> calibrationDeviceList) {
        this.calibrationDeviceList = calibrationDeviceList;
    }

    public EquipmentModel getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(EquipmentModel equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (physicalComponentId != null ? physicalComponentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        if ((this.physicalComponentId == null && other.physicalComponentId != null) || (this.physicalComponentId != null && !this.physicalComponentId.equals(other.physicalComponentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.Equipment[ physicalComponentId=" + physicalComponentId + " ]";
    }
    
}
