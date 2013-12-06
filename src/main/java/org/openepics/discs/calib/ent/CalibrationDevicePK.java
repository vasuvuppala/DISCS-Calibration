/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.ent;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author vuppala
 */
@Embeddable
public class CalibrationDevicePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "calibration_record_id")
    private int calibrationRecordId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "physical_component_id")
    private int physicalComponentId;

    public CalibrationDevicePK() {
    }

    public CalibrationDevicePK(int calibrationRecordId, int physicalComponentId) {
        this.calibrationRecordId = calibrationRecordId;
        this.physicalComponentId = physicalComponentId;
    }

    public int getCalibrationRecordId() {
        return calibrationRecordId;
    }

    public void setCalibrationRecordId(int calibrationRecordId) {
        this.calibrationRecordId = calibrationRecordId;
    }

    public int getPhysicalComponentId() {
        return physicalComponentId;
    }

    public void setPhysicalComponentId(int physicalComponentId) {
        this.physicalComponentId = physicalComponentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) calibrationRecordId;
        hash += (int) physicalComponentId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalibrationDevicePK)) {
            return false;
        }
        CalibrationDevicePK other = (CalibrationDevicePK) object;
        if (this.calibrationRecordId != other.calibrationRecordId) {
            return false;
        }
        if (this.physicalComponentId != other.physicalComponentId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationDevicePK[ calibrationRecordId=" + calibrationRecordId + ", physicalComponentId=" + physicalComponentId + " ]";
    }
    
}
