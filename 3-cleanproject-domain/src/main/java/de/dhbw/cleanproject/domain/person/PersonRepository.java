package de.dhbw.cleanproject.domain.person;


import java.util.List;

public interface PersonRepository {

    List<Person> findAllPersons();

    Person findByUsername(String username);

    Person save(Person person);

    void delete(Person person);
}

