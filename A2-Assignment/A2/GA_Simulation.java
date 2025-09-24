import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GA_Simulation {

  // Use the instructions to identify the class variables, constructors, and methods you need
  /**
   * seed for random number generator
   */
  public static Random rng; 
  /**
   * number of individuals in each generation
   */
  private int n;
  /**
   * number of winners allowed to reproduce
   */
  private int k;
  /**
   * number of rounds of evolution to run
   */
  private int r;
  /**
   * initial chromosome size
   */
  private int c_0;
  /**
   * maximum chromosome size
   */
  private int c_max;
  /**
   * mutation rate per gene
   */
  private float m;
  /**
   * number of states or letters per gene
   */
  private int g; 



  /**
   * constructor, initializes the parameters
   * @param n number of individuals in each generation
   * @param k number of winners per generation
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
   * @param roundNumber which round of evolution we're on
   * @param pop current population
   */
  private void ga_describe(int roundNumber, ArrayList<Individual> pop) {
    // grabs score of population's individual w/best fitness
    int bestFitness = pop.get(0).indiv_getFitness();
    // grabs score of population's kth fitness
    int kthFitness = pop.get(k-1).indiv_getFitness();
    // grabs score of population's individual w/least fitness
    int leastFitness = pop.get(pop.size() - 1).indiv_getFitness();
    // grabs chromosome of population's best individual
    Individual best = pop.get(0);

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
        return (int)Math.signum(c2.indiv_getFitness()-c1.indiv_getFitness());
      }
    };
    pop.sort(ranker);
  }
  

  /**
   * initializes the first generation of n random individuals. 
   * @return ArrayList of Individuals which represent the initial population
   */
  public ArrayList<Individual> ga_init(){
    ArrayList<Individual> population = new ArrayList<>(n);
    for (int i=0; i < n; i++){
      population.add(new Individual(c_0, g, rng));
    }
    return population;
  }

  
  /**
   * Selects each generation's winners
   * @param currentPop
   * @return nextGeneration , arrayList of two parents' offspring
   */
  public ArrayList<Individual> ga_evolve(ArrayList<Individual> currentPop) {
    // next generation arrayList
    ArrayList<Individual> nextGeneration = new ArrayList<>(n);
    // top k winners arrayList
    ArrayList<Individual> winners = new ArrayList<>(k);

    // selects each gen's top k winners
    for (int i = 0; i < k; i++) {
      winners.add(currentPop.get(i));
    }

    // creates next generation from randomly selected parents
    for (int i=0; i < n; i++){
      int parent1Index = rng.nextInt(winners.size());
      int parent2Index = rng.nextInt(winners.size());
      Individual parent1 = winners.get(parent1Index);
      Individual parent2 = winners.get(parent2Index);
      
      // create offspring 
      Individual offspring = new Individual(parent1, parent2, c_max, m, 5, rng);
      nextGeneration.add(offspring);
  }

  return nextGeneration;

  }


  /**
   * Will run entire experiment
   * Calls methods in order
   * @return nothing
   */
  public void ga_run(){
    // initial population functions
    ArrayList<Individual> population = ga_init();
    rankPopulation(population);
    ga_describe(1, population);

    // new generation functions
    for (int gen = 2; gen <=r ; gen ++){
      population = ga_evolve(population);
      rankPopulation(population);
      ga_describe(gen, population);
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
    GA_Simulation newSimulation = new GA_Simulation(100, 80, 100, 8, 20, seed, 5);
    newSimulation.ga_run();

  }

}

