package src;

import src.Users.*;
import src.Enums.UserRole;
import src.Utils.LanguageManager;

import java.util.Scanner;

public class Main {
    private static User currentUser; // Logged-in user
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initial admin account
        currentUser = new Admin("Admin", "User", "admin@system.com", "admin123");
        System.out.println("Default Admin account created. Use email 'admin@system.com' and password 'admin123' to log in.");

        while (true) {
            if (currentUser == null || !currentUser.getIsLoggedIn()) {
                displayMainMenu();
            } else {
                currentUser.performDuties();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Exit Program");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                System.out.println("Exiting the program. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleLogin() {
        System.out.print("\n--- Login ---\nEnter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (currentUser != null && currentUser.getEmail().equals(email) && currentUser.login(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    private static void createUserMenu(Admin admin) {
        System.out.println("\n--- Create User ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Select User Role:");
        for (int i = 0; i < UserRole.values().length; i++) {
            System.out.printf("%d. %s%n", i + 1, UserRole.values()[i]);
        }

        try {
            System.out.print("Choose role (1-" + UserRole.values().length + "): ");
            int roleChoice = Integer.parseInt(scanner.nextLine());
            UserRole role = UserRole.values()[roleChoice - 1];
            User newUser = UserCreator.createUser(firstName, lastName, email, password, role);
            admin.addUser(newUser);
            System.out.println("User created successfully!");
        } catch (Exception e) {
            System.out.println("Invalid role selection. User creation failed.");
        }
    }

    private static void logout() {
        if (currentUser != null && currentUser.getIsLoggedIn()) {
            currentUser.logout();
            currentUser = null;
            System.out.println("Logged out successfully.");
        } else {
            System.out.println("No user currently logged in.");
        }
    }
}