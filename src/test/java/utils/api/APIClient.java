package utils.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class APIClient {
    public Response post(
            String endpoint,
            Object body) {
        return given()
                .header("Authorization", TokenManager.getToken())
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint);
    }


    public Response get(String endpoint) {
        return given()
                .header("Authorization", TokenManager.getToken())
                .when()
                .get(endpoint);
    }
}





















/*
import io.restassured.RestAssured;

public class APIClient {

    public static final String BASE_URL = RestAssured.baseURI =
            "https://www.syntaxhrm.com/web/index.php/";

    public static final String CREATE_EMPLOYEE =
            "api/v2/pim/create_employee";

    public static final String GET_EMPLOYEE =
            "/api/v1/getEmployee/";

    public static final String EMPLOYEE =
            "api/v2/pim/employee";

    public static final String DELETE_EMPLOYEE =
            "api/v2/pim/employees";
}*/
