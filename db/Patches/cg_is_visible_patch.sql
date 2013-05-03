use student_scheduling_system;

ALTER TABLE course_groups ADD cg_is_visible TINYINT( 1 ) NOT NULL DEFAULT '1' AFTER cg_is_simple;