package database;



import java.sql.*;

/*
    Student Credentials:
    Database: syntax_hrm
    Host: 148.72.132.33
    Port: 3307

    Username: syntax_std
    Password: syntax_std@2026
*/


/*
Following Single Responsibility Principle (SRP):
DBUtility manages database connections and query execution.
EmployeeDB knows how to query employee data.
*/

public class DBUtility {

    private static Connection connection;

    public static void connect() throws Exception {
        connection = DriverManager.getConnection(
                utils.common.ConfigReader.getProperty("dbUrl"),
                utils.common.ConfigReader.getProperty("dbUserName"),
                utils.common.ConfigReader.getProperty("dbUserPassword"));
    }

    // Execute SELECT query
    public static ResultSet executeQuery(String sqlQuery) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sqlQuery);
    }

    // Execute INSERT, UPDATE or DELETE query
    public static int executeUpdate(String sqlQuery) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sqlQuery);
    }

    public static void close() throws Exception {
        if(connection!=null){
            connection.close();
        }
    }
}
