package seedu.unburden.commons.events.storage;

import seedu.unburden.commons.events.BaseEvent;

//@@author A0139714B
public class StoragePathChangedEvent extends BaseEvent {
	
	public String oldStoragePath;
	public String newStoragePath;
	
	public StoragePathChangedEvent(String oldPath, String newPath) {
		this.oldStoragePath = oldPath;
		this.newStoragePath = newPath;
	}
	
	@Override
	public String toString() {
		return "Storage Path has changed."
				+ "Old Path: " + oldStoragePath 
				+ "New Path: " + newStoragePath;
	}
}
