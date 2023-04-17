import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

/**
 * Author: Judah Silva
 * Date: 3/24/2023
 * Description: This is the last class of the library project.  It functions as a library using every file in the project.
 */
public class Library {
    // This library has a limit of 5 books to be lent out to one person
    public static final int LENDING_LIMIT = 5;

    // Each library has a name, a list of readers, a list of shelves, and a list of books
    private String name;
    private static int libraryCard; // Holds the card number of the most recently entered reader
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    public Library(String name) {
        this.name = name;
        this.books = new HashMap<>();
        this.shelves = new HashMap<>();
        this.readers = new ArrayList<>();
    }

    // Reads information from a file and adds it to the library
    public Code init(String filename) {
        // Create a scanner and file object to hold the file that will be read from
        Scanner file = null;
        File f = new File(filename);

        // Assign the file to the scanner
        try {
            file = new Scanner(f);
        } catch(FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        // This will loop 3 times, each time calling different methods
        for (int i = 0; i < 3; i++) {
            // Will hold the total of each type of information to be read
            int recordCount = convertInt(file.nextLine(), Code.BOOK_COUNT_ERROR);
            switch (i) {
                // The first loop will be to read all the book information and add them to the library using initBooks
                case 0 -> {
                    System.out.println("Parsing " + recordCount + " books:");
                    initBooks(recordCount, file);
                    listBooks();
                }
                // The second loop will be to read all the shelf information and add them to the library using initShelves
                case 1 -> {
                    System.out.println("\nParsing " + recordCount + " shelves:");
                    initShelves(recordCount, file);
                    listShelves(true);
                }
                // The final loop will be to read all the reader information and add them to the library using initReaders
                case 2 -> {
                    System.out.println("\nParsing " + recordCount + " readers:");
                    initReader(recordCount, file);
                    listReaders();
                }
            }
        }
        return Code.SUCCESS;
    }

    // Parses through the books in the file and adds them to the library
    private Code initBooks(int bookCount, Scanner scan) {
        // If there are no books to be parsed, return an error code
        if (bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }
        // Create an array and fill it with the information for one book
        String[] bookFields;
        for (int i = 0; i < bookCount; i++) {
            bookFields = scan.nextLine().split(",");
            // Use the indices provided in the Book class to get the right info
            String isbn = bookFields[Book.ISBN_];
            String title = bookFields[Book.TITLE_];
            String subject = bookFields[Book.SUBJECT_];
            int pageCount = convertInt(bookFields[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR);
            String author = bookFields[Book.AUTHOR_];
            LocalDate dueDate = convertDate(bookFields[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
            Book book = new Book(isbn, title, subject, pageCount, author, dueDate);
            // Add the book to the library and repeat for the rest of them
            addBook(book);
        }
        return Code.SUCCESS;
    }

    // Parses through the shelves in the file and adds them to the library
    private Code initShelves(int shelfCount, Scanner scan) {
        // If there are no shelves to be parsed, return an error code
        if (shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }

        // Create an array to hold the information for one shelf
        String[] shelfFields;
        for (int i = 0; i < shelfCount; i++) {
            StringBuilder message = new StringBuilder();
            message.append("Parsing Shelf: ");
            shelfFields = scan.nextLine().split(",");
            // Use the indices provided in the Shelf class to get the right info
            int shelfNumber = convertInt(shelfFields[Shelf.SHELF_NUMBER_], Code.SHELF_NUMBER_PARSE_ERROR);
            String subject = shelfFields[Shelf.SUBJECT_];
            Shelf shelf = new Shelf(shelfNumber, subject);
            message.append(shelfNumber).append(",").append(subject);
            System.out.println(message);
            // Add the shelf and repeat for the rest of them
            addShelf(shelf);
        }
        // If the number of shelves in the library and the number in the file don't match, then return an error code
        if (shelves.size() != shelfCount) {
            return Code.SHELF_NUMBER_PARSE_ERROR;
        } else {
            return Code.SUCCESS;
        }
    }

    private Code initReader(int readerCount, Scanner scan) {
        // If there are no readers to be parsed, then return an error code
        if (readerCount < 1) {
            return Code.READER_COUNT_ERROR;
        }

        // Create an array that will hold the info for one reader
        String[] readerFields;
        for (int i = 0; i < readerCount; i++) {
            readerFields = scan.nextLine().split(",");
            // Use the indices provided in the Reader class to get the right info
            int cardNumber = convertInt(readerFields[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR);
            String name = readerFields[Reader.NAME_];
            String phone = readerFields[Reader.PHONE_];
            int bookCount = convertInt(readerFields[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR);
            Reader reader = new Reader(cardNumber, name, phone);
            addReader(reader);
            // After the reader was created and added, parse through the books that belong to them and check them out
            for (int j = 0; j <= bookCount; j += 2) {
                String isbn = readerFields[Reader.BOOK_START_ + j];
                LocalDate dueDate = convertDate(readerFields[Reader.BOOK_START_ + j + 1], Code.DATE_CONVERSION_ERROR);
                Book book = getBookByISBN(isbn);
                if (book != null) {
                    checkOutBook(reader, book);
                }
            }
        }
        return Code.SUCCESS;
    }

    // Adds a book to the library
    public Code addBook(Book book) {
        // If the book is already in the library, then increase the count of them by one
        if (books.containsKey(book)) {
            int count = books.get(book);
            books.put(book, count + 1);
            System.out.println(books.get(book) + " copies of " + book.getTitle() + " in the stacks.");
        // If the book is not in the library, then add it with a count of one
        } else {
            books.put(book, 1);
            System.out.println(book.getTitle() + " added to the stacks.");
        // If the library has a shelf with the same subject, add the book to that shelf
        } if (shelves.containsKey(book.getSubject())) {
            shelves.get(book.getSubject()).addBook(book);
            return Code.SUCCESS;
        }
        // If there is no shelf for the book, then return an error code
        System.out.println("No shelf for " + book.getSubject() + " books.");
        return Code.SHELF_EXISTS_ERROR;
    }

    // Returns a book from a reader
    public Code returnBook(Reader reader, Book book) {
        // If the reader does not have the book then return an error code
        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out.");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        // If the book is not from this library, then return an error code
        } else if (!books.containsKey(book)) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            System.out.println(reader.getName() + " is returning " + book);
        }

        // Remove the book from the reader, and if that happens successfully then return it to the library
        Code code = reader.removeBook(book);
        if (code == Code.SUCCESS) {
            return returnBook(book);
        } else {
            System.out.println("Could not return " + book);
            return code;
        }
    }

    // Returns a book to the library
    public Code returnBook(Book book) {
        // If there is no shelf for the book, then return an error code
        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book);
            return Code.SHELF_EXISTS_ERROR;
        // Add the book to the shelf that it belongs
        } else {
            shelves.get(book.getSubject()).addBook(book);
            return Code.SUCCESS;
        }
    }

    // Adds a book to a shelf
    private Code addBookToShelf(Book book, Shelf shelf) {
        // Try to return the book using returnBook
        if (returnBook(book) == Code.SUCCESS) {
            return Code.SUCCESS;
        }
        // If the shelf subject and book subject don't match, then return an error code
        if (!shelf.getSubject().equals(book.getSubject())) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }
        // Add the book to the shelf and print a confirmation or failure message
        Code code = shelf.addBook(book);
        if (code == Code.SUCCESS) {
            System.out.println(book + " added to " + shelf);
            return Code.SUCCESS;
        } else {
            System.out.println("Could not add " + book + " to shelf.");
            return code;
        }
    }

    // Lists the books that are in the library
    public int listBooks() {
        // Goes through the hashmap of books, prints them out, and returns the total number of books in the library
        int count = 0;
        for (Book b : books.keySet()) {
            System.out.println(books.get(b) + " copies of " + b);
            count += books.get(b);
        }
        return count;
    }

    // Checks out a book to a reader
    public Code checkOutBook(Reader reader, Book book) {
        // Check if the reader exists and is registered in this library
        if (reader == null) {
            System.out.println("ERROR: Reader does not exist.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        } else if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        // If the reader has the max amount of books checked out, then return an error code and stop book check out
        } else if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, " + LENDING_LIMIT);
            return Code.BOOK_LIMIT_REACHED_ERROR;
        // Makes sure that the book exists in the library
        } else if (!books.containsKey(book)) {
            System.out.println("ERROR: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        // Makes sure the shelf exists and that there are copies of the book still left on it
        } else if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        } else if (shelves.get(book.getSubject()).getBookCount(book) < 1) {
            System.out.println("ERROR: no copies of " + book + " remain.");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }
        // Add the book to the reader's list
        Code code = reader.addBook(book);
        if (code != Code.SUCCESS) {
            System.out.println("Couldn't checkout " + book);
            return code;
        }
        // Remove the book from the shelf
        code = shelves.get(book.getSubject()).removeBook(book);
        if (code == Code.SUCCESS) {
            System.out.println(book + " checked out successfully.");
        }
        return code;
    }

    // Search the hashmap of books for one with a matching isbn
    public Book getBookByISBN(String isbn) {
        for (Book b : books.keySet()) {
            if (b.getIsbn().equals(isbn)) {
                return b;
            }
        }
        System.out.println("ERROR: could not find a book with isbn: " + isbn);
        return null;
    }

    // Lists the shelves in the library
    public Code listShelves(Boolean showBooks) {
        // Gives an option for whether the books should be listed along with the shelves
        if (showBooks) {
            for (Shelf s : shelves.values()) {
                System.out.println(s.listBooks() + "\n");
            }
        } else {
            for (Shelf s : shelves.values()) {
                System.out.println(s);
            }
        }
        return Code.SUCCESS;
    }

    // Adds a shelf to the library
    public Code addShelf(String shelfSubject) {
        // Create a shelf with the given subject and pass it to addShelf
        Shelf shelf = new Shelf(shelves.size() + 1, shelfSubject);
        return addShelf(shelf);
    }

    // Adds a shelf to the library
    public Code addShelf(Shelf shelf) {
        // Makes sure that the shelf isn't already in the library
        if (shelves.containsValue(shelf)) {
            System.out.println("ERROR: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        // Add the shelf to the library
        } else {
            shelves.put(shelf.getSubject(), shelf);
        }
        // Add all the corresponding books in the library to the new shelf
        for (Book b : books.keySet()) {
            if (b.getSubject().equals(shelf.getSubject())) {
                for (int i = 0; i < books.get(b); i++) {
                    shelf.addBook(b);
                }
            }
        }
        return Code.SUCCESS;
    }

    // Searches for a shelf in the library using a shelf number and returns it
    public Shelf getShelf(int shelfNumber) {
        for (Shelf s : shelves.values()) {
            if (s.getShelfNumber() == shelfNumber) {
                return s;
            }
        }
        System.out.println("No shelf number " + shelfNumber + " found.");
        return null;
    }

    // Searches for a shelf in the library using a subject and returns it
    public Shelf getShelf(String subject) {
        for (Shelf s : shelves.values()) {
            if (s.getSubject().equals(subject)) {
                return s;
            }
        }
        System.out.println("No shelf for " + subject + " books.");
        return null;
    }

    // Lists the readers that are registered in the library
    public int listReaders() {
        for (Reader r : readers) {
            System.out.println(r);
        }
        // Returns the total number of readers
        return readers.size();
    }

    // Lists the readers that are registered in the library
    public int listReaders(boolean showBooks) {
        // Gives an option for whether to show the books that each reader has along with the list of readers
        if (showBooks) {
            for (Reader r : readers) {
                System.out.println(r.getName() + "(#" + r.getCardNumber() + ") has the following books:");
                System.out.println(r.getBooks());
            }
        } else {
            listReaders();
        }
        return readers.size();
    }

    // Searches for a reader in the library using a card number
    public Reader getReaderByCard(int cardNumber) {
        for (Reader r: readers) {
            if (r.getCardNumber() == cardNumber) {
                return r;
            }
        }
        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    // Adds a reader to the library
    public Code addReader(Reader reader) {
        // If the reader is already registered, then return an error code
        if (readers.contains(reader)) {
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        } else {
            // Makes sure that two readers don't have the same card number
            for (Reader r : readers) {
                if (r.getCardNumber() == reader.getCardNumber()) {
                    System.out.println(r.getName() + " and " + reader.getName() + " have the same card number!");
                    return Code.READER_CARD_NUMBER_ERROR;
                }
            }
            // Add the reader to the library and update the libraryCard variable if necessary
            readers.add(reader);
            if (reader.getCardNumber() > libraryCard) {
                libraryCard = reader.getCardNumber();
            }
            return Code.SUCCESS;
        }
    }

    // Removes the reader from the library
    public Code removeReader(Reader reader) {
        // If the reader is registered, but they still have books checked out, tell them to return them and return an error code
        if (readers.contains(reader) && !reader.getBooks().isEmpty()) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        // If the reader is not registered in the library, then return an error code
        } else if (!readers.contains(reader)) {
            System.out.println(reader + " is not part of this Library.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        // Remove the reader from the library
        } else {
            readers.remove(reader);
            return Code.SUCCESS;
        }
    }

    // Converts a string to an int
    public static int convertInt(String recordCountString, Code code) {
        int convertedInt;
        // Convert the string to an int
        try {
            convertedInt = Integer.parseInt(recordCountString);
        } catch (NumberFormatException e) {
            // Print out an error message for the specific type of error that occurred
            System.out.println("Value which caused the error: " + recordCountString);
            switch (code) {
                case BOOK_COUNT_ERROR -> {
                    System.out.println("ERROR: Could not read number of books.");
                    return code.getCode();
                }
                case PAGE_COUNT_ERROR -> {
                    System.out.println("ERROR: Could not parse page count.");
                    return code.getCode();
                }
                case DATE_CONVERSION_ERROR -> {
                    System.out.println("ERROR: Could not parse date component.");
                    return code.getCode();
                }
            }
            System.out.println("ERROR: Unknown conversion error.");
            return code.getCode();
        }
        return convertedInt;
    }

    // Converts a string to a LocalDate object
    public static LocalDate convertDate(String date, Code code) {
        // Return the epoch object as a default date if date is not valid
        if (date.equals("0000")) {
            return LocalDate.EPOCH;
        }
        // Create an array to hold the date split into different parts
        String[] separatedDate = date.split("-");
        if (separatedDate.length < 3) {
            System.out.println("ERROR: Date conversion error, could not parse " + date);
            System.out.println("Using default date (01-Jan-1970)");
            return LocalDate.EPOCH;
        }
        // Put the date information from the array to the corresponding variables
        int year = convertInt(separatedDate[0], Code.DATE_CONVERSION_ERROR);
        int month = convertInt(separatedDate[1], Code.DATE_CONVERSION_ERROR);
        int day = convertInt(separatedDate[2], Code.DATE_CONVERSION_ERROR);
        // Return the converted LocalDate object
        if (year >= 0 && month >= 0 && day >= 0) {
            return LocalDate.of(year, month, day);
        }
        // Check for errors
        if (year < 0) {
            System.out.println("Error converting date: Year " + year);
        } if (month < 0) {
            System.out.println("Error converting date: Month " + month);
        } if (day < 0) {
            System.out.println("Error converting date: Day " + day);
        }
        return LocalDate.EPOCH;
    }

    // Updates libraryCard
    public static int getLibraryCard() {
        return libraryCard + 1;
    }

    // Returns an error code for the corresponding code number
    private Code errorCode(int codeNumber) {
        for (Code code: Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }
}
