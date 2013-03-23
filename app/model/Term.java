package model;

public class Term {
	Semester semester;
	Integer year;

	public Term() {

	}

	public static Term create(Semester semester, Integer year) {
		return new Term(semester, year);
	}
	
	Term(Semester semester, Integer year) {
		super();
		this.semester = semester;
		this.year = year;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		return this.semester.hashCode() + year;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Term) {
			Term anotherTerm = (Term) o;
			if ((this.year == anotherTerm.year)
					&& this.semester.equals(anotherTerm.semester))
				return true;
			else
				return false;
		}
		return false;
	}
}
