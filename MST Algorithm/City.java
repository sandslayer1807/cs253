

/**
 * @author Ethan Wells
 * @class Algorithms
 * @teacher: Bruce McMillin
 * @date Nov 25, 2012
 * @assignment
 */
package algorithmsproj4;

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

public class City {

    double latitude;
    double longitude;
    String name;
    int index;
    
    public City (double lat, double long2, String nameIn, int indexOfCity)
    {
        latitude = lat;
        longitude = long2;
        name = nameIn;
        index = indexOfCity;
    }
    public double getLat()
    {
        return latitude;
    }
    public double getLong()
    {
        return longitude;
    }
    public String getName()
    {
        return name;
    }
    
}
