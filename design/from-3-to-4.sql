--
-- migrate data from v0.3 to v0.4
--

-- all EE devices to be owned by drewyor
update device set owner = 2 where device_group = 1; 
