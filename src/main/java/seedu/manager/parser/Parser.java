package seedu.manager.parser;

import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.command.CopyCommand;
import seedu.manager.command.ExitCommand;
import seedu.manager.command.FilterCommand;
import seedu.manager.command.ListCommand;
import seedu.manager.command.MarkCommand;
import seedu.manager.command.MarkEventCommand;
import seedu.manager.command.MarkItemCommand;
import seedu.manager.command.MarkParticipantCommand;
import seedu.manager.command.MenuCommand;
import seedu.manager.command.RemoveCommand;
import seedu.manager.command.EditParticipantCommand;
import seedu.manager.command.EditEventCommand;
import seedu.manager.command.EditItemCommand;
import seedu.manager.command.SortCommand;
import seedu.manager.command.ViewCommand;
import seedu.manager.command.FindCommand;
import seedu.manager.enumeration.Priority;
import seedu.manager.exception.InvalidCommandException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the command parser for EventManagerCLI
 */
public class Parser {
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command!";
    private static final String INVALID_ADD_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            add -e EVENT -t TIME -v VENUE -u PRIORITY
            add -p PARTICIPANT -email EMAIL -e EVENT
            add -m ITEM -e EVENT
            """;
    private static final String INVALID_REMOVE_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            remove -e EVENT
            remove -p PARTICIPANT -e EVENT
            remove -m ITEM -e EVENT
            """;
    private static final String INVALID_EDIT_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            edit -e EVENT -name EVENT_NAME -t TIME -v VENUE -u PRIORITY
            edit -m ITEM > NEW_ITEM -e EVENT
            edit -p OLD_PARTICIPANT -name NEW_PARTICIPANT -email EMAIL -e EVENT
            """;
    private static final String INVALID_VIEW_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            view -e EVENT -y TYPE
            """;
    private static final String INVALID_MARK_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            mark -e EVENT -s STATUS
            mark -p PARTICIPANT -e EVENT -s STATUS
            mark -m ITEM -e EVENT -s STATUS
            """;
    private static final String INVALID_COPY_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            copy FROM_EVENT > TO_EVENT
            """;
    private static final String INVALID_SORT_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            sort -by name/time/priority
            """;
    private static final String INVALID_FILTER_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            filter -e/-d/-t/-x/-u FILTER_DESCRIPTION
            """;
    private static final String INVALID_FIND_MESSAGE = """
            Invalid command!
            Please enter your commands in the following format:
            find -e EVENT -p NAME
            """;
    private static final String INVALID_DATE_TIME_MESSAGE = """
            Invalid date-time format!
            Please enter the event time in the following format:
            YYYY-MM-DD HH:mm
            
            Ensure the following general guidelines:
            - The year (YYYY) is from 0001 onwards.
            - The date (MM-DD) is between 01-01 and 12-31.
            - The time (HH:mm) is between 00:00 and 23:59.
            
            Please also take into account leap years!
            """;
    private static final String INVALID_PRIORITY_MESSAGE = """
            Invalid priority level status!
            Please use the following format for priority level:
            high/medium/low
            """;
    private static final String PAST_DATE_MESSAGE = """
            Datetime is in the past! Stop living in the past!
            Please enter a new date in the same format.
            """;
    private static final String INVALID_EMAIL_MESSAGE = """
            Invalid email format!
            Please enter a valid email address.
            """;
    private static final String INVALID_TYPE_MESSAGE = """
            Invalid type!
            Please set the type as either "participant" or "item"
            """;
    private static final String INVALID_EVENT_STATUS_MESSAGE = """
            Invalid event status!
            Please set the event status as either "done" or "undone"
            """;
    private static final String INVALID_PARTICIPANT_STATUS_MESSAGE = """
            Invalid participant status!
            Please set the event status as either "present" or "absent"
            """;
    private static final String INVALID_ITEM_STATUS_MESSAGE = """
            Invalid mark status!
            Please set the event status as either "accounted" or "unaccounted"
            """;
    private static final String INVALID_SORT_KEYWORD_MESSAGE = """
            Invalid sort keyword!
            Please set the sort keyword as either "name"/"time"/"priority"
            """;
    private static final String DUPLICATE_FLAG_MESSAGE = """
            Duplicate flags found!
            Please only use each flag once!
            """;
    private static final String EMPTY_INPUT_MESSAGE = """
            Invalid input!
            One of your input fields are empty!
            Please fill in every field appropriately!
            """;
    private static final String NON_ENGLISH_MESSAGE = """
            Invalid input!
            Please only use ASCII characters!
            """;

