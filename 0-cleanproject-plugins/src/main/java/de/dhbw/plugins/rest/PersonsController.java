package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.person.PersonResource;
import de.dhbw.cleanproject.adapter.person.PersonToPersonResourceMapper;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.plugins.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Person p, HttpServletRequest request) {
        Person person;
        try {
            person = new Person(p.getUsername(), p.getPasswordHash(), p.getLastname(), p.getFirstname());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(new ErrorMessage("no data provided"), HttpStatus.BAD_REQUEST);
        }

        if (personService.findByUsername(p.getUsername()) == null) {
            person = personService.save(person);
            request.getSession().setAttribute("person", person.getId().toString());
            return ResponseEntity
                    .created(URI.create(String.format("/persons/%s", person.getId()))).build();
        } else return ResponseEntity.status(403).body("already exists");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody(required = false) PersonResource p, HttpServletRequest request) {
        if (p.getUsername().isBlank() || p.getPasswordHash().isBlank()) {
            return new ResponseEntity<>(new ErrorMessage("username or password invalid"), HttpStatus.BAD_REQUEST);
        }
        Person person = personService.findByUsername(p.getUsername());
        if (person == null) {
            return new ResponseEntity<>(new ErrorMessage("username invalid"), HttpStatus.BAD_REQUEST);
        }
        if (person.getPasswordHash().equals(p.getPasswordHash())) {
            request.getSession().setAttribute("person", person.getId().toString());
            return new ResponseEntity<>(new PersonResource(person.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMessage("password invalid"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
