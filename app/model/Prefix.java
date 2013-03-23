package model;

import java.util.ArrayList;
import java.util.List;

import model.entities.ECourse;
import model.entities.EPrefix;




import com.avaje.ebean.Ebean;


public class Prefix {
	private EPrefix entity;
	
	public static List<Prefix> getAll(){
		List<EPrefix> list  = Ebean.find(EPrefix.class).findList();
		List<Prefix> prefixList = new ArrayList<>(list.size());
		
		for (EPrefix eprefix: list){
			Prefix prefix = new Prefix();
			prefix.entity = eprefix;
			prefixList.add(prefix);
	
		}		
		 return prefixList;

	}
	
	public static Prefix getById(Integer id){
		EPrefix entity = Ebean.find(EPrefix.class, id);
		return wrap(entity);
	}
	//display java like..
	//has to return a list of all the prefixes..
	
	public static Prefix create(String prefix_full_name, String prefix_title){
		Prefix prefix = new Prefix();
		EPrefix newPrefix = new EPrefix();
		newPrefix.setPrefixFullName(prefix_full_name);
		newPrefix.setPrefixTitle(prefix_title);
		Ebean.save(newPrefix);
		
		newPrefix = Ebean.find(EPrefix.class, newPrefix.getPrefixId());
		
		prefix.entity = newPrefix;
		return prefix;
	}
	
	
	//has to create a new entity/record/tuple in database and returns its object
	
	public void update(String prefix_full_name, String prefix_title){

		this.entity.setPrefixTitle(prefix_title);
		this.entity.setPrefixFullName(prefix_full_name);
		this.entity.update();
	
	}
	
	public static void delete(Integer prefix_id){
		EPrefix eprefix = Ebean.find(EPrefix.class, prefix_id);
		eprefix.delete();
	}
	
	public static Prefix wrap(EPrefix ePrefix) {
		Prefix prefix = new Prefix();
		prefix.entity = ePrefix;
		return ePrefix == null ? null : prefix;
	}

	public static EPrefix unwrap(Prefix prefix) {
		return prefix == null ? null : prefix.entity;
	}
	
	
	public Integer getId(){
		return entity.getPrefixId();
	}
	
	public String getName(){
		return entity.getPrefixTitle();
	}
	
	public String getFullName(){
		return entity.getPrefixFullName();
	}
	
}