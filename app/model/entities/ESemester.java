package model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name = "semesters")
public class ESemester extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "semster_id_pk")
	public Integer semester_id;
	@Column(name = "semster_name")
	public String semester_name;

	@OneToMany(mappedBy = "semester")
	public List<ECourseAvailability> availableSemester;

	public static Finder<Long, ESemester> find = new Finder<Long, ESemester>(
			Long.class, ESemester.class);

	public List<ECourseAvailability> getAvailableSemester() {
		return availableSemester;
	}

	public void setAvailableSemester(List<ECourseAvailability> availableSemester) {
		this.availableSemester = availableSemester;
	}

	public Integer getSemester_id() {
		return semester_id;
	}

	public String getSemester_name() {
		return semester_name;
	}

	public void setSemester_id(Integer semester_id) {
		this.semester_id = semester_id;
	}

	public void setSemester_name(String semester_name) {
		this.semester_name = semester_name;
	}

}
