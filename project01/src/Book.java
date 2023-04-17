import java.time.LocalDate;

/**
 * Author: Judah Silva
 * Date: 2/10/2023
 * Description: This is a class that represent a book in a library
 */

public class Book {
    // Variables that hold indices for an arra
    public static final int  ISBN_ = 0, TITLE_ = 1, SUBJECT_ = 2, PAGE_COUNT_ = 3, AUTHOR_ = 4, DUE_DATE_ = 5;
    // Each book has an isbn, a title, a subject, an author, a page count, and a due date.
    private String isbn, title, subject, author;
    private int pageCount;
    private LocalDate dueDate;

    public Book (String isbn, String title, String subject, int pageCount, String author, LocalDate dueDate) {
        this.isbn = isbn;
        this.title = title;
        this.subject = subject;
        this.pageCount = pageCount;
        this.author = author;
        this.dueDate = dueDate;
    }

    // Getters and setters to manipulate the fields from a different class
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (getPageCount() != book.getPageCount()) return false;
        if (getIsbn() != null ? !getIsbn().equals(book.getIsbn()) : book.getIsbn() != null) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        if (getSubject() != null ? !getSubject().equals(book.getSubject()) : book.getSubject() != null) return false;
        return getAuthor() != null ? getAuthor().equals(book.getAuthor()) : book.getAuthor() == null;
    }

    @Override
    public int hashCode() {
        int result = getIsbn() != null ? getIsbn().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + getPageCount();
        return result;
    }

    // toString example: "Ghosts of Onyx by Eric Nylund ISBN: 1234"
    @Override
    public String toString() {
        return this.title + " by " + this.author + " ISBN: " + this.isbn;
    }
}
