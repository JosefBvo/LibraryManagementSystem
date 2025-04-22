package services;

import models.Book;
import models.Borrower;
import java.util.*;

public class LibraryService {
    private List<Book> books;
    private List<Borrower> borrowers;
    private Map<String, String> borrowedBooks; // Book ID to Borrower ID

    public LibraryService() {
        books = new ArrayList<>();
        borrowers = new ArrayList<>();
        borrowedBooks = new HashMap<>();
    }

    // Book Management
    public boolean addBook(Book book) {
        if (findBookById(book.getId()) != null) return false;
        books.add(book);
        return true;
    }

    public boolean editBook(String id, String title, String author, String genre) {
        Book book = findBookById(id);
        if (book == null) return false;
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        return true;
    }

    public boolean deleteBook(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    // Borrower Management
    public boolean registerBorrower(Borrower borrower) {
        if (findBorrowerById(borrower.getId()) != null) return false;
        borrowers.add(borrower);
        return true;
    }

    public boolean updateBorrower(String id, String name, String contact) {
        Borrower borrower = findBorrowerById(id);
        if (borrower == null) return false;
        borrower.setName(name);
        borrower.setContactDetails(contact);
        return true;
    }

    public boolean deleteBorrower(String id) {
        return borrowers.removeIf(b -> b.getId().equals(id));
    }

    // Borrow/Return
    public boolean borrowBook(String bookId, String borrowerId) {
        Book book = findBookById(bookId);
        Borrower borrower = findBorrowerById(borrowerId);
        if (book == null || borrower == null || !book.isAvailable()) return false;
        book.borrowBook();
        borrowedBooks.put(bookId, borrowerId);
        return true;
    }

    public boolean returnBook(String bookId) {
        Book book = findBookById(bookId);
        if (book == null || book.isAvailable() || !borrowedBooks.containsKey(bookId)) return false;
        book.returnBook();
        borrowedBooks.remove(bookId);
        return true;
    }

    // Search Methods
    public List<Book> searchBooksByTitle(String title) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).toList();
    }

    public List<Book> searchBooksByAuthor(String author) {
        return books.stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).toList();
    }

    public List<Book> searchBooksByGenre(String genre) {
        return books.stream().filter(b -> b.getGenre().equalsIgnoreCase(genre)).toList();
    }

    public List<Book> filterBooksByAvailability(boolean available) {
        return books.stream().filter(b -> b.isAvailable() == available).toList();
    }

    // Helper Methods
    public Book findBookById(String id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    public Borrower findBorrowerById(String id) {
        return borrowers.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Book> getBooks() { return books; }
    public List<Borrower> getBorrowers() { return borrowers; }
}