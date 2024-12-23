package src.Stuff;

import src.Users.Student;
import src.Utils.DatabaseConnection;
import src.Utils.LanguageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
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
        updateBook();
        System.out.println(LanguageManager.getMessage("book_added", book.getTitle(), quantity));
    }

    public void updateBook(){
        String query = "update book set count ? where id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            for (Map.Entry<Book, Integer> entry : books.entrySet()) {
                Book book = entry.getKey();
                int quantity = entry.getValue();

                ps.setInt(1, quantity);
                ps.setObject(2, book.getId());
                ps.addBatch();
            }


            ps.executeBatch();
            System.out.println("Book counts updated in database");
        } catch (SQLException e){
            System.out.println("Failed to update book counts: " + e.getMessage());
        }
    }

//    public boolean borrowBook(Student student, Book book) {
//        if (!books.containsKey(book) || books.get(book) <= 0) {
//            System.out.println(LanguageManager.getMessage("book_unavailable", book.getTitle()));
//            return false;
//        }
//
//        books.put(book, books.get(book) - 1);
//
//        Date borrowDate = new Date();
//        borrowRecords.computeIfAbsent(student, k -> new HashMap<>())
//                .put(book, borrowDate);
//        boolean recordSaved = saveBorrowRecords(book.getId(), student.getId(), borrowDate);
//        if (!recordSaved) {
//            System.out.println("failed to save borrow records");
//            return false;
//        }
//        System.out.println(LanguageManager.getMessage("book_borrowed", student.getId(), book.getTitle()));
//        return true;
//    }

    public boolean saveBorrowRecords(UUID bookId, UUID studentId, java.util.Date borrowDate) {
        String query = "insert into borrow_records (id, book_id, student_id, borrow_date) values (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            UUID borrowId = UUID.randomUUID();
            ps.setObject(1, borrowId);
            ps.setObject(2, bookId);
            ps.setObject(3, studentId);
            ps.setDate(4, new Date(borrowDate.getTime()));
            ps.executeUpdate();
            System.out.println("Borrow record saved to database");
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to save borrow record: " + e.getMessage());
            return false;
        }
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

//    public boolean isOverdue(Student student, Book book) {
//        if (!borrowRecords.containsKey(student) || !borrowRecords.get(student).containsKey(book)) {
//            return false;
//        }
//
//        Date borrowDate = borrowRecords.get(student).get(book);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(borrowDate);
//        cal.add(Calendar.WEEK_OF_YEAR, maxBorrowPeriod);
//        return new Date().after(cal.getTime());
//    }
}