-- 
-- add user
--
-- use discs_calib;

delimiter ##

--
-- add user
--
drop procedure if exists add_user;
create procedure add_user(IN uniqueName VARCHAR(64), IN userName VARCHAR(64), 
   IN email VARCHAR(64), IN groupName VARCHAR(32))
begin
    declare uid INT;
	declare roleid INT;
	declare groupid INT;
	
	insert into sysuser(unique_name,name,email,comment)
	  select uniqueName,userName,email,userName;
	
	select u.user_id into uid 
	  from sysuser u where u.unique_name = uniqueName;
	  
	select r.role_id into roleid from role r where r.name =   'GroupAdmin';
	select g.group_id into groupid from device_group g where g.name = groupName;
	
	insert into user_role(sysuser,role, startTime) 
	   select uid, roleid, now();
	
	insert into user_preference(sysuser,pref_name, pref_value)
	  select uid,'DefaultGroup',groupid;
end
##
delimiter ;

--
--
