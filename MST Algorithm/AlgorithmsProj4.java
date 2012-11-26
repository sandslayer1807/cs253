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
        ArrayList<City> citiesOfUs = new ArrayList();
        initialize(citiesOfUs);
        // Will be using Adjacency Lists as the graph will be sparse, which plays into the advantages of the Adjacency
        //  list not needing near as much space.
    }
    
    public static void initialize(ArrayList<City> cityList)
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
        while( inFile.hasNext() )
        {
            String name = inFile.next();
            int latDegrees = Integer.parseInt(inFile.next());
            int latMinutes = Integer.parseInt(inFile.next());
            int longDegrees = Integer.parseInt(inFile.next());
            int longMinutes = Integer.parseInt(inFile.next());
            String whiteSpace = inFile.next();
            
            cityList.add(new City(toDecDeg(latDegrees,latMinutes), toDecDeg(longDegrees,longMinutes),name, indexOfCity));
            System.out.println("Name: "+name+" latDeg: "+latDegrees+" latMin: "+latMinutes+" longDeg: "+longDegrees+" longmins: "+longMinutes);
           // if( name.equals("Torrington"))
       //     {
        //        String temp = inFile.next();
        //        break;
        //    }
            // Add city to the adjacency list
          //  City test = new City(toDecDeg(latDegrees,latMinutes), toDecDeg(longDegrees,longMinutes),name, indexOfCity);
         //   AdjacencyListGraph.addCity(test);
            indexOfCity++;
        }
        
        // Start initializing all the edges
        for( int i = 0; i < cityList.size(); i++)
        {
            for(int j = 0; j < cityList.size(); j++)
            {
                if(i == j)
                {
                    continue;
                }
                else
                {
                    if(getDistance(cityList.get(i), cityList.get(j)) <= 30.0)
                    {
                        AdjacencyListGraph.addNode( cityList.get(i), cityList.get(j), getDistance(cityList.get(i), cityList.get(j)) );
                    }
                }
                    
            }
        }
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
    
    public static double toRad( double degrees)
    {
        return (degrees)*(Math.PI/180.0);
    }
    public static double toDecDeg( int degrees, int minutes)
    {
        return (degrees+(double)(minutes)/60.0);
    }
}
