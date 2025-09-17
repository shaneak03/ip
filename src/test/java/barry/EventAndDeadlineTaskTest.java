// This test file was created with the assistance of GitHub Copilot (AI code assistant).
// Copilot helped generate tests for EventTask and DeadlineTask, including field access and string output validation.
package barry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import barry.tasks.EventTask;
import barry.tasks.DeadlineTask;
import java.time.LocalDateTime;

public class EventAndDeadlineTaskTest {
    @Test
    public void eventTask_fieldsAndToString() {
        EventTask event = new EventTask("play",
                                        LocalDateTime.of(2025,8,26,14,0),
                                        LocalDateTime.of(2025,8,26,18,0));
        assertEquals(LocalDateTime.of(2025,8,26,14,0), event.getStart());
        assertEquals(LocalDateTime.of(2025,8,26,18,0), event.getEnd());
        String s = event.toString();
        assertTrue(s.contains("play"));
    assertTrue(s.toLowerCase().contains("aug 26 2025, 2:00pm"));
    assertTrue(s.toLowerCase().contains("aug 26 2025, 6:00pm"));
    }

    @Test
    public void deadlineTask_fieldsAndToString() {
        DeadlineTask deadline = new DeadlineTask("sleep",
                                                 LocalDateTime.of(2025,8,26,23,59));
        assertEquals(LocalDateTime.of(2025,8,26,23,59), deadline.getDeadline());
        String s = deadline.toString();
        assertTrue(s.contains("sleep"));
    assertTrue(s.toLowerCase().contains("aug 26 2025, 11:59pm"));
    }
}
