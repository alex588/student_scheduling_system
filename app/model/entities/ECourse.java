package model.entities;

import java.util.List;

import play.db.ebean.Model;
import javax.persistence.*;

@Entity
@Table(name = "courses")
public class ECourse extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "course_id_pk")
	public Integer course_id;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="course_prefix_id_fk")
	public EPrefix course_prefix;
	
	public Integer course_number;
	public String course_title;
	public Integer course_credits;
	public Integer course_level;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_prereq_formula_id_fk")
	public EPrereqCoreqFormula prereq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_coreq_formula_id_fk")
	public EPrereqCoreqFormula coreq;

	@OneToMany(mappedBy = "course")
	public List<ECourseAvailability> availableCourse;

	public List<ECourseAvailability> getAvailableCourse() {
		return availableCourse;
	}

	public void setAvailableCourse(List<ECourseAvailability> availableCourse) {
		this.availableCourse = availableCourse;
	}

	public static Finder<Long, ECourse> find = new Finder<Long, ECourse>(
			Long.class, ECourse.class);

	public Integer getCourse_id() {
		return course_id;
	}

	//public Integer getCourse_prefix_id() {
		//return course_prefix_id;
	//}

	public Integer getCourse_number() {
		return course_number;
	}

	public String getCourse_title() {
		return course_title;
	}

	public Integer getCourse_credits() {
		return course_credits;
	}

	public Integer getCourse_level() {
		return course_level;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	//public void setCourse_prefix_id(Integer course_prefix_id) {
		//this.course_prefix_id = course_prefix_id;
	//}

	public void setCourse_number(Integer course_number) {
		this.course_number = course_number;
	}

	public void setCourse_title(String course_title) {
		this.course_title = course_title;
	}

	public void setCourse_credits(Integer course_credits) {
		this.course_credits = course_credits;
	}

	public void setCourse_level(Integer course_level) {
		this.course_level = course_level;
	}

	public EPrereqCoreqFormula getPrereq() {
		return prereq;
	}

	public void setPrereq(EPrereqCoreqFormula prereq) {
		this.prereq = prereq;
	}

	public EPrereqCoreqFormula getCoreq() {
		return coreq;
	}

	public void setCoreq(EPrereqCoreqFormula coreq) {
		this.coreq = coreq;
	}

	public EPrefix getCourse_prefix() {
		return course_prefix;
	}

	public void setCourse_prefix(EPrefix course_prefix) {
		this.course_prefix = course_prefix;
	}

}
