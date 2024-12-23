package src.Users;

import java.util.*;

import src.Enums.ManagerType;
import src.Enums.UserRole;
import src.Stuff.Course;
import src.Utils.LanguageManager;

public class Manager extends User {
    public Manager(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.courses = new ArrayList<>();
    }

    private ManagerType managerType;
    private List<Course> courses;

    @Override
    protected String getRolePrefix() {
        return "MAN";
    }

    public UserRole getRole() {
        return UserRole.MANAGER;
    }

    @Override
    public void performDuties() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + LanguageManager.getMessage("manager_menu"));
            System.out.println("1. " + LanguageManager.getMessage("approve_course_registration"));
            System.out.println("2. " + LanguageManager.getMessage("assign_teacher_to_course"));
            System.out.println("3. " + LanguageManager.getMessage("view_courses"));
            System.out.println("4. " + LanguageManager.getMessage("generate_performance_report"));
            System.out.println("5. " + LanguageManager.getMessage("log_out"));
            System.out.println(LanguageManager.getMessage("choice_option") + ": ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println(LanguageManager.getMessage("enter_student_id"));
                    String studentId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_course_id"));
                    String courseId = scanner.nextLine();
                    Student student = Student.findStudentById(UUID.fromString(studentId));
                    Course course = Course.fetchCourseById(UUID.fromString(courseId));
                    if (student != null && course != null) {
                        approveCourseRegistration(student, course);
                    } else {
                        System.out.println(LanguageManager.getMessage("invalid_student_or_course"));
                    }
                    break;
                case "2":
                    System.out.println(LanguageManager.getMessage("enter_teacher_id"));
                    String teacherId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_course_id"));
                    String courseIdForTeacher = scanner.nextLine();
                    Teacher teacher = Teacher.fetchTeacherById(UUID.fromString(teacherId));
                    Course courseForTeacher = Course.fetchCourseById(UUID.fromString(courseIdForTeacher));
                    if (teacherId != null && courseIdForTeacher != null) {
                        assignTeacherToCourse(teacher, courseForTeacher);
                    } else {
                        System.out.println(LanguageManager.getMessage("invalid_teacher_or_course"));
                    }
                    break;
                case "3":
                    System.out.println(LanguageManager.getMessage("enter_search_criteria"));
                    String searchCriteria = scanner.nextLine();
                    List<Course> filteredCourses = viewCoursesByCriteria(searchCriteria);
                    filteredCourses.forEach(course1 -> System.out.println(course1.getName()));
                    break;
                case "4":
                    generatePerfomanceReport();
                    break;
                case "5":
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;
                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }
    }

    public void approveCourseRegistration(Student student, Course course) {
        if (student == null || course == null) {
            System.out.println(LanguageManager.getMessage("invalid_student_or_course"));
            return;
        }

        if (!courses.contains(course)) {
            System.out.println(LanguageManager.getMessage("course_not_managed", course.getName()));
            return;
        }
        student.registerCourse(course);
        System.out.println(LanguageManager.getMessage("course_registration_approved", student.getId()));
    }

    public void assignTeacherToCourse(Teacher teacher, Course course) {
        if (teacher == null || course == null) {
            System.out.println(LanguageManager.getMessage("invalid_teacher_or_course"));
            return;
        }
        if (!courses.contains(course)) {
            courses.add(course);
        }
        teacher.addCourses(course);
        System.out.println(LanguageManager.getMessage("teacher_assigned", teacher.getId()));
    }

    public List<Course> viewCoursesByCriteria(String criteria) {
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getName().toLowerCase().contains(criteria.toLowerCase())) {
                filteredCourses.add(course);
            }
        }
        System.out.println(LanguageManager.getMessage("filtered_courses", filteredCourses.size()));
        return filteredCourses;
    }

    public void generatePerfomanceReport() {
        if (courses.isEmpty()) {
            System.out.println(LanguageManager.getMessage("no_courses_managed"));
            return;
        }
        System.out.println(LanguageManager.getMessage("performance_report_header"));
        for (Course course : courses) {
            System.out.println(LanguageManager.getMessage("course_performance", course.getName(), course.getAverageGrade()));
        }
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", managerType=").append(managerType).append('\'');
        sb.append(", courses=").append(courses).append('\'');
        sb.append('}');
        return sb.toString();
    }
}