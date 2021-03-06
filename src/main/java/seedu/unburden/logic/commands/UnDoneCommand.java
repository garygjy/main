package seedu.unburden.logic.commands;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.model.task.ReadOnlyTask;

/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 * 
 * @@author A0143095H
 */
public class UnDoneCommand extends Command {

	public static final String COMMAND_WORD = "undone";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Marks the task identified by the index number used in the last task listing as undone.\n"
			+ "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Task Marked as Undone";

	public final int targetIndex;

	public UnDoneCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute(){

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);

		model.saveToPrevLists();
		model.doneTask(taskToDone, false);
		return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, taskToDone));
	}
}
