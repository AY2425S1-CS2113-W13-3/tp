package seedu.manager.event;

/**
 * The Event class represents an event with a name, time, and venue.
 * It provides methods to access and modify the time and venue of the event.
 */
public class Event {
    private final String eventName;
    private String eventTime;
    private String eventVenue;

    /**
     * Constructs an Event with the specified name.
     *
     * @param eventName the name of the event
     */
    public Event(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @return the event time
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * @return the event venue
     */
    public String getEventVenue() {
        return eventVenue;
    }

    /**
     * Sets a new time for the event.
     *
     * @param eventTime the new event time
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Sets a new venue for the event.
     *
     * @param eventVenue the new event venue
     */
    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }
}
