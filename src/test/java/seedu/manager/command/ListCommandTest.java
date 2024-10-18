package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ListCommandTest {

    private ListCommand listCommand;

    @BeforeEach
    public void setUp() {
        EventList eventList = new EventList();

        eventList.addEvent("Event 1", "2024-10-10 10:00", "Venue A");
        eventList.addEvent("Event 2", "2024-11-11 12:00", "Venue B");

        listCommand = new ListCommand();
        listCommand.setData(eventList);
        listCommand.execute();
    }

    @Test
    public void execute_twoEvents_success() {
        String expectedMessage = "There are 2 events in your list! Here are your scheduled events:\n"
                + "1. Event name: Event 1 / Event time: 2024-10-10 10:00 / Event venue: Venue A / Done: N\n"
                + "2. Event name: Event 2 / Event time: 2024-11-11 12:00 / Event venue: Venue B / Done: N\n";

        assertEquals(expectedMessage, listCommand.getMessage());
        assertFalse(listCommand.getCanExit());
    }
}
