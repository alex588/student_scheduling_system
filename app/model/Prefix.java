package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.EPrefix;
import com.avaje.ebean.Ebean;

/**
 * 
 * @author Alexey Tregubov
 *
 */
public class Prefix {
	private EPrefix entity;

	public static List<Prefix> getAll() {
		List<EPrefix> list = Ebean.find(EPrefix.class).findList();
		List<Prefix> prefixList = new ArrayList<Prefix>(list.size());
		for (EPrefix eprefix : list) {
			Prefix prefix = new Prefix();
			prefix.entity = eprefix;
			prefixList.add(prefix);
		}
		return prefixList;
	}

	public static Prefix getById(Integer id) {
		EPrefix entity = Ebean.find(EPrefix.class, id);
		return wrap(entity);
	}

	public static Prefix getbyName(String title) {
		EPrefix ePrefix = Ebean.find(EPrefix.class).where()
				.eq("prefix_title", title).findList().get(0);
		return wrap(ePrefix);
	}

	public static Prefix create(String prefixFullName, String prefixTitle) {
		Prefix prefix = new Prefix();
		EPrefix newPrefix = new EPrefix();
		newPrefix.setPrefixFullName(prefixFullName);
		newPrefix.setPrefixTitle(prefixTitle);
		Ebean.save(newPrefix);
		newPrefix = Ebean.find(EPrefix.class, newPrefix.getId());
		prefix.entity = newPrefix;
		return prefix;
	}

	public void update(String prefixFullName, String prefixTitle) {
		this.entity.setPrefixTitle(prefixTitle);
		this.entity.setPrefixFullName(prefixFullName);
		this.entity.update();
	}

	public static void delete(Integer prefixId) {
		EPrefix eprefix = Ebean.find(EPrefix.class, prefixId);
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

	public Integer getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getPrefixTitle();
	}

	public String getFullName() {
		return entity.getPrefixFullName();
	}

	@Override
	public boolean equals(Object o) {
		if (o != null) {
			if (o instanceof Prefix) {
				Prefix anotherPrefix = (Prefix) o;
				return (this.getId() == anotherPrefix.getId());
			}
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	@Override
	public String toString() {
		return this.getName();
	}
}