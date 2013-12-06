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
import javax.validation.constraints.Size;

/**
 *
 * @author vuppala
 */
@Embeddable
public class CalibrationMeasurementPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "calibration_record_id")
    private int calibrationRecordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "step")
    private String step;

    public CalibrationMeasurementPK() {
    }

    public CalibrationMeasurementPK(int calibrationRecordId, String step) {
        this.calibrationRecordId = calibrationRecordId;
        this.step = step;
    }

    public int getCalibrationRecordId() {
        return calibrationRecordId;
    }

    public void setCalibrationRecordId(int calibrationRecordId) {
        this.calibrationRecordId = calibrationRecordId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) calibrationRecordId;
        hash += (step != null ? step.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalibrationMeasurementPK)) {
            return false;
        }
        CalibrationMeasurementPK other = (CalibrationMeasurementPK) object;
        if (this.calibrationRecordId != other.calibrationRecordId) {
            return false;
        }
        if ((this.step == null && other.step != null) || (this.step != null && !this.step.equals(other.step))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationMeasurementPK[ calibrationRecordId=" + calibrationRecordId + ", step=" + step + " ]";
    }
    
}
