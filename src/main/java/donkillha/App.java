package donkillha;

import donkillha.controller.DatabaseManager;
import donkillha.controller.TablesCreator;
import donkillha.model.Admin;
import donkillha.model.SchoolClass;
import donkillha.model.Teacher;
import donkillha.model.Student;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        // In case there wasn't a database ready yet
        DatabaseManager mysql = new DatabaseManager();
        TablesCreator tablesCreator = new TablesCreator(mysql);
        // This method will make the tables in case they don't exist and handles the
        // foreign keys too
        tablesCreator.initialise();
        Admin admin = new Admin(mysql);
        Student student = new Student("K", "O", 13, 12, 1997, "Computer Science", 27);
        Teacher johnDoe = new Teacher("John", "Doe", 168473);
        SchoolClass mathmaticsClass = new SchoolClass("Mathematics", johnDoe);
        SchoolClass physicsClass = new SchoolClass("Physics", johnDoe);
        admin.addNewClass("Mathematics", johnDoe);
        admin.addNewClass("Physics", johnDoe);
        admin.addStudentToClass(new Student[] { student }, mathmaticsClass);
        admin.addStudentToClass(new Student[] { student }, physicsClass);
        admin.removeStudentFromClass(student, mathmaticsClass);

    }
}
