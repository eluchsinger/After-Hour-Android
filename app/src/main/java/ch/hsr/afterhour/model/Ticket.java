package ch.hsr.afterhour.model;

import java.io.Serializable;

/**
 * Created by Marcel on 03.04.17.
 */

public class Ticket implements Serializable {
    private int id;
    private Event event;
    private Enum type;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }
}
