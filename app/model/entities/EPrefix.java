package model.entities;

import javax.persistence.*;
import play.db.ebean.Model;

@Entity
@Table(name="prefixes")
public class EPrefix extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="prefix_id_pk")
	public Integer prefix_id;
	public String prefix_title;
	public String prefix_full_name;
	
	public static Finder<Long, EPrefix> find = new Finder<Long, EPrefix>(Long.class, EPrefix.class);

	public void setPrefixId(Integer prefix_id) {
		this.prefix_id = prefix_id;
	}
	
	public void setPrefixFullName(String prefix_full_name) { 
		this.prefix_full_name = prefix_full_name; 
		}
	public void setPrefixTitle(String prefix_title) { 
		this.prefix_title = prefix_title; 
		}

	
	
	public String getPrefixFullName() { return prefix_full_name; }
	public String getPrefixTitle() { return prefix_title; }
	public Integer getPrefixId() {return prefix_id; }
	
	
}
