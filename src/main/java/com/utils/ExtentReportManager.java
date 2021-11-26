package com.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReportManager {

	// public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;

	public static ExtentReports getReportInstance() {

		if (report == null) {
			String reportName = DateUtils.getTimeStamp() + ".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "\\test-output\\"
							+ reportName);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);

			htmlReporter.config().setDocumentTitle("Arintra UI Automation Results");
			htmlReporter.config().setReportName("Arintra UI Test Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		}

		return report;
	}

}
