package algorithms;
import java.util.Random;
/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date 9/11/12
 * @assignment Assignment 1: Insertion Sort and Assignment 2: Add Heap Sort and Merge Sort functionality
 */


 // Insertion Sort, Merge Sort, and Heap Sort implementation that includes driver to drive the program(s).
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
           passedArraySize = 700;
           passedArrayEndSize = 5000;
           passedCase = 3;
           numTimesToRun = 20;
        }    
        //For array values between min and max
        for(int k = passedArraySize; k <= passedArrayEndSize; k++)                            
        {  
            int[] arrayToSort = new int[k];         //Create an array   
            long finalTime = 1000000;
            for(int i = numTimesToRun; i > 0; i--)
            {
                arrayGen(arrayToSort, k, passedCase);
                long beginTime = System.nanoTime();
                //long tempWriteToArray=insertionSort(arrayToSort);      //And sort it.
                heapSort(arrayToSort);
                long endTime = System.nanoTime() - beginTime;
                if(endTime < finalTime)
                {
                    finalTime = endTime;
                }
                System.out.println("Number of items: "+k+" and time (ns): " +
                        finalTime);
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

    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int[] MergeSort(int[] arrayToSort)
    // Description: Takes an array of integers, and using Merge Sort, splits the array into
    //          halves and then merges them into a sorted array.
    // Precondition: An array of integers must be passed (for the method to have
    //          something to sort, although it will not break).
    // Postcondition: Returns a sorted array that is built from merging the two "half"
    //          arrays, and is therefore a permutation of the original array.
    // Return: Returns a sorted array of integers built from the two "half" arrays.
    ///////////////////////////////////////////////////////////////////////////
    public static int[] mergeSort(int[] arrayToSort)
    {
        //Asserting the precondition
        //assert(arrayToSort.length > 0)
        //Making sure that there is something to split in half.
        if(arrayToSort.length <= 1)
        {
            return arrayToSort;
        }
        else
        {
            //Split the length of the passed in array by half.
            int middleOfArray = arrayToSort.length / 2;
            //This below line is to deal with odd length arrays and making sure everything is sorted.
            int numbersLeft = arrayToSort.length - middleOfArray;
            //Making the associated "half" arrays.
            int[] arrayLeft = new int[middleOfArray];
            int[] arrayRight = new int [numbersLeft];
            //Filling the half arrays with the proper values.
            System.arraycopy(arrayToSort, 0, arrayLeft, 0, middleOfArray);
            System.arraycopy(arrayToSort, middleOfArray, arrayRight, 0, numbersLeft);
            //Recursive calls to continue splitting the half arrays until the length is <= 1.
            arrayLeft = mergeSort(arrayLeft);
            arrayRight = mergeSort(arrayRight);
            //Final call to merge to sort the half arrays into a final sorted array.
            //See the postcondition assert of Merge() to verify that the array is sorted.
            return merge(arrayLeft, arrayRight);
        }
    }
 
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static int[] Merge(int[] arrayLeft, int[] arrayRight)
    // Description: Takes two arrays of integers (the split halves of the original array) and
    //          sorts them piece by piece, returning the final product.
    // Precondition: The two arrays being passed in are sorted.
    // Postcondition: Returns a sorted array that is built from merging the two "half"
    //          arrays, and is therefore a permutation of the original array.
    // Return: Returns a sorted array of integers built from the two "half" arrays.
    ///////////////////////////////////////////////////////////////////////////    
    public static int[] merge(int[] arrayLeft, int[] arrayRight)
    {
        //Creating result array to store the merged arrays.
        int[] arrayResult = new int[ (arrayLeft.length + arrayRight.length) ];
        //Variables to store progress in their respective arrays.
        int arrayLeftToSort=0;
        int arrayRightToSort=0;
        //While there are things to sort...
        while ( (arrayLeft.length - arrayLeftToSort) > 0 || (arrayRight.length - arrayRightToSort) > 0 )
        {
            //assert invariant here
            //If there are elements still in both arrays
            if( (arrayLeft.length - arrayLeftToSort) > 0 && (arrayRight.length - arrayRightToSort) > 0 )
            {
                //If the item in the "left" array is smaller than the item in the "right" array
                if( arrayLeft[arrayLeftToSort] <= arrayRight[arrayRightToSort] )
                {
                    //Copy that result to the left array and increment the left array counter
                    arrayResult[arrayLeftToSort + arrayRightToSort] = arrayLeft[arrayLeftToSort];
                    arrayLeftToSort++;
                }
                else  //the item in the right is smaller
                {
                    arrayResult[arrayLeftToSort + arrayRightToSort] = arrayRight[arrayRightToSort];
                    arrayRightToSort++;
                }
            }
            //if the left array has items and the right array doesn't
            else if( (arrayLeft.length - arrayLeftToSort) > 0 )
            {
                //Copy everything from the left array (as they're already sorted.
                arrayResult[arrayLeftToSort + arrayRightToSort] = arrayLeft[arrayLeftToSort];
                arrayLeftToSort++;
            }
            //if the right array has items and the left doesn't
            else if( (arrayRight.length - arrayRightToSort) > 0 )
            {
                //Copy everything from the right array as they're already sorted
                arrayResult[arrayLeftToSort + arrayRightToSort] = arrayRight[arrayRightToSort];
                arrayRightToSort++;
            }
        }
        //Return the final sorted array.
        return arrayResult;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void maxHeapify(int[] arrayToSort, int index, int sizeOfHeap)
    // Description: Starting from the index passed, creates a max heap where the index is
    //          the parent.
    // Precondition: The left and right children of index are the roots of max heaps.
    // Postcondition: The index is the root of a max heap.
    // Return: Returns nothing, however modifies the passed in array via pass-by-reference.
    ///////////////////////////////////////////////////////////////////////////  
    public static void maxHeapify(int[] arrayToSort, int index, int sizeOfHeap)
    {
        //The children's indexes in the array are 2x the passed index, with the right being +1.
        int leftChild = 2*index;
        int rightChild = (2*index) + 1;
        int largestNum;
        //If the index of the left child is a valid size and if array[leftchild] is bigger than array[index].
        if( leftChild < sizeOfHeap && arrayToSort[leftChild] > arrayToSort[index] )
        {
            //Set the largest number equal to the left child
            largestNum = leftChild;
        }
        //Else, make sure the largest number is set to index (in case numbers are equal).
        else
        {
            largestNum = index;
        }
        //If the index of the right child is a valid size and if array[rightchild] is bigger than array[index].
        if( rightChild < sizeOfHeap && arrayToSort[rightChild] > arrayToSort[index] )
        {
            largestNum = rightChild;
        }
        //If largest num isn't the index
        if( largestNum != index )
        {
            //Swap the largest number with the index
            exchange(arrayToSort, index, largestNum);
            //Call maxHeapify recursively on the new index
            maxHeapify(arrayToSort, largestNum, sizeOfHeap);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void buildMaxHeap(int[] arrayToSort)
    // Description: Makes a max heap out of the passed in array rooted at one.
    // Precondition: The array passed is a valid array of size n.
    // Postcondition: Makes a max heap out of the passed in array rooted at one.
    // Return: Returns nothing, but maxHeapify changes the array.
    ///////////////////////////////////////////////////////////////////////////  
    public static void buildMaxHeap(int[] arrayToSort)
    {
        //Runs down the tree, making sure all nodes are in compliance with a max heap.
        for(int indexOfArray = (arrayToSort.length - 1); indexOfArray >= 0; indexOfArray--)
        {
            //Calling max heapify...
            maxHeapify(arrayToSort, indexOfArray, arrayToSort.length);
        }
    }
 
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void heapSort(int[] arrayToSort)
    // Description: Builds a max heap, and sorts the array using heap sort while
    //          decrementing the "size of the heap", known as the size of the array,
    //          meaning it sorts from right to left.
    // Precondition: A is an array of size n.
    // Postcondition: The passed in array is sorted and a permutation of the original array.
    // Return: None, although modifies the array through pass-by-reference.
    ///////////////////////////////////////////////////////////////////////////     
    public static void heapSort(int[] arrayToSort)
    {
        //Builds the initial max heap
        buildMaxHeap( arrayToSort );
        //Declares the size of the heap as the size of the array.
        int sizeOfHeap = arrayToSort.length;
        
        //From the end of the array to the beginning of the array
        for( int i = arrayToSort.length - 1; i >= 1; i-- )
        {
            //Exchange the biggest item to the end of the array.
            exchange(arrayToSort, 0, i);
            //Decrease the working size of the array.
            sizeOfHeap--;
            //re-make the heap
            maxHeapify( arrayToSort, 0, sizeOfHeap);
        }
    }
    
    //Simple function to aid in exchanging array values.
    public static void exchange(int[] array, int num1, int num2)
    {
        int tempVar = array[num1];
        array[num1] = array[num2];
        array[num2] = tempVar;
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
