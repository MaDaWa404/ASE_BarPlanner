package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.drink.DrinkResource;
import de.dhbw.cleanproject.adapter.drink.DrinkToDrinkResourceMapper;
import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.domain.drink.Drink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
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
    public List<DrinkResource> getDrinks(@RequestParam(required = false) String title) {
        if (title != null) {
            return this.drinkApplicationService.findDrinksByTitleContaining(title).stream()
                    .map(drinkToDrinkResourceMapper)
                    .collect(Collectors.toList());
        }
        return this.drinkApplicationService.findAllDrinks().stream()
                .map(drinkToDrinkResourceMapper)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addDrink(@RequestBody Drink d) {
        Drink drink = new Drink(d.getTitle(), d.getPrice(), d.getAmount());
        if (drinkApplicationService.findByTitle(d.getTitle()) == null) {
            drink = drinkApplicationService.addDrink(drink);
            return ResponseEntity
                    .created(URI.create(String.format("/drinks/%s", drink.getId()))).build();

        } else return ResponseEntity.status(403).body("already exists");
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public DrinkResource updateAmount(@RequestBody Map<String,String> value) {
        String title = value.get("title");
        int amount = Integer.parseInt(value.get("amount"));
        return drinkToDrinkResourceMapper.apply(this.drinkApplicationService.updateAmount(title, amount));

    }
}