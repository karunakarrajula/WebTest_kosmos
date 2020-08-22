package com.pearson.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import com.pearson.pages.Login;
import com.pearson.testbed.core.TestBedConfig;
import com.pearson.util.LogUtil;

public class TestBed extends TestBedConfig {
	static Log log = LogUtil.getLog(TestBed.class);
	protected TestBedConfig testBedConfig;
	private String currentTestBedName = "";
	public static HashMap<Long, String> currentTestBedNameMap = new HashMap<Long, String>();
	public Login loginTest;

	@BeforeTest(alwaysRun = true)
	public void prepareBeforeTest() {
		try {

			String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\Config.txt";
			//String filePath = "C:\\Users\\karun\\Desktop\\WebTest\\WebTest\\src\\main\\resources\\Config.txt";
			TestBedConfig.readConfig(filePath);
			testBedConfig = TestBedConfig.getInstance();
			// Initialize driver
			driverInit(getCurrentTestBedName());
			currentTestBedNameMap.put(Thread.currentThread().getId(), getCurrentTestBedName());
			log.info("Current testbed Name:::" + currentTestBedNameMap.get(Thread.currentThread().getId()));
			loginTest = new Login(testBedConfig.getDriver());
		} catch (Exception iex) {
			iex.printStackTrace();
			log.error("Unable to load the property file::: " + iex.getMessage());
		}
	}

	/**
	 * driverCleanUp :- Clean up driver session once test execution is completed
	 */
	@AfterSuite(alwaysRun = true)
	public void driverCleanUp() throws InterruptedException {
		testBedConfig = TestBedConfig.getInstance();
		try {
			if (testBedConfig.getDriver() == null) {
				log.error("Unable to close Selenium as driver is null");
			} else {
				testBedConfig.getDriver().quit();
				log.info("Driver is closed");
			}
		} catch (Exception e) {
			log.error("Exception encountered in driverCleanUp():::" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * driverInit :- Initialize Android driver
	 */
	private void driverInit(String testBedName) {
		testBedConfig = TestBedConfig.getInstance();
		log.info("Initializing WebDriver  for testBedName:::" + testBedName);
		WebDriver driver = null;
		try {
			driver = createDriver.getDriver(null, testBedConfig, testBedName);
			log.info(testBedName + " Driver created!! Driver details :::" + driver.toString());
			testBedConfig.setDriver(driver);
		} catch (Exception e) {
			log.error("Unable to initialize driver :::" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Method to set current test bed name
	 */
	public void setCurrentTestBedName(String testBedName) {
		this.currentTestBedName = testBedName;
	}

	/**
	 * Method to get current test bed name
	 */
	public String getCurrentTestBedName() {
		return this.currentTestBedName;
	}
	
	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

}
