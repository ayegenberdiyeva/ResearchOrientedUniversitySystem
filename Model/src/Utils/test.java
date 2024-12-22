package src.Utils;

import src.Users.Student;
import src.Users.User;

import java.sql.Connection;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection db = new DatabaseConnection();
        db.getConnection();
        User user = new Student("a", "b", "c", "d");
//        Student stu1 = new Student("A", "A", "a", "a");
//        Student stu2 = new Student("B", "B", "b", "b");
    }
}
