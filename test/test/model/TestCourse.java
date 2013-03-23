package test.model;

import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import model.*;

public class TestCourse {
/*
	@Test
	public void create() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				// Test to create a course

				Course cmain = Course.create(Prefix.getById(1), 303, "TestCourse", 3, 300, null, null);
				Course cprereq1 = Course.create(Prefix.getById(1), 302, "TestCo1", 3, 300, null, null);
				Course cprereq2 = Course.create(Prefix.getById(1), 299, "TestCo2", 3, 200, null, null);
				
				CoRequisite proot = CoRequisite.createNode(true, null, Junction.OR);		
				cmain.setCoreq(proot);
				CoRequisite.createLeaf(true, proot, cprereq1);
				CoRequisite.createLeaf(true, proot, cprereq2);
				
				
				
				
				
				// assertThat(prefix.equals(prefix)).isTrue();
				//c.setPrereq(prereq );
			}
		});
	}
	@Test
	public void testComplexRrereqFormula() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				// Example:
				// courseWithPrereq has the following prerequisite: ((courseA or courseB) and courseC)
				
				Course courseA = Course.create(Prefix.getById(1), 302, "courseA", 3, 300, null, null);
				Course courseB = Course.create(Prefix.getById(1), 299, "courseB", 3, 200, null, null);
				Course courseC = Course.create(Prefix.getById(1), 280, "courseC", 3, 200, null, null);

				PreRequisite rootOfTheFormula = PreRequisite.createNode(true, null, Junction.AND);
				PreRequisite rootOfAorBsubFormula = PreRequisite.createNode(true, rootOfTheFormula, Junction.OR);
				PreRequisite.createLeaf(true, rootOfTheFormula, courseC);
				PreRequisite.createLeaf(true, rootOfAorBsubFormula, courseA);
				PreRequisite.createLeaf(true, rootOfAorBsubFormula, courseB);
				
				Course courseWithPrereq = Course.create(Prefix.getById(1), 280, "courseC", 3, 200, rootOfTheFormula, null);
				// or
				courseWithPrereq.setPrereq(rootOfTheFormula);
			}
		});
	}*/
/*
	public void update() {
		running(fakeApplication(), new Runnable() {
			public void run() {
			}
			// Test to update
			
			//  List<Course> courseList = Course.getAll(); Course c =
			 // courseList.get(2); c.update("newTest", 8);
			 
		});

	}

	public void delete() {
		running(fakeApplication(), new Runnable() {
			public void run() {
			}
			//Test to Delete
        	//Course.delete(20);
		});

	}
*/
}