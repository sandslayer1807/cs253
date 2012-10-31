/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date Oct 27, 2012
 * @assignment 0-1 Knapsack
 */

package algorithms;
import java.util.Random;

 // 
 //   Copyright (C) 2012  Ethan Wells

//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.

//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.

//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

public class DynamicKnapsack {

    public static void main (String[] args)
    {  
        //number of times to run:
        int numTimesToRun = 1;
        
        // For loop to control how many times to run each algorithm for certain
        //  values of n.
        
        for(int k = 2; k <= 17; k++)                            
        {   
            //Create the profit and weight arrays.
            int[] profitArray = new int[k];
            int[] weightArray = new int[k]; 
            
            //Create the capacity of the knapsack to be 2^n.
            int capacityOfKnapsack = (int)(Math.exp(k));
            
            //Initialize the value that the algorithms compare their runtime against
            long finalTime = Long.MAX_VALUE;
            
            //Fill the profit and weight arrays.
            generateArray(capacityOfKnapsack, weightArray);
            generateArray(capacityOfKnapsack, profitArray);
            
            //Structure to help control fluctuating runtime; runs algorithm a few
            //  times per n-value to reduce interference from other processes.
            for(int i = numTimesToRun; i > 0; i--)
            {
                //start timing
                long beginTime = System.nanoTime();
                //Start algorithm
                int greed = greedyKnapsack(weightArray,profitArray,capacityOfKnapsack);
                int recurse = recursiveKnapsack(weightArray,profitArray,0,capacityOfKnapsack);
                int iterative = iterativeKnapsack(weightArray,profitArray,capacityOfKnapsack);
                //Stop timing
                long endTime = System.nanoTime() - beginTime;
                if(endTime < finalTime && endTime != 0)
                {
                    finalTime = endTime;
                }
                //Print out results
                System.out.println(k+" "+recurse+" "+iterative+" "+greed);
            }
        //System.out.println(k+" "+finalTime);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int iterativeKnapsack( final int[] weightArray, final int[] profitArray,
    //              int capacityOfKnapsack)
    // Description: Implements the iterative version of the 0/1 knapsack problem using 
    //              dynamic programming techniques (memoization) to speed up processing.
    // Precondition: Given arrays containing weights and profits and the capacity of the 
    //              knapsack. The arrays containing weights and profits must be the same size
    //              as the number of items to select from, and the weight array must contain weights > 0.
    //              The arrays must also be valid arrays.
    // Postcondition: Returns the maximum profit possible from the item choices/knapsack
    //              capacity given, using the memoized solution from dynamic programming. This
    //              profit is less than or equal to the optimal profit from the recursive algorithm.
    // Return: An integer representing the maximum profit possible.
    ///////////////////////////////////////////////////////////////////////////
    public static int iterativeKnapsack( final int[] weightArray, final int[] profitArray, int capacityOfKnapsack)
    {
        //Asserting the precondition
        for( int j = 0; j < weightArray.length; j++)
        {
            assert( weightArray[j] > 0);
        }
        //Declaring solutions array, final sum, and keeper array.
        int [][] solArray = new int[weightArray.length+1][capacityOfKnapsack+1];
        int sumOfProfits = 0;
        int[] itemsInKnapsack = new int[weightArray.length+1];
        
        //Shifting arrays to start at 1 to match book's pseudocode.
        int[] weight = new int[weightArray.length+1];
        System.arraycopy( weightArray, 0, weight, 1, weightArray.length);
        int[] profit = new int[profitArray.length+1];
        System.arraycopy( profitArray, 0, profit, 1, profitArray.length);
        
        //Put initial zeroes in the table of solutions.
        for(int k = 0; k < weight[weightArray.length]; k++)
        {
            solArray[weightArray.length][k] = 0;
        }
        for( int m = weight[weightArray.length]; m <= capacityOfKnapsack; m++)
        {
            solArray[profitArray.length][m] = profit[profitArray.length];
        }
        
        //Starting the computation of the tuples in the table.
        for( int k = weightArray.length-1; k > 1; k--)
        {
            for( int j = 0; j < weight[k]; j++)
            {
                //Value equals the value above it.
                solArray[k][j] = solArray[k+1][j];
            }
            for( int j = weight[k]; j <= capacityOfKnapsack; j++)
            {
                //Value equals the max of the value directly above it and the value if you had taken the item
                solArray[k][j] = max( solArray[k+1][j], solArray[k+1][j-weight[k]] + profit[k]);
            }
        }
        solArray[1][capacityOfKnapsack] = solArray[2][capacityOfKnapsack];
        //If you have room to take the first item
        if( capacityOfKnapsack >= weightArray[1])
        {
            //Figure out if it is the best option
            solArray[1][capacityOfKnapsack] = max( solArray[1][capacityOfKnapsack], solArray[2][capacityOfKnapsack - weight[1]] + profit[1]);
        }
        
        //Figure out which items we picked to calculate the profit
        for( int j = 1; j < weightArray.length; j++)
        {
            //If the rightmost jth item equals the item above it (initial run)
            //else, it is the (capacity-weights of items picked)th, jth item
            if(solArray[j][capacityOfKnapsack] == solArray[j+1][capacityOfKnapsack])
            {
                //Didn't pick the item
                itemsInKnapsack[j] = 0;
            }
            else //picked the item
            {
                //subtract weight and "pick" the item
                itemsInKnapsack[j] = 1;
                capacityOfKnapsack -= weight[j];
            }
        }
        
        //Last item handling
        if( solArray[weightArray.length][capacityOfKnapsack] == 0)
        {
            //Didn't pick it.
            itemsInKnapsack[weightArray.length] = 0;
        }
        else
        {
            //Picked it.
            itemsInKnapsack[weightArray.length] = 1;
        }
        
        //Figuring out dem profits...
        for(int j = 0; j <= weightArray.length; j++)
        {
            //If item was picked
            if( itemsInKnapsack[j] == 1)
            {
                //Add profit of said item
                sumOfProfits += profit[j];
            }
        }
        //Return dem profits.
        //Asserting the postcondtion: This algorithm returns profits that are less than or
        //      equal to the exact profits reported by the recursive algorithm.
        return sumOfProfits;
    }
 
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int greedyKnapsack( int[] weightArray, int[] profitArray,
    //                  int knapsackCapacity
    // Description: Using the greedy method, the algorithm sorts items based on their
    //              profit/weight ratio, and puts the most profitable items in the bag
    //              first. 
    // Precondition: Given the weight and profit arrays of the items and the knapsack capacity.
    //              The weights must be > 0, and the weight/profit arrays must have the same
    //              number of items as the number of items we are looking to choose from.
    // Postcondition: Returns the maximum profit possible from the item choices/knapsack
    //              capacity given, using the greedy method (picking the most profitable
    //              items first). The profit returned by this algorithm must be less than or equal
    //              to the solution reported by the iterative algorithm (optimal solution).
    // Return: An integer representing the maximum profit possible from the greedy
    //          method.
    ///////////////////////////////////////////////////////////////////////////    
    public static int greedyKnapsack( final int[] weightArray, final int[] profitArray, int knapsackCapacity)
    {
        //Asserting the precondition
        for( int j = 0; j < weightArray.length; j++)
        {
            assert( weightArray[j] > 0);
        }
        // Sort the array based on weight-to-profit, and start picking up items
        int[] profitWeight = new int[weightArray.length];
        // Traceback array to figure out what items need to be put into the knapsack
        int[] traceBackArray = new int[weightArray.length];

        int finalProfit = 0;
        // For the items in the profit/weight array
        for( int i = 0; i < profitWeight.length; i++)
        {
            // Put the item's profit density in the array
            profitWeight[i] = (int)(profitArray[i] / weightArray[i]);
            traceBackArray[i] = profitWeight[i];
        }   
        //Sort the array with insertion sort; insertion sort is used due to efficiency.
        System.arraycopy(profitWeight,0,algorithms.SortingAlgorithms.insertionSort(profitWeight),0,profitWeight.length);
        for( int i = 0; i < profitWeight.length; i++ )
        {
            //If the knapsack has room for the item
            int indexOfObject = findItem(profitWeight, traceBackArray, i);
            if( weightArray[indexOfObject] <= knapsackCapacity )
            {
                //Calculate profit
                finalProfit += profitArray[indexOfObject];
                knapsackCapacity -= weightArray[indexOfObject];
            }
        }
        //Returning the maximum profit.
        //Postcondition: the profit reported by this algorithm must be less than or equal to the profit
        //      reported by the iterative algorithm (optimal solution).
        return finalProfit;       
    }

    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int recursiveKnapsack( final int[] weightArray, final int profitArray[],
    //          int index, int knapsackCapacity, int profitFound)
    // Description: Implements the recursive (optimal) method to solve the 0/1 knapsack, which 
    //          runs on a problem size one less than the one before, either taking the item or not. 
    //          This solution has O(2^n).
    // Precondition: Given arrays containing weights and profits and the capacity of the 
    //              knapsack, an index to start from, and the total profit found. The weights
    //              must be > 0, and the weight/profit arrays must be the same size as the number
    //              of items we want to choose from.
    // Postcondition: Returns the maximum profit possible from the item choices/knapsack
    //              capacity given, via the recursive (optimal) solution.
    // Return: An integer representing the maximum profit possible.
    ///////////////////////////////////////////////////////////////////////////
    public static int recursiveKnapsack( final int[] weightArray, final int profitArray[], int index, int knapsackCapacity)
    {
        //Asserting the precondition
        for( int j = 0; j < weightArray.length; j++)
        {
            assert( weightArray[j] > 0);
        }
        //Stopping case: last item in the array
        if( index == weightArray.length-1 )
        {
            //if the item fits, take the item and get the profit. if not, then don't.
            return knapsackCapacity < weightArray[index] ? 0 : profitArray[index];
        }
        //If not at the last item in the array, keep going
        if( knapsackCapacity < weightArray[index])
        {
            return recursiveKnapsack( weightArray, profitArray, index+1, knapsackCapacity);
        }
        //Asserting the postcondition: The profit returned from this algorithm is the combination of the profits calculated from the subarrays and is the optimal solution.
        return max( recursiveKnapsack(weightArray, profitArray, index+1, knapsackCapacity), (recursiveKnapsack(weightArray, profitArray, index+1, knapsackCapacity - weightArray[index]) + profitArray[index]));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int generateArray( int capacityOfKnapsack, int[] generatedArray )
    // Description: Function to help in generating random arrays. Creates values that are
    //          certain to be under the capacity of the knapsack.
    // Precondition: Passed the capacity of the knapsack, and the array to fill.
    // Postcondition: Returns the array, filled with values from 1 to the capacity
    //          of the knapsack.
    // Return: ^^^
    ///////////////////////////////////////////////////////////////////////////
    public static int[] generateArray( int capacityOfKnapsack, int[] generatedArray )
    {
        Random randNumGenerator = new Random();
        for(int i = 0; i < generatedArray.length; i++)
        {
            //Generating number from 1 to the capacity of the knapsack
            generatedArray[i] = randNumGenerator.nextInt(capacityOfKnapsack-1) + 1;
        }
        return generatedArray;
    }
  
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int findItem ( int[] ratioArray, int[] sourceArray, 
    //          int indexToLook)
    // Description: Just a helper function to find the index of the item to put into the 
    //          solution array after the greedy method sorts by profit/weight, as this
    //          messes up the order of the indexes.
    // Precondition: Given two arrays, and an index that represents the item to look for
    //          in one of them. Assumes the arrays are valid arrays, and the index is a 
    //          valid index.
    // Postcondition: Returns the index that represents the original item that the particular
    //          ratio in the greedy array represents.
    // Return: An index that represents the original item that the particular
    //          ratio in the greedy array represents.
    ///////////////////////////////////////////////////////////////////////////
    public static int findItem ( final int[] ratioArray, final int[] sourceArray, int indexToLook)
    {
        //Check to see if the index is valid. (Both arrays are same size)
        if( indexToLook >= ratioArray.length)
        {
            //ERROR!?!
            return -1;
        }
        //While there are items to search in the source array
        for( int i = 0; i < sourceArray.length; i++)
        {
            //if that item has the same contents as the ratioArray
            if(sourceArray[i] == ratioArray[indexToLook])
            {
                //return it, as this represents the item to choose for the solution array
                return i;
            }
        }
        //Can't find the item
        return 0;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int max( final int a, final int b)
    // Description: A max function to determine the max of the two items passed in
    // Precondition: a and b are valid integers.
    // Postcondition: Returns the value that is greater.
    // Return: ^^^^
    ///////////////////////////////////////////////////////////////////////////
    public static int max (final int a, final int b)
    {
        if( a > b)
        {
            return a;
        }
        else
        {
            return b;
        }
    }
        
}
