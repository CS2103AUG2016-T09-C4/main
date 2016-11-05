package seedu.unburden.logic.commands;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Deletes a task or a set of tasks identified 
 * using it's last displayed index from the address book.
 * Author@@ A0147986H
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final String targetIndexes;
    
    public final int targetIndex;
    
    public final String mode;
    
    public final int targetIndex_low,targetIndex_high;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetIndexes = null;
        this.mode = "index";
        this.targetIndex_low = 0;
        this.targetIndex_high = 0;
    }

    public DeleteCommand(String targetIndexes) {
    	this.targetIndex = 0;
        this.targetIndexes = targetIndexes;
        this.mode = "indexes";
        this.targetIndex_low = 0;
        this.targetIndex_high = 0;
    }
    
    @Override
    public CommandResult execute() throws DuplicateTagException, IllegalValueException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if(this.mode == "index"){
        	 ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

             try {
            	model.saveToPrevLists();
                 model.deleteTask(taskToDelete);
             } catch (TaskNotFoundException pnfe) {
                 assert false : "The target task cannot be missing";
             }
             return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
         }  
        	
        if(this.mode =="indexes"){
              for(int i=targetIndex_low; i<=targetIndex_high; i++) { 
        
        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
       	model.saveToPrevLists();
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        }    
      }
   }
}

