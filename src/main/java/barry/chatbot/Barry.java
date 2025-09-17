package barry.chatbot;

import barry.exceptions.InvalidInputException;
import barry.exceptions.UnknownCommandException;
import barry.tasks.DeadlineTask;
import barry.tasks.EventTask;
import barry.tasks.Task;
import barry.tasks.ToDoTask;

/**
 * The main entry point for the Barry chatbot.
 * <p>
 * This class initializes the user interface, task storage, and task list,
 * and then enters an interactive command loop with the user.
 * It parses input commands, executes the corresponding actions,
 * and updates persistent storage as needed.
 * </p>
 *
 * <h2>Supported commands</h2>
 * <ul>
 *   <li><b>{@code bye}</b> – Exit the program</li>
 *   <li><b>{@code list}</b> – Show all tasks</li>
 *   <li><b>{@code mark N}</b> – Mark task number {@code N} as done</li>
 *   <li><b>{@code unmark N}</b> – Mark task number {@code N} as not done</li>
 *   <li><b>{@code delete N}</b> – Remove task number {@code N}</li>
 *   <li><b>{@code todo <desc>}</b> – Add a to-do task</li>
 *   <li><b>{@code deadline <desc> /by yyyy-MM-dd HHmm}</b> – Add a deadline task</li>
 *   <li><b>{@code event <desc> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm}</b> – Add an event task</li>
 * </ul>
 *
 * <p>
 * If a command is invalid or arguments are missing, an error message is displayed.
 * </p>
 */
public class Barry {

    /**
     * Enum for all supported command words.
     */
    public static enum CommandWord {
    BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND, VIEW;

