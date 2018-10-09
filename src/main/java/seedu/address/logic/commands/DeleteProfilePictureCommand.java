package seedu.address.logic.commands;

/**
 * Deletes the profile picture of a person identified using it's last displayed index from the address book.
 */
public class DeleteProfilePictureCommand extends Command {
    public static final String COMMAND_WORD = "deleteProfilePic";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the profile picture of the person identified by the index number used in "
            + "the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_DELETE_PROFILE_PIC_SUCCESS = "Deleted profile picture of Person: %1$s";

    private final Index targetIndex;

    public DeleteProfilePictureCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson profilePicToDelete = lastShownList.get(targetIndex.getZeroBased());

        UpdateProfilePicCommand updateToDefault = new UpdateProfilePicCommand(targetIndex, new ProfilePic());
        updateToDefault.setData(model, history, undoRedoStack);
        updateToDefault.execute();

        return new CommandResult(String.format(MESSAGE_DELETE_PROFILE_PIC_SUCCESS, profilePicToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteProfilePictureCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteProfilePictureCommand) other).targetIndex)); // state check
    }
}