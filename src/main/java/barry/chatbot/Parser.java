package barry.chatbot;

/**
 * Utility class for parsing user input into commands and arguments.
 */
public class Parser {

    /**
     * @param input the raw user input string
     * @return the first word of the input string (the command),
     *         returns an empty string if the input is empty or only contains whitespace
     */
    public static String getCommand(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts[0];
    }

    /**
     * @param input the raw user input string
     * @return the substring after the first word (the command),
     *         or an empty string if there are no arguments
     */
    public static String getArguments(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
