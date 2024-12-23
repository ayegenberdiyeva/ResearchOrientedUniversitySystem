package src.Utils;

import src.Enums.MajorSchools;
import src.Stuff.Course;
import src.Users.Student;
import src.Users.Teacher;
import src.Users.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.UUID;

public class test {
    public static void main(String[] args) throws SQLException {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Failed to connect to the database");
            }

            System.out.println("Testing object creation...");
//            Teacher student = new Teacher("B", "A", "b.teacher@example.com", "p");
            Course course = new Course("OOP", 5, "Object-oriented-programming", MajorSchools.SCHOOL_OF_IT_AND_ENGINEERING);

//            verifyUserInDatabase(conn, student.getId());
//            verifyStudentInDatabase(conn, student.getId());


            System.out.println("Testing passed successfully...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void clearTestData(Connection conn, UUID id) {
//        try (Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate("DELETE FROM students WHERE id ILIKE 'test-%'");
//            stmt.executeUpdate("DELETE FROM users WHERE id ILIKE 'test-%'");
//            System.out.println("Test data cleared");
//        } catch (Exception e) {
//            System.out.println("Failed to clear test data: " + e.getMessage());
//        }
//    }

    private static void verifyUserInDatabase(Connection conn, UUID userId) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + userId + "'");
            if (rs.next()) {
                System.out.println("User data verified in database");
            } else {
                throw new AssertionError("User data not found in database");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify user in database: " + e.getMessage());
        }
    }

    private static void verifyStudentInDatabase(Connection conn, UUID studentId) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM students WHERE id = '" + studentId + "'");
            if (rs.next()) {
                System.out.println("Student data verified in database");
            } else {
                throw new AssertionError("Student data not found in database");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify student in database: " + e.getMessage());
        }
    }

    private static void verifyCourseInDatabase(Connection conn, UUID id) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE id = '" + id + "'");
            if (rs.next()) {
                System.out.println("Course data verified in database");
            } else {
                throw new AssertionError("Course data not found in database");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify course in database: " + e.getMessage());
        }
    }
}
