/*
 * This software is Copyright by the Board of Trustees of Michigan
 * State University (c) Copyright 2012.
 * 
 * You may use this software under the terms of the GNU public license
 *  (GPL). The terms of this license are described at:
 *       http://www.gnu.org/licenses/gpl.txt
 * 
 * Contact Information:
 *   Facilitty for Rare Isotope Beam
 *   Michigan State University
 *   East Lansing, MI 48824-1321
 *   http://frib.msu.edu
 * 
 */
package org.openepics.discs.calib;

import java.util.List;
import javax.ejb.Local;
import org.openepics.discs.calib.ent.*;

/**
 *
 * @author Vasu V <vuppala@frib.msu.org>
 */
@Local
public interface CalibrationEJBLocal {
     List<Equipment> physicalComponents();
     // List<Equipment> physicalComponents(String sno);
     Equipment physicalComponentById(int id);
     CalibrationRecord calibrationRecordById(int id);
     List<Equipment> standards();
     CalibrationRecord addCalibRecord(CalibrationRecord cr, Equipment[] standards, List<CalibrationMeasurement> measurements); 
     void deleteCalibRecord(Integer crid) throws Exception;
     List<CalibrationRecord> calibrationRecords() ;
     boolean isAuthorized(String user);
}
