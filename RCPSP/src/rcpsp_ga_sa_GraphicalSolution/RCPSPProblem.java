package rcpsp_ga_sa_GraphicalSolution;
public class RCPSPProblem {
    private Task[] tasks;
    private int numResources;
    private int[][] precedenceMatrix;
    private int[] resourceAvailability;
    public RCPSPProblem(Task[] tasks, int numResources, int[][] precedenceMatrix, int[] resourceAvailability) {
        this.tasks = tasks;
        this.numResources = numResources;
        this.precedenceMatrix = precedenceMatrix;
        this.resourceAvailability = resourceAvailability;
    }
    public Task[] getTasks() {
        return tasks;
    }

    public int[][] getPrecedenceMatrix() {
        return precedenceMatrix;
    }

    public int[] getResourceAvailability() {
        return resourceAvailability;
    }
}
