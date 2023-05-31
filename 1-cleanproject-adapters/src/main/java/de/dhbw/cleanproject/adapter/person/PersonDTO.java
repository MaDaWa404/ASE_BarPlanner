package de.dhbw.cleanproject.adapter.person;

import java.util.Objects;

final public class PersonDTO {

    private final String username;


    public PersonDTO(String username) {
        this.username = username;

    }


    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PersonDTO personDTO = (PersonDTO) o;
        return username.equals(personDTO.username);
    }
}
