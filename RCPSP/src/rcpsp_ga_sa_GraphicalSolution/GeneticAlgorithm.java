package rcpsp_ga_sa_GraphicalSolution;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GeneticAlgorithm {
    private RCPSPProblem problem;
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    public GeneticAlgorithm(RCPSPProblem problem, int populationSize, double crossoverRate, double mutationRate) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
    public Chromosome run() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<Task> schedule = new ArrayList<>();
            for (Task task : problem.getTasks()) {
                schedule.add(task);
            }
            population.add(new Chromosome(schedule));
        }
        Chromosome bestChromosome = population.get(0);
        for (Chromosome individual : population) {
            if (individual.getFitness() < bestChromosome.getFitness()) {
                bestChromosome = individual;
            }
        }
        return bestChromosome;
    }
}
