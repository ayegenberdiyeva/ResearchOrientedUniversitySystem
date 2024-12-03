package Stuff;

import Users.Student;

import java.util.*;

public class Library {
    public Library() {
    }

    private Map<Book, Integer> books;
    private List<Student> borrowers;
    private int maxBorrowPeriod = 6;

    public void addBook(Book book, int quantity) {
        // TODO implement here
    }

    public boolean borrowBook(Student student, Book book) {
        // TODO implement here
        return false;
    }

    public boolean returnBooks(Student student, Book book) {
        // TODO implement here
        return false;
    }

    public void viewAvailableBooks() {
        // TODO implement here
    }

    public boolean isOverdue(Student student, Book book) {
        // TODO implement here
        return false;
    }
}