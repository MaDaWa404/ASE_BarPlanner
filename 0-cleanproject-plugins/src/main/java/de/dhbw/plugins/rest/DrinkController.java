package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.adapter.drink.DrinkResource;
import de.dhbw.cleanproject.adapter.drink.DrinkToDrinkResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/drink")
public class DrinkController {

    private final DrinkApplicationService drinkApplicationService;

    private final DrinkToDrinkResourceMapper drinkToDrinkResourceMapper;

    @Autowired
    public DrinkController(final DrinkApplicationService drinkApplicationService, final DrinkToDrinkResourceMapper drinkToDrinkResourceMapper) {
        this.drinkApplicationService = drinkApplicationService;
        this.drinkToDrinkResourceMapper = drinkToDrinkResourceMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DrinkResource> getDrinks() {
        return this.drinkApplicationService.findAllDrinks().stream()
                .map(drinkToDrinkResourceMapper)
                .collect(Collectors.toList());
    }
}
