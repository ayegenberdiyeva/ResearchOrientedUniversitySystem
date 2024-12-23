package src.Utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

public class LanguageManager {
    private static Locale currentLocale = Locale.ENGLISH;
//    private static ResourceBundle bundle = ResourceBundle.getBundle("messages", currentLocale);
//
//    public static void setLanguage(String language) {
//        currentLocale = new Locale(language);
//        bundle = ResourceBundle.getBundle("messages", currentLocale);
//    }
//
//    public static String getMessage(String key, Object... args) {
//        if (bundle == null) {
//            return switch (key) {
//                case "login_scs" -> "Login successful!";
//                case "login_fl" -> "Login failed.";
//                default -> "Message not found for key: " + key;
//            };
//        }
//        String message = bundle.getString(key);
//        return String.format(message, args);
//    }

    private static ResourceBundle bundle;

    static {
        try {
            // Try to load the resource bundle
            bundle = ResourceBundle.getBundle("messages", currentLocale);
        } catch (MissingResourceException e) {
            System.err.println("Error: Resource bundle 'messages' not found! Defaulting to hardcoded messages.");
            bundle = null; // Set to null if the bundle cannot be loaded
        }
    }

    public static void setLanguage(String language) {
        currentLocale = new Locale(language);
        try {
            bundle = ResourceBundle.getBundle("messages", currentLocale);
        } catch (MissingResourceException e) {
            System.err.println("Error: Resource bundle 'messages' not found for language: " + language);
            bundle = null; // Reset to null if the bundle is not found
        }
    }

    public static String getMessage(String key, Object... args) {
        // Use hardcoded messages if the bundle is null
        if (bundle == null) {
            return getFallbackMessage(key, args);
        }
        try {
            String message = bundle.getString(key);
            return String.format(message, args);
        } catch (MissingResourceException e) {
            // Fallback if the specific key is not found
            return getFallbackMessage(key, args);
        }
    }

    private static String getFallbackMessage(String key, Object... args) {
        return switch (key) {
            // User-related messages
            case "login_scs" -> "Logged in.";
            case "login_fl" -> "Invalid email or password.";
            case "logout_scs" -> String.format("%s %s is logged out.", args);
            case "logout_fl" -> "User is already logged out.";
            case "user_collect_first_name" -> "Enter first name";
            case "user_collect_last_name" -> "Enter last name";
            case "user_collect_email" -> "Enter email";
            case "user_collect_password" -> "Enter password";
            case "user_choose_role" -> "Choose a role";
            case "user_update" -> " or \"-\" without updates";
            case "role_choice" -> "Choose role: 1-ADMIN, 2-STUDENT, 3-TEACHER, 4-MANAGER, 5-LIBRARIAN, 6-EMPLOYEE";
            case "invalid_role" -> "The role you entered is invalid. Please try again.";

            // Admin-related messages
            case "add_user_scs" -> String.format("Added user with ID %s: %s %s.", args);
            case "add_user_fl" -> String.format("Attempted to add user with ID %s, but user already exists.", args);
            case "remove_user_scs" -> String.format("Removed user with ID %s: %s %s.", args);
            case "remove_user_fl_not_found" -> String.format("Attempted to remove user with ID %s, but user was not found.", args);
            case "remove_user_fl_no_users" -> String.format("Attempted to remove user with ID %s, but no users exist.", args);
            case "update_user_scs" -> String.format("Updated user with ID %s: %s %s.", args);
            case "update_user_fl" -> String.format("Attempted to update user with ID %s, but user was not found.", args);
            case "log_error" -> String.format("An error occurred while writing log file: %s", args);
            case "no_logs" -> "Log file is empty.";
            case "viewing_logs" -> "Viewing logs.";
            case "admin_menu" -> "Admin Menu:";
            case "admin_menu_option1" -> "1. Add User";
            case "admin_menu_option2" -> "2. Remove User";
            case "admin_menu_option3" -> "3. Update User";
            case "admin_menu_option4" -> "4. View Logs";
            case "admin_menu_option5" -> "5. Exit";
            case "choose_option" -> "Choose an option";
            case "user_added_successfully" -> "User added successfully!";
            case "enter_user_id_remove" -> "Enter the ID of the user to remove";
            case "user_removed_successfully" -> "User removed successfully!";
            case "enter_user_id_update" -> "Enter the ID of the user to update";
            case "enter_user_fname_update" -> "If you need to update, enter first name of user, \"-\" otherwise";
            case "enter_user_lname_update" -> "If you need to update, enter last name of user, \"-\" otherwise";
            case "enter_user_email_update" -> "If you need to update, enter email name of user, \"-\" otherwise";
            case "enter_user_password_update" -> "If you need to update, enter password name of user, \"-\" otherwise";
            case "user_not_found" -> "User not found.";
            case "user_updated_successfully" -> "User updated successfully!";
            case "logs_title" -> "Logs:";
            case "exiting" -> "Exiting...";
            case "invalid_option" -> "Invalid option. Please try again.";

            // Course-related messages
            case "course_add_student_scs" -> String.format("Student with ID %s has been successfully enrolled to course %s", args);
            case "course_add_student_fl_contains" -> String.format("Student with ID %s already enrolled to this course.", args);

            // Lesson-related messages
            case "empty_lesson" -> String.format("No students assigned to lesson with ID %s.", args);
            case "conducted_lesson" -> String.format("Lesson with ID %s is conducted.", args);
            case "student_not_found" -> String.format("Student not found in records of lesson with ID %s.", args);

            // Teacher-related messages
            case "teacher_not_assigned_to_course" -> String.format("Teacher with ID %s does not teach course with ID %s.", args);
            case "student_not_enrolled_to_course" -> String.format("Student with ID %s is not enrolled in course with ID %s.", args);
            case "invalid_mark_type" -> "Invalid mark type specified.";
            case "teacher_not_assigned_to_lesson" -> String.format("Teacher with ID %s does not assigned to lesson with ID %s.", args);
            case "teacher_is_not_professor" -> String.format("Teacher with ID %s is not a professor.", args);
            case "student_null_fl" -> "Student cannot be null";
            case "teacher_no_papers" -> "No research papers available.";
            case "invalid_criteria" -> "Unsupported sort criteria.";
            case "invalid_rating" -> "Rating must be in range 1 and 10.";

            // Default message
            default -> "Message not found for key: " + key;
        };
    }
}
