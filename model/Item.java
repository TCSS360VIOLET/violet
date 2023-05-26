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

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public double getMyPrice() {
        return myPrice;
    }

    public void setMyPrice(double myPrice) {
        this.myPrice = myPrice;
    }

    public int getMyQuantity() {
        return myQuantity;
    }

    public void setMyQuantity(int myQuantity) {
        this.myQuantity = myQuantity;
    }
}
