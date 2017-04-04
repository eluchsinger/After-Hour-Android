package ch.hsr.afterhour.model;

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
}
