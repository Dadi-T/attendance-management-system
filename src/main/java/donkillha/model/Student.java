package donkillha.model;

import java.util.Arrays;



public class Student {
    private String firstName;
    private String lastName;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;
    private String major;
    private String[] classes;

    public Student(String firstName, String lastName, int dayOfBirth, int monthOfBirth, int yearOfBirth, String major, String[] classes){
        if(firstName==null || firstName.isBlank()){
            throw new IllegalArgumentException("first name should not be empty or null");
        }
        this.firstName= firstName;
        if(lastName==null || lastName.isBlank()){
            throw new IllegalArgumentException("last name should not be empty or null");
        }
        this.lastName=lastName;
        if(dayOfBirth == 0){
            throw new IllegalArgumentException("Day of birth should not be empty or null");
        }
        this.dayOfBirth=dayOfBirth;
        if(monthOfBirth == 0){
            throw new IllegalArgumentException("Month of birth should not be empty or null");
        }
        this.monthOfBirth=monthOfBirth;
        if(yearOfBirth == 0){
            throw new IllegalArgumentException("Year of birth should not be empty or null");
        }
        this.yearOfBirth=yearOfBirth;
        if(major==null || major.isBlank()){
            throw new IllegalArgumentException("Major should not be empty or null");
        }
        this.major=major;
        if(classes == null || classes.length == 0){
            throw new IllegalArgumentException("Classes should not be empty or null");
        }
        this.classes= Arrays.copyOf(classes,classes.length);
    }


    // public void study(){
    //    Faculty faculty = new Faculty();
    //    faculty.attendClass(this);
    // }

}
