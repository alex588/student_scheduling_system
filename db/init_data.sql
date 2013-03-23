use student_scheduling_system;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

INSERT INTO prefixes (prefix_id_pk, prefix_title, prefix_full_name) VALUES
(1, 'PEP', 'Physics'),
(2, 'CH', 'Chemistry'),
(3, 'CS', 'Computer Science'),
(4, 'HPL', 'Philosophy');

INSERT INTO semesters (semster_id_pk, semster_name) VALUES
(1, 'Fall'),
(2, 'Spring'),
(3, 'Summer I'),
(4, 'Summer II'),
(5, 'Winter'),
(6, 'Summer');

INSERT INTO prereq_coreq_formulas (pcf_id_pk, pcf_parent_id_fk, pcf_junction_type, pcf_is_course, pcf_course_id_fk, pcf_is_positive) VALUES
/*(0,0,'',0,NULL,1),*/
(4, NULL, 'OR', 0, NULL, 1);

INSERT INTO courses (course_id_pk, course_prefix_id_fk, course_number, course_title, course_credits, course_level, course_prereq_formula_id_fk, course_coreq_formula_id_fk) VALUES
(1, 1, 111, 'Mechanics', 3, 100, NULL, NULL),
(2, 1, 112, 'E&M', 3, 100, NULL, NULL),
(3, 2, 115, 'Gen. Chem. I', 3, 100, NULL, NULL),
(5, 2, 116, 'Gen. Chem II', 3, 100, NULL, NULL),
(6, 2, 281, 'Bio. Biotech', 3, 200, NULL, NULL),
(7, 2, 117, '', 1, 100, NULL, NULL),
(8, 2, 282, '', 1, 200, NULL, NULL),
(9, 1, 221, '', 1, 200, NULL, NULL),
(10, 3, 181, 'Introduction to Computer Science Honors I', 3, 100, NULL, NULL),
(11, 3, 284, 'Data Structures', 3, 200, 4, NULL),
(12, 3, 135, 'Discrete Structures', 3, 100, NULL, NULL),
(13, 3, 115, 'Introduction to Computer Science', 3, 100, NULL, NULL),
(14, 3, 347, 'Software Development Process', 3, 300, NULL, NULL),
(15, 3, 105, 'Introduction to Scientific Computing', 3, 100, NULL, NULL),
(16, 4, 111, 'Philosophy I', 3, 100, NULL, NULL),
(17, 4, 112, 'Knowledge, Reality and Nature', 3, 100, NULL, NULL),
(18, 4, 339, 'Ethics', 3, 300, NULL, NULL),
(19, 4, 348, 'Aesthetics', 3, 300, NULL, NULL);

INSERT INTO prereq_coreq_formulas (pcf_id_pk, pcf_parent_id_fk, pcf_junction_type, pcf_is_course, pcf_course_id_fk, pcf_is_positive) VALUES
(5, 4, '', 1, 10, 1),
(6, 4, 'AND', 0, NULL, 1),
(7, 6, '', 1, 11, 1),
(8, 6, '', 1, 12, 1),
(9, NULL, '', 0, NULL, 0);



INSERT INTO course_availability (ca_semester_id_pk_fk, ca_course_id_pk_fk, ca_year, ca_location) VALUES
(1, 13, 2008, 'ONCAMPUS'),
(2, 13, 2008, 'ONCAMPUS'),
(6, 15, 2009, 'ONLINE');

INSERT INTO course_groups (cg_id_pk, cg_title, cg_is_simple, cg_junction_type) VALUES
(1, 'Science I', 1, ''),
(2, 'Science II', 1, ''),
(3, 'Humanities: 100-200 level ', 1, ''),
(4, 'Humanities: 300 level or higher', 1, ''),
(5, 'Humanities', 0, 'AND');

INSERT INTO course_groups_formulas (cgf_parent_id_pk_fk, cgf_child_id_pk_fk, cgf_is_positive) VALUES
(5, 3, 1),
(5, 4, 1);

INSERT INTO course_grp_courses (course_group_id_fk, course_id_fk) VALUES
(1, 1),
(1, 3),
(2, 2),
(2, 5),
(2, 6),
(3, 16),
(3, 17),
(4, 18),
(4, 19);

INSERT INTO requirements (req_id_pk, req_is_simple, req_title, req_number_of_courses, req_is_visible, req_junction_type, req_cg_id_fk) VALUES
(1, 1, 'Humanities: 300 level and above ', 1, 1, NULL, 4),
(2, 1, 'Humanities: 100-200 level', 1, 1, NULL, 3),
(3, 0, 'Humanities', 0, 1, 'AND', NULL);

INSERT INTO degree_programs (dp_id_pk, dp_title) VALUES
(1, 'CS_Entering_2011_Starting_With_CS115');

INSERT INTO degree_program_reqs (requirement_id_fk, degree_program_id_fk) VALUES
(3, 1);


INSERT INTO req_formulas (rf_parent_id_pk_fk, rf_child_id_pk_fk, rf_is_positive) VALUES
(3, 1, 1),
(3, 2, 1);


