package de.dhbw.cleanproject.application.person;

import de.dhbw.cleanproject.application.exceptions.MyErrorCode;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.cleanproject.domain.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findByID(UUID id) {
        return personRepository.findPersonById(id);
    }

    public Person findByUsername(String username) {
        return personRepository.findPersonByUsername(username);
    }

    public Person save(Person user) {
        return personRepository.save(user);
    }

    public Person getPersonFromId(String id) throws MyException {
        Person p = findByID(UUID.fromString(id));
        if (p == null) throw new MyException(MyErrorCode.NO_PERSON);
        return p;
    }
}