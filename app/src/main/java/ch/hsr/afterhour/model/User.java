package ch.hsr.afterhour.model;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable {

    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;
    private List<Ticket> tickets;

    public User(String lastName, String firstName, String email, String mobileNumber, String dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

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
        return lastName;
    }

    public void setName(String name) {
        this.lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
