package seedu.unburden.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.commons.util.CollectionUtil;
import seedu.unburden.logic.commands.EditCommand;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        FXCollections.sort(internalList);
    }
    

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        FXCollections.sort(internalList);
        return taskFoundAndDeleted;
    }
    
    //@@Gary Goh A0139714B
    public boolean edit(ReadOnlyTask key, Task toEdit) 
    		throws TaskNotFoundException, IllegalValueException {
    	
    	assert toEdit != null;
    	assert key != null;
    	int taskIndex = internalList.indexOf(key);
    	Task updatedTask = toEdit;
    	Task oldTask = internalList.get(taskIndex);
    	
    	if (updatedTask.getName().getFullName() == "") {
    		updatedTask.setName(oldTask.getName());
    	}
    	
    	if (EditCommand.removeTaskDescription == false && updatedTask.getTaskDescription().getFullTaskDescription() == "") {
    		updatedTask.setTaskDescription(oldTask.getTaskDescription());
    	}
    	
    	if (EditCommand.removeDate == false && updatedTask.getDate().getFullDate() == "") {
    		updatedTask.setDate(oldTask.getDate());
    	}
    	
    	if (EditCommand.removeStartTime == false && updatedTask.getStartTime().getFullTime() == "") {
    		updatedTask.setStartTime(oldTask.getStartTime());
    	}
    	
    	if (EditCommand.removeEndTime == false && updatedTask.getEndTime().getFullTime() == "") {
    		updatedTask.setEndTime(oldTask.getEndTime());
    	}
    	
    	updatedTask.setTags(oldTask.getTags());
    	internalList.set(taskIndex, updatedTask);
    	FXCollections.sort(internalList);
    	
    	return true;
     
    }
    

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    
    
    //@@ Gauri Joshi A0139714B
	public void done(ReadOnlyTask key, boolean isDone) {
		assert key != null;
		int taskIndex = internalList.indexOf(key);
        Task newTask = internalList.get(taskIndex);
        newTask.setDone(isDone);
        internalList.set(taskIndex, newTask);
	}
	
	public void undone(ReadOnlyTask key, boolean isDone) {
		assert key != null;
		int taskIndex = internalList.indexOf(key);
        Task newTask = internalList.get(taskIndex);
        newTask.setDone(isDone);
        internalList.set(taskIndex, newTask);
	}
}
