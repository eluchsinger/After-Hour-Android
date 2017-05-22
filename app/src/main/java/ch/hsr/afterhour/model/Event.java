package ch.hsr.afterhour.model;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.util.Date;


public class Event {

    private int id;
    private String title;
    private Location location;
    private Date eventDate;
    private String description;
    private Bitmap picture;
    private TicketCategory[] ticketCategories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location.getName();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEventDate() {
        return DateFormat.getDateInstance().format(eventDate);
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setPicture(Bitmap picture){
        this.picture = picture;
    }

    public Bitmap getPicture(){
        return picture;
    }

    public TicketCategory[] getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(TicketCategory[] ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    @Override
    public String toString() {
        return this.getTitle() + " - " + this.getEventDate();
    }
}
