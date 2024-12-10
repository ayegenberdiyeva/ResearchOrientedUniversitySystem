package Stuff;

import Enums.LessonType;
import Users.Student;
import Users.Teacher;

import java.util.*;

public class Lesson {
    public Lesson(String id, String name, LessonType type, Course course, Teacher instructor, int rateOfLessonsPerWeek) {
        this.id = id;
        this.type = type;
        this.course = course;
        this.instructor = instructor;
        this.rateOfLessonsPerWeek = rateOfLessonsPerWeek;
        this.students = new ArrayList<>();
        this.numberOfStudents = 0;
    }

    private String id;
    private LessonType type;
    private Course course;
    private List<Student> students;
    private Teacher instructor;
    private int numberOfStudents;
    private int rateOfLessonsPerWeek;
    private Map<Student, Integer> attendanceRecords;

    public void assignInstructor(Teacher teacher) {
        this.instructor = teacher;
    }

    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
            numberOfStudents++;
        }
    }

    public void removeStudent(Student student) {
        if (students != null && !students.isEmpty()) {
            this.students.removeIf(s -> s.getId().equals(student.getId()));
            numberOfStudents--;
        }
    }

    public void conductLesson() {
        // TODO implement here
    }

    public String getId() {
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
        sb.append('}');
        return sb.toString();
    }
}