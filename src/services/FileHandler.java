package services;

import models.Book;
import models.Borrower;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static List<Book> loadBooks(String filename) {
        List<Book> books = new ArrayList<>();
        File file = new File(filename);
        
        // Create file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating books file: " + e.getMessage());
                return books;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Book book = new Book(parts[0], parts[1], parts[2], parts[3]);
                    book.setAvailable(Boolean.parseBoolean(parts[4]));
                    
                    // Handle due date if exists
                    if (parts.length > 5 && !parts[5].equals("null")) {
                        book.setDueDate(LocalDate.parse(parts[5], DATE_FORMATTER));
                    }
                    
                    // Handle borrower ID if exists
                    if (parts.length > 6) {
                        book.setBorrowedBy(parts[6]);
                    }
                    
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    public static void saveBooks(String filename, List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                String dueDateStr = book.getDueDate() != null ? 
                                  DATE_FORMATTER.format(book.getDueDate()) : "null";
                String borrowedBy = book.getBorrowedBy() != null ? 
                                  book.getBorrowedBy() : "null";
                
                writer.write(String.join(",",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        String.valueOf(book.isAvailable()),
                        dueDateStr,
                        borrowedBy));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public static List<Borrower> loadBorrowers(String filename) {
        List<Borrower> borrowers = new ArrayList<>();
        File file = new File(filename);
        
        // Create file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating borrowers file: " + e.getMessage());
                return borrowers;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    borrowers.add(new Borrower(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading borrowers: " + e.getMessage());
        }
        return borrowers;
    }

    public static void saveBorrowers(String filename, List<Borrower> borrowers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Borrower borrower : borrowers) {
                writer.write(String.join(",",
                        borrower.getId(),
                        borrower.getName(),
                        borrower.getContactDetails()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving borrowers: " + e.getMessage());
        }
    }
}