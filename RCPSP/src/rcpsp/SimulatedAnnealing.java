package rcpsp;
import java.util.Random;
public class SimulatedAnnealing {
    private RCPSPProblem problem;
    private double initialTemperature;
    private double coolingRate;
    public SimulatedAnnealing(RCPSPProblem problem, double initialTemperature, double coolingRate) {
        this.problem = problem;
        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
    }
    public Chromosome run(Chromosome initialSolution) {
        Chromosome currentSolution = initialSolution;
        Chromosome bestSolution = currentSolution;
        double temperature = initialTemperature;
        while (temperature > 1) {
            Chromosome newSolution = generateNeighbor(currentSolution);
            currentSolution.calculateFitness();
            newSolution.calculateFitness();
            if (acceptanceProbability(currentSolution.getFitness(), newSolution.getFitness(), temperature) > Math.random()) {
                currentSolution = newSolution;
            }
            if (currentSolution.getFitness() > bestSolution.getFitness()) {
                bestSolution = currentSolution;
            }
            temperature *= coolingRate;
        }
        return bestSolution;
    }
    private Chromosome generateNeighbor(Chromosome solution) {
        int[] newSchedule = solution.getSchedule().clone();
        Random rand = new Random();
        int index1 = rand.nextInt(newSchedule.length);
        int index2 = rand.nextInt(newSchedule.length);
        int temp = newSchedule[index1];
        newSchedule[index1] = newSchedule[index2];
        newSchedule[index2] = temp;
        Chromosome newSolution = new Chromosome(problem);
        newSolution.setSchedule(newSchedule);
        return newSolution;
    }
    private double acceptanceProbability(double currentFitness, double newFitness, double temperature) {
        if (newFitness > currentFitness) {
            return 1.0;
        }
        return Math.exp((newFitness - currentFitness) / temperature);
    }
}
