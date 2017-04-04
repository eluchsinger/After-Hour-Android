package ch.hsr.afterhour.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class User implements Serializable {

    private String id;
    private String name;
    private String firstName;
    private Date dateOfBirth;
    private List<Ticket> tickets;

    public String getTickets() {
        StringBuilder sb = new StringBuilder(1024);
        for (Ticket ticket : tickets) {
            sb.append(ticket.getEvent().getTitle() + ", " + ticket.getEvent().getDescription() + "\n");
        }
        return sb.toString();
    }

    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return DateFormat.getDateInstance().format(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
