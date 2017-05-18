package ch.hsr.afterhour.model;

import java.io.Serializable;


public class CoatCheck implements Serializable {
    // server & user viewable parameter
    private int publicIdentifier; // show to employee
    private CoatHanger coatHanger;

    public int getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(int publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    public CoatHanger getCoatHanger() {
        return coatHanger;
    }

    public void setCoatHanger(CoatHanger coatHanger) {
        this.coatHanger = coatHanger;
    }
}
