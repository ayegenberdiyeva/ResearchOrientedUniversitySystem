package Users;

public class Employee extends User {
    public Employee(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    private String position;
    private double salary;


    public void performDuties(){

    }

    @Override
    protected String getRolePrefix() {
        return "EMP";
    }

    public String getDetails() {
        // TODO implement here
        return "";
    }

    public void updatePosition(String newPosition){

    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append(super.toString());
        sb.append(", position='").append(position).append('\'');
        sb.append(", salary=").append(salary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}