package rcpsp;
public class Task {
    private int id;
    private int duration;
    private int[] resourceRequirements; 
    public Task(int id, int duration, int[] resourceRequirements) {
        this.id = id;
        this.duration = duration;
        this.resourceRequirements = resourceRequirements;
    }
    public int getId() {
        return id;
    }
    public int getDuration() {
        return duration;
    }
    public int[] getResourceRequirements() {
        return resourceRequirements;
    }
}
