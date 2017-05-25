package ch.hsr.afterhour.model;

import java.io.Serializable;

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
