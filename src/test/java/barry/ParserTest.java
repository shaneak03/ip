package barry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void testGetCommand() {
        assertEquals("todo", Parser.getCommand("todo eat"));
        assertEquals("deadline", Parser.getCommand("deadline sleep /by 2025-08-26 2359"));
    }

    @Test
    public void testGetArguments() {
        assertEquals("eat", Parser.getArguments("todo eat"));
        assertEquals("sleep /by 2025-08-26 2359", Parser.getArguments("deadline sleep /by 2025-08-26 2359"));
    }
}