package de.dhbw.cleanproject.domain.person;

import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;



    protected Person() {
        //default constructor for JPA
    }

    public Person(String username, String passwordHash, String lastname, String firstname) {
        Validate.notBlank(username);
        Validate.notBlank(passwordHash);
        Validate.notBlank(lastname);
        Validate.notBlank(firstname);

        this.id = UUID.randomUUID();
        this.username = username;
        this.passwordHash = passwordHash;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "id: " + this.id +
                ", lastname: " + this.lastname +
                ", firstname: " + this.firstname;

    }
}
