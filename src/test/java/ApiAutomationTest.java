
import io.restassured.path.json.JsonPath;
import utils.ExtentReportManager;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiAutomationTest {

    String userId="";
    @BeforeClass
    public void setUp() {
        ExtentReportManager.setupExtentReport();
        RestAssured.baseURI="https://reqres.in/";
    }

    @Test(priority = 1)
    public void getAllUsers(){
        ExtentReportManager.startTest("GET: Get All Users");
        Response response=given().when().get("/api/users").then().log().all().extract().response();
        ExtentReportManager.test.info(response.asPrettyString());

        try {
            Assert.assertEquals(response.getStatusCode(), 200);
            ExtentReportManager.test.pass("Status code is 200 as expected");
        } catch (AssertionError e) {
            ExtentReportManager.test.fail("Expected status code 200, but got " + response.getStatusCode());
            throw e;
        }
    }

    @Test(priority = 2)
    public void createUser(){
        ExtentReportManager.startTest("POST: Create User");
        JsonObject object = new JsonObject();
        object.addProperty("name","kamal");
        object.addProperty("job","SDET");
        Response response=given().body(object).log().all().when().post("/api/users").then().log().all().extract().response();
        ExtentReportManager.test.info(response.asPrettyString());

        try {
            Assert.assertEquals(response.getStatusCode(), 201);
            ExtentReportManager.test.pass("Status code is "+response.getStatusCode()+" as expected");
            JsonPath path = new JsonPath(response.asString());
            userId=path.getString("id");
            ExtentReportManager.test.info("UserId is "+userId);

        } catch (AssertionError e) {
            ExtentReportManager.test.fail("Expected status code 201, but got " + response.getStatusCode());
            throw e;
        }
    }

    @Test(priority  =3)
    public void getSpecificUser(){
        ExtentReportManager.startTest("GET: Get Specific Users");
        Response response=given().log().all().when().get("/api/users/2").then().log().all().extract().response();
        ExtentReportManager.test.info(response.asPrettyString());

        try {
            Assert.assertEquals(response.getStatusCode(), 200);
            ExtentReportManager.test.pass("Status code is 200 as expected");
        } catch (AssertionError e) {
            ExtentReportManager.test.fail("Expected status code 200, but got " + response.getStatusCode());
            throw e;
        }
    }
    @Test(priority = 4,dependsOnMethods = "createUser")
    public void updateUser(){
        ExtentReportManager.startTest("PUT: Update User");
        JsonObject object = new JsonObject();
        object.addProperty("job","QA");
        Response response=given().body(object).log().all().when().put("/api/users/"+userId).then().log().all().extract().response();
        ExtentReportManager.test.info(response.asPrettyString());

        try {
            Assert.assertEquals(response.getStatusCode(), 200);
            ExtentReportManager.test.pass("Status code is "+response.getStatusCode()+" as expected");

        } catch (AssertionError e) {
            ExtentReportManager.test.fail("Expected status code 201, but got " + response.getStatusCode());
            throw e;
        }
    }

    @Test(priority = 5,dependsOnMethods = "createUser")
    public void deleteUser(){
        ExtentReportManager.startTest("DELETE: Delete User");
        Response response=given().log().all().when().delete("/api/users/"+userId).then().log().all().extract().response();

        try {
            Assert.assertEquals(response.getStatusCode(), 204);
            ExtentReportManager.test.pass("Status code is "+response.getStatusCode()+" as expected");


        } catch (AssertionError e) {
            ExtentReportManager.test.fail("Expected status code 201, but got " + response.getStatusCode());
            throw e;
        }
    }

    @AfterClass
    public void endTest() {
        ExtentReportManager.endTest();
    }
}