    private static final String EVENT_FLAG = "-e";
    private static final String PARTICIPANT_FLAG = "-p";
    private static final String ITEM_FLAG = "-m";

    private static final String SPACE = " ";
    private static final String ARROW = ">";

    private static final String EVENT_FLAG_REGEX = "(-e|-t|-v|-u)";
    private static final String PARTICIPANT_FLAG_REGEX = "(-p|-email|-e)";
    private static final String ITEM_FLAG_REGEX = "(-m|-e)";
    private static final String REMOVE_EVENT_FLAG_REGEX = "-e";
    private static final String REMOVE_PARTICIPANT_FLAG_REGEX = "(-p|-e)";
    private static final String EDIT_EVENT_ATTRIBUTE_FLAG_REGEX = "(-e|-name|-t|-v|-u)";
    private static final String VIEW_FLAG_REGEX = "(-e|-y)";
    private static final String MARK_EVENT_FLAG_REGEX = "-e|-s";
    private static final String MARK_PARTICIPANT_FLAG_REGEX = "-p|-e|-s";
    private static final String MARK_ITEM_FLAG_REGEX = "-m|-e|-s";
    private static final String COPY_FLAG_REGEX = ">";
    private static final String SORT_FLAG_REGEX = "-by";
    private static final String FILTER_FLAG_REGEX = "(-e|-d|-t|-x|-u)";
    private static final String FIND_FLAG_REGEX = "(-e|-p)";

    private static final Pattern ASCII_PATTERN = Pattern.compile("^[\\x00-\\x7F]+$");
    private static final String ADD_EVENT_REGEX = "add\\s+-e\\s+(.*?)\\s+-t\\s+(.*?)\\s+-v\\s+(.*?)\\s+-u\\s+(.*)";
    private static final String ADD_PARTICIPANT_REGEX = "add\\s+-p\\s+(.*?)\\s+" +
            "-email\\s+(.*?)\\s+-e\\s+(.*)";
    private static final String ADD_ITEM_REGEX = "add\\s+-m\\s+(.*?)\\s+-e\\s+(.*)";
    private static final String REMOVE_EVENT_REGEX = "remove\\s+-e\\s+(.*)";
    private static final String REMOVE_PARTICIPANT_REGEX = "remove\\s+-p\\s+(.*?)\\s+-e\\s+(.*)";
    private static final String REMOVE_ITEM_REGEX = "remove\\s+-m\\s+(.*?)\\s+-e\\s+(.*)";
    private static final String EDIT_EVENT_ATTRIBUTE_REGEX = "edit\\s+-e\\s+(.*?)\\s+" +
            "-name\\s+(.*?)\\s+-t\\s+(.*?)\\s+-v\\s+(.*?)\\s+-u\\s+(.*)";
    private static final String EDIT_PARTICIPANT_REGEX = "edit\\s+-p\\s+(.*?)\\s+-name\\s+(.*?)" +
            "-email\\s+(.*?)\\s+-e\\s+(.*)";
    private static final String EDIT_ITEM_REGEX = "edit\\s+-m\\s+(.*?)\\s+-e\\s+(.*)";
    private static final String VIEW_REGEX = "view\\s+-e\\s+(.*?)\\s+-y\\s+(.*)";
    private static final String MARK_EVENT_REGEX = "mark\\s+-e\\s+(.*?)\\s+-s\\s+(.*)";
    private static final String MARK_PARTICIPANT_REGEX = "mark\\s+-p\\s+(.*?)\\s+-e\\s+(.*?)\\s+-s\\s+(.*)";
    private static final String MARK_ITEM_REGEX = "mark\\s+-m\\s+(.*?)\\s+-e\\s+(.*?)\\s+-s\\s+(.*)";
    private static final String COPY_REGEX = "copy\\s+(.*?)\\s\\>\\s+(.*)";
    private static final String SORT_REGEX = "sort\\s+-by\\s+(.*)";
    private static final String FILTER_REGEX = "filter\\s+(-[e|d|t|x|u])\\s(.*)";
    private static final String FIND_REGEX = "find\\s+-e\\s+(.*?)\\s+-p\\s+(.*)";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");

