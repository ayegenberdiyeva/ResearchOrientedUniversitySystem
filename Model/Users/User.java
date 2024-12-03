package Users;

import Enums.UseRole;

public abstract class User extends Student {
    public User() {
    }

    private String id;
    private String name;
    private String email;
    private String password;
    private UseRole role;

    public void login(String email, String password) {
        // TODO implement here
    }

    public void logout() {
        // TODO implement here
    }

    public boolean equals(Object obj) {
        // TODO implement here
        return false;
    }

    public abstract UseRole getRole();

    public abstract void performDuties();

    public String toString() {
        // TODO implement here
        return "";
    }
}