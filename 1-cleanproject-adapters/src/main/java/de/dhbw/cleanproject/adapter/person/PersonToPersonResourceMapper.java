package de.dhbw.cleanproject.adapter.person;

import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PersonToPersonResourceMapper implements Function<Person, PersonResource> {

    @Override
    public PersonResource apply(Person person) {
        return map(person);
    }

    private PersonResource map(final Person person) {
        return new PersonResource(person.getUsername(), person.getPasswordHash(), person.getLastname(), person.getFirstname());
    }
}
