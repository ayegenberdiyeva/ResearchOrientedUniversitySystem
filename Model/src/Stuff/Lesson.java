package src.Stuff;

import src.Enums.LessonType;
import src.Users.Student;
import src.Users.Teacher;
import src.Utils.DatabaseConnection;
import src.Utils.LanguageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Lesson {
    public Lesson(LessonType type, Course course, Teacher instructor, int rateOfLessonsPerWeek) {
        this.id = generateId();
        this.type = type;
        this.course = course;
        this.instructor = instructor;
        this.rateOfLessonsPerWeek = rateOfLessonsPerWeek;
        this.students = new ArrayList<>();
        this.attendanceRecords = new HashMap<>();
        this.marksRecords = new HashMap<>();
        this.numberOfStudents = 0;
        insertIntoDatabase();
    }

    private void insertIntoDatabase(){
        try (Connection conn = DatabaseConnection.getConnection()){
            String query = "insert into lessons (id, course_id, instructor_id, type, rate_per_week) VALUES (?, ?, ?, ?::lesson_type, ?);";
            try (PreparedStatement ps = conn.prepareStatement(query)){
                ps.setObject(1, this.getId());
                ps.setObject(2, this.getCourse().getId());
                ps.setObject(3, this.getInstructor().getId());
                ps.setString(4, this.getType().name());
                ps.setInt(5, this.getRateOfLessonsPerWeek());
                ps.executeUpdate();
                System.out.println("Lesson inserted into database");
            }
        } catch (SQLException e){
            System.out.println("Failed to insert lesson into database" + e.getMessage());
        }
    }

    private UUID id;
    private LessonType type;
    private Course course;
    private List<Student> students;
    private Teacher instructor;
    private int numberOfStudents;
    private int rateOfLessonsPerWeek;
    private Map<Student, Integer> attendanceRecords;
    private Map<Student, Mark> marksRecords;

    private UUID generateId() {
        return UUID.randomUUID();
    }

    public void assignInstructor(Teacher teacher) {
        this.instructor = teacher;
    }

    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
            numberOfStudents = students.size();
        }
    }

    public void removeStudent(Student student) {
        if (students != null && !students.isEmpty()) {
            this.students.removeIf(s -> s.getId().equals(student.getId()));
            numberOfStudents = students.size();
        }
    }

    public void conductLesson(List<Student> attendedStudents) {
        if (students.isEmpty()) {
            System.out.println(LanguageManager.getMessage("empty_lesson", this.id));
            return;
        }

        for (Student student : attendedStudents) {
            if (attendanceRecords.containsKey(student)) {
                attendanceRecords.put(student, attendanceRecords.getOrDefault(student, 0) + 1);
            }
        }
        System.out.println(LanguageManager.getMessage("conducted_lesson", this.id));
    }

    public double calculateAttendance(Student student) {
        if (rateOfLessonsPerWeek == 0 || !attendanceRecords.containsKey(student)) {
            System.out.println(LanguageManager.getMessage("student_not_found", this.id));
            return 0;
        }

        int numberOfLessons = rateOfLessonsPerWeek*15;
        return attendanceRecords.get(student)*100 / numberOfLessons;
    }



    public void setMarksRecords() {
        if (students.isEmpty()) {
            System.out.println(LanguageManager.getMessage("empty_lesson", this.id));
            return;
        }
        for (Student student : students) {
            marksRecords.putIfAbsent(student, new Mark());
        }
    }

    public static Lesson fetchLessonById(UUID id) {
        String query = "select * from lessons where id = ?;";
        Lesson lesson = null;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LessonType type = LessonType.valueOf(rs.getString("type"));
                    Course course = Course.fetchCourseById((UUID) rs.getObject("course_id"));
                    Teacher instructor = Teacher.fetchTeacherById((UUID) rs.getObject("instructor_id"));
                    int rateOfLessonsPerWeek = rs.getInt("rate_per_week");

                    lesson = new Lesson(type, course, instructor, rateOfLessonsPerWeek);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch lesson from database" + e.getMessage());
        }
        return lesson;
    }

    public UUID getId() {
        return id;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public int getNumberOfStudents() {
        this.numberOfStudents = students.size();
        return numberOfStudents;
    }

    public int getRateOfLessonsPerWeek() {
        return rateOfLessonsPerWeek;
    }

    public Map<Student, Integer> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(Map<Student, Integer> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    public Map<Student, Mark> getMarksRecords() {
        return marksRecords;
    }

    public void setMarksRecords(Map<Student, Mark> marksRecords) {
        this.marksRecords = marksRecords;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", type=").append(type);
        sb.append(", course=").append(course);
        sb.append(", instructor=").append(instructor);
        sb.append(", numberOfStudents=").append(numberOfStudents);
        sb.append(", rateOfLessonsPerWeek=").append(rateOfLessonsPerWeek);
        sb.append(", students=").append(students);
        sb.append(", attendanceRecords=").append(attendanceRecords);
        sb.append(", marksRecords=").append(marksRecords);
        sb.append('}');
        return sb.toString();
    }
}