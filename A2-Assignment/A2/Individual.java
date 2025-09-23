import java.util.ArrayList;
import java.util.Random;

public class Individual {

    // Attributes
    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList<Character> chromosome;
    

    /**
     * stores the size of the individual's ArrayList, which represents its genetic data
     */
    int size;


    /**
     * Inital constructor to generate initial population members
     * @param c_0 The initial chromosome size
     * @param num_letters The number of letters available to choose from
     * @param rng Seed for random number generator
     * @return nothing
     */
    public Individual(int c_0, int num_letters, Random rng) {
        // initializes chromosome ArrayList to initial size of c_0
        this.chromosome = new ArrayList<>(c_0);
        // adds random characters, meant to represent "genes", to chromosome ArrayList
        for (int i = 0; i < c_0; i++) {
            chromosome.add(randomLetter(num_letters, rng));
        }
        // gets size of chromosome ArrayList and stores in size attribute
        this.size = chromosome.size();
    }

    /**
     * Second constructor to create parents and offspring chromosomes
     * @param parent1 The first parent chromosome
     * @param parent2 The second parent chromosome
     * @param c_max The maximum chromosome size
     * @param m The chances per round of mutation in each gene
     * @return nothing
     */
    public Individual(Individual parent1, Individual parent2, int c_max, float m, int num_letters, Random rng) {

        int prefixLength = rng.nextInt(parent1.chromosome.size()) + 1;
        int suffixLength = rng.nextInt(parent2.chromosome.size()) + 1;

        ArrayList<Character> newChromosome =  new ArrayList<>();

        //Add parent's 1 prefix to nex chromosome
        for(int i = 0; i < prefixLength; i++){
            newChromosome.add(parent1.chromosome.get(i));
        }

        //Add parent's 2 suffix to newChromosome
        for(int i = parent2.chromosome.size() - suffixLength; i < parent2.chromosome.size(); i++){
            newChromosome.add(parent2.chromosome.get(i));
        }

        //Check if the new chromosome is longer than the max allowed, if it's true take one letter from the end
        while(newChromosome.size() > c_max){
            newChromosome.remove(newChromosome.size() - 1);
        }
        
        //if the doesMutate method returns true, then replace the gene with a new random letter 
        for(int i = 0; i < newChromosome.size(); i++){
            if (doesMutate(num_letters, rng)){
                newChromosome.set(i, randomLetter(num_letters, rng));
            }
        }



    }

    /**
     * Provided method to choose a letter at random, in the range from A to the number of states indicated
     * @param num_letters The number of letters available to choose from (number of possible states)
     * @param rng The random number generator being used for the current run
     * @return The letter as a Character
     */
    private Character randomLetter(int num_letters, Random rng) {
        return Character.valueOf((char)(65 + rng.nextInt(num_letters)));
    }

    /**
     * Provided method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @param rng The random number generator being used for the current run
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
     */
    private Boolean doesMutate(float m, Random rng) {
        float randomNum = rng.nextInt(100) / 100f;
        return randomNum < m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
            builder.append(ch);
        }
        return builder.toString();
    }


    public int getFitness(){
        int fitness = 0;
        int n = chromosome.size();

        for (int i = 0; i < n/2; i++){
            char left = chromosome.get(i);
            char right = chromosome.get(n - 1 - i);
            if (left == right){
                fitness += 1;
            }
            else{
                fitness -= 1;
            }
            }
        if (n % 2 == 1){
            fitness += 1;
        }

        for (int i = 0; i < n-1; i++){
            char current = chromosome.get(i);
            char next = chromosome.get(i + 1);
            
            if (current == next){
                fitness -= 1;
            }

        }
        return fitness;
    
    }


    public static void main(String[] args) {
        // This code will set a random seed when you're testing Individual (i.e., running it without GA_Simulation)
        Random rng = new Random(System.currentTimeMillis());

        // You can pass rng, as defined above, to your constructors.
        Individual i = new Individual(8, 4, rng);
        i.toString();

    }


}