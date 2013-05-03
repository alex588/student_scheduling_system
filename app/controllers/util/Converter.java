package controllers.util;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import model.*;
import model.PreRequisite;

/**
 * 
 * @author Doug Kinnes
 *
 */
public class Converter {

	public static List<Course> parseCourseList(String courseList) {
		if (courseList != null) {
			String delims = "[ .,]+";
			String[] tokens = courseList.split(delims);

			List<Course> result = new ArrayList<Course>(tokens.length);
			for (String token : tokens)
				result.add(Course.getByPrefixAndNumber(extractPrefix(token),
						extractNumber(token)));

			return result;
		} else
			return null;
	}

	public static Course parseCourse(String course) {

		return course == null ? null : Course.getByPrefixAndNumber(
				extractPrefix(course), extractNumber(course));
	}

	private static Prefix extractPrefix(String str) {
		String number = extractNumber(str).toString();
		String prefix = str.substring(0, str.indexOf(number));
		return Prefix.getbyName(prefix);
	}

	private static Integer extractNumber(String str) {
		if (str.length() < 4)
			throw new IllegalArgumentException("Incorrect course number: "
					+ str);
		String number = str.substring(str.length() - 3);
		return Integer.valueOf(number);
	}

	/**
	 * 
	 * @param name
	 *            = Name of the requirement/coursegroup
	 * @return type wanted example CSC01, HUM01 (same as)
	 */
	public static String cgAbbreviationGen(String name) {
		name = name.toUpperCase();
		String[] nameWords = name.split("[_,. ]+");
		StringBuilder abbr = new StringBuilder();
		String abbrPrefix = "";
		Integer abbrNo = 0;

		// abbreviation prefix creation
		if (nameWords.length >= 3) {
			abbrPrefix = nameWords[0].substring(0, 1)
					+ nameWords[1].substring(0, 1)
					+ nameWords[2].substring(0, 1);
		}

		else if (nameWords.length == 2) {
			if (nameWords[0].length() > 1) {
				abbrPrefix = nameWords[0].substring(0, 2);
				abbrPrefix += nameWords[1].substring(0, 1);
			} else {
				abbrPrefix = nameWords[0].substring(0, 1);
				if (nameWords[1].length() > 1) {
					abbrPrefix += nameWords[1].substring(0, 2);
				} else
					abbrPrefix += "0";
			}
		}

		// for 1 word course
		else {
			if (nameWords[0].length() > 3)
				abbrPrefix = nameWords[0].substring(0, 3);

			else {
				abbrPrefix = nameWords[0];
				while (abbrPrefix.length() <= 3)
					abbrPrefix += "0";
			}
		}

		// Number Creation!!
		Integer no = CourseGroup.findSimilarAbbrNo(abbrPrefix);
		if (no < 9) {
			abbrNo = no + 1;
			abbr.append(abbrPrefix);
			abbr.append("0");
			abbr.append(abbrNo.toString());
		}

		else {
			abbrNo = no + 1;
			abbr.append(abbrPrefix);
			abbr.append(abbrNo.toString());
		}

		return abbr.toString();
	}

