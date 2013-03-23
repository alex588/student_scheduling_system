package controllers.util;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.lang.*;
import model.*;

import model.PreRequisite;

public class Converter {

	public static PreRequisite convertPreCorReq(String requisite) {
		if(isExpressionValid(requisite)){
			//Break requisite string into pieces
			List<String> equation = breakString(requisite);
			
			//Push into stack so that beginning is at the top
			Stack<String> stack = new Stack<String>();
			for(int i = equation.size()-1; i >= 0; i--){
				stack.push(equation.get(i));
			}
			//Set as atomic for reference
			AtomicReference<Stack<String>> stackRef = new AtomicReference<Stack<String>>();
			stackRef.set(stack);
			return buildTree(null, stackRef);
		}
		else{
			return null;
		}
	}

	public static String convertPreCorReq(PreRequisite requisite){
	
		return buildString(requisite);
	}
	
	private static boolean isExpressionValid(String string){
		if(true)
			return true;
		else
			return false;
	}
	//Recursive solution to create string from tree
	private static String buildString(PreRequisite parent){
		List<PreRequisite> children = parent.getChildren();
		if(children.isEmpty()){
			if(parent.getIsLeaf()){
				Course course = parent.getCourse();
				return Course.getPrefixCourseNo(Prefix.getById(course.getPrefixId()), course);
			}else{
				 if( parent.getJunction() == Junction.AND)
					 return " AND ";
				 else
					 return " OR ";
			}
		}else{
			String string = "(";
			for(int i = 0; i < children.size()-1; i++){
				string = string + buildString(children.get(i));				
				if( parent.getJunction() == Junction.AND)
					 string = string + " AND ";
				 else
					 string = string + " OR ";
			}
			string = string + children.get(children.size()-1);
			string = string + ")";
			return string;
		}		
	}
	
	
	//Recursive solution to build tree
	private static PreRequisite buildTree(PreRequisite parent, AtomicReference<Stack<String>> stackRef ){
		Stack<String> stack = stackRef.get();
		//When finished, just return parent
		if(stack.isEmpty()){
			return parent;
		}else{
			//Check for junctions, not a switch so no duplicate junction code for AND and OR
			//Sets isPositive to false if junction is NOT and then moves to real junction
			//Creates node, sets stack and calls itself
			//When done returns itself as the parent
			if(stack.peek().equals("AND") || stack.peek().equals("OR") || stack.peek().equals("NOT")){
				boolean isPositive = true;
				if(stack.peek().equals("NOT")){
					isPositive = false;
					stack.pop();
				}
				Junction junction;
				if(stack.pop().equals("AND"))
					junction = Junction.AND;
				else
					junction = Junction.OR;
				PreRequisite newNode = PreRequisite.createNode(isPositive, parent, junction);
				stackRef.set(stack);
				buildTree(newNode, stackRef);
				return newNode;
			}
			//If at closing parenthesis, then return parent
			else if(stack.peek().equals(")")){
				stack.pop();
				stackRef.set(stack);
				return parent;
			}
			//If at opening parenthesis, then continue into subequation
			//Then when returned, continue on for what is after this subequation
			else if(stack.peek().equals("(")){
				stack.pop();
				stackRef.set(stack);
				buildTree(parent, stackRef);
				return buildTree(parent, stackRef);			}
			//Catcher for courses
			//Ignore course until parent node is returned
			//When parent node is returned, then create leaf and return new parent.
			else{
				String courseStr = stack.pop();
				stackRef.set(stack);
				PreRequisite courseParent = buildTree(parent, stackRef);
				//Get matching course
				Course prereqCourse = null;
				for(Course course : Course.getAll()){
					if(Course.getPrefixCourseNo(Prefix.getById(course.getPrefixId()), course).equals(courseStr)){
						prereqCourse = course;
					}
				}
				PreRequisite.createLeaf(true, courseParent, prereqCourse);
				return courseParent;
			}
		}
	}
	/*
	public static PreRequisite solveExpression(AtomicReference<Stack<String>> refStack, PreRequisite previousNode){
		Stack<String> stack = refStack.get();
		//Node for holding last parent created
		PreRequisite lastParent = null;
		//Node for holding any alone children
		PreRequisite aloneChild = null;
		//Bool for indicating Not
		boolean isPositive = true;
		
		while( !stack.empty()){
			switch(stack.peek()){
			case "(":
				//Popoff so it does not interfere later
				stack.pop();
				return lastParent;
				break;
			case "AND":
				//If it is the first parent created
				if(lastParent == null){
					lastParent = PreRequisite.createNode(stack.pop(), isPositive, null);
					//Reset isPositive
					isPositive = true;
				}else{//Else create new parent, set it as the previous parent's parent, and set new parent as last parent
					PreRequisite newParent = PreRequisite.createNode(stack.pop(), isPositive, null);
					lastParent.setParent(newParent);
					lastParent = newParent;
					//Reset isPositive
					isPositive = true;
				}
				//If there is an alone child, attach as child
				if(aloneChild != null){
					aloneChild.setParent(lastParent);
					aloneChild = null;
				}
				break;
			case "OR":
				//If it is the first parent created
				if(lastParent == null){
					lastParent = PreRequisite.createNode(stack.pop(), isPositive, null);
					//Reset isPositive
					isPositive = true;
				}else{//Else create new parent, set it as the previous parent's parent, and set new parent as last parent
					PreRequisite newParent = PreRequisite.createNode(stack.pop(), isPositive, null);
					lastParent.setParent(newParent);
					lastParent = newParent;
					//Reset isPositive
					isPositive = true;
				}
				//If there is an alone child, attach as child
				if(aloneChild != null){
					aloneChild.setParent(lastParent);
					aloneChild = null;
				}
				break;
			case "NOT":
				isPositive = false;
				break;
			case "NODE"://If section of expression has already been turned into a tree(A previous pass)
				//If there is a parent, attach it as a child
				if(lastParent != null){
					previousNode.setParent(lastParent);
				}else{//No parent, set as aloneChild
					aloneChild = previousNode;
				}
				break;
			default://Catcher for courses
				//Get Course for string course
				Course prereqCourse = null;
				for(Course course : Course.getAll()){
					if(course.getPrefixCourseNumber() == stack.peek()){
						prereqCourse = course;
					}
				}
				//If course was found, create child and store it
				if(prereqCourse != null){
					//If there is a parent, attach it as a child
					if(lastParent != null){
						PreRequisite newChild = PreRequisite.createLeaf(isPositive, lastParent, prereqCourse);
					}else{//No parent, set as aloneChild
						aloneChild = PreRequisite.createLeaf(isPositive, null, prereqCourse);
					}
				}
				break;
			}
		}
		return lastParent;
	}
	
	//Given expression as a list of Strings and returns parent node
	public static PreRequisite buildTree(List<String> expression){
		//Create a stack for iterating through the string
		Stack<String> stack = new Stack<String>();
		
		PreRequisite parent = null;
		
		//Iterate through expression
		for(String item : expression){
			switch(item){
			case ")":
				//If it is a closing parenthesis then process until previous parenthesis
				
				//Create an AtomicReference to pass stack by reference
				AtomicReference<Stack<String>> refStack = new AtomicReference<Stack<String>>(stack);
				
				parent = solveExpression(refStack, parent);
				
				//push keyword node signifying that there is section already converted to nodes there
				stack.push("NODE");
				break;
			default:
				//Else push onto stack for later processing
				stack.push(item);
				break;
			}
		}
		
		return parent;
	}
	*/
	//Function to break apart boolean expression into an array of Strings
	private static List<String> breakString(String string){
		List<String> brokenString = new ArrayList<String>();
		
		//Temp variable to hold Strings
		String temp = "";
		//Loop through every character
		for(int i = 0; i < string.length(); i++){
			switch(string.substring(i, i+1)){
			case "(":
				//If there is something in temp, then add it to the String array and clear temp
				if(temp.length() > 0){
					brokenString.add(temp.toUpperCase());
					temp = "";
				}
				//add this to String array
				brokenString.add(string.substring(i,i+1));
				break;
			case " ":
				//If there is something in temp, then add it to the String array and clear temp
				if(temp.length() > 0){
					brokenString.add(temp.toUpperCase());
					temp = "";
				}
				//Ignore a space
				break;
			case ")":
				//If there is something in temp, then add it to the String array and clear temp
				if(temp.length() > 0){
					brokenString.add(temp.toUpperCase());
					temp = "";
				}
				//add this to String array
				brokenString.add(string.substring(i,i+1));
				break;
			default:
				//This is for course numbers and relationships
				temp = temp + string.substring(i,i+1);
				break;
			}
		}
		//If temp contains a final string, add it before returning brokenString
		if(temp.length() > 0){
			brokenString.add(temp.toUpperCase());
		}
		
		return brokenString;
	}
}