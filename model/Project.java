package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Project {
    private Date startDate;

    private Date endDate;

    private double budget;

    private String name;

    private int daysTillFinished;


    public Project(Date startDate, Date endDate, String name, double budget) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.budget = budget;
        long daysLeft = endDate.getTime() - startDate.getTime();
        this.daysTillFinished = (int) TimeUnit.DAYS.convert(daysLeft, TimeUnit.MILLISECONDS);

    }

    //    private int calculateDaysBetween(Date d1, Date d2) {
//        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
//        String firstInput = formatter.parse(d1.toString()).toString();
//        String secondInput = formatter.parse(d2.toString()).toString();
//        final LocalDate firstDate = LocalDate.parse(firstInput, formatter);
//        final LocalDate secondDate = LocalDate.parse(secondInput, formatter);
//        final int days = (int)ChronoUnit.DAYS.between(firstDate, secondDate);
//        return days;
//    }
    public Date getStartDate() {

        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getDaysTillFinished() {
        return daysTillFinished;
    }

    public void setDaysTillFinished(int daysTillFinished) {
        this.daysTillFinished = daysTillFinished;
    }
}
