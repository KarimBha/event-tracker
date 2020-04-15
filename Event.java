/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nitos
 */
public class Event {

    private StringProperty eventid;
    private StringProperty event;
    private StringProperty date;
    private StringProperty details;

    public Event(String eventid, String event, String date, String details) {
        this.eventid = new SimpleStringProperty(eventid);
        this.event = new SimpleStringProperty(event);
        this.date = new SimpleStringProperty(date);
        this.details = new SimpleStringProperty(details);

    }

    public String getEventid() {
        return eventid.get();
    }

    public void setEventid(StringProperty eventid) {
        this.eventid = eventid;
    }

    public String getEvent() {
        return event.get();
    }

    public void setEvent(StringProperty event) {
        this.event = event;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(StringProperty date) {
        this.date = date;
    }

    public String getDetails() {
        return details.get();
    }

    public void setDetails(StringProperty details) {
        this.details = details;
    }

    public StringProperty eventidproperty() {
        return eventid;
    }

    public StringProperty eventproperty() {
        return event;
    }

    public StringProperty dateproperty() {
        return date;
    }

    public StringProperty detailsproperty() {
        return details;
    }

}
