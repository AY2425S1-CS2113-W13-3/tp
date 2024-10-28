package seedu.manager.command;

import seedu.manager.event.Event;
import seedu.manager.item.Participant;

import java.util.ArrayList;
import java.util.Optional;

public class CopyCommand extends Command {
    public static final String COMMAND_WORD = "copy";

    private static final String EVENT_NOT_FOUND = "Event(s) not found!";
    private static final String PARTICIPANT_NOT_FOUND = "Participant list is empty!";
    private static final String COPY_SUCCESSFUL = "Participant list copied over successfully!";

    protected String copyTo;
    protected String copyFrom;

    public CopyCommand(String copyTo, String copyFrom) {
        super(false);
        this.copyTo = copyTo;
        this.copyFrom = copyFrom;
    }

    @Override
    public void execute() {
        StringBuilder outputMessage = new StringBuilder();

        ArrayList<Participant> participants;
        Optional<Event> eventTo = eventList.getEventByName(copyTo);
        Optional<Event> eventFrom = eventList.getEventByName(copyFrom);

        if (eventTo.isPresent() && eventFrom.isPresent()) {
            if (eventFrom.get().getParticipantList().isEmpty()) {
                outputMessage.append(PARTICIPANT_NOT_FOUND);
            } else {
                participants = eventFrom.get().getParticipantList();
                eventTo.get().setParticipantList(participants);
                outputMessage.append(COPY_SUCCESSFUL);
            }
        } else {
            outputMessage.append(EVENT_NOT_FOUND);
        }

        this.message = outputMessage.toString();
    }

}
