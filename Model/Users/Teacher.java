package Users;

import Enums.*;
import Interfaces.Researcher;
import Stuff.*;
import Utils.LanguageManager;

import java.util.*;

public class Teacher extends User implements Researcher {
    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.courses = new ArrayList<>();
        this.rating = 0;
    }

    private TeacherTitle title;
    private MajorSchools department;
    private boolean isProfessor;
    private List<Course> courses;
    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;
    private int rating;
    private int ratingCount = 0;

    @Override
    protected String getRolePrefix() {
        return "TEA";
    }

    @Override
    public void performDuties() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + LanguageManager.getMessage("teacher_menu"));
            System.out.println(LanguageManager.getMessage("teacher_option1"));
            System.out.println(LanguageManager.getMessage("teacher_option2"));
            System.out.println(LanguageManager.getMessage("teacher_option3"));
            System.out.println(LanguageManager.getMessage("teacher_option4"));
            System.out.println(LanguageManager.getMessage("teacher_option5"));
            System.out.println(LanguageManager.getMessage("teacher_option6"));
            System.out.print(LanguageManager.getMessage("choose_option") + ": ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println(LanguageManager.getMessage("enter_paper_details"));
                    String title = scanner.nextLine();
                    int citations = scanner.nextInt();
                    scanner.nextLine();
                    ResearchPaper paper = new ResearchPaper();
                    paper.setTitle(title);
                    paper.setCitations(citations);
                    addResearchPaper(paper);
                    System.out.println(LanguageManager.getMessage("teacher_research_paper_added_scs"));
                    break;
                case "2":
                    System.out.println(LanguageManager.getMessage("enter_lesson_id"));
                    String lessonId = scanner.nextLine();
                    Lesson lesson = null;
                    if (lessonId != null) {
                        try {
                            List<Student> students = viewStudents(lesson);
                            students.forEach(System.out::println);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println(LanguageManager.getMessage("lesson_not_found"));
                    }
                    break;
                case "3":
                    System.out.println(LanguageManager.getMessage("enter_student_id"));
                    String studentId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_course_id"));
                    String courseId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_mark_type"));
                    MarkType markType = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_grade_value"));
                    double gradeValue = scanner.nextDouble();
                    scanner.nextLine();

                    Student student = null;
                    Course course = null;
                    Lesson lessonForMark = null;
                    if (studentId != null && courseId != null && lessonForMark != null) {
                        try{
                            putMarks(student, course, markType, gradeValue, lessonForMark);
                            System.out.println(LanguageManager.getMessage("teacher_grade_assigned_scs"));
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println(LanguageManager.getMessage("invalid_data_input"));
                    }
                    break;
                case "4":
                    System.out.println(LanguageManager.getMessage("enter_project_topic"));
                    String topic = scanner.nextLine();
                    ResearchProject project = new ResearchProject();
                    project.setTopic(topic);
                    addResearchProject(project);
                    System.out.println(LanguageManager.getMessage("project_added_successfully"));
                    break;
                case "5":
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;
                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }
    }

    public void addCourses(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void sendComplaint(Student student, UrgencyLevel urgencyLevel, String content) {
        if (student == null) {
            throw new IllegalArgumentException(LanguageManager.getMessage("student_null_fl"));
        }

        Message complaint = new Message(this.getId(), student.getId(), content, MessageType.COMPLAINT, urgencyLevel);
        complaint.send();
    }

    public void putMarks(Student student, Course course, MarkType markType, double value, Lesson lesson) {
        if (!courses.contains(course)) {
            throw new IllegalArgumentException(LanguageManager.getMessage("teacher_not_assigned_to_course", getId(), course.getId()));
        }
        if (!course.getStudents().contains(student)) {
            throw new IllegalArgumentException(LanguageManager.getMessage("student_not_enrolled_to_course", student.getId(), course.getId()));
        }

        lesson.setMarksRecords();

        Mark studentMark = lesson.getMarksRecords().get(student);

        switch (markType) {
            case FIRST_ATTESTATION:
                studentMark.setFirstAttestation(value);
                break;
            case SECOND_ATTESTATION:
                studentMark.setSecondAttestation(value);
                break;
            case FINAL_EXAM:
                studentMark.setFinalExam(value);
                break;
            default:
                throw new IllegalArgumentException(LanguageManager.getMessage("invalid_mark_type"));
        }

        lesson.getMarksRecords().put(student, studentMark);
    }

    public List<Student> viewStudents(Lesson lesson) {
        if (!lesson.getInstructor().equals(this)) {
            throw new IllegalArgumentException(LanguageManager.getMessage("teacher_not_assigned_to_lesson", this.getId(), lesson.getId()));
        }

        return lesson.getStudents();
    }

    public void addResearchPaper(ResearchPaper paper) {
        if (!researchPapers.contains(paper)) {
            researchPapers.add(paper);
        }
    }

    public void addResearchProject(ResearchProject project) {
        if (!researchProjects.contains(project)) {
            researchProjects.add(project);
        }
    }

    public int calculateHIndex(){
        researchPapers.sort(Comparator.comparingInt(ResearchPaper::getCitations).reversed());
        int hIndex = 0;
        for (int i = 0; i < researchPapers.size(); i++) {
            if (researchPapers.get(i).getCitations() >= i + 1){
                hIndex = i + 1;
            } else {
                break;
            }
        }
        return hIndex;
    }

    public List<ResearchPaper> printPapers(ResearchSortCriteria criteria) {
        if (researchPapers.isEmpty()) {
            System.out.println(LanguageManager.getMessage("teacher_no_papers"));
            return researchPapers;
        }

        List<ResearchPaper> sortedPapers = new ArrayList<>(researchPapers);

        switch (criteria){
            case BY_DATE:
                sortedPapers.sort(Comparator.comparing(ResearchPaper::getPublicationDate));
                break;
            case BY_CITATIONS:
                sortedPapers.sort(Comparator.comparingInt(ResearchPaper::getCitations).reversed());
                break;
            case BY_PAGE_COUNT:
                sortedPapers.sort(Comparator.comparingInt(ResearchPaper::getPages));
                break;
            default:
                throw new IllegalArgumentException(LanguageManager.getMessage("invalid_criteria"));
        }

        return sortedPapers;
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

    public void setDepartment(MajorSchools department) {
        this.department = department;
    }

    public boolean getIsProfessor() {
        return isProfessor;
    }

    public void setIsProfessor(boolean isProfessor) {
        this.isProfessor = isProfessor;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public void setResearchPapers(List<ResearchPaper> researchPapers) {
        this.researchPapers = researchPapers;
    }

    public List<ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    public void setResearchProjects(List<ResearchProject> researchProjects) {
        this.researchProjects = researchProjects;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException(LanguageManager.getMessage("invalid_rating"));
        }
        this.rating = ((this.rating * this.ratingCount) + rating) / (++this.ratingCount);
    }

    public int getRatingCount() {
        return ratingCount;
    }

    @Override
    public UserRole getRole() {
        return UserRole.TEACHER;
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
        sb.append(", researchProjects=").append(researchProjects).append('\'');
        sb.append(", rating=").append(rating).append('\'');
        sb.append('}');
        return sb.toString();
    }
}