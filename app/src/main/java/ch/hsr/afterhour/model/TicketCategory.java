package ch.hsr.afterhour.model;

import java.util.Date;

public class TicketCategory {
    private int id;
    private String name;
    private String description;
    private double price;
    private Date startAvailability;
    private Date endAvailability;

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartAvailability() {
        return startAvailability;
    }

    public void setStartAvailability(Date startAvailability) {
        this.startAvailability = startAvailability;
    }

    public Date getEndAvailability() {
        return endAvailability;
    }

    public void setEndAvailability(Date endAvailability) {
        this.endAvailability = endAvailability;
    }
}
