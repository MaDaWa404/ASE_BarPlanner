package de.dhbw.cleanproject.domain.person;


import java.util.UUID;

public interface PersonRepository {

    Person findPersonById(UUID username);

    Person findPersonByUsername(String username);

    Person save(Person person);
}

