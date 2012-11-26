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
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;

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
        primMST(citiesOfUs, 126); //Index of Washington, DC
        for( City c : citiesOfUs)
        {
            if(c.parent == -1)
            {
                System.out.println(c.name);
            }
        }
        System.out.println("The cost: "+getCost(citiesOfUs));
        outputToKML(citiesOfUs);
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
                       // System.out.println(cityList.get(i).name+ " "+ cityList.get(j).name);
                        AdjacencyMatrixGraph.setCell(i, j, getDistance(cityList.get(i), cityList.get(j)));
                    }
                    else
                    {
                       // AdjacencyMatrixGraph.setCell(i, j, Double.POSITIVE_INFINITY);
                    }
                }
                    
            }
        }
    }
    
    public static void primMST(ArrayList<City> cityList, int indexOfRootCity)
    {
        cityList.get(indexOfRootCity).key = 0.0;
        cityList.get(indexOfRootCity).parent = 0;
        ArrayList<City> Q = new ArrayList<City>();
        Q.addAll(cityList);
        Collections.sort(Q);
        while (!Q.isEmpty())
        {
            
            City u = Q.remove(0);
            
            for (Iterator<City> it = AdjacencyMatrixGraph.returnAdj(cityList,u.index).iterator(); it.hasNext();) {
                City v = it.next();
                if( Q.contains(v) && AdjacencyMatrixGraph.getCell(u.index, v.index) < v.key )
                {
                    cityList.get(v.index).parent = cityList.get(u.index).index;
                    cityList.get(v.index).key = AdjacencyMatrixGraph.getCell(u.index, v.index);
                }
                Collections.sort(Q);
                if( Q.contains(v) && v.key < Double.POSITIVE_INFINITY)
                {
                    assert( v.parent != 0 );
                }
                
            }

        }
    }
    public static double getCost( ArrayList<City> cityList)
    {
        double costInLeagues=0.0;
        for(City city : cityList)
        {
            if(city.parent != 0);
            {
                costInLeagues += AdjacencyMatrixGraph.getCell(city.index, city.parent);
            }
        }
        return costInLeagues;
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
        System.out.println(citiesOfUs.size());
        for( City c : citiesOfUs )
        {
            out.println("<Placemark>");
            out.println("<name>"+c.name+"</name>");
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
            int parentOfI = citiesOfUs.get(c.index).parent;
            out.println("<Placemark>");
            out.println("<name>"+citiesOfUs.get(parentOfI).name+" and "+c.name+"</name>");
            out.println("<LineString>");
            out.println("<extrude>1</extrude>");
            out.println("<coordinates>");
            out.println("-"+citiesOfUs.get(c.index).longitude+","+citiesOfUs.get(c.index).latitude+",0 -"+citiesOfUs.get(parentOfI).longitude+","+citiesOfUs.get(parentOfI).latitude+",0");
            out.println("</coordinates>");
            out.println("</LineString>");
            out.println("</Placemark>");

}
}