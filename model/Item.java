package model;


public class Item {
    /**
     * The price of the item
     */
    private double myPrice;

    /**
     * The name of the item
     */
    private String myName;

    private int myQuantity;

    /**
     * Initialzie the fields of the item.
     * @param name The name of the item.
     * @param quantity The number of the item.
     * @param price The price of the item.
     * @author Parker J.
     */
    public Item(String name, int quantity, double price) {
        myName = name;
        myQuantity = quantity;
        myPrice = price;
    }

    /**
     * Return the name of the item.
     * @return The name of the item.
     * @author Parker J.
     */
    public String getMyName() {
        return myName;
    }


    /**
     * Get the price of the item.
     * @return The price of the item.
     * @author Parker J.
     */
    public double getMyPrice() {
        return myPrice;
    }

    /**
     * Get the quantity of the Item.
     * @return The number of items used
     * @author Parker J.
     */
    public int getMyQuantity() {
        return myQuantity;
    }

}
