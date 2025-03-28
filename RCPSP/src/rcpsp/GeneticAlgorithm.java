package rcpsp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class GeneticAlgorithm {
    private RCPSPProblem problem;
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private Random random;
    public GeneticAlgorithm(RCPSPProblem problem, int populationSize, double crossoverRate, double mutationRate) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.random = new Random();
    }
    public Chromosome run() {
        List<Chromosome> population = initializePopulation();
        int maxGenerations = 100; 
        int generation = 0;
        while (generation < maxGenerations) {
            for (Chromosome chromosome : population) {
                chromosome.calculateFitness();
            }
            List<Chromosome> selected = selection(population);
            List<Chromosome> nextGeneration = new ArrayList<>();
            while (nextGeneration.size() < populationSize) {
                Chromosome parent1 = selected.get(random.nextInt(selected.size()));
                Chromosome parent2 = selected.get(random.nextInt(selected.size()));
                Chromosome offspring = crossover(parent1, parent2);
                mutate(offspring);
                nextGeneration.add(offspring);
            }
            population = nextGeneration;
            double bestFitness = population.stream()
                                           .mapToDouble(Chromosome::getFitness)
                                           .max()
                                           .orElse(Double.NEGATIVE_INFINITY);
            System.out.println("Generation " + generation + " - Best Fitness: " + bestFitness);
            generation++;
        }
        return getBestSolution(population);
    }
    private List<Chromosome> initializePopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome chromosome = new Chromosome(problem);
            chromosome.randomize();
            population.add(chromosome);
        }
        return population;
    }
    private List<Chromosome> selection(List<Chromosome> population) {
        population.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness()));
        return population.subList(0, population.size() / 2);
    }
    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        Chromosome offspring = new Chromosome(problem);
        if (random.nextDouble() < crossoverRate) {
            int crossoverPoint = random.nextInt(problem.getTasks().length);
            for (int i = 0; i < crossoverPoint; i++) {
                offspring.getSchedule()[i] = parent1.getSchedule()[i];
            }
            for (int i = crossoverPoint; i < problem.getTasks().length; i++) {
                offspring.getSchedule()[i] = parent2.getSchedule()[i];
            }
        } else {
            System.arraycopy(parent1.getSchedule(), 0, offspring.getSchedule(), 0, problem.getTasks().length);
        }
        return offspring;
    }
    private void mutate(Chromosome chromosome) {
        if (random.nextDouble() < mutationRate) {
            int index1 = random.nextInt(problem.getTasks().length);
            int index2 = random.nextInt(problem.getTasks().length);
            int temp = chromosome.getSchedule()[index1];
            chromosome.getSchedule()[index1] = chromosome.getSchedule()[index2];
            chromosome.getSchedule()[index2] = temp;
        }
    }
    private Chromosome getBestSolution(List<Chromosome> population) {
        return population.stream()
                         .max((a, b) -> Double.compare(a.getFitness(), b.getFitness()))
                         .orElseThrow(() -> new RuntimeException("Population is empty"));
    }
}
