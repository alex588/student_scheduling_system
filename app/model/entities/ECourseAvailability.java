package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import play.db.ebean.Model;

/**
 * @author Mihi Daptardar
 * 
 */
@Entity
@Table(name = "course_availability")
public class ECourseAvailability extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ca_id")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "ca_semester_id_fk")
	private ESemester semester;
	@ManyToOne
	@JoinColumn(name = "ca_course_id_fk")
	private ECourse course;
	@Column(name = "ca_year")
	private Integer year;
	@Column(name = "ca_location")
	private String location;

	public ECourse getCourse() {
		return course;
	}

	public void setCourse(ECourse course) {
		this.course = course;
	}

	public ESemester getSemester() {
		return semester;
	}

	public Integer getYear() {
		return (year == null) ? null : (year == 0 ? null : year);
	}

	public String getLocation() {
		return location;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSemester(ESemester semester) {
		this.semester = semester;
	}

	public Integer getId() {
		return id;
	}
}
