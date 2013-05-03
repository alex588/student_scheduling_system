CREATE DATABASE student_scheduling_system;
USE student_scheduling_system;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


CREATE TABLE IF NOT EXISTS courses (
  course_id_pk int(10) NOT NULL AUTO_INCREMENT,
  course_prefix_id_fk int(11) NOT NULL,
  course_number int(11) NOT NULL,
  course_title varchar(50) NOT NULL,
  course_credits int(11) NOT NULL,
  course_level int(11) NOT NULL,
  course_prereq_formula_id_fk int(10) DEFAULT NULL,
  course_coreq_formula_id_fk int(10) DEFAULT NULL,
  PRIMARY KEY (course_id_pk),
  KEY course_prefix_id_fk (course_prefix_id_fk),
  KEY course_prereq_formula_id_fk (course_prereq_formula_id_fk),
  KEY course_coreq_formula_id_fk (course_coreq_formula_id_fk)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS course_availability (
  ca_id int(11) NOT NULL AUTO_INCREMENT,
  ca_semester_id_fk int(11) NOT NULL,
  ca_course_id_fk int(11) NOT NULL,
  ca_year int(11) DEFAULT NULL,
  ca_location varchar(20) NOT NULL,
  PRIMARY KEY (ca_id),
  KEY ca_semester_id_fk (ca_semester_id_fk),
  KEY ca_course_id_fk (ca_course_id_fk)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS course_groups (
  cg_id_pk int(10) NOT NULL AUTO_INCREMENT,
  cg_title varchar(50) NOT NULL,
  cg_abbr varchar(50) DEFAULT NULL,
  cg_is_simple tinyint(1) NOT NULL,
  cg_is_visible tinyint(1) NOT NULL DEFAULT '1',
  cg_junction_type varchar(20) DEFAULT NULL,
  PRIMARY KEY (cg_id_pk),
  UNIQUE KEY cg_abbr (cg_abbr)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS course_groups_formulas (
  cgf_parent_id_pk_fk int(10) NOT NULL,
  cgf_child_id_pk_fk int(11) NOT NULL,
  cgf_is_positive tinyint(1) NOT NULL,
  PRIMARY KEY (cgf_parent_id_pk_fk,cgf_child_id_pk_fk),
  KEY cgf_child_id_pk_fk (cgf_child_id_pk_fk)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS course_grp_courses (
  course_group_id_fk int(10) NOT NULL,
  course_id_fk int(10) NOT NULL,
  KEY course_id (course_id_fk),
  KEY course_group_id_fk (course_group_id_fk)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS degree_programs (
  dp_id_pk int(10) NOT NULL AUTO_INCREMENT,
  dp_title varchar(50) NOT NULL,
  PRIMARY KEY (dp_id_pk)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS degree_program_reqs (
  requirement_id_fk int(11) NOT NULL,
  degree_program_id_fk int(11) NOT NULL,
  KEY requirements_id (requirement_id_fk),
  KEY degree_programs_id (degree_program_id_fk)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS prefixes (
  prefix_id_pk int(11) NOT NULL AUTO_INCREMENT,
  prefix_title varchar(20) NOT NULL,
  prefix_full_name varchar(20) NOT NULL,
  PRIMARY KEY (prefix_id_pk)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS prereq_coreq_formulas (
  pcf_id_pk int(10) NOT NULL AUTO_INCREMENT,
  pcf_parent_id_fk int(10),
  pcf_junction_type varchar(10) NULL,
  pcf_is_course tinyint(1) NOT NULL,
  pcf_course_id_fk int(11) DEFAULT NULL,
  pcf_is_positive tinyint(1) NOT NULL,
  PRIMARY KEY (pcf_id_pk),
  KEY course_id (pcf_course_id_fk),
  KEY pcf_parent_id_fk (pcf_parent_id_fk)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS requirements (
  req_id_pk int(10) NOT NULL AUTO_INCREMENT,
  req_is_simple tinyint(1) NOT NULL,
  req_title varchar(50) NOT NULL,
  req_abbr varchar(50) DEFAULT NULL,
  req_number_of_courses int(10) DEFAULT NULL,
  req_is_visible tinyint(1) NOT NULL,
  req_junction_type varchar(20) DEFAULT NULL,
  req_cg_id_fk int(11) DEFAULT NULL,
  PRIMARY KEY (req_id_pk),
  UNIQUE KEY req_abbr (req_abbr),
  KEY formula_id (req_junction_type),
  KEY req_cg_id_fk (req_cg_id_fk)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS req_formulas (
  rf_parent_id_pk_fk int(11) NOT NULL,
  rf_child_id_pk_fk int(11) NOT NULL,
  rf_is_positive tinyint(1) NOT NULL,
  PRIMARY KEY (rf_parent_id_pk_fk,rf_child_id_pk_fk),
  KEY rf_child_id_pk_fk (rf_child_id_pk_fk)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS semesters (
  semster_id_pk int(11) NOT NULL AUTO_INCREMENT,
  semster_name varchar(20) NOT NULL,
  PRIMARY KEY (semster_id_pk)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


ALTER TABLE `courses`
  ADD CONSTRAINT courses_ibfk_4 FOREIGN KEY (course_coreq_formula_id_fk) REFERENCES prereq_coreq_formulas (pcf_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT courses_ibfk_2 FOREIGN KEY (course_prefix_id_fk) REFERENCES prefixes (prefix_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT courses_ibfk_3 FOREIGN KEY (course_prereq_formula_id_fk) REFERENCES prereq_coreq_formulas (pcf_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `course_availability`
  ADD CONSTRAINT course_availability_ibfk_1 FOREIGN KEY (ca_semester_id_fk) REFERENCES semesters (semster_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT course_availability_ibfk_2 FOREIGN KEY (ca_course_id_fk) REFERENCES courses (course_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `course_groups_formulas`
  ADD CONSTRAINT course_groups_formulas_ibfk_1 FOREIGN KEY (cgf_parent_id_pk_fk) REFERENCES course_groups (cg_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT course_groups_formulas_ibfk_2 FOREIGN KEY (cgf_child_id_pk_fk) REFERENCES course_groups (cg_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `course_grp_courses`
  ADD CONSTRAINT course_grp_courses_ibfk_3 FOREIGN KEY (course_group_id_fk) REFERENCES course_groups (cg_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT course_grp_courses_ibfk_4 FOREIGN KEY (course_id_fk) REFERENCES courses (course_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `degree_program_reqs`
  ADD CONSTRAINT degree_program_reqs_ibfk_1 FOREIGN KEY (requirement_id_fk) REFERENCES requirements (req_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT degree_program_reqs_ibfk_2 FOREIGN KEY (degree_program_id_fk) REFERENCES degree_programs (dp_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `prereq_coreq_formulas`
  ADD CONSTRAINT prereq_coreq_formulas_ibfk_1 FOREIGN KEY (pcf_course_id_fk) REFERENCES courses (course_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT prereq_coreq_formulas_ibfk_2 FOREIGN KEY (pcf_parent_id_fk) REFERENCES prereq_coreq_formulas (pcf_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `requirements`
  ADD CONSTRAINT requirements_ibfk_1 FOREIGN KEY (req_cg_id_fk) REFERENCES course_groups (cg_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;
  
ALTER TABLE `req_formulas`
  ADD CONSTRAINT req_formulas_ibfk_1 FOREIGN KEY (rf_parent_id_pk_fk) REFERENCES requirements (req_id_pk) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT req_formulas_ibfk_2 FOREIGN KEY (rf_child_id_pk_fk) REFERENCES requirements (req_id_pk) ON DELETE CASCADE ON UPDATE CASCADE;
