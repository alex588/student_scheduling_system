-- MySQL dump 10.13  Distrib 5.5.27, for Linux (i686)
--
-- Host: localhost    Database: student_scheduling_system
-- ------------------------------------------------------
-- Server version	5.5.27

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
-- Table structure for table `course_availability`
--
USE student_scheduling_system;

DROP TABLE IF EXISTS `course_availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_availability` (
  `ca_id` int(11) NOT NULL AUTO_INCREMENT,
  `ca_semester_id_fk` int(11) NOT NULL,
  `ca_course_id_fk` int(11) NOT NULL,
  `ca_year` int(11) DEFAULT NULL,
  `ca_location` varchar(20) NOT NULL,
  PRIMARY KEY (`ca_id`),
  KEY `ca_semester_id_fk` (`ca_semester_id_fk`),
  KEY `ca_course_id_fk` (`ca_course_id_fk`),
  CONSTRAINT `course_availability_ibfk_1` FOREIGN KEY (`ca_semester_id_fk`) REFERENCES `semesters` (`semster_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_availability_ibfk_2` FOREIGN KEY (`ca_course_id_fk`) REFERENCES `courses` (`course_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=422 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_availability`
--

LOCK TABLES `course_availability` WRITE;
/*!40000 ALTER TABLE `course_availability` DISABLE KEYS */;
INSERT INTO `course_availability` VALUES (3,3,15,NULL,'ONLINE'),(6,1,20,NULL,'ONCAMPUS'),(20,2,28,NULL,'ONCAMPUS'),(21,1,16,NULL,'ONCAMPUS'),(22,2,16,NULL,'ONCAMPUS'),(23,1,17,NULL,'ONCAMPUS'),(24,2,17,NULL,'ONCAMPUS'),(25,1,18,NULL,'ONCAMPUS'),(26,2,18,NULL,'ONCAMPUS'),(27,1,19,NULL,'ONCAMPUS'),(28,2,19,NULL,'ONCAMPUS'),(29,1,32,NULL,'ONCAMPUS'),(32,1,28,NULL,'ONCAMPUS'),(33,2,32,NULL,'ONCAMPUS'),(77,2,35,NULL,'ONCAMPUS'),(80,1,36,NULL,'ONCAMPUS'),(81,2,36,NULL,'ONCAMPUS'),(82,1,13,NULL,'ONCAMPUS'),(83,2,13,NULL,'ONCAMPUS'),(86,1,12,NULL,'ONCAMPUS'),(87,2,12,NULL,'ONCAMPUS'),(88,1,37,NULL,'ONCAMPUS'),(89,2,38,NULL,'ONCAMPUS'),(90,1,11,NULL,'ONCAMPUS'),(91,2,11,NULL,'ONCAMPUS'),(92,1,21,NULL,'ONCAMPUS'),(93,2,21,NULL,'ONCAMPUS'),(94,2,14,NULL,'ONCAMPUS'),(95,1,39,NULL,'ONCAMPUS'),(96,2,39,NULL,'ONCAMPUS'),(97,1,22,NULL,'ONCAMPUS'),(98,1,23,NULL,'ONCAMPUS'),(99,2,23,NULL,'ONCAMPUS'),(100,3,23,NULL,'ONCAMPUS'),(101,1,40,NULL,'ONCAMPUS'),(102,2,40,NULL,'ONCAMPUS'),(104,2,42,NULL,'ONCAMPUS'),(105,1,41,NULL,'ONCAMPUS'),(106,1,43,NULL,'ONCAMPUS'),(107,1,44,NULL,'ONCAMPUS'),(108,1,45,NULL,'ONCAMPUS'),(109,2,45,NULL,'ONCAMPUS'),(110,2,46,NULL,'ONCAMPUS'),(117,1,54,NULL,'ONCAMPUS'),(126,1,61,NULL,'ONCAMPUS'),(127,2,61,NULL,'ONCAMPUS'),(128,3,61,NULL,'ONCAMPUS'),(132,1,59,NULL,'ONCAMPUS'),(151,1,68,NULL,'ONCAMPUS'),(154,1,70,NULL,'ONCAMPUS'),(155,2,71,NULL,'ONCAMPUS'),(156,2,72,NULL,'ONCAMPUS'),(157,1,73,NULL,'ONCAMPUS'),(158,2,73,NULL,'ONCAMPUS'),(159,1,74,NULL,'ONCAMPUS'),(160,2,74,NULL,'ONCAMPUS'),(161,1,75,NULL,'ONCAMPUS'),(162,2,75,NULL,'ONCAMPUS'),(163,1,76,NULL,'ONCAMPUS'),(164,2,76,NULL,'ONCAMPUS'),(165,1,77,NULL,'ONCAMPUS'),(166,2,77,NULL,'ONCAMPUS'),(167,1,78,NULL,'ONCAMPUS'),(168,2,78,NULL,'ONCAMPUS'),(169,1,79,NULL,'ONCAMPUS'),(170,2,79,NULL,'ONCAMPUS'),(171,1,80,NULL,'ONCAMPUS'),(172,2,80,NULL,'ONCAMPUS'),(173,1,81,NULL,'ONCAMPUS'),(174,2,81,NULL,'ONCAMPUS'),(176,2,69,NULL,'ONCAMPUS'),(177,1,82,NULL,'ONCAMPUS'),(178,2,82,NULL,'ONCAMPUS'),(179,1,83,NULL,'ONCAMPUS'),(180,2,83,NULL,'ONCAMPUS'),(181,1,84,NULL,'ONCAMPUS'),(182,2,84,NULL,'ONCAMPUS'),(183,1,85,NULL,'ONCAMPUS'),(184,2,85,NULL,'ONLINE'),(185,2,48,NULL,'ONCAMPUS'),(186,1,51,NULL,'ONCAMPUS'),(187,2,51,NULL,'ONCAMPUS'),(188,1,52,NULL,'ONCAMPUS'),(189,2,52,NULL,'ONCAMPUS'),(190,1,53,NULL,'ONCAMPUS'),(191,2,53,NULL,'ONCAMPUS'),(194,1,27,NULL,'ONCAMPUS'),(195,1,86,NULL,'ONCAMPUS'),(196,2,86,NULL,'ONCAMPUS'),(197,1,87,NULL,'ONCAMPUS'),(198,2,87,NULL,'ONCAMPUS'),(199,1,88,NULL,'ONCAMPUS'),(200,2,88,NULL,'ONCAMPUS'),(201,3,88,NULL,'ONCAMPUS'),(202,1,89,NULL,'ONCAMPUS'),(203,2,89,NULL,'ONCAMPUS'),(204,1,90,NULL,'ONCAMPUS'),(205,2,90,NULL,'ONCAMPUS'),(208,1,92,NULL,'ONCAMPUS'),(209,2,92,NULL,'ONCAMPUS'),(210,1,93,NULL,'ONCAMPUS'),(211,2,93,NULL,'ONCAMPUS'),(216,1,67,NULL,'ONCAMPUS'),(217,3,67,NULL,'ONLINE'),(218,1,24,NULL,'ONCAMPUS'),(219,2,24,NULL,'ONCAMPUS'),(220,1,94,NULL,'ONCAMPUS'),(221,2,94,NULL,'ONCAMPUS'),(222,1,95,NULL,'ONLINE'),(223,2,95,NULL,'ONLINE'),(224,1,96,NULL,'BOTH'),(225,2,96,NULL,'BOTH'),(226,3,96,NULL,'ONLINE'),(227,1,25,NULL,'ONCAMPUS'),(228,2,25,NULL,'ONCAMPUS'),(229,2,26,NULL,'ONCAMPUS'),(230,1,66,NULL,'ONLINE'),(231,1,97,NULL,'ONLINE'),(232,1,63,NULL,'ONCAMPUS'),(233,2,63,NULL,'ONLINE'),(234,3,63,NULL,'ONCAMPUS'),(235,4,63,NULL,'ONLINE'),(237,1,98,NULL,'ONCAMPUS'),(238,2,98,NULL,'ONCAMPUS'),(239,2,62,NULL,'ONCAMPUS'),(240,1,99,NULL,'ONCAMPUS'),(241,2,99,NULL,'ONCAMPUS'),(242,1,100,NULL,'ONCAMPUS'),(243,2,100,NULL,'ONCAMPUS'),(247,1,101,NULL,'ONCAMPUS'),(248,2,101,NULL,'ONCAMPUS'),(249,1,102,NULL,'ONCAMPUS'),(250,2,102,NULL,'ONCAMPUS'),(251,1,103,NULL,'ONCAMPUS'),(252,2,103,NULL,'ONCAMPUS'),(253,1,104,NULL,'ONCAMPUS'),(254,2,104,NULL,'ONCAMPUS'),(255,1,105,NULL,'ONCAMPUS'),(256,2,105,NULL,'ONCAMPUS'),(257,1,106,NULL,'ONCAMPUS'),(258,2,106,NULL,'ONCAMPUS'),(259,1,107,NULL,'ONCAMPUS'),(260,2,107,NULL,'ONCAMPUS'),(261,1,108,NULL,'ONCAMPUS'),(262,2,108,NULL,'ONCAMPUS'),(265,1,110,NULL,'ONCAMPUS'),(266,2,110,NULL,'ONCAMPUS'),(267,1,111,NULL,'ONCAMPUS'),(268,2,111,NULL,'ONCAMPUS'),(269,1,112,NULL,'ONCAMPUS'),(270,2,112,NULL,'ONCAMPUS'),(271,1,113,NULL,'ONCAMPUS'),(272,2,113,NULL,'ONCAMPUS'),(273,1,114,NULL,'ONCAMPUS'),(274,2,114,NULL,'ONCAMPUS'),(275,1,115,NULL,'ONCAMPUS'),(276,2,115,NULL,'ONCAMPUS'),(277,1,116,NULL,'ONCAMPUS'),(278,2,116,NULL,'ONCAMPUS'),(279,1,117,NULL,'ONCAMPUS'),(280,1,119,NULL,'ONCAMPUS'),(281,2,119,NULL,'ONCAMPUS'),(282,1,120,NULL,'ONCAMPUS'),(283,2,120,NULL,'ONCAMPUS'),(284,1,121,NULL,'ONCAMPUS'),(285,2,121,NULL,'ONCAMPUS'),(286,1,122,NULL,'ONCAMPUS'),(287,2,122,NULL,'ONCAMPUS'),(288,1,123,NULL,'ONCAMPUS'),(289,2,123,NULL,'ONCAMPUS'),(290,1,124,NULL,'ONCAMPUS'),(291,2,124,NULL,'ONCAMPUS'),(292,1,125,NULL,'ONCAMPUS'),(293,2,125,NULL,'ONCAMPUS'),(294,1,126,NULL,'ONCAMPUS'),(295,2,126,NULL,'ONCAMPUS'),(296,1,127,NULL,'ONCAMPUS'),(297,2,127,NULL,'ONCAMPUS'),(298,2,128,NULL,'ONCAMPUS'),(299,1,129,NULL,'ONCAMPUS'),(300,2,129,NULL,'ONCAMPUS'),(301,1,130,NULL,'ONCAMPUS'),(302,2,130,NULL,'ONCAMPUS'),(303,1,131,NULL,'ONCAMPUS'),(304,2,131,NULL,'ONCAMPUS'),(305,1,132,NULL,'ONCAMPUS'),(306,2,132,NULL,'ONCAMPUS'),(307,1,133,NULL,'ONCAMPUS'),(308,2,133,NULL,'ONCAMPUS'),(309,1,134,NULL,'ONCAMPUS'),(310,2,134,NULL,'ONCAMPUS'),(311,1,135,NULL,'ONCAMPUS'),(312,2,135,NULL,'ONCAMPUS'),(313,1,136,NULL,'ONCAMPUS'),(314,2,136,NULL,'ONCAMPUS'),(315,1,137,NULL,'ONCAMPUS'),(316,2,137,NULL,'ONCAMPUS'),(317,1,138,NULL,'ONCAMPUS'),(318,2,138,NULL,'ONCAMPUS'),(319,1,139,NULL,'ONCAMPUS'),(320,2,139,NULL,'ONCAMPUS'),(321,1,140,NULL,'ONCAMPUS'),(322,2,140,NULL,'ONCAMPUS'),(323,1,141,NULL,'ONCAMPUS'),(324,2,141,NULL,'ONCAMPUS'),(325,1,142,NULL,'ONCAMPUS'),(326,1,143,NULL,'ONCAMPUS'),(327,2,143,NULL,'ONCAMPUS'),(328,1,144,NULL,'ONCAMPUS'),(329,2,144,NULL,'ONCAMPUS'),(330,1,145,NULL,'ONCAMPUS'),(331,2,145,NULL,'ONCAMPUS'),(332,1,146,NULL,'ONCAMPUS'),(333,2,146,NULL,'ONCAMPUS'),(334,1,147,NULL,'ONCAMPUS'),(335,2,147,NULL,'ONCAMPUS'),(336,1,148,NULL,'ONCAMPUS'),(337,2,148,NULL,'ONCAMPUS'),(338,1,149,NULL,'ONCAMPUS'),(339,2,149,NULL,'ONCAMPUS'),(340,1,150,NULL,'ONCAMPUS'),(341,1,151,NULL,'ONCAMPUS'),(342,1,152,NULL,'ONCAMPUS'),(343,2,152,NULL,'ONCAMPUS'),(344,1,153,NULL,'ONCAMPUS'),(345,1,154,NULL,'ONCAMPUS'),(346,2,154,NULL,'ONCAMPUS'),(347,1,155,NULL,'ONCAMPUS'),(348,2,155,NULL,'ONCAMPUS'),(349,1,156,NULL,'ONCAMPUS'),(350,2,156,NULL,'ONCAMPUS'),(351,1,157,NULL,'ONCAMPUS'),(352,2,157,NULL,'ONCAMPUS'),(353,1,158,NULL,'ONCAMPUS'),(354,2,158,NULL,'ONCAMPUS'),(355,1,159,NULL,'ONCAMPUS'),(356,2,159,NULL,'ONCAMPUS'),(357,1,160,NULL,'ONCAMPUS'),(358,2,160,NULL,'ONCAMPUS'),(359,1,161,NULL,'ONCAMPUS'),(360,2,161,NULL,'ONCAMPUS'),(361,1,162,NULL,'ONCAMPUS'),(362,2,162,NULL,'ONCAMPUS'),(363,1,163,NULL,'ONCAMPUS'),(364,2,163,NULL,'ONCAMPUS'),(365,1,164,NULL,'ONCAMPUS'),(366,2,164,NULL,'ONCAMPUS'),(367,1,165,NULL,'ONCAMPUS'),(368,2,165,NULL,'ONCAMPUS'),(369,1,166,NULL,'ONCAMPUS'),(370,2,166,NULL,'ONCAMPUS'),(371,1,167,NULL,'ONCAMPUS'),(372,2,167,NULL,'ONCAMPUS'),(373,1,168,NULL,'ONCAMPUS'),(374,2,168,NULL,'ONCAMPUS'),(375,1,169,NULL,'ONCAMPUS'),(376,2,169,NULL,'ONCAMPUS'),(377,1,170,NULL,'ONCAMPUS'),(378,2,170,NULL,'ONCAMPUS'),(379,1,171,NULL,'ONCAMPUS'),(380,2,171,NULL,'ONCAMPUS'),(383,1,173,NULL,'ONCAMPUS'),(384,2,173,NULL,'ONCAMPUS'),(385,1,172,NULL,'ONCAMPUS'),(386,1,174,NULL,'ONCAMPUS'),(387,2,174,NULL,'ONCAMPUS'),(388,1,175,NULL,'ONCAMPUS'),(389,2,175,NULL,'ONCAMPUS'),(390,1,176,NULL,'ONCAMPUS'),(391,2,176,NULL,'ONCAMPUS'),(392,1,177,NULL,'ONCAMPUS'),(393,2,177,NULL,'ONCAMPUS'),(394,1,178,NULL,'ONCAMPUS'),(395,2,178,NULL,'ONCAMPUS'),(396,1,179,NULL,'ONCAMPUS'),(397,2,179,NULL,'ONCAMPUS'),(398,1,180,NULL,'ONCAMPUS'),(400,1,182,NULL,'BOTH'),(404,1,185,NULL,'BOTH'),(405,1,184,NULL,'BOTH'),(406,2,184,NULL,'BOTH'),(407,1,29,NULL,'BOTH'),(408,2,29,NULL,'BOTH'),(409,1,181,NULL,'BOTH'),(410,5,181,NULL,'BOTH'),(411,1,183,NULL,'BOTH'),(412,1,55,NULL,'BOTH'),(413,1,60,NULL,'ONCAMPUS'),(414,3,60,NULL,'ONCAMPUS'),(421,2,50,NULL,'ONCAMPUS');
/*!40000 ALTER TABLE `course_availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_groups`
--

DROP TABLE IF EXISTS `course_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_groups` (
  `cg_id_pk` int(10) NOT NULL AUTO_INCREMENT,
  `cg_title` varchar(50) NOT NULL,
  `cg_abbr` varchar(50) DEFAULT NULL,
  `cg_is_simple` tinyint(1) NOT NULL,
  `cg_is_visible` tinyint(1) NOT NULL DEFAULT '1',
  `cg_junction_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cg_id_pk`),
  UNIQUE KEY `cg_abbr` (`cg_abbr`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_groups`
--

LOCK TABLES `course_groups` WRITE;
/*!40000 ALTER TABLE `course_groups` DISABLE KEYS */;
INSERT INTO `course_groups` VALUES (3,'Humanities: 100-200 level','H1L02',1,1,NULL),(4,'Humanities: 300 level or higher','H3L02',1,1,NULL),(5,'Humanities','HUM03',0,1,'OR'),(6,'Technical Electives','TEE02',1,1,NULL),(7,'Software Electives','SOE02',1,1,NULL),(8,'Computer Science - Mandatory','CS-02',1,1,NULL),(18,'Science - Physics','S-P03',1,1,NULL),(19,'Science - Chemistry','S-C04',1,1,NULL),(20,'Science - Chem & Bio','S-C05',1,1,NULL),(21,'Science - Chem & Bio2','S-C06',1,1,NULL),(22,'Science - Phys & Bio','S-P04',1,1,NULL),(23,'Software Development Electives','SDE02',1,1,NULL),(24,'CS 300 Level','C3L03',1,1,NULL),(25,'CS 400 Level','C4L03',1,1,NULL),(26,'Mathematics - Mandatory','M-M03',1,1,NULL),(27,'Management - Mandatory','M-M04',1,1,NULL),(28,'Humanitites - Special','H-S03',1,1,NULL),(29,'Humanities - 200 Level','H-203',1,1,NULL),(30,'Humanities - 100 level','H-102',1,1,NULL),(31,'Humanities - 300 level','H-302',1,1,NULL),(32,'Humanities - 400 level','H-402',1,1,NULL),(33,'Humanities - Literature','H-L02',1,1,NULL),(34,'Humanities -  Philosophy','H-P02',1,1,NULL),(35,'Humanities -  History','H-H02',1,1,NULL),(36,'Humanities - Social Science','H-S04',1,1,NULL),(37,'Science/Math Electives courses','SEC01',1,1,NULL),(38,'CAL humanities','CAH01',1,1,NULL),(39,'Humanities',NULL,0,0,'OR'),(40,'CS mandtory','CSM01',1,1,NULL),(41,'Estra CS mandaroty','ECM01',1,1,NULL),(42,'Free humanities','FRH01',1,1,NULL);
/*!40000 ALTER TABLE `course_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_groups_formulas`
--

DROP TABLE IF EXISTS `course_groups_formulas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_groups_formulas` (
  `cgf_parent_id_pk_fk` int(10) NOT NULL,
  `cgf_child_id_pk_fk` int(11) NOT NULL,
  `cgf_is_positive` tinyint(1) NOT NULL,
  PRIMARY KEY (`cgf_parent_id_pk_fk`,`cgf_child_id_pk_fk`),
  KEY `cgf_child_id_pk_fk` (`cgf_child_id_pk_fk`),
  CONSTRAINT `course_groups_formulas_ibfk_1` FOREIGN KEY (`cgf_parent_id_pk_fk`) REFERENCES `course_groups` (`cg_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_groups_formulas_ibfk_2` FOREIGN KEY (`cgf_child_id_pk_fk`) REFERENCES `course_groups` (`cg_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_groups_formulas`
--

LOCK TABLES `course_groups_formulas` WRITE;
/*!40000 ALTER TABLE `course_groups_formulas` DISABLE KEYS */;
INSERT INTO `course_groups_formulas` VALUES (5,3,1),(5,4,1),(39,3,1),(39,4,1);
/*!40000 ALTER TABLE `course_groups_formulas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_grp_courses`
--

DROP TABLE IF EXISTS `course_grp_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_grp_courses` (
  `course_group_id_fk` int(10) NOT NULL,
  `course_id_fk` int(10) NOT NULL,
  KEY `course_id` (`course_id_fk`),
  KEY `course_group_id_fk` (`course_group_id_fk`),
  CONSTRAINT `course_grp_courses_ibfk_3` FOREIGN KEY (`course_group_id_fk`) REFERENCES `course_groups` (`cg_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_grp_courses_ibfk_4` FOREIGN KEY (`course_id_fk`) REFERENCES `courses` (`course_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_grp_courses`
--

LOCK TABLES `course_grp_courses` WRITE;
/*!40000 ALTER TABLE `course_grp_courses` DISABLE KEYS */;
INSERT INTO `course_grp_courses` VALUES (7,28),(7,32),(7,29),(18,68),(18,69),(18,82),(19,70),(19,71),(19,83),(20,70),(20,72),(20,83),(21,70),(21,72),(21,84),(22,68),(22,72),(22,84),(27,93),(28,94),(28,81),(23,24),(23,25),(23,26),(23,66),(23,59),(23,27),(23,63),(23,85),(23,67),(23,62),(30,101),(30,102),(30,110),(30,111),(30,16),(30,17),(30,103),(30,104),(30,105),(30,106),(30,107),(30,108),(30,122),(30,123),(30,115),(30,116),(30,117),(30,118),(30,124),(30,125),(30,119),(30,120),(30,121),(30,126),(30,127),(30,113),(30,114),(31,128),(31,129),(31,130),(31,131),(31,132),(31,133),(31,134),(31,135),(31,136),(31,137),(31,138),(31,18),(31,139),(31,74),(31,140),(31,141),(31,75),(31,76),(31,19),(31,142),(31,143),(31,144),(31,145),(31,146),(31,147),(31,148),(31,149),(31,150),(31,94),(31,151),(31,152),(31,153),(31,154),(31,155),(31,156),(31,157),(31,158),(31,159),(32,160),(32,161),(32,162),(32,163),(32,164),(32,165),(32,166),(32,167),(32,168),(32,77),(32,78),(32,79),(32,80),(32,169),(32,170),(32,81),(32,171),(32,172),(32,173),(32,174),(32,175),(32,176),(32,177),(32,178),(32,179),(33,103),(33,104),(33,105),(33,106),(33,107),(33,108),(34,16),(34,17),(34,18),(34,74),(34,75),(34,76),(34,19),(34,77),(34,78),(34,79),(34,80),(34,81),(35,115),(35,116),(35,117),(35,118),(35,119),(35,120),(35,121),(35,128),(35,129),(35,130),(35,131),(35,132),(35,133),(35,134),(35,135),(35,136),(35,137),(35,138),(35,139),(35,140),(35,141),(35,142),(35,143),(35,144),(35,145),(35,146),(35,147),(35,148),(35,150),(35,151),(35,152),(35,153),(35,154),(35,155),(35,156),(35,158),(35,159),(35,160),(35,161),(35,162),(35,163),(35,164),(35,165),(35,166),(35,167),(35,168),(35,169),(35,170),(35,171),(35,172),(35,173),(35,174),(35,175),(35,176),(35,177),(35,178),(35,179),(36,122),(36,123),(36,124),(36,125),(36,126),(36,127),(36,149),(36,94),(36,157),(24,21),(24,14),(24,39),(24,22),(24,23),(24,73),(24,40),(25,41),(25,42),(25,43),(25,44),(25,45),(25,46),(25,48),(25,50),(25,51),(25,52),(25,53),(29,112),(26,86),(26,87),(26,89),(26,90),(26,100),(26,92),(6,39),(6,45),(6,51),(6,52),(6,53),(6,24),(6,25),(6,26),(6,27),(37,180),(37,181),(37,182),(37,183),(38,184),(38,185),(8,13),(8,20),(8,12),(8,11),(8,21),(8,14),(8,22),(8,23),(8,73),(8,41),(8,42),(8,43),(8,44),(8,46),(8,48),(8,50),(8,54),(8,55),(40,13),(40,20),(40,12),(40,11),(40,21),(40,14),(40,22),(40,23),(40,73),(40,41),(40,42),(40,43),(40,44),(40,46),(40,48),(40,54),(40,55),(41,50),(3,16),(3,17),(3,101),(3,102),(3,103),(3,104),(3,105),(3,106),(3,107),(3,108),(3,110),(3,111),(3,112),(3,115),(3,116),(3,117),(3,118),(42,167),(42,168),(42,169),(42,170),(42,171),(42,172),(42,173),(42,174),(42,175),(42,176),(4,18),(4,19),(4,74),(4,75),(4,76),(4,77),(4,78),(4,79),(4,80),(4,81),(4,128),(4,129),(4,130),(4,131),(4,132),(4,133),(4,134);
/*!40000 ALTER TABLE `course_grp_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `course_id_pk` int(10) NOT NULL AUTO_INCREMENT,
  `course_prefix_id_fk` int(11) NOT NULL,
  `course_number` int(11) NOT NULL,
  `course_title` varchar(50) NOT NULL,
  `course_credits` int(11) NOT NULL,
  `course_level` int(11) NOT NULL,
  `course_prereq_formula_id_fk` int(10) DEFAULT NULL,
  `course_coreq_formula_id_fk` int(10) DEFAULT NULL,
  PRIMARY KEY (`course_id_pk`),
  KEY `course_prefix_id_fk` (`course_prefix_id_fk`),
  KEY `course_prereq_formula_id_fk` (`course_prereq_formula_id_fk`),
  KEY `course_coreq_formula_id_fk` (`course_coreq_formula_id_fk`),
  CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`course_prefix_id_fk`) REFERENCES `prefixes` (`prefix_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `courses_ibfk_3` FOREIGN KEY (`course_prereq_formula_id_fk`) REFERENCES `prereq_coreq_formulas` (`pcf_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `courses_ibfk_4` FOREIGN KEY (`course_coreq_formula_id_fk`) REFERENCES `prereq_coreq_formulas` (`pcf_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (11,3,284,'Data Structures',3,200,120,122),(12,3,135,'Discrete Structures',3,100,NULL,NULL),(13,3,115,'Introduction to Computer Science',4,100,NULL,NULL),(14,3,347,'Software Development Process',3,300,126,NULL),(15,3,105,'Introduction to Scientific Computing',3,100,NULL,NULL),(16,4,111,'Philosophy I',3,100,NULL,NULL),(17,4,112,'Knowledge, Reality and Nature',3,100,NULL,NULL),(18,4,339,'Ethics',3,300,NULL,NULL),(19,4,348,'Aesthetics',3,300,NULL,NULL),(20,3,146,'Web Fundamentals',3,100,NULL,NULL),(21,3,334,'Automata & Computation',3,300,123,NULL),(22,3,383,'Computer Organization & Programming',3,300,135,136),(23,3,385,'Algorithms',4,300,139,NULL),(24,3,516,'Compiler Design',3,500,257,NULL),(25,3,521,'TCP/IP Networks',3,500,265,NULL),(26,3,522,'Mobile Systems and Applications',3,500,268,NULL),(27,3,541,'Artifical Intelligence',3,500,238,NULL),(28,5,606,'Introduction to Developing Internet Applications',3,600,15,NULL),(29,5,521,'Software Requirements Analysis and Engineering',3,500,308,NULL),(30,5,565,'Software Architecture and Component-Based Design',3,500,NULL,NULL),(31,5,567,'Software Testing, Quality Assurance, and Maintenan',3,500,NULL,NULL),(32,5,605,'Intoduction to Service Oriented Computing',3,600,22,NULL),(35,3,577,'Software Engineering',4,500,99,105),(36,3,105,'Introduction to Scientific Computing',3,100,NULL,NULL),(37,3,181,'Introduction to Computer Science Honors I',3,100,NULL,NULL),(38,3,182,'Introduction to Computer Science Honors II',3,100,116,118),(39,3,370,'Creative Problem Solving and Team Programming',3,300,131,NULL),(40,3,393,'Systems Programming',3,300,142,NULL),(41,3,423,'Senior Design I',3,400,152,NULL),(42,3,424,'Senior Design II',3,400,151,NULL),(43,3,442,'Database Management Systems',3,400,157,NULL),(44,3,443,'Database Practicum',3,400,161,NULL),(45,3,465,'Selected Topics in Computer Science',3,400,NULL,NULL),(46,3,488,'Computer Architecture',3,400,163,NULL),(48,3,492,'Operating Systems',3,400,228,NULL),(50,3,496,'Principles of Programming Languages',3,400,327,NULL),(51,3,497,'Independent Study',3,400,NULL,NULL),(52,3,498,'Senior Research I',3,400,NULL,NULL),(53,3,499,'Senior Research II',3,400,NULL,NULL),(54,3,506,'Introduction to IT Security',3,500,NULL,NULL),(55,3,511,'Concurrent Programming',3,500,309,NULL),(59,3,537,'Interactive Computer Graphics',3,500,187,NULL),(60,3,590,'Algorithms',3,500,NULL,NULL),(61,3,570,'Introduction to Programming in C++',3,500,NULL,NULL),(62,3,558,'Computer Vision',3,500,284,NULL),(63,3,546,'Web Programming',3,500,NULL,NULL),(66,3,526,'Enterprise and Cloud Computing',3,500,273,NULL),(67,3,549,'Distributed Systems and Cloud Computing',3,500,252,NULL),(68,1,111,'Mechanics',3,100,NULL,NULL),(69,1,112,'Electricity and Magnetism',3,100,216,NULL),(70,2,115,'General Chemistry I',3,100,NULL,NULL),(71,2,116,'General Chemistry II',3,100,209,NULL),(72,2,281,'Bio & Biotech',3,200,NULL,NULL),(73,3,392,'System Programming',3,300,210,NULL),(74,4,340,'Social and Political Philosophy',3,300,NULL,NULL),(75,4,345,'Intro to minority Group Identity & Legal Theory',3,300,NULL,NULL),(76,4,346,'Modern Philosophy',3,300,NULL,NULL),(77,4,442,'Logic',3,400,NULL,NULL),(78,4,443,'The Philosophy of Language',3,400,NULL,NULL),(79,4,444,'Philosophy of Mind',3,400,NULL,NULL),(80,4,445,'The History of Philosophy',3,400,NULL,NULL),(81,4,455,'Ethical Issues in Science and Technology',3,400,NULL,NULL),(82,1,221,'Physics Lab I for Scientists',3,200,218,220),(83,2,117,'General Chemistry Laboratory I ',1,100,NULL,222),(84,2,282,'Introductory Biology Laboratory',3,200,NULL,NULL),(85,3,548,'Enterprise Software Architecture and Design',3,500,223,NULL),(86,6,121,'Differential Calculus',3,100,NULL,NULL),(87,6,122,'Integral Calculus',3,100,244,NULL),(88,3,561,'Database Management Systems I',3,500,246,NULL),(89,6,123,'Series, Vectors, Functions, and Surfaces',3,100,NULL,NULL),(90,6,124,'Calculus for Functions of Two Variables',3,100,NULL,NULL),(92,6,331,'Intermediate Statistics',3,300,NULL,NULL),(93,7,330,'Social Psychology and Organizational Behavior',3,300,NULL,NULL),(94,13,371,'Computers and Society',3,300,NULL,NULL),(95,3,550,'Computer Organization and Programming',3,500,NULL,NULL),(96,3,520,'Introduction to Operating Systems',3,500,262,NULL),(97,5,611,'Fundemental of Service Oriented Computing',3,600,NULL,NULL),(98,6,232,'Linear Algebra',3,200,NULL,NULL),(99,6,116,'Calculus II',3,100,NULL,NULL),(100,6,222,'Probability and Statistics',3,200,292,NULL),(101,8,103,'Freshmen Writing And Communications I',3,100,NULL,NULL),(102,8,104,'Freshmen Writing And Communications II',3,100,298,NULL),(103,9,113,'Western Literature & Classical Literature',3,100,NULL,NULL),(104,9,114,'Western Literature: Middle Ages to the Present',3,100,NULL,NULL),(105,9,115,'The English Language: Language of Ideas',3,100,NULL,NULL),(106,9,116,'Analysis of Literary Forms',3,100,NULL,NULL),(107,9,117,'Colonial and Romantic American Literature',3,100,NULL,NULL),(108,9,118,'Realist and Modern American Literature',3,100,NULL,NULL),(110,8,107,'Studies in History / Social Science: Modernization',3,100,NULL,NULL),(111,8,108,'Studies in History & Social Science II',3,100,NULL,NULL),(112,8,288,'Sophomore Honors in History / Social History',3,200,NULL,NULL),(113,10,190,'History of Art: Prehistory to the Modern Era',3,100,NULL,NULL),(114,10,191,'Modern Art History and Theory',3,100,NULL,NULL),(115,11,123,'European Society and Cultural History to 1500',3,100,NULL,NULL),(116,11,124,'History of European Society and Culture Since 1500',3,100,300,NULL),(117,11,125,'United States History to 1865',3,100,NULL,NULL),(118,11,126,'HHS 126 United States History Since 1865',3,100,302,NULL),(119,11,129,'Topics in the History of Science and Technology',3,100,NULL,NULL),(120,11,130,'Hsitory of Science and Technology',3,100,NULL,NULL),(121,11,135,'Survey of the Islamic World',3,100,NULL,NULL),(122,13,121,'Cities and Civilizations I',3,100,NULL,NULL),(123,13,122,'Cities and Civilizations II',3,100,304,NULL),(124,13,127,'Political Science I',3,100,NULL,NULL),(125,13,128,'Political Science II',3,100,306,NULL),(126,13,175,'Psychology: Brain, Mind and Behavior',3,100,NULL,NULL),(127,13,176,' Psychology: Development, Personality, Pathology',3,100,NULL,NULL),(128,11,301,'Introduction to Historical Methods',3,300,NULL,NULL),(129,11,309,'Newton and the Scientific Revolution',3,300,NULL,NULL),(130,11,310,'Social History of Science',3,300,NULL,NULL),(131,11,311,'Science and Society in the 20th Century',3,300,NULL,NULL),(132,11,312,'Technology and Society in America',3,300,NULL,NULL),(133,11,313,'Science, Faith, and the American Imagination',3,300,NULL,NULL),(134,11,319,'The Roman Republic',3,300,NULL,NULL),(135,11,322,'American Cultural History',3,300,NULL,NULL),(136,11,323,'Women and Gender in American History',3,300,NULL,NULL),(137,11,325,'African-American Studies',3,300,NULL,NULL),(138,11,338,'The Russian Revolution and the Soviet Regime',3,300,NULL,NULL),(139,11,340,'History of Middle East to 1800',3,300,NULL,NULL),(140,11,341,'History of the Middle East Since 1800',3,300,NULL,NULL),(141,11,345,'Science and Technology in Islamic History',3,300,NULL,NULL),(142,11,355,'U.S. Foreign Relations',3,300,NULL,NULL),(143,11,356,'The Golden Age of Athens',3,300,NULL,NULL),(144,11,361,'Scientific Revolution: Galileo',3,300,NULL,NULL),(145,11,363,'Darwin and the Darwinian Revolution',3,300,NULL,NULL),(146,11,365,'History of Modern Germany',3,300,NULL,NULL),(147,11,367,'Twentieth-Cetury History',3,300,NULL,NULL),(148,11,369,'Studies in the Scientific Revolution',3,300,NULL,NULL),(149,13,370,'US Constitutional Law I',3,300,NULL,NULL),(150,11,371,'Modern US Presidency and the Legislative Process',3,300,NULL,NULL),(151,11,373,'US Constitutional Law II',3,300,NULL,NULL),(152,11,374,' Psychohistory',3,300,NULL,NULL),(153,11,378,'Modern European History',3,300,NULL,NULL),(154,11,382,'The Spanish Republic and the Civil War',3,300,NULL,NULL),(155,11,384,'US and the Rise of the Modern Middle East',3,300,NULL,NULL),(156,11,386,'Ancient Civilizations: The Roman Empire',3,300,NULL,NULL),(157,13,390,'History of Money, Credit, and Banking',3,300,NULL,NULL),(158,11,391,'Metropolitan Developmental Studies',3,300,NULL,NULL),(159,11,397,'Historical Materialism',3,300,NULL,NULL),(160,11,414,'Industrial America',3,400,NULL,NULL),(161,11,415,'Religion in America',3,400,NULL,NULL),(162,11,420,'Modern East Asian Studies',3,400,NULL,NULL),(163,11,429,'The Scientist, the Engineer, and the Computer',3,400,NULL,NULL),(164,11,430,'History of Modern Turkey',3,400,NULL,NULL),(165,11,431,'20th Century Arab Nationalism',3,400,NULL,NULL),(166,11,432,'History of Nationalism in the Middle East',3,400,NULL,NULL),(167,11,433,'History of Central Asia',3,400,NULL,NULL),(168,11,434,'History of the Ottoman Empire',3,400,NULL,NULL),(169,11,451,'From Ape to Adam: Understanding Human Evolution',3,400,NULL,NULL),(170,11,453,'Justice in War',3,400,NULL,NULL),(171,11,460,'Technogenesis in American History',3,400,NULL,NULL),(172,11,465,'Engineering in History',3,400,NULL,NULL),(173,11,468,'History of the World',3,400,NULL,NULL),(174,11,469,'History of England: 1066 - Present',3,400,NULL,NULL),(175,11,473,'Renaissance Studies: Leonardo da Vinci',3,400,NULL,NULL),(176,11,476,'History of Medicine',3,400,NULL,NULL),(177,11,479,'Studies in the History of Technology',3,400,NULL,NULL),(178,11,483,'History and Geography',3,400,NULL,NULL),(179,11,495,'Seminar in History',3,400,NULL,NULL),(180,6,335,'Number Theory',3,300,NULL,NULL),(181,6,336,'Modern Algebra',3,300,NULL,NULL),(182,6,346,'Numerical Methods',3,300,NULL,NULL),(183,6,460,'Chaotic Dynamics',3,400,NULL,NULL),(184,12,103,'CAL 103',3,100,NULL,NULL),(185,12,105,'CAL 105',3,100,NULL,NULL);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degree_program_reqs`
--

DROP TABLE IF EXISTS `degree_program_reqs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degree_program_reqs` (
  `requirement_id_fk` int(11) NOT NULL,
  `degree_program_id_fk` int(11) NOT NULL,
  KEY `requirements_id` (`requirement_id_fk`),
  KEY `degree_programs_id` (`degree_program_id_fk`),
  CONSTRAINT `degree_program_reqs_ibfk_1` FOREIGN KEY (`requirement_id_fk`) REFERENCES `requirements` (`req_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `degree_program_reqs_ibfk_2` FOREIGN KEY (`degree_program_id_fk`) REFERENCES `degree_programs` (`dp_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree_program_reqs`
--

LOCK TABLES `degree_program_reqs` WRITE;
/*!40000 ALTER TABLE `degree_program_reqs` DISABLE KEYS */;
INSERT INTO `degree_program_reqs` VALUES (1,2),(2,2),(4,2),(5,2),(6,2),(16,2),(18,2),(19,2),(20,2),(21,2),(22,2),(29,2),(48,1);
/*!40000 ALTER TABLE `degree_program_reqs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degree_programs`
--

DROP TABLE IF EXISTS `degree_programs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degree_programs` (
  `dp_id_pk` int(10) NOT NULL AUTO_INCREMENT,
  `dp_title` varchar(50) NOT NULL,
  PRIMARY KEY (`dp_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree_programs`
--

LOCK TABLES `degree_programs` WRITE;
/*!40000 ALTER TABLE `degree_programs` DISABLE KEYS */;
INSERT INTO `degree_programs` VALUES (1,'Mega Complex Requirements (deme)'),(2,'CS_Entering_2013_Starting_With_CS115');
/*!40000 ALTER TABLE `degree_programs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prefixes`
--

DROP TABLE IF EXISTS `prefixes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prefixes` (
  `prefix_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `prefix_title` varchar(20) NOT NULL,
  `prefix_full_name` varchar(20) NOT NULL,
  PRIMARY KEY (`prefix_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prefixes`
--

LOCK TABLES `prefixes` WRITE;
/*!40000 ALTER TABLE `prefixes` DISABLE KEYS */;
INSERT INTO `prefixes` VALUES (1,'PEP','Physics'),(2,'CH','Chemistry'),(3,'CS','Computer Science'),(4,'HPL','Philosophy'),(5,'SOC','Software Engineering'),(6,'MA','Mathematics'),(7,'BT','Business and Technol'),(8,'HUM','Humanities'),(9,'HLI','Humanities Literatur'),(10,'HAR','Humanities Arts'),(11,'HHS','Humanities History'),(12,'CAL','College of Arts and '),(13,'HSS','Humanities and Socia');
/*!40000 ALTER TABLE `prefixes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prereq_coreq_formulas`
--

DROP TABLE IF EXISTS `prereq_coreq_formulas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prereq_coreq_formulas` (
  `pcf_id_pk` int(10) NOT NULL AUTO_INCREMENT,
  `pcf_parent_id_fk` int(10) DEFAULT NULL,
  `pcf_junction_type` varchar(10) DEFAULT NULL,
  `pcf_is_course` tinyint(1) NOT NULL,
  `pcf_course_id_fk` int(11) DEFAULT NULL,
  `pcf_is_positive` tinyint(1) NOT NULL,
  PRIMARY KEY (`pcf_id_pk`),
  KEY `course_id` (`pcf_course_id_fk`),
  KEY `pcf_parent_id_fk` (`pcf_parent_id_fk`),
  CONSTRAINT `prereq_coreq_formulas_ibfk_1` FOREIGN KEY (`pcf_course_id_fk`) REFERENCES `courses` (`course_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `prereq_coreq_formulas_ibfk_2` FOREIGN KEY (`pcf_parent_id_fk`) REFERENCES `prereq_coreq_formulas` (`pcf_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prereq_coreq_formulas`
--

LOCK TABLES `prereq_coreq_formulas` WRITE;
/*!40000 ALTER TABLE `prereq_coreq_formulas` DISABLE KEYS */;
INSERT INTO `prereq_coreq_formulas` VALUES (4,NULL,'OR',0,NULL,1),(6,4,'AND',0,NULL,1),(7,6,'',1,11,1),(8,6,'',1,12,1),(9,NULL,'AND',0,NULL,1),(10,9,'',1,11,1),(11,9,'',1,12,1),(12,NULL,'',1,23,1),(13,NULL,'',1,23,1),(14,NULL,'',1,23,1),(15,NULL,'',1,32,1),(16,NULL,'',1,28,1),(17,NULL,'',1,13,1),(18,NULL,'AND',0,NULL,1),(19,18,'',1,13,1),(20,NULL,'',1,11,1),(21,NULL,'',1,13,1),(22,NULL,'',1,13,1),(24,18,'',1,12,1),(25,NULL,NULL,1,13,1),(27,NULL,NULL,1,13,1),(28,NULL,NULL,1,13,1),(29,NULL,NULL,1,13,1),(31,NULL,NULL,1,13,1),(32,NULL,NULL,1,13,1),(33,NULL,NULL,1,13,1),(35,NULL,NULL,1,20,1),(36,NULL,NULL,1,20,1),(37,NULL,NULL,1,13,1),(38,NULL,NULL,1,13,1),(39,NULL,NULL,1,13,1),(41,NULL,NULL,1,20,1),(42,NULL,NULL,1,20,1),(43,NULL,NULL,1,13,1),(44,NULL,NULL,1,13,1),(47,NULL,NULL,1,13,1),(49,NULL,NULL,1,20,1),(50,NULL,NULL,1,20,1),(51,NULL,NULL,1,13,1),(52,NULL,NULL,1,13,1),(53,NULL,NULL,1,13,1),(55,NULL,NULL,1,13,1),(56,NULL,NULL,1,13,1),(57,NULL,NULL,1,13,1),(59,NULL,NULL,1,20,1),(60,NULL,NULL,1,20,1),(61,NULL,NULL,1,13,1),(62,NULL,NULL,1,13,1),(63,NULL,NULL,1,13,1),(64,NULL,NULL,1,13,1),(65,NULL,NULL,1,20,1),(66,NULL,NULL,1,20,1),(67,NULL,'AND',0,NULL,1),(68,67,NULL,1,20,1),(69,67,NULL,1,13,1),(70,NULL,NULL,1,20,1),(71,NULL,NULL,1,20,1),(72,NULL,NULL,1,20,1),(73,NULL,NULL,1,20,1),(74,NULL,'AND',0,NULL,1),(75,74,NULL,1,13,1),(76,74,NULL,1,20,1),(77,NULL,NULL,1,13,1),(78,NULL,NULL,1,13,1),(79,NULL,'AND',0,NULL,1),(80,79,NULL,1,20,1),(81,79,NULL,1,13,1),(82,NULL,NULL,1,20,1),(83,NULL,NULL,1,20,1),(84,NULL,NULL,1,13,1),(85,NULL,NULL,1,13,1),(86,NULL,NULL,1,20,1),(87,NULL,NULL,1,20,1),(88,NULL,NULL,1,13,1),(89,NULL,NULL,1,13,1),(90,NULL,NULL,1,20,1),(91,NULL,NULL,1,20,1),(92,NULL,'AND',0,NULL,1),(93,92,'OR',0,NULL,1),(94,93,NULL,1,13,1),(95,93,NULL,1,12,1),(96,92,NULL,1,23,1),(97,NULL,NULL,1,29,1),(98,NULL,NULL,1,29,1),(99,NULL,'AND',0,NULL,1),(100,99,'OR',0,NULL,1),(101,100,NULL,1,13,1),(102,100,NULL,1,12,1),(103,99,NULL,1,23,1),(104,NULL,NULL,1,29,1),(105,NULL,NULL,1,29,1),(106,NULL,'AND',0,NULL,1),(107,106,'OR',0,NULL,1),(108,107,NULL,1,13,1),(109,107,NULL,1,12,1),(110,106,NULL,1,23,1),(111,NULL,NULL,1,29,1),(112,NULL,NULL,1,29,1),(113,NULL,NULL,1,13,1),(114,NULL,NULL,1,13,1),(115,NULL,NULL,1,37,1),(116,NULL,NULL,1,37,1),(117,NULL,NULL,1,12,1),(118,NULL,NULL,1,12,1),(119,NULL,NULL,1,13,1),(120,NULL,NULL,1,13,1),(121,NULL,NULL,1,12,1),(122,NULL,NULL,1,12,1),(123,NULL,'AND',0,NULL,1),(124,123,NULL,1,12,1),(125,123,NULL,1,13,1),(126,NULL,'AND',0,NULL,1),(127,126,'OR',0,NULL,1),(128,127,NULL,1,37,1),(129,127,NULL,1,11,1),(130,126,NULL,1,12,1),(131,NULL,'OR',0,NULL,1),(132,131,NULL,1,23,1),(133,131,NULL,1,38,1),(134,NULL,NULL,1,13,1),(135,NULL,NULL,1,13,1),(136,NULL,'OR',0,NULL,1),(137,136,NULL,1,11,1),(138,136,NULL,1,37,1),(139,NULL,'OR',0,NULL,1),(140,139,NULL,1,11,1),(141,139,NULL,1,37,1),(142,NULL,'OR',0,NULL,1),(143,142,NULL,1,23,1),(144,142,NULL,1,38,1),(145,NULL,'AND',0,NULL,1),(146,145,'OR',0,NULL,1),(147,146,NULL,1,38,1),(148,146,NULL,1,23,1),(149,145,NULL,1,14,1),(150,NULL,NULL,1,41,1),(151,NULL,NULL,1,41,1),(152,NULL,'AND',0,NULL,1),(153,152,'OR',0,NULL,1),(154,153,NULL,1,38,1),(155,153,NULL,1,23,1),(156,152,NULL,1,14,1),(157,NULL,'OR',0,NULL,1),(158,157,NULL,1,23,1),(159,157,NULL,1,38,1),(160,NULL,NULL,1,43,1),(161,NULL,NULL,1,43,1),(162,NULL,NULL,1,22,1),(163,NULL,NULL,1,22,1),(164,NULL,NULL,1,22,1),(165,NULL,NULL,1,22,1),(166,NULL,NULL,1,22,1),(167,NULL,NULL,1,22,1),(168,NULL,'AND',0,NULL,1),(169,NULL,'AND',0,NULL,1),(170,NULL,NULL,1,21,1),(171,NULL,NULL,1,21,1),(172,NULL,'OR',0,NULL,1),(173,172,NULL,1,23,1),(174,172,NULL,1,38,1),(175,NULL,'OR',0,NULL,1),(176,175,'OR',0,NULL,1),(177,NULL,'OR',0,NULL,1),(178,NULL,'OR',0,NULL,1),(179,178,'OR',0,NULL,1),(180,179,NULL,1,38,1),(181,179,NULL,1,23,1),(182,NULL,'OR',0,NULL,1),(183,182,NULL,1,38,1),(184,182,NULL,1,23,1),(185,NULL,NULL,1,61,1),(186,NULL,NULL,1,61,1),(187,NULL,'OR',0,NULL,1),(188,187,'OR',0,NULL,1),(189,188,NULL,1,38,1),(190,188,NULL,1,23,1),(191,187,NULL,1,60,1),(192,NULL,'OR',0,NULL,1),(193,192,'OR',0,NULL,1),(194,193,NULL,1,38,1),(195,193,NULL,1,23,1),(196,192,NULL,1,60,1),(197,NULL,'OR',0,NULL,1),(198,197,'OR',0,NULL,1),(199,198,NULL,1,38,1),(200,198,NULL,1,23,1),(201,197,NULL,1,60,1),(202,NULL,'OR',0,NULL,1),(203,202,NULL,1,43,1),(204,NULL,'OR',0,NULL,1),(205,204,NULL,1,43,1),(206,NULL,NULL,1,68,1),(207,NULL,NULL,1,68,1),(208,NULL,NULL,1,70,1),(209,NULL,NULL,1,70,1),(210,NULL,'OR',0,NULL,1),(211,210,NULL,1,23,1),(212,210,NULL,1,38,1),(213,NULL,NULL,1,68,1),(214,NULL,NULL,1,68,1),(215,NULL,NULL,1,68,1),(216,NULL,NULL,1,68,1),(217,NULL,NULL,1,68,1),(218,NULL,NULL,1,68,1),(219,NULL,NULL,1,69,1),(220,NULL,NULL,1,69,1),(221,NULL,NULL,1,70,1),(222,NULL,NULL,1,70,1),(223,NULL,'OR',0,NULL,1),(224,223,'OR',0,NULL,1),(225,224,NULL,1,60,1),(226,224,NULL,1,23,1),(227,223,NULL,1,38,1),(228,NULL,'OR',0,NULL,1),(229,228,NULL,1,73,1),(230,228,NULL,1,22,1),(231,NULL,NULL,1,61,1),(232,NULL,NULL,1,61,1),(233,NULL,'OR',0,NULL,1),(234,233,'OR',0,NULL,1),(235,234,NULL,1,38,1),(236,234,NULL,1,23,1),(237,233,NULL,1,60,1),(238,NULL,'OR',0,NULL,1),(239,238,'OR',0,NULL,1),(240,239,NULL,1,38,1),(241,239,NULL,1,23,1),(242,238,NULL,1,60,1),(243,NULL,NULL,1,86,1),(244,NULL,NULL,1,86,1),(245,NULL,NULL,1,60,1),(246,NULL,NULL,1,60,1),(247,NULL,'AND',0,NULL,1),(248,247,'OR',0,NULL,1),(249,248,NULL,1,88,1),(250,248,NULL,1,43,1),(251,247,NULL,1,20,1),(252,NULL,'OR',0,NULL,1),(253,252,'OR',0,NULL,1),(254,253,NULL,1,60,1),(255,253,NULL,1,23,1),(256,252,NULL,1,38,1),(257,NULL,'OR',0,NULL,1),(258,257,'OR',0,NULL,1),(259,258,NULL,1,38,1),(260,258,NULL,1,60,1),(261,257,NULL,1,23,1),(262,NULL,'OR',0,NULL,1),(263,262,NULL,1,95,1),(264,262,NULL,1,60,1),(265,NULL,'OR',0,NULL,1),(266,265,NULL,1,48,1),(267,265,NULL,1,96,1),(268,NULL,'OR',0,NULL,1),(269,268,'OR',0,NULL,1),(270,269,NULL,1,60,1),(271,269,NULL,1,38,1),(272,268,NULL,1,23,1),(273,NULL,'OR',0,NULL,1),(274,273,'OR',0,NULL,1),(275,274,NULL,1,60,1),(276,274,NULL,1,23,1),(277,273,NULL,1,38,1),(278,NULL,'AND',0,NULL,1),(279,278,'OR',0,NULL,1),(280,279,'OR',0,NULL,1),(281,280,NULL,1,38,1),(282,280,NULL,1,23,1),(283,279,NULL,1,60,1),(284,NULL,'AND',0,NULL,1),(285,284,'OR',0,NULL,1),(286,285,'OR',0,NULL,1),(287,286,NULL,1,38,1),(288,286,NULL,1,23,1),(289,285,NULL,1,60,1),(290,284,NULL,1,98,1),(291,NULL,NULL,1,99,1),(292,NULL,NULL,1,99,1),(293,NULL,NULL,1,22,1),(294,NULL,NULL,1,22,1),(297,NULL,NULL,1,101,1),(298,NULL,NULL,1,101,1),(299,NULL,NULL,1,115,1),(300,NULL,NULL,1,115,1),(301,NULL,NULL,1,117,1),(302,NULL,NULL,1,117,1),(303,NULL,NULL,1,122,1),(304,NULL,NULL,1,122,1),(305,NULL,NULL,1,124,1),(306,NULL,NULL,1,124,1),(307,NULL,NULL,1,28,1),(308,NULL,NULL,1,28,1),(309,NULL,'OR',0,NULL,1),(310,309,'OR',0,NULL,1),(311,310,NULL,1,38,1),(312,310,NULL,1,23,1),(313,309,NULL,1,60,1),(314,NULL,NULL,1,21,1),(315,NULL,NULL,1,21,1),(316,NULL,NULL,1,21,1),(317,NULL,NULL,1,21,1),(318,NULL,NULL,1,21,1),(319,NULL,NULL,1,21,1),(320,NULL,NULL,1,21,1),(321,NULL,NULL,1,21,1),(322,NULL,NULL,1,21,1),(323,NULL,NULL,1,21,1),(324,NULL,NULL,1,21,1),(325,NULL,NULL,1,21,1),(326,NULL,NULL,1,21,1),(327,NULL,NULL,1,21,1);
/*!40000 ALTER TABLE `prereq_coreq_formulas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `req_formulas`
--

DROP TABLE IF EXISTS `req_formulas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `req_formulas` (
  `rf_parent_id_pk_fk` int(11) NOT NULL,
  `rf_child_id_pk_fk` int(11) NOT NULL,
  `rf_is_positive` tinyint(1) NOT NULL,
  PRIMARY KEY (`rf_parent_id_pk_fk`,`rf_child_id_pk_fk`),
  KEY `rf_child_id_pk_fk` (`rf_child_id_pk_fk`),
  CONSTRAINT `req_formulas_ibfk_1` FOREIGN KEY (`rf_parent_id_pk_fk`) REFERENCES `requirements` (`req_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `req_formulas_ibfk_2` FOREIGN KEY (`rf_child_id_pk_fk`) REFERENCES `requirements` (`req_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `req_formulas`
--

LOCK TABLES `req_formulas` WRITE;
/*!40000 ALTER TABLE `req_formulas` DISABLE KEYS */;
INSERT INTO `req_formulas` VALUES (29,23,1),(29,30,1),(30,24,1),(30,31,1),(31,25,1),(31,32,1),(32,27,1),(32,28,1),(36,2,1),(36,37,1),(37,20,1),(37,38,1),(38,21,1),(38,22,1),(41,23,1),(41,45,1),(42,23,1),(42,43,1),(43,1,1),(43,2,1),(44,23,1),(44,45,1),(45,1,1),(45,2,1),(48,29,1),(48,41,1);
/*!40000 ALTER TABLE `req_formulas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirements`
--

DROP TABLE IF EXISTS `requirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirements` (
  `req_id_pk` int(10) NOT NULL AUTO_INCREMENT,
  `req_is_simple` tinyint(1) NOT NULL,
  `req_title` varchar(50) NOT NULL,
  `req_abbr` varchar(50) DEFAULT NULL,
  `req_number_of_courses` int(10) DEFAULT NULL,
  `req_is_visible` tinyint(1) NOT NULL,
  `req_junction_type` varchar(20) DEFAULT NULL,
  `req_cg_id_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`req_id_pk`),
  UNIQUE KEY `req_abbr` (`req_abbr`),
  KEY `formula_id` (`req_junction_type`),
  KEY `req_cg_id_fk` (`req_cg_id_fk`),
  CONSTRAINT `requirements_ibfk_1` FOREIGN KEY (`req_cg_id_fk`) REFERENCES `course_groups` (`cg_id_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirements`
--

LOCK TABLES `requirements` WRITE;
/*!40000 ALTER TABLE `requirements` DISABLE KEYS */;
INSERT INTO `requirements` VALUES (1,1,'Humanities: 300 level and above ','H3L03',1,1,NULL,4),(2,1,'Humanities: 100-200 level','H1L03',1,1,NULL,3),(4,1,'Technical Electives','TEE03',2,1,NULL,6),(5,1,'Software Electives','SOE03',2,1,NULL,7),(6,1,'Required Computer Science','RCS01',14,1,NULL,8),(16,1,'Required Math','REM01',6,1,NULL,26),(18,1,'Management - Mandatory','M-M05',1,1,NULL,27),(19,1,'Science/Math Electives','SCE01',2,1,NULL,37),(20,1,'Humanitites - Special','H-S05',1,1,NULL,28),(21,1,'CAL humanities','CAH02',2,1,NULL,38),(22,1,'Free humanities','FRH01',3,1,NULL,42),(23,1,'Science - Physics','S-P05',3,1,NULL,18),(24,1,'Science - Chemistry','S-C07',3,1,NULL,19),(25,1,'Chem & Bio','C&B01',3,1,NULL,20),(27,1,'Chem Bio 2','CB201',3,1,NULL,21),(28,1,'Physics & Bio','P&B01',3,1,NULL,22),(29,0,'Science sequences','SCS01',NULL,1,'OR',NULL),(30,0,'Science sequences',NULL,NULL,0,'OR',NULL),(31,0,'Science sequences',NULL,NULL,0,'OR',NULL),(32,0,'Science sequences',NULL,NULL,0,'OR',NULL),(36,0,'Humanities all',NULL,NULL,0,'AND',NULL),(37,0,'Humanities all',NULL,NULL,0,'AND',NULL),(38,0,'Humanities all',NULL,NULL,0,'AND',NULL),(41,0,'complex demo requirement','CDR01',NULL,1,'OR',NULL),(42,0,'complex demo requirement',NULL,NULL,0,'OR',NULL),(43,0,'complex demo requirement',NULL,NULL,0,'OR',NULL),(44,0,'complex demo requirement',NULL,NULL,0,'OR',NULL),(45,0,'complex demo requirement',NULL,NULL,0,'AND',NULL),(47,0,'another complex req',NULL,NULL,0,'OR',NULL),(48,0,'demo','DEM01',NULL,1,'AND',NULL);
/*!40000 ALTER TABLE `requirements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semesters`
--

DROP TABLE IF EXISTS `semesters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `semesters` (
  `semster_id_pk` int(11) NOT NULL AUTO_INCREMENT,
  `semster_name` varchar(20) NOT NULL,
  PRIMARY KEY (`semster_id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semesters`
--

LOCK TABLES `semesters` WRITE;
/*!40000 ALTER TABLE `semesters` DISABLE KEYS */;
INSERT INTO `semesters` VALUES (1,'Fall'),(2,'Spring'),(3,'Summer I'),(4,'Summer II'),(5,'Winter');
/*!40000 ALTER TABLE `semesters` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-04-18 19:36:21
