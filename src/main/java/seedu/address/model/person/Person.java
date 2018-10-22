package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Mark;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    private ProfilePicture picture;
    private final Map<String, Mark> marks = new HashMap<>();

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Address address, ProfilePicture pic, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = pic;
        this.tags.addAll(tags);
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Map<String, Mark> marks) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = new ProfilePicture();
        this.tags.addAll(tags);
        requireNonNull(marks);
        this.marks.putAll(marks);
    }

    public Person(Name name, Phone phone, Email email, Address address, ProfilePicture pic, Set<Tag> tags, Map<String,
        Mark> marks) {

        this(name, phone, email, address, pic, tags);
        requireNonNull(marks);
        this.marks.putAll(marks);
    }

    public Person(Person source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getProfilePicture(),
            source.getTags());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public ProfilePicture getProfilePicture() {
        return picture;
    }

    /**
     * Update contact picture to that located in path
     * @param path
     */
    public void updatePicture(String path) {
        int hash = this.hashCode();
        String filename = String.valueOf(hash);
        this.picture = new ProfilePicture(path, filename);
    }

    /**
     * Delete the current picture and set up a default picture.
     */
    public void deleteProfilePicture() {
        this.picture = new ProfilePicture();
    }

    /**
     * Set profile picture to that in path
     */
    public void setProfilePicture(String path) throws IllegalValueException {

        ProfilePicture oldPic = this.picture;
        try {
            int fileName = this.hashCode();
            this.picture = new ProfilePicture(path, String.valueOf(fileName));
        } catch (Exception e) {
            this.picture = oldPic; //reset picture back to default
            throw new IllegalValueException(ProfilePicture.MESSAGE_PICTURE_CONSTRAINTS);
        }
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable mark map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, Mark> getMarks() {
        return Collections.unmodifiableMap(marks);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getMarks().equals(getMarks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, marks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
