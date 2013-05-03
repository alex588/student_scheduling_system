package test.controllers;

import java.text.ParseException;
import java.util.List;

import model.ComplexCourseGroup;
import model.ComplexRequirement;
import model.CourseGroup;
import model.Junction;
import model.Requirement;
import model.SimpleRequirement;
import controllers.util.*;
import model.*;

import org.junit.Assert;
import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class TestConverter {

	@Test
	public void initData() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				ComplexCourseGroup newGroup;
				try {
					newGroup = Converter.convertGroupCombo("HUM100 AND HUM300",
							"New Course Group", "NEW", true);
					String awesome;
					awesome = Converter.convertGroupCombo(newGroup);

					ComplexCourseGroup.delete(newGroup);

					SimpleRequirement sreq1 = SimpleRequirement.create("Sreq1",
							"Sreq1", 2, CourseGroup.getAll().get(0));
					SimpleRequirement sreq2 = SimpleRequirement.create("Sreq2",
							"Sreq2", 4, CourseGroup.getAll().get(1));

					ComplexRequirement creq = Converter
							.convertRequirementCombo("Sreq2 AND Sreq1", "New",
									"NEW", true);
					awesome = Converter.convertRequirementCombo(creq);

					List<String> list = Converter.breakString(awesome);

					for (String str : list) {
						if (str.equals(")") || str.equals("(")) {

						} else if (str.equals(Junction.AND.toString())
								|| str.equals(Junction.OR.toString())) {
							Assert.assertNotNull(str);
						} else {
							Requirement req = Requirement
									.getByAbbreviation(str);
							Assert.assertNotNull(req);
						}

					}
					Requirement.delete(creq);
					Requirement.delete(sreq1);
					Requirement.delete(sreq2);
					Requisite req;
					req = Converter.convertPreCorReq(
							"CH115 AND (CH116 OR CH117)", true);
					String str = Converter.convertPreCorReq(req);
					Assert.assertTrue("CH115 AND (CH116 OR CH117)".equalsIgnoreCase(str));
				} catch (ParseException e) {
					Assert.assertTrue(false);
					e.printStackTrace();
				}

			}
		});
	}
}
