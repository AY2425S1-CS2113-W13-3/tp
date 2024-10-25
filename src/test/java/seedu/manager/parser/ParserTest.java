package seedu.manager.parser;

import org.junit.jupiter.api.Test;
import seedu.manager.command.AddCommand;
import seedu.manager.command.Command;
import seedu.manager.command.ExitCommand;
import seedu.manager.command.MarkEventCommand;
import seedu.manager.command.MarkParticipantCommand;
import seedu.manager.command.MenuCommand;
import seedu.manager.command.ListCommand;
import seedu.manager.command.RemoveCommand;
import seedu.manager.exception.InvalidCommandException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    public void parseCommand_invalidString_throwsException() {
        Parser parser = new Parser();
        String commandString = "Hello world!";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void parseCommand_exitWord_exit() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("exit");

        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void parseCommand_menuWord_menu() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("menu");

        assertInstanceOf(MenuCommand.class, command);
    }

    @Test
    public void parseCommand_listWord_list() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("list");

        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void addCommand_addEvent_add() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("add -e event -t 2024-09-10 -v Venue A");

        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void addCommand_addNoParameter_invalid() {
        Parser parser = new Parser();
        String commandString = "add";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void removeCommand_removeEvent_add() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("remove -e event");

        assertInstanceOf(RemoveCommand.class, command);
    }

    @Test
    public void removeCommand_removeNoParameter_invalid() {
        Parser parser = new Parser();
        String commandString = "remove";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void parseCommand_markEvent_mark() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("mark -e event -s done");

        assertInstanceOf(MarkEventCommand.class, command);
    }

    @Test
    public void parseCommand_markEventNoStatus_throwsException() {
        Parser parser = new Parser();
        String commandString = "mark -e event";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void parseCommand_markEventInvalidStatus_throwsException() {
        Parser parser = new Parser();
        String commandString = "mark -e event -s yes";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void parseCommand_markParticipantPresent_mark() {
        Parser parser = new Parser();
        String commandString = "mark -p John Doe -e event -s present";
        Command command = parser.parseCommand(commandString);

        assertInstanceOf(MarkParticipantCommand.class, command);
    }

    @Test
    public void parseCommand_markParticipantAbsent_mark() {
        Parser parser = new Parser();
        String commandString = "mark -p John Doe -e event -s absent";
        Command command = parser.parseCommand(commandString);

        assertInstanceOf(MarkParticipantCommand.class, command);
    }

    @Test
    public void parseCommand_markParticipantNoStatus_throwsException() {
        Parser parser = new Parser();
        String commandString = "mark -p John Doe -e event";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void parseCommand_markParticipantInvalidStatus_throwsException() {
        Parser parser = new Parser();
        String commandString = "mark -p John Doe -e event -s done";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }

    @Test
    public void parseCommand_markInvalidFlags_throwsException() {
        Parser parser = new Parser();
        String commandString = "mark -s done";

        assertThrows(InvalidCommandException.class,() -> {
            parser.parseCommand(commandString);});
    }
}
