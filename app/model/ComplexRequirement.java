package model;

import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;
import model.entities.ECourseGroupFormula;
import model.entities.ERequirement;
import model.entities.ERequirementFormula;

/**
 * @author Alexey Tregubov
 * 
 */
public class ComplexRequirement extends Requirement {
	private static final String DELETE_FORMULA_STM = "delete from req_formulas where rf_child_id_pk_fk = :cid and rf_parent_id_pk_fk = :pid";

	protected ComplexRequirement() {
		super();
	}

	/**
	 * 
	 * @param title
	 * @param abbreviation
	 * @param isVisible
	 *            - this is mainly used if we don't want to consider a
	 *            particular requirement in a degree program
	 * 
	 *            For creation of a complex requirement, there are intermediate
	 *            nodes created as a part of formula. These formula nodes should
	 *            not be visible in the requirement list in the UI. In order to
	 *            filter them, their visibility is set as false.
	 * 
	 *            So if you want visible courses, you need to call
	 *            Requirement.getAllVisible() instead of Requirement.getAll()..
	 * 
	 * @param junction
	 * @return new complex requirement.
	 */
	public static ComplexRequirement create(String title, String abbreviation,
			Boolean isVisible, Junction junction) {
		ComplexRequirement requirement = new ComplexRequirement();
		ERequirement newRequirement = new ERequirement();
		newRequirement.setIsSimple(false);
		newRequirement.setTitle(title);
		newRequirement.setAbbreviation(abbreviation);
		newRequirement.setIsVisible(isVisible);
		newRequirement.setJunction(junction.name());
		newRequirement.save();

		requirement.entity = newRequirement;
		return requirement;
	}

	public void update(ComplexRequirement src, Boolean isVisible) {
		this.removeAllChildrenNodes();
		for (RequirementFormulaNode child : src.getChildrenNodes())
			this.addChildNode(child.getRequirement(), child.isPositive());
		this.update(src.getTitle(), this.getAbbreviation(), isVisible,
				src.getJunction());
		return;
	}

	public void update(String title, String abbreviation, Boolean isVisible,
			Junction junction) {
		this.entity.setTitle(title);
		this.entity.setAbbreviation(abbreviation);
		this.entity.setIsVisible(isVisible);
		this.entity.setJunction(junction.name());
		this.entity.save();
		return;
	}

	public void addChildNode(Requirement req, boolean isPositive) {
		this.entity.addChild(Requirement.unwrap(req), isPositive);
		return;
	}

	public void removeChildNode(Requirement req) {
		for (ERequirementFormula formulaItm : this.entity.getChildren()) {
			if (formulaItm.getChild().equals(req.entity)) {
				Update<ECourseGroupFormula> update = Ebean.createUpdate(
						ECourseGroupFormula.class, DELETE_FORMULA_STM);
				update.set("cid", formulaItm.getChild().getId());
				update.set("pid", formulaItm.getParent().getId());
				update.execute();
				this.entity.refresh();
				return;
			}
		}
		return;
	}

	public void removeAllChildrenNodes() {
		for (RequirementFormulaNode node : getChildrenNodes()) {
			Requirement req = node.getRequirement();
			removeChildNode(req);
		}
		return;
	}

	public Junction getJunction() {
		return Junction.valueOf(entity.getJunction());
	}

	public List<RequirementFormulaNode> getChildrenNodes() {
		List<RequirementFormulaNode> nodeList = new ArrayList<RequirementFormulaNode>();
		entity.refresh();
		for (ERequirementFormula eachChild : this.entity.getChildren()) {
			ERequirement eReq = eachChild.getChild();
			RequirementFormulaNode node = new RequirementFormulaNode(
					Requirement.wrap(eReq), eachChild.isPositive());
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public List<Course> getCourses() {
		List<Course> res = new ArrayList<Course>();

		for (ERequirementFormula eachChild : this.entity.getChildren()) {
			Requirement req = Requirement.wrap(eachChild.getChild());
			res.addAll(req.getCourses());
		}
		return res;
	}

	@Override
	public List<Requirement> getAllSubrequirements() {
		List<Requirement> reqs = new ArrayList<Requirement>();
		for (RequirementFormulaNode req : this.getChildrenNodes())
			reqs.addAll(req.getRequirement().getAllSubrequirements());
		return reqs;
	}

}
