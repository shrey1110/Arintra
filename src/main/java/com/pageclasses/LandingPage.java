package com.pageclasses;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.BaseUI;

public class LandingPage extends BaseUI {

	public LandingPage(WebDriver driver, ExtentTest logger, Properties prop) {
		this.driver = driver;
		this.prop = prop;
		this.logger = logger;
		logger.log(Status.INFO, "On landing page");
	}


	public void requestDemo() throws InterruptedException {
		elementclick("requestDemo_xpath");
		Thread.sleep(2000);
	}

	public void datafill() throws InterruptedException {
		enterValue("name_id","Shreyansh");
		enterValue("org_id","Arinta");
		enterValue("email_id","shreyanshsurana@gmail.com");
		enterValue("phone_id","9854712537");
		Thread.sleep(2000);
		//elementclick("scheduleDemo_xpath");
		//Actions actions = new Actions(driver);

		//actions.moveToElement(getElement("scheduleDemo_xpath")).click().perform();
		JavascriptExecutor jse = (JavascriptExecutor)driver;

		jse.executeScript("arguments[0].scrollIntoView()", getElement("scheduleDemo_xpath"));
		waitLoad(1);
		elementclick("scheduleDemo_xpath");


	}



}
