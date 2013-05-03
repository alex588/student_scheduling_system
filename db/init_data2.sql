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
(4, 'HPL', 'Philosophy'),
(5, 'SOC', 'Software Engineering'),
(6, 'MA', 'Mathematics'),
(7, 'BT', 'Business and Technology'),
(8, 'HUM', 'Humanities'),
(9, 'HLI',  'Humanities Literature'),
(10, 'HAR', 'Humanities Arts'),
(11, 'HHS', 'Humanities History'),
(12, 'CAL','College of Arts and Letters'),
(13, 'HSS', 'Humanities and Social Sciences');

INSERT INTO semesters (semster_id_pk, semster_name) VALUES
(1, 'Fall'),
(2, 'Spring'),
(3, 'Summer I'),
(4, 'Summer II'),
(5, 'Winter');

INSERT INTO prereq_coreq_formulas (pcf_id_pk, pcf_parent_id_fk, pcf_junction_type, pcf_is_course, pcf_course_id_fk, pcf_is_positive) VALUES
(4, NULL, 'OR', 0, NULL, 1),
(9, NULL, 'AND', 0, NULL, 1),
(18, NULL, 'AND', 0, NULL, 1);

INSERT INTO courses (course_id_pk, course_prefix_id_fk, course_number, course_title, course_credits, course_level, course_prereq_formula_id_fk, course_coreq_formula_id_fk) VALUES
(1, 1, 111, 'Mechanics', 3, 100, NULL, NULL),
(2, 1, 112, 'E&M', 3, 100, NULL, NULL),
(3, 2, 115, 'Gen. Chem. I', 3, 100, NULL, NULL),
(5, 2, 116, 'Gen. Chem II', 3, 100, NULL, NULL),
(6, 2, 281, 'Bio. Biotech', 3, 200, NULL, NULL),
(7, 2, 117, 'Laboratory', 1, 100, NULL, NULL),
(8, 2, 282, 'Laboratory', 1, 200, NULL, NULL),
(9, 1, 221, 'Laboratory', 1, 200, NULL, NULL),
(10, 3, 181, 'Introduction to Computer Science Honors I', 3, 100, NULL, NULL),
(11, 3, 284, 'Data Structures', 3, 200, 4, NULL),
(12, 3, 135, 'Discrete Structures', 3, 100, NULL, NULL),
(13, 3, 115, 'Introduction to Computer Science', 4, 100, NULL, NULL),
(14, 3, 347, 'Software Development Process', 3, 300, 9, NULL),
(15, 3, 105, 'Introduction to Scientific Computing', 3, 100, NULL, NULL),
(16, 4, 111, 'Philosophy I', 3, 100, NULL, NULL),
(17, 4, 112, 'Knowledge, Reality and Nature', 3, 100, NULL, NULL),
(18, 4, 339, 'Ethics', 3, 300, NULL, NULL),
(19, 4, 348, 'Aesthetics', 3, 300, NULL, NULL),
(20, 3, 146, 'Web Fundamentals', 3, 100, NULL, NULL),
(21, 3, 334, 'Automata & Computations', 3, 300, 18, NULL),
(22, 3, 383, 'Computer Organization & Programming', 3, 300, NULL, NULL),
(23, 3, 385, 'Algorithms', 4, 300, NULL, NULL),
(24, 3, 516, 'Compiler Design', 3, 500, NULL, NULL),
(25, 3, 521, 'TCP/IP Networks', 3, 500, NULL, NULL),
(26, 3, 522, 'Mobile Systems and Applications', 3, 500, NULL, NULL),
(27, 3, 541, 'Artifical Intelligence', 3, 500, NULL, NULL),
(28, 5, 606, 'Introduction to Developing Internet Applications', 3, 600, NULL, NULL),
(29, 5, 521, 'Software Requirements Analysis and Engineering', 3, 500, NULL, NULL),
(30, 5, 565, 'Software Architecture and Component-Based Design', 3, 500, NULL, NULL),
(31, 5, 567, 'Software Testing, Quality Assurance, and Maintenance', 3, 500, NULL, NULL),
(32, 5, 605, 'Intoduction to Service Oriented Computing', 3, 600, NULL, NULL);

INSERT INTO prereq_coreq_formulas (pcf_id_pk, pcf_parent_id_fk, pcf_junction_type, pcf_is_course, pcf_course_id_fk, pcf_is_positive) VALUES
(5, 4, '', 1, 10, 1),
(6, 4, 'AND', 0, NULL, 1),
(7, 6, '', 1, 11, 1),
(8, 6, '', 1, 12, 1),
(10, 9, '', 1, 11, 1),
(11, 9, '', 1, 12, 1),
(12, NULL, '', 1, 23, 1),
(13, NULL, '', 1, 23, 1),
(14, NULL, '', 1, 23, 1),
(15, NULL, '', 1, 32, 1),
(16, NULL, '', 1, 28, 1),
(17, NULL, '', 1, 13, 1),
(19, 18, '', 1, 13, 1),
(20, NULL, '', 1, 11, 1),
(21, NULL, '', 1, 13, 1),
(22, NULL, '', 1, 13, 1),
(23, NULL, '', 1, 7, 1),
(24, 18, '', 1, 12, 1);

UPDATE courses set course_prereq_formula_id_fk = 12 where course_id_pk = 24;
UPDATE courses set course_prereq_formula_id_fk = 13 where course_id_pk = 26;
UPDATE courses set course_prereq_formula_id_fk = 14 where course_id_pk = 27;
UPDATE courses set course_prereq_formula_id_fk = 15 where course_id_pk = 28;
UPDATE courses set course_prereq_formula_id_fk = 16 where course_id_pk = 29;
UPDATE courses set course_prereq_formula_id_fk = 17 where course_id_pk = 11;
UPDATE courses set course_prereq_formula_id_fk = 20 where course_id_pk = 25;
UPDATE courses set course_prereq_formula_id_fk = 21 where course_id_pk = 23;
UPDATE courses set course_prereq_formula_id_fk = 22 where course_id_pk = 32;
UPDATE courses set course_coreq_formula_id_fk = 23 where course_id_pk = 6;





