package barry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import barry.chatbot.TaskList;
import barry.tasks.ToDoTask;

public class TaskListTest {
    @Test
    public void addAndGetTask() {
        TaskList list = new TaskList();
        ToDoTask task = new ToDoTask("eat");
        list.addTask(task);
        assertEquals(1, list.size());
        assertEquals(task, list.getTask(0));
    }

    @Test
    public void removeTask() {
        TaskList list = new TaskList();
        ToDoTask task = new ToDoTask("eat");
        list.addTask(task);
        list.removeTask(0);
        assertEquals(0, list.size());
    }

    @Test
    public void removeTaskAtInvalidIndex() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.removeTask(0));
    }
}