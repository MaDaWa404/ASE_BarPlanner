package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.drink.DrinkResource;
import de.dhbw.cleanproject.adapter.drink.DrinkToDrinkResourceMapper;
import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.domain.drink.Drink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/drinks")
public class DrinksController {

    private final DrinkApplicationService drinkApplicationService;

    private final DrinkToDrinkResourceMapper drinkToDrinkResourceMapper;

    @Autowired
    public DrinksController(final DrinkApplicationService drinkApplicationService, final DrinkToDrinkResourceMapper drinkToDrinkResourceMapper) {
        this.drinkApplicationService = drinkApplicationService;
        this.drinkToDrinkResourceMapper = drinkToDrinkResourceMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getDrinks(@CookieValue(name = "bar-id", required = false) String barId, @RequestParam(required = false) String title) {
        if(barId != null) {
            UUID id = UUID.fromString(barId);
            if (title != null) {
                return new ResponseEntity<>(this.drinkApplicationService.findDrinksByBarAndTitleContaining(id, title).stream()
                        .map(drinkToDrinkResourceMapper)
                        .collect(Collectors.toList()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(this.drinkApplicationService.findDrinksByBar(id).stream()
                        .map(drinkToDrinkResourceMapper)
                        .collect(Collectors.toList()), HttpStatus.OK);
            }
        } else return new ResponseEntity<>("{\"message\" : \"not registered\"}",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addDrink(@RequestBody Drink d) {
        Drink drink;
        try {
            drink = new Drink(d.getTitle(), d.getPrice(), d.getAmount(), UUID.fromString("194df0d2-9d0e-451c-aa6a-a6065a8caf94")); //TODO change to specific UUID

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
