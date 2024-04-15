import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class VotingSystem extends JFrame implements ActionListener {
    private JButton voteButton, resultsButton;
    private Connection connection;
     VotingPage votingPage;

    public VotingSystem() {
        setTitle("Voting System");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        connectToDatabase();

        voteButton = new JButton("Vote");
        resultsButton = new JButton("Display Results");

        voteButton.addActionListener(this);
        resultsButton.addActionListener(this);

        add(voteButton);
        add(resultsButton);

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String JDBC_URL = "jdbc:mysql://localhost:3306/voting_system";
            String USERNAME = "root";
            String PASSWORD = "root";
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == voteButton) {
            dispose(); // Close the current frame
            openVotingPage();
        } else if (e.getSource() == resultsButton) {
            dispose(); // Close the current frame
            openResultsPage();
        }
    }

    private void openVotingPage() {
        votingPage = new VotingPage(connection, this); // Pass VotingSystem instance as an argument
        votingPage.setVisible(true);
    }

    private void openResultsPage() {
        ResultsPage resultsPage = new ResultsPage(connection);
        resultsPage.setVisible(true);
        // After displaying results, return to the main page

        // returnToMainPage();
        // dispose();
    }

    public void returnToMainPage() {
        VotingSystem mainPage = new VotingSystem();
        mainPage.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingSystem::new);
    }

    public void returnToVotingSystem() {
        // Reopen the voting page after vote is saved
        openVotingPage();
    }
}

// VotingPage and ResultsPage classes remain unchanged


class VotingPage extends JFrame implements ActionListener {
    private JButton party1Button, party2Button, party3Button, party4Button;
    private ImageIcon party1Icon, party2Icon, party3Icon, party4Icon;
    private Connection connection;
    private VotingSystem votingSystem;

    public VotingPage(Connection connection, VotingSystem votingSystem) {
        this.connection = connection;
        this.votingSystem = votingSystem;

        setTitle("Voting Page");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        party1Icon = new ImageIcon("party1_image.png");
        party2Icon = new ImageIcon("party2_image.png");
        party3Icon = new ImageIcon("party3_image.png");
        party4Icon = new ImageIcon("party4_image.png");

        party1Button = new JButton(party1Icon);
        party2Button = new JButton(party2Icon);
        party3Button = new JButton(party3Icon);
        party4Button = new JButton(party4Icon);

        party1Button.addActionListener(this);
        party2Button.addActionListener(this);
        party3Button.addActionListener(this);
        party4Button.addActionListener(this);

        add(party1Button);
        add(party2Button);
        add(party3Button);
        add(party4Button);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == party1Button) {
            vote("BJP");
        } else if (e.getSource() == party2Button) {
            vote("Manse");
        } else if (e.getSource() == party3Button) {
            vote("Shivsena");
        } else if (e.getSource() == party4Button) {
            vote("Congress");
        }
    }

    private void vote(String party) {
        String voterId = JOptionPane.showInputDialog(this, "Enter Voter ID:");
        if (voterId != null && !voterId.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO votes (voter_id, party) VALUES (?, ?)");
                statement.setString(1, voterId);
                statement.setString(2, party);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Vote for " + party + " recorded successfully.");
                        dispose();
                    // Return to voting page
                    votingSystem.returnToMainPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to record vote.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while recording vote.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Voter ID cannot be empty.");
        }
    }
}

class ResultsPage extends JFrame {
    private Connection connection;

    public ResultsPage(Connection connection) {
        this.connection = connection;

        setTitle("Results Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display election results here
        displayResults();
    }

    private void displayResults() {
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT party, COUNT(*) AS votes FROM votes GROUP BY party ORDER BY votes DESC LIMIT 1");
            if (resultSet.next()) {
                String winningParty = resultSet.getString("party");
                int votes = resultSet.getInt("votes");
                resultsArea.append("Winning Party: " + winningParty + ", Votes: " + votes + "\n");

                // Display image of the winning party
                ImageIcon winningIcon = null;
                switch (winningParty) {
                    case "BJP":
                        winningIcon = new ImageIcon("party1_image.png");
                        break;
                    case "Manse":
                        winningIcon = new ImageIcon("party2_image.png");
                        break;
                    case "Shivsena":
                        winningIcon = new ImageIcon("party3_image.png");
                        break;
                    case "Congress":
                        winningIcon = new ImageIcon("party4_image.png");
                        break;
                    default:
                        break;
                }

                if (winningIcon != null) {
                    JLabel imageLabel = new JLabel(winningIcon);
                    imageLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            // Display additional details or perform actions on mouse click
                            JOptionPane.showMessageDialog(null, "You clicked on " + winningParty + ". ");

                        }
                    });
                    add(imageLabel);
                }

            } else {
                resultsArea.append("No results available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
