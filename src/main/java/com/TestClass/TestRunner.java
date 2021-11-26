package com.TestClass;

import java.util.Properties;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.base.BaseUI;
import com.pageclasses.LandingPage;

public class TestRunner extends BaseUI {
	LandingPage landingpage;
	//MobilePage mobilepage;
	Properties prop = new Properties();
	ExtentTest logger;

	@Test
	public void ScheduleDemo() throws InterruptedException {
		BaseUI bu = new BaseUI();
		bu.invokeBrowser("browserName", "osName");
		landingpage = bu.openURL("websiteURL");
		landingpage.requestDemo();
		landingpage.datafill();
		bu.closeBrowser();

	}

}
