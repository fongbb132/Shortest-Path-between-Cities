import java.io.*;
import java.util.Scanner;

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
 * The driver program of the SigmaAir. Prompting user to enter input and provides required informaitons.
 */
public class SigmaAirDriver {

    public static SigmaAir sigmaAir;

    public static String displayMes = "(A) Add City\n" +
            "(B) Add Connection\n" +
            "(C) Load all Cities\n" +
            "(D) Load all Connections\n" +
            "(E) Print all Cities\n" +
            "(F) Print all Connections\n" +
            "(G) Remove Connection\n" +
            "(H) Find Shortest Path\n" +
            "(Q) Quit\n";


    public static Scanner input = new Scanner(System.in);

    /**
     * The main method of prompting users to enter inputs.
     * @param args
     */
    public static void main(String[] args) {
        boolean running = true;
        try {
            FileInputStream file = new FileInputStream("filename.obj");
            ObjectInputStream fin = new ObjectInputStream(file);
            sigmaAir = (SigmaAir)fin.readObject();
            fin.close();
            System.out.println("Successfully loaded contents of filename.obj.");
        }catch (IOException e){
            System.out.println("filename.obj is not found. Using a new SigmaAir.\n");
            sigmaAir = new SigmaAir();
        }catch (ClassNotFoundException e){
            System.out.println("filename.obj is not found. Using a new SigmaAir.\n");
            sigmaAir = new SigmaAir();
        }

        System.out.println(displayMes);
        while(running){
            System.out.print("Enter a selection: ");

            String command = input.next();
            command = command.toLowerCase();

            if(command.equals("a")){
                addCity();
            }else if(command.equals("b")){
                addConnection();
            }else if(command.equals("c")){
                loadAllCities();
            }else if(command.equals("d")){
                loadAllConnections();
            }else if(command.equals("e")){
                printAllCities();
            }else if(command.equals("f")){
                printAllConnections();
            }else if(command.equals("g")){
                removeConnections();
            }else if(command.equals("h")){
                findShortestPath();
            }else if(command.equals("q")){
                running = false;
                quit();
            }else{
                System.out.println("Invalid Input!");
            }
        }
    }

    /**
     * remove the connections between 2 cities when called.
     */
    public static void removeConnections(){
        Scanner a = new Scanner(System.in);
        System.out.print("Enter a source city: ");
        String from = a.nextLine();
        System.out.print("Enter a destination city:");
        String to = a.nextLine();
        sigmaAir.removeConnection(from,to);
    }

    /**
     * Print all the cities in a sorted and neat format.
     */
    public static void printAllCities(){
        Scanner in = new Scanner(System.in);
        System.out.println("(EA) Sort Cities by Name\n" +
                "(EB) Sort Cities by Latitude\n" +
                "(EC) Sort Cities by Longitude\n" +
                "(Q) Quit");
        boolean running = true;
        while(running){
            System.out.print("Enter a selection: ");
            String selection = in.next();
            selection = selection.toLowerCase();
            if(selection.equals("ea")){
                sigmaAir.printAllCities(new NameComparator());
            }else if(selection.equals("eb")) sigmaAir.printAllCities(new LatComparator());
            else if(selection.equals("ec")) sigmaAir.printAllCities(new LngComparator());
            else if(selection.equals("q")) {
                running = false;
                System.out.println(displayMes);
            }
            else  System.out.println("Invalid input!");
        }
    }

    /**
     * Print all the connections for all the cities in a neat table.
     */
    public static void printAllConnections(){
        sigmaAir.printAllConnections();
    }

    /**
     * Load all the cities from a file.
     */
    public static void loadAllCities(){
        System.out.print("Enter the file name: ");
        String fileName = input.next();
        sigmaAir.loadAllCities(fileName);
    }

    /**
     * load all the connections from a file.
     */
    public static void loadAllConnections(){
        System.out.print("Enter the file name:");
        String fileName = input.next();
        sigmaAir.loadAllConnections(fileName);
    }

    /**
     * prompting user information and add the city in a list when it is called.
     */
    public static void addCity(){
        System.out.print("Enter the name of the city: ");
        String name ;
        input.nextLine();
        name = input.nextLine();
        sigmaAir.addCity(name);
    }

    /**
     * Prompting users for city from and city to then add connection between them.
     */
    public static void addConnection(){
        System.out.print("Enter the source city: ");
        input.nextLine();
        String from = input.nextLine();
        System.out.print("Enter the destination city: ");
        String to = input.nextLine();
        sigmaAir.addConnection(from, to);
    }

    /**
     * to find the shortest path between two location when it is called.
     */
    public static void findShortestPath(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter source city:");
        String from = in.nextLine();
        System.out.print("Enter the destination city: ");
        String to = in.nextLine();
        sigmaAir.shortestPath(from,to);
    }

    /**
     * Terminate the program and saved all the informations to a file.
     */
    public static void quit(){
        try {
            FileOutputStream file = new FileOutputStream("filename.obj");
            ObjectOutputStream fout = new ObjectOutputStream(file);
            fout.writeObject(sigmaAir);
            fout.close();
            System.out.println("sigmaAir is saved into file filename.obj.");
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("File is not saved.");
        }
        System.out.println("Program terminating normally...");
    }

}
