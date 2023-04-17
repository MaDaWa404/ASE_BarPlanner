package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.person.PersonResource;
import de.dhbw.cleanproject.adapter.person.PersonToPersonResourceMapper;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.plugins.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/persons")
public class PersonsController {

    private final PersonService personService;

    private final PersonToPersonResourceMapper personToPersonResourceMapper;

    @Autowired
    public PersonsController(PersonService personService, PersonToPersonResourceMapper personToPersonResourceMapper) {
        this.personService = personService;
        this.personToPersonResourceMapper = personToPersonResourceMapper;
    }

    @GetMapping
    public Object getPerson(HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if (id == null) {
            return new ResponseEntity<>(ErrorMessage.notRegistered, HttpStatus.BAD_REQUEST);
        }

        // TODO remove passwordHash
        PersonResource p = personToPersonResourceMapper.apply(personService.findByID(UUID.fromString(id)));
        if (p == null) return new ResponseEntity<>(ErrorMessage.notRegistered, HttpStatus.BAD_REQUEST);
        return p;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody Person p, HttpServletRequest request) {
        Person person;
        try {
            person = new Person(p.getUsername(), p.getPasswordHash(), p.getLastname(), p.getFirstname());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ErrorMessage("no data provided", true), HttpStatus.BAD_REQUEST);
        }

        if (personService.findByUsername(p.getUsername()) == null) {
            person = personService.save(person);
            request.getSession().setAttribute("person", person.getId().toString());
            return new ResponseEntity<>(new PersonResource(person.getUsername()), HttpStatus.CREATED);
        } else return ResponseEntity.status(403).body("already exists");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody(required = false) PersonResource p, HttpServletRequest request) {
        if (p.getUsername().isBlank() || p.getPasswordHash().isBlank()) {
            return new ResponseEntity<>(new ErrorMessage("username or password invalid", true), HttpStatus.BAD_REQUEST);
        }
        Person person = personService.findByUsername(p.getUsername());
        if (person == null) {
            return new ResponseEntity<>(new ErrorMessage("username invalid", true), HttpStatus.BAD_REQUEST);
        }
        if (person.getPasswordHash().equals(p.getPasswordHash())) {
            request.getSession().setAttribute("person", person.getId().toString());
            return new ResponseEntity<>(new PersonResource(person.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMessage("password invalid", true), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
