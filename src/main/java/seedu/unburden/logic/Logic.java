package seedu.unburden.logic;

import java.text.ParseException;

import javafx.collections.ObservableList;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.logic.commands.CommandResult;
import seedu.unburden.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws ParseException 
     * @throws  
     */
    CommandResult execute(String commandText) throws ParseException, IllegalValueException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
