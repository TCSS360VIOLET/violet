package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Project {
    private Date startDate;

    private Date endDate;

    private double budget;

    private String name;

    private int daysTillFinished;

    private List<String> filePaths;

    private List<Item> items;

    /**
     * Initialize the fields of the project.
     * @param startDate The start date of the project.
     * @param endDate The end date of the project.
     * @param name The name of the project.
     * @param budget The budget of the project.
     * @author Parker J.
     */
    public Project(Date startDate, Date endDate, String name, double budget) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.budget = budget;
        long daysLeft = endDate.getTime() - startDate.getTime();
        this.daysTillFinished = (int) TimeUnit.DAYS.convert(daysLeft, TimeUnit.MILLISECONDS);
        this.filePaths = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    /**
     * Get the start date for the project.
     * @return The end date for the project.
     * @author Parker J.
     */
    public Date getStartDate() {

        return startDate;
    }

    /**
     * Get the name of the project.
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }


    /**
     * Set the name of the project.
     * @param name new name.
     * @author Parker J.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the end date for the project.
     * @return The end date for the project.
     * @author Parker J.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * get the budget for the project.
     * @return The budget for the item.
     */
    public double getBudget() {
        return budget;
    }

    /**
     * set the budget of the project.
     * @param budget the new budget
     * @author Parker J.
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Return days till finished.
     * @return days till finsihed.
     * @author Parker J.
     */
    public int getDaysTillFinished() {
        return daysTillFinished;
    }

    /**
     * Set the file paths for the project.
     * @param filePaths The file paths for the project.
     * @author Lixin W.
     */
    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    /**
     * Return the items in the project.
     * @return The items in the project.
     * @author Lixin W.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Set the items for the project.
     * @param items The items to set for the project.
     * @author Lixin W.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    
}
