import java.util.*;
import java.io.*;


public class Main{
    public static void main(String[] args){
        // Disreguard this entire class to run run the fastfoodkitchendriver class
        /*
        try{
            //This is creating the code to take input from the order.csv file
            File file = new File("Orders.csv");
            Scanner scnr = new Scanner(file);
            System.out.println(scnr.nextLine());

            //This is creating a String array from the csv file making a new string at every comma in the line
            String line = scnr.nextLine();
            String[] lineArray = line.split(",");
            int id = Integer.parseInt(lineArray[0]);
            int numHamburgers = Integer.parseInt(lineArray[1]);
            int numCheeseburgers = Integer.parseInt(lineArray[2]);
            int numVeggieburgers = Integer.parseInt(lineArray[3]);
            int numSodas = Integer.parseInt(lineArray[4]);
            boolean togo = false;
            if (lineArray[5].equals("FALSE")){
                togo = false;
            } else if (lineArray[5].equals("TRUE")){
                togo = true;
            }
            System.out.println(id);
            BurgerOrder b = new BurgerOrder(numHamburgers,numCheeseburgers,numVeggieburgers,numSodas,togo,id);
            System.out.println(b);

        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        */
            
    }
}