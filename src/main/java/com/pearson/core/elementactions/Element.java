package com.pearson.core.elementactions;

import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pearson.testbed.core.TestBedConfig;
import com.pearson.util.LogUtil;


public class Element {

	protected WebDriver driver;
	static Log log = LogUtil.getLog(Element.class);

	public Element(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElementByLocator(By locator) {
		WebElement element = null;
		try {
			// Wait for the element to be visible
			WebDriverWait wait = new WebDriverWait(driver, TestBedConfig.maxWaitSeconds);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			log.info("Element found ::getElementByLocator::with locator : " + locator);
		} catch (TimeoutException e) {
			log.error(" Failed to find element even on Explicit wait using locator - " + locator + " Error Message:"
					+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Failed to find element even on Explicit wait using locator - " + locator + " Error Message :"
					+ e.getMessage());
			e.printStackTrace();
		}
		if (element == null) {
			log.error("Failed to find element getElementByLocator as element is NULL  with locator : " + locator);
		}
		return element;
	}

	/**
	 * Method to get element text
	 *
	 * @param locator
	 * @return text of element
	 */
	public String getElementText(By locator) {
		String text = "";
		try {
			WebElement element = getElementByLocator(locator);
			if (element != null)
				text = element.getText().trim();
			else {
				log.info("getElementText is NULL>>>>Actual Element text:::" + text);
				return text;
			}
			log.info("Actual Element text::: " + text);
		} catch (Exception ex) {
			log.error("Exception occured while getting element text for:::" + locator);
			ex.printStackTrace();
		}
		return getUTF8Text(text);
	}

	public String getElementText(WebElement element) {
		String text = "";
		try {
			if (element != null)
				text = element.getText().trim();
			else {
				log.info("getElementText is NULL>>>>Actual Element text:::" + text);
				return text;
			}
			log.info("Actual Element text::: " + text);
		} catch (Exception ex) {
			log.error("Exception occured while getting element text for:::");
			log.error("Error :- " + ex.getMessage());
			ex.printStackTrace();
		}
		return getUTF8Text(text);
	}

	/**
	 * Method to convert Actual text to UTF8
	 *
	 * @param text of element
	 * @return UTF8 encoded text
	 */
	public String getUTF8Text(String text) {
		String retText = "";
		try {
			retText = new String(text.getBytes("UTF8"), "UTF8");
			// log.info("UTF8 Encoded text::: " + retText);
		} catch (Exception ex) {
			log.error("Exception occured while encoding text");
			log.error("Error :- " + ex.getMessage());
			ex.printStackTrace();
		}
		return retText;
	}

	/**
	 * Method to send Keys on element with some test data as string
	 *
	 * @param locator
	 * @param text
	 */
	public boolean sendKeys(By locator, String text) {
		try {
			WebElement element = getElementByLocator(locator);
			element.clear();
			element.sendKeys(text);
			log.info("Sending text on element having locator:::" + locator + " with text :::" + text);
			return true;
		} catch (Exception e) {
			log.error("Failed to send keys to element having locator:::" + locator + " due to :::" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (Exception e) {
			log.error("Exception in sleep" + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean click(By locator) {
		try {
			WebElement mapElement = getElementByLocator(locator);
			if (mapElement != null) {
				mapElement.click();
				log.info("Clicked on Element having locator:::" + locator);
				return true;
			} else {
				log.info("Failed to click on element as element is NULL ::: Locator:::" + locator);
				return false;
			}
		} catch (Exception e) {
			log.error("Unable to click on element :::" + locator);
			e.printStackTrace();
		}
		return false;
	}

	public boolean clickOnElement(WebElement webElement) {
		try {
			if (webElement != null) {
				webElement.click();
				sleep(1000);
				log.info("Clicked on webElement ");
				return true;
			} else {
				log.info("Unable to click on Element as element is null");
				return false;
			}
		} catch (Exception e) {
			log.error("Unable to click on webElement due to error message :::" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public List<WebElement> getListOfElementsByLocator(By locator) {
		return getElementsByLocator(locator);
	}

	public List<WebElement> getElementsByLocator(By locator) {
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(locator);
			log.info("Size of List element:::" + elements.size());
		} catch (TimeoutException e) {
			log.error("Element could not be located TimeoutException using locator - " + locator + " due to "
					+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Element could not be located using locator - " + locator + " due to " + e.getMessage());
			e.printStackTrace();
		}
		return elements;
	}

	public void navigateToURL(String url) {
		driver.get(TestBedConfig.baseURL + url);
		waitForLoad(driver);
	}

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, TestBedConfig.maxInvisibletWaitSeconds);
		wait.until(pageLoadCondition);
	}
}
