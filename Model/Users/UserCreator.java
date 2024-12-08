package Users;

import Enums.UserRole;

public class UserCreator {
    public static User createUser(String firstName, String lastName, String email, String password, UserRole role) {
        switch (role) {
            case ADMIN:
                return new Admin(firstName, lastName, email, password);
            case STUDENT:
                return new Student(firstName, lastName, email, password);
            case TEACHER:
                return new Teacher(firstName, lastName, email, password);
            case MANAGER:
                return new Manager(firstName, lastName, email, password);
            case EMPLOYEE:
                return new Employee(firstName, lastName, email, password);
            case LIBRARIAN:
                return new Librarian(firstName, lastName, email, password);
            default:
                throw new IllegalArgumentException("Invalid role" + role);
        }
    }
}
