package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import static database.DBUtility.executeQuery;
/*
Following Single Responsibility Principle (SRP):
DBUtility manages database connections and query execution.
EmployeeDB knows how to query employee data.
*/
public class EmployeeDB {

    public static String getEmployeeById(String employeeId) {
        return "SELECT * FROM hs_hr_employee WHERE employee_id='" + employeeId + "'";
    }

    public static String deleteEmployeeById(String employeeId) {
        return "DELETE FROM hs_hr_employee WHERE employee_id='" + employeeId + "'";
    }

    // Check if employee exists
    public static boolean employeeExists(String employeeId)  throws SQLException {
        String query = "SELECT * FROM hs_hr_employee WHERE employee_id='" + employeeId + "'";
        ResultSet rs = executeQuery(query);
        return rs.next();
    }

    // Check if employee has been deleted
    public static boolean employeeDeleted(String employeeId) throws SQLException {
        String query = "SELECT * FROM hs_hr_employee WHERE employee_id='" + employeeId + "'";
        ResultSet rs = executeQuery(query);
        return !rs.next();
    }

    // Return employee record
    public static ResultSet getEmployee(String employeeId) throws SQLException {
        String query = "SELECT * FROM hs_hr_employee WHERE employee_id='" + employeeId + "'";
        return executeQuery(query);
    }

    public static Boolean getEmployeesByName(String employeeFirstName, String employeeLastName) throws SQLException {
        String query = "SELECT * FROM hs_hr_employee WHERE emp_firstname='" + employeeFirstName + "'" +
                "or emp_lastname='\" + employeeLastName + \"'";
        ResultSet rs = executeQuery(query);
        return rs.next();
    }

    // Count matching employees
    public static int getEmployeeCount(String employeeId) throws SQLException {
        String query = "SELECT COUNT(*) AS total FROM hs_hr_employee WHERE employee_id='" + employeeId + "'";
        ResultSet rs = executeQuery(query);
        rs.next();
        return rs.getInt("total");
    }
}
