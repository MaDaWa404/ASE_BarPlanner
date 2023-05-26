package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.person.PersonResource;
import de.dhbw.cleanproject.adapter.person.PersonToPersonResourceMapper;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<Object> getPerson(HttpServletRequest request) {
        Person p;
        try {
            String id = Helper.getIdFromRequest(request);
            p = personService.getPersonFromId(id);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.BAD_REQUEST);
        }

        // TODO remove passwordHash
        return new ResponseEntity<>(personToPersonResourceMapper.apply(p), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody Person p, HttpServletRequest request) {
        Person person;
        try {
            person = new Person(p.getUsername(), p.getPasswordHash(), p.getLastname(), p.getFirstname());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (personService.findByUsername(p.getUsername()) == null) {
            person = personService.save(person);
            request.getSession().setAttribute("person", person.getId().toString());
            return new ResponseEntity<>(new PersonResource(person.getUsername()), HttpStatus.CREATED);
        } else return ResponseEntity.status(403).body("already exists");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody(required = false) PersonResource p, HttpServletRequest request) {
        if (p.getUsername().isBlank() || p.getPasswordHash().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Person person = personService.findByUsername(p.getUsername());
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (person.getPasswordHash().equals(p.getPasswordHash())) {
            request.getSession().setAttribute("person", person.getId().toString());
            return new ResponseEntity<>(new PersonResource(person.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
