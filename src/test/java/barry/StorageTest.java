package barry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import barry.tasks.Task;
import barry.tasks.ToDoTask;

public class StorageTest {
    private Storage storage;

    @BeforeEach
    public void setUp() throws Exception {
        File tempFile = File.createTempFile("test_tasks", ".txt");
        tempFile.deleteOnExit();
        storage = new Storage(tempFile.getAbsolutePath());
    }

    @Test
    public void saveAndLoadTasks() {
        TaskList tasksToSave = new TaskList();
        tasksToSave.addTask(new ToDoTask("eat"));
        tasksToSave.addTask(new ToDoTask("sleep"));

        storage.saveAllState(tasksToSave.getAllTasks());
        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(2, loadedTasks.size());
        assertEquals("eat", loadedTasks.get(0).toString().trim().substring(7));
        assertEquals("sleep", loadedTasks.get(1).toString().trim().substring(7));
    }

    @Test
    public void loadFromNonExistentFile_returnsEmptyList() {
        File nonExistent = new File("nonexistentfile.txt");
        Storage s = new Storage(nonExistent.getAbsolutePath());
        ArrayList<Task> loaded = s.load();
        assertTrue(loaded.isEmpty());

        if (nonExistent.exists()) {
            nonExistent.delete();
        }
    }
}