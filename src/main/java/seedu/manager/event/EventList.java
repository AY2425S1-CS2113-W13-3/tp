package seedu.manager.event;

import java.util.ArrayList;


/**
 * The EventList class manages a list of Event objects.
 * It provides methods to manage an event list.
 */
public class EventList  {
    private final ArrayList<Event> eventList;

    /**
     * Constructor that initializes EventList with a given list of event.
     *
     * @param eventList The initial list of tasks.
     */
    public EventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Default constructor that initializes an empty event list.
     */
    public EventList(){
        eventList = new ArrayList<>();
    }

    /**
     * @return The size of the event list.
     */
    public int getListSize() {
        return eventList.size();
    }

    /**
     * Adds a new event to the event list.
     * 
     * This method creates a new instance of the {@link Event} class with the specified
     * event name and adds it to the {@code eventList}.
     *
     * @param eventName the name of the event to be added
     */
    public void addEvent(String eventName, String time, String venue) {
        Event newEvent = new Event(eventName, time, venue);
        eventList.add(newEvent);
    }

    /**
     * @param index The index of event in the list (0 based indexing)
     * @return The specific event in the event list.
     */
    public Event getEvent(int index) {
        return eventList.get(index);
    }

    public boolean removeEvent(String eventName) {
        for (Event event : eventList) {
            if (event.getEventName().equals(eventName)) { // Assuming Event has a getName() method
                eventList.remove(event);
                return true; // Event found and removed
            }
        }
        return false; // Event not found
    }
}
