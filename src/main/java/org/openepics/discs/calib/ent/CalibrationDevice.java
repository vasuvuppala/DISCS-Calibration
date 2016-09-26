/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openepics.discs.calib.ent;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "CalibrationDevice.findById", query = "SELECT c FROM CalibrationDevice c WHERE c.id = :id"),
    @NamedQuery(name = "CalibrationDevice.findByVersion", query = "SELECT c FROM CalibrationDevice c WHERE c.version = :version")})
public class CalibrationDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version = 0;
    
    @JoinColumn(name = "calibration_record", referencedColumnName = "calibration_record_id")
    @ManyToOne(optional = false)
    private CalibrationRecord calibrationRecord;
    
    @JoinColumn(name = "device", referencedColumnName = "device_id")
    @ManyToOne(optional = false)
    private Device device;

    public CalibrationDevice() {
    }

    public CalibrationDevice(Integer id) {
        this.id = id;
    }

    public CalibrationDevice(Integer id, int version) {
        this.id = id;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
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
        if (!(object instanceof CalibrationDevice)) {
            return false;
        }
        CalibrationDevice other = (CalibrationDevice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.openepics.discs.calib.ent.CalibrationDevice[ id=" + id + " ]";
    }
    
}
