import java.util.ArrayList;
import java.util.List;

/**
 * Author: Judah Silva
 * Date: 2/11/2023
 * Description: This is a class that will represent a reader at a library
 */

public class Reader {
    // Variables that hold indices of an array
    public final static int CARD_NUMBER_ = 0, NAME_ = 1, PHONE_ = 2, BOOK_COUNT_ = 3, BOOK_START_ = 4;

    // Each reader will have a card number, a name, a phone number, and a list of books that are checked out to them
    private int cardNumber;
    private String name, phone;
    private List<Book> books;

    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        this.books = new ArrayList<>();
    }

    // Adds a book to the reader
    public Code addBook(Book book) {
        // Check if they have already checked out the book, don't let them check out another copy
        if (this.books.contains(book)) {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        } else {
            this.books.add(book);
            return Code.SUCCESS;
        }
    }

    // Removes a book from the reader
    public Code removeBook(Book book) {
        // If the reader has the book, remove it
        if (this.hasBook(book)) {
            this.books.remove(book);
            return Code.SUCCESS;
        // If the reader does not have the book then it can't be removed, so return an error code
        } else if (!this.hasBook(book)) {
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        // If there is a problem when trying to remove the book, return an error code
        } else {
            return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
        }
    }

    // Checks if the reader has the book in their list
    public boolean hasBook(Book book) {
        return this.books.contains(book);
    }

    // Getters and setters to manipulate the fields from a different class
    public int getBookCount() { return this.books.size(); }

    public int getCardNumber() { return cardNumber; }

    public void setCardNumber(int cardNumber) { this.cardNumber = cardNumber; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public List<Book> getBooks() { return books; }

    public void setBooks(List<Book> books) { this.books = books; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (getCardNumber() != reader.getCardNumber()) return false;
        if (getName() != null ? !getName().equals(reader.getName()) : reader.getName() != null) return false;
        return getPhone() != null ? getPhone().equals(reader.getPhone()) : reader.getPhone() == null;
    }

    @Override
    public int hashCode() {
        int result = getCardNumber();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }

    // toString example: "Bob Barker (#2187) has checked out {Headfirst Java}"
    @Override
    public String toString() {
        StringBuilder bookNames = new StringBuilder();
        for (int i = 0; i < this.getBookCount(); i++) {
            bookNames.append(this.getBooks().get(i).getTitle());
            if (i != this.getBookCount() - 1) {
                bookNames.append(", ");
            }
        }
        return this.name + " (#" + this.cardNumber + ") has checked out {" + bookNames + "}";
    }
}
