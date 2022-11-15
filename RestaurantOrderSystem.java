import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RestaurantOrderSystem {

    //Attributes
    double salesTaxRate;



    //Getters and Setters
    public double getSalesTaxRate() {
        return salesTaxRate;
    }//end getter

    public void setSalesTaxRate(double salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }//end setter

    //Creates a new menu for the restaurant and adds items to the menu.
    public Menu createMenu(){

        //Create some new menu items
        Map<Integer,MenuItem> menuItems = new HashMap<Integer,MenuItem>();

        menuItems.put(1, new MenuItem("Poppers", "Fresh peppers stuffed with sharp cheddar", 6.99, 1));
        menuItems.put(2, new MenuItem("Nachos", "Fresh chips topped with sharp cheddar", 4.99, 1));
        menuItems.put(3, new MenuItem("Quesadilla", "A cheesy delight!", 5.99, 2));
        menuItems.put(4, new MenuItem("Taco", "Topped with fresh lettuce!", 3.99, 2));
        menuItems.put(5, new MenuItem("Churros", "Cinnamon sweetness!", 2.99, 3));
        menuItems.put(6, new MenuItem("Sopapillas", "Crispy cinnamon fried goodness!", 2.99, 3));

        //Create some categories for the menu items
        Map<Integer, String> categories = new HashMap<Integer, String>();
        categories.put(1, "Appetizers");
        categories.put(2, "Entrees");
        categories.put(3, "Desserts");

        //Create a new menu
        Menu mexicanMenu = new Menu("Dos Mary's Tex-Mex", "The home of Tex-Mex in Austin, TX!", menuItems,categories);

        //Return the menu we just created
        return mexicanMenu;
    }//end method

    //Takes the customer's order
    public Order takeCustomerOrder(Map<Integer,MenuItem> menuItems) {
        //Use a scanner to get the customer's input
        Scanner scanner = new Scanner(System.in);

        //Create an arraylist to hold the items the user wants to order
        ArrayList<MenuItem> orderItems = new ArrayList<MenuItem>();

        //Get the items that the customer would like to order
        //Keep looping until the customer is done ordering
        boolean orderMoreItems = true;
        while(orderMoreItems) {
            //Prompt the customer to order
            System.out.println("Enter the number for the item you would like to order: ");

            //Get the item number from the customer and add the corresponding menu item to the
            //list of items for the order
            orderItems.add(menuItems.get(Integer.parseInt(scanner.next())));

            //Ask the customer if they want to order more and update the flag to stop the loop
            //if they are done ordering
            System.out.println("Do you want to order another item? Y/N");
            if(scanner.next().equalsIgnoreCase("N")){
                orderMoreItems = false;
            }//end if

        }//end while

        //Get the user's name for the order
        System.out.println("Please enter the name for the order: ");
        String customerName = scanner.next();

        System.out.println("Please enter the percent for the tip (numbers only please) 10, 15, or 20: ");
        Double tipPercentage = Double.parseDouble(scanner.next())/100;


        //Return the order
        Order order = new Order(orderItems,customerName,this.getSalesTaxRate(),tipPercentage);
        return order;
    } //end method


    public static void main(String[] args){
        //Create a new instance of the resturant ordering system
        RestaurantOrderSystem orderSystem = new RestaurantOrderSystem();

        //Set the sales tax rate, based on the location of the restaurant
        orderSystem.setSalesTaxRate(0.075);

        //Create a new menu
        Menu menu = orderSystem.createMenu();

        //Show the menu to the customer
        menu.displayMenu();

        //Take the customer's order
        Order order = orderSystem.takeCustomerOrder(menu.getMenuItems());

        //Give the customer their receipt
        order.printReceipt(menu);
    } //end main

}//end class
