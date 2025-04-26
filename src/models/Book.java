package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;
    private LocalDate dueDate;
    private String borrowedBy;

    public Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
        this.dueDate = null;
        this.borrowedBy = null;
    }

    // Getter functions
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public LocalDate getDueDate() { return dueDate; }
    public String getBorrowedBy() { return borrowedBy; }

    // Setter functions 
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setBorrowedBy(String borrowerId) { this.borrowedBy = borrowerId; }

    // Functional Methods
    public void borrowBook(String borrowerId) {
        this.isAvailable = false;
        this.dueDate = LocalDate.now().plusWeeks(2); // Return due date calculated 
        this.borrowedBy = borrowerId;
    }
    
    public void returnBook() {
        this.isAvailable = true;
        this.dueDate = null;
        this.borrowedBy = null;
    }
    
    public double calculateLateFee() {
        if (dueDate == null || isAvailable) {
            return 0.0;
        }
        long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return daysLate > 0 ? daysLate * .50 : 0.0;
    }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : 
            "Borrowed by " + borrowedBy + " (Due: " + dueDate + ")";
        return String.format("%s - %s by %s [%s]", id, title, author, status);
    }
}