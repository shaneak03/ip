package barry;

import java.util.ArrayList;

import barry.tasks.Task;

/**
 * Represents a list of {@link Task} objects.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty list of tasks
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a list of tasks based on existing tasks
     * @param tasks existing list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the list
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Gets tasks at a specified index
     * @param index the position of task on the list
     * @return the task at the specified index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Removes task at a specified index
     * @param index the position of task on the list
     * @return the removed task
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword the keyword to search for
     * @return a list of matching tasks
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.matches(keyword)) {
                result.add(task);
            }
        }
        return new TaskList(result);
    }

    /**
     * Returns number of tasks on the list
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns full list of tasks
     * @return the underlying {@link ArrayList} of tasks
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
