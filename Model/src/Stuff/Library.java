package src.Stuff;

import src.Users.Student;
import src.Utils.LanguageManager;

import java.util.*;

public class Library {
    public Library() {
        this.books = new HashMap();
        this.borrowRecords = new HashMap();
        this.borrowers = new ArrayList();
    }

    private Map<Book, Integer> books;
    private Map<Student, Map<Book, Date>> borrowRecords;
    private List<Student> borrowers;
    private int maxBorrowPeriod = 6;

    public void addBook(Book book, int quantity) {
        if (book == null || quantity <= 0) {
            System.out.println(LanguageManager.getMessage("invalid_book_or_quantity"));
            return;
        }
        books.put(book, books.getOrDefault(book, 0) + quantity);
        System.out.println(LanguageManager.getMessage("book_added", book.getTitle(), quantity));
    }

    public boolean borrowBook(Student student, Book book) {
        if (!books.containsKey(book) || books.get(book) <= 0) {
            System.out.println(LanguageManager.getMessage("book_unavailable", book.getTitle()));
            return false;
        }

        books.put(book, books.get(book) - 1);
        borrowRecords.computeIfAbsent(student, k -> new HashMap<>())
                .put(book, new Date());
        System.out.println(LanguageManager.getMessage("book_borrowed", student.getId(), book.getTitle()));
        return true;
    }

    public boolean returnBooks(Student student, Book book) {
        if (!borrowRecords.containsKey(student) || !borrowRecords.get(student).containsKey(book)) {
            System.out.println(LanguageManager.getMessage("book_not_borrowed", book.getTitle()));
            return false;
        }

        books.put(book, books.getOrDefault(book, 0) + 1);
        borrowRecords.get(student).remove(book);

        if (borrowRecords.get(student).isEmpty()) {
            borrowRecords.remove(student);
        }

        System.out.println(LanguageManager.getMessage("book_returned", student.getId(), book.getTitle()));
        return true;
    }

    public void viewAvailableBooks() {
        System.out.println(LanguageManager.getMessage("available_books"));
        books.forEach((book, quantity) -> System.out.println(book.getTitle() + " - " + quantity));
    }

    public boolean isOverdue(Student student, Book book) {
        if (!borrowRecords.containsKey(student) || !borrowRecords.get(student).containsKey(book)) {
            return false;
        }

        Date borrowDate = borrowRecords.get(student).get(book);
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.WEEK_OF_YEAR, maxBorrowPeriod);
        return new Date().after(cal.getTime());
    }
}