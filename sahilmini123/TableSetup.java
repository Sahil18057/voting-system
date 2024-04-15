import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableSetup {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/voting_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DATABASE_NAME = "voting_system";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            
            createTables(connection);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private static void createTables(Connection connection) throws SQLException {
        String createVotersTableSQL = "CREATE TABLE IF NOT EXISTS voters (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "voter_id VARCHAR(50) UNIQUE," +
                "name VARCHAR(100)," +
                "has_voted BOOLEAN DEFAULT FALSE" +
                ")";

        String createVotesTableSQL = "CREATE TABLE IF NOT EXISTS votes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "voter_id VARCHAR(50)," +
                "party VARCHAR(50)," +
                "FOREIGN KEY (voter_id) REFERENCES voters(voter_id)" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createVotersTableSQL);
            statement.executeUpdate(createVotesTableSQL);
            System.out.println("Tables created successfully.");
        }
    }
}
