-- MySQL dump 10.13  Distrib 5.1.66, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: discs_calib
-- ------------------------------------------------------
-- Server version	5.1.66-0+squeeze1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit_record`
--

DROP TABLE IF EXISTS `audit_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_record` (
  `audit_record_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `log_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `oper` varchar(16) NOT NULL COMMENT 'c-created,d-deleted,u-updated,l-login,o-logout',
  `user` varchar(64) NOT NULL,
  `entity_type` varchar(32) DEFAULT NULL COMMENT 'Slot, device, or component type. ',
  `entity_key` varchar(64) DEFAULT NULL COMMENT ' key of the entity as string',
  `entry` text NOT NULL COMMENT 'notes',
  PRIMARY KEY (`audit_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Each row is an audit record';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_record`
--

LOCK TABLES `audit_record` WRITE;
/*!40000 ALTER TABLE `audit_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calibration_device`
--

DROP TABLE IF EXISTS `calibration_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calibration_device` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `calibration_record` int(10) unsigned NOT NULL,
  `device` int(10) unsigned NOT NULL COMMENT 'equipment id',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT 'For concurrency control',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_caibration_record_device` (`calibration_record`,`device`),
  KEY `calibration_record` (`calibration_record`),
  KEY `device` (`device`),
  CONSTRAINT `FK_calibration_device_physical_component` FOREIGN KEY (`device`) REFERENCES `device` (`device_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_calibration_device_calibration_record` FOREIGN KEY (`calibration_record`) REFERENCES `calibration_record` (`calibration_record_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=latin1 COMMENT='Each row is a device used in a calibration';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calibration_device`
--

LOCK TABLES `calibration_device` WRITE;
/*!40000 ALTER TABLE `calibration_device` DISABLE KEYS */;
INSERT INTO `calibration_device` VALUES (1,2,27,0),(2,2,33,0),(3,2,35,0),(4,3,27,0),(5,3,33,0),(6,3,35,0),(7,4,27,0),(8,4,33,0),(9,4,35,0),(10,5,27,0),(11,5,33,0),(12,5,35,0),(13,8,27,0),(14,8,33,0),(15,9,27,0),(16,9,33,0),(17,10,27,0),(18,10,33,0),(19,10,35,0),(20,11,27,0),(21,11,33,0),(22,11,35,0),(23,12,27,0),(24,12,33,0),(25,12,35,0),(26,13,27,0),(27,13,33,0),(28,13,35,0),(29,14,27,0),(30,14,33,0),(31,14,35,0),(32,15,27,0),(33,15,33,0),(34,15,35,0),(35,16,27,0),(36,16,33,0),(37,16,35,0),(38,17,27,0),(39,17,33,0),(40,17,35,0),(41,18,27,0),(42,18,33,0),(43,18,35,0),(44,20,27,0),(45,20,33,0),(46,20,35,0),(47,21,27,0),(48,21,33,0),(49,21,35,0),(50,22,27,0),(51,22,33,0),(52,22,35,0),(53,23,27,0),(54,23,33,0),(55,23,35,0),(56,24,27,0),(57,24,33,0),(58,24,35,0),(59,25,27,0),(60,25,33,0),(61,25,35,0),(62,26,27,0),(63,26,33,0),(64,26,35,0),(65,27,27,0),(66,27,33,0),(67,28,27,0),(68,28,33,0),(69,28,35,0),(70,29,27,0),(71,29,33,0),(72,29,35,0),(73,31,27,0),(74,31,33,0),(75,31,34,0),(76,31,35,0),(77,32,27,0),(78,32,33,0),(79,32,34,0),(80,32,35,0),(81,33,27,0),(82,33,33,0),(83,33,35,0),(84,34,27,0),(85,34,33,0),(86,34,35,0),(87,35,27,0),(88,35,33,0),(89,35,35,0),(90,36,27,0),(91,36,33,0),(92,36,35,0),(93,37,27,0),(94,37,33,0),(95,37,35,0),(96,38,27,0),(97,38,33,0),(98,38,35,0),(99,39,27,0),(100,39,33,0),(101,39,35,0),(102,41,27,0),(103,41,33,0),(104,41,34,0),(105,41,35,0),(106,42,27,0),(107,42,33,0),(108,42,34,0),(109,42,35,0),(110,43,27,0),(111,43,33,0),(112,43,34,0),(113,43,35,0),(114,44,27,0),(115,44,33,0),(116,44,34,0),(117,44,35,0),(118,45,27,0),(119,45,33,0),(120,45,34,0),(121,45,35,0),(122,46,27,0),(123,46,33,0),(124,46,35,0),(125,47,33,0),(126,48,33,0),(127,49,27,0),(128,49,33,0),(129,49,35,0),(130,53,27,0),(131,53,33,0),(132,53,35,0),(133,55,27,0),(134,55,33,0),(135,55,35,0),(136,56,27,0),(137,56,33,0),(138,56,35,0),(139,58,27,0),(140,58,33,0),(141,58,35,0);
/*!40000 ALTER TABLE `calibration_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calibration_measurement`
--

DROP TABLE IF EXISTS `calibration_measurement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calibration_measurement` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `calibration_record` int(10) unsigned NOT NULL COMMENT 'record identifier',
  `step` varchar(32) NOT NULL COMMENT 'calibration step',
  `function_tested` text NOT NULL COMMENT 'tested function',
  `nominal_value` text NOT NULL COMMENT 'nominal value',
  `measured_value` text NOT NULL COMMENT 'measured value',
  `lower_tolerance` text NOT NULL COMMENT 'lower tolerance',
  `upper_tolerance` text NOT NULL COMMENT 'upper tolerance',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT 'For concurrency control',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_calib_step` (`calibration_record`,`step`),
  KEY `calibration_record` (`calibration_record`),
  CONSTRAINT `FK_calibration_measuremnt_calibration_record` FOREIGN KEY (`calibration_record`) REFERENCES `calibration_record` (`calibration_record_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COMMENT='Each row is a measurement in a calibration';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calibration_measurement`
--

LOCK TABLES `calibration_measurement` WRITE;
/*!40000 ALTER TABLE `calibration_measurement` DISABLE KEYS */;
INSERT INTO `calibration_measurement` VALUES (1,5,'36','500k Ohms','500.000','499.410','499.730','500.270',0),(2,10,'ACV','10V @ 10Hz','10.000000','10.029150','9.991000','10.009000',0),(3,17,'21','DCmV','0','0.028','-0.02','0.02',0),(4,17,'9','ACmV','500','532.4','474.6','525.4',0),(5,20,'2','ACV 20kHz','3.500','3.507','3.498','3.502',0),(6,20,'4','ACV 10kHz','3.500','3.511','3.498','3.502',0),(7,28,'Gain Verification','100 Ohms','100.000000','100.029000','100.014000','99.986000',0),(8,37,'23','Ohms','330','343.9','329.1','330.9',0),(9,37,'24','Ohms','3.300k','3.314k','3.292k','3.309k',0);
/*!40000 ALTER TABLE `calibration_measurement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calibration_record`
--

DROP TABLE IF EXISTS `calibration_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calibration_record` (
  `calibration_record_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `device` int(10) unsigned NOT NULL COMMENT 'equipment id',
  `calibration_date` date NOT NULL COMMENT 'date of calibration',
  `performed_by` varchar(64) NOT NULL COMMENT 'who did it',
  `notes` text COMMENT 'calibration notes',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT 'For optimistic concurrency control',
  PRIMARY KEY (`calibration_record_id`),
  KEY `device` (`device`),
  CONSTRAINT `FK_calibration_record_physical_component` FOREIGN KEY (`device`) REFERENCES `device` (`device_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1 COMMENT='Each row represents a calibration';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calibration_record`
--

LOCK TABLES `calibration_record` WRITE;
/*!40000 ALTER TABLE `calibration_record` DISABLE KEYS */;
INSERT INTO `calibration_record` VALUES (2,8,'2013-11-12','drewyor','',0),(3,4,'2013-08-13','drewyor','',0),(4,5,'2013-11-13','drewyor','',0),(5,7,'2013-11-14','drewyor','',0),(8,43,'2013-12-04','drewyor','',0),(9,42,'2013-12-04','drewyor','',0),(10,22,'2013-11-14','drewyor','',0),(11,24,'2013-12-09','drewyor','',0),(12,45,'2013-11-21','drewyor','',0),(13,85,'2013-11-26','drewyor','',0),(14,86,'2013-11-26','drewyor','',0),(15,83,'2013-11-26','drewyor','',0),(16,81,'2013-12-10','drewyor','',0),(17,94,'2013-11-21','drewyor','',0),(18,95,'2013-11-21','drewyor','',0),(20,74,'2013-12-10','drewyor','',0),(21,77,'2013-12-10','drewyor','',0),(22,82,'2013-11-15','drewyor','',0),(23,89,'2013-11-22','drewyor','',0),(24,78,'2013-11-25','drewyor','',0),(25,37,'2013-12-10','drewyor','',0),(26,39,'2013-12-04','drewyor','',0),(27,47,'2013-12-09','drewyor','',0),(28,23,'2013-11-26','drewyor','',0),(29,21,'2013-12-09','drewyor','',0),(31,108,'2013-11-15','drewyor','',0),(32,109,'2013-11-15','drewyor','',0),(33,11,'2013-11-20','drewyor','',0),(34,44,'2013-11-11','drewyor','',0),(35,46,'2013-11-12','drewyor','',0),(36,75,'2013-11-20','drewyor','',0),(37,88,'2013-11-12','drewyor','',0),(38,87,'2013-11-12','drewyor','',0),(39,80,'2013-11-12','drewyor','',0),(41,18,'2013-11-11','drewyor','Note:  Unit Calibrated to only 550A DC/AC Only.  Frequency response Calibrated  at 50-60 Hz Only.  5500A Unable to drive coil at required frequency for tests.',0),(42,19,'2013-11-25','drewyor','Note:  Unit Calibrated to only 550A DC/AC Only.  Frequency response Calibrated  at 50-60 Hz Only.  5500A Unable to drive coil at required frequency for tests.',0),(43,16,'2013-11-13','drewyor','Note:  Unit Calibrated to only 550A DC/AC Only.  Frequency response Calibrated  at 50-60 Hz Only.  5500A Unable to drive coil at required frequency for tests.',0),(44,17,'2013-11-14','drewyor','Note:  Unit Calibrated to only 550A DC/AC Only.  Frequency response Calibrated  at 50-60 Hz Only.  5500A Unable to drive coil at required frequency for tests.',0),(45,20,'2013-11-25','drewyor','Note:  Unit Calibrated to only 550A DC/AC Only.  Frequency response Calibrated  at 50-60 Hz Only.  5500A Unable to drive coil at required frequency for tests.',0),(46,40,'2013-12-05','drewyor','',0),(47,50,'2013-12-11','drewyor','',0),(48,49,'2013-12-11','drewyor','',0),(49,76,'2013-12-12','drewyor','',0),(50,33,'2013-06-19','drewyor','Calibrated by Fluke Corporation',0),(51,35,'2013-06-24','drewyor','Calibrated by Fluke Corporation',0),(52,27,'2013-06-20','drewyor','Calibrated by Agilent',0),(53,25,'2013-12-12','drewyor','',0),(54,73,'2013-12-12','drewyor','This item has been damaged, and is inoperable.\r\nDelete from Inventory',0),(55,41,'2013-12-13','drewyor','',0),(56,38,'2013-12-13','drewyor','',0),(57,36,'2013-12-16','drewyor','Calibrated at Manufacturer',0),(58,79,'2013-12-11','drewyor','',0),(59,143,'2014-05-19','drewyor','No Adustments',0),(60,144,'2014-05-19','drewyor','Calibrated to 550A only',0),(61,27,'2014-06-12','drewyor','Calibrated at Agilent Technologies, Loveland Co.\r\nCalibration Report Number: 62203',0),(62,33,'2014-06-17','drewyor','In Tolerance',0),(68,35,'2014-06-17','drewyor','Calibrated by Fluke Corp\r\nOut of Tolerance',0);
/*!40000 ALTER TABLE `calibration_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `device_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(64) NOT NULL COMMENT 'serial number',
  `model` int(10) unsigned NOT NULL COMMENT 'model identifier',
  `device_group` int(10) unsigned NOT NULL,
  `description` varchar(255) NOT NULL,
  `location` varchar(64) DEFAULT NULL COMMENT 'location, if component is not installed',
  `calib_standard` tinyint(1) DEFAULT '0' COMMENT 'Is it a standard for calibration',
  `custodian` varchar(32) DEFAULT NULL COMMENT 'Custodian of the equipment',
  `calib_cycle` int(11) DEFAULT NULL COMMENT 'calibration cycle in days',
  `date_modified` date DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1' COMMENT 'is the equipment in active use',
  `modified_by` varchar(64) DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'for concurrency control',
  PRIMARY KEY (`device_id`),
  UNIQUE KEY `UQ_physical_component_serial_number` (`serial_number`),
  KEY `device_group` (`device_group`),
  KEY `model` (`model`),
  CONSTRAINT `FK_equipment_equipment_model` FOREIGN KEY (`model`) REFERENCES `device_model` (`model_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_device_department` FOREIGN KEY (`device_group`) REFERENCES `device_group` (`group_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=latin1 COMMENT='Each row represents a calibration equipment.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (1,'86850649',2,1,'True RMS Multimeter','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(2,'MY50370260',3,1,'Logic Analyzer','1300J',0,'Nathan Usher',0,'2013-12-05',1,'system',0),(3,' 176200574409020000',4,1,'Triple Output DC Power Supply','1300E',0,'Brian Drewyor',0,'2013-12-05',1,'system',0),(4,'79410048',5,1,'True RMS Multimeter','1300Q',0,'RF Engineers Pool',12,'2013-12-05',1,'system',0),(5,'10440102',5,1,'True RMS Multimeter','1313D',0,'Jeffrey Casteel',12,'2013-12-05',1,'system',0),(6,'21029',6,1,'RF Probe','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(7,'89130232',7,1,'True RMS Multimeter','1300G',0,'Kent Holland',12,'2013-12-05',1,'system',0),(8,'1068378',8,1,'Multimeter','1300E',0,'Brian Drewyor',12,'2013-12-05',1,'system',0),(9,'1068362',8,1,'Multimeter','1300-8',0,'Charisse Supangco',0,'2013-12-05',1,'system',0),(10,'12520101',9,1,'Remote Display Digital Multimeter','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(11,'47010287',10,1,'Digital Multimeter','1344',0,'Kurt Kranz',12,'2013-12-05',1,'system',0),(12,'B029986',11,1,'300MHZ 4 Channel Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(13,'D101027WS',12,1,'Dual Output DC Power Supplies','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(14,'MY44043999',13,1,'Arbitrary Waveform Generator','1300J',0,'Nathan Usher',0,'2013-12-05',1,'system',0),(15,'MY44012252',14,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(16,'10370984',15,1,'True RMS Clamp Meter','1313D',0,'Jeffrey Casteel',12,'2013-12-05',1,'system',0),(17,'99360234',15,1,'True RMS Clamp Meter','1300G',0,'Kent Holland',12,'2013-12-05',1,'system',0),(18,'99360235',15,1,'True RMS Clamp Meter','1300-2',0,'Patrick Shinaver',12,'2013-12-05',1,'system',0),(19,'99360224',15,1,'True RMS Clamp Meter','1344',0,'Kurt Kranz',12,'2013-12-05',1,'system',0),(20,'99360222',15,1,'True RMS Clamp Meter','1300Q',0,'Michael Holcomb',12,'2013-12-05',1,'system',0),(21,'US36041314',16,1,'6.5 Digit Multimeter','1300T',0,'Ghulam Mujtaba',12,'2013-12-05',1,'system',0),(22,'US36013575',16,1,'6.5 Digit Multimeter','1313D',0,'Jeffrey Casteel',12,'2013-12-05',1,'system',0),(23,'3146A62452',16,1,'6.5 Digit Multimeter','1300F',0,'Mark Davis',12,'2013-12-05',1,'system',0),(24,'MY47015383',17,1,'6.5 Digit Multimeter','1300G',0,'Kent Holland',12,'2013-12-05',1,'system',0),(25,'MY47015519',17,1,'6.5 Digit Multimeter','1313D',0,'Jeffrey Casteel',12,'2013-12-05',1,'system',0),(26,'MY45001917',17,1,'6.5 Digit Multimeter','1300D',0,'John Priller',0,'2013-12-05',1,'system',0),(27,'MY45047869',18,1,'8.5 Digit Multimeter','1300E',1,'Brian Drewyor',12,'2013-12-05',1,'system',0),(28,'30382',19,1,'Accelerometer','1300L',0,'Shen Zhao',0,'2013-12-05',1,'system',0),(29,'2445A11587',20,1,'Power Meter','1300K',0,'Dan Morris',0,'2013-12-05',1,'system',0),(30,'6625-679-7882',21,1,'\"0.01 Ohm, 100 Amp Shunt\"','1300G',0,'Power Supplies Pool',0,'2013-12-05',1,'system',0),(31,'2112A16296',22,1,'Frequency Counter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(32,'2338A06079',22,1,'Frequency Counter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(33,'9840008',23,1,'Calibrator','1300E',1,'Brian Drewyor',12,'2013-12-05',1,'system',0),(34,'200807000',24,1,'Calibration Coil','1300E',1,'Brian Drewyor',0,'2013-12-05',1,'system',0),(35,'1505005',25,1,'Amplifier','1300E',1,'Brian Drewyor',12,'2013-12-05',1,'system',0),(36,'1067271',26,1,'Current Source','1300T',0,'Ghulam Mujtaba',12,'2013-12-05',1,'system',0),(37,'8834022',27,1,'Volt/mA Calibrator','1300T',0,'Ghulam Mujtaba',12,'2013-12-05',1,'system',0),(38,'9489079',28,1,'Multifunction Calibrator','1300-8',0,'Charisse Supangco',12,'2013-12-05',1,'system',0),(39,'9186026',28,1,'Multifunction Calibrator','1300F',0,'Mark Davis',12,'2013-12-05',1,'system',0),(40,'8859137',28,1,'Multifunction Calibrator','1300E',0,'Brian Drewyor',12,'2013-12-05',1,'system',0),(41,'8859132',28,1,'Multifunction Calibrator','1300B',0,'Kelly Davidson',12,'2013-12-05',1,'system',0),(42,'9226103',28,1,'Multifunction Calibrator','1300G',0,'Kent Holland',12,'2013-12-05',1,'system',0),(43,'1090041',28,1,'Multifunction Calibrator','1313D',0,'Jeffrey Casteel',12,'2013-12-05',1,'system',0),(44,'84190110',29,1,'True RMS Voltmeter','1300-2',0,'Patrick Shinaver',12,'2013-12-05',1,'system',0),(45,'42151496',29,1,'True RMS Voltmeter','1300P',0,'John Brandon',12,'2013-12-05',1,'system',0),(46,'38328927',30,1,'Digital Multimeter','1313C',0,'Jim Vincent',12,'2013-12-05',1,'system',0),(47,'98010018',31,1,'Milliamp Process Clamp Meter','1300B',0,'Kelly Davidson',12,'2013-12-05',1,'system',0),(49,'98130044',31,1,'Milliamp Process Clamp Meter','1300-8',0,'Charisse Supangco',12,'2013-12-05',1,'system',0),(50,'98710070',31,1,'Milliamp Process Clamp Meter','1300R',0,'Edmund Supangco',12,'2013-12-05',1,'system',0),(51,'97410108',32,1,'\"High Voltage Probe,40kV Peak\"','1300G',0,'Kent Holland',0,'2013-12-05',1,'system',0),(52,'86930361',33,1,'True RMS Multimeter','1300D',0,'John Priller',0,'2013-12-05',1,'system',0),(53,'1527A06239',34,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(54,'1849A07946',34,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(55,'2039A08686',34,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(56,'2051A08968',34,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(57,'2246A10541',34,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(58,'2603A11959',34,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(59,'331BA95920',35,1,'Power Sensor 0.1-18GHz','1300K',0,'Dan Morris',0,'2013-12-05',1,'system',0),(60,'2949U01871',36,1,'Vector Voltmeter','K1200 CB-Phase',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(61,'2949U01126',36,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(62,'2949U01685',36,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(63,'2949U01957',36,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(64,'2949U01961',36,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(65,'2949U92094',36,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(66,'3303U03367',36,1,'Vector Voltmeter','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(67,'2943A00493',37,1,'Spectrum Analyzer','1300P',0,'John Brandon',0,'2013-12-05',1,'system',0),(68,'2153A18316',38,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(69,'381QU01974',39,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(70,'2915U00198',40,1,'Signal Generator','1300P',0,'John Brandon',0,'2013-12-05',1,'system',0),(71,'2421A00473',41,1,'Signal Generator','1300P',0,'John Brandon',0,'2013-12-05',1,'system',0),(72,'3106A00331',42,1,'Signal Generator 2-26GHz','1300P',0,'John Brandon',0,'2013-12-05',1,'system',0),(73,'65280743',43,1,'True RMS Multimeter','1300S',0,'Roben Walker',12,'2013-12-05',1,'system',0),(74,'67660338',43,1,'True RMS Multimeter','1300B',0,'Kelly Davidson',12,'2013-12-05',1,'system',0),(75,'54020078',43,1,'True RMS Multimeter','1344',0,'Kurt Kranz',12,'2013-12-05',1,'system',0),(76,'74770640',44,1,'True RMS Multimeter','1300-8',0,'Charisse Supangco',12,'2013-12-05',1,'system',0),(77,'80270175',44,1,'True RMS Multimeter','1300T',0,'Ghulam Mujtaba',0,'2013-12-05',1,'system',0),(78,'79980174',44,1,'True RMS Multimeter','1300F',0,'Mark Davis',12,'2013-12-05',1,'system',0),(79,'89230294',45,1,'True RMS Multimeter','1300-8',0,'Charisse Supangco',12,'2013-12-05',1,'system',0),(80,'89230470',45,1,'True RMS Multimeter','1300E',0,'Brian Drewyor',12,'2013-12-05',1,'system',0),(81,'99490119',45,1,'True RMS Multimeter','1300K',0,'Dan Morris',12,'2013-12-05',1,'system',0),(82,'17710068',45,1,'True RMS Multimeter','1300-7',0,'Leslie Hodges',12,'2013-12-05',1,'system',0),(83,'89230469',45,1,'True RMS Multimeter','1300Q',0,'Michael Holcomb',12,'2013-12-05',1,'system',0),(85,'14840212',45,1,'True RMS Multimeter','1300Q',0,'Michael Holcomb',12,'2013-12-05',1,'system',0),(86,'20440152',45,1,'True RMS Multimeter','1300J',0,'Nathan Usher',12,'2013-12-05',1,'system',0),(87,'88530059',45,1,'True RMS Multimeter','1344',0,'Kurt Kranz',12,'2013-12-05',1,'system',0),(88,'88110436',45,1,'True RMS Multimeter','1313C',0,'Jim Vincent',12,'2013-12-05',1,'system',0),(89,'19740033',45,1,'True RMS Multimeter','1313B',0,'Yihua Wu',12,'2013-12-05',1,'system',0),(90,'92',46,1,'LCR Meter','1300E',0,'Brian Drewyor',0,'2013-12-05',1,'system',0),(91,'5020040',46,1,'LCR Meter','1300G',0,'Kent Holland',0,'2013-12-05',1,'system',0),(92,'8752058606030160',46,1,'LCR Meter','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(93,'P1002312',47,1,'\"80kV  DC Hi-Pot Tester, 800PL Series\"','1300G',0,'Power Supplies Pool',12,'2013-12-05',1,'system',0),(94,'75540188',48,1,'True RMS Multimeter','1300P',0,'John Brandon',12,'2013-12-05',1,'system',0),(95,'77100365',48,1,'True RMS Multimeter','1300P',0,'John Brandon',0,'2013-12-05',1,'system',0),(96,'H11A012',49,1,'TOPACC Zero-flux Measuring Head','1313D',0,'Jeffrey Casteel',0,'2013-12-05',1,'system',0),(97,'02DD8122DV',50,1,'\"High Voltage Oscilloscope Probe,20kV\"','1300G',0,'Kent Holland',0,'2013-12-05',1,'system',0),(98,'CT100000023HF',51,1,'Time Domain Reflectometer','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(99,'311005261',52,1,'Digital Multimeter','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(100,'S80900617',52,1,'Digital Multimeter','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(101,'C020203',53,1,'\"500 MHz, 2.5 GS/s, 4 Channel Digital Oscilloscope\"','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(102,'MY48260221',54,1,'Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(103,'MY48260223',54,1,'Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(104,'MY48260282',54,1,'Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(105,'MY40012028',55,1,'Triple Output DC Power Supply','1313D',0,'Jeffrey Casteel',0,'2013-12-05',1,'system',0),(106,'MY49102227',56,1,'Network Analyzer','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(107,'1090064',57,1,'Function Generator','1300Q',0,'Michael Holcomb',0,'2014-06-25',1,'drewyor',0),(108,'17290034',58,1,'AC Current Clamp','1300-7',0,'Leslie Hodges',12,'2013-12-05',1,'system',0),(109,'18530319',58,1,'AC Current Clamp','1313B',0,'Yihua Wu',12,'2013-12-05',1,'system',0),(110,'11181',59,1,'High Voltage Divider','1300G',0,'Kent Holland',0,'2013-12-05',1,'system',0),(111,'A18669',60,1,'Dual Regulated Power Supply','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(112,'167283DKDV',61,1,'\"AC Current Probe  (150A-10mV/A, 1500-1mV/A, BNC)\"','1300G',0,'Kent Holland',0,'2013-12-05',1,'system',0),(113,'MY48260761',62,1,'Oscilloscope','1300J',0,'Nathan Usher',0,'2013-12-05',1,'system',0),(114,'155F206',63,1,'Analog Input Module','1300L',0,'Shen Zhao',0,'2013-12-05',1,'system',0),(115,'1562D51',64,1,'Analog Input Module','1300L',0,'Shen Zhao',0,'2013-12-05',1,'system',0),(116,'1572EE4',64,1,'Analog Input Module','1300L',0,'Shen Zhao',0,'2013-12-05',1,'system',0),(117,'1572EE8',64,1,'Analog Input Module','1300L',0,'Shen Zhao',0,'2013-12-05',1,'system',0),(118,'15763A2',65,1,'CDAQ Chassis','1300L',0,'Shen Zhao',0,'2013-12-05',1,'system',0),(119,'N/A',66,1,'\"High Voltage Oscilloscope Probe, 2.5kV, 250MHz, X100\"','1313D',0,'Jeffrey Casteel',0,'2013-12-05',1,'system',0),(120,'N/A1',67,1,'\"High Voltage Oscilloscope Probe, 1kV, 600V Floating, 100MHz, X10\"','1313D',0,'Jeffrey Casteel',0,'2013-12-05',1,'system',0),(121,'N/A(2)',67,1,'\"High Voltage Oscilloscope Probe, 1kV, 600V Floating, 100MHz, X10\"','1313D',0,'Jeffrey Casteel',0,'2013-12-05',1,'system',0),(122,'CU2091',68,1,'\"High Voltage Oscilloscope Probe,20kV\"','1313D',0,'Jeffrey Casteel',0,'2013-12-05',1,'system',0),(123,'1A-31281',69,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(124,'1A-31282',69,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(125,'4755',70,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(126,'1A-2560',71,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(127,'1A-2336',72,1,'Signal Generator','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(128,'1701109',73,1,'60kV High Voltage Probe','1300G',0,'Power Supplies Pool',0,'2013-12-05',1,'system',0),(129,'102264',74,1,'Signal Generator 9kHz To 3.3GHz','1300J',0,'Nathan Usher',0,'2013-12-05',1,'system',0),(130,'B021508',75,1,'100MHz 2 Channel Oscilloscope','1300T',0,'Ghulam Mujtaba',0,'2013-12-05',1,'system',0),(131,'B020260',76,1,'Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(132,'B021296',76,1,'Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(133,'3515',77,1,'RF Vector Impedance Analyzer','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(134,'M11A012',78,1,'Zero-Flux Current Transformer','1300G',0,'Power Supplies Pool',0,'2013-12-05',1,'system',0),(135,'C010108',79,1,'100MHz Digital Storage Oscilloscope','1300Q',0,'Michael Holcomb',0,'2013-12-05',1,'system',0),(136,'C010158',79,1,'100MHz Digital Storage Oscilloscope','1300E',0,'Brian Drewyor',0,'2013-12-05',1,'system',0),(137,'C010197',79,1,'100MHz Digital Storage Oscilloscope','1344',0,'Kurt Kranz',0,'2013-12-05',1,'system',0),(138,'C014697',80,1,'Digital Storage Oscilloscope 200 MHz 4 Channel','1300G',0,'Power Supplies Pool',12,'2013-12-05',1,'system',0),(139,'C011707',81,1,'Digital Storage Oscilloscope 200 MHz 4 Channel','1300G',0,'Power Supplies Pool',0,'2013-12-05',1,'system',0),(140,'MY51390027',82,1,'Handheld LCR Meter','1313D',0,'Jeffrey Casteel',12,'2013-12-05',1,'system',0),(141,'120719-13',83,1,'120kV High Voltage Probe','1300G',0,'Power Supplies Pool',0,'2013-12-05',1,'system',0),(142,'MY53400300',84,1,'4+16CH 200MHz Oscilloscope',NULL,0,'Usher',0,'2014-02-25',1,'drewyor',0),(143,'26260014',85,1,'Remote Display True RMS Multimeter',NULL,0,'Vaughn',12,'2014-05-27',1,'drewyor',0),(144,'25880097WS',86,1,'True RMS Clamp Meter',NULL,0,'Vaughn',12,'2014-05-27',1,'drewyor',0);
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_group`
--

DROP TABLE IF EXISTS `device_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_group` (
  `group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_group`
--

LOCK TABLES `device_group` WRITE;
/*!40000 ALTER TABLE `device_group` DISABLE KEYS */;
INSERT INTO `device_group` VALUES (1,'EE','Electrical Engineering'),(2,'IG','Inspection Group');
/*!40000 ALTER TABLE `device_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_model`
--

DROP TABLE IF EXISTS `device_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_model` (
  `model_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(255) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `manual_uri` varchar(255) DEFAULT NULL COMMENT 'URI for the manual',
  `manual_name` varchar(255) DEFAULT NULL COMMENT 'name of the manual',
  `calibration_cycle` int(11) DEFAULT NULL COMMENT 'calibration cycle in days',
  `modified_by` varchar(64) DEFAULT NULL COMMENT 'who last modified this record',
  `date_modified` date DEFAULT NULL COMMENT 'when it was last modified',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT 'for concurrency control',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_model`
--

LOCK TABLES `device_model` WRITE;
/*!40000 ALTER TABLE `device_model` DISABLE KEYS */;
INSERT INTO `device_model` VALUES (1,'Dummy','Dummy','Dummy Record',NULL,NULL,0,'system','2013-12-05',0),(2,'Fluke','112','True RMS Multimeter',NULL,NULL,0,'system','2013-12-05',0),(3,'Agilent','16801A','Logic Analyzer',NULL,NULL,0,'system','2013-12-05',0),(4,'B&K','1762','Triple Output DC Power Supply',NULL,NULL,0,'system','2013-12-05',0),(5,'Fluke','179','True RMS Multimeter',NULL,NULL,12,'system','2013-12-05',0),(6,'Narda','18511','RF Probe',NULL,NULL,0,'system','2013-12-05',0),(7,'Fluke','187','True RMS Multimeter',NULL,NULL,12,'system','2013-12-05',0),(8,'Keithley','2000','Multimeter',NULL,NULL,0,'system','2013-12-05',0),(9,'Fluke','223','Remote Display Digital Multimeter',NULL,NULL,0,'system','2013-12-05',0),(10,'Fluke','23','Digital Multimeter',NULL,NULL,12,'system','2013-12-05',0),(11,'Tektronix','2465DMS','300MHZ 4 Channel Oscilloscope',NULL,NULL,0,'system','2013-12-05',0),(12,'Protek','3205L','Dual Output DC Power Supplies',NULL,NULL,0,'system','2013-12-05',0),(13,'Agilent','33220A','Arbitrary Waveform Generator',NULL,NULL,0,'system','2013-12-05',0),(14,'Agilent','3322A','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(15,'Fluke','337','True RMS Clamp Meter',NULL,NULL,12,'system','2013-12-05',0),(16,'Agilent','34401A','6.5 Digit Multimeter',NULL,NULL,12,'system','2013-12-05',0),(17,'Agilent','34410A','6.5 Digit Multimeter',NULL,NULL,0,'system','2013-12-05',0),(18,'Agilent','3458A','8.5 Digit Multimeter',NULL,NULL,12,'system','2013-12-05',0),(19,'PCB Peizoelectronics','393B04','Accelerometer',NULL,NULL,0,'system','2013-12-05',0),(20,'Hewlett-Packard','435B','Power Meter',NULL,NULL,0,'system','2013-12-05',0),(21,'Leed And Northrop','4361','\"0.01 Ohm, 100 Amp Shunt\"',NULL,NULL,0,'system','2013-12-05',0),(22,'Hewlett-Packard','5381A','Frequency Counter',NULL,NULL,0,'system','2013-12-05',0),(23,'Fluke','5500A','Calibrator',NULL,NULL,12,'system','2013-12-05',0),(24,'Fluke','5500A/COIL','Calibration Coil',NULL,NULL,0,'system','2013-12-05',0),(25,'Fluke','5725A','Amplifier',NULL,NULL,12,'system','2013-12-05',0),(26,'Keithley','6221','Current Source',NULL,NULL,12,'system','2013-12-05',0),(27,'Fluke','715','Volt/mA Calibrator',NULL,NULL,12,'system','2013-12-05',0),(28,'Fluke','725','Multifunction Calibrator',NULL,NULL,12,'system','2013-12-05',0),(29,'Fluke','73-III','True RMS Voltmeter',NULL,NULL,12,'system','2013-12-05',0),(30,'Fluke','77','Digital Multimeter',NULL,NULL,12,'system','2013-12-05',0),(31,'Fluke','771','Milliamp Process Clamp Meter',NULL,NULL,12,'system','2013-12-05',0),(32,'Fluke','80K-40','\"High Voltage Probe,40kV Peak\"',NULL,NULL,0,'system','2013-12-05',0),(33,'Fluke','81438','True RMS Multimeter',NULL,NULL,0,'system','2013-12-05',0),(34,'Hewlett-Packard','8405A','Vector Voltmeter',NULL,NULL,0,'system','2013-12-05',0),(35,'Hewlett-Packard','8481A','Power Sensor 0.1-18GHz',NULL,NULL,0,'system','2013-12-05',0),(36,'Hewlett-Packard','8508A','Vector Voltmeter',NULL,NULL,0,'system','2013-12-05',0),(37,'Hewlett-Packard','8590B','Spectrum Analyzer',NULL,NULL,0,'system','2013-12-05',0),(38,'Hewlett-Packard','8640B','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(39,'Hewlett-Packard','8647A','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(40,'Hewlett-Packard','8657B','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(41,'Hewlett-Packard','8673B','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(42,'Hewlett-Packard','8673G','Signal Generator 2-26GHz',NULL,NULL,0,'system','2013-12-05',0),(43,'Fluke','87','True RMS Multimeter',NULL,NULL,12,'system','2013-12-05',0),(44,'Fluke','87-III','True RMS Multimeter',NULL,NULL,12,'system','2013-12-05',0),(45,'Fluke','87-V','True RMS Multimeter',NULL,NULL,12,'system','2013-12-05',0),(46,'B&K','875B','LCR Meter',NULL,NULL,0,'system','2013-12-05',0),(47,'Hipotronics','880PL-10MA-A','\"80kV  DC Hi-Pot Tester, 800PL Series\"',NULL,NULL,12,'system','2013-12-05',0),(48,'Fluke','89-IV','True RMS Multimeter',NULL,NULL,12,'system','2013-12-05',0),(49,'Hitec','A','TOPACC Zero-flux Measuring Head',NULL,NULL,0,'system','2013-12-05',0),(50,'Tektronix','A621','\"High Voltage Oscilloscope Probe,20kV\"',NULL,NULL,0,'system','2013-12-05',0),(51,'Mohr','CT100HF','Time Domain Reflectometer',NULL,NULL,0,'system','2013-12-05',0),(52,'Unknown','DM-311','Digital Multimeter',NULL,NULL,0,'system','2013-12-05',0),(53,'Tektronix','DPO4054B','\"500 MHz, 2.5 GS/s, 4 Channel Digital Oscilloscope\"',NULL,NULL,0,'system','2013-12-05',0),(54,'Agilent','DSO7034A','Oscilloscope',NULL,NULL,0,'system','2013-12-05',0),(55,'Agilent','E3630A','Triple Output DC Power Supply',NULL,NULL,0,'system','2013-12-05',0),(56,'Agilent','E5061B','Network Analyzer',NULL,NULL,0,'system','2013-12-05',0),(57,'Wavetek','FG2C','Function Generator',NULL,NULL,0,'drewyor','2014-06-26',0),(58,'Fluke','I400','AC Current Clamp',NULL,NULL,12,'system','2013-12-05',0),(59,'Ohm-Labs','KV-25','High Voltage Divider',NULL,NULL,0,'system','2013-12-05',0),(60,'Lambda','LPD-422A-FM','Dual Regulated Power Supply',NULL,NULL,0,'system','2013-12-05',0),(61,'AEMC','MR561','\"AC Current Probe  (150A-10mV/A, 1500-1mV/A, BNC)\"',NULL,NULL,0,'system','2013-12-05',0),(62,'Agilent','MSO7104A','Oscilloscope',NULL,NULL,0,'system','2013-12-05',0),(63,'National Inst.','NI 9234','Analog Input Module',NULL,NULL,0,'system','2013-12-05',0),(64,'National Inst.','NI 9239','Analog Input Module',NULL,NULL,0,'system','2013-12-05',0),(65,'National Inst.','NI CDAQ 9174','CDAQ Chassis',NULL,NULL,0,'system','2013-12-05',0),(66,'Tektronix','P5100','\"High Voltage Oscilloscope Probe, 2.5kV, 250MHz, X100\"',NULL,NULL,0,'system','2013-12-05',0),(67,'Tektronix','P5102','\"High Voltage Oscilloscope Probe, 1kV, 600V Floating, 100MHz, X10\"',NULL,NULL,0,'system','2013-12-05',0),(68,'Tektronix','P6015','\"High Voltage Oscilloscope Probe,20kV\"',NULL,NULL,0,'system','2013-12-05',0),(69,'Prog. Test Sources','PTS040','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(70,'Prog. Test Sources','PTS160','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(71,'Prog. Test Sources','PTS200','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(72,'Prog. Test Sources','PTS250','Signal Generator',NULL,NULL,0,'system','2013-12-05',0),(73,'North Star','PVM-5','60kV High Voltage Probe',NULL,NULL,0,'system','2013-12-05',0),(74,'Rohde & Schwarz','SML03','Signal Generator 9kHz To 3.3GHz',NULL,NULL,0,'system','2013-12-05',0),(75,'Tektronix','TDS220','100MHz 2 Channel Oscilloscope',NULL,NULL,0,'system','2013-12-05',0),(76,'Tektronix','TDS224','Oscilloscope',NULL,NULL,0,'system','2013-12-05',0),(77,'Tomco','TE1000','RF Vector Impedance Analyzer',NULL,NULL,0,'system','2013-12-05',0),(78,'Hitec','TOPACC1.0','Zero-Flux Current Transformer',NULL,NULL,0,'system','2013-12-05',0),(79,'Tektronix','TPS2012','100MHz Digital Storage Oscilloscope',NULL,NULL,0,'system','2013-12-05',0),(80,'Tektronix','TPS2024','Digital Storage Oscilloscope 200 MHz 4 Channel',NULL,NULL,12,'system','2013-12-05',0),(81,'Tektronix','TPS2024B','Digital Storage Oscilloscope 200 MHz 4 Channel',NULL,NULL,0,'system','2013-12-05',0),(82,'Agilent','U1731C','Handheld LCR Meter',NULL,NULL,12,'system','2013-12-05',0),(83,'Ross Engineering','VD120-6.2Y-AK-LB-A','120kV High Voltage Probe',NULL,NULL,0,'system','2013-12-05',0),(84,'Agilent','MSOX3024A','4+16CH 200MHz Oscilloscope',NULL,NULL,0,'drewyor','2014-02-25',0),(85,'Fluke','233','Remote Display True RMS Multimeter',NULL,NULL,12,'drewyor','2014-05-27',0),(86,'Fluke','376','True RMS Clamp Meter',NULL,NULL,12,'drewyor','2014-05-27',0);
/*!40000 ALTER TABLE `device_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_property`
--

DROP TABLE IF EXISTS `module_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_property` (
  `name` varchar(64) NOT NULL,
  `value` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Configuration info for this module ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_property`
--

LOCK TABLES `module_property` WRITE;
/*!40000 ALTER TABLE `module_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `module_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(255) NOT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UQ_role_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Each row represents a role';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'GroupAdmin','Group Administrator',0),(2,'Admin','Overall Administrator',0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysuser`
--

DROP TABLE IF EXISTS `sysuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sysuser` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `unique_name` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UQ_user_unique_name` (`unique_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='Each row represents a user';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysuser`
--

LOCK TABLES `sysuser` WRITE;
/*!40000 ALTER TABLE `sysuser` DISABLE KEYS */;
INSERT INTO `sysuser` VALUES (1,'vuppala','Vasu Vuppala','vuppala@frib.msu.edu',NULL,0),(2,'drewyor','Brian Drewyor','drewyor@frib.msu.edu',NULL,0),(3,'goodrich','Michael Goodrich','Goodrich@frib.msu.edu',NULL,0);
/*!40000 ALTER TABLE `sysuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_preference`
--

DROP TABLE IF EXISTS `user_preference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_preference` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sysuser` int(10) unsigned NOT NULL,
  `pref_name` varchar(32) NOT NULL,
  `pref_value` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_user_prefname` (`sysuser`,`pref_name`),
  KEY `sysuser` (`sysuser`),
  CONSTRAINT `FK_user_preference_user` FOREIGN KEY (`sysuser`) REFERENCES `sysuser` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_preference`
--

LOCK TABLES `user_preference` WRITE;
/*!40000 ALTER TABLE `user_preference` DISABLE KEYS */;
INSERT INTO `user_preference` VALUES (1,2,'DefaultGroup','1'),(2,3,'DefaultGroup','2'),(3,1,'DefaultGroup','1');
/*!40000 ALTER TABLE `user_preference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sysuser` int(10) unsigned NOT NULL,
  `role` int(10) unsigned NOT NULL,
  `canDelegate` tinyint(1) NOT NULL DEFAULT '0',
  `isRoleManager` tinyint(1) NOT NULL DEFAULT '0',
  `startTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `endTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `comment` varchar(255) DEFAULT NULL,
  `version` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_role_id`),
  KEY `role` (`role`),
  KEY `sysuser` (`sysuser`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`sysuser`) REFERENCES `sysuser` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role`) REFERENCES `role` (`role_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='Each row represents a user''s assignment to a role';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,2,0,0,'2014-07-24 07:26:20','0000-00-00 00:00:00',NULL,0),(2,2,1,0,0,'2014-07-24 07:26:20','0000-00-00 00:00:00',NULL,0),(3,3,1,0,0,'2014-07-24 07:26:20','0000-00-00 00:00:00',NULL,0);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-24  3:27:41
