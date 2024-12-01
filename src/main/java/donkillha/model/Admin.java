package donkillha.model;

import java.util.ArrayList;

import donkillha.controller.DatabaseManager;
import java.util.List;
public class Admin {
    DatabaseManager mysql;

    public Admin(DatabaseManager mysql){
        this.mysql=mysql;
    }
    //--- Students ---
    public void addStudentToClass(Student[] student, String classConcerned){
        this.mysql.executeQuery("INSERT INTO Students() VALUES=", null);
    }

    public void removeStudentFromClass(Student student, String classConcerned){

    }

    public void updateStudentInClass(Student student, String classConcerned){
        
    }

    //--- Classes ---
    public void addNewClass(String className,int teacher_id){
        
    }
    public void removeClass(){
        
    }
    public void updateClass(){

    }


    //--- Teachers ---
    public void addNewTeacher(Teacher teacher){
        List<Object> args =new ArrayList<>();
        args.add(teacher.getFirstName());
        args.add(teacher.getLastName());
        args.add(teacher.getTeacherId());
        this.mysql.executeQuery("INSERT INTO Teachers (first_name,last_name,teacher_id) VALUES (?,?,?)",args);
    }
    public void removeNewTeacher(){

    }
    public void updateTeacher(){

    }
}
