/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.ent;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "calibration_device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalibrationDevice.findAll", query = "SELECT c FROM CalibrationDevice c"),
    @NamedQuery(name = "CalibrationDevice.findByCalibrationRecordId", query = "SELECT c FROM CalibrationDevice c WHERE c.calibrationDevicePK.calibrationRecordId = :calibrationRecordId"),
    @NamedQuery(name = "CalibrationDevice.findByPhysicalComponentId", query = "SELECT c FROM CalibrationDevice c WHERE c.calibrationDevicePK.physicalComponentId = :physicalComponentId"),
    @NamedQuery(name = "CalibrationDevice.findByVersion", query = "SELECT c FROM CalibrationDevice c WHERE c.version = :version")})
public class CalibrationDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CalibrationDevicePK calibrationDevicePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    @JoinColumn(name = "calibration_record_id", referencedColumnName = "calibration_record_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CalibrationRecord calibrationRecord;
    @JoinColumn(name = "physical_component_id", referencedColumnName = "physical_component_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Equipment equipment;

    public CalibrationDevice() {
    }

    public CalibrationDevice(CalibrationDevicePK calibrationDevicePK) {
        this.calibrationDevicePK = calibrationDevicePK;
    }

    public CalibrationDevice(CalibrationDevicePK calibrationDevicePK, int version) {
        this.calibrationDevicePK = calibrationDevicePK;
        this.version = version;
    }

    public CalibrationDevice(int calibrationRecordId, int physicalComponentId) {
        this.calibrationDevicePK = new CalibrationDevicePK(calibrationRecordId, physicalComponentId);
    }

    public CalibrationDevicePK getCalibrationDevicePK() {
        return calibrationDevicePK;
    }

    public void setCalibrationDevicePK(CalibrationDevicePK calibrationDevicePK) {
        this.calibrationDevicePK = calibrationDevicePK;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CalibrationRecord getCalibrationRecord() {
        return calibrationRecord;
    }

    public void setCalibrationRecord(CalibrationRecord calibrationRecord) {
        this.calibrationRecord = calibrationRecord;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calibrationDevicePK != null ? calibrationDevicePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalibrationDevice)) {
            return false;
        }
        CalibrationDevice other = (CalibrationDevice) object;
        if ((this.calibrationDevicePK == null && other.calibrationDevicePK != null) || (this.calibrationDevicePK != null && !this.calibrationDevicePK.equals(other.calibrationDevicePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationDevice[ calibrationDevicePK=" + calibrationDevicePK + " ]";
    }
    
}
