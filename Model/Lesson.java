import Enums.LessonType;

import java.util.*;

public class Lesson {
    public Lesson() {
    }

    private String lessonId;
    private String lessonName;
    private LessonType type;
    private Course course;
    private List<Student> students;
    private Teacher instructor;
    private Map<Student, Integer> attendanceRecords;

    public void assignInstructor(Teacher teacher) {
        // TODO implement here
    }

    public void addStudent(Student student) {
        // TODO implement here
    }

    public void removeStudent(Student student) {
        // TODO implement here
    }

    public void conductLesson() {
        // TODO implement here
    }
}