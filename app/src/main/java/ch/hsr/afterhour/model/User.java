package ch.hsr.afterhour.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {

    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;
    private Gender gender;
    private boolean isWorking = false;
    private List<Ticket> tickets;
    private Bitmap qrImage = null;
    private boolean employee;
    private ArrayList<CoatCheck> coatChecks = new ArrayList<>();

    public User(String lastName, String firstName,
                String email, Gender gender, String mobileNumber,
                String dateOfBirth, boolean isEmployee) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.employee = isEmployee;
    }

    public User() {

    }

    public List<CoatCheck> getCoatChecks() {
        return coatChecks;
    }

    public void addCoatCheck(CoatCheck coatCheck) {
        coatChecks.add(coatCheck);
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setIsEmployee(boolean employee) {
        this.employee = employee;
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

    public Gender getGender() {return gender;}

    public void setGender(Gender gender) {this.gender = gender;}

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
