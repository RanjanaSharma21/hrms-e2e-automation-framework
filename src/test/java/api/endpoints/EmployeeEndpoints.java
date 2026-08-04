package api.endpoints;

public class EmployeeEndpoints {

    public static final String CREATE_EMPLOYEE =
            "api/v2/pim/create_employee";

    public static final String GET_EMPLOYEE =
            "api/v2/pim/employee/";

    public static final String UPDATE_EMPLOYEE =
            "api/v2/pim/employee/";

    public static final String DELETE_EMPLOYEE =
            "api/v2/pim/employees";
}





/*package api.endpoints;

import utils.api.APIClient;

public class EmployeeEndpoints {

    public static String createEmployee() {
        return APIClient.CREATE_EMPLOYEE;
    }


    public static String getEmployee(String empNumber) {
        return APIClient.EMPLOYEE + "/" + empNumber;
    }


    public static String updateEmployee(String empNumber) {
        return APIClient.EMPLOYEE + "/" + empNumber;
    }


    public static String deleteEmployee() {
        return APIClient.DELETE_EMPLOYEE;
    }
}
*/


