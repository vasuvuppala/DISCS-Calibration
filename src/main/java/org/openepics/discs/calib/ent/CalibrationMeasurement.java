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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vuppala
 */
@Entity
@Table(name = "calibration_measurement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalibrationMeasurement.findAll", query = "SELECT c FROM CalibrationMeasurement c"),
    @NamedQuery(name = "CalibrationMeasurement.findByCalibrationRecordId", query = "SELECT c FROM CalibrationMeasurement c WHERE c.calibrationMeasurementPK.calibrationRecordId = :calibrationRecordId"),
    @NamedQuery(name = "CalibrationMeasurement.findByStep", query = "SELECT c FROM CalibrationMeasurement c WHERE c.calibrationMeasurementPK.step = :step"),
    @NamedQuery(name = "CalibrationMeasurement.findByVersion", query = "SELECT c FROM CalibrationMeasurement c WHERE c.version = :version")})
public class CalibrationMeasurement implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CalibrationMeasurementPK calibrationMeasurementPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "function_tested")
    private String functionTested;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "nominal_value")
    private String nominalValue;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "measured_value")
    private String measuredValue;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "lower_tolerance")
    private String lowerTolerance;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "upper_tolerance")
    private String upperTolerance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    @JoinColumn(name = "calibration_record_id", referencedColumnName = "calibration_record_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CalibrationRecord calibrationRecord;

    public CalibrationMeasurement() {
    }

    public CalibrationMeasurement(CalibrationMeasurementPK calibrationMeasurementPK) {
        this.calibrationMeasurementPK = calibrationMeasurementPK;
    }

    public CalibrationMeasurement(CalibrationMeasurementPK calibrationMeasurementPK, String functionTested, String nominalValue, String measuredValue, String lowerTolerance, String upperTolerance, int version) {
        this.calibrationMeasurementPK = calibrationMeasurementPK;
        this.functionTested = functionTested;
        this.nominalValue = nominalValue;
        this.measuredValue = measuredValue;
        this.lowerTolerance = lowerTolerance;
        this.upperTolerance = upperTolerance;
        this.version = version;
    }

    public CalibrationMeasurement(int calibrationRecordId, String step) {
        this.calibrationMeasurementPK = new CalibrationMeasurementPK(calibrationRecordId, step);
    }

    public CalibrationMeasurementPK getCalibrationMeasurementPK() {
        return calibrationMeasurementPK;
    }

    public void setCalibrationMeasurementPK(CalibrationMeasurementPK calibrationMeasurementPK) {
        this.calibrationMeasurementPK = calibrationMeasurementPK;
    }

    public String getFunctionTested() {
        return functionTested;
    }

    public void setFunctionTested(String functionTested) {
        this.functionTested = functionTested;
    }

    public String getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(String nominalValue) {
        this.nominalValue = nominalValue;
    }

    public String getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
    }

    public String getLowerTolerance() {
        return lowerTolerance;
    }

    public void setLowerTolerance(String lowerTolerance) {
        this.lowerTolerance = lowerTolerance;
    }

    public String getUpperTolerance() {
        return upperTolerance;
    }

    public void setUpperTolerance(String upperTolerance) {
        this.upperTolerance = upperTolerance;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calibrationMeasurementPK != null ? calibrationMeasurementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalibrationMeasurement)) {
            return false;
        }
        CalibrationMeasurement other = (CalibrationMeasurement) object;
        if ((this.calibrationMeasurementPK == null && other.calibrationMeasurementPK != null) || (this.calibrationMeasurementPK != null && !this.calibrationMeasurementPK.equals(other.calibrationMeasurementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationMeasurement[ calibrationMeasurementPK=" + calibrationMeasurementPK + " ]";
    }
    
}
