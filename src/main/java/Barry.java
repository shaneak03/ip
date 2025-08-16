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
        String command;
        int index;
        List<String> items = new ArrayList<>();
        List<Boolean> done = new ArrayList<>();

        while (true) {
            System.out.println("User: ");
            input = sc.nextLine();
            index = -1;
            String[] parts = input.split( " ");
            command = parts[0];
            if (parts.length > 1) {
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number!\n" + line);
                    continue;
                }
            }

            System.out.println(line);

            switch(command.toLowerCase()) {
                case "bye":
                    System.out.println("Goodbye! Hope to see you again soon!");
                    System.out.println(line);
                    return;

                case "list":
                    System.out.println("Here are the tasks in your list: ");
                    for (int i = 0; i < items.size(); i++) {
                        int num = i + 1;
                        String done_status = done.get(i) ? "[X]" : "[ ]";
                        System.out.println(num + "." + done_status + " " + items.get(i));
                    }
                    System.out.println(line);
                    continue;

                case "mark":
                    if (index >= done.size() || index < 0) {
                        System.out.println("Invalid task number!\n" + line);
                        continue;
                    }
                    done.set(index, true);
                    System.out.printf("I have marked item %d as done\n", index + 1);
                    System.out.println("[X] " + items.get(index));
                    System.out.println(line);
                    continue;

                default:
                    items.add(input);
                    done.add(false);
                    System.out.println(input);
                    System.out.println(line);
            }
        }
    }
}
