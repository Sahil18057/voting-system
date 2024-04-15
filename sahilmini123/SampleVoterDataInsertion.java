import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SampleVoterDataInsertion {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/voting_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Insert sample voter IDs
            insertSampleVoters(connection);
            System.out.println("Sample voter IDs inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertSampleVoters(Connection connection) throws SQLException {
    // Sample voter IDs to insert
    String[] sampleVoterIds = {"V1001", "V1002", "V1003", "V1004", "V1005"};

    String insertSQL = "INSERT IGNORE INTO voters (voter_id, name, has_voted) VALUES (?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
        for (String voterId : sampleVoterIds) {
            statement.setString(1, voterId);
            statement.setString(2, "Sample Voter");
            statement.setBoolean(3, false); // Assuming none of them have voted initially
            statement.executeUpdate();
        }
    }
}

}
