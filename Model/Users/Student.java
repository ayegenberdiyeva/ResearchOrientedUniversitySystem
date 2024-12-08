package Users;

import Stuff.*;
import Enums.MajorSchools;
import java.util.*;
import java.time.LocalDate;

public class Student extends User {
    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.year = LocalDate.now().getYear();
    }

    private double gpa;
    private int year;
    private MajorSchools major;
    private List<Course> courses;
    private List<ResearchPaper> researchPapers;



    @Override
    protected String getRolePrefix() {
        return year+"B";
    }

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

    public double getGPA(){
        return gpa;
    }

    public int getYear(){
        return year;
    }

    public MajorSchools getMajor(){
        return major;
    }

    public void setMajor(MajorSchools major){
        this.major = major;
    }

    public List<Course> getCourses(){
        return courses;
    }

    public List<ResearchPaper> researchPapers(){
        return researchPapers;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", GPA='").append(gpa).append('\'');
        sb.append(", year=").append(year).append('\'');
        sb.append(", major=").append(major).append('\'');
        sb.append(", courses=").append(courses).append('\'');
        sb.append(", researchPapers=").append(researchPapers).append('\'');
        sb.append('}');
        return sb.toString();
    }
}