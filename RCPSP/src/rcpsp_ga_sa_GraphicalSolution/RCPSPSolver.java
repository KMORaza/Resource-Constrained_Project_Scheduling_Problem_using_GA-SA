package rcpsp_ga_sa_GraphicalSolution;
import org.knowm.xchart.*;
import java.util.List;
import java.util.ArrayList;
public class RCPSPSolver {
    public static void main(String[] args) {
        Task[] tasks = {
                new Task(0, 5, new int[]{2}),
                new Task(1, 3, new int[]{1}),
                new Task(2, 4, new int[]{3}),
                new Task(3, 2, new int[]{1}),
        };
        int[][] precedenceMatrix = {
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 0}
        };
        int[] resourceAvailability = {5};
        RCPSPProblem problem = new RCPSPProblem(tasks, 1, precedenceMatrix, resourceAvailability);
        GeneticAlgorithm ga = new GeneticAlgorithm(problem, 50, 0.7, 0.05);
        Chromosome gaSolution = ga.run();
        System.out.println("Final Best Solution (Makespan): " + gaSolution.getFitness());
        visualizeSolution(gaSolution, tasks);
    }
    public static void visualizeSolution(Chromosome solution, Task[] tasks) {
        List<Integer> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        int totalDuration = solution.getFitness();
        int currentTime = 0;
        for (int i = 0; i < tasks.length; i++) {
            Task task = tasks[i];
            int taskStartTime = currentTime;
            int taskEndTime = taskStartTime + task.getDuration();
            xData.add(taskStartTime);
            xData.add(taskEndTime);
            yData.add(i);
            yData.add(i);
            currentTime = taskEndTime;
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).title("RCPSP Task Schedule").xAxisTitle("Time").yAxisTitle("Task ID").build();
        chart.addSeries("Task Schedule", xData, yData);
        new SwingWrapper<>(chart).displayChart();
    }


}
