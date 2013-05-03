package model;

import java.util.*;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;
import model.entities.ECourseGroup;
import model.entities.ECourseGroupFormula;

/**
 * @author Alexey Tregubov
 */
public class ComplexCourseGroup extends CourseGroup {
	private static final String DELETE_FORMULA_STM = "delete from course_groups_formulas where cgf_child_id_pk_fk = :cid and cgf_parent_id_pk_fk = :pid";

	protected ComplexCourseGroup() {
		super();
	}

	/**
	 * Creates a new complex course group
	 * 
	 * @param title
	 * @param abbreviation
	 * @param isVisible
	 *            if node is visible, it appears in UI. We should create
	 *            invisible nodes for intermediate nodes in complex course group
	 *            formula.
	 * @param junction
	 *            AND, OR
	 * @return
	 */
	public static ComplexCourseGroup create(String title, String abbreviation,
			Boolean isVisible, Junction junction) {
		ComplexCourseGroup coursegroup = new ComplexCourseGroup();
		ECourseGroup newCourseGroup = new ECourseGroup();
		newCourseGroup.setTitle(title);
		newCourseGroup.setAbbreviation(abbreviation);
		newCourseGroup.setIsSimple(false);
		newCourseGroup.setJnction(junction.name());
		newCourseGroup.setIsVisible(isVisible);
		newCourseGroup.save();
		coursegroup.entity = newCourseGroup;

		return coursegroup;
	}

	public void update(ComplexCourseGroup src, Boolean isVisible) {
		this.removeAllChildrenNodes();
		for (CourseGroupFormulaNode child : src.getChildrenNodes())
			this.addChildNode(child.getCg(), child.getIsPositive());
		this.update(src.getTitle(), false, src.getJunction(),
				this.getAbbreviation(), isVisible);
		return;
	}

	public void addChildNode(CourseGroup courseGroup, Boolean isPositive) {
		this.entity.addChild(courseGroup.entity, isPositive);
	}

	public void removeChildNode(CourseGroup courseGroup) {
		for (ECourseGroupFormula formulaItm : this.entity.getChildren()) {
			if (formulaItm.getChild().equals(courseGroup.entity)) {
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

	/**
	 * Performs set operations such as union(OR), intersection (AND),
	 * subtraction (AND not) and calculates the result set courses that
	 * constitute the group.
	 * 
	 * @return list of all courses in the group.
	 */

	@Override
	public List<Course> getAllCourses() {
		List<Course> courseList = new ArrayList<Course>();
		Set<Course> courseSet = new HashSet<Course>();
		if (this.getJunction() == Junction.OR) {
			for (CourseGroupFormulaNode eachChild : getChildrenNodes()) {
				CourseGroup cg = eachChild.getCg();
				if (eachChild.getIsPositive()) {
					courseSet.addAll(cg.getAllCourses());

				} else {
					List<Course> universalSet = Course.getAll();
					universalSet.removeAll(cg.getAllCourses());
					courseSet.addAll(universalSet);
				}
			}
			courseList.addAll(courseSet);
			return courseList;
		}
		if (this.getJunction() == Junction.AND) {
			Set<CourseGroup> positiveSet = new HashSet<CourseGroup>();
			Set<CourseGroup> negativeSet = new HashSet<CourseGroup>();

			for (CourseGroupFormulaNode eachChild : getChildrenNodes()) {
				CourseGroup cg = eachChild.getCg();
				if (eachChild.getIsPositive())
					positiveSet.add(cg);
				else
					negativeSet.add(cg);
			}

			// union of all negative nodes
			Set<Course> negativeCourseSet = new HashSet<Course>();
			for (CourseGroup eachCG : negativeSet)
				negativeCourseSet.addAll(eachCG.getAllCourses());

			// intersection of all the positive nodes
			Set<Course> positiveCourseSet = new HashSet<Course>();
			if (!positiveSet.isEmpty()) {
				positiveCourseSet.addAll(positiveSet.iterator().next()
						.getAllCourses());
			}
			for (CourseGroup eachCG : positiveSet)
				positiveCourseSet.retainAll(eachCG.getAllCourses());

			// subtraction
			if (!positiveSet.isEmpty()) {
				positiveCourseSet.removeAll(negativeCourseSet);
				courseList.addAll(positiveCourseSet);
			} else {
				List<Course> universalSet = Course.getAll();
				universalSet.removeAll(negativeCourseSet);
				courseList.addAll(universalSet);
			}
			return courseList;
		}
		return courseList;
	}

	public void removeAllChildrenNodes() {
		for (CourseGroupFormulaNode node : getChildrenNodes()) {
			removeChildNode(node.getCg());
		}
		return;
	}

	public List<CourseGroupFormulaNode> getChildrenNodes() {
		List<CourseGroupFormulaNode> nodeList = new ArrayList<CourseGroupFormulaNode>();
		for (ECourseGroupFormula eachChild : this.entity.getChildren()) {
			CourseGroupFormulaNode node = new CourseGroupFormulaNode(
					CourseGroupFactory.wrap(eachChild.getChild()),
					eachChild.isPositive());
			nodeList.add(node);
		}
		return nodeList;
	}

}
