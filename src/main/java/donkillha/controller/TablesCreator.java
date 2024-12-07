package donkillha.controller;

// This class creates the tables needed for the project


import java.sql.ResultSet;

public class TablesCreator {
    DatabaseManager mysql;

    public TablesCreator(DatabaseManager mysql) {
        this.mysql = mysql;
    }

    public void initialise() {
        // Checking if the tables exist and making them if they don't exist
        String query = "SELECT table_name FROM information_schema.tables WHERE table_name IN ('classes', 'students', 'teachers')";
        try {
            Object result = this.mysql.executeQuery(query, null);
            if (result instanceof ResultSet && (!((ResultSet) result).next())) {
                this.createClassesTable();
                this.createStudentTable();
                this.createTeachersTable();
                this.makeForeignKeys();
            } else {
                System.out.println("Tables have been checked and they exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void createClassesTable() {
        String queryToCreate = "CREATE TABLE IF NOT EXISTS Classes (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,`class_name` VARCHAR(100) NOT NULL,`teached_by` INT)";
        this.mysql.executeQuery(queryToCreate,null);
        System.out.println("Classes table was successfully created");
    }

    private void createStudentTable() {
        String queryToCreate = "CREATE TABLE IF NOT EXISTS Students (  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  `first_name` VARCHAR(100) NOT NULL,  `last_name` VARCHAR(100) NOT NULL,  `date_of_birth` DATE NOT NULL,  `major` VARCHAR(100) NOT NULL,`student_id` INT NOT NULL,  `class_id` INT )";
        this.mysql.executeQuery(queryToCreate,null);
        System.out.println("Students table was successfully created");
    }

    private void createTeachersTable() {
        String queryToCreate = "CREATE TABLE IF NOT EXISTS Teachers (  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  `first_name` VARCHAR(100) NOT NULL,  `last_name` VARCHAR(100) NOT NULL,`teacher_id` INT NOT NULL )";
        this.mysql.executeQuery(queryToCreate,null);
        System.out.println("Teachers table was successfully created");
    }

    private void makeForeignKeys() {
        String classesWithTeachers = "ALTER TABLE Classes ADD CONSTRAINT Classes_teached_by_fk FOREIGN KEY (`teached_by`) REFERENCES Teachers (`id`)";
        // String teachersWithClasses="ALTER TABLE Teachers ADD CONSTRAINT
        // Teachers_class_id_fk FOREIGN KEY (`class_id`) REFERENCES Classes (`id`)";
        String studentsWithClasses = "ALTER TABLE Students ADD CONSTRAINT Students_class_id_fk FOREIGN KEY (`class_id`) REFERENCES Classes (`id`)";
        String[] queries = new String[] { classesWithTeachers, studentsWithClasses };
        for (int i = 0; i < queries.length; i++) {
            this.mysql.executeQuery(queries[i],null);
        }
    }

}
