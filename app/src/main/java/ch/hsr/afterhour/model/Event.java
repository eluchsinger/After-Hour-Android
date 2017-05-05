package ch.hsr.afterhour.model;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Marcel on 03.04.17.
 */

public class Event {

    private int id;
    private String title;
    private String location;
    private Date date;
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
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return DateFormat.getDateInstance().format(date);
    }

    public void setDate(Date date) {
        this.date = date;
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
}
