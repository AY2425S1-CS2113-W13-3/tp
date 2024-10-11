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
        try (FileWriter writer = new FileWriter(filepath)) { // Using try-with-resources
            for (Event event : events.getList()) {
                writer.write(event.getEventName() + "," + event.getEventTime() + ","
                        + event.getEventVenue() + "\n"); // Save event details
            }
        }
    }

    /**
     * Loads events from the file and returns an EventList.
     *
     * @throws IOException if there's an error reading from the file.
     */
    public void loadEvents(EventList events) throws IOException {
        for (String line : Files.readAllLines(Paths.get(filepath))) {
            String[] parts = line.split(","); // Assuming CSV format
            if (parts.length == 3) {
                String eventName = parts[0].trim();
                String time = parts[1].trim();
                String venue = parts[2].trim();
                events.addEvent(eventName, time, venue); // Create event from loaded data
            }
        }
    }
}
