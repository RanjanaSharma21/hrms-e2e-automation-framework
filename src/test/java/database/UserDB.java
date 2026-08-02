package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.DBUtility.executeQuery;

public class UserDB {

        // Return employee record
        public static ResultSet getUser(String username) throws SQLException {
            String query = "SELECT emp_number, user_name, user_password FROM syntax_hrm.ohrm_user WHERE user_name='" + username + "'";
            return executeQuery(query);
        }
}
