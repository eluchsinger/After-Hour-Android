package ch.hsr.afterhour.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hsr.afterhour.service.barcode.BarcodeGenerator;
import ch.hsr.afterhour.service.barcode.QrBarcodeGenerator;


public class User implements Serializable {
    /**
     * The size of the barcode in pixel
     */
    private static final int BARCODE_SIZE = 250;
    private static final String PREFIX_USER_BC = "USR-ZRH-";

    private final BarcodeGenerator barcodeGenerator = new QrBarcodeGenerator();
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String mobileNumber;
    private Date dateOfBirth;
    private Gender gender;
    private boolean isWorking = false;
    private List<Ticket> tickets;
    private Bitmap qrImage = null;
    private boolean employee;
    private ArrayList<CoatCheck> coatChecks = new ArrayList<>();

    public User(String lastName, String firstName,
                String email, Gender gender, String mobileNumber,
                Date dateOfBirth, boolean isEmployee) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.employee = isEmployee;
    }

    public User(String lastName, String firstName,
                String email, Gender gender, String mobileNumber,
                String dateOfBirth, boolean isEmployee) throws ParseException {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateFormat.parse(dateOfBirth);;
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
        return barcodeGenerator.generateBarcodeWithSize(this.getPublicId(), BARCODE_SIZE, BARCODE_SIZE);
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

    public String getTickets() {
        StringBuilder sb = new StringBuilder(1024);
        for (Ticket ticket : tickets) {
            sb.append(ticket.getEvent().getTitle() + ", " + ticket.getEvent().getDescription() + "\n");
        }
        return sb.toString();
    }

    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublicId() {
        return PREFIX_USER_BC + getId();
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthFormatted() {
        return dateFormat.format(this.getDateOfBirth());
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
