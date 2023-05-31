package de.dhbw.cleanproject.adapter.person;

import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PersonToPersonDTOMapper implements Function<Person, PersonDTO> {

    @Override
    public PersonDTO apply(Person person) {
        return map(person);
    }

    private PersonDTO map(final Person person) {
        return new PersonDTO(person.getUsername());
    }
}
