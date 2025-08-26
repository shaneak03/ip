import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    private final String line = "____________________________________________________________\n";

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo = "__________    _____ _________________________.___.\n"
                + "\\______   \\  /  _  \\\\______   \\______   \\__  |   |\n"
                + " |    |  _/ /  /_\\  \\|       _/|       _//   |   |\n"
                + " |    |   \\/    |    \\    |   \\|    |   \\\\____   |\n"
                + " |______  /\\____|__  /____|_  /|____|_  // ______|\n"
                + "        \\/         \\/       \\/        \\/ \\/    \n";
        System.out.println("Hello I'm Barry\n" + logo + "What can I do for you?\n" + line);
    }

    public void showLine() {
        System.out.println(line);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    public String readCommand() {
        System.out.print("User: \n");
        return scanner.nextLine();
    }
}
