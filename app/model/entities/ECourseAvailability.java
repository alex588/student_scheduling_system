package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="course_availability")
public class ECourseAvailability extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="ca_semester_id_pk_fk")
	public ESemester semester;
	
	@ManyToOne//(fetch=FetchType.LAZY)
	@JoinColumn(name="ca_course_id_pk_fk")
	public ECourse course;
	
	@Column(name="ca_year")
	public Integer year;
	@Column(name="ca_location")
	public String location;
		
	public static Finder<Long, ECourseAvailability> find = new Finder<Long, ECourseAvailability>(Long.class, ECourseAvailability.class);


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
		return year;
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
}
