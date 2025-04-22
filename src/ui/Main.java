package ui;

import models.Book;
import models.Borrower;
import services.FileHandler;
import services.LibraryService;
import java.util.*;

public class Main {
    private static final String BOOKS_FILE = "books.txt";
    private static final String BORROWERS_FILE = "borrowers.txt";
    private static LibraryService library = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> manageBorrowers();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> exit = true;
                default -> System.out.println("Invalid choice!");
            }
        }
        saveData();
        scanner.close();
    }

    private static void loadData() {
        library.getBooks().addAll(FileHandler.loadBooks(BOOKS_FILE));
        library.getBorrowers().addAll(FileHandler.loadBorrowers(BORROWERS_FILE));
    }

    private static void saveData() {
        FileHandler.saveBooks(BOOKS_FILE, library.getBooks());
        FileHandler.saveBorrowers(BORROWERS_FILE, library.getBorrowers());
    }

    private static void printMainMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Manage Books");
        System.out.println("2. Manage Borrowers");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Books");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    private static void manageBooks() {
        boolean back = false;
        while (!back) {
            System.out.println("\nBook Management");
            System.out.println("1. Add Book");
            System.out.println("2. Edit Book");
            System.out.println("3. Delete Book");
            System.out.println("4. List Books");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            int choice = getIntInput();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> editBook();
                case 3 -> deleteBook();
                case 4 -> listBooks();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        if (library.findBookById(id) != null) {
            System.out.println("Book ID exists!");
            return;
        }
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        library.addBook(new Book(id, title, author, genre));
        System.out.println("Book added!");
    }

    private static void editBook() {
        System.out.print("Enter book ID to edit: ");
        Book book = library.findBookById(scanner.nextLine());
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        System.out.print("New title (" + book.getTitle() + "): ");
        String title = scanner.nextLine();
        System.out.print("New author (" + book.getAuthor() + "): ");
        String author = scanner.nextLine();
        System.out.print("New genre (" + book.getGenre() + "): ");
        String genre = scanner.nextLine();
        library.editBook(book.getId(), title, author, genre);
        System.out.println("Book updated!");
    }

    private static void deleteBook() {
        System.out.print("Enter book ID to delete: ");
        String id = scanner.nextLine();
        if (library.deleteBook(id)) System.out.println("Book deleted!");
        else System.out.println("Book not found!");
    }

    private static void listBooks() {
        library.getBooks().forEach(System.out::println);
    }

    private static void manageBorrowers() {
        // Similar structure to manageBooks()
        // Implement addBorrower, editBorrower, deleteBorrower, listBorrowers
    }

    private static void borrowBook() {
        System.out.print("Enter borrower ID: ");
        String borrowerId = scanner.nextLine();
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();
        if (library.borrowBook(bookId, borrowerId)) System.out.println("Book borrowed!");
        else System.out.println("Borrow failed. Check IDs and availability.");
    }

    private static void returnBook() {
        System.out.print("Enter book ID to return: ");
        String bookId = scanner.nextLine();
        if (library.returnBook(bookId)) System.out.println("Book returned!");
        else System.out.println("Return failed. Check book ID and status.");
    }

    private static void searchBooks() {
        System.out.println("Search by: 1-Title 2-Author 3-Genre 4-Availability");
        int choice = getIntInput();
        List<Book> results = switch (choice) {
            case 1 -> library.searchBooksByTitle(prompt("Title: "));
            case 2 -> library.searchBooksByAuthor(prompt("Author: "));
            case 3 -> library.searchBooksByGenre(prompt("Genre: "));
            case 4 -> library.filterBooksByAvailability(prompt("Available (true/false): ").equalsIgnoreCase("true"));
            default -> { System.out.println("Invalid choice!"); yield new ArrayList<>(); }
        };
        results.forEach(System.out::println);
    }

    private static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }
}