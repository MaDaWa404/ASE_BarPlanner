package de.dhbw.cleanproject.adapter.person;

public class PersonResource {

    private final String username;

    private String passwordHash;

    private String lastname;

    private String firstname;

    public PersonResource(String username, String passwordHash, String lastname, String firstname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public PersonResource(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }
}
