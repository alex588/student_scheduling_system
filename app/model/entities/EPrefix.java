package model.entities;

import javax.persistence.*;
import play.db.ebean.Model;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Entity
@Table(name = "prefixes")
public class EPrefix extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "prefix_id_pk")
	private Integer prefix_id;
	@Column(name = "prefix_title")
	private String prefixTitle;
	@Column(name = "prefix_full_name")
	private String prefixFullName;

	public Integer getId() {
		return prefix_id;
	}

	public void setPrefixFullName(String prefixFullName) {
		this.prefixFullName = prefixFullName;
	}

	public void setPrefixTitle(String prefixTitle) {
		this.prefixTitle = prefixTitle;
	}

	public String getPrefixFullName() {
		return prefixFullName;
	}

	public String getPrefixTitle() {
		return prefixTitle;
	}

}
