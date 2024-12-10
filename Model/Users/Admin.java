package Users;

import Enums.UserRole;
import Utils.LanguageManager;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

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
                    System.out.println(LanguageManager.getMessage("enter_user_details") + ": ");
                    System.out.println(LanguageManager.getMessage("enter_first_name") + ": ");
                    String firstName = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_last_name") + ": ");
                    String lastName = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_email") + ": ");
                    String email = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("enter_password") + ": ");
                    String password = scanner.nextLine();
                    System.out.println(LanguageManager.getMessage("choose_role") + ": ");
                    int role_choice = scanner.nextInt();
                    scanner.nextLine();
                    UserRole userRole = UserRole.values()[role_choice-1];

                    try {
                        User newUser = UserCreator.createUser(firstName, lastName, email, password, userRole);
                        addUser(newUser);
                        System.out.println(LanguageManager.getMessage("user_added_successfully"));
                    } catch (IllegalArgumentException e) {
                        System.out.println(LanguageManager.getMessage("invalid_role"));
                    }
                    break;
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
                    }

                    System.out.println(LanguageManager.getMessage("enter_user_fname_update") + ": ");
                    String fnameToUpdate = scanner.nextLine();
                    if (!fnameToUpdate.equals("-")) userToUpdate.setFirstName(fnameToUpdate);

                    System.out.println(LanguageManager.getMessage("enter_user_lname_update") + ": ");
                    String lnameToUpdatee = scanner.nextLine();
                    if (!lnameToUpdatee.equals("-")) userToUpdate.setLastName(lnameToUpdatee);

                    System.out.println(LanguageManager.getMessage("enter_user_email_update") + ": ");
                    String emailToUpdate = scanner.nextLine();
                    if (!emailToUpdate.equals("-")) userToUpdate.setEmail(emailToUpdate);

                    System.out.println(LanguageManager.getMessage("enter_user_password_update") + ": ");
                    String passwordToUpdate = scanner.nextLine();
                    if (!passwordToUpdate.equals("-")) userToUpdate.setPassword(passwordToUpdate);

                    updateUser(userToUpdate);
                    System.out.println(LanguageManager.getMessage("user_updated_successfully"));
                    break;
                case "4":
                    System.out.println(LanguageManager.getMessage("logs_title"));
                    List<String> logs = viewLogs();
                    for (String log : logs) {
                        System.out.println(log);
                    }
                    break;
                case "5":
                    System.out.println(LanguageManager.getMessage("exiting"));
                    return;

                default:
                    System.out.println(LanguageManager.getMessage("invalid_option"));
            }
        }

        //TODO fill the performduties
    }

    private void writeLog(String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))){
            writer.write(LocalDateTime.now() + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(LanguageManager.getMessage("log_error", e.getMessage()));
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

    public List<String> viewLogs() {
        writeLog(LanguageManager.getMessage("viewing_logs"));
        return new ArrayList<>();
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