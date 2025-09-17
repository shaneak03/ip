// This test file was created with the assistance of GitHub Copilot (AI code assistant).
// Copilot helped generate tests for the Parser class, including event command parsing and argument extraction.
package barry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import barry.chatbot.Parser;

public class ParserEventTest {
    @Test
    public void testGetCommand_event() {
        assertEquals("event",
                     Parser.getCommand("event play /from 2025-08-26 1400 /to 2025-08-26 1800"));
    }

    @Test
    public void testGetArguments_event() {
        assertEquals("play /from 2025-08-26 1400 /to 2025-08-26 1800",
                     Parser.getArguments("event play /from 2025-08-26 1400 /to 2025-08-26 1800"));
    }

    @Test
    public void testGetCommand_empty() {
        assertEquals("", Parser.getCommand(""));
    }
}
