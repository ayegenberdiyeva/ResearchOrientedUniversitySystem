import java.io.*;
import java.util.*;

public class Student {
    public Student() {
    }

    private double gpa;
    private int year;
    private String major;
    private List<Course> courses;
    private List<ResearchPaper> researchPapers;

    public void registerCourse(Course course) {
        // TODO implement here
    }

    public List<Course> viewCourses() {
        // TODO implement here
        return null;
    }

    public String viewTranscript() {
        // TODO implement here
        return "";
    }

    public void rateTeacher(Teacher teacher, int rating) {
        // TODO implement here
    }

    public void addResearchPaper(ResearchPaper paper) {
        // TODO implement here
    }

    public List<ResearchPaper> getResearchPapers() {
        // TODO implement here
        return null;
    }
}