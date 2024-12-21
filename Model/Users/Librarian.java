package Users;

import Stuff.Book;
import Utils.LanguageManager;

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