package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import java.util.*;
@Entity
@Table(name="degree_programs")
public class EDegreeProgram extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="dp_id_pk")
	public Integer dp_id;
	public String dp_title;
	
	@ManyToMany
	@JoinTable(
			name="degree_program_reqs",
			joinColumns={@JoinColumn(name="degree_program_id_fk", referencedColumnName="dp_id_pk")},
			inverseJoinColumns={@JoinColumn(name="requirement_id_fk", referencedColumnName="req_id_pk")}
			)
	public List<ERequirement> requirementList;
	
	public static Finder<Long, EDegreeProgram> find = new Finder<Long, EDegreeProgram>(Long.class, EDegreeProgram.class);

	public Integer getDp_id() {
		return dp_id;
	}

	public String getDp_title() {
		return dp_title;
	}

	public void setDp_id(Integer dp_id) {
		this.dp_id = dp_id;
	}

	public void setDp_title(String dp_title) {
		this.dp_title = dp_title;
	}

	public List<ERequirement> getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(List<ERequirement> requirementList) {
		this.requirementList = requirementList;
	}
	
	
}
