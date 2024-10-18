package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkCommandTest {
    private EventList eventList;

    @BeforeEach
    public void testSetUp() {
        eventList = new EventList();
        eventList.addEvent("Event 1", "2024-10-10 1600", "Venue 1");
    }

    @Test
    public void execute_eventPresentMarkDone_success() {
        String expectedMessage = "Event marked as done";
        MarkCommand command = new MarkCommand("Event 1", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_eventPresentMarkNotDone_success() {
        String expectedMessage = "Event marked not done";
        MarkCommand command = new MarkCommand("Event 1", false);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_eventAbsent_failure() {
        String expectedMessage = "Event not found!";
        MarkCommand command = new MarkCommand("Event 2", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }
}
