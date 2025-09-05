package barry.chatbot;

import java.util.Scanner;

/**
 * Handles all user interactions for the Barry chatbot
 */
public class Ui {
    private final Scanner scanner;
    private static final String line = "____________________________________________________________\n";

    /**
     * Creates a {@code Ui} instance with a new {@link Scanner} to read input from User
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and ASCII Logo
     */
    public void showWelcome() {
        String logo = "__________    _____ _________________________.___.\n"
                + "\\______   \\  /  _  \\\\______   \\______   \\__  |   |\n"
                + " |    |  _/ /  /_\\  \\|       _/|       _//   |   |\n"
                + " |    |   \\/    |    \\    |   \\|    |   \\\\____   |\n"
                + " |______  /\\____|__  /____|_  /|____|_  // ______|\n"
                + "        \\/         \\/       \\/        \\/ \\/    \n";
        System.out.println("Hello I'm Barry\n" + logo + "What can I do for you?\n" + line);
    }

    public static String getWelcome() {
        String logo = "__________    _____ _________________________.___.\n"
                + "\\______   \\  /  _  \\\\______   \\______   \\__  |   |\n"
                + " |    |  _/ /  /_\\  \\|       _/|       _//   |   |\n"
                + " |    |   \\/    |    \\    |   \\|    |   \\\\____   |\n"
                + " |______  /\\____|__  /____|_  /|____|_  // ______|\n"
                + "        \\/         \\/       \\/        \\/ \\/    \n";
        return ("Hello I'm Barry\n" + logo + "What can I do for you?\n");
    }

    /**
     * Prints a horizontal line
     */
    public void showLine() {
        System.out.println(line);
    }

    /**
     * Displays specified message to console
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays specified error to console followed by line
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    /**
     * Reads the next command entered by User
     * @return raw input string by user
     */
    public String readCommand() {
        System.out.print("User: \n");
        return scanner.nextLine();
    }
}
