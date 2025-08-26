package barry.tasks;

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

    public void markUndone() {
        this.isDone = false;
    }

    public String toSaveString() {
        return String.format(" | %b | %s ", this.isDone, this.desc);
    }

    @Override
    public String toString() {
        String doneStatus = isDone ? "[X]" : "[ ]";
        return String.format("%s %s", doneStatus, this.desc);
    }
}
