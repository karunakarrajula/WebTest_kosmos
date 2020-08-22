package com.pearson.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.pearson.core.elementactions.Element;

public class Login extends Element {

	By loginIntroducingParentAccountPopUp = By.xpath("//iframe[@id='contentIframe']");
	By loginDialogCloseButton = By
			.xpath("//div[@class='dialogInprogDialog']//div//div//div//div//*[local-name()='svg']");
	By languageDropDown = By.className("accountDetailsLangDropDown");
	By defaultLanguage = By.xpath("//div[contains(text(),'English')]");
	By languageDropDownValues = By.xpath("//span[@role='menuitem']/div/div/div");
	By continueButton = By.xpath("//button[@type='submit']/div/div");

	By languageDropDownEnglish = By.xpath("//div[contains(text(),'English')]");
	By languageDropDownHindi = By.xpath("//div[2]//span[1]//div[1]//div[1]//div[1]");
	By languageDropDownEspanol = By.xpath("//div[contains(text(),'Español')]");

	String expectedContinueButtonEnglish = "CONTINUE";
	String expectedContinueButtonHindi = "अग्रसर रहें";
	String expectedContinueButtonEspanol = "CONTINUAR";

	public Login(WebDriver driver) {
		super(driver);
	}

	public int getLanguageDropDownValuesSize() {
		click(loginDialogCloseButton);
		click(languageDropDown);
		List<WebElement> lang = getElementsByLocator(languageDropDownValues);
		return lang.size();
	}

	public void verifySelectLanguageFunctionality() {
		SoftAssert softAssert = new SoftAssert();
		 click(loginDialogCloseButton);
		selectEnglishLanguage();
		softAssert.assertEquals(getContinueButtonText(), expectedContinueButtonEnglish,
				"[English locale] 'Continue' button text mismatched.");
		selectHindiLanguage();
		softAssert.assertEquals(getContinueButtonText(), expectedContinueButtonHindi,
				"[Hindi locale] 'Continue' button text mismatched.");
		selectEspanolLanguage();
		softAssert.assertEquals(getContinueButtonText(), expectedContinueButtonEspanol,
				"[Espanol locale] 'Continue' button text mismatched.");
		softAssert.assertAll();
	}

	public void selectEnglishLanguage() {
		click(languageDropDown);
		click(languageDropDownEnglish);
	}

	public void selectHindiLanguage() {
		click(languageDropDown);
		click(languageDropDownHindi);
	}

	public void selectEspanolLanguage() {
		click(languageDropDown);
		click(languageDropDownEspanol);
	}

	public String getContinueButtonText() {
		sleep(3000);
		return getElementText(continueButton);
	}

}
