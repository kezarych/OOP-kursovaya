package kursovaya.oop;

public abstract class Person {

    private String firstName;
    private String lastName;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
