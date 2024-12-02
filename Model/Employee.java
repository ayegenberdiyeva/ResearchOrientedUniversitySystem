import java.io.*;
import java.util.*;

public abstract class Employee extends Manager {
    public Employee() {
    }

    private String employeeId;
    private String name;
    private String position;
    private double salary;
    public abstract void performDuties();

    public String getDetails() {
        // TODO implement here
        return "";
    }

    public abstract void updatePosition(String newPosition);
}