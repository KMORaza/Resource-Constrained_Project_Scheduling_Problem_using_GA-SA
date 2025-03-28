package rcpsp;
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
    }
}
