package donkillha.model;

public class SchoolClass {
    private String name;
    private Teacher teached_by;

    public SchoolClass(String name, Teacher teached_by){
        this.name=name;
        this.teached_by = teached_by;
    }

    public String getName(){
        return this.name;
    }
    public Integer getTeacherId(){
        return this.teached_by.getTeacherId();
    }

    public void setName(String name){
        this.name=name;
    }
    public void setTeacher(Teacher teached_by){
        this.teached_by=teached_by;
    }
}
