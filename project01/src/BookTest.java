import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Judah Silva
 * Date: 2/10/2023
 * Description: This is a unit test for the Book.java class
 */

class BookTest {
//    1337,Headfirst Java,education,1337,Grady Booch,0000
    private Book testBook = null;
    private String testISBN = "1337";
    private String testTitle = "Headfirst Java";
    private String testSubject = "education";
    private int testPageCount = 1337;
    private String testAuthor = "Grady Booch";
    private LocalDate testDueDate = LocalDate.now();


    @BeforeAll
    static void mainSetUp() {
    }
    @AfterAll
    static void mainTearDown() {
    }
    @BeforeEach
    void setUp() {
        testBook = new Book(testISBN, testTitle, testSubject, testPageCount, testAuthor, testDueDate);
    }

    @AfterEach
    void tearDown() {
        testBook = null;
    }

    @Test
    void testConstructor() {
        Book testBook2 = null;
        assertNull(testBook2);
        testBook2 = testBook;
        assertNotNull(testBook2);
        assertEquals(testBook2, testBook);
    }
    @Test
    void getIsbn() {
        assertEquals("1337", testBook.getIsbn());
    }

    @Test
    void setIsbn() {
        testBook.setIsbn("5297");
        assertNotEquals("1337", testBook.getIsbn());
        assertEquals("5297", testBook.getIsbn());
    }

    @Test
    void getTitle() {
        assertEquals("Headfirst Java", testBook.getTitle());
    }

    @Test
    void setTitle() {
        testBook.setTitle("Count of Monte Cristo");
        assertNotEquals("Headfirst Java", testBook.getTitle());
        assertEquals("Count of Monte Cristo", testBook.getTitle());
    }

    @Test
    void getSubject() {
        assertEquals("education", testBook.getSubject());
    }

    @Test
    void setSubject() {
        testBook.setSubject("Adventure");
        assertNotEquals("education", testBook.getSubject());
        assertEquals("Adventure", testBook.getSubject());
    }

    @Test
    void getPageCount() {
        assertEquals(1337, testBook.getPageCount());
    }

    @Test
    void setPageCount() {
        testBook.setPageCount(999);
        assertNotEquals(1337, testBook.getPageCount());
        assertEquals(999, testBook.getPageCount());
    }

    @Test
    void getAuthor() {
        assertEquals("Grady Booch", testBook.getAuthor());
    }

    @Test
    void setAuthor() {
        testBook.setAuthor("Alexandre Dumas");
        assertNotEquals("Grady Booch", testBook.getAuthor());
        assertEquals("Alexandre Dumas", testBook.getAuthor());
    }

    @Test
    void getDueDate() {
        assertEquals(LocalDate.now(), testBook.getDueDate());
    }

    @Test
    void setDueDate() {
        testBook.setDueDate(LocalDate.of(2003, 11, 15));
        assertNotEquals(LocalDate.now(), testBook.getDueDate());
        assertEquals(LocalDate.of(2003, 11, 15), testBook.getDueDate());
    }

    @Test
    void testEquals() {
        Book testBook2 = new Book("5297", "Count of Monte Cristo", "Adventure", 999, "Alexandre Dumas", LocalDate.of(2003, 11, 15));
        Book testBook3 = new Book("5297", "Count of Monte Cristo", "Adventure", 999, "Alexandre Dumas", LocalDate.of(2003, 11, 15));
        assertFalse(testBook2.equals(testBook));
        assertTrue(testBook2.equals(testBook3));
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}