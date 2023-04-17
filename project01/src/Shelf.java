import java.util.HashMap;

/**
 * Author: Judah Silva
 * Date: 3/05/2023
 * Description: This program is part of a library project; it represents a shelf that contains books.
 */
public class Shelf {
    // SHELF_NUMBER and SUBJECT_ hold numbers that will represent indices in an array
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    // Each shelf has a number, a subject, and a list of books stored in a hashmap
    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
        this.books = new HashMap<>();
    }

    public int getBookCount(Book book) {
        return books.getOrDefault(book, 0);
    }

    // Adds a book to the shelf
    public Code addBook(Book book) {
        // If the book is on the shelf already, then just increase the count of them
        if (books.containsKey(book)) {
            int count = books.get(book);
            books.put(book, count + 1);
        // If the book is not on the shelf, then make sure the subject matches the subject of the shelf
        } else if (book.getSubject().equals(this.subject)) {
            books.put(book, 1);
        // If the subjects don't match, then return an error code
        } else {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        // Print confirmation text
        System.out.println(book + " added to shelf");
        return Code.SUCCESS;
    }

    // Removes a book from the shelf
    public Code removeBook(Book book) {
        // If the book is not on the shelf then it can't be removed, so return an error code
        if (!books.containsKey(book)) {
            System.out.println(book.getTitle() + " is not on shelf " + this.subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        // If there are zero or fewer copies of the book, then it can't be removed
        } else if (books.get(book) <= 0) {
            System.out.println("No copies of " + book + " remain on the shelf " + this.subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        // If there are copies of the book, then lower the count of them by one
        } else {
            books.put(book, books.get(book) - 1);
            System.out.println(book + " successfully removed from shelf " + this.subject);
            return Code.SUCCESS;
        }
    }

    // Lists all the books on the shelf and how many copies of each book there are
    public String listBooks() {
        // Use a string builder to allow appending
        StringBuilder bookList = new StringBuilder();

        int bookCount = 0;
        // Gets the total number of books on the shelf
        for (Integer quantity : books.values()) {
            bookCount += quantity;
        }
        bookList.append(bookCount);

        // If there is more than one book, then use "books" instead of "book"
        if (bookCount > 1 || bookCount == 0) {
            bookList.append(" books on shelf: ");
        } else {
            bookList.append(" book on shelf: ");
        }
        bookList.append(this.shelfNumber).append(" : ").append(this.subject);

        // Go through the hashmap of books and append them to the string builder to print
        for (Book b : books.keySet()) {
            bookList.append("\n").append(b).append(" ").append(books.get(b));
        }
        return bookList.toString();
    }

    // Getters and setters to manipulate the fields from a different class
    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelf shelf = (Shelf) o;

        if (getShelfNumber() != shelf.getShelfNumber()) return false;
        return getSubject() != null ? getSubject().equals(shelf.getSubject()) : shelf.getSubject() == null;
    }

    @Override
    public int hashCode() {
        int result = getShelfNumber();
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        return result;
    }

    // toString example: 2 : Adventure
    @Override
    public String toString() {
        return this.shelfNumber + ": " + this.subject;
    }
}
