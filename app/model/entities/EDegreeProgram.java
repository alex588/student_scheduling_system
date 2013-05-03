package model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import play.db.ebean.Model;
import java.util.*;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Entity
@Table(name = "degree_programs")
public class EDegreeProgram extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "dp_id_pk")
	private Integer id;
	@Column(name = "dp_title")
	private String title;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "degree_program_reqs", joinColumns = { @JoinColumn(name = "degree_program_id_fk", referencedColumnName = "dp_id_pk") }, inverseJoinColumns = { @JoinColumn(name = "requirement_id_fk", referencedColumnName = "req_id_pk") })
	private List<ERequirement> requirementList;

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ERequirement> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<ERequirement> requirementList) {
		this.requirementList = requirementList;
	}

}
