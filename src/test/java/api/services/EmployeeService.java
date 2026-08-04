package api.services;

import api.endpoints.EmployeeEndpoints;
import api.payloads.EmployeePayload;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.api.TokenManager;

import static api.endpoints.EmployeeEndpoints.*;

public class EmployeeService {

    public Response createEmployee(){

        return RestAssured
                .given()
                .header("Authorization", TokenManager.getToken())
                .header("Content-Type", "application/json")
                .body(EmployeePayload.createEmployeePayload())
                .when()
                .post(EmployeeEndpoints.CREATE_EMPLOYEE);

    }


    public Response getEmployee(String empNumber){

        return RestAssured
                .given()
                .header("Authorization", TokenManager.getToken())
                .header("Content-Type", "application/json")
                .when()
                .get(EmployeeEndpoints.GET_EMPLOYEE + empNumber);

    }
}
