package ch.hsr.afterhour.model;

public class Location {
    private String name;
    private String description;
    private String placeId;

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
