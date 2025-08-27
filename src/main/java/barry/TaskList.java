package barry;

import java.util.ArrayList;

import barry.tasks.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

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

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
