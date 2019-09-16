package com.transferwise.reporter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporter implements IReporter {
	private ExtentReports extent;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExecutionReport.html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult res : result.values()) {
				ITestContext testContext = res.getTestContext();

				buildResult(testContext.getPassedTests(), LogStatus.PASS);
				buildResult(testContext.getFailedTests(), LogStatus.FAIL);
				buildResult(testContext.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	private void buildResult(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult testResult : tests.getAllResults()) {
				test = extent.startTest(testResult.getMethod().getMethodName());

				test.setStartedTime(getCurrentTime(testResult.getStartMillis()));
				test.setEndedTime(getCurrentTime(testResult.getEndMillis()));

				if (testResult.getThrowable() != null) {
					test.log(status, testResult.getThrowable());
				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed successfully");
				}

				extent.endTest(test);
			}
		}
	}

	private Date getCurrentTime(long milliSeconds) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		
		return calendar.getTime();
		
	}
}