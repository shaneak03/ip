// This test file was created with the assistance of GitHub Copilot (AI code assistant).
// Copilot helped generate tests for the TaskList class, including finding tasks, marking/unmarking, and handling invalid indices.
package barry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import barry.chatbot.TaskList;
import barry.tasks.ToDoTask;
import barry.tasks.EventTask;
import barry.tasks.DeadlineTask;
import java.time.LocalDateTime;

public class TaskListFindMarkTest {
    @Test
    public void findTasks_keywordMatch() {
        TaskList list = new TaskList();
        list.addTask(new ToDoTask("eat"));
        list.addTask(new DeadlineTask("sleep", LocalDateTime.of(2025,8,26,23,59)));
        list.addTask(new EventTask("play", LocalDateTime.of(2025,8,26,14,0), LocalDateTime.of(2025,8,26,18,0)));
        TaskList found = list.findTasks("play");
        assertEquals(1, found.size());
    }

    @Test
    public void markAndUnmarkTask() {
    TaskList list = new TaskList();
    ToDoTask task = new ToDoTask("eat");
    list.addTask(task);
    assertTrue(list.getTask(0).toString().contains("[ ]"));
    list.getTask(0).markDone();
    assertTrue(list.getTask(0).toString().contains("[X]"));
    list.getTask(0).markUndone();
    assertTrue(list.getTask(0).toString().contains("[ ]"));
    }

    @Test
    public void markInvalidIndex_throws() {
        TaskList list = new TaskList();
        assertThrows(AssertionError.class, () -> list.getTask(0).markDone());
    }
}