    private final Logger logger;

    /**
     * Constructs a new Parser.
     */
    public Parser() {
        logger = Logger.getLogger(Parser.class.getName());
        logger.setUseParentHandlers(false);
    }

    /**
     * Returns a command based on the given user command string.
     *
     * @param command The given command string from the user.
     * @throws InvalidCommandException if the given command string cannot be parsed to a valid command.
     * @throws IOException if the log file cannot be written to.
     */
    public Command parseCommand(String command) throws InvalidCommandException, IOException {
        Matcher matcher = ASCII_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new InvalidCommandException(NON_ENGLISH_MESSAGE);
        }
        String[] commandParts = command.trim().split(SPACE);
        command = command.trim();
        String commandWord = commandParts[0].toLowerCase();
        try {
            switch (commandWord) {
            case MenuCommand.COMMAND_WORD:
                return new MenuCommand();
            case ListCommand.COMMAND_WORD:
                return new ListCommand();
            case AddCommand.COMMAND_WORD:
                return parseAddCommand(command, commandParts);
            case RemoveCommand.COMMAND_WORD:
                return parseRemoveCommand(command, commandParts);
            case EditParticipantCommand.COMMAND_WORD:
                return parseEditCommand(command, commandParts);
            case ViewCommand.COMMAND_WORD:
                return parseViewCommand(command, commandParts);
            case MarkCommand.COMMAND_WORD:
                return parseMarkCommand(command, commandParts);
            case CopyCommand.COMMAND_WORD:
                return parseCopyCommand(command, commandParts);
            case SortCommand.COMMAND_WORD:
                return parseSortCommand(command, commandParts);
            case FilterCommand.COMMAND_WORD:
                return parseFilterCommand(command, commandParts);
            case FindCommand.COMMAND_WORD:
                return parseFindCommand(command, commandParts);
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();
            default:
                throw new InvalidCommandException(INVALID_COMMAND_MESSAGE);
            }
        } catch (IndexOutOfBoundsException exception) {
            logWarning("Invalid command format");
            String errorMessage = getErrorMessage(commandWord);
            throw new InvalidCommandException(errorMessage);
        } catch (ParseException exception) {
            logWarning("Invalid date-time format");
            throw new InvalidCommandException(INVALID_DATE_TIME_MESSAGE);
        } catch (IllegalArgumentException exception) {
            logWarning("Invalid priority level status");
            throw new InvalidCommandException(INVALID_PRIORITY_MESSAGE);
        } catch (IOException exception) {
            throw new IOException("Log file cannot be written to");
        }
    }

    //@@author LTK-1606
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
     * @throws InvalidCommandException   if the command flag is invalid, or if there are improperly
     *                                   formatted input details
     * @throws IndexOutOfBoundsException if not all parameters are present.
     * @throws DateTimeParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     * @throws IOException if the log file cannot be written to.
     */
    public Command parseAddCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException, ParseException, IllegalArgumentException, IOException {
        assert commandParts[0].equalsIgnoreCase(AddCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getAddEventCommand(input);
        case PARTICIPANT_FLAG:
            return getAddParticipantCommand(input);
        case ITEM_FLAG:
            return getAddItemCommand(input);
        default:
            logWarning("Invalid command format");
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        }
    }

    /**
     * Returns an {@link AddCommand} that adds an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws ParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getAddEventCommand(String input) throws IndexOutOfBoundsException, ParseException,
            IllegalArgumentException, IOException {
        checkForDuplicateFlags(input, EVENT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, ADD_EVENT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()
                || matcher.group(3).isBlank() || matcher.group(4).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        logInfo("Creating AddCommand for event with details: " +
                matcher.group(1).trim() + ", " + matcher.group(2).trim() + ", " + matcher.group(3).trim());

        String eventName = matcher.group(1).trim();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateTimeFormat.setLenient(false);
        Date parsedDateTime = dateTimeFormat.parse(matcher.group(2).trim());
        LocalDateTime eventTime = LocalDateTime.ofInstant(parsedDateTime.toInstant(), ZoneId.systemDefault());
        String venue = matcher.group(3).trim();
        Priority eventPriority = Priority.valueOf(matcher.group(4).trim().toUpperCase());

        if (eventTime.isBefore(LocalDateTime.now())) {
            throw new InvalidCommandException(PAST_DATE_MESSAGE);
        }

        return new AddCommand(eventName, eventTime, venue, eventPriority);
    }

    //@@author LTK-1606
    /**
     * Returns an {@link AddCommand} that adds a participant with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws InvalidCommandException   if the input phone number and email are not in the correct format.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getAddParticipantCommand(String input) throws IndexOutOfBoundsException, InvalidCommandException,
            IOException {
        checkForDuplicateFlags(input, PARTICIPANT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, ADD_PARTICIPANT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()
                || matcher.group(3).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        logInfo("Creating AddCommand for participant with details: " +
                matcher.group(1).trim() + ", " + matcher.group(2).trim());
        String participantName = matcher.group(1).trim();
        String participantEmail = matcher.group(2).trim();
        String eventName = matcher.group(3).trim();

        if (!isValidEmail(participantEmail)) {
            logWarning("Invalid email format");
            throw new InvalidCommandException(INVALID_EMAIL_MESSAGE);
        }

        return new AddCommand(participantName, participantEmail, eventName);
    }

    //@@author jemehgoh
    /**
     * Returns an {@link AddCommand} that adds an item with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link AddCommand} that adds an item with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getAddItemCommand(String input) throws IndexOutOfBoundsException, IOException {
        checkForDuplicateFlags(input, ITEM_FLAG_REGEX);
        Matcher matcher = getMatcher(input, ADD_ITEM_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_ADD_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String itemName = matcher.group(1).trim();
        String eventName = matcher.group(2).trim();
        logInfo(String.format("Creating AddCommand for item with details: %s, %s", itemName,
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
     * @throws InvalidCommandException   if the flags are not matched in the command parts.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command parseRemoveCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException, IOException {
        assert commandParts[0].equalsIgnoreCase(RemoveCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getRemoveEventCommand(input);
        case PARTICIPANT_FLAG:
            return getRemoveParticipantCommand(input);
        case ITEM_FLAG:
            return getRemoveItemCommand(input);
        default:
            logWarning("Invalid command format");
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
        checkForDuplicateFlags(input, REMOVE_EVENT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, REMOVE_EVENT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        }

        if (matcher.group(1).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        return new RemoveCommand(matcher.group(1).trim());
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
        checkForDuplicateFlags(input, REMOVE_PARTICIPANT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, REMOVE_PARTICIPANT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        return new RemoveCommand(matcher.group(1).trim(), matcher.group(2).trim(), true);
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
        checkForDuplicateFlags(input, ITEM_FLAG_REGEX);
        Matcher matcher = getMatcher(input, REMOVE_ITEM_REGEX);

        if (matcher.matches()) {
            if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
                throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
            }

            return new RemoveCommand(matcher.group(1).trim(), matcher.group(2).trim(), false);
        } else {
            throw new InvalidCommandException(INVALID_REMOVE_MESSAGE);
        }
    }

    /**
     * Parses the input string to create a Command object based on the provided command parts.
     * <p>
     * This method checks the command flag extracted from the command parts. If the command
     * flag is "-e", it splits the input string to create an EditCommand
     *
     * @return a Command object representing the parsed command.
     * @throws InvalidCommandException if the flags are not matched in the command parts.
     * @throws ParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     */
    private Command parseEditCommand(String input, String[] commandParts) throws InvalidCommandException,
            IOException, ParseException {
        assert commandParts[0].equalsIgnoreCase(EditParticipantCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getEditEventCommand(input);
        case PARTICIPANT_FLAG:
            return getEditParticipantCommand(input);
        case ITEM_FLAG:
            return getEditItemCommand(input);
        default:
            logWarning("Invalid command format");
            throw new InvalidCommandException(INVALID_EDIT_MESSAGE);
        }
    }

    //@@author MatchaRRR
    /**
     * Returns an {@link EditEventCommand} that edits an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link EditEventCommand} that edits an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws ParseException    if the time parameter is not entered in the correct format.
     * @throws IllegalArgumentException  if the priority parameter is not valid.
     */
    private Command getEditEventCommand(String input) throws IndexOutOfBoundsException, ParseException,
            IllegalArgumentException {
        checkForDuplicateFlags(input, EDIT_EVENT_ATTRIBUTE_FLAG_REGEX);
        Matcher matcher = getMatcher(input, EDIT_EVENT_ATTRIBUTE_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_EDIT_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()
                || matcher.group(3).isBlank() || matcher.group(4).isBlank() || matcher.group(5).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String eventName = matcher.group(1).trim();
        String eventNewName = matcher.group(2).trim();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateTimeFormat.setLenient(false);
        Date parsedDateTime = dateTimeFormat.parse(matcher.group(3).trim());
        LocalDateTime eventTime = LocalDateTime.ofInstant(parsedDateTime.toInstant(), ZoneId.systemDefault());
        String eventVenue = matcher.group(4).trim();
        Priority eventPriority = Priority.valueOf(matcher.group(5).trim().toUpperCase());

        if (eventTime.isBefore(LocalDateTime.now())) {
            throw new InvalidCommandException(PAST_DATE_MESSAGE);
        }

        return new EditEventCommand(eventName, eventNewName, eventTime, eventVenue, eventPriority);
    }

    //@@author KuanHsienn
    /**
     * Returns an {@link EditParticipantCommand} that edits a participant with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link EditParticipantCommand} that edits a participant with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getEditParticipantCommand(String input) throws IndexOutOfBoundsException, InvalidCommandException,
            IOException {
        checkForDuplicateFlags(input, PARTICIPANT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, EDIT_PARTICIPANT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_EDIT_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()
                || matcher.group(3).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String participantName = matcher.group(1).trim();
        String newParticipantName = matcher.group(2).trim();
        String newEmail = matcher.group(3).trim();
        String eventName = matcher.group(4).trim();

        if (!isValidEmail(newEmail)) {
            logWarning("Invalid email format");
            throw new InvalidCommandException(INVALID_EMAIL_MESSAGE);
        }

        return new EditParticipantCommand(participantName, newParticipantName, newEmail, eventName);
    }

    //@@author MatchaRRR
    /**
     * Returns an {@link EditItemCommand} that edits an event with fields parsed from a given user input.
     *
     * @param input the given user input.
     * @return an {@link EditEventCommand} that edits an event with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command getEditItemCommand(String input) {
        checkForDuplicateFlags(input, ITEM_FLAG_REGEX);
        Matcher matcher = getMatcher(input, EDIT_ITEM_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_EDIT_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String itemName = matcher.group(1).split(ARROW)[0].trim();
        String itemNewName = matcher.group(1).split(ARROW)[1].trim();
        String eventName = matcher.group(2).trim();

        return new EditItemCommand(itemName, itemNewName, eventName);
    }

    //@@author KuanHsienn
    /**
     * Checks if the email address is valid.
     *
     * @param email the email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
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
     * @throws InvalidCommandException   if the flag is not matched.
     * @throws IndexOutOfBoundsException if not all fields are present.
     */
    private Command parseViewCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException, IOException {
        assert commandParts[0].equalsIgnoreCase(ViewCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        if (commandFlag.equals(EVENT_FLAG)) {
            return getViewCommand(input);
        }

        logWarning("Invalid command format");
        throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
    }

    //@@author jemehgoh
    /**
     * Returns a {@link ViewCommand} with fields parsed from a given user input.
     *
     * @param input the user input to be parsed.
     * @return a {@link ViewCommand} with fields parsed from input.
     * @throws IndexOutOfBoundsException if not all fields are present in input.
     * @throws InvalidCommandException   if the status parameter in input is invalid.
     */
    private ViewCommand getViewCommand(String input) throws IndexOutOfBoundsException, InvalidCommandException {
        checkForDuplicateFlags(input, VIEW_FLAG_REGEX);
        Matcher matcher = getMatcher(input, VIEW_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_VIEW_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String eventName = matcher.group(1).trim();
        String viewType = matcher.group(2).trim();

        if (viewType.equalsIgnoreCase("participant")) {
            return new ViewCommand(eventName, true);
        } else if (viewType.equalsIgnoreCase("item")) {
            return new ViewCommand(eventName, false);
        } else {
            throw new InvalidCommandException(INVALID_TYPE_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkCommand} to mark an event, participant or item based on a given input string
     * and command parts.
     *
     * @param input        the input string containing the command details.
     * @param commandParts an array of strings representing the parsed command parts,
     *                     where the second element is the command flag.
     * @return a {@link MarkCommand} with fields parsed from input.
     * @throws InvalidCommandException   if the flag is not matched, or if the mark status is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command parseMarkCommand(String input, String[] commandParts) throws InvalidCommandException,
            IndexOutOfBoundsException, IOException {
        assert commandParts[0].equalsIgnoreCase(MarkCommand.COMMAND_WORD);
        String commandFlag = commandParts[1];

        switch (commandFlag) {
        case EVENT_FLAG:
            return getMarkEventCommand(input);
        case PARTICIPANT_FLAG:
            return getMarkParticipantCommand(input);
        case ITEM_FLAG:
            return getMarkItemCommand(input);
        default:
            logWarning("Invalid command format");
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkEventCommand} with fields from a given user input.
     *
     * @param input the given user input.
     * @return a {@link MarkEventCommand} with fields from input.
     * @throws InvalidCommandException   if the status parameter is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getMarkEventCommand(String input) throws InvalidCommandException, IndexOutOfBoundsException,
            IOException {
        checkForDuplicateFlags(input, MARK_EVENT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, MARK_EVENT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String eventName = matcher.group(1).trim();
        boolean isToMark = toMarkEvent(matcher.group(2).trim());

        return new MarkEventCommand(eventName, isToMark);
    }

    /**
     * Returns true if status is to mark, returns false if status is to unmark.
     *
     * @param status the status parameter.
     * @return true if status is to mark, returns false if status is to unmark.
     * @throws InvalidCommandException if status is invalid.
     * @throws IOException if the log file cannot be written to.
     */
    private boolean toMarkEvent(String status) throws InvalidCommandException, IOException {
        if (status.equalsIgnoreCase(MarkEventCommand.EVENT_MARK_STATUS)) {
            return true;
        } else if (status.equalsIgnoreCase(MarkEventCommand.EVENT_UNMARK_STATUS)) {
            return false;
        } else {
            logWarning("Invalid status keyword");
            throw new InvalidCommandException(INVALID_EVENT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkParticipantCommand} with fields from a given user input.
     *
     * @param input the given user input.
     * @return a {@link MarkParticipantCommand} with fields from input.
     * @throws InvalidCommandException   if the status parameter is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getMarkParticipantCommand(String input) throws InvalidCommandException, IndexOutOfBoundsException,
            IOException {
        checkForDuplicateFlags(input, MARK_PARTICIPANT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, MARK_PARTICIPANT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank() || matcher.group(3).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String participantName = matcher.group(1).trim();
        String eventName = matcher.group(2).trim();
        boolean isToMark = toMarkParticipant(matcher.group(3).trim());

        return new MarkParticipantCommand(participantName, eventName, isToMark);
    }

    /**
     * Returns true if status is to mark, returns false if status is to unmark.
     *
     * @param status the status parameter.
     * @return true if status is to mark, returns false if status is to unmark.
     * @throws InvalidCommandException if status is invalid.
     * @throws IOException if the log file cannot be written to.
     */
    private boolean toMarkParticipant(String status) throws InvalidCommandException, IOException {
        if (status.equalsIgnoreCase(MarkParticipantCommand.PARTICIPANT_MARK_STATUS)) {
            return true;
        } else if (status.equalsIgnoreCase(MarkParticipantCommand.PARTICIPANT_UNMARK_STATUS)) {
            return false;
        } else {
            logWarning("Invalid status keyword");
            throw new InvalidCommandException(INVALID_PARTICIPANT_STATUS_MESSAGE);
        }
    }

    /**
     * Returns a {@link MarkItemCommand} with fields from a given user input.
     *
     * @param input the given user input.
     * @return a {@link MarkItemCommand} with fields from input.
     * @throws InvalidCommandException   if the status parameter is invalid.
     * @throws IndexOutOfBoundsException if not all fields are present.
     * @throws IOException if the log file cannot be written to.
     */
    private Command getMarkItemCommand(String input) throws InvalidCommandException, IndexOutOfBoundsException,
            IOException {
        checkForDuplicateFlags(input, MARK_ITEM_FLAG_REGEX);
        Matcher matcher = getMatcher(input, MARK_ITEM_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_MARK_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank() || matcher.group(3).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        String itemName = matcher.group(1).trim();
        String eventName = matcher.group(2).trim();
        boolean isToMark = toMarkItem(matcher.group(3).trim());

        return new MarkItemCommand(itemName, eventName, isToMark);
    }

    /**
     * Returns true if status is "accounted", returns false if status is "unaccounted".
     *
     * @param status the status parameter.
     * @return true if status is "accounted", returns false if status is "unaccounted".
     * @throws InvalidCommandException if status is invalid.
     * @throws IOException if the log file cannot be written to.
     */
    private boolean toMarkItem(String status) throws InvalidCommandException, IOException {
        if (status.equalsIgnoreCase(MarkItemCommand.ITEM_MARK_STATUS)) {
            return true;
        } else if (status.equalsIgnoreCase(MarkItemCommand.ITEM_UNMARK_STATUS)) {
            return false;
        } else {
            logWarning("Invalid status keyword");
            throw new InvalidCommandException(INVALID_ITEM_STATUS_MESSAGE);
        }
    }

    /**
     * Parses the input command to create a {@code CopyCommand} object.
     * <p>
     * This method checks if the command input starts with the specified command word
     * and then removes it from the input. It splits the remaining input at the '>' character
     * to separate the source and destination parts. If the split does not yield exactly
     * two parts, an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input        the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code CopyCommand} object with the parsed source and destination
     * @throws InvalidCommandException if the command is missing required parts or has an invalid format
     */
    private Command parseCopyCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(CopyCommand.COMMAND_WORD);
        checkForDuplicateFlags(input, COPY_FLAG_REGEX);
        Matcher matcher = getMatcher(input, COPY_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_COPY_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        return new CopyCommand(matcher.group(1).trim(), matcher.group(2).trim());
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
    private Command parseSortCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(SortCommand.COMMAND_WORD);
        checkForDuplicateFlags(input, SORT_FLAG_REGEX);
        Matcher matcher = getMatcher(input, SORT_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_SORT_MESSAGE);
        }

        if (matcher.group(1).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        if (!matcher.group(1).equals("time") && !matcher.group(1).equals("name") &&
                !matcher.group(1).equals("priority")) {
            throw new InvalidCommandException(INVALID_SORT_KEYWORD_MESSAGE);
        }

        return new SortCommand(matcher.group(1).trim());
    }

    //@@author LTK-1606
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
        checkForDuplicateFlags(input, FILTER_FLAG_REGEX);
        Matcher matcher = getMatcher(input, FILTER_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
        }

        if (matcher.group(1).isBlank()) {
            throw new InvalidCommandException(INVALID_FILTER_MESSAGE);
        }

        if (matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        return new FilterCommand(matcher.group(1).trim(), matcher.group(2).trim());
    }

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
     * @param input        the full command input string to be parsed
     * @param commandParts the parts of the command, typically split by whitespace
     * @return a {@code FindCommand} object with the parsed event and person names
     * @throws InvalidCommandException if the command is missing required flags or has an invalid format
     */
    private Command parseFindCommand(String input, String[] commandParts) throws InvalidCommandException {
        assert commandParts[0].equalsIgnoreCase(FindCommand.COMMAND_WORD);
        checkForDuplicateFlags(input, FIND_FLAG_REGEX);
        Matcher matcher = getMatcher(input, FIND_REGEX);

        if (!matcher.matches()) {
            throw new InvalidCommandException(INVALID_FIND_MESSAGE);
        }

        if (matcher.group(1).isBlank() || matcher.group(2).isBlank()) {
            throw new InvalidCommandException(EMPTY_INPUT_MESSAGE);
        }

        if (matcher.groupCount() < 2 || matcher.group(1).isBlank()) {
            throw new InvalidCommandException(INVALID_FIND_MESSAGE);
        }

        return new FindCommand(matcher.group(1).trim(), matcher.group(2).trim());
    }

    //@@author jemehgoh
    /**
     * Returns an error message corresponding to the given command word.
     *
     * @param commandWord the command word entered.
     * @return an error message corresponding to commandWord.
     */
    private String getErrorMessage(String commandWord) {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return INVALID_ADD_MESSAGE;
        case RemoveCommand.COMMAND_WORD:
            return INVALID_REMOVE_MESSAGE;
        case EditEventCommand.COMMAND_WORD:
            return INVALID_EDIT_MESSAGE;
        case ViewCommand.COMMAND_WORD:
            return INVALID_VIEW_MESSAGE;
        case MarkCommand.COMMAND_WORD:
            return INVALID_MARK_MESSAGE;
        default:
            return INVALID_COMMAND_MESSAGE;
        }
    }

    /**
     * Logs an info message to a file.
     *
     * @param message the message to be logged.
     * @throws IOException if the log file cannot be written to.
     */
    private void logInfo(String message) throws IOException {
        FileHandler handler = new FileHandler("logs.txt", true);
        logger.addHandler(handler);
        logger.info(message);
        handler.close();
    }

    /**
     * Logs a warning message to a file.
     *
     * @param message the message to be logged.
     * @throws IOException if the log file cannot be written to.
     */
    private void logWarning(String message) throws IOException {
        FileHandler handler = new FileHandler("logs.txt", true);
        logger.addHandler(handler);
        logger.warning(message);
        handler.close();
    }

    //@@author LTK-1606
    /**
     * Checks for duplicate flags in the specified input string based on the provided flag regex.
     * <p>
     * This method uses a regular expression pattern to match flags within the input string.
     * If any flag appears more than once, an {@code InvalidCommandException} is thrown.
     * </p>
     *
     * @param input     The input string to be checked for duplicate flags.
     * @param flagRegex The regular expression pattern used to identify flags in the input string.
     * @throws InvalidCommandException if a duplicate flag is found in the input string.
     */
    private void checkForDuplicateFlags(String input, String flagRegex) throws InvalidCommandException {
        Pattern flagPattern = Pattern.compile(flagRegex);
        Matcher flagMatcher = flagPattern.matcher(input);

        Set<String> seenFlags = new HashSet<>();

        while (flagMatcher.find()) {
            String flag = flagMatcher.group();

            if (!seenFlags.add(flag)) {
                throw new InvalidCommandException(DUPLICATE_FLAG_MESSAGE);
            }
        }
    }

    /**
     * Gets a {@link Matcher} from a given input string and regex.
     *
     * @param input the given input string.
     * @param regex the given regex.
     * @return a {@link Matcher} for input and regex.
     */
    private Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
