package seedu.manager.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticipantTest {

    @Test
    public void setPresent_setTrue_present() {
        Participant participant = new Participant("Participant");
        participant.setPresent(true);
        assertEquals("present", participant.markIfPresent());
    }

    @Test
    public void setPresent_setFalse_notPresent() {
        Participant participant = new Participant("Participant");
        participant.setPresent(false);
        assertEquals("not present", participant.markIfPresent());
    }

    @Test
    public void toString_setTrue_present() {
        Participant participant = new Participant("Participant");
        participant.setPresent(true);
        assertEquals("Participant (status: present)", participant.toString());
    }
}
