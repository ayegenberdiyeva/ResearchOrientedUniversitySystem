package src.Users;

import src.Enums.UserRole;
import src.Utils.LanguageManager;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Admin extends User {
    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.users = new ArrayList<>();
    }

    private List<User> users;
    private static final String LOG_FILE = "logs/user-actions.log";

    @Override
    protected String getRolePrefix() {
        return "ADM";
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    @Override
    public void performDuties() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n" + LanguageManager.getMessage("admin_menu"));
            System.out.println(LanguageManager.getMessage("admin_menu_option1"));
            System.out.println(LanguageManager.getMessage("admin_menu_option2"));
            System.out.println(LanguageManager.getMessage("admin_menu_option3"));
            System.out.println(LanguageManager.getMessage("admin_menu_option4"));
            System.out.println(LanguageManager.getMessage("admin_menu_option5"));
            System.out.print(LanguageManager.getMessage("choose_option") + ": ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    User newUser = collectUserInput(scanner);
                    if (newUser != null) {
                        addUser(newUser);
                        System.out.println(LanguageManager.getMessage("user_added_successfully"));
                    }
                case "2":
                    System.out.println(LanguageManager.getMessage("enter_user_id_remove") + ": ");
                    String userIdToRemove = scanner.nextLine();
                    removeUser(userIdToRemove);
                    System.out.println(LanguageManager.getMessage("user_removed_successfully"));
                    break;
                case "3":
                    System.out.println(LanguageManager.getMessage("enter_user_id_update") + ": ");
                    String userIdToUpdate = scanner.nextLine();
                    User userToUpdate = findUserById(userIdToUpdate);
                    if (userToUpdate == null) {
                        System.out.println(LanguageManager.getMessage("user_not_found"));
                        break;
                    } else {
                        collectUserUpdates(scanner, userToUpdate);
                        updateUser(userToUpdate);
                        System.out.println(LanguageManager.getMessage("user_updated_successfully"));
                    }
                case "4":
                    System.out.println(LanguageManager.getMessage("logs_title"));
                    displayLogs();
                    break;
                case "5":
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;
                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }
    }

    private void writeLog(String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))){
            writer.write(LocalDateTime.now() + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(LanguageManager.getMessage("log_error", e.getMessage()));
        }
    }

    public List<String> viewLogs(){
        writeLog(LanguageManager.getMessage("viewing_logs"));
        try {
            return Files.readAllLines(Paths.get(LOG_FILE));
        } catch (IOException e) {
            System.out.println(LanguageManager.getMessage("log_error", e.getMessage()));
            return new ArrayList<>();
        }
    }

    public void displayLogs(){
        List<String> logs = viewLogs();
        if (logs.isEmpty()){
            System.out.println(LanguageManager.getMessage("no_logs"));
        } else {
            logs.forEach(System.out::println);
        }
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }

        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                writeLog(LanguageManager.getMessage("add_user_fl", user.getId()));
                return;
            }
        }

        users.add(user);
        UserCreator.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
        writeLog(LanguageManager.getMessage("add_user_scs", user.getId(), user.getFirstName(), user.getLastName()));
    }

    public void removeUser(String userId) {
        if (users == null || users.isEmpty()) {
            writeLog(LanguageManager.getMessage("remove_user_fl_no_users", userId));
            return;
        }

        for (User u : users) {
            if (u.getId().equals(userId)) {
                users.remove(u);
                writeLog(LanguageManager.getMessage("remove_user_scs", u.getId(), u.getFirstName(), u.getLastName()));
                return;
            }
        }

        writeLog(LanguageManager.getMessage("remove_user_fl_not_found", userId));
    }

    public void updateUser(User user) {
        if (users == null || users.isEmpty()) {
            writeLog(LanguageManager.getMessage("update_user_fl", user.getId()));
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                writeLog(LanguageManager.getMessage("update_user_scs", user.getId(), user.getFirstName(), user.getLastName()));
                return;
            }
        }

        System.out.println(LanguageManager.getMessage("update_user_fl", user.getId()));
    }

    public User findUserById(String userId) {
        for (User u : users) {
            if (u.getId().equals(userId)) {
                return u;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", users='").append(users).append('\'');
        sb.append('}');
        return sb.toString();
    }
}