INSERT INTO course_availability (ca_id, ca_semester_id_fk, ca_course_id_fk, ca_year, ca_location) VALUES
(1, 1, 13, NULL, 'ONCAMPUS'),
(2, 2, 13, NULL, 'ONCAMPUS'),
(3, 3, 15, NULL, 'ONLINE'),
(4, 1, 12, NULL, 'ONCAMPUS'),
(5, 2, 12, NULL, 'ONCAMPUS'),
(6, 1, 20, NULL, 'ONCAMPUS'),
(7, 1, 11, NULL, 'ONCAMPUS'),
(8, 2, 11, NULL, 'ONCAMPUS'),
(9, 1, 21, NULL, 'ONCAMPUS'),
(10, 2, 14, NULL, 'ONCAMPUS'),
(11, 1, 22, NULL, 'ONCAMPUS'),
(12, 1, 23, NULL, 'ONCAMPUS'),
(13, 2, 23, NULL, 'ONCAMPUS'),
(14, 3, 23, NULL, 'ONCAMPUS'),
(15, 1, 24, NULL, 'ONCAMPUS'),
(16, 1, 25, NULL, 'ONCAMPUS'),
(17, 2, 25, NULL, 'ONCAMPUS'),
(18, 2, 26, NULL, 'ONCAMPUS'),
(19, 1, 27, NULL, 'ONCAMPUS'),
(20, 2, 28, NULL, 'ONCAMPUS'),
(21, 1, 16, NULL, 'ONCAMPUS'),
(22, 2, 16, NULL, 'ONCAMPUS'),
(23, 1, 17, NULL, 'ONCAMPUS'),
(24, 2, 17, NULL, 'ONCAMPUS'),
(25, 1, 18, NULL, 'ONCAMPUS'),
(26, 2, 18, NULL, 'ONCAMPUS'),
(27, 1, 19, NULL, 'ONCAMPUS'),
(28, 2, 19, NULL, 'ONCAMPUS'),
(29, 1, 32, NULL, 'ONCAMPUS'),
(30, 2, 22, NULL, 'ONCAMPUS'),
(31, 1, 14, NULL, 'ONCAMPUS'),
(32, 1, 28, NULL, 'ONCAMPUS'),
(33, 2, 32, NULL, 'ONCAMPUS'),
(34, 2, 24, NULL, 'ONCAMPUS'),
(35, 1, 26, NULL, 'ONCAMPUS'),
(36, 2, 27, NULL, 'ONCAMPUS'),
(37, 2, 21, NULL, 'ONCAMPUS');


INSERT INTO course_groups (cg_id_pk, cg_title, cg_abbr, cg_is_simple, cg_is_visible, cg_junction_type) VALUES
(1, 'Science 1', 'SC102', 1, 1, NULL),
(2, 'Science 2', 'SC202', 1, 1, NULL),
(3, 'Humanities: 100-200 level', 'H1L02', 1, 1, NULL),
(4, 'Humanities: 300 level or higher', 'H3L02', 1, 1, NULL),
(5, 'Humanities', 'HUM02', 0, 1, 'AND'),
(6, 'Technical Electives', 'TEE02', 1, 1, NULL),
(7, 'Software Electives', 'SOE02', 1, 1, NULL),
(8, 'Required Computer Science', 'RCS02', 1, 1, NULL);



INSERT INTO course_groups_formulas (cgf_parent_id_pk_fk, cgf_child_id_pk_fk, cgf_is_positive) VALUES
(5, 3, 1),
(5, 4, 1);

INSERT INTO course_grp_courses (course_group_id_fk, course_id_fk) VALUES
(2, 2),
(2, 5),
(2, 6),
(3, 16),
(3, 17),
(4, 18),
(4, 19),
(6, 24),
(6, 25),
(6, 26),
(6, 27),
(7, 28),
(7, 32),
(7, 29),
(8, 13),
(8, 12),
(8, 11),
(8, 21),
(8, 22),
(8, 23),
(8, 14),
(1, 3);


INSERT INTO requirements (req_id_pk, req_is_simple, req_title, req_abbr, req_number_of_courses, req_is_visible, req_junction_type, req_cg_id_fk) VALUES
(1, 1, 'Humanities: 300 level and above ', 'HUM300', 1, 1, NULL, 4),
(2, 1, 'Humanities: 100-200 level', 'HUM100', 1, 1, NULL, 3),
(3, 0, 'Humanities', 'HUM', 0, 1, 'OR', NULL),
(4, 1, 'Technical Electives', 'Tech', 2, 1, NULL, 6),
(5, 1, 'Software Electives', 'Soft', 2, 1, NULL, 7),
(6, 1, 'Required Computer Science', 'CSReq', 7, 1, NULL, 8);

INSERT INTO degree_programs (dp_id_pk, dp_title) VALUES
(1, 'CS_Entering_2011_Starting_With_CS115'),
(2, 'CS_Test');

INSERT INTO degree_program_reqs (requirement_id_fk, degree_program_id_fk) VALUES
(3, 1),
(3, 2),
(4, 2),
(5, 2),
(6, 2);


INSERT INTO req_formulas (rf_parent_id_pk_fk, rf_child_id_pk_fk, rf_is_positive) VALUES
(3, 1, 1),
(3, 2, 1);


