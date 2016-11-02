package seedu.unburden.logic.commands;

import java.util.List;

import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList.DuplicateTagException;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;

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

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "Task Marked as Undone";

	public final int targetIndex;

	public UnDoneCommand(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	@Override
	public CommandResult execute() throws DuplicateTagException, IllegalValueException {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);

		model.saveToPrevLists();
		model.doneTask(taskToDone, false);
		overdueOrNot();
		return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDone));
	}

	// This method checks the entire list to check for overdue tasks
	private void overdueOrNot() throws IllegalValueException, DuplicateTagException {
		List<ReadOnlyTask> currentTaskList = model.getListOfTask().getTaskList();
		for (ReadOnlyTask task : currentTaskList) {
			if (((Task) task).checkOverDue()) {
				((Task) task).setOverdue();
			} else {
				((Task) task).setNotOverdue();
			}
		}
	}
}
