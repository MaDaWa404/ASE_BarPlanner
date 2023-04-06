package de.dhbw.cleanproject.domain.drink;

import java.util.List;
import java.util.UUID;

public interface DrinkRepository {

    List<Drink> findAllDrinks();

    List<Drink> findDrinksByTitleContaining(String title);

    List<Drink> findDrinksByBar(UUID bar);

    List<Drink> findDrinksByBarAndTitleContaining(UUID bar, String title);

    Drink findByTitle(String title);

    Drink save(Drink drink);

    void delete(Drink drink);
}
