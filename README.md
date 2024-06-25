1. Under testngsuite package two testng.xml has been created-> Api.xml for API automation and web.xml for UI Automation
   
UI Automation Flow-
1. Execution flow begins from src->test->java->UIAutomationTest.java in which defined @BeforeClass to initialize the ExtentReport object.
2. Then @Test contains basic execution of the test case and the assertions and screenshot on assertion success and failure
3. then @AfterClass flush the report


Api Automation Flow-
1. Execution flow begins from src->test->java->ApiAutomationTest.java in which defined @BeforeClass to initialize the ExtentReport object.
2. Contains the Tests for POST,GET,PUT,DELETE.for api https://reqres.in/ Priortized accordingly 
3. then @AfterClass flush the report

   src->main->java->utils->ExtentReportManager.java contains the extentReport handling
   src->main->resources->chromedriver contains chromedriver required for execution
   reports present under /target/extenreport.html
