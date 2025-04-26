package ui;

import models.Book;
import models.Borrower;
import services.FileHandler;
import services.LibraryService;
import services.LibraryService.ReturnResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static LibraryService libraryService = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load data
        libraryService.setBooks(FileHandler.loadBooks("books.txt"));
        libraryService.setBorrowers(FileHandler.loadBorrowers("borrowers.txt"));

        boolean running = true;
        while (running) {
            printMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: manageBooks(); break;
                    case 2: manageBorrowers(); break;
                    case 3: borrowBook(); break;
                    case 4: returnBook(); break;
                    case 5: searchBooks(); break;
                    case 6: sortAndFilterBooks(); break;
                    case 7: checkOverdueBooks(); break;
                    case 8: running = false; break;
                    default: System.out.println("Invalid choice. Please try again."); //Error handling
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 and 8."); 
            }
        }

        // Save data into corresponding .txt files
        FileHandler.saveBooks("books.txt", libraryService.getAllBooks());
        FileHandler.saveBorrowers("borrowers.txt", libraryService.getAllBorrowers());
        scanner.close();
        System.out.println("Exiting Library Management System. Goodbye!");
    }

    private static void printMainMenu() {
    	System.out.println("----------------------------------------------------------");//Helps with CLI readability
        System.out.println("\nWelcome to the Library Management System\n");
        System.out.println("1. Book Management");
        System.out.println("2. Borrower Management");
        System.out.println("3. Borrow a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. Search Books");
        System.out.println("6. Sort & Filter Books");
        System.out.println("7. Check Overdue Books");
        System.out.println("8. Exit");
        System.out.print("\nPlease Select an option: ");
    }

    private static void manageBooks() {
        while (true) {
            System.out.println("\nBook Management");
            System.out.println("1. Add Book");
            System.out.println("2. Edit Book");
            System.out.println("3. Delete Book");
            System.out.println("4. List All Books");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addBook(); break;
                case 2: editBook(); break;
                case 3: deleteBook(); break;
                case 4: listBooks(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        Book book = new Book(id, title, author, genre);
        if (libraryService.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Error: Book ID already exists.");
        }
    }

    private static void editBook() {
        System.out.print("Enter book ID to edit: ");
        String id = scanner.nextLine();
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new genre: ");
        String genre = scanner.nextLine();

        if (libraryService.editBook(id, title, author, genre)) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Error: Book not found.");
        }
    }

    private static void deleteBook() {
        System.out.print("Enter book ID to delete: ");
        String id = scanner.nextLine();
        if (libraryService.deleteBook(id)) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Error: Book not found.");
        }
    }

    private static void listBooks() {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    private static void manageBorrowers() {
        while (true) {
            System.out.println("\nBorrower Management");
            System.out.println("1. Register Borrower");
            System.out.println("2. Update Borrower");
            System.out.println("3. Delete Borrower");
            System.out.println("4. List All Borrowers");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: registerBorrower(); break;
                case 2: updateBorrower(); break;
                case 3: deleteBorrower(); break;
                case 4: listBorrowers(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void registerBorrower() {
        System.out.print("Enter borrower ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact details: ");
        String contact = scanner.nextLine();

        Borrower borrower = new Borrower(id, name, contact);
        if (libraryService.registerBorrower(borrower)) {
            System.out.println("Borrower registered successfully.");
        } else {
            System.out.println("Error: Borrower ID already exists.");
        }
    }

    private static void updateBorrower() {
        System.out.print("Enter borrower ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new contact details: ");
        String contact = scanner.nextLine();

        if (libraryService.updateBorrower(id, name, contact)) {
            System.out.println("Borrower updated successfully.");
        } else {
            System.out.println("Error: Borrower not found.");
        }
    }

    private static void deleteBorrower() {
        System.out.print("Enter borrower ID to delete: ");
        String id = scanner.nextLine();
        if (libraryService.deleteBorrower(id)) {
            System.out.println("Borrower deleted successfully.");
        } else {
            System.out.println("Error: Borrower not found.");
        }
    }

    private static void listBorrowers() {
        List<Borrower> borrowers = libraryService.getAllBorrowers();
        if (borrowers.isEmpty()) {
            System.out.println("No borrowers registered.");
        } else {
            borrowers.forEach(System.out::println);
        }
    }

    private static void borrowBook() {
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter borrower ID: ");
        String borrowerId = scanner.nextLine();

        if (libraryService.borrowBook(bookId, borrowerId)) {
            System.out.println("Book borrowed successfully. Due in 2 weeks.");
        } else {
            System.out.println("Failed to borrow book. Check IDs and availability.");
        }
    }

    private static void returnBook() {
        System.out.print("Enter book ID to return: ");
        String bookId = scanner.nextLine();

        ReturnResult result = libraryService.returnBook(bookId);
        if (result.isSuccess()) {
            if (result.getLateFee() > 0) {
                System.out.printf("Book returned successfully. Late fee: $%.2f%n", result.getLateFee());
            } else {
                System.out.println("Book returned successfully. No late fee.");
            }
        } else {
            System.out.println("Failed to return book. Check ID and status.");
        }
    }

    private static void searchBooks() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        List<Book> results = libraryService.searchBooks(query);
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void sortAndFilterBooks() {
        while (true) {
            System.out.println("\nSort & Filter Books");
            System.out.println("1. Sort by Title");
            System.out.println("2. Sort by Author");
            System.out.println("3. Sort by Genre");
            System.out.println("4. Sort by Availability");
            System.out.println("5. Filter Available Books");
            System.out.println("6. Filter Borrowed Books");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            List<Book> sortedOrFilteredBooks;
            switch (choice) {
                case 1:
                    sortedOrFilteredBooks = libraryService.sortBooksByTitle();
                    displayBooks("Books Sorted by Title", sortedOrFilteredBooks);
                    break;
                case 2:
                    sortedOrFilteredBooks = libraryService.sortBooksByAuthor();
                    displayBooks("Books Sorted by Author", sortedOrFilteredBooks);
                    break;
                case 3:
                    sortedOrFilteredBooks = libraryService.sortBooksByGenre();
                    displayBooks("Books Sorted by Genre", sortedOrFilteredBooks);
                    break;
                case 4:
                    sortedOrFilteredBooks = libraryService.sortBooksByAvailability();
                    displayBooks("Books Sorted by Availability", sortedOrFilteredBooks);
                    break;
                case 5:
                    sortedOrFilteredBooks = libraryService.filterAvailableBooks();
                    displayBooks("Available Books", sortedOrFilteredBooks);
                    break;
                case 6:
                    sortedOrFilteredBooks = libraryService.filterBorrowedBooks();
                    displayBooks("Borrowed Books", sortedOrFilteredBooks);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void checkOverdueBooks() {
        List<Book> overdueBooks = libraryService.getOverdueBooks();
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books found.");
        } else {
            System.out.println("\nOverdue Books:");
            overdueBooks.forEach(book -> {
                double fee = book.calculateLateFee();
                System.out.printf("%s (Late fee: $%.2f)%n", book, fee);
            });
            
            System.out.print("\nCheck late fees for a specific borrower? (y/n): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                System.out.print("Enter borrower ID: ");
                String borrowerId = scanner.nextLine();
                double totalFees = libraryService.calculateBorrowerLateFees(borrowerId);
                System.out.printf("Total late fees for borrower %s: $%.2f%n", borrowerId, totalFees);
            }
        }
    }

    private static void displayBooks(String title, List<Book> books) {
        System.out.println("\n" + title + ":");
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            books.forEach(System.out::println);
        }
    }
}