package Users;

import Stuff.Book;

import java.util.*;

public class Librarian extends User {
    public Librarian(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    private Map<Book, Integer> inventory;

    @Override
    protected String getRolePrefix() {
        return "LIB";
    }

    public void addBook(Book book, int quantity) {
        // TODO implement here
    }

    public void removeBook(Book book) {
        // TODO implement here
    }

    public boolean borrowBook(Student student, Book book) {
        // TODO implement here
        return false;
    }

    public boolean returnBook(Student student, Book book) {
        // TODO implement here
        return false;
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