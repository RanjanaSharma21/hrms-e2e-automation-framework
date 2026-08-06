package api.steps;

import api.models.Employee;
import api.payloads.Payloads;
import api.services.APIServices;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import ui.utils.ConfigReader;
import api.context.ScenarioContext;

import static io.restassured.RestAssured.given;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APISteps {

    private Response response;
    private Object payload;
    private List<String> requestIds;
    private Map<String, String> dynamicContextData; // Temporary store for dynamic strings
    private Employee expectedEmployeeRequest;
    public Employee expectedEmployeePOJO;
    // Just declaring the instance variable, not creating instance with new keyword
    public final ScenarioContext scenarioContextObject;
    public APISteps(ScenarioContext scenarioContextObject) {
        this.scenarioContextObject = scenarioContextObject;
    }

    /* 1. Authorization and Token */
    @Given("user sends valid credentials")
    public void user_sends_valid_credentials() {

        payload = Payloads.loginPayload(
                        ConfigReader.getProperty("apiUsername"),
                        ConfigReader.getProperty("apiPassword"));
    }
    @When("authentication API is called")
    public void authentication_api_is_called() {

        response = APIServices.login(payload);
    }
    @Then("token should be generated")
    public void token_should_be_generated() {

        String token = ConfigReader.getProperty("apiPrefix") + " " +
                response.jsonPath().getString("data.token");
        scenarioContextObject.setToken(token);
        Assert.assertNotNull("Token is null", scenarioContextObject.getToken());
        Assert.assertFalse("Token is empty", scenarioContextObject.getToken().isEmpty());
        scenarioContextObject.setContext("AUTH_TOKEN", scenarioContextObject.getToken());
    }
    @Given("a token is created")
    public void a_token_is_created() {

        Response response = APIServices.login(
                Payloads.loginPayload(
                        ConfigReader.getProperty("apiUsername"),
                        ConfigReader.getProperty("apiPassword")
                )
        );

        String token = ConfigReader.getProperty("apiPrefix") + " "
                + response.jsonPath().getString("data.token");

        scenarioContextObject.setToken(token);
        Assert.assertNotNull("Token was not generated", scenarioContextObject.getToken());
    }

    /* 2. a request is prepared to create an employee using various format */
    @Given("a request is prepared to create an employee using API")
    public void a_request_is_prepared_to_create_an_employee_using_api() {

        payload = Payloads.createEmployeePayload();
    }
    @Given("a request is prepared to create an employee using Map format")
    public void a_request_is_prepared_to_create_an_employee_using_map_format() {

        payload = Payloads.createEmployeePayloadMap();
    }
    @Given("a request is prepared to create an employee using JSON format")
    public void a_request_is_prepared_to_create_an_employee_using_json_format() {

        payload = Payloads.createEmployeePayloadJson();
    }
    @Given("a request is prepared to create an employee using dynamic JSON format with {string}, {string}, {string}, {string}, {string}, {string}")
    public void a_request_is_prepared_to_create_an_employee_using_dynamic_json_format_with(
            String firstName, String lastName, String middleName, String gender, String birthday, String jobTitle) {

        payload =
                Payloads.createEmployeePayloadJsonDynamic(
                        firstName,
                        lastName,
                        middleName,
                        gender,
                        birthday,
                        jobTitle
                );
    }
    @Given("a request is prepared to create an employee using lombok pojo format with {string}, {string}, {string}, {string}, {string}, {string}")
    public void a_request_is_prepared_to_create_an_employee_using_lombok_pojo_format_with(
            String firstName,
            String lastName,
            String middleName,
            String gender,
            String birthday,
            String jobTitle) {

        expectedEmployeePOJO =
                Payloads.createEmployeePayloadLombokPojo(
                        firstName,
                        lastName,
                        middleName,
                        gender,
                        birthday,
                        jobTitle
                );

        payload = expectedEmployeePOJO;
    }



    /* 3. POST service */
    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {

        response = APIServices.createEmployee(payload,
                scenarioContextObject.getToken());

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println(response.asPrettyString());
    }

    /* 4. Status code */
    @Then("the status code for this request is {int}")
    public void the_status_code_for_this_request_is(Integer statusCode) {

        response.then().assertThat().statusCode(statusCode);
        //Assert.assertEquals( statusCode, response.getStatusCode());

    }


    /* 5. Request body and Response body must match */
    @Then("the request body must match the response body")
    public void the_request_body_must_match_the_response_body() {
        // 3. validate the response. Validate body, key and value, response header, etc
        // we use assertions of hamcreast matchers (org.hamcrestmatchers)
        response.then().body("data.firstName", CoreMatchers.equalTo("Janki"));
        response.then().body("data.middleName", CoreMatchers.equalTo("Sita"));
        response.then().body("data.lastName", CoreMatchers.equalTo("Vallabh"));
        response.then().body("data.birthday", CoreMatchers.equalTo("1990-01-15"));
        response.then().body("data.gender", CoreMatchers.equalTo(2));
        response.then().header("Content-Type", CoreMatchers.equalTo("application/json"));


        /* OR

        String requestFirstName =
            ((Map<?, ?>) payload).get("firstName").toString();

        String responseFirstName =
            response.jsonPath().getString("data.firstName");


        String requestLastName =
            ((Map<?, ?>) payload).get("lastName").toString();

        String responseLastName =
            response.jsonPath().getString("data.lastName");


        String requestMiddleName =
            ((Map<?, ?>) payload).get("middleName").toString();

        String responseMiddleName =
            response.jsonPath().getString("data.middleName");


        String requestGender =
            ((Map<?, ?>) payload).get("gender").toString();

        String responseGender =
            response.jsonPath().getString("data.gender");


        String requestBirthday =
            ((Map<?, ?>) payload).get("birthday").toString();

        String responseBirthday =
            response.jsonPath().getString("data.birthday");


        String requestJobTitle =
            ((Map<?, ?>) payload).get("job_title").toString();

        String responseJobTitle =
            response.jsonPath().getString("data.job_title");


        Assert.assertEquals(requestFirstName, responseFirstName);
        Assert.assertEquals(requestLastName, responseLastName);
        Assert.assertEquals(requestMiddleName, responseMiddleName);
        Assert.assertEquals(requestGender, responseGender);
        Assert.assertEquals(requestBirthday, responseBirthday);
        Assert.assertEquals(requestJobTitle, responseJobTitle);
    */
    }
    @Then("the Map request body must match the response body")
    public void the_map_request_body_must_match_the_response_body() {
        // Extract both as Maps and compare them directly
        Map<String, Object> actualData = response.jsonPath().getMap("data");
        Map<String, Object> expectedData = (Map<String, Object>) payload;
        Assert.assertEquals(expectedData.get("firstName"), actualData.get("firstName"));
        Assert.assertEquals(expectedData.get("lastName"), actualData.get("lastName"));
        // Custom logic can check string formatting or map keys directly here
    }
    @Then("the JSON request body must match the response body")
    public void the_json_request_body_must_match_the_response_body() {
        Map<String, Object> actualData = response.jsonPath().getMap("data");
        // Assert directly against the known baseline hardcoded values
        Assert.assertEquals("Janki", actualData.get("firstName"));
        Assert.assertEquals("Vallabh", actualData.get("lastName"));
    }
    @Then("the dynamic JSON request body must match the response body")
    public void the_dynamic_json_request_body_must_match_the_response_body() {
        Map<String, Object> actualData = response.jsonPath().getMap("data");
        JSONObject expectedJson = new JSONObject((String) payload);
        Assert.assertEquals(expectedJson.get("firstName"), actualData.get("firstName"));
        Assert.assertEquals(expectedJson.get("lastName"), actualData.get("lastName"));
    }
    @Then("the POJO request body must match the response body")
    public void the_pojo_request_body_must_match_the_response_body() {
        // 1. Convert the nested JSON "data" block directly back to Employee object
        // Deserialization: Convert response JSON directly back to Employee model object
        // The Jackson library is trying to convert the nested "data" JSON block into Employee class.
        // However, the API response contains extra system-generated fields like "empNumber", "employeeId", and "terminationId".
        // Because those extra fields do not exist as variables inside your Employee class, Jackson crashes with an UnrecognizedPropertyException.
        // (With ignoreUnknown = true added in model, this line will now run perfectly!)
        Employee actualEmployeeResponse = response.jsonPath().getObject("data", Employee.class);

        // Perform your core string assertions
        // Core assertions using JUnit 4 format: Assert.assertEquals(message, expected, actual)
        Assert.assertEquals("First name mismatch", expectedEmployeePOJO.getFirstName(), actualEmployeeResponse.getFirstName());
        Assert.assertEquals("Last name mismatch", expectedEmployeePOJO.getLastName(), actualEmployeeResponse.getLastName());

        // Safe handling for empty/null middle names
        String expectedMiddle = expectedEmployeePOJO.getMiddleName() == null ? "" : expectedEmployeePOJO.getMiddleName().trim();
        String actualMiddle = actualEmployeeResponse.getMiddleName() == null ? "" : actualEmployeeResponse.getMiddleName().trim();
        Assert.assertEquals("Middle name mismatch", expectedMiddle, actualMiddle);

        Assert.assertEquals("Birthday mismatch", expectedEmployeePOJO.getBirthday(), actualEmployeeResponse.getBirthday());

        // Safely extract the gender integer and handle the custom Gender String-to-Integer validation securely
        int actualGenderCode = response.jsonPath().getInt("data.gender");
        int expectedGenderCode = expectedEmployeePOJO.getGender().equalsIgnoreCase("F") ? 2 : 1;
        Assert.assertEquals("Gender code mapping failed", expectedGenderCode, actualGenderCode);

    }


    /* 6. Employee Id is stored  */
    @Then("the employee is stored as global variable {string}")
    public void the_employee_is_stored_as_global_variable(String jsonPathForEmpid) {
        //Storing parsedEmpId as a class variable for this class to be shared by all methods
        // Storing parsedEmpId inside the scenarioContext map/storage container
        // so that it does not lose its value during parallel running where
        // different tests will overwrite that single static variable simultaneously,
        // causing random test failures.
        String parsedEmpId = response.jsonPath().getString(jsonPathForEmpid);
        // Store it safely inside the context map container
        scenarioContextObject.setContext("EMPLOYEE_ID", parsedEmpId);
        scenarioContextObject.setEmployeeId(parsedEmpId);
    }


    /* 7. Read Employee */
    @Given("a request is prepared to get an employee")
    public void a_request_is_prepared_to_get_an_employee() {

        Assert.assertNotNull(
                "Token is missing",
                scenarioContextObject.getToken()
        );

        Assert.assertNotNull(
                "Employee ID is missing",
                scenarioContextObject.getEmployeeId()
        );
    }
    @When("a GET call is made to get a created employee")
    public void a_get_call_is_made_to_get_a_created_employee() {

        response = APIServices.getEmployee(scenarioContextObject.getEmployeeId(),
                scenarioContextObject.getToken() );
    }
    @Then("the employee number {string} must match the stored employee number")
    public void the_employee_number_must_match_the_stored_employee_number(String jsonPathEmployeeId) {

        String actualEmployeeId =
                response.jsonPath().getString(jsonPathEmployeeId);

        String expectedEmployeeId =
                scenarioContextObject.getEmployeeId();

        //System.out.println("Expected Employee Number: " + expectedEmployeeId);
        //System.out.println("Actual Employee Number: " + actualEmployeeId);

        Assert.assertEquals(
                "The response employee number did not match the created employee number",
                expectedEmployeeId,
                actualEmployeeId
        );
    }


    /* 7. Update Employee */
    @Given("a request is prepared to update an employee")
    public void a_request_is_prepared_to_update_an_employee() {

        payload = Payloads.updateEmployeePayloadLombokPojo(
                "Ranjana",
                "Sharma",
                "ML",
                "F",
                "1971-09-09",
                "SDET"
        );

    }
    @When("a PUT call is made to update an employee")
    public void a_put_call_is_made_to_update_an_employee() {

        response = APIServices.updateEmployee(
                scenarioContextObject.getEmployeeId(),
                payload,
                scenarioContextObject.getToken()
        );
    }




    /* 8. Delete Employee */
    @Given("a request is prepared to delete an employee")
    public void a_request_is_prepared_to_delete_an_employee() {

        String employeeId = scenarioContextObject.getEmployeeId();

        List<String> requestIds = Collections.singletonList(employeeId);

        Map<String, Object> deletePayload = new HashMap<>();
        deletePayload.put("ids", requestIds);

        payload = deletePayload;

        scenarioContextObject.setContext("DELETE_IDS", requestIds);

    }
    @When("a DELETE call is made to delete the employee")
    public void a_delete_call_is_made_to_delete_the_employee() {

        response = APIServices.deleteEmployee(
                payload,
                scenarioContextObject.getToken()
        );
    }
    @Then("the deleted employee numbers must match the requested employee numbers")
    public void the_deleted_employee_numbers_must_match_the_requested_employee_numbers() {

        List<String> actualResponseIds =
                response.jsonPath().getList("data");

        List<String> expectedRequestIds =
                (List<String>) scenarioContextObject.getContext("DELETE_IDS");

        Assert.assertEquals(
                "Deleted employee numbers do not match requested employee numbers",
                expectedRequestIds,
                actualResponseIds
        );
    }



    @Then("the response structure must match the employee JSON schema blueprint")
    public void the_response_structure_must_match_the_employee_json_schema_blueprint() {
        // Statically imports io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
        response.then().assertThat()
                .body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/employee-schema.json"));

        System.out.println("JSON Schema validation successfully completed.");
    }

}
