package src.Stuff;

import src.Utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Book {
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.id = UUID.randomUUID();
        insertIntoDatabase();
    }

    private void insertIntoDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()){
            String query = "insert into book (id, title, author, isbn) values (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)){
                ps.setObject(1, this.getId());
                ps.setObject(2, this.getTitle());
                ps.setObject(3, this.getAuthor());
                ps.setObject(4, this.getIsbn());
                ps.executeUpdate();
                System.out.println("Book inserted into database");
            }
        } catch (SQLException e) {
            System.out.println("Failed to insert book into database" + e.getMessage());
        }
    }

    private UUID id;
    private String title;
    private String author;
    private String isbn;

    private UUID generateId(){
        return UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public static Book fetchBookById(UUID id){
        String query = "select title, author, isbn from book where id = ?";
        Book book = null;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    book = new Book(rs.getString("title"), rs.getString("author"), rs.getString("isbn"));
                }
            }
        } catch (SQLException e){
            System.out.println("Failed to fetch book by id" + e.getMessage());
        }
        return book;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append('}');
        return sb.toString();
    }

}