import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RunCommands {
    public static void main(String[] args) {
        // Commands to be executed
        List<String> commands = Arrays.asList(
                "javac DatabaseInitializer.java SampleVoterDataInsertion.java TableSetup.java VotingSystem_Register.java",
                "javac VotingSystem.java",
                "java DatabaseInitializer",
                "java TableSetup",
                "java SampleVoterDataInsertion",
                "java VotingSystem_Register",
                "java VotingSystem"
        );

        // Execute each command
        for (String command : commands) {
            try {
                ProcessBuilder pb = new ProcessBuilder(command.split(" "));
                pb.inheritIO(); // Redirect input/output to the current process
                Process process = pb.start();
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.err.println("Error occurred while executing command: " + command);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
