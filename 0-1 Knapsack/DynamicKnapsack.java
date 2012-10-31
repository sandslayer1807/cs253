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
        int numTimesToRun = 5;
        
        // For loop to control how many times to run each algorithm for certain
        //  values of n.
        
        for(int k = 5; k <= 30; k++)                            
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
                //greedyKnapsack(weightArray,profitArray,capacityOfKnapsack);
                recursiveKnapsack(weightArray,profitArray,0,capacityOfKnapsack,0);
                //Stop timing
                long endTime = System.nanoTime() - beginTime;
                if(endTime < finalTime && endTime != 0)
                {
                    finalTime = endTime;
                }
            }
        System.out.println("Ran algo. for "+k+" inputs with a time of "+finalTime);
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
    // Postcondition: Returns the maximum profit possible from the item choices/knapsack
    //              capacity given, using the memoized solution from dynamic programming.
    // Return: An integer representing the maximum profit possible.
    ///////////////////////////////////////////////////////////////////////////
    public static int iterativeKnapsack( final int[] weightArray, final int[] profitArray, int capacityOfKnapsack)
    {
        
        // Declaring the solutions array and the 'traceback' array, or the array of items kept in the knapsack.
        int [][] solArray = new int[weightArray.length][capacityOfKnapsack];
        int [][] keepersArray = new int[weightArray.length][capacityOfKnapsack];
        // Fill the top row with zeroes as we don't terribly care about them.
        for( int k = 0; k < capacityOfKnapsack; k++)
        {
            solArray[0][k] = 0;
        }
        //While we still have items to fit
        for( int k = 0; k < weightArray.length; k++)
        {
            //While the knapsack still has room
            for( int m = 1; m < capacityOfKnapsack; m++ )
            {
                // If the item weighs less than the capacity of the knapsack AND picking up the item is better than not picking it up
                if( weightArray[k] <= m && (profitArray[k] + solArray[k - 1][(m - weightArray[k])] > solArray[k - 1][m] ) )
                {
                    // Pick up the item
                    solArray[k][m] = profitArray[k] + solArray[k - 1][m - weightArray[k]];
                    //Add it to the "keepers" array
                    keepersArray[k][m] = 1;
                }
                // If we don't pick up the item
                else
                {
                    // Don't pick up the item and move on (without passing GO and without collecting $200)
                    solArray[k][m] = solArray[k - 1][m];
                    keepersArray[k][m] = 0;
                }
            }
        }
        //Return the highest profit, located at the bottom left of the array.
        return solArray[weightArray.length-1][capacityOfKnapsack-1];   
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
    //              items first).
    // Return: An integer representing the maximum profit possible from the greedy
    //          method.
    ///////////////////////////////////////////////////////////////////////////    
    public static int greedyKnapsack( final int[] weightArray, final int[] profitArray, int knapsackCapacity)
    {
        // Sort the array based on weight-to-profit, and start picking up items
        int[] profitWeight = new int[weightArray.length];
        // Traceback array to figure out what items need to be put into the knapsack
        int[] traceBackArray = new int[weightArray.length];
        // Solution array
        //int[] solutionArray = new int[weightArray.length];
        //int solutionArrayIndex = 0;
        int finalProfit = 0;
        // For the items in the profit/weight array
        for( int i = 0; i < profitWeight.length; i++)
        {
            // Put the item's profit density in the array
            profitWeight[i] = (int)(profitArray[i] / weightArray[i]);
            traceBackArray[i] = profitWeight[i];
        }   
        //Sort the array with insertion sort
        profitWeight = algorithms.SortingAlgorithms.insertionSort( profitWeight );
        for( int i = 0; i < profitWeight.length; i++ )
        {
            //If the knapsack has room for the item
            int indexOfObject = findItem(profitArray, traceBackArray, i);
            if( weightArray[indexOfObject] <= knapsackCapacity )
            {
                //Put the item into the knapsack
                // solutionArray[solutionArrayIndex] = indexOfObject;
                // solutionArrayIndex++;
                finalProfit += profitArray[indexOfObject];
            }
        }
        //Putting in the delimiting item of the solution array
        // solutionArray[solutionArrayIndex] = -1;
        //Returning the maximum profit.
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
    public static int recursiveKnapsack( final int[] weightArray, final int profitArray[], int index, int knapsackCapacity, int profitFound)
    {
        //Stopping case: last item in the array
        if( (weightArray.length - index) == 1 )
        {
            //if the item fits, take the item and get the profit. if not, then don't.
            return weightArray[index] <= knapsackCapacity ? profitFound+profitArray[index] : profitFound;
        }
        //If not at the last item in the array, keep going
        else
        {
            //If the item fits... (it ships) [ reference to UPS flat rate "knapsacks" ] 
            if( weightArray[index] <= knapsackCapacity)
            {
                //int to store value of not taking item
                int leaveItem = recursiveKnapsack( weightArray, profitArray, index+1, knapsackCapacity, profitFound);
                //int to store value of taking item
                int takeItem = recursiveKnapsack( weightArray, profitArray, index+1, knapsackCapacity - weightArray[index], profitFound + profitArray[index]);
                //Compare the value of taking the item and leaving the item, and pick the greatest of those
                return max(leaveItem, takeItem);
            }
            //the item doesn't fit
            else
            {
                //don't pick the item and move on
                return recursiveKnapsack ( weightArray, profitArray, index+1, knapsackCapacity, profitFound);
            }
        }
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
        if( indexToLook <= ratioArray.length)
        {
            return 0;
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
    
    public static int max (final int a, final int b)
    {
        return a >= b ? a : b;
    }
    
    public static void test ()
    {
        
    }
    
}
