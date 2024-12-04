package donkillha.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import donkillha.controller.DatabaseManager;
import java.util.List;

public class Admin {
    DatabaseManager mysql;

    public Admin(DatabaseManager mysql) {
        this.mysql = mysql;
    }

    // --- Students ---
    public void addStudentToClass(Student[] students, SchoolClass classConcerned) {
        if (students != null && students.length > 0) {
            for (int i = 0; i < students.length; i++) {
                Student student = students[i];
                List<Object> args = new ArrayList<>();
                args.add(student.getFirstName());
                args.add(student.getLastName());
                args.add(student.dateOfBirth());
                args.add(student.getMajor());
                args.add(student.getStudentId());
                args.add(classConcerned.getName());
                String query = "INSERT INTO Students(first_name,last_name,date_of_birth,major,student_id,class_id) VALUES (?,?,(STR_TO_DATE(?,'%d-%m-%Y')),?,?,(SELECT id FROM Classes WHERE class_name=?) )";
                this.mysql.executeQuery(query, args);
            }

        }
    }

    public void removeStudentFromClass(Student student, SchoolClass classConcerned) {
        int id = this.getId("SELECT id FROM Students WHERE first_name=? AND last_name=? AND major = ? AND student_id = ? AND class_id=(SELECT id FROM Classes WHERE class_name=?)",List.of(student.getFirstName(),student.getLastName(),student.getMajor(),student.getStudentId(),classConcerned.getName()));
        this.mysql.executeQuery("DELETE FROM Students WHERE id = ?",List.of(id));
    }

    public void updateStudentInClass(Student student, String classConcerned) {

    }

    // --- Classes ---
    public void addNewClass(String className, Teacher teacher) {
        List<Object> args = new ArrayList<>();
        args.add(className);
        args.add(teacher.getTeacherId());
        this.mysql.executeQuery(
                "INSERT INTO Classes(class_name,teached_by) VALUES (?,(SELECT id FROM Teachers WHERE teacher_id = ? ))",
                args);
        System.err.println("Class " + className + " has been added. It is taught by teacher " + teacher.toString());
    }

    public void removeClass(String className, Teacher teacher) {

        // teached_by is optional, it's there just to be precise
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher must not be null");
        }

        // For the delete to work, we need to update the foreign key to null so we can
        // safely remove the class
        int id = this.getId("SELECT id FROM Classes WHERE class_name = ? AND teached_by = (SELECT id FROM Teachers WHERE teacher_id = ?)",  List.of(className, teacher.getTeacherId()));
        // Here we update the foreign key to null
        this.mysql.executeQuery("UPDATE Classes SET teached_by = NULL WHERE id = ?", List.of(id));
        // Now we can safely remove the class
        this.mysql.executeQuery("DELETE FROM Classes WHERE id = ?", List.of(id));

    }

    public void updateClass(SchoolClass schoolClass, String newClassName) {

        int id = this.getId(
                "SELECT id FROM Classes WHERE class_name = ? AND teached_by=(SELECT id FROM Teachers  WHERE teacher_id = ? )",
                List.of(schoolClass.getName(), schoolClass.getTeacherId()));
        this.mysql.executeQuery("UPDATE Classes SET class_name = ? WHERE id = ?", List.of(newClassName, id));
        // We shouldn't forget to change the school class to reflect in the code so we
        // don't fall into issues
        schoolClass.setName(newClassName);

    }

    public void updateClass(SchoolClass schoolClass, Teacher newTeacher) {

        int id = this.getId(
                "SELECT id FROM Classes WHERE class_name = ? AND teached_by=(SELECT id FROM Teachers WHERE teacher_id = ? )",
                List.of(schoolClass.getName(), schoolClass.getTeacherId()));
        this.mysql.executeQuery(
                "UPDATE Classes SET teached_by = (SELECT id FROM Teachers WHERE teacher_id = ?) WHERE id = ?",
                List.of(newTeacher.getTeacherId(), id));
        // We shouldn't forget to change the school class to reflect in the code so we
        // don't fall into issues
        schoolClass.setTeacher(newTeacher);

    }

    // --- Teachers ---
    public void addNewTeacher(Teacher teacher) {
        this.mysql.executeQuery("INSERT INTO Teachers (first_name,last_name,teacher_id) VALUES (?,?,?)",
                List.of(teacher.getFirstName(), teacher.getLastName(), teacher.getTeacherId()));

    }

    public void removeTeacher(Teacher teacher) {
        List<Object> args = new ArrayList<>();
        args.add(teacher.getTeacherId());
        this.mysql.executeQuery("DELETE FROM Teachers WHERE teacher_id = ?", args);
    }

    public void updateTeacher(Teacher oldTeacher, Teacher teacher) {
        int id = this.getId("SELECT id FROM Teachers WHERE teacher_id = ?", List.of(oldTeacher.getTeacherId()));
        String query = "UPDATE Teachers SET first_name=? , last_name = ? , teacher_id = ? WHERE id = ?";
        this.mysql.executeQuery(query,List.of(teacher.getFirstName(), teacher.getLastName(), teacher.getTeacherId(), id));
        oldTeacher.changeToAnotherTeacher(teacher);
    }

    private int getId(String query, List<Object> args) {
        ResultSet result = (ResultSet) this.mysql.executeQuery(query, args);
        try {
            if (result.next()) {
                int id = (int) result.getInt("id");
                return id;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return 0;

    }
}
