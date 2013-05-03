package test.controllers;

import java.util.ArrayList;
import java.util.List;

import model.CoRequisite;
import model.ComplexRequirement;
import model.Course;
import model.Course.Location;
import model.CourseGroup;
import model.DegreeProgram;
import model.GeneralSettings;
import model.Junction;
import model.PreRequisite;
import model.Prefix;
import model.Requirement;
import model.SchedulingResult;
import model.Semester;
import model.SimpleCourseGroup;
import model.SimpleRequirement;
import model.StudentDesire;
import model.StudyPlan;
import model.Term;

import org.junit.Assert;
import org.junit.Test;
import controllers.solver.ConstraintProcessor;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * 
 * @author Mihir Daptardar, Alexey Tregubov.
 * 
 */
public class TestAlgorithm {
	// Course ca, cb, cc, cd, ce, cf, cg, ch, ci, cj, ck, c;

	@Test
	public void initData() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				// Create Semester

				// Create Prefix
				Prefix prefix = Prefix.create("Test Prefix", "TP");

				// Create course
				Course ca = Course.create(prefix, 101, "a", 3, 100, null, null);
				Course cb = Course.create(prefix, 102, "b", 3, 100, null, null);
				Course cc = Course.create(prefix, 103, "c", 3, 100, null, null);
				Course cd = Course.create(prefix, 104, "d", 3, 100, null, null);
				Course ce = Course.create(prefix, 105, "e", 3, 100, null, null);
				Course cf = Course.create(prefix, 106, "f", 3, 100, null, null);
				Course cg = Course.create(prefix, 107, "g", 3, 100, null, null);
				Course ch = Course.create(prefix, 108, "h", 3, 100, null, null);
				Course ci = Course.create(prefix, 109, "i", 3, 100, null, null);
				Course cj = Course.create(prefix, 110, "j", 3, 100, null, null);
				Course ck = Course.create(prefix, 111, "k", 3, 100, null, null);
				Course cl = Course.create(prefix, 112, "l", 3, 100, null, null);
				Course cp = Course.create(prefix, 113, "p", 3, 100, null, null);
				Course cq = Course.create(prefix, 114, "q", 3, 100, null, null);
				Course cr = Course.create(prefix, 115, "r", 3, 100, null, null);

				// Set Requisites
				cc.setPrereq(PreRequisite.createLeaf(true, null, cb));

				PreRequisite kroot = PreRequisite.createNode(true, null,
						Junction.AND);
				PreRequisite.createLeaf(true, kroot, cq);
				PreRequisite.createLeaf(true, kroot, cj);
				ck.setPrereq(kroot);

				PreRequisite droot = PreRequisite.createNode(true, null,
						Junction.AND);
				PreRequisite.createLeaf(true, droot, cl);
				PreRequisite.createLeaf(true, droot, cf);
				cd.setPrereq(droot);

				cg.setCoreq(CoRequisite.createLeaf(true, null, ch));

				CoRequisite iroot = CoRequisite.createNode(true, null,
						Junction.OR);
				CoRequisite.createLeaf(true, iroot, cp);
				CoRequisite.createLeaf(true, iroot, cq);
				ci.setCoreq(iroot);

				// Set Availabilty

