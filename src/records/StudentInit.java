package records;

public class StudentInit {
    public static void main(String[] args) {
        Student student = new Student("Johnson", "johnson11@gmail.com");
        System.out.println(student.getName());
        StudentRecord studentRecord = new StudentRecord("Jane", "jane12@gmail.com");
        System.out.println(studentRecord.name());
//        StudentRecord.print();
    }
}
