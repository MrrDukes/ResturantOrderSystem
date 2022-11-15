import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Order {

    //Attributes
    ArrayList<MenuItem> orderItems;
    String customerName;
    double salesTaxRate;
    double tipPercentage;

    //Constructor
    public Order(ArrayList<MenuItem> orderItems, String customerName, double salesTaxRate, double tipPercentage) {
        this.orderItems = orderItems;
        this.customerName = customerName;
        this.salesTaxRate = salesTaxRate;
        this.tipPercentage = tipPercentage;
    }//end constructor method


    //---------------------------------------------------------------------------
    // Action methods - these methods perform a task or behavior of the object.
    //---------------------------------------------------------------------------

    //This method prints the receipt for the order.
    //The menu is passed as an argument to print the restaurant name
    //and slogan on the receipt.
    // This method calculates the total amount that the customer owes for their food order,
    // including tax and tip.
    public void printReceipt(Menu menu){
        // Get a currency formatter for the current locale.
        NumberFormat fmt = NumberFormat.getCurrencyInstance();

        //Print Receipt header
        System.out.println("-----------------------------------------------\n");
        System.out.println(menu.getRestaurantName());
        System.out.println(menu.getRestaurantSlogan());
        System.out.println("-----------------------------------------------\n");
        System.out.println("Order for Customer " + this.getCustomerName());

        //Variable to hold the total
        double total = 0.00;

        //Add up the subtotal amount for the items that the customer ordered
        for(MenuItem orderItem : orderItems){
            //Print each item on the receipt
            System.out.println(orderItem.getItemName() + "\t\t\t\t" + fmt.format(orderItem.getPrice()));
            total = total + orderItem.getPrice();
        }//end for loop

        //Print the subtotal on the receipt
        System.out.println("\nSubtotal: " + "\t\t\t" + fmt.format(total));


        //Print the sales tax
        System.out.println("\nTax: " + "\t\t\t\t" +  fmt.format((this.getSalesTaxRate() * total)));

        //print the tip
        System.out.println("Tip: " + "\t\t\t\t" +  fmt.format((this.getTipPercentage() * total)));

        //Calculate the total with the tax and tip
        total = total * (1 + this.getSalesTaxRate());
        total = total * (1 + this.getTipPercentage());

        System.out.println("-----------------------------------------------\n");
        //Calculate the total and display it to the user with the currency format
        System.out.println("Grand Total: " +  "\t\t" + fmt.format(total));
        System.out.println("-----------------------------------------------\n");

    }//end method

    //Getters and Setters
    public ArrayList<MenuItem> getOrderItems() {
        return orderItems;
    }//end getter setter

    public void setOrderItems(ArrayList<MenuItem> orderItems) {
        this.orderItems = orderItems;
    }//end getter setter

    public String getCustomerName() {
        return customerName;
    }//end getter setter

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }//end getter setter

    public double getSalesTaxRate() {
        return salesTaxRate;
    }//end getter setter

    public void setSalesTaxRate(double salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }//end getter setter

    public double getTipPercentage() {
        return tipPercentage;
    }//end getter setter

    public void setTipPercentage(double tipPercentage) {
        this.tipPercentage = tipPercentage;
    }//end getter setter



    //Creates a new menu for the restaurant and adds items to the menu.
    public Menu createMenu(){

        //Create some new menu items
        Map<Integer,MenuItem> menuItems = new HashMap<Integer,MenuItem>();
        //Create some categories for the menu items
        Map<Integer, String> categories = new HashMap<Integer, String>();

        //Create a new database connection
        Connection connection = null;
        try {
            // db parameters
            String url       = "jdbc:mysql://localhost:3306/restaurant";
            String user      = "root";
            String password  = "admin";

            // create a connection to the database
            connection = DriverManager.getConnection(url, user, password);

            //Create a new statement to execute the query
            Statement statement = connection.createStatement();

            //Execute a SQL SELECT query to retrieve all of the menu items from the database
            ResultSet rsMenuItems = statement.executeQuery("SELECT * FROM Menu_Items");

            //Loop through the menu items in the resultset and add them to the hashmap of menu items
            while (rsMenuItems.next()) {
                //Create the new menu item from the result set from the database
                MenuItem menuitem = new MenuItem(rsMenuItems.getString("name"),
                        rsMenuItems.getString("description"),
                        rsMenuItems.getDouble("price"),
                        rsMenuItems.getInt("categoryID"));

                //Put the new menu item in the hashmap
                menuItems.put(rsMenuItems.getInt("itemID"), menuitem);

            }//end while loop

            //Execute a SQL SELECT query to retrieve all of the menu categories from the database
            ResultSet rsCategories = statement.executeQuery("SELECT * FROM menu_categories");

            //Loop through the categories in the resultset and add them to the hashmap of categories
            while (rsCategories.next()) {
                //Add the category from the result set to the hashmap of categories
                categories.put(rsCategories.getInt("categoryID"),
                        rsCategories.getString("name"));
            }//new end while loop

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }//establish the connecter for SQL

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





    //This method saves the customer's order to the database
    public void saveOrder(Order order, Menu menu){
        //Create a new database connection
        Connection connection = null;
        try {
            // db parameters
            String url       = "jdbc:mysql://localhost:3306/restaurant";
            String user      = "root";
            String password  = "admin";

            // create a connection to the database
            connection = DriverManager.getConnection(url, user, password);

            //Create a new statement to execute the query
            Statement statement = connection.createStatement();

            //Get the order ID for the highest order number in the database and add one for the
            //current order. This ensures we don't try to add an order id that already exists.
            ResultSet rsOrderId = statement.executeQuery("SELECT orderID FROM orders ORDER BY orderID DESC LIMIT 1;");
            if(rsOrderId.next()){
                int orderID = rsOrderId.getInt("orderID") + 1;

                //Get the current date for the order
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  //2022-09-28
                LocalDateTime now = LocalDateTime.now();
                String orderDate = dtf.format(now);

                //Add a new record to the database for the order
                String sql = "INSERT INTO ORDERS (orderID, date, customername) VALUES (" + orderID +
                        ", '" + orderDate + "', '"+ order.getCustomerName()+ "')";

                statement.executeUpdate(sql);

                //Get the order ID for the highest order details ID number in the database and add one for each
                //item in the current order.
                // This ensures we don't try to add an order detail id that already exists.
                ResultSet rsOrderDetailId = statement.executeQuery(
                        "SELECT orderdetailID FROM ORDER_DETAILS ORDER BY orderdetailID DESC LIMIT 1;");
                if(rsOrderDetailId.next()) {
                    int orderDetailID = rsOrderDetailId.getInt("orderdetailID") + 1;

                    //Get the list of food items from the customer's order
                    ArrayList<MenuItem> orderItems = order.getOrderItems();

                    //Get the map collection of menu items from the restaurant's menu
                    Map<Integer,MenuItem> menuItems = menu.getMenuItems();

                    //Loop through the food items in the order to
                    // add them to the order details table in the database
                    for(MenuItem orderItem : orderItems) {

                        //Get the number for the current order item
                        //Integer itemID = menuItems.containsValue(orderItem);
                        for (Map.Entry<Integer, MenuItem> entry : menuItems.entrySet()) {
                            //Get the item name
                            String itemName = orderItem.getItemName();

                            //Find the
                            String menuItem = entry.getValue().getItemName();
                            if (menuItem.equals(itemName)) {
                                //Get the item ID
                                Integer itemID = entry.getKey();

                                //Increment the order detail ID
                                orderDetailID = orderDetailID + 1;

                                //Create a SQL statement to insert a new record for the food item on the order
                                //INSERT INTO ORDER_DETAILS (orderdetailID, orderID, itemID) VALUES (999, 1018, 3);
                                sql = "INSERT INTO ORDER_DETAILS (orderdetailID, orderID, itemID) VALUES (" + orderDetailID +
                                        ", " + orderID + ", " + itemID + ")";

                                //Execute the statement to update the database
                                statement.executeUpdate(sql);
                            } //end if statement
                        } //end inner for loop
                    }//end outer for loop
                }//end nested if
            }//end if

            //Catch any SQL Exceptions
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally { //Close the connection to the database
            try{
                if(connection != null){
                    connection.close();
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }//end try catch
    } //end method
}//end class
