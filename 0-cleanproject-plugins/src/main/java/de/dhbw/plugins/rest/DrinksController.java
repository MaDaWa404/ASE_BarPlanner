package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.drink.DrinkResource;
import de.dhbw.cleanproject.adapter.drink.DrinkToDrinkResourceMapper;
import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.plugins.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
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
    public ResponseEntity<?> getDrinks(@RequestParam(required = false) String title, HttpServletRequest request) {
        Map<String, Object> response;
        UUID barId = (UUID) request.getSession().getAttribute("bar");
        if (barId != null) {
            Bar bar = barService.findBarById(barId);
            List<Drink> drinks = drinkApplicationService.findDrinksByBar(bar.getId());
            response = Map.of("bar", bar.getTitle(), "drinks", drinks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String id = (String) request.getSession().getAttribute("person");
        if (id == null) {
            return new ResponseEntity<>(ErrorMessage.notRegistered, HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));

        //TODO if person is null

        Bar b = barService.findBarByAdministrator(p.getId());

        if (b == null)
            return new ResponseEntity<>(ErrorMessage.registerBarFirst, HttpStatus.BAD_REQUEST);
        else {

            if (title != null) {
                response = Map.of("drinks", this.drinkApplicationService.findDrinksByBarAndTitleContaining(b.getId(), title).stream()
                        .map(drinkToDrinkResourceMapper)
                        .collect(Collectors.toList()), "bar", b.getTitle());
            } else {
                response = Map.of("drinks", this.drinkApplicationService.findDrinksByBar(b.getId()).stream()
                        .map(drinkToDrinkResourceMapper)
                        .collect(Collectors.toList()), "bar", b.getTitle());
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDrink(@RequestBody Drink d, HttpServletRequest request) {
        String id = (String) request.getSession().getAttribute("person");
        if (id == null) {
            return new ResponseEntity<>(ErrorMessage.notRegisteredAlert, HttpStatus.BAD_REQUEST);
        }
        Person p = personService.findByID(UUID.fromString(id));

        //TODO if person is null

        Bar b = barService.findBarByAdministrator(p.getId());

        if (b == null) {
            return new ResponseEntity<>(ErrorMessage.registerBarFirst, HttpStatus.BAD_REQUEST);
        }

        Drink drink;
        try {
            drink = new Drink(d.getTitle(), d.getPrice(), d.getAmount(), b.getId());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return new ResponseEntity<>(ErrorMessage.noDataProvided, HttpStatus.BAD_REQUEST);
        }
        if (drinkApplicationService.findDrinkByBarAndTitle(b.getId(), d.getTitle()) == null) {
            drink = drinkApplicationService.addDrink(drink);
            return ResponseEntity
                    .created(URI.create(String.format("/drinks/%s", drink.getId()))).build();
        } else return new ResponseEntity<>(ErrorMessage.alreadyExists, HttpStatus.FORBIDDEN);
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
