/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date November 22, 2012
 * @assignment Assignment 4 Part 1
 */

package algorithmsproj4;

import java.util.ArrayList;

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


public class AdjacencyMatrixGraph {
    
    //Class to contain the adjacency matrix data structure
    // size: |V|^2
    
    //variables
   static double[][] adjMatrix;
    int sizeOfMatrix;
    
    // Getters & Setters
    public static double getCell (int firstDim, int secondDim) {
        return adjMatrix[firstDim][secondDim];
    }
    public static void setCell (int firstDim, int secondDim, double numberToSet) {
        adjMatrix[firstDim][secondDim] = numberToSet;
    }
    //Method to return list of adjacent vertices
    public static ArrayList<City> returnAdj (ArrayList<City> cityList, int indexToSearch)
    {
        ArrayList<City> adj = new ArrayList(); // Arbitrary size that will be sure to contain all adj vertices.
        for( int i = 0; i < adjMatrix[indexToSearch].length; i++)
        {
            if(adjMatrix[indexToSearch][i] < Double.POSITIVE_INFINITY)
            {
                adj.add(cityList.get(i));
            }
        }
        return adj;
    }
    //Method to set the dimensions of the matrix
    public static void setSize( int size )
    {
        adjMatrix = new double[size][size];
    }
}
