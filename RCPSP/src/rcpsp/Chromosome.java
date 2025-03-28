package rcpsp;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Chromosome {
    private RCPSPProblem problem;
    private int[] schedule;  
    private double fitness = Double.MAX_VALUE;  
    public Chromosome(RCPSPProblem problem) {
        this.problem = problem;
        this.schedule = new int[problem.getTasks().length];
    }
    public int[] getSchedule() {
        return schedule;
    }
    public void randomize() {
        List<Integer> taskOrder = new ArrayList<>();
        for (int i = 0; i < problem.getTasks().length; i++) {
            taskOrder.add(i);
        }
        Collections.shuffle(taskOrder);

        for (int i = 0; i < problem.getTasks().length; i++) {
            schedule[i] = taskOrder.get(i);
        }
    }
public void calculateFitness() {
    int[] startTimes = new int[problem.getTasks().length];
    int[] finishTimes = new int[problem.getTasks().length];
    boolean feasible = problem.scheduleTasks(schedule, startTimes, finishTimes);
    if (feasible) {
        int makespan = finishTimes[schedule[schedule.length - 1]];
        fitness = makespan;
    } else {
        fitness = Double.MAX_VALUE;
    }
}
    public double getFitness() {
        return fitness;
    }
}
