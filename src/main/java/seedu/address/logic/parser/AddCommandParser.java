package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Mark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmptyAddress;
import seedu.address.model.person.EmptyEmail;
import seedu.address.model.person.EmptyPhone;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePhoto;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX, PREFIX_PHONE, PREFIX_EMAIL,
                                           PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String value;
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        // Optional argument handling, assigns null if not present
        value = argMultimap.getValue(PREFIX_PHONE).orElse("");
        Phone phone = value.isEmpty() ? new EmptyPhone() : ParserUtil.parsePhone(value);
        value = argMultimap.getValue(PREFIX_EMAIL).orElse("");
        Email email = value.isEmpty() ? new EmptyEmail() : ParserUtil.parseEmail(value);
        value = argMultimap.getValue(PREFIX_ADDRESS).orElse("");
        Address address = value.isEmpty() ? new EmptyAddress() : ParserUtil.parseAddress(value);
        value = argMultimap.getValue(PREFIX_INDEX).orElse("");
        Index index = value.isEmpty() ? null : ParserUtil.parseIndex(value);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        ProfilePhoto photo = new ProfilePhoto();
        Map<String, Mark> markMap = new HashMap<>();

        Person person = new Person(name, phone, email, address, photo, tagList, markMap);

        return new AddCommand(person, index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
