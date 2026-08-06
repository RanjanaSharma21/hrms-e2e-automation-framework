package api.endpoints;

import api.utils.APIConstants;

public class APIEndpoints {

    public static String login() {
        return APIConstants.LOGIN;
    }
    public static String createEmployee() {
        return APIConstants.CREATE_EMPLOYEE;
    }

    public static String createEmployeeById() {
        return APIConstants.CREATE_EMPLOYEE_BY_ID;
    }
    public static String getEmployee(String empNumber) {
        return APIConstants.GET_EMPLOYEE + empNumber;
    }

    public static String getAllEmployees() {
        return APIConstants.GET_ALL_EMPLOYEES;
    }

    public static String updateEmployee(String empNumber) {
        return APIConstants.UPDATE_EMPLOYEE + empNumber;
    }
    public static String deleteEmployee() {
        return APIConstants.DELETE_EMPLOYEE;
    }
    public static String countEmployees() {
        return APIConstants.COUNT_EMPLOYEES;
    }

    public static String getAllJobs() {
        return APIConstants.GET_ALL_JOBS;
    }

    public static String getApiVersion() {
        return APIConstants.GET_API_VERSION;
    }
}