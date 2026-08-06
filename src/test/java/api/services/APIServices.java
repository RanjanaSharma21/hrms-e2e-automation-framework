package api.services;

import api.endpoints.APIEndpoints;
import io.restassured.response.Response;
import ui.utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class APIServices {

        public static Response login(Object payload) {

            return given()
                    .header("Content-Type","application/json")
                    .body(payload)
                    .when()
                    .post(ConfigReader.getProperty("apiUrl")+APIEndpoints.login());

        }

        public static Response createEmployee(Object payload, String token){

            return given()
                    .header("Authorization", token)
                    .header("Content-Type","application/json")
                    .body(payload)
                    .when()
                    .post(ConfigReader.getProperty("apiUrl")+APIEndpoints.createEmployee());

        }

        public static Response getEmployee(String empId, String token){

            return given()
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .when()
                    .get(ConfigReader.getProperty("apiUrl")+APIEndpoints.getEmployee(empId));

        }


        public static Response updateEmployee(
                String empId,
                Object payload,
                String token){

            return given()
                    .header("Authorization", token)
                    .header("Content-Type","application/json")
                    .body(payload)
                    .when()
                    .put(ConfigReader.getProperty("apiUrl")+APIEndpoints.updateEmployee(empId));

        }


        public static Response deleteEmployee(
            Object payload,
            String token) {

           return given()
                   .header("Authorization", token)
                   .header("Content-Type", "application/json")
                   .body(payload)
                   .when()
                   .delete(ConfigReader.getProperty("apiUrl")+APIEndpoints.deleteEmployee());
    }


        public static Response getAllEmployees(String token){

            return given()
                    .header("Authorization", token)
                    .header("Content-Type","application/json")
                    .when()
                    .get(ConfigReader.getProperty("apiUrl")+APIEndpoints.getAllEmployees());

        }


        public static Response getAllJobs(String token){

            return given()
                    .header("Authorization", token)
                    .header("Content-Type","application/json")
                    .when()
                    .get(ConfigReader.getProperty("apiUrl")+APIEndpoints.getAllJobs());

        }


        public static Response getVersion(String token){

            return given()
                    .header("Authorization", token)
                    .header("Content-Type","application/json")
                    .when()
                    .get(ConfigReader.getProperty("apiUrl")+APIEndpoints.getApiVersion());

        }
}
