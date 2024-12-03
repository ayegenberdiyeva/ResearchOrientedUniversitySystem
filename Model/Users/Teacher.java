package Users;

import Enums.TeacherTitle;
import Enums.UrgencyLevel;

import java.util.*;

public class Teacher {
    public Teacher() {
    }

    private TeacherTitle title;
    private String department;
    private boolean isProfessor;
    private List<Course> courses;
    private List<ResearchPaper> researchPapers;

    public void addCourses(Course course) {
        // TODO implement here
    }

    public void sendComplaint(Student student, UrgencyLevel urgencyLevel) {
        // TODO implement here
    }

    public void putMarks(Student student, Mark mark) {
        // TODO implement here
    }

    public List<Student> viewStudents() {
        // TODO implement here
        return null;
    }

    public void addResearchPaper(ResearchPaper paper) {
        // TODO implement here
    }

    public List<ResearchPaper> getResearchPapers() {
        // TODO implement here
        return null;
    }

}