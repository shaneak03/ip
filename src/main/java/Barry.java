import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Barry {
    public static void main(String[] args) {
        String logo = "__________    _____ _________________________.___.\n"
                      + "\\______   \\  /  _  \\\\______   \\______   \\__  |   |\n"
                      + " |    |  _/ /  /_\\  \\|       _/|       _//   |   |\n"
                      + " |    |   \\/    |    \\    |   \\|    |   \\\\____   |\n"
                      + " |______  /\\____|__  /____|_  /|____|_  // ______|\n"
                      + "        \\/         \\/       \\/        \\/ \\/    \n";
        String line = "____________________________________________________________\n";
        System.out.println("Hello I'm Barry\n" + logo + "What can I do for you?\n" + line);

        Scanner sc = new Scanner(System.in);
        String input;
        List<String> items = new ArrayList<>();

        while (true) {
            System.out.println("User: ");
            input = sc.nextLine();

            if (input.trim().equalsIgnoreCase("bye")) {
                System.out.println(line + "Goodbye! Hope to see you again soon!\n" + line);
                return;
            } else if (input.trim().equalsIgnoreCase("list")) {
                for (int i = 0; i < items.size(); i++) {
                    int num = i + 1;
                    System.out.println(num + ". " + items.get(i));
                }
                System.out.println(line);
                continue;
            }
            items.add(input);
            System.out.println(line + input + "\n" + line);
        }
    }
}
