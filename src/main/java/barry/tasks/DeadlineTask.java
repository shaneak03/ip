package barry.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public DeadlineTask(String desc, String deadlineStr) {
        super(desc);
        assert deadlineStr != null && !deadlineStr.isEmpty() : "Deadline string should not be null or empty";
        this.deadline = LocalDateTime.parse(deadlineStr, INPUT_FORMAT);
    }

    public DeadlineTask(String desc, LocalDateTime deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toSaveString() {
        return "D" + super.toSaveString() + String.format("| %s ", deadline.format(INPUT_FORMAT));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), deadline.format(OUTPUT_FORMAT));
    }
}
