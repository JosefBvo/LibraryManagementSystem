package services;

import models.Book;
import models.Borrower;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryService {
    private List<Book> books;
    private List<Borrower> borrowers;

    public LibraryService() {
        this.books = new ArrayList<>();
        this.borrowers = new ArrayList<>();
    }

    // Book Management
    public boolean addBook(Book book) {
        if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) return false;
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

    public List<Book> getAllBooks() { 
        return new ArrayList<>(books); 
    }

    // Borrower Management
    public boolean registerBorrower(Borrower borrower) {
        if (borrowers.stream().anyMatch(b -> b.getId().equals(borrower.getId()))) return false;
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

    public List<Borrower> getAllBorrowers() { 
        return new ArrayList<>(borrowers); 
    }

    // Borrow & Return
    public boolean borrowBook(String bookId, String borrowerId) {
        if (findBorrowerById(borrowerId) == null) return false;
        Book book = findBookById(bookId);
        if (book == null || !book.isAvailable()) return false;
        book.borrowBook();
        return true;
    }

    public boolean returnBook(String bookId) {
        Book book = findBookById(bookId);
        if (book == null || book.isAvailable()) return false;
        book.returnBook();
        return true;
    }

    // Search & Filter
    public List<Book> searchBooks(String query) {
        String q = query.toLowerCase();
        return books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(q) ||
                         b.getAuthor().toLowerCase().contains(q) ||
                         b.getGenre().toLowerCase().contains(q))
            .collect(Collectors.toList());
    }

    // Sorting Methods
    public List<Book> sortBooksByTitle() { //Will search and print in Alphabetical Order
        return books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<Book> sortBooksByAuthor() {
        return books.stream()
                .sorted(Comparator.comparing(Book::getAuthor)) //Will sort in Alphabetical order
                .collect(Collectors.toList());
    }

    public List<Book> sortBooksByGenre() {
        return books.stream()
                .sorted(Comparator.comparing(Book::getGenre))
                .collect(Collectors.toList());
    }

    public List<Book> sortBooksByAvailability() {
        return books.stream()
                .sorted(Comparator.comparing(Book::isAvailable).reversed())
                .collect(Collectors.toList());
    }

    // Filtering Methods
    public List<Book> filterAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Book> filterBorrowedBooks() {
        return books.stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }

    // Helper methods
    private Book findBookById(String id) {
        return books.stream()
            .filter(b -> b.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    private Borrower findBorrowerById(String id) {
        return borrowers.stream()
            .filter(b -> b.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // Data loading methods
    public void setBooks(List<Book> books) { 
        this.books = books; 
    }

    public void setBorrowers(List<Borrower> borrowers) { 
        this.borrowers = borrowers; 
    }
}