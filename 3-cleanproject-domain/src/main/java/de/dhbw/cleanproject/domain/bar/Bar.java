package de.dhbw.cleanproject.domain.bar;

import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "bar")
public class Bar {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "administrator")
    private UUID administrator;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    protected Bar() {
        //default constructor for JPA
    }

    public Bar(String title, UUID administrator, Integer zip, String city, String street, Integer number) {
        Validate.notBlank(title);
        Validate.notNull(administrator);
        Validate.notNaN(zip);
        Validate.notBlank(city);
        Validate.notBlank(street);
        Validate.notNaN(number);

        this.id = UUID.randomUUID();
        this.title = title;
        this.administrator = administrator;
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public UUID getAdministrator() {
        return administrator;
    }

    public Integer getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "id: " + this.id +
                ", title: " + this.title +
                ", administrator: " + this.administrator +
                ", zip: " + this.zip +
                ", city: " + this.city +
                ", street: " + this.street +
                ", number: " + this.number;
    }
}
