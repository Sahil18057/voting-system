import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class VotingSystem_Register extends JFrame implements ActionListener {
    // Components for login panel
    // JLabel loginTitleLabel, loginUsernameLabel, loginPasswordLabel;
    // JTextField loginUsernameField;
    // JPasswordField loginPasswordField;
    // JButton loginButton;

    // Components for voter information panel
    JLabel nameLabel, phoneLabel, ageLabel,  imageLabel;  //voterIdLabel,
    JTextField nameField, phoneField, ageField; //voterIdField
    JButton uploadImageButton, submitButton;

    // Card layout and container
    JPanel cardPanel;
    CardLayout cardLayout;
    ImageIcon selectedImage;

    public VotingSystem_Register() {
        // Frame setup
        setTitle("Online Voting System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create card panel and set layout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Login Panel
        // JPanel loginPanel = new JPanel(new GridLayout(4, 2));
        // loginTitleLabel = new JLabel("Login Form");
        // loginTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        // loginUsernameLabel = new JLabel("Username:");
        // loginPasswordLabel = new JLabel("Password:");
        // loginUsernameField = new JTextField();
        // loginPasswordField = new JPasswordField();
        // loginButton = new JButton("Login");

        // loginPanel.add(loginTitleLabel);
        // loginPanel.add(new JLabel(""));
        // loginPanel.add(loginUsernameLabel);
        // loginPanel.add(loginUsernameField);
        // loginPanel.add(loginPasswordLabel);
        // loginPanel.add(loginPasswordField);
        // loginPanel.add(new JLabel("")); // Blank space for password field
        // loginPanel.add(loginButton);

        // loginButton.addActionListener(this);

        // Voter Information Panel
        JPanel voterInfoPanel = new JPanel(new GridLayout(7, 2));
        nameLabel = new JLabel("Name:");
        phoneLabel = new JLabel("Phone:");
        ageLabel = new JLabel("Age:");
        //voterIdLabel = new JLabel("Voter ID:");
        imageLabel = new JLabel("Image:");

        nameField = new JTextField();
        phoneField = new JTextField();
        ageField = new JTextField();
       // voterIdField = new JTextField();
        uploadImageButton = new JButton("Upload Image");
        submitButton = new JButton("Submit");

        voterInfoPanel.add(nameLabel);
        voterInfoPanel.add(nameField);
        voterInfoPanel.add(phoneLabel);
        voterInfoPanel.add(phoneField);
        voterInfoPanel.add(ageLabel);
        voterInfoPanel.add(ageField);
       // voterInfoPanel.add(voterIdLabel);
       // voterInfoPanel.add(voterIdField);
        voterInfoPanel.add(imageLabel);
        voterInfoPanel.add(uploadImageButton);
        voterInfoPanel.add(new JLabel(""));
        voterInfoPanel.add(submitButton);

        uploadImageButton.addActionListener(this);
        submitButton.addActionListener(this);

        // Add panels to card panel
        //cardPanel.add(loginPanel, "login");
        cardPanel.add(voterInfoPanel, "voterInfo");

        // Add card panel to frame
        add(cardPanel);

        // Set frame visibility
        setVisible(true);

        // Start with login panel visible
        cardLayout.show(cardPanel, "login");
    }

    // Action performed when buttons are clicked
    public void actionPerformed(ActionEvent e) {
        // if (e.getSource() == loginButton) {
        //     String username = loginUsernameField.getText();
        //     String password = new String(loginPasswordField.getPassword());

        //     if (username.isEmpty() || password.isEmpty()) {
        //         JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        //     } else {
        //         // Here you would typically authenticate the user against a database
        //         // For simplicity, let's assume successful login
        //         cardLayout.show(cardPanel, "voterInfo");
        //     }
        // } else 
        if (e.getSource() == uploadImageButton) {
            // Open file chooser dialog to select image
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
            fileChooser.setFileFilter(filter);

            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String imageName = file.getName();
                imageLabel.setText(imageName);

                // Load selected image and display it
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image image = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                selectedImage = new ImageIcon(image);
                imageLabel.setIcon(selectedImage);
            }
        } else if (e.getSource() == submitButton) {
            // Process voter information and image upload
            String name = nameField.getText();
            String phone = phoneField.getText();
            String age = ageField.getText();
           // String voterId = voterIdField.getText();  + "\nVoter ID: " + voterId
            String imageName = imageLabel.getText();

            // Here you can process the data as needed
            // For now, let's just display the information
            JOptionPane.showMessageDialog(this, "Name: " + name + "\nPhone: " + phone + "\nAge: " + age  + "\nImage: " + imageName, "Voter Information", JOptionPane.INFORMATION_MESSAGE);
            dispose();

           // VotingSystem = new VotingSystem() ;
            
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VotingSystem_Register();
            }
        });
    }
}
