import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Judah Silva
 * Date: 2/11/2023
 * Description: This is a unit test for the Reader class of the Library project
 */

class ReaderTest {

    Reader testReader = null;
    private int testCardNumber = 2187;
    private String testName = "Bob Barker";
    private String testPhone = "555-555-4444";
    private Book book = new Book("1337", "Headfirst Java", "education", 1337, "Grady Booch", LocalDate.now());
    private List<Book> testBooks = new ArrayList();
    @BeforeEach
    void setUp() {
        testReader = new Reader(testCardNumber, testName, testPhone);
    }

    @AfterEach
    void tearDown() {
        testReader = null;
    }

    @Test
    void testConstructor() {
        Reader testReader2 = null;
        assertNull(testReader2);
        testReader2 = testReader;
        assertNotNull(testReader2);
        assertEquals(testReader, testReader2);
    }

    @Test
    void addBook() {
        assertEquals(Code.SUCCESS, testReader.addBook(book));
        assertNotEquals(Code.SUCCESS, testReader.addBook(book));
        assertEquals(Code.BOOK_ALREADY_CHECKED_OUT_ERROR, testReader.addBook(book));
    }

    @Test
    void removeBook() {
        assertEquals(Code.READER_DOESNT_HAVE_BOOK_ERROR, testReader.removeBook(book));
        testReader.addBook(book);
        assertEquals(Code.SUCCESS, testReader.removeBook(book));
    }

    @Test
    void hasBook() {
        assertFalse(testReader.hasBook(book));
        testReader.addBook(book);
        assertTrue(testReader.hasBook(book));
    }

    @Test
    void getBookCount() {
        assertEquals(0, testReader.getBookCount());
        testReader.addBook(book);
        assertEquals(1, testReader.getBookCount());
        testReader.removeBook(book);
        assertEquals(0, testReader.getBookCount());
    }

    @Test
    void getCardNumber() {
        assertEquals(2187, testReader.getCardNumber());
    }

    @Test
    void setCardNumber() {
        testReader.setCardNumber(117);
        assertNotEquals(2187, testReader.getCardNumber());
        assertEquals(117, testReader.getCardNumber());
    }

    @Test
    void getName() {
        assertEquals("Bob Barker", testReader.getName());
    }

    @Test
    void setName() {
        testReader.setName("John");
        assertNotEquals("Bob Barker", testReader.getName());
        assertEquals("John", testReader.getName());
    }

    @Test
    void getPhone() {
        assertEquals("555-555-4444", testReader.getPhone());
    }

    @Test
    void setPhone() {
        testReader.setPhone("772-630-0054");
        assertNotEquals("555-555-4444", testReader.getPhone());
        assertEquals("772-630-0054", testReader.getPhone());
    }

    @Test
    void getBooks() {
        assertEquals(testBooks, testReader.getBooks());
    }

    @Test
    void setBooks() {
        testReader.setBooks(new ArrayList());
        assertEquals(testBooks, testReader.getBooks());
        testReader.addBook(book);
        assertNotEquals(testBooks, testReader.getBooks());
    }

    @Test
    void testEquals() {
        Reader testReader2 = new Reader(117, "John", "772-630-0054");
        Reader testReader3 = new Reader(117, "John", "772-630-0054");
        assertFalse(testReader2.equals(testReader));
        assertTrue(testReader2.equals(testReader3));
    }

    @Test
    void testHashCode() {
        Reader testReader2 = new Reader(117, "John", "772-630-0054");
        Reader testReader3 = new Reader(117, "John", "772-630-0054");
        assertNotEquals(testReader.hashCode(), testReader2.hashCode());
        assertEquals(testReader2.hashCode(), testReader3.hashCode());
    }

    @Test
    void testToString() {
        assertNotEquals("Bob Barker (#2187) has checked out {Headfirst Java}", testReader.toString());
        testReader.addBook(book);
        testReader.addBook(new Book("501", "Anakin's Dark Deeds", "Sci-fi", 66, "Anakin Skywalker", LocalDate.of(2005, 5, 19)));
        assertEquals("Bob Barker (#2187) has checked out {Headfirst Java, Anakin's Dark Deeds}", testReader.toString());
    }
}