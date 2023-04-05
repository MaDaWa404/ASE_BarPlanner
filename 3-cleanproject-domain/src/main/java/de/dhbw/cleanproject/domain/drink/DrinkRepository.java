package de.dhbw.cleanproject.domain.drink;

import java.util.List;

public interface DrinkRepository {

    List<Drink> findAllDrinks();

    List<Drink> findDrinksByTitleContaining(String title);

    Drink findByTitle(String title);

    Drink save(Drink drink);
}
