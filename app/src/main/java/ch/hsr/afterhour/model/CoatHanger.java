package ch.hsr.afterhour.model;

/**
 * Created by Marcel on 15.05.17.
 */

public class CoatHanger {
    private int coatHangerNumber;
    private Location location;

    public CoatHanger(int coatHangerNumber, Location location) {
        this.coatHangerNumber = coatHangerNumber;
        this.location = location;
    }

    public CoatHanger() {
    }

    public int getCoatHangerNumber() {
        return coatHangerNumber;
    }

    public void setCoatHangerNumber(int coatHangerNumber) {
        this.coatHangerNumber = coatHangerNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
