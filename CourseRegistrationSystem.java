import java.util.*;
class Course {
    String code;
    String title;
    String description;
    int capacity;
    int enrolled;
    String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public boolean hasAvailableSlots() {
        return enrolled < capacity;
    }

    public void enrollStudent() {
        if (hasAvailableSlots()) {
            enrolled++;
        }
    }

    public void dropStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }
}

class Student {
    String id;
    String name;
    List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.hasAvailableSlots() && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.enrollStudent();
            System.out.println("Successfully registered for course: " + course.title);
        } else {
            System.out.println("Course registration failed. Course may be full or already registered.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            System.out.println("Successfully dropped course: " + course.title);
        } else {
            System.out.println("Course drop failed. You are not registered for this course.");
        }
    }

    public void listRegisteredCourses() {
        System.out.println("Registered courses for " + name + ":");
        for (Course course : registeredCourses) {
            System.out.println(course.code + " - " + course.title);
        }
    }
}

public class CourseRegistrationSystem {
    private static final Map<String, Course> courses = new HashMap<>();
    private static final Map<String, Student> students = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();

        while (true) {
            System.out.println("\nCourse Registration System Menu:");
            System.out.println("1. List available courses");
            System.out.println("2. Register student for course");
            System.out.println("3. Drop student from course");
            System.out.println("4. List student's registered courses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listAvailableCourses();
                    break;
                case 2:
                    registerStudentForCourse();
                    break;
                case 3:
                    dropStudentFromCourse();
                    break;
                case 4:
                    listStudentRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeData() {
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "MWF 9-10AM"));
        courses.put("MATH101", new Course("MATH101", "Calculus I", "Introduction to calculus", 25, "TTh 11-12:30PM"));
        courses.put("ENG101", new Course("ENG101", "English Literature", "Study of English literature", 20, "MWF 10-11AM"));

        students.put("S001", new Student("S001", "Alice"));
        students.put("S002", new Student("S002", "Bob"));
    }

    private static void listAvailableCourses() {
        System.out.println("Available courses:");
        for (Course course : courses.values()) {
            System.out.println(course.code + " - " + course.title + " (" + course.enrolled + "/" + course.capacity + ")");
            System.out.println("  Description: " + course.description);
            System.out.println("  Schedule: " + course.schedule);
        }
    }

    private static void registerStudentForCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Invalid student ID.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);
        if (course == null) {
            System.out.println("Invalid course code.");
            return;
        }

        student.registerCourse(course);
    }

    private static void dropStudentFromCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Invalid student ID.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);
        if (course == null) {
            System.out.println("Invalid course code.");
            return;
        }

        student.dropCourse(course);
    }

    private static void listStudentRegisteredCourses() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Invalid student ID.");
            return;
        }

        student.listRegisteredCourses();
    }
}