				ca.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				cc.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				ce.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				cf.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				ch.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				ci.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				ck.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				cl.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				cq.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);

				cb.setCourseAvailability(Semester.SPRING, null, true,
						Location.ONCAMPUS);
				cd.setCourseAvailability(Semester.SPRING, null, true,
						Location.ONCAMPUS);
				cg.setCourseAvailability(Semester.SPRING, null, true,
						Location.ONCAMPUS);
				cj.setCourseAvailability(Semester.SPRING, null, true,
						Location.ONCAMPUS);
				cp.setCourseAvailability(Semester.SPRING, null, true,
						Location.ONCAMPUS);
				cr.setCourseAvailability(Semester.SPRING, null, true,
						Location.ONCAMPUS);

				// Create Course Groups

				SimpleCourseGroup scg1 = SimpleCourseGroup.create("scg1",
						"scg100");
				SimpleCourseGroup scg2 = SimpleCourseGroup.create("scg2",
						"scg200");
				SimpleCourseGroup scg3 = SimpleCourseGroup.create("scg3",
						"scg300");
				SimpleCourseGroup scg4 = SimpleCourseGroup.create("scg4",
						"scg400");

				// Add Courses to course groups

				scg1.addCourse(ca);
				scg1.addCourse(cb);
				scg1.addCourse(cc);

				scg2.addCourse(cd);
				scg2.addCourse(ce);
				scg2.addCourse(cf);

				scg3.addCourse(cg);
				scg3.addCourse(ch);
				scg3.addCourse(ci);

				scg4.addCourse(cj);
				scg4.addCourse(ck);
				scg4.addCourse(cl);

				// Create Degree Requirements
				SimpleRequirement sr1 = SimpleRequirement.create("sr1",
						"sr100", 2, scg1);
				SimpleRequirement sr2 = SimpleRequirement.create("sr2",
						"sr200", 1, scg2);

				ComplexRequirement cr1 = ComplexRequirement.create("cr1",
						"cr100", true, Junction.AND);
				SimpleRequirement sr3 = SimpleRequirement.create("sr3",
						"sr300", 2, scg3);
				SimpleRequirement sr4 = SimpleRequirement.create("sr4",
						"sr400", 3, scg4);

				cr1.addChildNode(sr3, true);
				cr1.addChildNode(sr4, true);

				List<Requirement> reqList = new ArrayList<Requirement>();
				reqList.add(sr1);
				reqList.add(sr2);
				reqList.add(cr1);

				// Create Terms
				Term s1 = Term.create(Semester.FALL, 2012);
				Term s2 = Term.create(Semester.SPRING, 2013);
				Term s3 = Term.create(Semester.FALL, 2013);
				Term s4 = Term.create(Semester.SPRING, 2014);

				// General Constraints
				/*
				 * List<Term> firstTerm = new ArrayList<>(); firstTerm.add(s1);
				 */
				List<Term> listTerms = new ArrayList<>();
				listTerms.add(s1);
				listTerms.add(s2);
				listTerms.add(s3);
				listTerms.add(s4);

				List<Integer> maxCourses = new ArrayList<>();
				maxCourses.add(4); // courses for 1st term
				maxCourses.add(3); // courses for 2nd term
				maxCourses.add(3); // courses for 3rd term
				maxCourses.add(3); // courses for 4th term

				List<Integer> minCourses = new ArrayList<>();
				minCourses.add(0); // courses for 1st term
				minCourses.add(0); // courses for 2nd term
				minCourses.add(0); // courses for 3rd term
				minCourses.add(0); // courses for 4th term

				List<Integer> maxCredits = new ArrayList<>();
				maxCredits.add(16);
				maxCredits.add(16);
				maxCredits.add(16);
				maxCredits.add(16);

				List<Integer> minCredits = new ArrayList<>();
				minCredits.add(0);
				minCredits.add(0);
				minCredits.add(0);
				minCredits.add(0);

				GeneralSettings settings = GeneralSettings.create(maxCourses,
						minCourses, maxCredits, minCredits, listTerms);

				// Student Desires
				StudentDesire sd2 = StudentDesire.create(ch, true, null, null,
						Location.BOTH);
				StudentDesire sd3 = StudentDesire.create(cd, false, null, s2,
						Location.BOTH);
				StudentDesire sd4 = StudentDesire.create(ci, false, null, null,
						Location.BOTH);

				List<StudentDesire> desireList = new ArrayList<StudentDesire>();
				desireList.add(sd2);
				desireList.add(sd3);
				desireList.add(sd4);

				// Degree Preogram
				DegreeProgram dp = DegreeProgram.create("TestDp", reqList);

				// Study Plan
				SchedulingResult result = null;

				result = ConstraintProcessor.solve(dp, desireList, settings);
				// check result

				StudyPlan plan = result.getStudyPlan();
				Assert.assertTrue(result.getIsValidPlan());
				List<Course> courses = plan.getCourseListForTerm(s1);
				Assert.assertFalse(courses.isEmpty());

				// ...................Cleanup STARTS..................

				Course.delete(ca.getId());
				Course.delete(cb.getId());
				Course.delete(cc.getId());
				Course.delete(cd.getId());
				Course.delete(ce.getId());
				Course.delete(cf.getId());
				Course.delete(cg.getId());
				Course.delete(ch.getId());
				Course.delete(ci.getId());
				Course.delete(cj.getId());
				Course.delete(ck.getId());
				Course.delete(cl.getId());
				Course.delete(cp.getId());
				Course.delete(cq.getId());
				Course.delete(cr.getId());

				Requirement.delete(sr1);
				Requirement.delete(sr2);
				Requirement.delete(sr3);
				Requirement.delete(sr4);
				Requirement.delete(cr1);

				CourseGroup.delete(scg1);
				CourseGroup.delete(scg2);
				CourseGroup.delete(scg3);
				CourseGroup.delete(scg4);
			}
		});
	}
}
