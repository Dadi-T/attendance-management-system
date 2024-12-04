package donkillha.model;

public class Teacher {
    private String firstName;
    private String lastName;
    private int teacherId;

    public Teacher(String firstName, String lastName, int teacherId){
        this.firstName=firstName;
        this.lastName=lastName;
        this.teacherId=teacherId;
    }
    public Teacher(Teacher teacher){
        this.firstName=teacher.getFirstName();
        this.lastName=teacher.getLastName();
        this.teacherId=teacher.getTeacherId();
    }
    public void changeToAnotherTeacher(Teacher teacher){
        this.firstName=teacher.getFirstName();
        this.lastName=teacher.getLastName();
        this.teacherId=teacher.getTeacherId();
    }

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public int getTeacherId(){
        return this.teacherId;
    }
    public String toString(){
        return this.firstName + " " +this.lastName;
    }
}
