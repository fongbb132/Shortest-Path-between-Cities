import latlng.LatLng;

import java.io.Serializable;
/**
 * Ka Wing Fong
 * 109794011
 * HW 6
 * CSE 214-R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author Ka Wing Fong
 */

/**
 * City object stores the information of a city such as the name of city, the location and the index of it.
 */
public class City implements Serializable{
    String city;
    LatLng location;
    int indexPos;
    static int cityCount;

    /**
     * Constructor of a city object
     * @param city the name of the city.
     * @param location the location of the city.
     * @param indexPos the index of this city in an arrayList.
     */
    public City(String city, LatLng location, int indexPos) {
        this.city = city;
        this.location = location;
        this.indexPos = indexPos;
    }

    /**
     * The getMethod of city to return the name of it.
     * @return the name of the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * To change the name of the city
     * @param city is the name that to be set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * return the Location of the city in LatLng format.
     * @return the location of the city.
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * To change the location of a city.
     * @param location is the location that needs to be changed.
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * To access the position of this object in an arrayList.
     * @return the position of the city.
     */
    public int getIndexPos() {
        return indexPos;
    }

}
