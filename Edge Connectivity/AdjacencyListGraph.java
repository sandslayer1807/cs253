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
import java.util.LinkedList;
import java.util.List;


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

    List<Node>[] cityList;
    int currentIndex = 0;
    //Constructor
    public AdjacencyListGraph( int size )
    {
        cityList = new List[size];
    }
    //Add a city/vertex to the list.
    public void addCity( City cityToAdd )
    {
       cityList[cityToAdd.index] = new LinkedList<Node>();
       currentIndex++;
    }
    //Add a node (adjacent vertex) to the current vertex.
    public void addNode( City origin, City destination, double weight, double flow)
    {
        cityList[origin.index].add(new Node(destination,weight,origin.index, flow));
    }
    //Returns an arraylist of nodes that represent the adjacent vertices to the given
    //  vertex.
    public ArrayList<Node> returnAdj( City origin)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        for( Node n : cityList[origin.index])
        {
            nodeList.add(n);
        }
        return nodeList;
    }
    //Method that checks if the nodes are connected, then returns the edge weight.
    public double isConnected( City origin, City destination)
    {
        for( Node n : cityList[origin.index])
        {
            if( n.dest == destination)
            {
                return n.weight;
            }
        }
        return -1.0;
    }
    //Get available edge flow between two cities
    public double getEdgeFlow(City origin, City destination)
    {
        for( Node n : cityList[origin.index])
        {
            if(n.dest == destination)
            {
                System.out.println(n.capacity-n.flow);
                return n.capacity-n.flow;
                
            }
        }
        return -1.0;
    }
    //Set available edge flow between two cities
    public void setEdgeFlow(City origin, City destination, double flow)
    {
        for( Node n : cityList[origin.index])
        {
            if(n.dest == destination)
            {
                n.flow = flow;
            }
        }
    }
    public void removeEdge(City origin, City destination)
    {
        for( Node n : cityList[origin.index])
        {
            if(n.dest == destination)
            {
                cityList[origin.index].remove(n);
            }
        }
    }
    public void addAllNodes(AdjacencyListGraph residualGraph, AdjacencyListGraph adjGraph)
    {
        for(int i = 0; i < adjGraph.currentIndex; i++)
        {
            for( Node e : adjGraph.cityList[i])
            {
                residualGraph.cityList[i].add(e);
            }
        }
    }
    public void dumpEdges(ArrayList<City> cityArray)
    {
        for(int i = 0; i < cityArray.size(); i++)
        {
            for( Node n : cityList[i])
            {
                System.out.println("Source: "+cityArray.get(i).name+" Dest: "+n.dest.name+" edge flow: "+
                        getEdgeFlow(cityArray.get(i),n.dest));
            }
        }
    }
}