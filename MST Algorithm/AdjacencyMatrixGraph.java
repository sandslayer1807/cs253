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
    double[][] adjMatrix;
    int sizeOfMatrix;
    
    // Constructor
    public AdjacencyMatrixGraph (int size) {
        adjMatrix = new double[size][size];
        sizeOfMatrix = size;
    }
    // Getters & Setters
    public double getCell (int firstDim, int secondDim) {
        return adjMatrix[firstDim][secondDim];
    }
    public void setCell (int firstDim, int secondDim, double numberToSet) {
        adjMatrix[firstDim][secondDim] = numberToSet;
    }
    //Method to return list of adjacent vertices
    public ArrayList returnAdj (int indexToSearch)
    {
        ArrayList<Node> adj = new ArrayList(); // Arbitrary size that will be sure to contain all adj vertices.
        for( int i = 0; i < adjMatrix[indexToSearch].length; i++)
        {
            if(adjMatrix[indexToSearch][i] > 0)
            {
                Node n = new Node(i, adjMatrix[indexToSearch][i],indexToSearch);
                adj.add(n);
            }
        }
        return adj;
    }
}
