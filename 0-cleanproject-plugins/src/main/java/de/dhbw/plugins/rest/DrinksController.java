package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.drink.DrinkResource;
import de.dhbw.cleanproject.adapter.drink.DrinkToDrinkResourceMapper;
import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.person.Person;
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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getDrinks(@RequestParam(required = false) String title, HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if(id == null) {
            return new ResponseEntity<>("{\"message\" : \"not registered\"}",HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));
        Bar b = barService.findBarByAdministrator(p.getId());
        Map<String, Object> response;
        if (title != null) {
            response = Map.of("drinks", this.drinkApplicationService.findDrinksByBarAndTitleContaining(b.getId(), title).stream()
                    .map(drinkToDrinkResourceMapper)
                    .collect(Collectors.toList()), "bar", b.getTitle(), "username", p.getUsername());
        } else {
            response = Map.of("drinks", this.drinkApplicationService.findDrinksByBar(b.getId()).stream()
                    .map(drinkToDrinkResourceMapper)
                    .collect(Collectors.toList()), "bar", b.getTitle(), "username", p.getUsername());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addDrink(@RequestBody Drink d, HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if(id == null) {
            return new ResponseEntity<>("{\"message\" : \"not registered\"}",HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));
        Bar b = barService.findBarByAdministrator(p.getId());

        Drink drink;
        try {
            drink = new Drink(d.getTitle(), d.getPrice(), d.getAmount(), b.getId());

        }   catch (IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>("{\"message\" : \"no data provided\"}",HttpStatus.BAD_REQUEST);
        }
        if (drinkApplicationService.findByTitle(d.getTitle()) == null) {
            drink = drinkApplicationService.addDrink(drink);
            return ResponseEntity
                    .created(URI.create(String.format("/drinks/%s", drink.getId()))).build();
        } else return ResponseEntity.status(403).body("already exists");
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public DrinkResource updateAmount(@RequestBody Map<String, String> value) {
        String title = value.get("title");
        int amount = Integer.parseInt(value.get("amount"));
        return drinkToDrinkResourceMapper.apply(this.drinkApplicationService.updateAmount(title, amount));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDrink(@RequestBody String title) {
        if (drinkApplicationService.findByTitle(title) != null) {
            drinkApplicationService.deleteDrink(drinkApplicationService.findByTitle(title));
            return ResponseEntity.ok().build();
        } else return ResponseEntity.notFound().build();
    }
}
