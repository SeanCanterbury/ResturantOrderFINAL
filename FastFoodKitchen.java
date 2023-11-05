

import java.util.*;
import java.io.*;

/**
 *
 * ITSC 1213 
 * University of North Carolina at Charlotte
 */

public class FastFoodKitchen {

    private ArrayList<BurgerOrder> orderList = new ArrayList();
    public ArrayList<BurgerOrder> completedOrders = new ArrayList();

    private static int nextOrderNum = 1;

    FastFoodKitchen() {
        this.addExternalOrders();
        /*
        orderList.add(new BurgerOrder(3, 15, 4, 10, false, getNextOrderNum()));
        incrementNextOrderNum();
        orderList.add(new BurgerOrder(10, 10, 3, 3, true, getNextOrderNum()));
        incrementNextOrderNum();
        orderList.add(new BurgerOrder(1, 1, 1, 2, false, getNextOrderNum()));
        incrementNextOrderNum();
        */
    }
    /*
    *
    *
    *   addExternalOrders method takes the recorder orders in the orders.csv file and puts them into the ordersList arrayList
    *
    */
    public void addExternalOrders(){
        try {
        File file = new File("Orders.csv");
        Scanner scnr = new Scanner(file);
        scnr.nextLine();
        
        while(scnr.hasNext()){
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
            BurgerOrder b = new BurgerOrder(numHamburgers,numCheeseburgers,numVeggieburgers,numSodas,togo,id);
            orderList.add(b);
            incrementNextOrderNum();

            

        
        }
            } catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        
    }

    /**
     * Writes the remaining unfinished orders at the end of the day to the remaining orders csv in the same csv format as the input orders
     *
     * 
     */
    public void saveRemainingOrders(){
        try{
            FileOutputStream out = new FileOutputStream("remainingOrders.csv");
            PrintWriter pw = new PrintWriter(out);

            for(BurgerOrder b : orderList){
                pw.println(b.toStringFile());
            }

            pw.close();
            out.close();

            } catch (IOException e){
                System.out.println("OOPS");
            }
    }

    /**
     * report loops through the completed order list and the current orderList and prints them out to the end of day report.txt using the toReport method
     * it also calculates the total of each item sold and prints that to the report aswell
     *
     * 
     */
    public void report(){
        try{
            FileOutputStream out = new FileOutputStream("endOfDayReport.txt");
            PrintWriter pw = new PrintWriter(out);

            for(BurgerOrder b: completedOrders){
                pw.println("(Completed) " + b.toReport());
                
            }
            for(BurgerOrder b: orderList){
                pw.println("(Pending) " + b.toReport());
            }
            pw.println();
            int totalHamburgers = 0;
            int totalCheeseburgers = 0;
            int totalVeggieburgers = 0;
            int totalSodas = 0;
            for(BurgerOrder b: completedOrders){
                totalHamburgers += b.getNumHamburger();
                totalCheeseburgers += b.getNumCheeseburgers();
                totalVeggieburgers += b.getNumVeggieburgers();
                totalSodas += b.getNumSodas();  
            }
            for(BurgerOrder b: orderList){
                totalHamburgers += b.getNumHamburger();
                totalCheeseburgers += b.getNumCheeseburgers();
                totalVeggieburgers += b.getNumVeggieburgers();
                totalSodas += b.getNumSodas(); 
            }

            pw.println("TOTALS FOR TODAY: "+ totalHamburgers + " Hamburgers, " + totalCheeseburgers + " Cheeseburgers, " + totalVeggieburgers + " Veggieburgers, " + totalSodas + " Sodas");
            pw.println("TOTAL ITEMS SOLD: " + (totalHamburgers + totalCheeseburgers + totalVeggieburgers + totalSodas));

            pw.close();
            out.close();

            
            
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException f){
            System.out.println("IOException");
        }
    }

    /**
     * Getter for the next order num variable
     *
     * @return nextOrderNum
     */
    public static int getNextOrderNum() {
        return nextOrderNum;
    }

    /**
     * This adds 1 to the next order number value
     *
     * 
     */
    private void incrementNextOrderNum() {
        nextOrderNum++;
    }

    /**
     * addOrder creates a new burgerorder object and adds that object to the orderlist
     *
     * @return orderNumber of that object
     */
    public int addOrder(int ham, int cheese, int veggie, int soda, boolean toGo) {
        int orderNum = getNextOrderNum();
        orderList.add(new BurgerOrder(ham, cheese, veggie, soda, toGo, orderNum));
        incrementNextOrderNum();
        orderCallOut(orderList.get(orderList.size() - 1));
        return orderNum;

    }

