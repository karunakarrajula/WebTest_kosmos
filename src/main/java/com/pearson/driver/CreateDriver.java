package com.pearson.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.pearson.testbed.core.TestBedConfig;

public class CreateDriver {

	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public void createLocalDriver(DesiredCapabilities capabilities, TestBedConfig testBedConfig, String testBedName) {
		WebDriver driver = null;
		try {
			String browserName = testBedConfig.getTestBedName().trim();
			switch (browserName) {
			case "Chrome":
				System.setProperty("webdriver.chrome.driver", TestBedConfig.chromeDriverPath);
				driver = new ChromeDriver();
				break;

			case "Firefox":
				driver = new FirefoxDriver();
				break;
			case "Edge":
				System.setProperty("webdriver.edge.driver", TestBedConfig.edgeDriverPath);
				driver = new EdgeDriver();
				break;

			default:
				driver = new ChromeDriver();
			}

			driver.manage().timeouts().pageLoadTimeout(
					Long.parseLong(testBedConfig.config.getValue("MAX_INVISIBLE_WAIT_TIME_SECONDS")), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(
					Long.parseLong(testBedConfig.config.getValue("IMPLICIT_WAIT_TIME_SECONDS")), TimeUnit.SECONDS);
			driver.manage().window().maximize();

			setDriver(driver);
			System.out.println(browserName + " WebDriver successfully created:::createLocalDriver");
		} catch (Exception ex) {
			System.out.println("Error while createLocalDriver:::" + ex.getMessage());
			ex.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Throwable while createLocalDriver:::" + e.getMessage());
		}
	}

	/**
	 * Method to get driver
	 * 
	 * @param capabilities
	 * @param testBedConfig
	 * @return
	 */
	public WebDriver getDriver(DesiredCapabilities capabilities, TestBedConfig testBedConfig, String testBedName) {
		if (webDriver.get() == null) {
			createLocalDriver(capabilities, testBedConfig, testBedName);
		}
		return webDriver.get();
	}

	/**
	 * Method to set driver
	 * 
	 * @param localDriver
	 */
	public void setDriver(WebDriver localDriver) {
		webDriver.set(localDriver);
	}

	/**
	 * Method to get driver
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return webDriver.get();
	}

}
