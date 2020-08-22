package com.pearson.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends TestBed {

	@Test(priority = 1, enabled = true, description = "Verify language down count is atleast 3", groups = { "Sanity",
			"Smoke", "Regression" })
	public void verifyCountOfLanguageDropDown() {
		SoftAssert softAssert = new SoftAssert();
		loginTest.navigateToURL("login");
		int languageCount = loginTest.getLanguageDropDownValuesSize();
		softAssert.assertTrue(languageCount >= 3,
				"Actual Language Count is not more than 3. Actual count::" + languageCount);
		softAssert.assertAll();
	}

	@Test(priority = 2, enabled = true, description = "Verify that after Selecting the language 'CONTINUE' button text is as expected value", groups = {
			"Sanity", "Smoke", "Regression" })
	public void verifySelectLanguageFunctionality() {
		loginTest.navigateToURL("login");
		loginTest.verifySelectLanguageFunctionality();
	}
}
