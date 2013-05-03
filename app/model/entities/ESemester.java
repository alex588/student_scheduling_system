package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import play.db.ebean.Model;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Entity
@Table(name = "semesters")
public class ESemester extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "semster_id_pk")
	private Integer id;
	@Column(name = "semster_name")
	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
