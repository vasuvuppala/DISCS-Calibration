-- 
-- disable a device
--
-- use discs_calib;

delimiter ##

--
-- disable device
--
drop procedure if exists disable_device;
create procedure disable_device(IN serialNumber VARCHAR(128))
begin
     update device set active = false where serial_number = serialNumber;
end
##
delimiter ;

--
--
