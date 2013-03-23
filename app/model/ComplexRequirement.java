package model;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.google.common.collect.Lists;

import model.entities.ERequirement;
import model.entities.ERequirementFormula;

public class ComplexRequirement extends Requirement {
	protected ComplexRequirement() {
		super();
	}

	public static ComplexRequirement create(String req_title,
			Boolean req_is_visible, Junction req_junction_type) {
		ComplexRequirement requirement = new ComplexRequirement();
		ERequirement newRequirement = new ERequirement();
		newRequirement.setReq_is_simple(false);
		newRequirement.setReq_title(req_title);
		newRequirement.setReq_is_visible(req_is_visible);
		newRequirement.setReq_junction_type(req_junction_type.name());
		newRequirement.save();

		requirement.entity = newRequirement;
		return requirement;
	}

	public void update(String req_title, Boolean req_is_visible,
			Junction req_junction_type) {
		this.entity.setReq_title(req_title);
		this.entity.setReq_is_visible(req_is_visible);
		this.entity.setReq_junction_type(req_junction_type.name());
		this.entity.save();
		return;
	}

	public void addChildNode(Requirement req, boolean isPositive) {
		// ERequirement eRequirement = Ebean.find(ERequirement.class,
		// entity.getReq_id());
		// eRequirement.addChild(Requirement.unwrap(req), isPositive);
		this.entity.addChild(Requirement.unwrap(req), isPositive);

		return;
	}

	public void removeChildNode(Requirement req) {
		for (ERequirementFormula formulaItm : this.entity.children) {
			if (formulaItm.getChild().equals(req.entity)) {
				formulaItm.getChild().delete();
				this.entity.refresh();
				req.entity.refresh();
			}
		}
		return;
	}

	public void removeAllChildrenNodes() {
		for (RequirementFormulaNode node : getChildrenNodes()) {
			removeChildNode(node.getRequirement());
		}
		return;
	}

	public Junction getJunction() {
		return Junction.valueOf(entity.getReq_junction_type());
	}

	public List<RequirementFormulaNode> getChildrenNodes() {
		
		List<RequirementFormulaNode> nodeList = new ArrayList<RequirementFormulaNode>();
		for (ERequirementFormula eachChild : this.entity.getChildren()){
			RequirementFormulaNode node = new RequirementFormulaNode(Requirement.wrap(eachChild.getChild()), eachChild.getRf_is_positive());
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	//TODO: TEST THIS!!!!!
	public List<Course> getCourses() {
		// TODO implement depth-first-search traverse of the requirements tree
		// and
		// collect all courses from all simple requirements.
		List<Course> res = new ArrayList<Course>();

		for (ERequirementFormula eachChild : this.entity.getChildren()){
			Requirement req = Requirement.wrap(eachChild.getChild());
			res.addAll(req.getCourses());
		}
		return res;
	}
		
		
		
}
