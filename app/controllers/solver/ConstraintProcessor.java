package controllers.solver;

import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.DegreeProgram;
import model.GeneralSettings;
import model.Requirement;
import model.StudentDesire;
import model.StudyPlan;

public class ConstraintProcessor {

	public static StudyPlan solve(DegreeProgram program,
			List<StudentDesire> desires, GeneralSettings settings) {
		StudyPlanMatrix matrix = new StudyPlanMatrix(settings.getTerms(), collectCourses(program));
		
		//TODO: IN PROGRESS:
		
		return matrix.convertToStudyPlan();
	}
	
	private static List<Course> collectCourses(DegreeProgram program){
		List<Course> courses = new ArrayList<Course>(); 
		for (Requirement req : program.getRequirements()){
			courses.addAll(req.getCourses());
		}
		return courses;
	}
}
