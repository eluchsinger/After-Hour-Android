package ch.hsr.afterhour.model;

/**
 * Created by Marcel on 13.05.17.
 */

public class CoatCheck {
    private int id;
    private Event event;

    public CoatCheck(Event event, int id) {
        this.event = event;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }
}
