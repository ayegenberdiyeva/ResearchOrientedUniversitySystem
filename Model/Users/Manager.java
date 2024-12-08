package Users;

import java.util.*;
import Stuff.Course;

public class Manager extends User {
    public Manager(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    private List<Course> courses;

    @Override
    protected String getRolePrefix() {
        return "MAN";
    }

    public void approveCourseRegistration(Student student, Course course) {
        // TODO implement here
    }

    public void assignTeacherToCourse(Teacher teacher, Course course) {
        // TODO implement here
    }

    public List<Course> viewCoursesByCriteria(String criteria) {
        // TODO implement here
        return null;
    }

    public void generatePerfomanceReport() {
        // TODO implement here
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", courses=").append(courses).append('\'');
        sb.append('}');
        return sb.toString();
    }
}