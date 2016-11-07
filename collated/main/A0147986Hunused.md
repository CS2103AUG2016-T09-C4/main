# A0147986Hunused
###### \src\main\java\seedu\unburden\commons\util\StringUtil.java
``` java
/**
 * Helper functions for handling strings.
 * 
 */
public class StringUtil {
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }
    
    public static boolean containsDate(String source, String query){
    	List<String> strings = new ArrayList<String>(Arrays.asList(source));
    	return strings.stream().filter(s -> s.equals(query)).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false for null, empty string, "-1", "0", "+1", and " 2 " 
     * (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
    
    public static String getTaskDetails(ArrayList<ReadOnlyTask> taskList){
    	StringBuilder resultedList=new StringBuilder();
    	for(ReadOnlyTask p:taskList){
    		resultedList.append(p.toString());
    		resultedList.append("\n\n");
    	}
    	return resultedList.toString();
    }
    
}
```
###### \src\main\java\seedu\unburden\logic\commands\SelectCommand.java
``` java
/**
 * Selects a task identified using it's last displayed index from the address book.
 * It will also show the details of the task selected, especially
 * task descriptions that may not be able to show on the taskList panel
 */
public class SelectCommand extends Command {

    public final int taskIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer that does not exceed the maximum number of tasks)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(taskIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownList.get(taskIndex-1)));
    }
}
```
###### \src\main\java\seedu\unburden\logic\parser\Parser.java
``` java
		private static final Pattern INDEX_PHASE_FORMAT = Pattern.compile("(?<targetIndex>\\d+-\\d+)");
		
		private static final Pattern INDEX_LIST_FORMAT = Pattern.compile("(?<targetIndex>\\d+(\\s+\\d+)*)");
		
```
###### \src\main\java\seedu\unburden\logic\parser\Parser.java
``` java
	/**
	 * Parses arguments in the context of the delete person command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareDelete(String args) throws ParseException {

		final Matcher matcherList = INDEX_LIST_FORMAT.matcher(args.trim());
		final Matcher matcherPhase = INDEX_PHASE_FORMAT.matcher(args.trim());  

		if(!matcherList.matches()&&!matcherPhase.matches()){

			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));		

		}

		if(matcherPhase.matches()){

			String indexes_phase = matcherPhase.group("targetIndex");

			String[] SeperateIndexes_phase = indexes_phase.trim().split("-");

			ArrayList<Integer> sortList = new ArrayList<> ();

			ArrayList<Integer> indexesInt_phase = new ArrayList<> ();

			Optional<Integer> index_list = parseIndex(SeperateIndexes_phase[0]);

			if (!index_list.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
			}			

			Optional<Integer> index_list2 = parseIndex(SeperateIndexes_phase[1]);

			if (!index_list2.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
			}

			sortList.add(Integer.parseInt(SeperateIndexes_phase[0]));
			sortList.add(Integer.parseInt(SeperateIndexes_phase[1]));

			Collections.sort(sortList);

			for(int i= sortList.get(0); i<=sortList.get(1); i++){     

				indexesInt_phase.add(i);

				System.out.print(SeperateIndexes_phase[0] + SeperateIndexes_phase[1]);	

			}

			Collections.sort(indexesInt_phase);

			return new DeleteCommand(indexesInt_phase);

		}

		else if(matcherList.matches()){


			String indexes_list = matcherList.group("targetIndex");     

			String[] SeperateIndexes_list = indexes_list.split(" ");
			
			ArrayList<Integer> indexesInt_list = new ArrayList<> (); 
									        
			for(int i=0; i<(SeperateIndexes_list.length); i++){

				Optional<Integer> index_list = parseIndex(SeperateIndexes_list[i]);

				if (!index_list.isPresent()) {
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
				}
				indexesInt_list.add(Integer.parseInt(SeperateIndexes_list[i]));
			}	
			
			indexesInt_list = (ArrayList<Integer>) indexesInt_list.stream().distinct().collect(Collectors.toList());

			Collections.sort(indexesInt_list);

			return new DeleteCommand(indexesInt_list); 
		}

		else

			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));	

	}

	/**
	 * Parses arguments in the context of the select task command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareSelect(String args) {

		Optional<Integer> index_select = parseIndex(args);
		if (!index_select.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}

		return new SelectCommand(index_select.get());
	}

	
	
```
###### \src\main\java\seedu\unburden\model\task\ReadOnlyTask.java
``` java
	/**
	 * Formats the task as text, showing all contact details.
	 */
	default String getAsText() {
		final StringBuilder builder = new StringBuilder();

		// Floating task
		if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() == ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Floating task with task description
		else if (getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() == ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}

		}

		// Task with deadline
		else if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline and task description
		else if (getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() == "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline and end date
		else if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() != "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("End time : ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline and end date and task description
		else if (getTaskDescription().getFullTaskDescription() != "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() == "" && getEndTime().getFullTime() != "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("End time : ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline, start time and end time
		else if (getTaskDescription().getFullTaskDescription() == "" && getDate().getFullDate() != ""
				&& getStartTime().getFullTime() != "" && getEndTime().getFullTime() != "") {
			builder.append(getName());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("Start Time - End time : ");
			builder.append(getStartTime() + " - ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		// Task with deadline, task description, start time and end time
		else {
			builder.append(getName());
			builder.append("\n");
			builder.append("Task Description : ");
			builder.append(getTaskDescription());
			builder.append("\n");
			builder.append("Deadline : ");
			builder.append(getDate());
			builder.append("\n");
			builder.append("Start Time - End time : ");
			builder.append(getStartTime() + " - ");
			builder.append(getEndTime() + "   ");
			if (!getTags().isEmpty()) {
				builder.append("\n");
				builder.append("Tags : ");
				builder.append("\n");
				getTags().forEach(builder::append);
			}
		}

		return builder.toString();
	}

	/**
	 * Returns a string representation of this Task's tags
	 */
	default String tagsString() {
		final StringBuffer buffer = new StringBuffer();
		final String separator = ", ";
		getTags().forEach(tag -> buffer.append(tag).append(separator));
		if (buffer.length() == 0) {
			return "";
		} else {
			return buffer.substring(0, buffer.length() - separator.length());
		}
	}

}
```
###### \src\test\java\seedu\unburden\logic\LogicManagerTest.java
``` java
	/**
	 * test the select command
	 * 
	 */
	@Test
	public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
	}

	@Test
	public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("select");
	}
	
	@Test
	public void execute_select_jumpsToCorrectPerson() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Task> threeTasks = helper.generateTaskList(3);

		ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
		helper.addToModel(model, threeTasks);

		assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, threeTasks.get(1)), expectedAB,
				expectedAB.getTaskList());
		assertEquals(1, targetedJumpIndex);
		assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
	}
	
```
###### \src\test\java\seedu\unburden\logic\LogicManagerTest.java
``` java
	/**test the multiple delete command. 
	 * test both reverse indexes and any kind 
	 * if format 
	 * @throws Exception
	 */
	@Test
	public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
	}
	
	@Test
	public void execute_deleteInvalidArgsFormat_errorMessageShownZero() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("delete 0", expectedMessage);
	}

	@Test
	public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("delete");
	}	

	@Test
	public void execute_delete_removesCorrectTask() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Task> threeTasks = helper.generateTaskList(3);

		ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(1));
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);

		assertCommandBehavior("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB, expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesCorrectTaskWithDuplicate() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Task> threeTasks = helper.generateTaskList(3);

		ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(1));
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);

		assertCommandBehavior("delete 2 2 2 2 2 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB, expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesCorrectTaskWithDuplicate2() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Task> threeTasks = helper.generateTaskList(3);

		ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(1));
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);

		assertCommandBehavior("delete 2-2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB, expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesMultipleTasks() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 1 2 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesMultipleTasksWithZero() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 01 02 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesMultipleTasksWithDuplicate() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 1 2 1",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesMultipleTasksReverse() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 2 1 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}
	
	@Test
	public void execute_delete_removesMultipleTasksReverseWithZero() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 02 01 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}
	
	
	@Test
	public void execute_delete_removesMultipleTasksWithDash() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 1-2 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}

	@Test
	public void execute_delete_removesMultipleTasksWithDashReverse() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 2-1 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}

	@Test
	public void execute_delete_removesMultipleTasksWithDashReverseWithZero() throws Exception{
		TestDataHelper helper=new TestDataHelper();
		List<Task> threeTasks=helper.generateTaskList(3);
		ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
		
		ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
		expectedAB.removeTask(threeTasks.get(0));
		expectedAB.removeTask(threeTasks.get(1));
		deletedTasks.add(threeTasks.get(0));
		deletedTasks.add(threeTasks.get(1));
		helper.addToModel(model, threeTasks);
		
		assertCommandBehavior("delete 02-01 ",String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
				expectedAB,expectedAB.getTaskList());
	}
			
	
```
