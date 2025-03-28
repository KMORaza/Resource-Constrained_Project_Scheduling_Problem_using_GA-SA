package rcpsp;
import java.util.ArrayList;
import java.util.List;
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
    public int getNumResources() {
        return numResources;
    }
    public int[][] getPrecedenceMatrix() {
        return precedenceMatrix;
    }
    public int[] getResourceAvailability() {
        return resourceAvailability;
    }
    public void calculateTimes(int[] startTimes, int[] finishTimes, int[] slackTimes) {
        int[] earliestStartTimes = new int[tasks.length];
        int[] latestStartTimes = new int[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            earliestStartTimes[i] = 0;
            latestStartTimes[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < tasks.length; i++) {
            Task task = tasks[i];
            int earliestFinishTime = 0;
            for (int j = 0; j < tasks.length; j++) {
                if (precedenceMatrix[j][i] == 1) {  
                    earliestFinishTime = Math.max(earliestFinishTime, finishTimes[j]);
                }
            }
            startTimes[i] = earliestFinishTime;
            finishTimes[i] = startTimes[i] + task.getDuration();
        }
        for (int i = tasks.length - 1; i >= 0; i--) {
            Task task = tasks[i];
            int latestFinishTime = Integer.MAX_VALUE;
            for (int j = 0; j < tasks.length; j++) {
                if (precedenceMatrix[i][j] == 1) { 
                    latestFinishTime = Math.min(latestFinishTime, startTimes[j]);
                }
            }
            latestStartTimes[i] = latestFinishTime - task.getDuration();
            slackTimes[i] = latestStartTimes[i] - startTimes[i];
        }
        int makespan = 0;
        for (int i = 0; i < finishTimes.length; i++) {
            makespan = Math.max(makespan, finishTimes[i]);
        }

        System.out.println("Project Completion Time (Makespan): " + makespan);
    }
public boolean scheduleTasks(int[] schedule, int[] startTimes, int[] finishTimes) {
    int[] resourceUsageAtEachTimeStep = new int[numResources];
    boolean feasible = true;
    for (int i = 0; i < schedule.length; i++) {
        int taskIndex = schedule[i];
        Task task = tasks[taskIndex];
        int startTime = -1;
        for (int t = 0; t < startTimes.length; t++) {
            if (isResourceAvailable(t, task, resourceUsageAtEachTimeStep)) {
                startTime = t;
                break;
            }
        }
        if (startTime == -1) {
            feasible = false;  
            System.out.println("No feasible start time for task " + taskIndex);
            break;
        }
        startTimes[taskIndex] = startTime;
        finishTimes[taskIndex] = startTime + task.getDuration();
        for (int t = startTime; t < finishTimes[taskIndex]; t++) {
            for (int r = 0; r < numResources; r++) {
                resourceUsageAtEachTimeStep[r] += task.getResourceRequirements()[r];
                if (resourceUsageAtEachTimeStep[r] > resourceAvailability[r]) {
                    feasible = false;
                    System.out.println("Resource " + r + " over-allocated at time " + t);
                    break;
                }
            }
        }
        if (!feasible) break;
    }
    return feasible;
}
private boolean isResourceAvailable(int timeStep, Task task, int[] resourceUsageAtEachTimeStep) {
    for (int r = 0; r < numResources; r++) {
        if (resourceUsageAtEachTimeStep[r] + task.getResourceRequirements()[r] > resourceAvailability[r]) {
            return false;
        }
    }
    return true;
}
    public void calculateResourceUsage(int[] startTimes, int[] finishTimes) {
    int[] resourceUsageAtEachTimeStep = new int[resourceAvailability.length];
    int maxTime = Integer.MIN_VALUE;
    for (int i = 0; i < finishTimes.length; i++) {
        maxTime = Math.max(maxTime, finishTimes[i]);
    }
    for (int time = 0; time <= maxTime; time++) {
        for (int i = 0; i < tasks.length; i++) {
            if (startTimes[i] <= time && time < finishTimes[i]) {
                Task task = tasks[i];
                for (int j = 0; j < numResources; j++) {
                    resourceUsageAtEachTimeStep[j] += task.getResourceRequirements()[j];
                }
            }
        }
        if (time % 10 == 0 || time == maxTime) { 
            System.out.print("Time " + time + ": ");
            for (int j = 0; j < numResources; j++) {
                System.out.print("Resource " + j + ": " + resourceUsageAtEachTimeStep[j] + " ");
            }
            System.out.println();
        }
    }
}
    public List<Integer> calculateCriticalPath(int[] startTimes, int[] finishTimes) {
        List<Integer> criticalPath = new ArrayList<>();
        int projectMakespan = 0;
        for (int i = 0; i < tasks.length; i++) {
            projectMakespan = Math.max(projectMakespan, finishTimes[i]);
        }
        for (int i = 0; i < tasks.length; i++) {
            if (finishTimes[i] == projectMakespan) {
                criticalPath.add(i);
            }
        }
        return criticalPath;
    }
}
