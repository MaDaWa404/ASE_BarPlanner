package de.dhbw.cleanproject.adapter.bar;

import java.util.UUID;

public class BarResource {

    private UUID id;
    private String title;

    private Integer zip;

    private String city;

    private String street;

    private Integer number;

    public BarResource(UUID id, String title, Integer zip, String city, String street, Integer number) {
        this.id = id;
        this.title = title;
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
}
