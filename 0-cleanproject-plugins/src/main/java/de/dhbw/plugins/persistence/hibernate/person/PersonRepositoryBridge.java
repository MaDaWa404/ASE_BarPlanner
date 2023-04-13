package de.dhbw.plugins.persistence.hibernate.person;


import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.cleanproject.domain.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PersonRepositoryBridge implements PersonRepository {

    private final SpringDataPersonRepository springDataPersonRepository;

    @Autowired
    public PersonRepositoryBridge(final SpringDataPersonRepository springDataPersonRepository) {
        this.springDataPersonRepository = springDataPersonRepository;
    }


    @Override
    public Person findPersonById(UUID id) {
        return this.springDataPersonRepository.findPersonById(id);
    }

    @Override
    public Person findPersonByUsername(String username) {
        return this.springDataPersonRepository.findPersonByUsername(username);
    }

    @Override
    public Person save(final Person person) {
        return this.springDataPersonRepository.save(person);
    }
}
