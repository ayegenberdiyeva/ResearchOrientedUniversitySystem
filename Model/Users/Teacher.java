package Users;

import Enums.MajorSchools;
import Enums.TeacherTitle;
import Enums.UrgencyLevel;
import Stuff.*;
import java.util.*;

public class Teacher extends User {
    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    private TeacherTitle title;
    private MajorSchools department;
    private boolean isProfessor;
    private List<Course> courses;
    private List<ResearchPaper> researchPapers;

    @Override
    protected String getRolePrefix() {
        return "TEA";
    }

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

    public TeacherTitle getTitle() {
        return title;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public MajorSchools getDepartment() {
        return department;
    }

    public boolean getIsProfessor() {
        return isProfessor;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", title='").append(title).append('\'');
        sb.append(", department=").append(department).append('\'');
        sb.append(", isProfessor=").append(isProfessor).append('\'');
        sb.append(", courses=").append(courses).append('\'');
        sb.append(", researchPapers=").append(researchPapers).append('\'');
        sb.append('}');
        return sb.toString();
    }
}