package barry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import barry.tasks.DeadlineTask;
import barry.tasks.EventTask;
import barry.tasks.Task;
import barry.tasks.ToDoTask;

/**
 * Handles saving and loading of tasks to and from a .txt file
 */
public class Storage {
    private static final String DEFAULT_PATH = "data/tasks.txt";
    private String filePath = DEFAULT_PATH;

    /**
     * Constructor for a Storage instance
     * If filepath or parent directories does not exist, they will be created
     * @param filePath the path to the storage file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        ensureParentDirExists();
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing storage file: " + e.getMessage());
        }
    }

    /**
     * Constructor for a Storage instance using default filepath
     * If filepath or parent directories does not exist, they will be created
     */
    public Storage() {
        ensureParentDirExists();
        File file = new File(DEFAULT_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing storage file: " + e.getMessage());
        }
    }

    /**
     * Creates parent directories if they don't exist
     */
    private void ensureParentDirExists() {
        File file = new File(this.filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    /**
     * Appends tasks to file
     * @param task the task to save
     */
    public void save(Task task) {
        ensureParentDirExists();
        try (FileWriter writer = new FileWriter(this.filePath, true)) {
            writer.write(task.toSaveString() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error saving task: " + e.getMessage());
        }
    }

    /**
     * Clears all tasks in the storage file
     */
    public void delete() {
        ensureParentDirExists();
        try (FileWriter writer = new FileWriter(this.filePath)) {
        } catch (IOException e) {
            System.out.println("Error deleting tasks: " + e.getMessage());
        }
    }

    /**
     * Saves a list of tasks in the file
     * @param taskList list of tasks to be saved
     */
    public void saveAllState(ArrayList<Task> taskList) {
        ensureParentDirExists();
        try (FileWriter writer = new FileWriter(this.filePath)) {
            for (Task task : taskList) {
                writer.write(task.toSaveString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Reads all tasks from .txt file into an ArrayList of tasks
     * @return ArrayList of tasks from file
     */
    public ArrayList<Task> load() {
        ensureParentDirExists();
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                String type = parts[0];
                boolean isDone = parts[1].equals("true");
                String desc = parts[2];
                switch (type) {
                case "T":
                    Task todo = new ToDoTask(desc);
                    if (isDone) todo.markDone();
                    tasks.add(todo);
                    break;
                case "D":
                    String deadline = parts[3];
                    Task deadlineTask = new DeadlineTask(desc, deadline);
                    if (isDone) deadlineTask.markDone();
                    tasks.add(deadlineTask);
                    break;
                case "E":
                    String start = parts[3];
                    String end = parts[4];
                    Task eventTask = new EventTask(desc, start, end);
                    if (isDone) eventTask.markDone();
                    tasks.add(eventTask);
                    break;
                default:
                    System.out.println("Unknown task type: " + type);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }
}
