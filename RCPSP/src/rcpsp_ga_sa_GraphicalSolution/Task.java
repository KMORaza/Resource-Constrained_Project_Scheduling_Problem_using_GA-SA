package rcpsp_ga_sa_GraphicalSolution;
public class Task {
    private int id;
    private int duration;
    private int[] resources;
    public Task(int id, int duration, int[] resources) {
        this.id = id;
        this.duration = duration;
        this.resources = resources;
    }
    public int getId() {
        return id;
    }
    public int getDuration() {
        return duration;
    }
    public int[] getResources() {
        return resources;
    }
}
