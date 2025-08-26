import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Barry {
    public static void main(String[] args) {

    Ui ui = new Ui();
    ui.showWelcome();
    String input;
    String command;
    int index;
    new Storage();
    ArrayList<Task> tasks = Storage.load();

        while (true) {
            input = ui.readCommand();
            index = -1;
            String[] parts = input.split(" ");
            command = parts[0];
            if (command.equalsIgnoreCase("mark") ||
                command.equalsIgnoreCase("unmark") ||
                command.equalsIgnoreCase("delete")) {
                if (parts.length < 2) {
                    ui.showError("Please specify the task number!");
                    ui.showLine();
                    continue;
                }
                try {
                    index = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    ui.showError("Invalid task number!");
                    ui.showLine();
                    continue;
                }
            }

            ui.showLine();

            try {
                switch (command.toLowerCase()) {
                    case "bye":
                        ui.showMessage("Goodbye! Hope to see you again soon!");
                        ui.showLine();
                        return;

                    case "list":
                        ui.showMessage("Here are the tasks in your list: ");
                        for (int i = 0; i < tasks.size(); i++) {
                            int num = i + 1;
                            ui.showMessage(num + "." + tasks.get(i));
                        }
                        ui.showLine();
                        continue;

                    case "mark":
                        if (index >= tasks.size() || index < 0) {
                            ui.showError("Invalid task number!");
                            continue;
                        }
                        Task task = tasks.get(index);
                        task.markDone();
                        Storage.saveAllState(tasks);
                        ui.showMessage(String.format("I have marked item %d as done", index + 1));
                        ui.showMessage(task.toString());
                        ui.showLine();
                        continue;

                    case "unmark":
                        if (index >= tasks.size() || index < 0) {
                            ui.showError("Invalid task number!");
                            continue;
                        }
                        Task t = tasks.get(index);
                        t.markUndone();
                        Storage.saveAllState(tasks);
                        ui.showMessage(String.format("I have marked item %d as not done yet", index + 1));
                        ui.showMessage(t.toString());
                        ui.showLine();
                        continue;

                    case "delete":
                        if (index >= tasks.size() || index < 0) {
                            ui.showError("Invalid task number!");
                            continue;
                        }
                        Task taskToRemove = tasks.get(index);
                        tasks.remove(index);
                        Storage.saveAllState(tasks);
                        ui.showMessage("I have removed the following task:");
                        ui.showMessage(taskToRemove.toString());
                        ui.showMessage("Now you have " + tasks.size() + " tasks in your list");
                        ui.showLine();
                        continue;

                    default:
                        if (parts.length < 2) {
                            ui.showMessage("The description of a todo cannot be empty");
                            ui.showLine();
                            break;
                        }

                        Task taskToAdd = null;

                        switch (command.toLowerCase()) {
                            case "todo":
                                String toDoDesc = input.substring(5).trim();
                                taskToAdd = new ToDoTask(toDoDesc);
                                break;

                            case "deadline": {
                                String[] deadlineParts = input.substring(9).split("/by", 2);
                                String deadlineDesc = deadlineParts[0].trim();
                                String deadline = deadlineParts[1].trim();
                                if (deadlineDesc.isEmpty() || deadline.isEmpty()) {
                                    throw new InvalidInputException("Deadline description and date/time cannot be empty");
                                }
                                try {
                                    java.time.format.DateTimeFormatter deadlineFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                                    java.time.LocalDateTime.parse(deadline, deadlineFormat);
                                    taskToAdd = new DeadlineTask(deadlineDesc, deadline);
                                } catch (java.time.format.DateTimeParseException e) {
                                    throw new InvalidInputException("Invalid deadline format! Use: deadline <desc> /by <date> (yyyy-MM-dd HHmm)");
                                }
                                break;
                            }

                            case "event": {
                                String[] eventParts = input.substring(6).split("/from|/to");
                                String desc = eventParts[0].trim();
                                String from = eventParts[1].trim();
                                String to = eventParts[2].trim();
                                if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                                    throw new InvalidInputException("Event description, start, and end cannot be empty");
                                }
                                try {
                                    java.time.format.DateTimeFormatter eventFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                                    java.time.LocalDateTime.parse(from, eventFormat);
                                    java.time.LocalDateTime.parse(to, eventFormat);
                                    taskToAdd = new EventTask(desc, from, to);
                                } catch (java.time.format.DateTimeParseException e) {
                                    throw new InvalidInputException("Invalid event format! Use: event <desc> /from <start> /to <end> (yyyy-MM-dd HHmm)");
                                }
                                break;
                            }
                        }
                        if (taskToAdd != null) {
                            tasks.add(taskToAdd);
                            Storage.saveAllState(tasks);
                            ui.showMessage("I've added this task into your list:");
                            ui.showMessage(taskToAdd.toString());
                            ui.showMessage(String.format("You now have %d task in the list", tasks.size()));
                            ui.showLine();
                        } else {
                            throw new UnknownCommandException("An error has occured, please try again");
                        }
                }
            } catch (InvalidInputException | UnknownCommandException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
