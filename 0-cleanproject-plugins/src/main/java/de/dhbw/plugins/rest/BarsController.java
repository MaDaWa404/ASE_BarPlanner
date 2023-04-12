package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.plugins.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/bars")
public class BarsController {

    private final PersonService personService;
    private final BarService barService;

    @Autowired
    public BarsController(PersonService personService, BarService barService) {
        this.personService = personService;
        this.barService = barService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addBar(@RequestBody Bar bar, HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if(id == null) {
            return new ResponseEntity<>(new ErrorMessage("not registered"), HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));


        if(barService.findBarByAdministrator(p.getId()) == null) {
            Bar b = new Bar(bar.getTitle(), bar.getAdministrator(), bar.getZip(), bar.getCity(), bar.getStreet(), bar.getNumber());
            barService.save(b);
            return ResponseEntity
                    .status(201).build();
        } else return ResponseEntity.status(403).body("you already have a bar");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> removeBar(HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if(id == null) {
            return new ResponseEntity<>(new ErrorMessage("not registered"), HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));

        Bar b = barService.findBarByAdministrator(p.getId());

        if(b != null) {
            barService.delete(b);
            return ResponseEntity
                    .status(204).build();
        } else return ResponseEntity.status(404).body("not found");
    }
}