	public static String requirementAbbreviationGen(String name) {
		name = name.toUpperCase();
		String[] nameWords = name.split("[_,. ]+");
		StringBuilder abbr = new StringBuilder();
		String abbrPrefix = "";
		Integer abbrNo = 0;

		// abbreviation prefix creation
		if (nameWords.length >= 3) {
			abbrPrefix = nameWords[0].substring(0, 1)
					+ nameWords[1].substring(0, 1)
					+ nameWords[2].substring(0, 1);
		}

		else if (nameWords.length == 2) {
			if (nameWords[0].length() > 1) {
				abbrPrefix = nameWords[0].substring(0, 2);
				abbrPrefix += nameWords[1].substring(0, 1);
			} else {
				abbrPrefix = nameWords[0].substring(0, 1);
				if (nameWords[1].length() > 1) {
					abbrPrefix += nameWords[1].substring(0, 2);
				} else
					abbrPrefix += "0";
			}
		}

		// for 1 word course
		else {
			if (nameWords[0].length() > 3)
				abbrPrefix = nameWords[0].substring(0, 3);

			else {
				abbrPrefix = nameWords[0];
				while (abbrPrefix.length() <= 3)
					abbrPrefix += "0";
			}
		}

		// Number Creation!!
		Integer no = CourseGroup.findSimilarAbbrNo(abbrPrefix);
		if (no < 9) {
			abbrNo = no + 1;
			abbr.append(abbrPrefix);
			abbr.append("0");
			abbr.append(abbrNo.toString());
		}

		else {
			abbrNo = no + 1;
			abbr.append(abbrPrefix);
			abbr.append(abbrNo.toString());
		}

		return abbr.toString();
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public static ComplexCourseGroup convertGroupCombo(String groupCombo,
			String complexGroupName, String complexAbbrev, Boolean isVisible)
			throws ParseException {
		// Break string into pieces for processing
		List<String> equation = breakString(groupCombo);

		if (!isExpressionValid(groupCombo)) {
			throw new ParseException(
					"Complex course group boolean expression not valid.", 0);
		}

		// Push into stack so that beginning is at the top
		Stack<String> stack = new Stack<String>();
		if (equation.get(0).equals("(")) {// If there is a ( at the beginning of
											// the string still, reverse the
											// order in the stack
			for (int i = 0; i < equation.size(); i++) {
				if (equation.get(i).equals(")")) {
					equation.set(i, "(");
				} else if (equation.get(i).equals("(")) {
					equation.set(i, ")");
				}
				stack.push(equation.get(i));
			}
		} else {
			for (int i = equation.size() - 1; i >= 0; i--) {
				stack.push(equation.get(i));
			}
		}
		// Set as atomic for reference
		AtomicReference<Stack<String>> stackRef = new AtomicReference<Stack<String>>();
		stackRef.set(stack);

		return buildCourseGroupTree(null, stackRef, complexGroupName,
				complexAbbrev, isVisible);
	}

	public static ComplexRequirement convertRequirementCombo(
			String requirementCombo, String complexName, String complexAbbrev,
			Boolean isVisible) throws ParseException {
		// Break string into pieces for processing
		List<String> equation = breakString(requirementCombo);

		if (!isExpressionValid(requirementCombo)) {
			throw new ParseException(
					"Complex requirement boolean expression not valid.", 0);
		}

		// Push into stack so that beginning is at the top
		Stack<String> stack = new Stack<String>();
		if (equation.get(0).equals("(")) {// If there is a ( at the beginning of
											// the string still, reverse the
											// order in the stack
			for (int i = 0; i < equation.size(); i++) {
				if (equation.get(i).equals(")")) {
					equation.set(i, "(");
				} else if (equation.get(i).equals("(")) {
					equation.set(i, ")");
				}
				stack.push(equation.get(i));
			}
		} else {
			for (int i = equation.size() - 1; i >= 0; i--) {
				stack.push(equation.get(i));
			}
		}
		// Set as atomic for reference
		AtomicReference<Stack<String>> stackRef = new AtomicReference<Stack<String>>();
		stackRef.set(stack);
		return buildRequirementTree(null, stackRef, complexName, complexAbbrev,
				isVisible);
	}

	public static Requisite convertPreCorReq(String requisite,
			boolean isPreRequisite) throws ParseException {
		if (!isExpressionValid(requisite)) {
			throw new ParseException(
					"Requisite boolean equation is not valid.", 0);
		}
		// Break requisite string into pieces
		List<String> equation = breakString(requisite);

		// Push into stack so that beginning is at the top
		Stack<String> stack = new Stack<String>();
		if (equation.get(0).equals("(")) {// If there is a ( at the
											// beginning of the string
											// still, reverse the order in
											// the stack
			for (int i = 0; i < equation.size(); i++) {
				if (equation.get(i).equals(")")) {
					equation.set(i, "(");
				} else if (equation.get(i).equals("(")) {
					equation.set(i, ")");
				}
				stack.push(equation.get(i));
			}
		} else {
			for (int i = equation.size() - 1; i >= 0; i--) {
				stack.push(equation.get(i));
			}
		}
		// Set as atomic for reference
		AtomicReference<Stack<String>> stackRef = new AtomicReference<Stack<String>>();
		stackRef.set(stack);
		Requisite req = buildTree(null, stackRef, isPreRequisite);
		if (req == null && isPreRequisite) {
			String prefixString = equation.get(0).substring(0,
					equation.get(0).length() - 3); // course number is 3
													// chars
			Integer courseNo = Integer.parseInt(equation.get(0).substring(
					equation.get(0).length() - 3, equation.get(0).length()));
			Course prereqCourse = Course.getByPrefixAndNumber(
					Prefix.getbyName(prefixString.toUpperCase()), courseNo);
			if (prereqCourse == null) {
				throw new ParseException(
						"Course in prerequisite boolean equation not found", 0);
			}
			return PreRequisite.createLeaf(true, null, prereqCourse);
		} else if (req == null && !isPreRequisite) {
			String prefixString = equation.get(0).substring(0,
					equation.get(0).length() - 3); // course number is 3
													// chars
			Integer courseNo = Integer.parseInt(equation.get(0).substring(
					equation.get(0).length() - 3, equation.get(0).length()));
			Course prereqCourse = Course.getByPrefixAndNumber(
					Prefix.getbyName(prefixString.toUpperCase()), courseNo);
			if (prereqCourse == null) {
				throw new ParseException(
						"Course in corequisite boolean equation not found", 0);
			}
			return CoRequisite.createLeaf(true, null, prereqCourse);
		}
		return req;
	}

	public static String convertGroupCombo(CourseGroup parent)
			throws ParseException {
		if (parent == null) {
			return "";
		} else {
			return buildCourseGroupString(parent, parent);
		}
	}

	public static String convertRequirementCombo(Requirement parent)
			throws ParseException {
		if (parent == null) {
			return "";
		} else {
			return buildRequirementString(parent, parent);
		}
	}

	public static String convertRequirementComboToSimpleRequirements(
			Requirement parent) throws ParseException {
		if (parent == null) {
			return "";
		} else {
			return buildRequirementStringToSimpleRequirements(parent, parent);
		}
	}

	public static String convertPreCorReq(Requisite requisite)
			throws ParseException {
		if (requisite == null) {
			return "";
		} else {
			return buildString(requisite);
		}
	}

	private static boolean isExpressionValid(String string) {
		int counter = 0;
		for (char ch : string.toCharArray()) {
			if (ch == '(' || ch == ')')
				counter++;
		}
		if (counter % 2 == 0)
			return true;
		else
			return false;
	}

	// Recursive solution to create string from tree
	private static String buildString(Requisite parent) throws ParseException {
		List<Requisite> children = parent.getChildren();

		if (children.isEmpty()) {
			if (parent.getIsLeaf()) {
				Course course = parent.getCourse();
				if (course == null) {
					throw new ParseException(
							"Could not find requisite course in course list", 0);
				}
				return course.toString();
			} else {
				if (parent.getJunction() == Junction.AND)
					return " AND ";
				else
					return " OR ";
			}
		} else {
			StringBuilder string = new StringBuilder("(");
			for (int i = 0; i < children.size() - 1; i++) {
				try {
					string.append(buildString(children.get(i)));
				} catch (Exception e) {
					throw new ParseException(e.getMessage(), 0);
				}
				if (parent.getJunction() == Junction.AND)
					string.append(" AND ");
				else
					string.append(" OR ");
			}
			for (int j = children.size() - 1; j >= 0; j--) {
				if (children.get(j).getIsLeaf()) {
					string.append(children.get(j).getCourse().toString());
					j = -1;
				}
			}
			string.append(")");
			return string.toString();
		}
	}

	// Recursive solution to create string from tree
	private static String buildRequirementString(Requirement parent,
			Requirement origParent) {
		List<RequirementFormulaNode> children = new ArrayList<RequirementFormulaNode>();

		if (!parent.isSimple()) {
			ComplexRequirement cParent = (ComplexRequirement) parent;
			children = cParent.getChildrenNodes();
		}

		if (children.isEmpty()
				|| (parent.isVisible() && !parent.equals(origParent))) {
			if (parent.isSimple()
					|| (parent.isVisible() && !parent.equals(origParent))) {
				return parent.getAbbreviation();
			} else {
				ComplexRequirement cParent = (ComplexRequirement) parent;
				if (cParent.getJunction() == Junction.AND)
					return " AND ";
				else
					return " OR ";
			}
		} else {
			StringBuilder string = new StringBuilder("(");
			for (int i = 0; i < children.size() - 1; i++) {
				string.append(buildRequirementString(children.get(i)
						.getRequirement(), origParent));
				ComplexRequirement cParent = (ComplexRequirement) parent;
				if (cParent.getJunction() == Junction.AND)
					string.append(" AND ");
				else
					string.append(" OR ");
			}
			string.append(buildRequirementString(
					children.get(children.size() - 1).getRequirement(),
					origParent));
			string.append(")");
			return string.toString();
		}
	}

	private static ComplexRequirement buildRequirementTree(
			ComplexRequirement parent, AtomicReference<Stack<String>> stackRef,
			String parentName, String parentAbbrev, boolean isVisible)
			throws ParseException {
		Stack<String> stack = stackRef.get();
		// When finished, just return parent
		if (stack.isEmpty()) {
			return parent;
		} else {
			// Check for junctions, not a switch so no duplicate junction code
			// for AND and OR
			// Sets isPositive to false if junction is NOT and then moves to
			// real junction
			// Creates node, sets stack and calls itself
			// When done returns itself as the parent
			if (stack.peek().equals("AND") || stack.peek().equals("OR")
					|| stack.peek().equals("NOT")) {
				boolean isPositive = true;
				if (stack.peek().equals("NOT")) {
					isPositive = false;
					stack.pop();
				}
				Junction junction;
				if (stack.pop().equals("AND"))
					junction = Junction.AND;
				else
					junction = Junction.OR;
				// create new complex course group parent
				String abbr = isVisible ? parentAbbrev : null;
				ComplexRequirement newNode = ComplexRequirement.create(
						parentName, abbr, isVisible, junction);
				// add as child under previous parent if not null, if null it is
				// the parent
				if (parent == null) {
					parent = newNode;
				} else {
					parent.addChildNode(newNode, isPositive);
				}
				stackRef.set(stack);
				buildRequirementTree(newNode, stackRef, parentName,
						parentAbbrev, parent == null ? isVisible : false);
				return newNode;
			}
			// If at closing parenthesis, then return parent
			else if (stack.peek().equals(")")) {
				stack.pop();
				stackRef.set(stack);
				return parent;
			}
			// If at opening parenthesis, then continue into subequation
			// Then when returned, continue on for what is after this
			// subequation
			else if (stack.peek().equals("(")) {
				stack.pop();
				stackRef.set(stack);
				// buildRequirementTree(parent, stackRef, parentName,
				// parentAbbrev, false);
				return buildRequirementTree(parent, stackRef, parentName,
						parentAbbrev, parent == null ? isVisible : false);
			}
			// Catcher for courses
			// Ignore course until parent node is returned
			// When parent node is returned, then create leaf and return new
			// parent.
			else {
				String reqStr = stack.pop();
				stackRef.set(stack);
				ComplexRequirement requirementParent = buildRequirementTree(
						parent, stackRef, parentName, parentAbbrev,
						parent == null ? isVisible : false);

				// get requirement by abbreviation
				Requirement req = Requirement.getByAbbreviation(reqStr);
				if (req == null)
					throw new ParseException("Requirement with abbreviation '"
							+ reqStr + "' was not found.", 0);

				requirementParent.addChildNode(req, true);

				return requirementParent;
			}
		}
	}

	// Recursive solution to create string from tree all the way down to the
	// simple requirements
	private static String buildRequirementStringToSimpleRequirements(
			Requirement parent, Requirement origParent) {
		List<RequirementFormulaNode> children = new ArrayList<RequirementFormulaNode>();

		if (!parent.isSimple()) {
			ComplexRequirement cParent = (ComplexRequirement) parent;
			children = cParent.getChildrenNodes();
		}

		if (children.isEmpty()) {
			if (parent.isSimple()) {
				return parent.getAbbreviation();
			} else {
				ComplexRequirement cParent = (ComplexRequirement) parent;
				if (cParent.getJunction() == Junction.AND)
					return " AND ";
				else
					return " OR ";
			}
		} else {
			StringBuilder string = new StringBuilder("(");
			for (int i = 0; i < children.size() - 1; i++) {
				string.append(buildRequirementStringToSimpleRequirements(
						children.get(i).getRequirement(), origParent));
				ComplexRequirement cParent = (ComplexRequirement) parent;
				if (cParent.getJunction() == Junction.AND)
					string.append(" AND ");
				else
					string.append(" OR ");
			}
			string.append(buildRequirementStringToSimpleRequirements(children
					.get(children.size() - 1).getRequirement(), origParent));
			string.append(")");
			return string.toString();
		}
	}

	// Recursive solution to create string from tree
	private static String buildCourseGroupString(CourseGroup parent,
			CourseGroup origParent) {
		List<CourseGroupFormulaNode> children = new ArrayList<CourseGroupFormulaNode>();

		if (!parent.isSimple()) {
			ComplexCourseGroup cParent = (ComplexCourseGroup) parent;
			children = cParent.getChildrenNodes();
		}

		if (children.isEmpty()
				|| (parent.isVisible() && !parent.equals(origParent))) {
			if (parent.isSimple()
					|| (parent.isVisible() && !parent.equals(origParent))) {
				return parent.getAbbreviation();
			} else {
				ComplexCourseGroup cParent = (ComplexCourseGroup) parent;
				if (cParent.getJunction() == Junction.AND)
					return " AND ";
				else
					return " OR ";
			}
		} else {
			StringBuilder string = new StringBuilder("(");
			for (int i = 0; i < children.size() - 1; i++) {
				string.append(buildCourseGroupString(children.get(i).getCg(),
						origParent));
				ComplexCourseGroup cParent = (ComplexCourseGroup) parent;
				if (cParent.getJunction() == Junction.AND)
					string.append(" AND ");
				else
					string.append(" OR ");
			}
			string.append(buildCourseGroupString(
					children.get(children.size() - 1).getCg(), origParent));
			string.append(")");
			return string.toString();
		}
	}

	private static ComplexCourseGroup buildCourseGroupTree(
			ComplexCourseGroup parent, AtomicReference<Stack<String>> stackRef,
			String parentName, String parentAbbrev, Boolean isVisible)
			throws ParseException {
		Stack<String> stack = stackRef.get();
		// When finished, just return parent
		if (stack.isEmpty()) {
			return parent;
		} else {
			// Check for junctions, not a switch so no duplicate junction code
			// for AND and OR
			// Sets isPositive to false if junction is NOT and then moves to
			// real junction
			// Creates node, sets stack and calls itself
			// When done returns itself as the parent
			if (stack.peek().equals("AND") || stack.peek().equals("OR")
					|| stack.peek().equals("NOT")) {
				boolean isPositive = true;
				if (stack.peek().equals("NOT")) {
					isPositive = false;
					stack.pop();
				}
				Junction junction;
				if (stack.pop().equals("AND"))
					junction = Junction.AND;
				else
					junction = Junction.OR;
				// create new complex course group parent
				String abbr = isVisible ? parentAbbrev : null;
				ComplexCourseGroup newNode = ComplexCourseGroup.create(
						parentName, abbr, isVisible, junction);
				// add as child under previous parent if not null, if null it is
				// the parent
				if (parent == null) {
					parent = newNode;
				} else {
					parent.addChildNode(newNode, isPositive);
				}
				stackRef.set(stack);
				buildCourseGroupTree(newNode, stackRef, parentName,
						parentAbbrev, parent == null ? isVisible : false);
				return newNode;
			}
			// If at closing parenthesis, then return parent
			else if (stack.peek().equals(")")) {
				stack.pop();
				stackRef.set(stack);
				return parent;
			}
			// If at opening parenthesis, then continue into subequation
			// Then when returned, continue on for what is after this
			// subequation
			else if (stack.peek().equals("(")) {
				stack.pop();
				stackRef.set(stack);
				// buildCourseGroupTree(parent, stackRef, parentName,
				// parentAbbrev, false);
				return buildCourseGroupTree(parent, stackRef, parentName,
						parentAbbrev, parent == null ? isVisible : false);
			}
			// Catcher for courses
			// Ignore course until parent node is returned
			// When parent node is returned, then create leaf and return new
			// parent.
			else {
				String groupStr = stack.pop();
				stackRef.set(stack);
				ComplexCourseGroup groupParent = buildCourseGroupTree(parent,
						stackRef, parentName, parentAbbrev,
						parent == null ? isVisible : false);

				// get Course Group by abbreviation
				CourseGroup group = CourseGroup.getByAbbreviation(groupStr);
				if (group == null)
					throw new ParseException("Requirement with abbreviation '"
							+ groupStr + "' was not found.", 0);

				groupParent.addChildNode(group, true);

				return groupParent;
			}
		}
	}

	// Recursive solution to build tree
	private static Requisite buildTree(Requisite parent,
			AtomicReference<Stack<String>> stackRef, boolean isPreRequisite)
			throws ParseException {
		Stack<String> stack = stackRef.get();
		// When finished, just return parent
		if (stack.isEmpty()) {
			return parent;
		} else {
			// Check for junctions, not a switch so no duplicate junction code
			// for AND and OR
			// Sets isPositive to false if junction is NOT and then moves to
			// real junction
			// Creates node, sets stack and calls itself
			// When done returns itself as the parent
			if (stack.peek().equals("AND") || stack.peek().equals("OR")
					|| stack.peek().equals("NOT")) {
				boolean isPositive = true;
				if (stack.peek().equals("NOT")) {
					isPositive = false;
					stack.pop();
				}
				Junction junction;
				if (stack.pop().equals("AND"))
					junction = Junction.AND;
				else
					junction = Junction.OR;
				// Check if parent is a prerequisite or corequisite
				Requisite newNode;
				if (isPreRequisite) {
					newNode = PreRequisite.createNode(isPositive,
							(PreRequisite) parent, junction);
				} else {
					newNode = CoRequisite.createNode(isPositive,
							(CoRequisite) parent, junction);
				}
				stackRef.set(stack);
				buildTree(newNode, stackRef, isPreRequisite);
				return newNode;
			}
			// If at closing parenthesis, then return parent
			else if (stack.peek().equals(")")) {
				stack.pop();
				stackRef.set(stack);
				return parent;
			}
			// If at opening parenthesis, then continue into subequation
			// Then when returned, continue on for what is after this
			// subequation
			else if (stack.peek().equals("(")) {
				stack.pop();
				stackRef.set(stack);
				buildTree(parent, stackRef, isPreRequisite);
				return buildTree(parent, stackRef, isPreRequisite);
			}
			// Catcher for courses
			// Ignore course until parent node is returned
			// When parent node is returned, then create leaf and return new
			// parent.
			else {
				String courseStr = stack.pop();
				stackRef.set(stack);
				Requisite courseParent = buildTree(parent, stackRef,
						isPreRequisite);

				// Get matching course 
				if (courseStr.length() < 5) {
					throw new ParseException(
							"Course entered into requisite equation must be the proper format: ie. CS115",
							0);
				}
				Course prereqCourse;
				try {
					prereqCourse = parseCourse(courseStr);
				} catch (Exception e) {
					throw new ParseException("A course named " + courseStr
							+ " does not exist", 0);
				}
				if (prereqCourse == null) {
					throw new ParseException(
							"Course entered in requisite equation does not exist",
							0);
				}
				// -------

				if (isPreRequisite) {
					PreRequisite.createLeaf(true, (PreRequisite) courseParent,
							prereqCourse);
				} else {
					CoRequisite.createLeaf(true, (CoRequisite) courseParent,
							prereqCourse);
				}
				return courseParent;
			}
		}
	}

	// Function to break apart boolean expression into an array of Strings
	public static List<String> breakString(String string) {
		List<String> brokenString = new ArrayList<String>();

		// Temp variable to hold Strings
		StringBuilder temp = new StringBuilder("");
		// Loop through every character
		for (int i = 0; i < string.length(); i++) {
			switch (string.substring(i, i + 1)) {
			case "(":
				// If there is something in temp, then add it to the String
				// array and clear temp
				if (temp.length() > 0) {
					brokenString.add(temp.toString().toUpperCase());
					temp = new StringBuilder("");
				}
				// add this to String array
				brokenString.add(string.substring(i, i + 1));
				break;
			case " ":
				// If there is something in temp, then add it to the String
				// array and clear temp
				if (temp.length() > 0) {
					brokenString.add(temp.toString().toUpperCase());
					temp = new StringBuilder("");
				}
				// Ignore a space
				break;
			case ")":
				// If there is something in temp, then add it to the String
				// array and clear temp
				if (temp.length() > 0) {
					brokenString.add(temp.toString().toUpperCase());
					temp = new StringBuilder("");
				}
				// add this to String array
				brokenString.add(string.substring(i, i + 1));
				break;
			default:
				// This is for course numbers and relationships
				temp.append(string.substring(i, i + 1));
				break;
			}
		}
		// If temp contains a final string, add it before returning brokenString
		if (temp.length() > 0) {
			brokenString.add(temp.toString().toUpperCase());
		}

		// If there are inner and outer paranthesis, remove for ease of
		// processing
		if (brokenString.get(0).equals("(")
				&& brokenString.get(brokenString.size() - 1).equals(")")) {
			brokenString.remove(brokenString.size() - 1);
			brokenString.remove(0);
		}

		return brokenString;
	}

	public static List<Term> TermGenerator(Integer enterYear,
			Integer noOfSemester) {
		List<Term> termL = new ArrayList<>();

		Semester semInit = Semester.FALL;
		for (int terms = 0; terms < noOfSemester; terms++) {

			if (semInit == Semester.FALL) {
				Term term = Term.create(Semester.FALL, enterYear);
				termL.add(term);
				semInit = Semester.SPRING;
			} else {
				Term term = Term.create(semInit, enterYear += 1);
				termL.add(term);
				semInit = Semester.FALL;
			}
		}

		return termL;
	}

}