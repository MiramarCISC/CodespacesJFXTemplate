package edu.sdccd.cisc;

import java.time.LocalDate;

public class Dog {
    private String name;
    private String breed;
    private LocalDate dob;

    public Dog() {}
    public Dog(String name, String breed, LocalDate dob) {
        this.name = name;
        this.breed = breed;
        this.dob = dob;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}
