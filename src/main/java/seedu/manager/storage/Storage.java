package seedu.manager.storage;

import seedu.manager.event.EventList;
import seedu.manager.event.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles the storage and retrieval of tasks from a specified file.
 */
public class Storage {
    private final String filepath;

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filepath The path to the storage file.
     */
    public Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Saves the list of events to the file.
     *
     * @param events The EventList to be saved.
     * @throws IOException if there's an error writing to the file.
     */
    public void saveEvents(EventList events) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        for (Event event : events.getList()) {
            writer.write(event.getEventName() + "\n");
        }
        writer.close();
    }

    /**
     * Loads events from the file and returns an EventList.
     *
     * @return An EventList populated from the file.
     * @throws IOException if there's an error reading from the file.
     */
    public EventList loadEvents(EventList events) throws IOException {
        for (String line : Files.readAllLines(Paths.get(filepath))) {
            events.addEvent(line);
        }
        return events;
    }
}
