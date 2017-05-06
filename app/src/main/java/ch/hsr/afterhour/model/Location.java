package ch.hsr.afterhour.model;

/**
 * Created by eluch on 05.05.2017.
 */

public class Location {
    private String name;
    private String description;
    private String placeId;

    public Location() {
        this("", "", "");
    }

    public Location(String name, String description, String placeId) {
        this.name = name;
        this.description = description;
        this.placeId = placeId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPlaceId() {
        return this.placeId;
    }
}
