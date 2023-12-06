/**
* The UserDB contains all the methods necassary to
* check the credentials entered by the user at 
* signup and login page
* 
* @author Amir Dawood
* @version December 2023
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;

//Access database methods for user operations
public class UserDB {
//Method to check the user credentials with database
	   public static String signIn(String username, String password) {
	        String userType = null;

	        try (Connection connection = Database.getConnection()) {
	            // Check in the users table
	            String queryUsers = "SELECT 'user' AS user_type FROM users WHERE username = ? AND password = ?";
	            userType = checkCredentialsInTable(connection, queryUsers, username, password);

	            // If user is not found in users table, check in the admin table
	            if (userType == null) {
	                String queryAdmin = "SELECT 'admin' AS user_type FROM admins WHERE username = ? AND password = ?";
	                userType = checkCredentialsInTable(connection, queryAdmin, username, password);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return userType;
	    }

	    private static String checkCredentialsInTable(Connection connection, String query, String username, String password) throws SQLException {
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getString("user_type");
	                }
	            }
	        }
	        return null;
	    }
	  
    
//sign up method for a new user by inserting their username and password into the 'users' table
    public static boolean signUp(String username, String password) {
    	// SQL query to insert a new user into the 'users' table
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   
}

    
 
