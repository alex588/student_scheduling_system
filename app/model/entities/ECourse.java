package model.entities;

import java.util.List;
import play.db.ebean.Model;
import javax.persistence.*;

/**
 * @author Mihi Daptardar
 * 
 */
@Entity
@Table(name = "courses")
public class ECourse extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "course_id_pk")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "course_prefix_id_fk")
	private EPrefix coursePrefix;
	@Column(name = "course_number")
	private Integer courseNumber;
	@Column(name = "course_title")
	private String courseTitle;
	@Column(name = "course_credits")
	private Integer courseCredits;
	@Column(name = "course_level")
	private Integer courseLevel;
	@ManyToOne
	@JoinColumn(name = "course_prereq_formula_id_fk")
	private EPrereqCoreqFormula prereq;
	@ManyToOne
	@JoinColumn(name = "course_coreq_formula_id_fk")
	private EPrereqCoreqFormula coreq;
	@OneToMany(mappedBy = "course")
	private List<ECourseAvailability> availableCourse;

	public List<ECourseAvailability> getAvailableCourse() {
		return availableCourse;
	}

	public void setAvailableCourse(List<ECourseAvailability> availableCourse) {
		this.availableCourse = availableCourse;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public Integer getCourseCredits() {
		return courseCredits;
	}

	public Integer getCourseLevel() {
		return courseLevel;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

	public void setTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void setCourseCredits(Integer courseCredits) {
		this.courseCredits = courseCredits;
	}

	public void setCourseLevel(Integer courseLevel) {
		this.courseLevel = courseLevel;
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

	public EPrefix getCoursePrefix() {
		return coursePrefix;
	}

	public void setCoursePrefix(EPrefix coursePrefix) {
		this.coursePrefix = coursePrefix;
	}

}
