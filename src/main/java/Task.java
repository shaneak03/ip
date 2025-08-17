public class Task {
    private String desc;
    private boolean isDone;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        String doneStatus = isDone ? "[X]" : "[ ]";
        return String.format("%s %s", doneStatus, this.desc);
    }
}
