package src.Stuff;

import src.Enums.MajorSchools;
import src.Users.Student;
import src.Users.Teacher;
import src.Utils.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Course {
    public Course(String name, int credits, String description, MajorSchools majorSchool) {
        this.id = generateId();
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.majorSchool = majorSchool;
        this.students = new ArrayList<>();
        this.instructors = new ArrayList<>();
        insertIntoDatabase();
    }

    private void insertIntoDatabase(){
        try (Connection conn = DatabaseConnection.getConnection()){
            String query = "insert into courses (id, name, credits, description, major_school) values (?, ?, ?, ?, ?::major_school);";
            try (PreparedStatement ps = conn.prepareStatement(query)){
                ps.setObject(1, this.getId());
                ps.setString(2, this.getName());
                ps.setInt(3, this.getCredits());
                ps.setString(4, this.getDescription());
                ps.setString(5, this.getMajorSchool().name());
                ps.executeUpdate();
                System.out.println("Course inserted into database");
            }
        } catch (SQLException e) {
            System.out.println("Failed to insert course into database" + e.getMessage());
        }
    }

    private UUID id;
    private String name;
    private int credits;
    private String description;
    private MajorSchools majorSchool;
    private List<Student> students;
    private List<Teacher> instructors;

    public UUID generateId(){
        return UUID.randomUUID();
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        if (students != null && !students.isEmpty()) {
            students.removeIf(s -> s.getId().equals(student.getId()));
        }
    }

    public void assignInstructor(Teacher teacher) {
        if (!instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    public void removeInstructor(Teacher teacher) {
        if (instructors != null && !instructors.isEmpty()) {
            instructors.removeIf(i -> i.getId().equals(teacher.getId()));
        }
    }

    public double getAverageGrade() {
        if (students.isEmpty()) {
            return -1;
        }

        double totalGrade = 0;
        int count = 0;

        for (Student student : students) {
            Map<Course, Mark> marks = student.getMarks();
            if (marks != null && marks.containsKey(this)) {
                totalGrade += marks.get(this).calculateFinalGrade();
                count++;
            }
        }
        return count > 0 ? totalGrade / count : -1;
    }

    public static Course fetchCourseById(UUID id) {
        String query = "select * from courses where id = ?";
        Course course = null;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)){
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    course = new Course(rs.getString("title"), rs.getInt("credits"), rs.getString("description"), MajorSchools.valueOf(rs.getString("major_school")));
                }
            }
        } catch (SQLException e){
            System.out.println("Failed to fetch course from database" + e.getMessage());
        }

        return course;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MajorSchools getMajorSchool() {
        return majorSchool;
    }

    public void setMajorSchool(MajorSchools majorSchool) {
        this.majorSchool = majorSchool;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getInstructors() {
        return instructors;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", credits='").append(credits).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", students=").append(students);
        sb.append(", instructors=").append(instructors);
        sb.append('}');
        return sb.toString();
    }

}