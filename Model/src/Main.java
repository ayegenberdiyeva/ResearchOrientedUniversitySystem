package src;

import src.Users.*;
import src.Enums.*;

public class Main {
    public static void main(String[] args) {
        User admin = UserCreator.createUser("A", "B", "a@a", "qwerty", UserRole.ADMIN);
        User stu1 = UserCreator.createUser("C", "D", "c@c", "qwertyu", UserRole.STUDENT);

        System.out.println(admin);
        System.out.println(stu1);
    }
}