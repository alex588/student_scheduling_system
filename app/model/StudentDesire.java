package model;

public class StudentDesire {
	private Course course;
	private Term term;
	boolean isTaken;
	
	private StudentDesire(){
		
	}
	
	public static StudentDesire create(Course course, Term term, boolean isTaken) {
		StudentDesire desire = new StudentDesire();
		desire.course = course;
		desire.term = term;
		desire.isTaken = isTaken;
		return desire;
	}
	
	public Course getCourse() {
		return course;
	}
	public Term getTerm() {
		return term;
	}
	public boolean isTaken() {
		return isTaken;
	}	
	
}
