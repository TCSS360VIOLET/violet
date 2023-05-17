package model;

public class Item {

    private double myPrice;
    private String myName;

    private double myBudget;

    public double getMyBudget() {
        return myBudget;
    }

    public void setMyBudget(double myBudget) {
        this.myBudget = myBudget;
    }

    public int getMyQuantity() {
        return myQuantity;
    }

    public void setMyQuantity(int myQuantity) {
        this.myQuantity = myQuantity;
    }

    private int myQuantity;

    public Item(String name, int quantity, double Budget, double price) {
        myName = name;
        myQuantity = quantity;
        myBudget = Budget;
        myPrice = price;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public double getDifference() {
        return getMyBudget() - (getMyQuantity() * getMyPrice());
    }

    public double getMyPrice() {
        return myPrice;
    }

    public void setMyPrice(double myPrice) {
        this.myPrice = myPrice;
    }
}
