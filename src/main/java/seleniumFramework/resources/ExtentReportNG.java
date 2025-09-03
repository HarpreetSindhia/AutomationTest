package seleniumFramework.resources;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {

	public static ExtentReports getReportObject()
	{
		//Two classes -> ExtentSparkReporter , ExtentReports
		
		
		File path =	new File(System.getProperty("user.dir")+"//reports//index.html");
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Automation Test Report");
		
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Harpreet Sindhia", "Automation Tester");
		return extent;
		
		
	}
}
