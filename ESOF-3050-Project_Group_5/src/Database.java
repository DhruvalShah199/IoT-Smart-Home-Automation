import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	//connection to MySQL using JDBC
    private static final String URL = "jdbc:mysql://localhost:3306/smartHome";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Dhr123uval456";
    
//establishes a connection to the MySQL database and returns the Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

