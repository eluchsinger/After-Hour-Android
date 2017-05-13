package ch.hsr.afterhour.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {

    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;
    private boolean isWorking = false;
    private List<Ticket> tickets;
    private Bitmap qrImage = null;
    private boolean isEmployeee;
    private ArrayList<CoatCheck> coatChecks = new ArrayList<>();

    public User(String lastName, String firstName, String email, String mobileNumber, String dateOfBirth, boolean isEmployee) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.isEmployeee = isEmployee;
    }

    public User() {

    }

    public List<CoatCheck> getCoatChecks() {
        return coatChecks;
    }

    public void addCoatCheck(CoatCheck coatCheck) {
        coatChecks.add(coatCheck);
    }

    public boolean isEmployeee() {
        return isEmployeee;
    }

    public void setIsEmployeee(boolean employeee) {
        isEmployeee = employeee;
    }

    public Bitmap getQrImage() {
        return qrImage;
    }

    public void setQrImage(Bitmap qrImage) {
        this.qrImage = qrImage;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
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

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
