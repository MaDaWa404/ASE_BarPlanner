package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.drink.DrinkResource;
import de.dhbw.cleanproject.adapter.drink.DrinkToDrinkResourceMapper;
import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.plugins.exceptions.MyErrorCode;
import de.dhbw.plugins.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/drinks")
public class DrinksController {

    private final DrinkApplicationService drinkApplicationService;
    private final PersonService personService;
    private final BarService barService;
    private final DrinkToDrinkResourceMapper drinkToDrinkResourceMapper;

    @Autowired
    public DrinksController(final DrinkApplicationService drinkApplicationService, PersonService personService, BarService barService, final DrinkToDrinkResourceMapper drinkToDrinkResourceMapper) {
        this.drinkApplicationService = drinkApplicationService;
        this.personService = personService;
        this.barService = barService;
        this.drinkToDrinkResourceMapper = drinkToDrinkResourceMapper;
    }

    @GetMapping
    public ResponseEntity<Object> getDrinks(@RequestParam(required = false) String title, HttpServletRequest request) {
        Bar b;
        try {
            String id = getIdFromRequest(request);
            Person p = getPersonFromId(id);
            b = getBarFromPerson(p);
        } catch (MyException e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(getDrinksAsMap(title, b), HttpStatus.OK);
    }

    private String getIdFromRequest(HttpServletRequest request) throws MyException {
        String id = (String) request.getSession().getAttribute("person");
        if (id == null) throw new MyException(MyErrorCode.NO_ID);
        return id;
    }

    private Person getPersonFromId(String id) throws MyException {
        Person p = personService.findByID(UUID.fromString(id));
        if (p == null) throw new MyException(MyErrorCode.NO_PERSON);
        return p;
    }

    private Bar getBarFromPerson(Person p) throws MyException {
        Bar b = barService.findBarByAdministrator(p.getId());
        if (b == null) throw new MyException(MyErrorCode.NO_BAR);
        return b;
    }

    private Map<String, Object> getDrinksAsMap(String title, Bar bar) {
        Map<String, Object> response;
        if (title != null) {
            response = Map.of("drinks", this.drinkApplicationService
                    .findDrinksByBarAndTitleContaining(bar.getId(), title).stream().map(drinkToDrinkResourceMapper)
                    .collect(Collectors.toList()), "bar", bar.getTitle());
        } else {
            response = Map.of("drinks", this.drinkApplicationService
                    .findDrinksByBar(bar.getId()).stream().map(drinkToDrinkResourceMapper)
                    .collect(Collectors.toList()), "bar", bar.getTitle());
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addDrink(@RequestBody Drink d, HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));

        //TODO if person is null

        Bar b = barService.findBarByAdministrator(p.getId());

        if (b == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Drink drink;
        try {
            drink = new Drink(d.getTitle(), d.getPrice(), d.getAmount(), b.getId());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (drinkApplicationService.findDrinkByBarAndTitle(b.getId(), d.getTitle()) == null) {
            drink = drinkApplicationService.addDrink(drink);
            return ResponseEntity
                    .created(URI.create(String.format("/drinks/%s", drink.getId()))).build();
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping
    public DrinkResource updateAmount(@RequestBody Map<String, String> value) {
        String title = value.get("title");
        int amount = Integer.parseInt(value.get("amount"));
        return drinkToDrinkResourceMapper.apply(this.drinkApplicationService.updateAmount(title, amount));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDrink(@RequestBody String title) {
        if (drinkApplicationService.findByTitle(title) != null) {
            drinkApplicationService.deleteDrink(drinkApplicationService.findByTitle(title));
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }
}