    /**
     * isOrderDone takes a number input and loops through the order list looking for an object matching that number
     *
     * @return the boolean value true if done false if not
     */
    public boolean isOrderDone(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                return false;
            }
        }
        return true;
    }

    /**
     *  cancelOrder loops through the orderList and finds an order matching the input value and removes it from that list
     *
     * @return the boolean value true if was removed false if not
     */
    public boolean cancelOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                orderList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * getter for the number of orders pending by returning the size of the orderList
     *
     * @return the int value size of orderList
     */
    public int getNumOrdersPending() {
        return orderList.size();
    }

    /**
     * This method removes the most reacently added object in the orderList
     *
     * @return the boolean value true if removed false if not
     */
    public boolean cancelLastOrder() {

        if (!orderList.isEmpty()) { // same as  if (orderList.size() > 0) 
            orderList.remove(orderList.size() - 1);
            return true;
        }

        return false;
    }

    /**
     * orderCallOut prints out the items ordered asuming the value is > 0
     *
     * 
     */
    private void orderCallOut(BurgerOrder order) {
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumHamburger() + " hamburgers");
        }
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumCheeseburgers() + " cheeseburgers");
        }
        if (order.getNumVeggieburgers() > 0) {
            System.out.println("You have " + order.getNumVeggieburgers() + " veggieburgers");
        }
        if (order.getNumSodas() > 0) {
            System.out.println("You have " + order.getNumSodas() + " sodas");
        }

    }

    /**
     * completeSpecificOrder takes in the orderId values loops through the orderList and removes the order that corosponds to that id and prints a message confirming its done
     *
     * 
     */
    public void completeSpecificOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                System.out.println("Order number " + orderID + " is done!");
                if (orderList.get(i).isOrderToGo()) {
                    orderCallOut(orderList.get(i));
                }
                completedOrders.add(orderList.get(i));
                orderList.remove(i);
                //i.setComplete(true);
            }
        }

    }

    /**
     * CompleteNextOrder takes the first order in the orderList and passes the orderID through to the completeSpecificOrder method
     *
     * 
     */
    public void completeNextOrder() {
        int nextOrder = orderList.get(0).getOrderNum();
        completeSpecificOrder(nextOrder);

    }

    /**
     * getOrderList is a getter for orderList
     *
     * @return the ArrayList orderList
     */
    public ArrayList<BurgerOrder> getOrderList() {
        return orderList;
    }

    /**
     * This method loops through orderList and compares the orderNum of the burgerOrder to whatWeAreLookingFor and if its == then it returns the position in the arrayList
     *
     * @return the int index of said object from the arrayList orderList
     */
    public int findOrderSeq(int whatWeAreLookingFor) {
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getOrderNum() == whatWeAreLookingFor) {
                return j;
            }
        }
        return -1;
    }

//    public int findOrderBin(int whatWeAreLookingFor) {
//        int left = 0;
//        int right = orderList.size() - 1;
//        while (left <= right) {
//            int middle = (left + right) / 2;
//            if (whatWeAreLookingFor < orderList.get(middle).getOrderNum()) {
//                right = middle - 1;
//            } else if (whatWeAreLookingFor > orderList.get(middle).getOrderNum()) {
//                left = middle + 1;
//            } else {
//                return middle;
//            }
//        }
//        return -1;
//    }

/*
  public int findOrderBin(int orderID){
        int left = 0;
        int right = orderList.size()-1;
        while (left <= right){
            int middle = ((left + right)/2);
            if (orderID < orderList.get(middle).getOrderNum()){
                right = middle-1;
            }
            else if(orderID > orderList.get(middle).getOrderNum()){
                left = middle +1;
            }
            else{
                return middle;
            }
        }
        return -1;
        
    }
    public void selectionSort(){
        for (int i = 0; i< orderList.size()-1; i++){
            int minIndex = i;
            for (int k = i+1; k < orderList.size(); k++){
                if (orderList.get(minIndex).getTotalBurgers() > orderList.get(k).getTotalBurgers()){
                    minIndex = k;
                }
            }
            BurgerOrder temp = orderList.get(i);
            orderList.set(i , orderList.get(minIndex));
            orderList.set(minIndex, temp);
        }
    }

    public void insertionSort() {
        for (int j = 1; j < orderList.size(); j++) {
            BurgerOrder temp = orderList.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.getTotalBurgers() < orderList.get(possibleIndex - 1).getTotalBurgers()) {
                orderList.set(possibleIndex, orderList.get(possibleIndex - 1));
                possibleIndex--;
            }
            orderList.set(possibleIndex, temp);
        }
    }
    */
//    public void selectionSort() { //weird method!
//
//        for (int j = 0; j < orderList.size() - 1; j++) {
//            int minIndex = j;
//            for (int k = j + 1; k < orderList.size(); k++) {
//
//                 if (orderList.get(minIndex).getTotalBurgers() > orderList.get(j).getTotalBurgers()){
//                    minIndex = k;
//                }
//            }
//            BurgerOrder temp = orderList.get(j);
//            orderList.set(j, orderList.get(minIndex));
//            orderList.set(minIndex, temp);
//
//        }
//    }

}
