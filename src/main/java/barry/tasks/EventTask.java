package barry.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public EventTask(String desc, String startStr, String endStr) {
        super(desc);
        assert startStr != null && !startStr.isEmpty() && endStr != null && !endStr.isEmpty()
                : "start and end string should not be null or empty";
        this.start = LocalDateTime.parse(startStr, INPUT_FORMAT);
        this.end = LocalDateTime.parse(endStr, INPUT_FORMAT);
    }

    public EventTask(String desc, LocalDateTime start, LocalDateTime end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public String toSaveString() {
        return "E" + super.toSaveString() + String.format("| %s | %s ", start.format(INPUT_FORMAT), end.format(INPUT_FORMAT));
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), start.format(OUTPUT_FORMAT), end.format(OUTPUT_FORMAT));
    }
}
