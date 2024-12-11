package Users;

import Enums.ResearchSortCriteria;
import Enums.UserRole;
import Interfaces.Researcher;
import Stuff.*;
import Enums.MajorSchools;
import Utils.LanguageManager;

import javax.management.relation.Role;
import java.util.*;
import java.time.LocalDate;

public class Student extends User implements Researcher {
    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.year = LocalDate.now().getYear();
    }

    private double gpa;
    private int year;
    private MajorSchools major;
    private List<Course> courses;
    private Map<Course, Mark> marks;
    private List<ResearchProject> researchProjects;
    private List<ResearchPaper> researchPapers;
    private boolean isEligibleForResearch;

    @Override
    public void performDuties() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + LanguageManager.getMessage("student_menu"));
            System.out.println(LanguageManager.getMessage("student_option1"));
            System.out.println(LanguageManager.getMessage("student_option2"));
            System.out.println(LanguageManager.getMessage("student_option3"));
            System.out.println(LanguageManager.getMessage("student_option4"));
            System.out.println(LanguageManager.getMessage("student_option5"));
            System.out.print(LanguageManager.getMessage("choose_option") + ": ");

            String option = scanner.nextLine();
            switch (option) {
                case "1": // Register for a course
                    System.out.println(LanguageManager.getMessage("enter_course_id"));
                    String courseId = scanner.nextLine();
                    Course course = /* fetch course by ID */ null; // Add logic to fetch course
                    if (course != null) {
                        registerCourse(course);
                        System.out.println(LanguageManager.getMessage("course_registered_successfully"));
                    } else {
                        System.out.println(LanguageManager.getMessage("course_not_found"));
                    }
                    break;
                case "2": // View transcript
                    System.out.println(viewTranscript());
                    break;
                case "3": // Rate a teacher
                    System.out.println(LanguageManager.getMessage("enter_teacher_id"));
                    String teacherId = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_rating"));
                    int rating = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Teacher teacher = /* fetch teacher by ID */ null; // Add logic to fetch teacher
                    if (teacher != null) {
                        rateTeacher(teacher, rating);
                        System.out.println(LanguageManager.getMessage("teacher_rated_successfully"));
                    } else {
                        System.out.println(LanguageManager.getMessage("teacher_not_found"));
                    }
                    break;
                case "4": // Add research paper/project
                    if (isEligibleForResearch()) {
                        System.out.println(LanguageManager.getMessage("enter_paper_or_project"));
                        String choice = scanner.nextLine();
                        if (choice.equalsIgnoreCase("paper")) {
                            System.out.println(LanguageManager.getMessage("enter_paper_title"));
                            String title = scanner.nextLine();
                            ResearchPaper paper = new ResearchPaper();
                            paper.setTitle(title);
                            addResearchPaper(paper);
                            System.out.println(LanguageManager.getMessage("paper_added_successfully"));
                        } else if (choice.equalsIgnoreCase("project")) {
                            System.out.println(LanguageManager.getMessage("enter_project_topic"));
                            String topic = scanner.nextLine();
                            ResearchProject project = new ResearchProject();
                            project.setTopic(topic);
                            addResearchProject(project);
                            System.out.println(LanguageManager.getMessage("project_added_successfully"));
                        } else {
                            System.out.println(LanguageManager.getMessage("invalid_input"));
                        }
                    } else {
                        System.out.println(LanguageManager.getMessage("not_eligible_for_research"));
                    }
                    break;
                case "5": // Exit
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;
                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }
    }

    @Override
    protected String getRolePrefix() {
        return year+"B";
    }

    public UserRole getRole() {
        return UserRole.STUDENT;
    }

    public void registerCourse(Course course) {
        courses.add(course);
    }

    public List<Course> viewCourses() {
        return this.getCourses();
    }

    public String viewTranscript() {
        if (marks == null || marks.isEmpty()) {
            System.out.println(LanguageManager.getMessage("student_marks_empty"));
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(LanguageManager.getMessage("student_marks", this.getId()));
        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();
            result.append("\n\t").append(course.getName());
            result.append("First Attestation: ").append(mark.getFirstAttestation());
            result.append("Second Attestation: ").append(mark.getSecondAttestation());
            result.append("Final Exam: ").append(mark.getFinalExam());
            result.append("\n\t").append("Final Grade: ").append(mark.calculateFinalGrade());
            result.append("Grade Symbol: ").append(mark.getMarkSymbol());
        }

        return result.toString();
    }

    public void rateTeacher(Teacher teacher, int rating) {
        if (rating < 1 || rating > 10){
            System.out.println(LanguageManager.getMessage("invalid_rating"));
        }
        teacher.setRating(rating);
    }

    public void addResearchPaper(ResearchPaper paper) {
        if (isEligibleForResearch && !researchPapers.contains(paper)) {
            researchPapers.add(paper);
        }
    }

    public void addResearchProject(ResearchProject project) {
        if (isEligibleForResearch && !researchProjects.contains(project)) {
            researchProjects.add(project);
        }
    }

    public int calculateHIndex(){
        researchPapers.sort((a, b) -> Integer.compare(b.getCitations(), a.getCitations()));
        int hIndex = 0;
        for (int i = 0; i < researchPapers.size(); i++) {
            if (researchPapers.get(i).getCitations() >= i + 1) {
                hIndex++;
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

    public double getGPA(){
        return gpa;
    }

    public void setGPA(double gpa){
        this.gpa = gpa;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
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

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    public List<ResearchPaper> getResearchPapers(){
        return researchPapers;
    }

    public void setResearchPapers(List<ResearchPaper> researchPapers){
        this.researchPapers = researchPapers;
    }

    public List<ResearchProject> getResearchProjects(){
        return researchProjects;
    }

    public void setResearchProjects(List<ResearchProject> researchProjects){
        this.researchProjects = researchProjects;
    }

    public boolean isEligibleForResearch(){
        return isEligibleForResearch;
    }

    public void setEligibleForResearch(boolean eligibleForResearch){
        isEligibleForResearch = eligibleForResearch;
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