public class EventTask extends Task {
    private String start;
    private String end;

    public EventTask(String desc, String start, String end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("[T]%s (from: %s to: %s)", super.toString(), start, end);
    }
}
