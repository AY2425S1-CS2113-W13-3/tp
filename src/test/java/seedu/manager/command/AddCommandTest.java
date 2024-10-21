package seedu.manager.command;

import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandTest {

    @Test
    public void add_event_success() {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-20-10 21:00", "Venue A");
        assertEquals(1, eventList.getListSize());
    }

    @Test
    public void add_twoParticipant_success() {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-10-10 10:00", "Venue A");
        eventList.addParticipantToEvent("Tom", "Event 1");
        eventList.addParticipantToEvent("Harry", "Event 1");

        assertEquals(2, eventList.getEvent(0).getParticipantCount());
    }

    @Test
    public void add_oneParticipantWrongly_success() {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-10-10 10:00", "Venue A");
        eventList.addParticipantToEvent("Tom", "Event 1");
        eventList.addParticipantToEvent("Harry", "Event 2");

        assertEquals(1, eventList.getEvent(0).getParticipantCount());
    }

    @Test
    public void add_oneParticipantInvalidEvent_failure() {
        EventList eventList = new EventList();
        String expectedMessage = "Event not found!";

        AddCommand addCommand = new AddCommand("Tom", "Event 1");
        addCommand.setData(eventList);
        addCommand.execute();

        assertEquals(expectedMessage, addCommand.getMessage());
    }
}

