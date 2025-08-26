import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public DeadlineTask(String desc, String deadlineStr) {
        super(desc);
        this.deadline = LocalDateTime.parse(deadlineStr, INPUT_FORMAT);
    }

    public DeadlineTask(String desc, LocalDateTime deadline) {
        super(desc);
        this.deadline = deadline;
    }

    @Override
    public String toSaveString() {
        return "d" + super.toSaveString() + String.format("| %s ", deadline.format(INPUT_FORMAT));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), deadline.format(OUTPUT_FORMAT));
    }
}
