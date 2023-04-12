package de.dhbw.cleanproject.adapter.person;

public class PersonResource {

    private final String username;

    private String passwordHash;

    public PersonResource(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
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
}
