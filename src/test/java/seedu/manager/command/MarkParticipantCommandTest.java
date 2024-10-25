package seedu.manager.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.manager.event.EventList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkParticipantCommandTest {
    private EventList eventList;

    @BeforeEach
    public void testSetUp() {
        eventList = new EventList();
        eventList.addEvent("Event 1", "2024-10-10 1600", "Venue 1");
        eventList.addParticipantToEvent("John Doe", "Event 1");
    }

    @Test
    public void execute_validParticipantMarkTrue_success() {
        String expectedMessage = "Participant marked present.";

        MarkParticipantCommand command = new MarkParticipantCommand("John Doe",
                "Event 1", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_validParticipantMarkFalse_success() {
        String expectedMessage = "Participant marked absent.";

        MarkParticipantCommand command = new MarkParticipantCommand("John Doe",
                "Event 1", false);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_invalidParticipant_failure() {
        String expectedMessage = "Participant not found!";

        MarkParticipantCommand command = new MarkParticipantCommand("Jane Doe",
                "Event 1", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }

    @Test
    public void execute_invalidEvent_failure() {
        String expectedMessage = "Event not found!";

        MarkParticipantCommand command = new MarkParticipantCommand("Jane Doe",
                "Event 2", true);
        command.setData(eventList);
        command.execute();
        assertEquals(expectedMessage, command.getMessage());
    }
}
