package Users;

import Enums.UserRole;
import java.util.UUID;

public abstract class User {
    public User(String firstName, String lastName, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = PasswordUtils.hashPassword(password);
        this.id = generateId();
        this.isLoggedIn = false;
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
            System.out.println(getFirstName() + " " + getLastName() + " logged in.");
            return true;
        } else {
            System.out.println("Invalid email or password.");
            return false;
        }
    }

    public void logout() {
        if (isLoggedIn) {
            this.isLoggedIn = false;
            System.out.println(getFirstName() + " " + getLastName() + " logged out.");
        } else {
            System.out.println("User is already logged out.");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;
        return id != null && id.equals(other.id);
    }

    public abstract UserRole getRole();

    public abstract void performDuties();

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
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