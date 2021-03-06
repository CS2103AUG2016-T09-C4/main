package seedu.unburden.logic.commands;

import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.commons.events.storage.StoragePathChangedEvent;
import seedu.unburden.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws IllegalValueException 
     */
    public abstract CommandResult execute() throws IllegalValueException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectCommandAttemptedEvent(this));
    }
    
    /*
     * raises an event to indicate that the storage path has been changed
     */
    //@@author A0139714B
    protected void indicateStoragePathChange(String oldPath, String newPath) {
    	EventsCenter.getInstance().post(new StoragePathChangedEvent(oldPath, newPath));
    }
}
