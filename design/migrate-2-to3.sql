--
--

set FOREIGN_KEY_CHECKS=0;
truncate device_group;
truncate device_model;
truncate device;
truncate calibration_record;
truncate calibration_device;
truncate calibration_measurement;
truncate sysuser;
truncate user_role;
truncate role;
truncate user_preference;
truncate audit_record;
set FOREIGN_KEY_CHECKS=0;

insert into device_group(group_id,name,description) values 
   (1,'EE','Electrical Engineering'), 
   (2,'IG', 'Inspection Group');
insert into role(name,description) values 
   ('GroupAdmin','Group Administrator'), 
   ('Admin', 'Overall Administrator');
insert into sysuser(unique_name,name,email) values 
   ('vuppala','Vasu Vuppala','vuppala@frib.msu.edu'), 
   ('drewyor','Brian Drewyor','drewyor@frib.msu.edu'), 
   ('goodrich','Michael Goodrich','Goodrich@frib.msu.edu'); 
 insert into user_role(sysuser,role,canDelegate,isRoleManager) values
   (1,2,false,false), (2,1,false,false), (3,1,false,false);
   
insert into user_preference(sysuser,pref_name,pref_value)    
   select user_id, 'DefaultGroup', '1' from sysuser where unique_name = 'drewyor';
insert into user_preference(sysuser,pref_name,pref_value)    
   select user_id, 'DefaultGroup', '2' from sysuser where unique_name = 'goodrich';
 insert into user_preference(sysuser,pref_name,pref_value)    
   select user_id, 'DefaultGroup', '1' from sysuser where unique_name = 'vuppala';  
   
 insert into device_model 
    select * from discs_calib_old.equipment_model;
 
 insert into device(device_id, serial_number, model, device_group,description, location,calib_standard,custodian, calib_cycle, date_modified, active,modified_by)  
   select physical_component_id, serial_number, model_id,1,description, location,calib_standard,custodian,calib_cycle,date_modified, active,modified_by 
   from discs_calib_old.equipment;
   
 insert into calibration_record(calibration_record_id,device,calibration_date,performed_by,notes) 
   select calibration_record_id, physical_component_id, calibration_date, performed_by, notes from discs_calib_old.calibration_record;

 insert into calibration_device(calibration_record,device)  
   select calibration_record_id, physical_component_id from discs_calib_old.calibration_device;

 insert into calibration_measurement(calibration_record,step,function_tested,nominal_value,measured_value, lower_tolerance, upper_tolerance)  
    select calibration_record_id, step, function_tested, nominal_value, measured_value, lower_tolerance, upper_tolerance from  discs_calib_old.calibration_measurement;