package algorithms;
import java.util.Random;
/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date 9/11/12
 * @assignment Assignment 1: Insertion Sort
 */


 // Insertion Sort implementation that includes driver to drive the program.
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



public class SortClass {

    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void main(String[] args)
    // Description: Driver to test and run the sorting method.
    // Precondition: Is passed 4 numbers on the command line. The first number 
    //      corresponds to the size of the array, the second to the end-size of 
    //      the array(what it should stop at), the third number to the case (1 being the
    //      worst case, 2 being average, 3 being best), and the fourth to the number
    //      of times to run said case
    // Postcondition: None, although it will run the sorting algorithm, and for the 
    //      main() to exit successfully, the sorting algorithm will have to run 
    //      successfully.
    // Return: None.
    ///////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        
        int passedArraySize; 
        int passedArrayEndSize;
        int passedCase;
        int numTimesToRun;
        if(args.length > 0)               //If there are command line arguments present                                                      
        {
            //assign values on command line to these variables
            passedArraySize = Integer.parseInt(args[0]);                                       
            passedCase = Integer.parseInt(args[1]);
            passedArrayEndSize = Integer.parseInt(args[2]);
            numTimesToRun = Integer.parseInt(args[3]);            
        }
        else                                                                                   
        {
           //else take these default values
           passedArraySize = 7;
           passedArrayEndSize = 3000;
           passedCase = 3;
           numTimesToRun = 10;
        }    
        //For array values between min and max
        for(int k = passedArraySize; k <= passedArrayEndSize; k++)                            
        {  
            int[] arrayToSort = new int[k];         //Create an array                                            
            for(int i = numTimesToRun; i > 0; i--)
            {
                arrayGen(arrayToSort, k, passedCase);
                long tempWriteToArray=insertionSort(arrayToSort);      //And sort it.                         
                System.out.println("Number of items: "+k+" and time (ns): "+
                        tempWriteToArray);
            }
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void averageArrayGen(int[] array1, int array_size, int 
    //            caseToRun)
    // Description: Makes a random array of size array_size and fills it with 
    //                  random numbers, courtesy of java.util.Random. This will
    //                  be for testing the "average" case scenario.
    // Precondition: The integer array is a valid array and the array's size 
    //          is equal to array_size, caseToRun is between 1 and 3.
    // Postcondition: Edits the passed in array and fills in the appropriate 
    //          numbers based on the case.
    // Return: Nothing, but edits a passed-by-reference array.
    ///////////////////////////////////////////////////////////////////////////
    public static void arrayGen(int[] array1, int array_size, int caseToRun)
    {
        Random randNumGenerator = new Random();
       // Checking to see if array_size is a valid size for an array.
        if( array_size <= 0 )                                                                   
        {                    
            System.out.println("This number is invalid!");
            System.exit(1);        //Exits if not                                                             
        }
        else if( array_size > 2000000)   //Checking if number is too large.                                                         
        {                                                                                       
            System.out.println("This number is far too large!");
            System.exit(1);    //Exits if so.                                                                
        }
        //Making number for best and worst case big enough so that for worst case number 
        //    doesn't get negative.
        int numberForCase = 20000 + randNumGenerator.nextInt(10000);                            
        //Switch case based on input on command line for the mode(best, worst, 
        //  average case)
        switch (caseToRun)  
        {
            case 1:    //Worst Case                                                                           
                for(int i = 0; i < array_size; i++)
                {
                    //Making each next number one smaller than the one previous, making a 
                    //   reverse sorted array.
                    array1[i] = numberForCase - i;                                              
                }
                break;
            case 2:     //Average Case                                                                        
                for(int j = 0; j < array_size; j++)
                {
                    //Randomly generating each number.
                    array1[j] = randNumGenerator.nextInt(10000);                                
                }
                break;
            case 3:   //Best Case                                                                          
                for(int k = 0; k < array_size; k++)
                {
                    //Making each next number one more than the one previous, making a 
                    //  sorted array.
                    array1[k] = numberForCase + k;                                              
                }
                break;
        }
        
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void insertionSort(int[] arrayToSort)
    // Description: Takes an array of integers and uses an implementation of 
    //                  insertion sort to sort the array.
    // Precondition: An array of integers must be passed (for the method to have
    //                  something to sort.
    // Postcondition: Returns nothing, but the array passed in is sorted, by
    //                  the magic of pass-by-reference.
    // Return: Returns nothing, but the array passed in is passed-by-reference.
    ///////////////////////////////////////////////////////////////////////////
    public static long insertionSort(int[] arrayToSort)
    {
        //Asserting the precondition.
        assert(arrayToSort.length > 0);                                                         
        long beginTime = System.nanoTime();
        //From the second element in the array to length+1 (keep in mind, 
          //the array.length variable is for arrays starting at one.
        for(int j = 1; j < arrayToSort.length; j++)                                             
        {                                                                                           
            for(int n=0; n<j;n++)    //Asserting the invariant                                                           
            {
                 //Array is sorted to j-1 position
                assert(arrayToSort[n-1]<arrayToSort[n]);                                       
            }
            //Make the "smallest" item the current item.
            int smallestInArray = arrayToSort[j]; 
            // "                "
            int indexOfSmallest = j;      
            //While the index is not at zero AND the item that's to the left of the 
            // current item is bigger. Note that this works because it sorts from left 
            // to right.
            while (indexOfSmallest > 0 && arrayToSort[indexOfSmallest-1] > smallestInArray)     
            {  
                //Asserting the invariant
                for(int m=indexOfSmallest; m<=j; m++)                                           
                {
                    //The hole value is smaller or equal to everything between it and j.
                    assert( arrayToSort[m]>=smallestInArray);                                     
                }
                 //Swap the two items, moving the smaller number down the array.
                arrayToSort[indexOfSmallest] = arrayToSort[indexOfSmallest-1];                 
                indexOfSmallest--;   //Decrement, and move to the left.                                                           
            }
            //Found the spot where it goes, putting number in the spot.
            arrayToSort[indexOfSmallest] = smallestInArray;                                     
        }
        long endTime = System.nanoTime() - beginTime;
        for(int i = 0; i < arrayToSort.length; i++)
        {
             //Verifying the postcondition
            assert(arrayToSort[i] <= arrayToSort[i+1]);                                        
        }
        return endTime;
        
    }
    
    public static int[] test()
    {
        //Testing for negative numbers
        int[] array = new int[10];
        
        for(int i = 0; i < 10; i++)
        {
            array[i] = (-1 - i);
        }
        
        
        return array;
    }
    
}
