package de.dhbw.cleanproject.adapter.person;

public class PersonResource {

    private final String username;

    private final String passwordHash;

    private final String lastname;

    private final String firstname;

    public PersonResource(String username, String passwordHash, String lastname, String firstname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.lastname = lastname;
        this.firstname = firstname;
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
