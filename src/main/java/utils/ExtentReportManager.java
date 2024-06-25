package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
        public static ExtentReports extent;
        public static ExtentTest test;


        public static void setupExtentReport() {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("OS", "Windows");
            extent.setSystemInfo("Browser", "Chrome");
        }

        public static void startTest(String testName) {
            test = extent.createTest(testName);
        }

        public static void endTest() {
            extent.flush();
        }
}
