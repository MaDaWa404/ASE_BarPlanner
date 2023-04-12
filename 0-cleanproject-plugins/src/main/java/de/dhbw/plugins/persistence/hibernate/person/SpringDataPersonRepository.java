package de.dhbw.plugins.persistence.hibernate.person;

import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPersonRepository extends JpaRepository<Person, UUID> {
    Person findPersonByUsername(String username);

    Person findPersonById(UUID id);

}
