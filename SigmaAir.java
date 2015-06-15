import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import latlng.LatLng;

import java.io.File;
import java.io.Serializable;
import java.util.*;
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
 * The object stores all the cities in a list and user them.
 */
public class SigmaAir implements Serializable{
    private ArrayList<City> cities = new ArrayList<City>();
    public static final int MAX_CITIES=100;
    private double[][] connections = new double[MAX_CITIES][MAX_CITIES];

    /**
     * The constructor of SigmaAir
     */
    public SigmaAir(){
        for(int i = 0; i< connections.length;i++){
            for(int j = 0;j<connections[0].length;j++){
                connections[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /**
     * Add a city to the list.
     * @param city the name of a city
     */
    public void addCity(String city){

        Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest;
        GeocodeResponse geocodeResponse;
        double lat;
        double lng;
        try {
            geocoderRequest = new GeocoderRequestBuilder().setAddress(city).getGeocoderRequest();
            geocodeResponse = geocoder.geocode(geocoderRequest);
            lat = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
            lng = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
            LatLng loc = new LatLng(lat,lng);
            City a = new City(city,loc,cities.size());
            cities.add(a);
            City.cityCount=cities.size();
            connections[cities.size()][cities.size()]=0;
            System.out.println(city+ " has been added: ( "+ lat+", " + lng + ")");
        }catch (Exception e){
            System.out.println("The city "+ city +" isn't exist");
        }
    }

    /**
     * Add connections between 2 cities.
     * @param cityFrom the city that the plane is coming form.
     * @param cityTo the city that the plane is going to.
     */
    public void addConnection(String cityFrom, String cityTo){
        City from = getCity(cityFrom);
        City to = getCity(cityTo);
        if(from!=null&&to!=null){
            double distance = LatLng.calculateDistance(from.getLocation(),to.getLocation());
            connections[from.getIndexPos()][to.getIndexPos()] = distance;
            connections[to.getIndexPos()][from.getIndexPos()] = distance;
            System.out.println(cityFrom+" --> "+cityTo+ " added: " + distance);
        }else{
            System.out.println("Either the city from is invalid or city to is invalid.");
        }
    }

    /**
     * To remove the connection between two cities.
     * @param cityFrom the city that the plane is coming form.
     * @param cityTo the city that the plane is going to.
     */
    public void removeConnection(String cityFrom, String cityTo){
        City from = getCity(cityFrom);
        City to = getCity(cityTo);
        if(from!=null&&to!=null){
            double distance = Double.POSITIVE_INFINITY;
            connections[from.getIndexPos()][to.getIndexPos()] = distance;
            System.out.println("Connection from "+cityFrom+ "to "+cityTo+" has been removed!");
        }else{
            System.out.println("Either the city from is invalid or city to is invalid.");
        }
    }

    /**
     * To get the shortest distance between 2 cities.
     * @param cityFrom the city that the plane is coming form.
     * @param cityTo the city that the plane is going to.
     * @return the shortest Path String.
     */
    public String shortestPath(String cityFrom, String cityTo){
        City from = getCity(cityFrom);
        City to = getCity(cityTo);

        double[] distance = new double[cities.size()];
        String[] path = new String[cities.size()];
        boolean[] marked = new boolean[cities.size()];
        for(int b = 0;b<marked.length;b++){
            marked[b]=false;
            distance[b]=Double.POSITIVE_INFINITY;
            path[b]="";
        }

        if(from!=null&&to!=null){

            ArrayList<City> queue = new ArrayList<City>();

            marked[from.getIndexPos()]=true;
            distance[from.getIndexPos()]=0;
            path[from.getIndexPos()]=from.getCity();
            queue.add(from);
            while(!queue.isEmpty()){
                City currentCity = queue.remove(0);
                int[] neighbors = getNeighbor(currentCity);

                for(int i = 0; i< neighbors.length;i++){
                    int nextNeighbor = neighbors[i];
                    double dis = connections[currentCity.getIndexPos()][nextNeighbor];
                    double newDis = distance[currentCity.getIndexPos()]+dis;
                    if(newDis<distance[nextNeighbor]) {
                        distance[nextNeighbor] = newDis;
                        path[nextNeighbor] = path[currentCity.getIndexPos()] + " --> " + getCity(nextNeighbor).getCity();
                    }else{

                    }
                }
                for(int i = 0; i<neighbors.length;i++){
                    int nextNeighbor = neighbors[i];
                    if(!marked[nextNeighbor]){
                        marked[nextNeighbor]=true;
                        queue.add(getCity(nextNeighbor));
                    }
                }
            }

            if(distance[to.getIndexPos()]<Double.POSITIVE_INFINITY) {
                System.out.println("Shortest path from " + cityFrom + " to " + cityTo + ": ");
                System.out.println( path[to.getIndexPos()] +  ": " + distance[to.getIndexPos()]);
            }else {
                System.out.println("Shortest path from " + cityFrom + " to " + cityTo + " does not exist!");
            }
        }else {
            System.out.println("Either the city from is invalid or city to is invalid.");
        }
        if(to!=null) {
            return path[to.getIndexPos()] + ": " + distance[to.getIndexPos()];
        }else{
            return "";
        }
    }

    /**
     * prints all cities in the order based on the given Comparator
     * @param comp the comparator that is based on to sort the items.
     */
    public void printAllCities(Comparator comp){
        ArrayList<City> sorted = cities;
        Collections.sort(sorted, comp);
        System.out.println("Cities:");
        System.out.println(String.format("%-30s%-15s%-15s","City Name","Latitude","Longitude"));
        System.out.println("----------------------------------------------------------------------------------");
        for(int i = 0; i<sorted.size();i++){
            System.out.println(String.format("%-30s%-15s%-15s",sorted.get(i).getCity(),sorted.get(i).getLocation().getLat(),sorted.get(i).getLocation().getLng()));
        }
    }

    /**
     * To print all the connections between all the cities.
     */
    public void printAllConnections(){
        System.out.println(String.format("%-40s%-30s","Connections: ","Distance"));
        System.out.println("----------------------------------------------------------------------------------");

        for(int i = 0; i<connections.length;i++){
            for(int a = 0;a <connections[0].length;a++){
                if(connections[i][a]<Double.POSITIVE_INFINITY){
                    if(i<cities.size()&&a<cities.size()&&a!=i) {
                        System.out.println(String.format("%-40s%-30f", getCity(i).getCity() + " --> " + getCity(a).getCity(), connections[i][a]));
                    }
                }
            }
        }
    }

    /**
     * To load a list of cities from a txt file
     * @param filename the name of the text file that needed to be add.
     */
    public void loadAllCities(String filename) {
        try {
            Scanner loadDoc = new Scanner(new File(filename));
            while (loadDoc.hasNext()) {
                String city = loadDoc.nextLine();
                addCity(city);
            }
        }catch (Exception e){
            System.out.print("Document is not found.");
        }
    }



    /**
     * To load a list of connections from a txt file
     * @param filename the name of the text file that needed to be add.
     */
    public void loadAllConnections(String filename){
        try {
            Scanner loadDoc = new Scanner(new File(filename));
            while (loadDoc.hasNext()) {
                String nextLine = loadDoc.nextLine();
                String[] locs = nextLine.split(",");
                addConnection(locs[0], locs[1]);
            }
        }catch (Exception e){
            System.out.println("Document is not found.");
        }
    }

    /**
     * Helper method to get a city from a list bases on name of city.
     * @param name the name of the city
     * @return the city that is requesting. Null if it is not found.
     */
    public City getCity(String name){
        for (int i = 0; i< cities.size();i++){
            if(cities.get(i).getCity().equals(name)){
                return cities.get(i);
            }
        }
        return null;
    }

    /**
     * A helper method to get a list of neighbor of a city.
     * @param city the city that needs to get its neighbors.
     * @return an interger array with all its neighbor locations.
     */
    public int[] getNeighbor(City city){
        int[] neighbor=new int[0];
        for(int i=0;i<cities.size();i++){
            double neighborDis=connections[city.getIndexPos()][i];
            if(neighborDis!=Double.POSITIVE_INFINITY&&neighborDis!=0){
                int[] newNei=new int[neighbor.length+1];
                for (int a = 0;a<neighbor.length;a++){
                    newNei[a]=neighbor[a];
                }
                newNei[newNei.length-1]=i;
                neighbor=newNei;
            }
        }
        return neighbor;
    }

    /**
     * To get a city based on its position of in an array.
     * @param num is the position.
     * @return The city is lookong for.
     */
    public City getCity(int num){
        for(int i =0;i<cities.size();i++){
            if(cities.get(i).getIndexPos()==num){
                return cities.get(i);
            }
        }
        return null;
    }

}
