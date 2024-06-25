import utils.ExtentReportManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class UIAutomationTest {

    WebDriver driver;
    @BeforeClass
    public void setUp() {
        ExtentReportManager.setupExtentReport();
    }

    @Test
    public void amazonSearchSuccess() throws IOException {
        ExtentReportManager.startTest("UI Automation Test");
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//main//resources//chromedriver");

        ChromeOptions options= new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://www.amazon.com/");

        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("iphone");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        By searchResultTxt=By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-3']/descendant::span[contains(@class,'a-text-normal')]");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultTxt));

        String WebElementText=driver.findElement(searchResultTxt).getText();

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile=screenshot.getScreenshotAs(OutputType.FILE);
        String destinationFile=System.getProperty("user.dir")+"//target//screenshot"+ RandomStringUtils.randomAlphanumeric(4)+".png";
        FileUtils.copyFile(screenshotFile,new File(destinationFile));

        if(WebElementText.contains("Apple iPhone")){

            ExtentReportManager.test.pass("Validation Success");
            ExtentReportManager.test.addScreenCaptureFromPath(destinationFile);

        }
        else{
            ExtentReportManager.test.fail("Validation Failure");
            ExtentReportManager.test.addScreenCaptureFromPath(destinationFile);
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterClass
    public void endTest() {
        ExtentReportManager.endTest();
    }
}
