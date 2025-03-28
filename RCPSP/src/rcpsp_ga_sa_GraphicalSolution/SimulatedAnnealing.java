package rcpsp_ga_sa_GraphicalSolution;
public class SimulatedAnnealing {
    private Chromosome initialSolution;
    private double temperature;
    private double coolingRate;
    public SimulatedAnnealing(Chromosome initialSolution, double temperature, double coolingRate) {
        this.initialSolution = initialSolution;
        this.temperature = temperature;
        this.coolingRate = coolingRate;
    }
    public Chromosome optimize() {
        Chromosome currentSolution = initialSolution;
        while (temperature > 1) {
            Chromosome neighbor = generateNeighbor(currentSolution);
            double acceptanceProbability = calculateAcceptanceProbability(currentSolution, neighbor);
            if (Math.random() < acceptanceProbability) {
                currentSolution = neighbor;
            }
            temperature *= coolingRate;
        }
        return currentSolution;
    }
    private double calculateAcceptanceProbability(Chromosome currentSolution, Chromosome neighbor) {
        if (neighbor.getFitness() < currentSolution.getFitness()) {
            return 1.0;
        }
        return Math.exp((currentSolution.getFitness() - neighbor.getFitness()) / temperature);
    }
    private Chromosome generateNeighbor(Chromosome currentSolution) {
        Chromosome neighbor = new Chromosome(currentSolution.getProblem()); // Use getter to access problem
        neighbor.mutate();  // Randomly mutate to generate a new neighbor
        return neighbor;
    }
}
