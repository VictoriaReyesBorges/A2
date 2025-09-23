import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GA_Simulation {

  // Use the instructions to identify the class variables, constructors, and methods you need
  public static Random rng; 
  private int n; // number of individuals in each generation
  private int k; // number of winners allowed to reproduce
  private int r; // number of rounds of evolution to run
  private int c_0; // initial chromosome size
  private int c_max; // maximum chromosome size
  private float m; // mutation rate per gene
  private int g; // number of states or letters per gene


  // constructor for GA_Simulation
  /**
   * initializes the parameters
   * @param n number of individuals in each generation
   * @param k number of winers per generation
   * @param r number of rounds to run
   * @param c_0 initial chromosome size
   * @param c_max maximum chromosome size
   * @param m mutation rate
   * @param g number of possible gene status
   * @return nothing
   */

  public GA_Simulation(int n, int k, int r, int c_0, int c_max, float m, int g){
    this.n = n; 
    this.k= k;
    this.r= r;
    this.c_0 = c_0;
    this.c_max = c_max;
    this.m= m;
    this.g = g;
  }


  // what "kth" means - 
  // The number of winners (individuals allowed to reproduce) in each generation, ***k*** (15)
  /**
   * will print statistics about current generation
   * such as fitness of fittest individual in generation, the ***k***th individual, and least fit individual
   * additionally, it will also show the actual chromosome of best the individual
   * @param roundNumber
   * @param bestFitness
   * @param kthIndividual
   * @param leastFitness
   * @param best
   * @return Nothing, describes and prints statistics about current generation
   */
    private void describeGeneration(int roundNumber, int bestFitness, int kthFitness, int leastFitness, Individual best) {
      printGenInfo(roundNumber, bestFitness, kthFitness, leastFitness, best);
    }


  /** Provided method that prints out summary statistics for a given
   * generation, based on the information provided
   * @param roundNumber: Which round of evolution are we on, from 0 to n
   * @param bestFitness: Fitness of top-ranked (most fit) individual
   * @param kthFitness: Fitness of kth-ranked individual
   * @param leastFitness: Fitness of lowest-ranked (least fit) individual
   * @param best: Individual with highest fitness
   * @return: Nothing, prints statistics to standard out
   */
  private void printGenInfo(int roundNumber, int bestFitness, int kthFitness, int leastFitness, Individual best) {
    System.out.println("Round " + roundNumber + ":");
    System.out.println("Best fitness: " + bestFitness);
    System.out.println("k-th (" + k + ") fitness: " + kthFitness);
    System.out.println("Least fit: " + leastFitness);
    System.out.println("Best chromosome: " + best);
    System.out.println(); // blank line to match the example format
  }

  /** Provided method that sorts population by fitness score, best first
   * @param pop: ArrayList of Individuals in the current generation
   * @return: Nothing. ArrayList is sorted in place
   */
  public void rankPopulation(ArrayList<Individual> pop) {
    // sort population by fitness
    Comparator<Individual> ranker = new Comparator<>() {
      // this order will sort higher scores at the front
      public int compare(Individual c1, Individual c2) {
        return (int)Math.signum(c2.getFitness()-c1.getFitness());
      }
    };
    pop.sort(ranker);
  }
  

  /**
   * initializes the first generation of n random individuals. 
   * @return ArrayList of Individuals which represent the initial population
   */
  public ArrayList<Individual> init(){
    ArrayList<Individual> population = new ArrayList<>();
    for (int i=0; i < n; i++){
      population.add(new Individual(c_0, g, rng));
    }
    return population;
  }

  

  public ArrayList<Individual> evolve(ArrayList<Individual> currentPop) {
    ArrayList<Individual> nextGeneration = new ArrayList<>();

    //select the top k winners
    for (int i=0; i < n; i++){
      //randomly pick two parents from the top k
      int parent1Index = rng.nextInt(k);
      int parent2Index = rng.nextInt(k);
      Individual parent1 = currentPop.get(parent1Index);
      Individual parent2 = currentPop.get(parent2Index);
      
      //create offspring 
      Individual offspring = new Individual(parent1Index, parent2Index, rng);
      nextGeneration.add(offspring);
  }

  return nextGeneration;

  }


  public void run(){
    init();
    rankPopulation(null);
    describeGeneration();

    for (int gen = 1; gen <=r ; gen ++){
      evolve(null);
      rankPopulation(population);
      describeGeneration(gen, gen, gen, gen, null);
    }

  } 



  public static void main(String[] args) {
    // This first block of code establishes a random seed, which will make
    // it easier to test your code. The output should remain consistent if the
    // seed is the same. To run with a specific seed, you can run from the
    // command line like:
    //                    java GA_Simulation 24601

    long seed = System.currentTimeMillis(); // default
    if (args.length > 0) {
      try {
        seed = Long.parseLong(args[0]);
      } catch (NumberFormatException e) {
        System.err.println("Seed wasn't passed so using current time.");
      }
    }
    rng = new Random(seed);

    // Write your main below:

  }

}

