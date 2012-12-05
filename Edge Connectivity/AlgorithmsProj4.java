/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date November 22, 2012
 * @assignment Assignment 4 Part 1
 */

package algorithmsproj4;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class AlgorithmsProj4 {

    public static void main(String[] args) {
        
        // Now to run it multiple times...
        int numTimesToRun = 1;
        ArrayList<City> citiesOfUs = null;
        AdjacencyListGraph adjGraph = null;
        long startTime;
      //  for( int i = 0; i <= 200; i++)
      //  {
       //     long bestTime = Long.MAX_VALUE;
       //     for( int j = 0; j < numTimesToRun; j++)
       //     {
                //Initializing the local city array and the adjacency list.
                citiesOfUs = new ArrayList();
                adjGraph = new AdjacencyListGraph(1000);
                //Initialize the cities and edges
                initialize(citiesOfUs, adjGraph);
                //Start timing
                //startTime = System.nanoTime();
                //Call Prim's algorithm on the adjacency list
              //  primMST(citiesOfUs, 126, adjGraph); //Index of Washington, DC
                edmondsKarp(citiesOfUs,adjGraph,citiesOfUs.get(120),citiesOfUs.get(700));
                //End time
               // long endTime = System.nanoTime() - startTime;
                //If this endtime was better than others seen before, take it.
                //if( endTime < bestTime )
               // {
              //      bestTime = endTime;
              //  }                
         //   }
   // }
       // System.out.println("The cost: "+getCost(citiesOfUs, adjGraph));
      //  outputToKML(citiesOfUs);
    }
    
    public static void initialize(ArrayList<City> cityList, AdjacencyListGraph adjGraph)
    {
        // Opening up file...
        Scanner inFile = null;
        try {
            inFile = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ethan\\Documents\\NetBeansProjects\\AlgorithmsProj4\\src\\algorithmsproj4\\cities.txt")));
            inFile.useDelimiter("</TD>|\\n");
        }
        catch (IOException e) {
            Logger.getLogger(AlgorithmsProj4.class.getName()).log(Level.SEVERE, "File failed to open!", e);
        }
        
        //Initializing Stuff
        int indexOfCity = 0;
        //Reading in the file
        while( inFile.hasNext() )
        {
            String name = inFile.next();
            int latDegrees = Integer.parseInt(inFile.next());
            int latMinutes = Integer.parseInt(inFile.next());
            int longDegrees = Integer.parseInt(inFile.next());
            int longMinutes = Integer.parseInt(inFile.next());
            String whiteSpace = inFile.next(); //To grab the newline off the end
            City c = new City(toDecDeg(latDegrees,latMinutes), toDecDeg(longDegrees,longMinutes),name, indexOfCity);
            //Add the city to the local city array and also the adjacency list.
            cityList.add(c);
            adjGraph.addCity(c);
            indexOfCity++;
        }        
        // Start initializing all the edges
        for( int i = 0; i < cityList.size(); i++)
        {
            for(int j = 0; j < cityList.size(); j++)
            {
                //If the distance between the two points is within 75 leagues and they're
                //  not the same point, add the edge to the list.
                if(getDistance(cityList.get(i), cityList.get(j)) <= 15.0 && i != j)
                {
                   adjGraph.addNode(cityList.get(i), cityList.get(j), getDistance(cityList.get(i), cityList.get(j)),0);
                }
            }
                    
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Method: public static void primMST (ArrayList<City> cityList, int indexOfRootCity,
    //      AdjacencyListGraph adjGraph )
    // Description: Implements Prim's algorithm for creating a minimal spanning tree (MST) from
    //      the points given to it by the graph cityList.
    // Precondition: There is a graph of vertices and edges, with the starting vertex in V.
    // Postcondition: There is an MST stored in pi(v) for all v in V.
    // Return: None.
    ///////////////////////////////////////////////////////////////////////////
    public static void primMST(ArrayList<City> cityList, int indexOfRootCity, AdjacencyListGraph adjGraph)
    {
        cityList.get(indexOfRootCity).key = 0.0;
        cityList.get(indexOfRootCity).parent = null;
        ArrayList<City> Q = new ArrayList<City>();
        //Add the vertices to the queue.
        Q.addAll(cityList);
        //Sort based on key value.
        Collections.sort(Q);
        //While there are vertices to process
        while (!Q.isEmpty())
        {
            //Pop the one from the top
            City u = Q.remove(0);
            //For each adjacent vertex
            for ( Node v : adjGraph.returnAdj(u) )
            {
                //If the queue contains v and if the weight(u,v) < v's key
                if( Q.contains(v.dest) && adjGraph.isConnected(u, v.dest) < v.dest.key )
                {
                    //Update the parent of v to be u, and update the key.
                    cityList.get(v.dest.index).parent = cityList.get(u.index);
                    cityList.get(v.dest.index).key = adjGraph.isConnected(u, v.dest);
                }
                //Sort again after all the changes have been enacted.
                Collections.sort(Q);
                //Making sure that an initialized city that has an edge also has a parent
                if( Q.contains(v.dest) && v.dest.key < Double.POSITIVE_INFINITY)
                {
                    assert( v.dest.parent != null );
                }
            }

        }
    }
    
    public static ArrayList dijkstraSSSP(ArrayList<City> cityList, AdjacencyListGraph adjGraph, City source, City destination)
    {
        cityList.get(source.index).key = 0.0;
        cityList.get(source.index).parent = null;
        ArrayList<City> Q = new ArrayList<City>();
        //Add the vertices to the queue.
        Q.addAll(cityList);
        //Sort based on key value.
        Collections.sort(Q);
        //While there are vertices to process
        while (!Q.isEmpty())
        {
            //Pop the one from the top
            City u = Q.remove(0);
            //For each adjacent vertex
            for ( Node v : adjGraph.returnAdj(u) )
            {
                //if a path to v is less efficient than some other path to v
                if( v.dest.key > u.key + adjGraph.isConnected(u, v.dest) )
                {
                    //Update the parent of v to be u, and update the key.
                    cityList.get(v.dest.index).parent = cityList.get(u.index);
                    cityList.get(v.dest.index).key = u.key + adjGraph.isConnected(u, v.dest);
                }
                //Sort again after all the changes have been enacted.
                Collections.sort(Q);
            }
        }
        
        ArrayList<City> pathToDest = new ArrayList<City>();
        City tmp = destination;
        while (tmp.parent != null)
        {
            pathToDest.add(0, tmp.parent);
            tmp = tmp.parent;
        }
        pathToDest.add(0, tmp);
        return pathToDest;
    }
    
    public static void edmondsKarp(ArrayList<City> cityList, AdjacencyListGraph adjGraph, City origin, City dest)
    {
        ArrayList<City> pathToDest = dijkstraSSSP(cityList, buildResidual(cityList, adjGraph), origin, dest);
        while( pathToDest != null)
        {
            for( int i = 1; i < pathToDest.size(); i++)
            {
                if(adjGraph.isConnected(pathToDest.get(i-1), pathToDest.get(i)) > 0)
                {
                    adjGraph.setEdgeFlow(pathToDest.get(i-1),pathToDest.get(i), 0);
                }
                else
                {
                    adjGraph.setEdgeFlow(pathToDest.get(i), pathToDest.get(i-1), 0);
                }
            }
            pathToDest = dijkstraSSSP(cityList, buildResidual(cityList, adjGraph), origin, dest);
        }
    }
    
    public static AdjacencyListGraph buildResidual(ArrayList<City> cityList, AdjacencyListGraph adjGraph)
    {
        AdjacencyListGraph residualGraph = new AdjacencyListGraph(1000);
        for( City source : cityList)
        {
            residualGraph.addCity(source);
            for( Node dest : adjGraph.returnAdj(source))
            {
                if(adjGraph.getEdgeFlow(source, dest.dest) > 0)
                {
                    residualGraph.addNode(source, dest.dest, 1, adjGraph.getEdgeFlow(source, dest.dest));
                }
            }
        }
        return residualGraph;
    }
    
    
    //Function to get the cost of the edges in the tree, for the final answer to the project.
    public static double getCost( ArrayList<City> cityList, AdjacencyListGraph adjGraph)
    {
        double costInLeagues=0.0;
        for(City city : cityList)
        {
            //If there is a defined edge, add it.
            if( city.key != Double.POSITIVE_INFINITY)
            {
                costInLeagues += city.key;
            }
        }
        return costInLeagues;
    }
    
    public static double getMaxFlow(AdjacencyListGraph adjGraph)
    {
        double maxFlow = 0;
        
        for(int i = 0; i < adjGraph.cityList.length; i++)
        {
            for(Node n : adjGraph.cityList[i])
            {
                //If the edge is being used
                if(n.capacity - n.flow == 0)
                {
                    maxFlow+=1;
                }
            }
        }
        return maxFlow;
    }
            
    public static double getDistance( City orig, City dest )
    {
        // Uses haversine formula for great-circle distance.
        double radiusOfEarth = 1147.96616; // Distance is in leagues which are equal to 5.55600 km
        double diffLatitude = toRad(dest.getLat() - orig.getLat());
        double diffLongitude = toRad(dest.getLong() - orig.getLong());
        double origLat = toRad(orig.getLat());
        double destLat = toRad(dest.getLat());
        double a = Math.sin(diffLatitude/2.0) * Math.sin(diffLatitude/2.0) + Math.sin(diffLongitude/2.0) * Math.sin(diffLongitude/2.0) * Math.cos(origLat) * Math.cos(destLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return radiusOfEarth * c;
    }
    //Method to convert decimals to radians.
    public static double toRad( double degrees)
    {
        return (degrees)*(Math.PI/180.0);
    }
    //Method to convert from DMS to decimal degrees
    public static double toDecDeg( int degrees, int minutes)
    {
        return (degrees+(double)(minutes)/60.0);
    }
    //Method that outputs the vertices/edges in a way that the Google Earth API can pick it up.
    public static void outputToKML( ArrayList<City> citiesOfUs)
    {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("C:\\Users\\Ethan\\test.kml"));
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmsProj4.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\"> <Document>");
        out.println("<Style id=\"20kleaguesUnderTheSea\">");
        out.println("<IconStyle>");
        out.println("<color>ffffffff</color>");
        out.println("<colorMode>random</colorMode>");
        out.println("<scale>1.05</scale>");
        out.println("<Icon><href>http://www.freegreatdesign.com/files/images/7/3193-assorted-cool-icon-png-2.jpg</href></Icon>");
        out.println("</IconStyle></Style>");
        for( City c : citiesOfUs )
        {
            out.println("<Placemark>");
            out.println("<name>"+c.name+"</name>");
            out.println("<styleUrl>#20kleaguesUnderTheSea</styleUrl>");
            out.println("<description></description>");
            out.println("<Point>");
            out.println("<coordinates>-"+c.longitude+","+c.latitude+",0</coordinates>");
            out.println("</Point>");
            out.println("</Placemark>");
            outputCityPaths(citiesOfUs, out, c);
        }
        out.println("</Document> </kml>");
        out.close();
    }

    public static void outputCityPaths(ArrayList<City> citiesOfUs, PrintWriter out, City c)
    {
        if(c.parent != null)
        {
               City parentOfI = citiesOfUs.get(c.index).parent;
                out.println("<Placemark>");
                out.println("<name>"+parentOfI.name+" and "+c.name+"</name>");
                out.println("<LineString>");
                out.println("<extrude>1</extrude>");
                out.println("<coordinates>");
                out.println("-"+citiesOfUs.get(c.index).longitude+","+citiesOfUs.get(c.index).latitude+",0 -"+parentOfI.longitude+","+parentOfI.latitude+",0");
                out.println("</coordinates>");
                out.println("</LineString>");
                out.println("</Placemark>");
        }

    }
}