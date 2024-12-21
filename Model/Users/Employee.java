package Users;

import Enums.UserRole;
import Utils.LanguageManager;

import java.util.Scanner;

public class Employee extends User {
    public Employee(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.salary = 0;
    }

    private String position;
    private double salary;

    public UserRole getRole() {
        return UserRole.EMPLOYEE;
    }

    public void performDuties() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + LanguageManager.getMessage("employee_menu"));
            System.out.println("1. " + LanguageManager.getMessage("view_details"));
            System.out.println("2. " + LanguageManager.getMessage("update_position"));
            System.out.println("3. " + LanguageManager.getMessage("update_salary"));
            System.out.println("4. " + LanguageManager.getMessage("log_out"));
            System.out.println(LanguageManager.getMessage("choice_option") + ": ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println(getDetails());
                    break;
                case "2":
                    System.out.println(LanguageManager.getMessage("enter_new_position"));
                    String newPosition = scanner.nextLine();
                    updatePosition(newPosition);
                    System.out.println(LanguageManager.getMessage("position_updated"));
                    break;
                case "3":
                    System.out.println(LanguageManager.getMessage("enter_new_salary"));
                    double newSalary = scanner.nextDouble();
                    scanner.nextLine();
                    updateSalary(newSalary);
                    System.out.println(LanguageManager.getMessage("salary_updated"));
                    break;
                case "4":
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;
                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }
    }

    @Override
    protected String getRolePrefix() {
        return "EMP";
    }

    public String getDetails() {
        return String.format(
                "%s\n%s: %s\n%s: %2.f\n%s: %s %s\n%s: %s",
                LanguageManager.getMessage("employee_details_header"),
                LanguageManager.getMessage("position_lb"),
                position,
                LanguageManager.getMessage("salary_lb"),
                salary,
                LanguageManager.getMessage("full_name_lb"),
                getFirstName(),
                getLastName(),
                LanguageManager.getMessage("email_lb"),
                getEmail()
        );
    }

    public String getPosition() {
        return position;
    }

    public void updatePosition(String newPosition) {
        if (newPosition == null || newPosition.isEmpty()) {
            throw new IllegalArgumentException(LanguageManager.getMessage("invalid_position"));
        }
        this.position = newPosition;
    }

    public double getSalary() {
        return salary;
    }

    public void updateSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException(LanguageManager.getMessage("invalid_salary"));
        }
        this.salary = salary;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", position='").append(position).append('\'');
        sb.append(", salary=").append(salary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}