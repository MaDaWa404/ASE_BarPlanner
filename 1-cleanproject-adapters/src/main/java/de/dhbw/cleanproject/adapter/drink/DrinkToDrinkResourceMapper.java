package de.dhbw.cleanproject.adapter.drink;

import de.dhbw.cleanproject.domain.drink.Drink;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DrinkToDrinkResourceMapper implements Function<Drink, DrinkResource> {

    @Override
    public DrinkResource apply(final Drink drink) {
        return map(drink);
    }

    private DrinkResource map(final Drink drink) {
        return new DrinkResource(drink.getTitle(), drink.getPrice(), drink.getAmount());
    }
}
