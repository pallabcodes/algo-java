package records;

// with record, these parameter are immutable (assignment once) and these parameters are likes constructor parameters for class
public record StudentRecord(String name, String email) {
    public StudentRecord(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static void print() {
        System.out.println("printing");
    }

}