        /**
         * Converts a string to a CommandWord, case-insensitive.
         * Returns null if not a valid command.
         */
        public static CommandWord from(String input) {
            try {
                return CommandWord.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    /**
     * Processes a single user command and returns the response as a string.
     *
     * @param input   The user's input command.
     * @param tasks   The current TaskList.
     * @param storage The Storage instance for saving/loading tasks.
     * @return The response message to display to the user.
     */
    public static String processCommand(String input, TaskList tasks, Storage storage) {
        String command = Parser.getCommand(input);
        String arguments = Parser.getArguments(input);
        int index = -1;
        assert command != null : "Parsed command should not be null";
        assert tasks != null : "TaskList should not be null";
        assert storage != null : "Storage should not be null";

        CommandWord cmd = CommandWord.from(command);
        if (cmd == CommandWord.MARK || cmd == CommandWord.UNMARK || cmd == CommandWord.DELETE) {
            if (arguments.isEmpty()) {
                return "Please specify the task number!\n";
            }
            try {
                index = Integer.parseInt(arguments.split(" ")[0]) - 1;
            } catch (NumberFormatException e) {
                return "Invalid task number!\n";
            }
        }
        try {
            if (cmd == null) {
                throw new UnknownCommandException("I'm sorry, but I don't know what that means.");
            }
            return dispatchCommand(cmd, arguments, index, tasks, storage);
        } catch (InvalidInputException | UnknownCommandException e) {
            return e.getMessage() + "\n";
        }
    }

    /**
     * Dispatches the command to the appropriate handler.
     */
    private static String dispatchCommand(CommandWord cmd, String arguments, int index, TaskList tasks, Storage storage)
            throws InvalidInputException, UnknownCommandException {
        switch (cmd) {
        case BYE:
            return "Goodbye! Hope to see you again soon!\n";
        case LIST:
            return handleList(tasks);
        case MARK:
            return handleMark(index, tasks, storage);
        case UNMARK:
            return handleUnmark(index, tasks, storage);
        case DELETE:
            return handleDelete(index, tasks, storage);
        case TODO:
            return handleTodo(arguments, tasks, storage);
        case DEADLINE:
            return handleDeadline(arguments, tasks, storage);
        case EVENT:
            return handleEvent(arguments, tasks, storage);
        case FIND:
            return handleFind(arguments, tasks);
        case VIEW:
            return handleViewSchedule(arguments, tasks);
        default:
            throw new UnknownCommandException("I'm sorry, but I don't know what that means.");
        }
    }

    /**
     * Handles the 'viewschedule' command.
     * @param arguments The date string (yyyy-MM-dd) to filter tasks/events.
     * @param tasks The current TaskList.
     * @return The formatted list of tasks/events scheduled for the date.
     */
    private static String handleViewSchedule(String arguments, TaskList tasks) {
        if (arguments == null || arguments.trim().isEmpty()) {
            return "Please provide a date in yyyy-MM-dd format!\n";
        }
        String dateStr = arguments.trim();
        java.time.LocalDate queryDate;
        try {
            queryDate = java.time.LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (java.time.format.DateTimeParseException e) {
            return "Invalid date format! Use yyyy-MM-dd.\n";
        }
        java.util.List<Task> scheduled = getScheduledTasksForDate(tasks, queryDate);
        if (scheduled.isEmpty()) {
            return "No tasks/events scheduled for this date.\n";
        }
        return formatScheduledTasksOutput(scheduled, dateStr);
    }

    /**
     * Returns a list of tasks/events scheduled for the given date.
     */
    private static java.util.List<Task> getScheduledTasksForDate(TaskList tasks, java.time.LocalDate queryDate) {
        java.util.List<Task> scheduled = new java.util.ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.getTask(i);
            if (t instanceof barry.tasks.DeadlineTask) {
                barry.tasks.DeadlineTask d = (barry.tasks.DeadlineTask) t;
                java.time.LocalDate deadlineDate = d.getDeadline().toLocalDate();
                if (!deadlineDate.isBefore(queryDate)) {
                    scheduled.add(t);
                }
            } else if (t instanceof barry.tasks.EventTask) {
                barry.tasks.EventTask e = (barry.tasks.EventTask) t;
                java.time.LocalDate startDate = e.getStart().toLocalDate();
                java.time.LocalDate endDate = e.getEnd().toLocalDate();
                if ((queryDate.isEqual(startDate) || queryDate.isAfter(startDate)) &&
                    (queryDate.isEqual(endDate) || queryDate.isBefore(endDate))) {
                    scheduled.add(t);
                }
            }
        }
        return scheduled;
    }

    /**
     * Formats the output string for scheduled tasks/events for a given date.
     */
    private static String formatScheduledTasksOutput(java.util.List<Task> scheduled, String dateStr) {
        StringBuilder sb = new StringBuilder("Tasks/events scheduled for ").append(dateStr).append(":\n");
        for (int i = 0; i < scheduled.size(); i++) {
            sb.append(i + 1).append(".").append(scheduled.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Handles the 'find' command.
     * @param arguments The keyword to search for.
     * @param tasks The current TaskList.
     * @return The formatted list of matching tasks, or a message if none found.
     */
    private static String handleFind(String arguments, TaskList tasks) {
        if (arguments == null || arguments.trim().isEmpty()) {
            return "Please provide a keyword to search for!\n";
        }
        String keyword = arguments.trim();
        TaskList matches = tasks.findTasks(keyword);
        if (matches.size() == 0) {
            return "No matching tasks found.\n";
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matches.size(); i++) {
                int num = i + 1;
                sb.append(num).append(".").append(matches.getTask(i)).append("\n");
            }
            return sb.toString();
        }
    }

    /**
     * Handles the 'list' command.
     *
     * @param tasks The current TaskList.
     * @return The formatted list of tasks.
     */
    private static String handleList(TaskList tasks) {
        StringBuilder response = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            response.append(i + 1).append(".").append(tasks.getTask(i)).append("\n");
        }
        return response.toString();
    }

    /**
     * Handles the 'mark' command.
     *
     * @param index   The index of the task to mark as done.
     * @param tasks   The current TaskList.
     * @param storage The Storage instance.
     * @return The response message.
     */
    private static String handleMark(int index, TaskList tasks, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Invalid task number!\n";
        }
        Task task = tasks.getTask(index);
        task.markDone();
        storage.saveAllState(tasks.getAllTasks());
        return String.format("I have marked item %d as done\n%s\n", index + 1, task);
    }

    /**
     * Handles the 'unmark' command.
     *
     * @param index   The index of the task to mark as not done.
     * @param tasks   The current TaskList.
     * @param storage The Storage instance.
     * @return The response message.
     */
    private static String handleUnmark(int index, TaskList tasks, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Invalid task number!\n";
        }
        Task task = tasks.getTask(index);
        task.markUndone();
        storage.saveAllState(tasks.getAllTasks());
        return String.format("I have marked item %d as not done yet\n%s\n", index + 1, task);
    }

    /**
     * Handles the 'delete' command.
     *
     * @param index   The index of the task to delete.
     * @param tasks   The current TaskList.
     * @param storage The Storage instance.
     * @return The response message.
     */
    private static String handleDelete(int index, TaskList tasks, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Invalid task number!\n";
        }
        Task task = tasks.getTask(index);
        tasks.removeTask(index);
        storage.saveAllState(tasks.getAllTasks());
        return String.format("I have removed the following task:\n%s\nNow you have %d tasks in your list\n", task, tasks.size());
    }

