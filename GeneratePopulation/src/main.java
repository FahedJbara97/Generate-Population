import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


    class main {
    
    private static int crossCount;	
    private static int cloneCount;	
    private static double crossRate;	
    private static double mutationRate;
    private static int maxGenerations;	
    private static int numOfItems;
    private static int numOfPopulation;
    private static int numOIterations;
    private int generationCounter = 1;
    private static int capacity;
    private double total_fitness_of_generation = 0;
    private ArrayList<String> next_gen = new ArrayList<String>();
    private ArrayList<Integer> weight = new ArrayList<Integer>();
    private ArrayList<Integer> price = new ArrayList<Integer>();
    private ArrayList<String> population = new ArrayList<String>();
    private ArrayList<Double> fitness = new ArrayList<Double>();
    private ArrayList<String> generation_solution = new ArrayList<String>();
    
    private ArrayList<Integer> statweight = new ArrayList<Integer>();
    private ArrayList<Integer> statprice = new ArrayList<Integer>();
    private static int statcapacity = 165;
    
    main(){
    	statweight.add(23);
    	statweight.add(31);
    	statweight.add(29);
    	statweight.add(44);
    	statweight.add(53);
    	statweight.add(38);
    	statweight.add(63);
    	statweight.add(85);
    	statweight.add(89);
    	statweight.add(82);
    	
    	statprice.add(92);
    	statprice.add(57);
    	statprice.add(49);
    	statprice.add(68);
    	statprice.add(60);
    	statprice.add(43);
    	statprice.add(67);
    	statprice.add(84);
    	statprice.add(87);
    	statprice.add(72);
    	
    	
    	Scanner scanner= new Scanner(System.in);
   	 numOfItems = scanner.nextInt();
//   	System.out.println(numOfItems);
   	
   	System.out.println("Enter the size of the population");
   	numOfPopulation = scanner.nextInt();
//   	System.out.println(numOfPopulation);
   	System.out.println("Enter the number of iterations");
   	numOIterations = scanner.nextInt();
//   	System.out.println(numOIterations);

   	System.out.println("Enter the Weight Capacity ");
   	capacity = scanner.nextInt();
   	
   	System.out.println("Enter the CrossOver Rate ");
   	crossRate = scanner.nextDouble();
   	
   	System.out.println("Enter the Mutation Rate ");
   	mutationRate = scanner.nextDouble();
   	
   	this.generatePopulation();
   	
   	this.generate();
   	
   	this.evaluateFitness();
 /*  	
    System.out.println("\nFitness:");
    for(int i = 0; i < this.numOfPopulation; i++) {
        System.out.println((i + 1) + " - " + this.fitness.get(i));
    }
   	*/
   	
   	double maxfit=Collections.max(fitness);
   	int index = fitness.indexOf(maxfit);
   	
 //  	System.out.println(maxfit);
 //  	System.out.println(index +"OK");

 //  	for(int i=0;i<numOfPopulation;i++)
 //     	System.out.println(fitness.get(i));
   	maxGenerations = numOfPopulation;
   	while(maxGenerations>0) {
   	/*
   		int parent1 = getTopFittest();
        int parent2 = getRandomParent(); 
        
        crossOver(parent1,parent2);
     */
   	 this.evaluateFitness();
     for(int i=0;i<numOfPopulation/2;i++){
         
         if(numOfPopulation % 2 == 1) {
             next_gen.add(generation_solution.get(generationCounter - 1));
         }
         int gene1=selectGene();
         int gene2=selectGene();

         crossOver(gene1,gene2);
   	}
     this.evaluateFitness();
     for(int i=0;i<numOfPopulation;i++){
         System.out.println("#"+(i+1)+" "+next_gen.get(i));
         population.add(i,next_gen.get(i));
     }
     System.out.println("\nFitness:");
     for(int m = 0; m < this.numOfPopulation; m++) {
        System.out.println((m + 1) + " - " + this.fitness.get(m));
     } 
     
     next_gen.clear();
     fitness.clear();
     
     System.out.println("Crossover occurred " + this.crossCount + " times");
     System.out.println("Cloning occurred " + this.cloneCount + " times");
     if(cloneCount==0) {
         System.out.println("Mutation did not occur\n");
     }
     else{
         System.out.println("Mutation did occur\n");
     }   

     maxGenerations--;
     
   	}
   
   
    }

   private void crossOver(int parent1,int parent2){
    	String child1;
    	String child2;
    	
    double randomCross = Math.random();
    if(randomCross<= crossRate) {
        crossCount = crossCount + 1;
        Random generator = new Random(); 
        int cross_point = generator.nextInt(numOfItems) + 1;

        child1 = population.get(parent1).substring(0, cross_point) + population.get(parent2).substring(cross_point);
        child2 = population.get(parent2).substring(0, cross_point) + population.get(parent1).substring(cross_point);

        next_gen.add(child1);
        next_gen.add(child2);
    }
    else {
        cloneCount = cloneCount + 1;
        next_gen.add(population.get(parent1));
        next_gen.add(population.get(parent2));
    }

    mutateGene();
   
   
   
   }
    
   private void mutateGene() {
       
       double randMutation = Math.random();
       if(randMutation <= mutationRate) {

           String mut_gene;
           String new_mut_gene;
           Random generator = new Random();
           int mut_point = 0;
           double which_gene = Math.random() * 1;

	    // Mutate gene
           if(which_gene <= 0.5) {
               mut_gene = next_gen.get(next_gen.size() - 1);
               mut_point = generator.nextInt(numOfItems);
               if(mut_gene.substring(mut_point, mut_point + 1).equals("1")) {
                   new_mut_gene = mut_gene.substring(0, mut_point) + "0" + mut_gene.substring(mut_point+1);
                   next_gen.set(next_gen.size() - 1, new_mut_gene);
               }
               if(mut_gene.substring(mut_point, mut_point + 1).equals("0")) {
                   new_mut_gene = mut_gene.substring(0, mut_point) + "1" + mut_gene.substring(mut_point+1);
                   next_gen.set(next_gen.size() - 1, new_mut_gene);
               }
           }
           if(which_gene >0.5) {
               mut_gene = next_gen.get(next_gen.size() - 2);
               mut_point = generator.nextInt(numOfItems);
               if(mut_gene.substring(mut_point, mut_point + 1).equals("1")) {
                   new_mut_gene = mut_gene.substring(0, mut_point) + "0" + mut_gene.substring(mut_point+1);
                   next_gen.set(next_gen.size() - 1, new_mut_gene);
               }
               if(mut_gene.substring(mut_point, mut_point + 1).equals("0")) {
                   new_mut_gene = mut_gene.substring(0, mut_point) + "1" + mut_gene.substring(mut_point+1);
                   next_gen.set(next_gen.size() - 2, new_mut_gene);
               }
           }           
       }
   }
    
    int getTopFittest() {
    	double maxfit=Collections.max(fitness);
       	int index = fitness.indexOf(maxfit);
       	return index;
    }
    
    int getRandomParent() {
    	Random rand = new Random();
		int n = rand.nextInt(numOfPopulation);
		return n;
    }
    
    void evaluateFitness(){
        double topFitestGene = 0;
        int topFitestIndex=0;
        total_fitness_of_generation = 0;
        for(int j = 0; j < numOfPopulation; j++) {
            int totalWeight = 0;
            int totalPrice = 0;
            double fitnessValue = 0;
            double diff = 0;
            char n = '1';

            for(int i = 0; i < numOfItems; i++) {
                n = population.get(j).charAt(i);
                //chromosome value 1
                if(n == '1') {
                //  System.out.println(n);
                	totalWeight = totalWeight + weight.get(i);
                    totalPrice = totalPrice + price.get(i);
                }
            }

            diff = capacity - totalWeight;
            
            if(diff >= 0) 
                fitnessValue = totalPrice;
            else
            	fitnessValue = 0;
            
            fitness.add(fitnessValue);
            if(fitnessValue>topFitestGene){
                topFitestGene=fitnessValue;
                topFitestIndex=j;
            }
            total_fitness_of_generation = total_fitness_of_generation + fitnessValue;
            
            }
            System.out.println("Total Generation Fitness "+total_fitness_of_generation);
            System.out.print("The fittest chromosome of this generation is "+population.get(topFitestIndex));
            System.out.println(" And its fitness is "+fitness.get(topFitestIndex));
            generation_solution.add(population.get(topFitestIndex));
           
    }
    
    private int selectGene() {

        double rand = Math.random() * total_fitness_of_generation;
        for(int i = 0; i < numOfPopulation; i++) {
            if(rand <= fitness.get(i)) {
                return i;
            }
            rand = rand - fitness.get(i);
        }
	return 0;
    }
    
    
    void generate() {
    	
    	for(int h=0;h<numOfItems;h++) {
    		Random rand = new Random();
            int n = rand.nextDouble();
    		weight.add(n);
    	}
    	for(int h=0;h<numOfItems;h++) {
    		Random rand = new Random();
    		int n = rand.nextInt((int) (numOfItems * 0.8));
    		n += 1;
    		price.add(n);
    	}
    }
    
    
    void generatePopulation(){
        System.out.println("Population:");
        int i = 0;
        while(i < numOfPopulation) {
        	char c;
        	String gene = "";

            for(int j = 0; j < numOfItems; j++) {          
                if(Math.random() > 0.5) 
                    gene+="1";
                
                else
                    gene+="0";
                
            }
            System.out.println("#"+(i+1)+" "+gene);
            population.add(gene);      
        i++;
        }
    }

    
    public static void main(String[] args) {

	System.out.println("Enter number of items");
	main k=new main();
	
	}
	
}
