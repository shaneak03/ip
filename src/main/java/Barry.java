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
        List<Task> tasks = new ArrayList<>();

        while (true) {
            System.out.println("User: ");
            input = sc.nextLine();
            index = -1;
            String[] parts = input.split( " ");
            command = parts[0];
            if (command.equalsIgnoreCase("mark") || command.equalsIgnoreCase("unmark")) {
                if (parts.length < 2) {
                    System.out.println("Please specify the task number!\n" + line);
                    continue;
                }
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number!\n" + line);
                    continue;
                }
            }

            System.out.println(line);

            try {
                switch (command.toLowerCase()) {
                    case "bye":
                        System.out.println("Goodbye! Hope to see you again soon!");
                        System.out.println(line);
                        return;

                    case "list":
                        System.out.println("Here are the tasks in your list: ");
                        for (int i = 0; i < tasks.size(); i++) {
                            int num = i + 1;
                            System.out.println(num + "." + tasks.get(i));
                        }
                        System.out.println(line);
                        continue;

                    case "mark":
                        if (index >= tasks.size() || index < 0) {
                            System.out.println("Invalid task number!\n" + line);
                            continue;
                        }
                        Task task = tasks.get(index);
                        task.markDone();
                        System.out.printf("I have marked item %d as done\n", index + 1);
                        System.out.println(task);
                        System.out.println(line);
                        continue;

                    case "unmark":
                        if (index >= tasks.size() || index < 0) {
                            System.out.println("Invalid task number!\n" + line);
                            continue;
                        }
                        Task t = tasks.get(index);
                        t.markUndone();
                        System.out.printf("I have marked item %d as not done yet\n", index + 1);
                        System.out.println(t);
                        System.out.println(line);
                        continue;

                    default:
                        if (parts.length < 2) {
                            System.out.println("The description of a todo cannot be empty");
                            System.out.println(line);
                            break;
                        }

                        Task taskToAdd = null;

                        switch (command.toLowerCase()) {
                            case "todo":
                                String toDoDesc = input.substring(5).trim();
                                taskToAdd = new ToDoTask(toDoDesc);
                                break;

                            case "deadline":
                                try {
                                    String[] deadlineParts = input.substring(9).split("/by", 2);
                                    String deadlineDesc = deadlineParts[0].trim();
                                    String deadline = deadlineParts[1].trim();
                                    taskToAdd = new DeadlineTask(deadlineDesc, deadline);
                                } catch (Exception e) {
                                    throw new EmptyDescriptionException("Invalid deadline format! Use: deadline <desc> /by <date>");
                                }
                                break;

                            case "event":
                                try {
                                    String[] eventParts = input.substring(6).split("/from|/to");
                                    String desc = eventParts[0].trim();
                                    String from = eventParts[1].trim();
                                    String to = eventParts[2].trim();
                                    taskToAdd = new EventTask(desc, from, to);
                                } catch (Exception e) {
                                    throw new EmptyDescriptionException("Invalid event format! Use: event <desc> /from <start> /to <end>");
                                }
                                break;
                        }
                        if (taskToAdd != null) {
                            tasks.add(taskToAdd);
                            System.out.println("I've added this task into your list:");
                            System.out.println(taskToAdd);
                            System.out.println(String.format("You now have %d task in the list", tasks.size()));
                            System.out.println(line);
                        } else {
                            throw new UnknownCommandException("An error has occured, please try again");
                        }
                }
            } catch (EmptyDescriptionException | UnknownCommandException e) {
                System.out.println(e.getMessage());
                System.out.println(line);
            }
        }
    }
}
