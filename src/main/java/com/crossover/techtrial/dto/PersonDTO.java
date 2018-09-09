package com.crossover.techtrial.dto;

public class PersonDTO {

    private String name;

    private String email;

    private String registrationNumber;

    private Long id;


    public PersonDTO(String name, String email, String registrationNumber) {
        this.name = name;
        this.email = email;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", id=" + id +
                '}';
    }

    public PersonDTO(String name, String email, String registrationNumber, Long id) {
        this.name = name;
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.id = id;
    }

    public PersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
