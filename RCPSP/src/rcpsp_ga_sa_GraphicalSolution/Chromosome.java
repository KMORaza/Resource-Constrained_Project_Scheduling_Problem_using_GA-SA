package rcpsp_ga_sa_GraphicalSolution;
import java.util.List;
public class Chromosome {
    private List<Task> schedule;
    private int fitness;
    public Chromosome(List<Task> schedule) {
        this.schedule = schedule;
        this.fitness = calculateFitness();
    }
    public int calculateFitness() {
        int time = 0;
        for (Task task : schedule) {
            time += task.getDuration();
        }
        return time;
    }
    public List<Task> getSchedule() {
        return schedule;
    }
    public int getFitness() {
        return fitness;
    }
    public List<Task> getProblem() {
        return null;
    }
    public void mutate() {
    }
}
