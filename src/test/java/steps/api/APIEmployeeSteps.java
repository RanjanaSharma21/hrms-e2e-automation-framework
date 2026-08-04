package steps.api;

import api.payloads.EmployeePayload;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import api.models.Employee;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import api.context.ScenarioContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIEmployeeSteps {

    private RequestSpecification request;
    private Response response;

    //public static String empNumber;
    //public static String token;
    private List<String> requestIds;
    private Map<String, String> dynamicContextData; // Temporary store for dynamic strings
    private Employee expectedEmployeeRequest;
    public Employee expectedEmployeePOJO;
    // Just declaring the instance variable, not creating instance with new keyword
    private final ScenarioContext scenarioContextObject;

    // Pass it into the constructor for PicoContainer injection
    // Injecting the shared test memory instance's State via
    // Cucumber Dependency Injection (PicoContainer) into the constructor
    public APIEmployeeSteps(ScenarioContext scenarioContextObject) {
        this.scenarioContextObject = scenarioContextObject;
    }



    @Given("a request is prepared to create an employee using API")
    public void a_request_is_prepared_to_create_an_employee_using_api() {
        // 1. prepare the request
        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"firstName\": \"Janki\",\n" +
                        "  \"lastName\": \"Vallabh\",\n" +
                        "  \"middleName\": \"Sita\",\n" +
                        "  \"gender\": \"F\",\n" +
                        "  \"birthday\": \"1990-01-15\",\n" +
                        "  \"job_title\": \"SDET\"\n" +
                        "} ");
    }

    @When("a POST call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        // 2. hitting the endpoint
        response = request.when().post("api/v2/pim/create_employee");
    }

    @Then("the status code for this request is {int}")
    public void the_status_code_for_this_request_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);  // 3. validate status code
        response.prettyPrint(); // print in API
    }

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
    }

    @Then("the employee is stored as global variable {string}")
    public void the_employee_is_stored_as_global_variable(String pathForEmpid) {
        //Storing empNumber as a class variable for this class to be shared by all methods

        //empNumber = response.jsonPath().getString(pathForEmpid);

        // Storing empNumber inside the scenarioContext map/storage container
        // so that it does not lose its value during parallel running where
        // different tests will overwrite that single static variable simultaneously,
        // causing random test failures.

        String parsedEmpNumber = response.jsonPath().getString(pathForEmpid);

        // Store it safely inside the context map container
        scenarioContextObject.setContext("EMPLOYEE_NUMBER", parsedEmpNumber);
    }



    @Given("a request is prepared to get an employee")
    public void a_request_is_prepared_to_get_an_employee() {
        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json");
    }

    @When("a GET call is made to get a created employee")
    public void a_get_call_is_made_to_get_a_created_employee() {
        //response = request.when().get("api/v2/pim/employee"+"/"+empNumber);
        String savedEmpNumber = (String) scenarioContextObject.getContext("EMPLOYEE_NUMBER");
        response = request.when().get("api/v2/pim/employee/" + savedEmpNumber);

    }

    @Then("the employee number {string} must match the global variable employee number")
    public void the_employee_number_must_match_the_global_variable_employee_number(String empNumber) {

        // 1. Extract the employee number from the response
        String actualEmpNoFromResponse = response.jsonPath().getString(empNumber);

        // 2. Retrieve the originally saved employee number from the shared context object using the "EMPLOYEE_NUMBER" key
        String expectedEmpNoFromContext = (String) scenarioContextObject.getContext("EMPLOYEE_NUMBER");
        System.out.println(expectedEmpNoFromContext);
        System.out.println(actualEmpNoFromResponse);
        // 3. Compare them using the proper JUnit 4 format: Assert.assertEquals(message, expected, actual)
        Assert.assertEquals("The response employee number did not match our saved global context variable!",
                expectedEmpNoFromContext, actualEmpNoFromResponse);
    }



    @Given("a request is prepared to update an employee")
    public void a_request_is_prepared_to_update_an_employee() {
        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"Ranjana\",\n" +
                        "    \"lastName\": \"Sharma\",\n" +
                        "    \"middleName\": \"ML\",\n" +
                        "    \"gender\": \"F\",\n" +
                        "    \"birthday\": \"1971-09-09\",\n" +
                        "    \"job_title\": \"SDET\"\n" +
                        "}");

    }
    @When("a PUT call is made to update an employee")
    public void a_put_call_is_made_to_update_an_employee() {
        //response = request.when().put("api/v2/pim/employee"+"/"+"EMPLOYEE_NUMBER");
        String savedEmpNumber = (String) scenarioContextObject.getContext("EMPLOYEE_NUMBER");
        response = request.when().put("api/v2/pim/employee/" + savedEmpNumber);
    }



    @Given("a request is prepared to delete an employee")
    public void a_request_is_prepared_to_delete_an_employee() {
        // 1. Fetch the active numerical ID from context container memory
        String savedEmpNumber = (String) scenarioContextObject.getContext("EMPLOYEE_NUMBER");

        // 2. Wrap it safely inside a collections array list pipeline. Automatically creates the JSON array [empNumber]
        requestIds = Collections.singletonList(savedEmpNumber);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("ids", requestIds);
        // Use Arrays.asList() to group multiple variables together into one list
        //bodyMap.put("ids", Arrays.asList(empNember1, empNember2, empNember3));

        // 3. Fetch token and build the request specification
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body(bodyMap);
    }

    @When("a DELETE call is made to delete the employee")
    public void a_delete_call_is_made_to_delete_the_employee() {
        response = request.when().delete("api/v2/pim/employees");
    }

    @Then("the deleted employee numbers must match the requested employee numbers")
    public void the_deleted_employee_numbers_must_match_the_requested_employee_numbers() {
        List<String> responseIds = response.jsonPath().getList("data");
        Assert.assertEquals(requestIds, responseIds);
    }


    //Map
    @Given("a request is prepared to create an employee using Map format")
    public void a_request_is_prepared_to_create_an_employee_using_map_format() {
        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body(EmployeePayload.createEmployeePayloadMap());
    }

    @Then("the Map request body must match the response body")
    public void the_map_request_body_must_match_the_response_body() {
        // Extract both as Maps and compare them directly
        Map<String, Object> actualData = response.jsonPath().getMap("data");
        Map<String, Object> expectedData = EmployeePayload.createEmployeePayloadMap();

        Assert.assertEquals(expectedData.get("firstName"), actualData.get("firstName"));
        Assert.assertEquals(expectedData.get("lastName"), actualData.get("lastName"));
        // Custom logic can check string formatting or map keys directly here
    }

    //Raw JSON
    @Given("a request is prepared to create an employee using JSON format")
    public void a_request_is_prepared_to_create_an_employee_using_json_format() {
        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body(EmployeePayload.createEmployeePayloadJson());
    }

    @Then("the JSON request body must match the response body")
    public void the_json_request_body_must_match_the_response_body() {
        Map<String, Object> actualData = response.jsonPath().getMap("data");

        // Assert directly against the known baseline hardcoded values
        Assert.assertEquals("Janki", actualData.get("firstName"));
        Assert.assertEquals("Vallabh", actualData.get("lastName"));
    }

    //Dynamic JSON
    @Given("a request is prepared to create an employee using dynamic JSON format with {string}, {string}, {string}, {string}, {string}, {string}")
    public void a_request_is_prepared_to_create_an_employee_using_dynamic_json_format_with
    (String firstName, String lastName, String middleName, String gender, String birthday, String jobTitle) {

        // Save to a local Map purely for this step's validation later
        dynamicContextData = Map.of(
                "firstName", firstName, "lastName", lastName, "middleName", middleName, "birthday", birthday
        );

        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body(EmployeePayload.createEmployeePayloadJsonDynamic(firstName, lastName, middleName, gender, birthday, jobTitle));
    }

    @Then("the dynamic JSON request body must match the response body")
    public void the_dynamic_json_request_body_must_match_the_response_body() {
        Map<String, Object> actualData = response.jsonPath().getMap("data");

        Assert.assertEquals(dynamicContextData.get("firstName"), actualData.get("firstName"));
        Assert.assertEquals(dynamicContextData.get("lastName"), actualData.get("lastName"));
    }

    //LOMBOK POJO
    @Given("a request is prepared to create an employee using lombok pojo dynamic JSON format with " +
            "{string}, {string}, {string}, {string}, {string}, {string}")
    public void a_request_is_prepared_to_create_an_employee_using_lombok_pojo_dynamic_json_format_with
    (String firstName, String lastName, String middleName, String gender, String birthday, String jobTitle) {
        // The data is cleanly encapsulated within the Java Object model
        expectedEmployeePOJO = new Employee(firstName, lastName, middleName, gender, birthday, jobTitle);

        // Fetch the active token string directly from your pipeline memory container
        String token = (String) scenarioContextObject.getContext("AUTH_TOKEN");
        request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body(expectedEmployeePOJO); // RestAssured automatically serializes the object to JSON
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

    @Then("the response structure must match the employee JSON schema blueprint")
    public void the_response_structure_must_match_the_employee_json_schema_blueprint() {
        // Statically imports io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
        response.then().assertThat()
                .body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/employee-schema.json"));

        System.out.println("JSON Schema validation successfully completed.");
    }

}
