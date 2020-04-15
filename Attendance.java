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
public class Attendance {
    private StringProperty eventid;
    private StringProperty event;
    private StringProperty confirmed;
    public Attendance(String eventid, String event, String confirmed) {
        this.eventid = new SimpleStringProperty(eventid);
        this.event = new SimpleStringProperty(event);
        this.confirmed = new SimpleStringProperty(confirmed);
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
    public String getConfirmed() {
        return confirmed.get();
    }
    public void setConfirmed(StringProperty confirmed) {
        this.confirmed = confirmed;
    }
    public StringProperty eventidproperty() {
        return eventid;
    }
    public StringProperty eventproperty() {
        return event;
    }
    public StringProperty confirmedproperty() {
        return confirmed;
    }
}
