/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date November 22, 2012
 * @assignment Assignment 4 Part 1
 */

package algorithmsproj4;

//   Copyright (C) 2012  Ethan Wells

import java.util.ArrayList;


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


public class AdjacencyListGraph {
    
    static ArrayList<ArrayList<Node>> cityList = new ArrayList<ArrayList<Node>>();
    
    public AdjacencyListGraph(int size)
    {
       
    }
    public static void addCity( City cityToAdd )
    {
       cityList.set(cityToAdd.index, new ArrayList<Node>());
    }
    
    public ArrayList returnAdj(int indexOfCity)
    {
        return cityList.get(indexOfCity);
    }
    
    public static void addNode( City origin, City destination, double weight)
    {
        cityList.get(origin.index).add(new Node(origin.index,weight,destination.index));
                //set(destination.index,new Node(origin.index, weight, destination.index));
       // cityList[destination.index].add(new Node(origin.index,weight,destination.index));
    }
}