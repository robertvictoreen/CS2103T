package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.AddStudentWindowHandle;
import javafx.scene.control.Button;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddStudentWindowTest extends GuiUnitTest {

    // test for buttons implicitly tested in all tests

    // valid arguments
    private static final String EXAMPLE_NAME = "Alice Pauline";
    private static final String EXAMPLE_PHONE = "85355255";
    private static final String EXAMPLE_EMAIL = "alice@gmail.com";
    private static final String EXAMPLE_ADDRESS = "123, Jurong West Ave 6, #08-111";
    private static final String[] ARRAY_EXAMPLE_TAGS = {"friends", "owesMoney", "client"};

    // invalid arguments
    // empty
    private static final String EMPTY_STRING = "";

    // name, only numbers + letters
    private static final String INVALID_NAME_SPECIAL_CHAR = "John_Doe";

    // phone, numbers only, at least 3 digits long
    private static final String INVALID_PHONE_LESS_THAN_THREE = "99";
    private static final String INVALID_PHONE_LETTERS = "9876543a";
    private static final String INVALID_PHONE_SPECIAL_CHAR = "9876543/";

    // email, format is name@domain, name only these special char !#$%&'*+/=?`{|}~^.- and numbers, letters
    //        domain >= 2, start and end with alphanumeric, middle alphanumeric with only - or . between
    private static final String INVALID_EMAIL_FORMAT = "testgmail.com";
    private static final String INVALID_EMAIL_NAME_SPECIAL_CHAR = "test1()_[];\\,:<>@gmail.com";
    private static final String INVALID_EMAIL_DOMAIN_ONE_CHAR = "test@g";
    private static final String INVALID_EMAIL_DOMAIN_START_AND_END = "test@!gmail.com#";
    private static final String INVALID_EMAIL_DOMAIN_MIDDLE_SPECIAL_CHAR = "test@gmail_com";

    // address,

    // tag, leave blank for now

    private AddressBook addressBook;
    private AddStudentWindow window;
    private AddStudentWindowHandle handle;
    private Button okButton;
    private Person person;

    @Before
    public void setUp() {
        this.addressBook = new AddressBook();
        this.window = new AddStudentWindow();
        this.handle = new AddStudentWindowHandle(window.getRootPane());
        PersonBuilder personBuilder = new PersonBuilder();
        personBuilder.withTags(ARRAY_EXAMPLE_TAGS);
        this.person = personBuilder.build();
        okButton = getChildNode(handle.getRoot(), handle.getButtonId());

        // set up default inputs with 3 tags (MAX)
        enterName(EXAMPLE_NAME);
        enterPhone(EXAMPLE_PHONE);
        enterEmail(EXAMPLE_EMAIL);
        enterAddress(EXAMPLE_ADDRESS);
        enterTagOne(ARRAY_EXAMPLE_TAGS[0]);
        enterTagTwo(ARRAY_EXAMPLE_TAGS[1]);
        enterTagThree(ARRAY_EXAMPLE_TAGS[2]);

    }

    @Test
    public void testValidInput() {
        // address book doesn't have default person yet
        assertFalse(addressBook.hasPerson(this.person));
        // test correct (all args)
        handle.click(okButton);
        assertTrue(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmptyName() {
        assertFalse(addressBook.hasPerson(this.person));
        // empty name
        enterName(EMPTY_STRING);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testInvalidName() {
        assertFalse(addressBook.hasPerson(this.person));
        // test invalid name
        enterName(INVALID_NAME_SPECIAL_CHAR);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmptyPhone() {
        assertFalse(addressBook.hasPerson(this.person));
        // empty phone
        enterPhone(EMPTY_STRING);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testPhoneWithLetters() {
        assertFalse(addressBook.hasPerson(this.person));
        enterPhone(INVALID_PHONE_LETTERS);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testPhoneWithSpecialChar() {
        assertFalse(addressBook.hasPerson(this.person));
        // empty phone
        enterPhone(INVALID_PHONE_SPECIAL_CHAR);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testPhoneWithTwoDigits() {
        assertFalse(addressBook.hasPerson(this.person));
        enterPhone(INVALID_PHONE_LESS_THAN_THREE);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmptyEmail() {
        assertFalse(addressBook.hasPerson(this.person));
        enterEmail(EMPTY_STRING);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmailFormat() {
        assertFalse(addressBook.hasPerson(this.person));
        enterEmail(INVALID_EMAIL_FORMAT);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmailNameWithSpecialChar() {
        assertFalse(addressBook.hasPerson(this.person));
        enterEmail(INVALID_EMAIL_NAME_SPECIAL_CHAR);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmailDomainOneChar() {
        assertFalse(addressBook.hasPerson(this.person));
        enterEmail(INVALID_EMAIL_DOMAIN_ONE_CHAR);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmailDomainStartAndEnd() {
        assertFalse(addressBook.hasPerson(this.person));
        enterEmail(INVALID_EMAIL_DOMAIN_START_AND_END);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }

    @Test
    public void testEmailDomainMiddleSpecialChar() {
        assertFalse(addressBook.hasPerson(this.person));
        enterEmail(INVALID_EMAIL_DOMAIN_MIDDLE_SPECIAL_CHAR);
        handle.click(okButton);
        assertFalse(addressBook.hasPerson(this.person));
    }
    // test address
    // test empty address

    // test invalid address

    // test correct (all args)

    // test tags
    // test empty tags

    // test invalid tags

    // test alternate tag sequences (1, 1+2, 1+3, 1+2+3)
    //                              (2, 2+3), (3)

    // test correct (all args)



    private void enterName(String name) {
        window.setNameField(name);
    }

    private void enterPhone(String phone) {
        window.setPhoneField(phone);
    }

    private void enterEmail(String email) {
        window.setEmailField(email);
    }

    private void enterAddress(String address) {
        window.setAddressField(address);
    }

    private void enterTagOne(String t1) {
        window.setTagOneField(t1);
    }

    private void enterTagTwo(String t2) {
        window.setTagTwoField(t2);
    }

    private void enterTagThree(String t3) {
        window.setTagThreeField(t3);
    }
}
