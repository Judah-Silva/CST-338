import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Judah Silva
 * Date: 3/05/2023
 * Description: This is a unit test for the Shelf class of the library project
 */

class ShelfTest {
    Shelf testShelf = null;
    private int testShelfNumber = 2;
    private String testSubject = "Adventure";
    private HashMap<Book, Integer> testBooks = null;
    private Book testBook = new Book("1234", "Ghosts of Onyx", "Adventure", 42, "Eric Nylund", LocalDate.now());

    @BeforeEach
    void setUp() {
        testShelf = new Shelf(testShelfNumber, testSubject);
        testBooks = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        testShelf = null;
    }

    @Test
    void testConstructor() {
        Shelf testShelf2 = null;
        assertNull(testShelf2);
        testShelf2 = testShelf;
        assertNotNull(testShelf2);
        assertEquals(testShelf, testShelf2);
    }

    @Test
    void getBookCount() {
        Random random = new Random();
        int randomNum = Math.abs(random.nextInt() % 10);
        for (int i = 0; i < randomNum; i++) {
            testShelf.addBook(testBook);
        }
        assertEquals(randomNum, testShelf.getBookCount(testBook));
        testShelf.removeBook(testBook);
        assertEquals(randomNum - 1, testShelf.getBookCount(testBook));
        Book testBook2 = new Book("123", "Clifford", "Children's", 8, "Bob", LocalDate.now());
        assertEquals(0, testShelf.getBookCount(testBook2));
    }

    @Test
    void addBook() {
        Book testBook2 = new Book("123", "Clifford", "Children's", 8, "Bob", LocalDate.now());
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        assertEquals(1, testShelf.getBooks().get(testBook));
        testShelf.addBook(testBook);
        assertEquals(2, testShelf.getBooks().get(testBook));
        assertEquals(Code.SHELF_SUBJECT_MISMATCH_ERROR, testShelf.addBook(testBook2));
        assertEquals(0, testShelf.getBookCount(testBook2));
    }

    @Test
    void removeBook() {
        Book testBook2 = new Book("123", "Clifford", "Children's", 8, "Bob", LocalDate.now());
        assertEquals(Code.BOOK_NOT_IN_INVENTORY_ERROR, testShelf.removeBook(testBook2));
        assertEquals(Code.SUCCESS, testShelf.addBook(testBook));
        testShelf.addBook(testBook);
        assertEquals(2, testShelf.getBooks().get(testBook));
        assertEquals(Code.SUCCESS, testShelf.removeBook(testBook));
        assertEquals(1, testShelf.getBooks().get(testBook));
        assertEquals(Code.SUCCESS, testShelf.removeBook(testBook));
        assertEquals(0, testShelf.getBooks().get(testBook));
    }

    @Test
    void listBooks() {
        testShelf.addBook(testBook);
        testShelf.addBook(testBook);
        Book testBook2 = new Book("123", "Clifford", "Adventure", 8, "Bob", LocalDate.now());
        testShelf.addBook(testBook2);
        assertEquals("3 books on shelf: 2 : Adventure\nClifford by Bob ISBN: 123 1\nGhosts of Onyx by Eric Nylund ISBN: 1234 2", testShelf.listBooks());
    }

    @Test
    void getShelfNumber() {
        assertEquals(2, testShelf.getShelfNumber());
    }

    @Test
    void setShelfNumber() {
        assertEquals(2, testShelf.getShelfNumber());
        testShelf.setShelfNumber(3);
        assertEquals(3, testShelf.getShelfNumber());
    }

    @Test
    void getSubject() {
        assertEquals("Adventure", testShelf.getSubject());
    }

    @Test
    void setSubject() {
        assertEquals("Adventure", testShelf.getSubject());
        testShelf.setSubject("Education");
        assertEquals("Education", testShelf.getSubject());
    }

    @Test
    void getBooks() {
        assertEquals(testBooks, testShelf.getBooks());
    }

    @Test
    void setBooks() {
        assertEquals(testBooks, testShelf.getBooks());
        testShelf.setBooks(null);
        assertNotEquals(testBooks, testShelf.getBooks());
    }

    @Test
    void testEquals() {
        Shelf testShelf2 = new Shelf(4, "Education");
        Shelf testShelf3 = new Shelf(4, "Education");
        assertFalse(testShelf2.equals(testShelf));
        assertTrue(testShelf2.equals(testShelf3));
    }
}