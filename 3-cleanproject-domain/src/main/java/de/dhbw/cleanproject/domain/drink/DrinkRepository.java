package de.dhbw.cleanproject.domain.drink;

import java.util.List;

public interface DrinkRepository {

    List<Drink> findAllDrinks();

    List<Drink> findDrinksWithTitle(String title);

    Drink save(Drink drink);
}
