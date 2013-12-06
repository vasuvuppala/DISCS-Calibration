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
@Table(name = "equipment_model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipmentModel.findAll", query = "SELECT e FROM EquipmentModel e"),
    @NamedQuery(name = "EquipmentModel.findByModelId", query = "SELECT e FROM EquipmentModel e WHERE e.modelId = :modelId"),
    @NamedQuery(name = "EquipmentModel.findByManufacturer", query = "SELECT e FROM EquipmentModel e WHERE e.manufacturer = :manufacturer"),
    @NamedQuery(name = "EquipmentModel.findByModel", query = "SELECT e FROM EquipmentModel e WHERE e.model = :model"),
    @NamedQuery(name = "EquipmentModel.findByDescription", query = "SELECT e FROM EquipmentModel e WHERE e.description = :description"),
    @NamedQuery(name = "EquipmentModel.findByManualUri", query = "SELECT e FROM EquipmentModel e WHERE e.manualUri = :manualUri"),
    @NamedQuery(name = "EquipmentModel.findByManualName", query = "SELECT e FROM EquipmentModel e WHERE e.manualName = :manualName"),
    @NamedQuery(name = "EquipmentModel.findByCalibrationCycle", query = "SELECT e FROM EquipmentModel e WHERE e.calibrationCycle = :calibrationCycle"),
    @NamedQuery(name = "EquipmentModel.findByModifiedBy", query = "SELECT e FROM EquipmentModel e WHERE e.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "EquipmentModel.findByDateModified", query = "SELECT e FROM EquipmentModel e WHERE e.dateModified = :dateModified"),
    @NamedQuery(name = "EquipmentModel.findByVersion", query = "SELECT e FROM EquipmentModel e WHERE e.version = :version")})
public class EquipmentModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @Column(name = "model")
    private String model;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentModel")
    private List<Equipment> equipmentList;

    public EquipmentModel() {
    }

    public EquipmentModel(Integer modelId) {
        this.modelId = modelId;
    }

    public EquipmentModel(Integer modelId, String manufacturer, String model, int version) {
        this.modelId = modelId;
        this.manufacturer = manufacturer;
        this.model = model;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
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
        if (!(object instanceof EquipmentModel)) {
            return false;
        }
        EquipmentModel other = (EquipmentModel) object;
        if ((this.modelId == null && other.modelId != null) || (this.modelId != null && !this.modelId.equals(other.modelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.EquipmentModel[ modelId=" + modelId + " ]";
    }
    
}
