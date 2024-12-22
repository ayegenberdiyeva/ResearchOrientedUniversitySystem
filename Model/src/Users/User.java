package src.Users;

import src.Enums.UserRole;
import src.Utils.LanguageManager;
import src.Utils.PasswordUtils;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public abstract class User {
    public User(String firstName, String lastName, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = PasswordUtils.hashPassword(password);
        this.id = generateId();
        this.isLoggedIn = false;
        //here to implement insertion  into database
    }

    public void insert_row(Connection conn) {
        Statement statement;
        try {
            String query = String.format("insert into users (id, first_name, last_name, email, password)  values ('%s', '%s', '%s', '%s', '%s')", this.getId(), this.getFirstName(), this.getLastName(), this.getEmail(), this.getPassword());
            statement = conn.createStatement();
            statement.execute(query);
            System.out.println("Inserted row into users table");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isLoggedIn;

    protected abstract String getRolePrefix();

    private String generateId(){
        return getRolePrefix() + UUID.randomUUID().toString();
    }

    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(PasswordUtils.hashPassword(password))) {
            this.isLoggedIn = true;
            System.out.println(LanguageManager.getMessage("login_scs", getFirstName(), getLastName()));
            return true;
        } else {
            System.out.println(LanguageManager.getMessage("login_fl"));
            return false;
        }
    }

    public void logout() {
        if (isLoggedIn) {
            this.isLoggedIn = false;
            System.out.println(LanguageManager.getMessage("logout_scs", getFirstName(), getLastName()));
        } else {
            System.out.println(LanguageManager.getMessage("logout_fl"));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return id != null && id.equals(other.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    public User collectUserInput(Scanner scanner) {
        System.out.println(LanguageManager.getMessage("user_collect_first_name") + ": ");
        String firstName_u = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("user_collect_last_name") + ": ");
        String lastName_u = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("user_collect_email") + ": ");
        String email_u = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("user_collect_password") + ": ");
        String password_u = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("user_choose_role") + ": ");
        System.out.println(LanguageManager.getMessage("role_choice"));

        try{
            int role_choice = Integer.parseInt(scanner.nextLine());
            UserRole userRole = UserRole.values()[role_choice-1];
            return UserCreator.createUser(firstName_u, lastName_u, email_u, password_u, userRole);
        } catch (Exception e) {
            System.out.println(LanguageManager.getMessage("invalid_role"));
            return null;
        }
    }

    public void collectUserUpdates(Scanner scanner, User user) {
        System.out.println(LanguageManager.getMessage("user_collect_first_name") + LanguageManager.getMessage("user_update") + ": ");
        String firstName_u = scanner.nextLine();
        if (!firstName_u.equals("-")) user.setFirstName(firstName_u);
        System.out.println(LanguageManager.getMessage("user_collect_last_name") + LanguageManager.getMessage("user_update") + ": ");
        String lastName_u = scanner.nextLine();
        if (!lastName_u.equals("-")) user.setLastName(lastName_u);
        System.out.println(LanguageManager.getMessage("user_collect_email") + LanguageManager.getMessage("user_update") + ": ");
        String email_u = scanner.nextLine();
        if (!email_u.equals("-")) user.setEmail(email_u);
        System.out.println(LanguageManager.getMessage("user_collect_password") + LanguageManager.getMessage("user_update") + ": ");
        String password_u = scanner.nextLine();
        if (!password_u.equals("-")) user.setPassword(password_u);
    }

    public abstract UserRole getRole();

    public abstract void performDuties();

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("id='").append(id).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}