package com.base;

import com.pageclasses.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.utils.DateUtils;
import com.utils.ExtentReportManager;

public class BaseUI {
	public WebDriver driver;
	public Properties prop;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	/**************** Invoke Browser ********************/
	public void invokeBrowser(String browserName, String osName) {
		if (this.prop == null) {
			this.prop = new Properties();

			try {
				FileInputStream file = new FileInputStream(
						System.getProperty("user.dir")
								+ "\\src\\test\\resources\\Repository\\configProperties.properties");
				prop.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger = report.createTest("Mobile Name Report");
			report.setSystemInfo("OS", prop.getProperty(osName));
			report.setSystemInfo("Browser", prop.getProperty(browserName));

			try {
				if (prop.getProperty(browserName).equalsIgnoreCase("Chrome")) {
					System.setProperty(
							"webdriver.chrome.driver",
							System.getProperty("user.dir")
									+ prop.getProperty("chromeDriver"));
					driver = new ChromeDriver();
				} else if (prop.getProperty(browserName).equalsIgnoreCase(
						"Mozila")) {
					System.setProperty(
							"webdriver.gecko.driver",
							System.getProperty("user.dir")
									+ prop.getProperty("mozilaDriver"));
					driver = new FirefoxDriver();
				}

			} catch (Exception e) {
				reportFail(e.getMessage());
			}
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();

		}

	}

	/*************** To open website *********************/
	public LandingPage openURL(String websiteURL) {
		LandingPage landingPage = new LandingPage(driver, logger, prop);
		try {
			driver.get(prop.getProperty(websiteURL));
			PageFactory.initElements(driver, landingPage);
			reportPass(websiteURL + " Identified Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return landingPage;
	}

	/***************** To close browser *******************/
	public void closeBrowser() {
		// logger.log(Status.PASS, "All name stored in excel");
		driver.close();
	}

	/***************** To click element *******************/
	public void elementclick(String locateKey) {
		try {
			WebElement ele = getElement(locateKey);
			ele.click();
			reportPass(locateKey + " : Element Clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/***************** To enter value on given location *******************/
	public void enterValue(String locateKey, String text) {
		try {
			WebElement ele = getElement(locateKey);
			ele.sendKeys(text);
			reportPass(text + " - Entered successfully in locator Element : "
					+ locateKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/**************** To find and return Webelement ********************/
	public WebElement getElement(String locateKey) {
		WebElement element = null;
		try {
			if (locateKey.endsWith("_id")) {
				element = driver
						.findElement(By.id(prop.getProperty(locateKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locateKey);
			} else if (locateKey.endsWith("_name")) {
				element = driver.findElement(By.name(prop
						.getProperty(locateKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locateKey);
			} else if (locateKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(prop
						.getProperty(locateKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locateKey);
			} else if (locateKey.endsWith("_classname")) {
				element = driver.findElement(By.className(prop
						.getProperty(locateKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locateKey);
			} else if (locateKey.endsWith("_css")) {
				element = driver.findElement(By.cssSelector(prop
						.getProperty(locateKey)));
			}

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return element;
	}

	/*************** To wait for page load *********************/
	public void waitForPageLoad(String page) {
		while (!prop.getProperty(page).equalsIgnoreCase(title())) {
			waitLoad(1);
		}
	}

	/*************** For Delay *********************/
	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************** To select element **********************/
	public void selectElement(String locaterKey, String value) {
		try {
			WebElement ele = getElement(locaterKey);
			Select sel = new Select(ele);
			sel.selectByValue(prop.getProperty(value));
			logger.log(Status.INFO, "Selected the Defined Value : " + value);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public String text(String locaterKey) {
		WebElement ele = null;
		try {
			ele = getElement(locaterKey);
			logger.log(Status.INFO, "Found the Defined Value : " + locaterKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return ele.getText();
	}

	public void verifyPageTitle(String pageTitle) {
		try {
			String actualTite = driver.getTitle();
			logger.log(Status.INFO, "Actual Title is : " + actualTite);
			logger.log(Status.INFO,
					"Expected Title is : " + prop.getProperty(pageTitle));
			Assert.assertEquals(actualTite, prop.getProperty(pageTitle));
		} catch (AssertionError e) {
			reportFail(e.getMessage());
		}
	}

	/************ To get page title ************************/

	public String title() {
		return driver.getTitle();
	}

	@AfterMethod
	public void reportflush() {
		report.flush();
	}

	/*************** To take screenshot *********************/
	public void takeScreenShots() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir")
				+ "\\ScreenShots\\" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir")
					+ "\\ScreenShots\\" + DateUtils.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*************** To report failure *********************/

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShots();
		Assert.fail(reportString);

	}

	/************** To report pass **********************/
	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	public void switchWindow(){
		String parent = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String w : windows){
			if(!w.equals(parent)){
				driver.switchTo().window(w);
				break;
			}
		}
	}
}
