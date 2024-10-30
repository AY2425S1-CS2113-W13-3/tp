package seedu.manager.parser;

import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.command.CopyCommand;
import seedu.manager.command.ExitCommand;
import seedu.manager.command.FilterCommand;
import seedu.manager.command.ListCommand;
import seedu.manager.command.MarkCommand;
import seedu.manager.command.MarkEventCommand;
import seedu.manager.command.MarkParticipantCommand;
import seedu.manager.command.MenuCommand;
import seedu.manager.command.RemoveCommand;
import seedu.manager.command.SortCommand;
import seedu.manager.command.ViewCommand;
import seedu.manager.command.FindCommand;
import seedu.manager.enumeration.Priority;
import seedu.manager.exception.InvalidCommandException;
import seedu.manager.event.EventList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import static java.util.logging.Level.WARNING;

/**
 * Represents the command parser for EventManagerCLI
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command!";
    private static final String INVALID_ADD_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            add -e EVENT -t TIME -v VENUE -u PRIORITY
            add -p PARTICIPANT -e EVENT
            add -m ITEM -e EVENT
            """;
    private static final String INVALID_REMOVE_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            remove -e EVENT
            remove -m ITEM -e EVENT
            """;
    private static final String INVALID_VIEW_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            view -e EVENT
            """;
    private static final String INVALID_MARK_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            mark -e EVENT -s STATUS
            mark -p PARTICIPANT -e EVENT -s STATUS
            """;
    private static final String INVALID_EVENT_STATUS_MESSAGE = """
            Invalid event status!
            Please set the event status as either "done" or "undone"
            """;
    private static final String INVALID_PARTICIPANT_STATUS_MESSAGE = """
            Invalid participant status!
            Please set the event status as either "present" or "absent"
            """;
    private static final String INVALID_SORT_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            sort -e EVENT -by name/time/priority
            """;
    private static final String INVALID_SORT_KEYWORD_MESSAGE = """
            Invalid sort keyword!
            Please set the sort keyword as either "name"/"time"/"priority"
            """;
    private static final String INVALID_DATE_TIME_MESSAGE = """
            Invalid date-time format!
            Please use the following format for event time:
            YYYY-MM-DD HH:mm
            """;
    private static final String INVALID_COPY_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            copy FROM_EVENT > TO_EVENT
            """;
    private static final String INVALID_PRIORITY_MESSAGE = """
            Invalid priority level status!
            Please use the following format for priority level:
            high/medium/low
            """;
    private static final String INVALID_FILTER_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            filter -e/-t/-u FILTER_DESCRIPTION
            """;
    private static final String INVALID_FILTER_FLAG_MESSAGE = """
            Invalid filter flag!
            Please set the filter flag as either "-e/-t/-u"
            """;
    private static final String INVALID_FIND_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            find -e EVENT -p NAME
            """;
    private static final String INVALID_FIND_FLAG_MESSAGE = """
            Invalid find flag!
            Please set the find flag using "-e" and "-p""
            """;
    private static final String ADD_EVENT_REGEX = "(-e|-t|-v|-u)";
    private static final String ADD_PARTICIPANT_REGEX = "(-p|-n|-email|-e)";
    private static final String ITEM_REGEX = "(-m|-e)";
    private static final String REMOVE_PARTICIPANT_REGEX = "(-p|-e)";
    private static final String FIND_REGEX = "\\s*(-e|-p)\\s*";

    /**
     * Returns a command based on the given user command string.
     *
     * @param command The given command string from the user.
     * @throws InvalidCommandException if the given command string cannot be parsed to a valid command.
     */
    public Command parseCommand(String command) throws InvalidCommandException {
        String[] commandParts = command.split(" ");
        String commandWord = commandParts[0];

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return parseAddCommand(command, commandParts);
        case RemoveCommand.COMMAND_WORD:
            return parseRemoveCommand(command, commandParts);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ViewCommand.COMMAND_WORD:
            return parseViewCommand(command, commandParts);
        case MenuCommand.COMMAND_WORD:
            return new MenuCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case MarkCommand.COMMAND_WORD:
            return parseMarkCommand(command, commandParts);
        case CopyCommand.COMMAND_WORD:
            return parseCopyCommand(command, commandParts);
        case FindCommand.COMMAND_WORD:
            return parseFindCommand(command, commandParts);
        case SortCommand.COMMAND_WORD:
            return parseSortCommand(command, commandParts);
        case FilterCommand.COMMAND_WORD:
            return parseFilterCommand(command, commandParts);
        default:
            throw new InvalidCommandException(INVALID_COMMAND_MESSAGE);
        }
    }

    //@@author KuanHsienn
    /**
     * Parses the input string to create an {@link Command} object based on the provided command parts.
     * <p>
     * This method examines the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create an {@link AddCommand} for adding an event
     * with the specified details (event name, time, and venue). If the command flag is {@code "-p"},
     * it creates an {@link AddCommand} for adding a participant to an event, including the participant's
     * name, contact number, email, and the event name. If the command flag does not match either,
     * an {@link InvalidCommandException} is thrown with an error message.
     * </p>
     *
     * @param input        the input string containing the command details
     * @param commandParts an array of strings representing the parsed command parts, where the second element
     *                     is the command flag, indicating the type of command
     * @return a {@link Command} object representing the parsed command
     * @throws InvalidCommandException if the command flag is invalid, or if there are missing or improperly
     *                                 formatted input details
     */
    public Command parseAddCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(AddCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equals("-e")) {
                return getAddEventCommand(input);
            } else if (commandFlag.equals("-p")) {
                return getAddParticipantCommand(input);
            } else if (commandFlag.equals("-m")) {
                return getAddItemCommand(input);
            }

            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        } catch (DateTimeParseException exception) {
            logger.log(WARNING,"Invalid date-time format");
            throw new InvalidCommandException(INVALID_DATE_TIME_MESSAGE);
        } catch (IllegalArgumentException exception) {
            logger.log(WARNING,"Invalid priority level status");
            throw new InvalidCommandException(INVALID_PRIORITY_MESSAGE);
        }
    }

    /**
     * Returns an {@link AddCommand} that adds an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws DateTimeParseException if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException if the priority parameter is not valid.
     */
    private Command getAddEventCommand(String input) throws IndexOutOfBoundsException, DateTimeParseException,
            IllegalArgumentException {
        String[] inputParts = input.split(ADD_EVENT_REGEX);
        logger.info("Creating AddCommand for event with details: " +
                inputParts[1].trim() + ", " + inputParts[2].trim() + ", " + inputParts[3].trim());
        String eventName = inputParts[1].trim();
        LocalDateTime eventTime = LocalDateTime.parse(inputParts[2].trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String venue = inputParts[3].trim();
        Priority eventPriority = Priority.valueOf(inputParts[4].trim().toUpperCase());
        return new AddCommand(eventName, eventTime, venue, eventPriority);
    }

    //@@author LTK-1606
    /**
     * Returns an {@link AddCommand} that adds a participant with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getAddParticipantCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(ADD_PARTICIPANT_REGEX);
        logger.info("Creating AddCommand for participant with details: " +
                inputParts[1].trim() + ", " + inputParts[2].trim());
        String participantName = inputParts[1].trim();
        String participantNumber = inputParts[2].trim();
        String participantEmail = inputParts[3].trim();
        String eventName = inputParts[4].trim();
        return new AddCommand(participantName, participantNumber, participantEmail, eventName);
    }

    //@@author jemehgoh
    /**
     * Returns an {@link AddCommand} that adds an item with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds an item with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getAddItemCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(ITEM_REGEX);
        String itemName = inputParts[1].trim();
        String eventName = inputParts[2].trim();
        logger.info(String.format("Creating AddCommand for item with details: %s, %s", itemName,
                eventName));
        return new AddCommand(itemName, eventName);
    }

    //@@author LTK-1606
    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link RemoveCommand}
     * for removing an event. If the command flag is {@code "-p"}, it creates a
     * {@link RemoveCommand} for removing a participant from an event. If neither flag
     * is matched, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flags are not matched in the command parts.
     */
    private Command parseRemoveCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(RemoveCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equals("-e")) {
                return getRemoveEventCommand(input);
            } else if (commandFlag.equals("-p")) {
                return getRemoveParticipantCommand(input);
            } else if (commandFlag.equals("-m")) {
                return getRemoveItemCommand(input);
            }

            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        }
    }

    //@@author KuanHsienn
    /**
     * Returns a {@link RemoveCommand} that removes an event, with a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link RemoveCommand} that removes an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveEventCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split("-e");
        return new RemoveCommand(inputParts[1].trim());
    }

    //@@author LTK-1606
    /**
     * Returns a {@link RemoveCommand} that removes a participant, with fields from a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link RemoveCommand} that removes a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveParticipantCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(REMOVE_PARTICIPANT_REGEX);
        return new RemoveCommand(inputParts[1].trim(), inputParts[2].trim(), true);
    }

    //@@author jemehgoh
    /**
     * Returns a {@link RemoveCommand} that removes an item, with fields from a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link RemoveCommand} that removes an item with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     */
    private RemoveCommand getRemoveItemCommand(String input) throws IndexOutOfBoundsException {
        String[] inputParts = input.split(ITEM_REGEX);
        return new RemoveCommand(inputParts[1].trim(), inputParts[2].trim(), false);
    }

    //@@author glenn-chew
    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link ViewCommand}
     * for viewing the participants in the event.
     * Otherwise, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flag is not matched.
     */
    private Command parseViewCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(ViewCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equals("-e")) {
                String [] inputParts = input.split("-e");
                return new ViewCommand(inputParts[1].trim());
            }

            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
        }
    }

    //@@author jemehgoh
    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-e"}, it splits the input string to create a {@link MarkCommand}
     * to mark an event done or undone. Otherwise, it throws an {@link InvalidCommandException}
     * with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flag is not matched.
     */
    private Command parseMarkCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(MarkCommand.COMMAND_WORD);
        try {
            String commandFlag = commandParts[1];

            if (commandFlag.equalsIgnoreCase("-e")) {
                String[] inputParts = input.split("-e|-s");
                return getMarkEventCommand(inputParts[1].trim(), inputParts[2].trim());
            } else if (commandFlag.equalsIgnoreCase("-p")) {
                String[] inputParts = input.split("-p|-e|-s");
                return getMarkParticipantCommand(inputParts[1].trim(), inputParts[2].trim(), inputParts[3].trim());
            }

            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkEventCommand} with a given event name and status. If the given status is invalid,
     *     throws an {@link InvalidCommandException}.
     *
     * @param eventName the given event name.
     * @param status the given event status.
     * @return a MarkCommand with a given event name and status
     * @throws InvalidCommandException if the given status is invalid.
     */
    private Command getMarkEventCommand(String eventName, String status) throws InvalidCommandException {
        if (status.equalsIgnoreCase("done")) {
            return new MarkEventCommand(eventName, true);
        } else if (status.equalsIgnoreCase("undone")) {
            return new MarkEventCommand(eventName, false);
        } else {
            logger.log(WARNING,"Invalid status keyword");
            throw new InvalidCommandException(INVALID_EVENT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkCommand} with a given participant name, event name and status. If the given status is
     *     invalid, throws an {@link InvalidCommandException}.
     *
     * @param participantName the given participant name.
     * @param eventName the given event name.
     * @param status the given event status.
     * @return a MarkCommand with a given event name and status
     * @throws InvalidCommandException if the given status is invalid.
     */
    private Command getMarkParticipantCommand(String participantName, String eventName, String status) {
        if (status.equalsIgnoreCase("present")) {
            return new MarkParticipantCommand(participantName, eventName, true);
        } else if (status.equalsIgnoreCase("absent")) {
            return new MarkParticipantCommand(participantName, eventName, false);
        } else {
            logger.log(WARNING, "Invalid status keyword");
            throw new InvalidCommandException(INVALID_PARTICIPANT_STATUS_MESSAGE);
        }
    }

    //@@author MatchaRRR
    /**
     * Parses the input string to create a {@link Command} based on the provided command parts.
     *
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is {@code "-by"}, it splits the input string to create a {@link SortCommand}
     * Otherwise, it throws an {@link InvalidCommandException} with an error message.
     * </p>
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link Command} object representing the parsed command.
     * @throws InvalidCommandException if the flag is not matched.
     */
    private Command parseSortCommand(String input, String[] commandParts) throws InvalidCommandException{
        assert commandParts[0].equalsIgnoreCase(SortCommand.COMMAND_WORD);
        try {
            String[] inputParts = input.split("-by", 2);
            if (inputParts.length < 2) {
                throw new InvalidCommandException(INVALID_SORT_MESSAGE);
            }

            String keyword = inputParts[1].trim();
            Set<String> validKeywords = Set.of("name", "time", "priority");
            if (validKeywords.contains(keyword.toLowerCase())) {
                return new SortCommand(keyword);
            }
            throw new InvalidCommandException(INVALID_SORT_KEYWORD_MESSAGE);

        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING, "Invalid command format");
            throw new InvalidCommandException(INVALID_SORT_MESSAGE);
        }
    }

    //@@author KuanHsienn
    /**
     * Parses a CSV file containing event details and loads the events into the specified EventList.
     *
     * This method reads each line from the specified file, expecting the format to be:
     * <pre>
     * eventName, eventTime, eventVenue, eventPriority
     * </pre>
     * where:
     * - eventName is a String representing the name of the event.
     * - eventTime is a String formatted as "yyyy-MM-dd HH:mm" that will be parsed into a LocalDateTime object.
     * - eventVenue is a String representing the venue of the event.
     * - eventPriority is a String representing the priority level of the event.
     *
     * If a line does not contain exactly three parts, it is skipped.
     *
     * @param events The EventList where the parsed events will be added.
     * @param filePath The path to the file containing the event details.
     * @throws IOException If there is an error reading from the file or if the file cannot be found.
     */
    public void parseFile(EventList events, String filePath) throws IOException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (String line : Files.readAllLines(Paths.get(filePath))) {
                String[] parts = line.split(","); // CSV format
                if (parts.length == 4) {
                    String eventName = parts[0].trim();
                    LocalDateTime time = LocalDateTime.parse(parts[1].trim(), formatter);
                    String venue = parts[2].trim();
                    Priority priority = Priority.valueOf(parts[3].trim().toUpperCase());
                    events.addEvent(eventName, time, venue, priority);
                }
            }
        } catch (IOException exception) {
            throw new IOException("Error loading events from file: " + filePath + ".");
        }
    }
  
    /**
     * Parses the input string and command parts to create a {@code FilterCommand} object.
     * <p>
     * This method verifies that the first part of {@code commandParts} matches the expected filter command
     * and then checks if a valid flag is provided. The filter flag should be one of <code>"-e"</code>,
     * <code>"-t"</code>, or <code>"-u"</code>, representing different filter types.
     * If the flag is valid and additional input is provided, a new {@code FilterCommand} is created.
     * <p>
     * If the input format is incorrect, or an invalid flag is provided,
     * this method throws an {@code InvalidCommandException}.
     *
     * @param input        the full user input string
     * @param commandParts the split parts of the command, with the first element expected to be the filter command word
     * @return a {@code FilterCommand} object initialized with the specified flag and filter criteria
     * @throws InvalidCommandException if the command format is invalid or an invalid flag is provided
     */
    private Command parseFilterCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FilterCommand.COMMAND_WORD);

        try {
            String[] inputParts = input.split("(-e|-t|-u)");
            if (inputParts.length < 2) {
                throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
            }

            Set<String> validFlags = Set.of("-e", "-t", "-u");
            if (validFlags.contains(commandParts[1].trim().toLowerCase())) {
                return new FilterCommand(commandParts[1].trim().toLowerCase(), inputParts[1].trim());
            }
            throw new InvalidCommandException(INVALID_FILTER_FLAG_MESSAGE);
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
        }
    }


    //@author LTK-1606
    /**
     * Parses the input command to create a {@code CopyCommand} object.
     * <p>
     * This method checks if the command input starts with the specified command word
     * and then removes it from the input. It splits the remaining input at the '>' character
     * to separate the source and destination parts. If the split does not yield exactly
     * two parts, an {@code InvalidCommandException} is thrown.
      * </p>
     *
     * @param input the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code CopyCommand} object with the parsed source and destination
     * @throws InvalidCommandException if the command is missing required parts or has an invalid format
     */
    private Command parseCopyCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(CopyCommand.COMMAND_WORD);

        try {
            String commandInput = input.replaceFirst("^" + commandParts[0] + "\\s*", "");
            String[] inputParts = commandInput.split(">");

            if (inputParts.length != 2) {
                throw new InvalidCommandException(INVALID_COPY_MESSAGE);
            }

            return new CopyCommand(inputParts[0].trim(), inputParts[1].trim());

        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_COPY_MESSAGE);
        }
    }

    //@author LTK-1606
    /**
     * Parses the input command to create a {@code FindCommand} object.
     * <p>
     * This method checks if the input contains the required flags (-e for event and -p for person).
     * It splits the input into parts based on these flags and validates the resulting segments.
     * If valid, it constructs and returns a new {@code FindCommand} with the specified event name
     * and participant name. If the command format is invalid or the required flags are missing,
     * an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code FindCommand} object with the parsed event and person names
     * @throws InvalidCommandException if the command is missing required flags or has an invalid format
     */
    private Command parseFindCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FindCommand.COMMAND_WORD);
        try {
            if (!input.contains("-e") || !input.contains("-p")) {
                throw new InvalidCommandException(INVALID_FIND_FLAG_MESSAGE);
            }

            String[] inputParts = input.split(FIND_REGEX);
            if (inputParts.length < 3 || inputParts[1].isBlank()) {
                throw new InvalidCommandException(INVALID_FIND_MESSAGE);
            }

            return new FindCommand(inputParts[1].trim(), inputParts[2].trim());
        } catch (IndexOutOfBoundsException exception) {
            logger.log(WARNING,"Invalid command format");
            throw new InvalidCommandException(INVALID_FIND_MESSAGE);
        }
    }
}
