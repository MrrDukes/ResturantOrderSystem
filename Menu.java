import java.text.NumberFormat;
import java.util.*;
/*
The Menu class represents a menu at a restaurant.
 */
public class Menu {

    //Attributes
    String restaurantName;
    String restaurantSlogan;
    Map<Integer,MenuItem> menuItems;
    Map<Integer, String> categories;

    //Constructor
    public Menu(String restaurantName, String restaurantSlogan, Map<Integer, MenuItem> menuItems, Map<Integer, String> categories) {
        this.restaurantName = restaurantName;
        this.restaurantSlogan = restaurantSlogan;
        this.menuItems = menuItems;
        this.categories = categories;
    }//end constructor

    //---------------------------------------------------------------------------
    // Action methods - these methods perform a task or behavior of the object.
    //---------------------------------------------------------------------------

    //Method to display the restaurant menu to the customer.
    public void displayMenu(){
        //Create a currency formatter for the prices on the menu
        double currencyAmount = 1500.00;

        // Create a new Locale displays which type currency country
        Locale usa = new Locale("en", "US");

        // Create a Currency instance for the Locale
        Currency dollars = Currency.getInstance(usa);

        // Create a formatter given the Locale
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        //Print a header for the menu
        System.out.println("--------------------------------------------------------------------------------\n" +
                "|                      Menu for " + getRestaurantName() + "                             |\n" +
                "--------------------------------------------------------------------------------\n");

        //Get a map collection of the categories of menu items
        Map categories = this.getCategories();

        //Get a collection of the items on the menu
        Map<Integer,MenuItem> menuItems = this.getMenuItems();

        //Iterate over the list of categories
        categories.forEach((key, value) -> {
            // Get the category ID and name for each category of menu items
            Integer categoryId = (Integer) key;
            String categoryName = (String) value;

            //Print out the category name (for example Appetizers)
            System.out.println("\n*** " +categoryName + " ***\n");

            //Now iterate through the list of menu items and print each of the menu items
            // that belongs in the category
            menuItems.forEach((menuItemKey, menuItem) -> {
                if(menuItem.getCategory() == categoryId) {
                    System.out.print("#" + menuItemKey + ". " + menuItem.getItemName() + " - ");
                    // Format the Number into a Currency String
                    System.out.print(dollarFormat.format(menuItem.getPrice()));
                    //Print the description of the menu item
                    System.out.println("\n" + menuItem.getDescription() + "\n");
                }//end if
            });//end menu items for each loop
        }); //end categories for each loop

        System.out.println("--------------------------------------------------------------------------------\n");
    } //end displayMenu method

    //Accessor methods - getters and setters
    public String getRestaurantName() {
        return restaurantName;
    } //end accessor method

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    } //end accessor method

    public String getRestaurantSlogan() {
        return restaurantSlogan;
    } //end accessor method

    public void setRestaurantSlogan(String restaurantSlogan) {
        this.restaurantSlogan = restaurantSlogan;
    } //end accessor method

    public Map<Integer,MenuItem> getMenuItems() {
        return menuItems;
    } //end accessor method

    public void setMenuItems(Map<Integer,MenuItem> menuItems) {
        this.menuItems = menuItems;
    } //end accessor method

    public Map getCategories() {
        return categories;
    } //end accessor method

    public void setCategories(Map categories) {
        this.categories = categories;
    } //end accessor method

}//end class
