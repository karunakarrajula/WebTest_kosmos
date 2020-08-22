package com.pearson.testbed.core;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.pearson.driver.CreateDriver;

public class TestBedConfig {

	public static DesiredCapabilities cap = null;
	public static int maxWaitSeconds = 20;// 85
	public static int maxImplicitWaitSeconds = 3;
	public static int maxInvisibletWaitSeconds = 3;
	public static String chromeDriverPath = null;
	public static String edgeDriverPath = null;
	public static String baseURL = null;
	public static Configuration config = null;
	protected static Properties props;
	protected WebDriver driver = null;
	protected static String env = null;
	protected static String configFile = null;
	public static String screenshotPath = null;
	public static String testBedNames = null;
	public CreateDriver createDriver;
	private static TestBedConfig instance;

	public TestBedConfig() {
		createDriver = new CreateDriver();
	}

	public static TestBedConfig getInstance() {
		if (instance == null) {
			synchronized (TestBedConfig.class) {
				if (instance == null) {
					instance = new TestBedConfig();
				}
			}
		}
		return instance;
	}

	/*
	 * readConfig :- Reads config file
	 */
	public static void readConfig(String configFilePath) {
		try {
			props = new Properties();
			File configFile = new File(configFilePath);
			System.out.println("Absolute file path - " + configFile.getAbsolutePath());
			FileReader configReadder = new FileReader(configFile.getAbsolutePath());
			props.load(configReadder);
			config = new Configuration(configFile);
			setVariable(props);
		} catch (Exception e) {
			System.out.println("Exception in readConfig:::" + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * setVariable :- Sets variables for test env
	 */
	private static void setVariable(Properties props) {
		try {
			env = props.getProperty("ENV");
			maxWaitSeconds = Integer.parseInt(props.getProperty("MAX_WAIT_TIME_SECONDS"));
			maxImplicitWaitSeconds = Integer.parseInt(props.getProperty("IMPLICIT_WAIT_TIME_SECONDS"));
			maxInvisibletWaitSeconds = Integer.parseInt(props.getProperty("MAX_INVISIBLE_WAIT_TIME_SECONDS"));
			testBedNames = props.getProperty("TESTBEDNAME");
			screenshotPath = props.getProperty("SCREENSHOTPATH").replace("DeviceType", getTestBedName());
			chromeDriverPath = props.getProperty("CHROMEDRIVERPATH");
			edgeDriverPath = props.getProperty("EDGEDRIVERPATH");
			baseURL = props.getProperty("BASEURL");
		} catch (Exception e) {
			System.out.println("Exception in setVariable:::" + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * setDriver :- Set Android driver for current object
	 */
	public void setDriver(WebDriver dr) {
		this.driver = dr;
	}

	/*
	 * getDriver :- Get current Android driver
	 */
	public WebDriver getDriver() {
		return this.driver;
	}

	/*
	 * getDataSheetPath :- Get getDataSheetPath value
	 */
	public static String getScreenshotPath() {
		return screenshotPath;
	}

	public String getEnv() {
		return env;
	}

	public static String getTestBedName() {
		return props.getProperty("TESTBEDNAME");
	}

	/**
	 * Method to get property by given name
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getProperty(String propertyName) {
		if (System.getenv(propertyName) == null)
			return props.getProperty(propertyName);
		else
			return System.getenv(propertyName);
	}

}