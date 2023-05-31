package de.dhbw.cleanproject.domain.person;


import java.util.UUID;

public interface PersonRepository {

    Person findPersonById(UUID uuid);

    Person findPersonByUsername(String username);

    Person save(Person person);
}

