package donkillha.model;

import java.util.Arrays;



public class Student {
    private String firstName;
    private String lastName;
    private Integer dayOfBirth;
    private Integer monthOfBirth;
    private Integer yearOfBirth;
    private String major;
    private Integer studentId;
    private String[] classes;

    public Student(String firstName, String lastName, Integer dayOfBirth, Integer monthOfBirth, Integer yearOfBirth, String major, Integer studentId){
        if(firstName==null || firstName.isBlank()){
            throw new IllegalArgumentException("first name should not be empty or null");
        }
        this.firstName= firstName;
        if(lastName==null || lastName.isBlank()){
            throw new IllegalArgumentException("last name should not be empty or null");
        }
        this.lastName=lastName;
        if(dayOfBirth == 0){
            throw new IllegalArgumentException("Day of birth should not zero or null");
        }
        this.dayOfBirth=dayOfBirth;
        if(monthOfBirth == 0){
            throw new IllegalArgumentException("Month of birth should not zero or null");
        }
        this.monthOfBirth=monthOfBirth;
        if(yearOfBirth == 0 ||yearOfBirth == null){
            throw new IllegalArgumentException("Year of birth should not zero or null");
        }
        this.yearOfBirth=yearOfBirth;
        if(major==null || major.isBlank()){
            throw new IllegalArgumentException("Major should not be empty or null");
        }
        this.major=major;
        if(studentId == null || studentId == 0){
            throw new IllegalArgumentException("Student Id should zero or null");
        }
        this.studentId=studentId;
    }


    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public Integer dayOfBirth(){
        return this.dayOfBirth;
    }
    public Integer monthOfBirth(){
        return this.monthOfBirth;
    }
    public Integer yearOfBirth(){
        return this.yearOfBirth;
    }
    public String dateOfBirth(){
        String dateOfBirth = this.dayOfBirth.toString() + "-" + this.monthOfBirth.toString() + "-" + this.yearOfBirth.toString();
        return dateOfBirth;
    }
    public String getMajor(){
        return this.major;
    }
    public Integer getStudentId(){
        return this.studentId;
    }
    

}
