package seedu.unburden.model.task;

import seedu.unburden.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's descriptions in the task manager
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskD(String)}
 */

public class TaskDescription {
	private static final String MESSAGE_TASKD_CONSTRAINTS = "Task descriptions should be spaces or alphanumeric characters.";
	public static final String TASKD_VALIDATION_REGEX = ".+";
	
	public final String fullTaskD;

	/**
	 * 
	 * @param details
	 *            Validates the given String as a task description
	 * @throws IllegalValueException
	 *             if the string passed in is invalid
	 */
	public TaskDescription(String taskdescriptions) throws IllegalValueException {
		assert taskdescriptions != null;
		taskdescriptions = taskdescriptions.trim();
		if (isValidTaskD(taskdescriptions)) {
			throw new IllegalValueException(MESSAGE_TASKD_CONSTRAINTS);
		}
		this.fullTaskD = taskdescriptions;
	}

	private boolean isValidTaskD(String taskdescriptions) {
		return taskdescriptions.matches(TASKD_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return fullTaskD;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof TaskDescription // instanceof handles nulls
						&& this.fullTaskD.equals(((TaskDescription) other).fullTaskD)); 
	}

	@Override
	public int hashCode() {
		return fullTaskD.hashCode();
	}

}
