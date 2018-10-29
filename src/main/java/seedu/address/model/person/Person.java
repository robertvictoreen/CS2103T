package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    private final List<AssignmentStub> assignments;

    // Data fields
    private final Address address;
    private ProfilePhoto photo;
    private final Set<Tag> tags = new HashSet<>();
    private final Map<String, Mark> marks = new HashMap<>();

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Address address, ProfilePhoto photo, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.photo = pic;
        this.tags.addAll(tags);

        // assignmentStub initialization
        this.assignments = new ArrayList<>();
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.photo = new ProfilePhoto();
        this.tags.addAll(tags);

        // assignmentStub initialization
        this.assignments = new ArrayList<>();
    }

    public Person(Person source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getProfilePhoto(),
                source.getTags(), source.getMarks());
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Map<String, Mark> marks) {
        this(name, phone, email, address, tags);
        requireNonNull(marks);
        this.marks.putAll(marks);
    }

    public Person(Name name, Phone phone, Email email, Address address, ProfilePhoto photo, Set<Tag> tags,
                  Map<String, Mark> marks) {
        this(name, phone, email, address, tags);
        requireAllNonNull(pic, marks);
        this.photo = photo;
        this.marks.putAll(marks);
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

    public ProfilePhoto getProfilePhoto() {
        return photo;
    }


    /**
     * Delete the current photo and set up a default photo.
     */
    public void deleteProfilePhoto() {
        this.photo = new ProfilePhoto();
    }

    /**
     * Set profile photo to that in path
     */
    public void setProfilePhoto(String path) throws IllegalValueException {

        ProfilePhoto oldPhoto = this.photo;
        try {
            int fileName = this.hashCode();
            this.photo = new ProfilePhoto(path, String.valueOf(fileName));
        } catch (Exception e) {
            this.photo = oldPhoto; //changes photo back to default
            throw new IllegalValueException(ProfilePhoto.MESSAGE_PHOTO_CONSTRAINTS);
        }
    }

    /**
     * Getter for AssignmentStub class
     */
    public List<AssignmentStub> getAssignments() {
        return assignments;
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
     * Adds assignment to student if student doesn't already have it, returns boolean that indicates if addition
     * was successful.
     */
    public boolean addAssignment(AssignmentStub toAdd) {
        return this.assignments.add(toAdd);
    }

    /**
     * Removes assignment from student if student has it, returns boolean that indicates if removal was successful.
     */
    public boolean removeAssignment(AssignmentStub toRemove) {
        return this.assignments.remove(toRemove);
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
                && otherPerson.getProfilePhoto().equals(getProfilePhoto())
                && otherPerson.getMarks().equals(getMarks())
                && otherPerson.getAssignments().equals(getAssignments());
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
