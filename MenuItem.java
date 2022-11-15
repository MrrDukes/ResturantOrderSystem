public class MenuItem {
    //Attributes
    String itemName;
    String description;
    double price;
    int category;

    //Constructor for creating a fully-populated menu item
    public MenuItem(String itemName, String description, double price, int category) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.category = category;
    }//end constructor

    public String getItemName() {
        return itemName;
    }//end accessor method

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }//end accessor method

    public String getDescription() {
        return description;
    }//end accessor method

    public void setDescription(String description) {
        this.description = description;
    }//end accessor method

    public double getPrice() {
        return price;
    }//end accessor method

    public void setPrice(double price) {
        this.price = price;
    }//end accessor method

    public int getCategory() {
        return category;
    }//end accessor method

    public void setCategory(int category) {
        this.category = category;
    }//end accessor method





}//end class
