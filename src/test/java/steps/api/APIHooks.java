package steps.api;

import api.context.ScenarioContext;
import api.payloads.AuthPayload;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIHooks {

    private RequestSpecification request;
    private Response response;

    private final ScenarioContext scenarioContextObject;

    public APIHooks(ScenarioContext scenarioContextObject) {
        this.scenarioContextObject = scenarioContextObject;
    }


    @Given("a token is created")
    public void a_token_is_created() {

        RestAssured.baseURI = "https://www.syntaxhrm.com/web/index.php/";

        request = given()
                .header("Content-Type", "application/json")
                .body(AuthPayload.loginPayload("hrm_user", "Hrm_user@123"));

        response = request.when()
                .post("api/v2/auth/login");


        String token = "Bearer " + response.jsonPath()
                .getString("data.token");


        scenarioContextObject.setContext("AUTH_TOKEN", token);

        System.out.println(token);
    }
}