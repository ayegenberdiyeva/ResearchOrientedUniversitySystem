package Users;

import Enums.UserRole;

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

    }

    private void writeLog(String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))){
            writer.write(LocalDateTime.now() + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while writing log file" + e.getMessage());
        }
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }

        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                writeLog("Attempted to add user with ID " + user.getId() + ", but user already exists.");
                return;
            }
        }

        users.add(user);
        writeLog("Added user with ID " + user.getId() + ": " + user.getFirstName() + " " + user.getLastName() + ".");
    }

    public void removeUser(String userId) {
        if (users == null || users.isEmpty()) {
            writeLog("Attempted to remove user with ID " + userId + ", but no users exist.");
            return;
        }

        for (User u : users) {
            if (u.getId().equals(userId)) {
                users.remove(u);
                writeLog("Removed user with ID " + u.getId() + ": " + u.getFirstName() + " " + u.getLastName() + ".");
                return;
            }
        }

        writeLog("Attempted to remove user with ID " + userId + ", but user was not found.");
    }

    public void updateUser(User user) {
        if (users == null || users.isEmpty()) {
            writeLog("Attempted to update user with ID " + user.getId() + ", but no users exist.");
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                writeLog("Updated user with ID " + user.getId() + ": " + user.getFirstName() + " " + user.getLastName() + ".");
                return;
            }
        }

        System.out.println("Attempted to update user with ID " + user.getId() + ", but user was not found.");
    }

    public List<String> viewLogs() {
        writeLog("Viewing logs");
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