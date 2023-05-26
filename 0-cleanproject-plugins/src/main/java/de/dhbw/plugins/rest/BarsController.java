package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.bar.BarResource;
import de.dhbw.cleanproject.adapter.bar.BarToBarResourceMapper;
import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/bars")
public class BarsController {

    private final PersonService personService;
    private final BarService barService;

    private final BarToBarResourceMapper barToBarResourceMapper;

    @Autowired
    public BarsController(PersonService personService, BarService barService, BarToBarResourceMapper barToBarResourceMapper) {
        this.personService = personService;
        this.barService = barService;
        this.barToBarResourceMapper = barToBarResourceMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<BarResource>>> getBars(@RequestParam(required = false) String title) {
        Map<String, List<BarResource>> response;

        if (title != null) {
            response = Map.of("bars", this.barService.findBarsByTitleContaining(title).stream()
                    .map(barToBarResourceMapper)
                    .collect(Collectors.toList()));
        } else {
            response = Map.of("bars", this.barService.findAll().stream()
                    .map(barToBarResourceMapper)
                    .collect(Collectors.toList()));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addBar(@RequestBody Bar bar, HttpServletRequest request) {
        Person p;
        try {
            String id = Helper.getIdFromRequest(request);
            p = personService.getPersonFromId(id);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.BAD_REQUEST);
        }

        if (barService.findBarByAdministrator(p.getId()) == null) {
            Bar b = new Bar(bar.getTitle(), p.getId(), bar.getZip(), bar.getCity(), bar.getStreet(), bar.getNumber());
            barService.save(b);
            return ResponseEntity
                    .status(201).build();
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<Object> removeBar(HttpServletRequest request) {
        Bar b;
        try {
            b = Helper.getBarFromRequest(request, personService, barService);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.BAD_REQUEST);
        }
        barService.delete(b);
        return ResponseEntity.status(204).build();
    }

    @PostMapping(value = "select")
    public ResponseEntity<String> selectBar(@RequestBody BarResource b, HttpServletRequest request) {
        request.getSession().setAttribute("bar", b.getId());
        return ResponseEntity.status(200).build();
    }
}