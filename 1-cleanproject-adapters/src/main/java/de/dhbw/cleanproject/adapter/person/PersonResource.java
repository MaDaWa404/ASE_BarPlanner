package de.dhbw.cleanproject.adapter.person;

public class PersonResource {

    private final String username;

    private final String passwordHash;

    public PersonResource(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
