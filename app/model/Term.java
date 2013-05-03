package model;

/**
 * @author Alexey Tregubov
 * 
 */
public class Term {
	private Semester semester;
	private Integer year;

	protected Term() {
		super();
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

	public Integer getYear() {
		return year;
	}

	@Override
	public int hashCode() {
		return this.semester.hashCode() + year;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Term) {
			Term anotherTerm = (Term) o;
			if (((this.year != null) && (this.year.equals(anotherTerm.year)) || (this.year == anotherTerm.year))
					&& ((this.semester != null && this.semester
							.equals(anotherTerm.semester)) || (this.semester == anotherTerm.semester)))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return (this.semester == null ? "null" : this.semester.toString())
				+ " " + this.year;
	}

	public String toShortString() {
		if (semester == null || year == null)
			return "   ";
		return (semester == null ? " " : semester.toShortString()
				+ (year == null ? "  " : year % 100));
	}
}
