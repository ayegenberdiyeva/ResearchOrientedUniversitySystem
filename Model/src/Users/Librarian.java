package src.Users;

import src.Enums.UserRole;
import src.Stuff.Book;
import src.Utils.LanguageManager;

import java.util.*;

public class Librarian extends User {
    public Librarian(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.inventory = new HashMap<>();
    }

    private Map<Book, Integer> inventory;

    @Override
    protected String getRolePrefix() {
        return "LIB";
    }

    public UserRole getRole() {
        return UserRole.LIBRARIAN;
    }

    @Override
    public void performDuties() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + LanguageManager.getMessage("librarian_menu"));
            System.out.println("1. " + LanguageManager.getMessage("add_book"));
            System.out.println("2. " + LanguageManager.getMessage("remove_book"));
            System.out.println("3. " + LanguageManager.getMessage("borrow_book"));
            System.out.println("4. " + LanguageManager.getMessage("return_book"));
            System.out.println("5. " + LanguageManager.getMessage("view_inventory"));
            System.out.println("6. " + LanguageManager.getMessage("log_out"));
            System.out.print(LanguageManager.getMessage("choose_option") + ": ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println(LanguageManager.getMessage("enter_book_title"));
                    String title = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_book_author"));
                    String author = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_book_isbn"));
                    String isbn = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_quantity"));
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    Book book = new Book(title, author, isbn);
                    addBook(book, quantity);
                    break;
                case "2":
                    System.out.println(LanguageManager.getMessage("enter_book_title"));
                    String removeTitle = scanner.nextLine();
                    Book bookToRemove = inventory.keySet().stream()
                            .filter(b -> b.getTitle().equals(removeTitle))
                            .findFirst().orElse(null);
                    if (bookToRemove != null) {
                        removeBook(bookToRemove);
                    } else {
                        System.out.println(LanguageManager.getMessage("book_not_found", removeTitle));
                    }
                    break;
                case "3":
                    System.out.println(LanguageManager.getMessage("enter_student_id"));
                    String studentId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_book_title"));
                    String borrowTitle = scanner.nextLine();
                    Book bookToBorrow = inventory.keySet().stream()
                            .filter(b -> b.getTitle().equals(borrowTitle))
                            .findFirst().orElse(null);
                    Student student = null;
                    if (studentId != null && bookToBorrow != null) {
                        borrowBook(student, bookToBorrow);
                    } else {
                        System.out.println(LanguageManager.getMessage("invalid_student_or_book"));
                    }
                    break;
                case "4":
                    System.out.println(LanguageManager.getMessage("enter_student_id"));
                    String returnStudentId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_book_title"));
                    String returnBookTitle = scanner.nextLine();
                    Book bookToReturn = inventory.keySet().stream()
                            .filter((b -> b.getTitle().equals(returnBookTitle)))
                            .findFirst().orElse(null);
                    Student returnStudent = null;
                    if (returnStudentId != null && bookToReturn != null) {
                        returnBook(returnStudent, bookToReturn);
                    } else {
                        System.out.println(LanguageManager.getMessage("invalid_student_or_book"));
                    }
                    break;
                case "5":
                    System.out.println(LanguageManager.getMessage("inventory_header"));
                    inventory.forEach((b, q) -> System.out.println(b.getTitle() + " - " + q));
                    break;
                case "6":
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;
                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }
    }

    public void addBook(Book book, int quantity) {
        if (book == null || quantity <= 0) {
            System.out.println(LanguageManager.getMessage("invalid_book_or_quantity"));
            return;
        }
        inventory.put(book, inventory.getOrDefault(book, 0) + quantity);
        System.out.println(LanguageManager.getMessage("book_added", book.getTitle(), quantity));
    }

    public void removeBook(Book book) {
        if (!inventory.containsKey(book)) {
            System.out.println(LanguageManager.getMessage("book_not_found", book.getTitle()));
            return;
        }
        inventory.remove(book);
        System.out.print(LanguageManager.getMessage("book_removed", book.getTitle()));
    }

    public boolean borrowBook(Student student, Book book) {
        if (!inventory.containsKey(book) || inventory.get(book) <= 0) {
            System.out.println(LanguageManager.getMessage("book_unavailable", book.getTitle()));
            return false;
        }
        inventory.put(book, inventory.get(book) - 1);
        System.out.println((LanguageManager.getMessage("book_borrowed", book.getTitle())));
        return true;
    }

    public boolean returnBook(Student student, Book book) {
        if (!inventory.containsKey(book)) {
            inventory.put(book, 1);
        } else {
            inventory.put(book, inventory.get(book) + 1);
        }
        System.out.println(LanguageManager.getMessage("book_returned", student.getId(), book.getTitle()));
        return true;
    }

    public Map<Book, Integer> getInventory() {
        return inventory;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", Inventory='").append(inventory).append('\'');
        sb.append('}');
        return sb.toString();
    }
}