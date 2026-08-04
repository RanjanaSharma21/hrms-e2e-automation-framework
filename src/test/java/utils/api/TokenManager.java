package utils.api;

import api.payloads.AuthPayload;
import api.payloads.EmployeePayload;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {

            RestAssured.baseURI =
                    "https://www.syntaxhrm.com/web/index.php/";

            Response response =
                    given()
                            .header("Content-Type", "application/json")
                            .body(AuthPayload.loginPayload(
                                    "hrm_user",
                                    "Hrm_user@123"))
                            .when()
                            .post("api/v2/auth/login");


            token = "Bearer " +
                    response.jsonPath()
                            .getString("data.token");
        }

        return token;
    }
}





/*package utils.api;

import api.payloads.APIPayloadConstants;
import api.context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private RequestSpecification request;
    private Response response;

    @Given("a token is created")
    public void a_token_is_created() {

        RestAssured.baseURI ="https://www.syntaxhrm.com/web/index.php/";
        request = given()
                .header("Content-Type", "application/json")
                .body(APIPayloadConstants.loginPayload("hrm_user", "Hrm_user@123"));

        response = request.when().post("api/v2/auth/login");

        String token = "Bearer " + response.jsonPath().getString("data.token");

        // STORE IT IN CONTEXT: Instead of using from 'public static String token'
        // This saves the token safely inside the shared pipeline container map!
        ScenarioContext.scenarioContextObject.setContext("AUTH_TOKEN", token);
        System.out.println(token);
    }

}*/
