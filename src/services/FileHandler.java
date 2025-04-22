package services;

import models.Book;
import models.Borrower;
import java.io.*;
import java.util.*;

public class FileHandler {
    public static List<Book> loadBooks(String path) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 5) continue;
                Book book = new Book(data[0], data[1], data[2], data[3]);
                if (!Boolean.parseBoolean(data[4])) book.borrowBook();
                books.add(book);
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    public static void saveBooks(String path, List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Book book : books) {
                bw.write(String.join(",",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        String.valueOf(book.isAvailable())));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public static List<Borrower> loadBorrowers(String path) {
        List<Borrower> borrowers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 3);
                if (data.length < 3) continue;
                borrowers.add(new Borrower(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            System.err.println("Error loading borrowers: " + e.getMessage());
        }
        return borrowers;
    }

    public static void saveBorrowers(String path, List<Borrower> borrowers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Borrower b : borrowers) {
                bw.write(String.join(",",
                        b.getId(),
                        b.getName(),
                        b.getContactDetails()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving borrowers: " + e.getMessage());
        }
    }
}