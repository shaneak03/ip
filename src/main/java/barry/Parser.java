package barry;

public class Parser {
    
    public static String getCommand(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts[0];
    }

    public static String getArguments(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