    /**
     * Handles the 'todo' command.
     *
     * @param arguments The description of the todo task.
     * @param tasks     The current TaskList.
     * @param storage   The Storage instance.
     * @return The response message.
     */
    private static String handleTodo(String arguments, TaskList tasks, Storage storage) {
        if (arguments.isEmpty()) {
            return "The description of a todo cannot be empty\n";
        }
        Task task = new ToDoTask(arguments.trim());
        tasks.addTask(task);
        storage.saveAllState(tasks.getAllTasks());
        return String.format("I've added this task into your list:\n%s\nYou now have %d task in the list\n", task, tasks.size());
    }

    /**
     * Handles the 'deadline' command.
     *
     * @param arguments The arguments string containing description and deadline.
     * @param tasks     The current TaskList.
     * @param storage   The Storage instance.
     * @return The response message.
     * @throws InvalidInputException if the input format is invalid.
     */
    private static String handleDeadline(String arguments, TaskList tasks, Storage storage) throws InvalidInputException {
        String[] deadlineParts = arguments.split("/by", 2);
        String deadlineDesc = deadlineParts[0].trim();
        String deadline = deadlineParts.length > 1 ? deadlineParts[1].trim() : "";
        if (deadlineDesc.isEmpty() || deadline.isEmpty()) {
            throw new InvalidInputException("Deadline description and date/time cannot be empty");
        }
        try {
            java.time.format.DateTimeFormatter deadlineFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            java.time.LocalDateTime.parse(deadline, deadlineFormat);
            Task task = new DeadlineTask(deadlineDesc, deadline);
            tasks.addTask(task);
            storage.saveAllState(tasks.getAllTasks());
            return String.format("I've added this task into your list:\n%s\nYou now have %d task in the list\n", task, tasks.size());
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidInputException("Invalid deadline format! Use: deadline <desc> /by <date> (yyyy-MM-dd HHmm)");
        }
    }

    /**
     * Handles the 'event' command.
     *
     * @param arguments The arguments string containing description, start, and end times.
     * @param tasks     The current TaskList.
     * @param storage   The Storage instance.
     * @return The response message.
     * @throws InvalidInputException if the input format is invalid.
     */
    private static String handleEvent(String arguments, TaskList tasks, Storage storage) throws InvalidInputException {
        String[] eventParts = arguments.split("/from|/to");
        String desc = eventParts[0].trim();
        String from = eventParts.length > 1 ? eventParts[1].trim() : "";
        String to = eventParts.length > 2 ? eventParts[2].trim() : "";
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InvalidInputException("Event description, start, and end cannot be empty");
        }
        try {
            java.time.format.DateTimeFormatter eventFormat = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            java.time.LocalDateTime.parse(from, eventFormat);
            java.time.LocalDateTime.parse(to, eventFormat);
            Task task = new EventTask(desc, from, to);
            tasks.addTask(task);
            storage.saveAllState(tasks.getAllTasks());
            return String.format("I've added this task into your list:\n%s\nYou now have %d task in the list\n", task, tasks.size());
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidInputException("Invalid event format! Use: event <desc> /from <start> /to <end> (yyyy-MM-dd HHmm)");
        }
    }

    /**
     * Processes a single line of user input and returns Barry's response.
     *
     * @param input The input from User.
     * @return The response from Barry.
     */
    public String getResponse(String input) {
        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());
        return processCommand(input, tasks, storage);
    }

    /**
     * Entry point of the application. Initializes the UI, storage, and task list,
     * then starts a loop that continuously reads and processes user commands
     * until the {@code bye} command is issued.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        Ui ui = new Ui();
        ui.showWelcome();
        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());

        while (true) {
            String input = ui.readCommand();
            String response = processCommand(input, tasks, storage);
            ui.showMessage(response);
            ui.showLine();
            if (input.trim().equalsIgnoreCase("bye")) {
                break;
            }
        }
    }
}
