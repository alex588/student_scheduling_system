package controllers.solver;

import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.Semester;
import model.StudyPlan;
import model.Term;

class StudyPlanMatrix extends StudyPlan {
	
	private Cell[][] matrix;
	private List<Course> courses;
	
	StudyPlanMatrix(List<Term> terms, List<Course> courses){
		super();	
		this.terms = terms;
		this.matrix = new Cell[terms.size()][courses.size()];
	}	
	
	public StudyPlan convertToStudyPlan(){
		//FIXME: replace this temporary stub
		this.scheduleMatrix = new ArrayList<List<Course>>(2);
		List<Course> sem1 = new ArrayList<Course>(2);
		sem1.add(Course.getById(10));
		sem1.add(Course.getById(11));
		this.scheduleMatrix.add(sem1);
		List<Course> sem2 = new ArrayList<Course>(2);
		sem2.add(Course.getById(12));
		sem2.add(Course.getById(13));
		this.scheduleMatrix.add(sem2);
		this.terms = new ArrayList<Term>(2);
		this.terms.add(Term.create(Semester.FALL, 2012));
		this.terms.add(Term.create(Semester.SPRING, 2013));
		return this;
	}
}
