package de.dhbw.cleanproject.application.drink;

import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.drink.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DrinkApplicationService {

    private final DrinkRepository drinkRepository;

    @Autowired
    public DrinkApplicationService(final DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<Drink> findAllDrinks() {
        return this.drinkRepository.findAllDrinks();
    }

    public List<Drink> findDrinksByTitleContaining(String title) {
        return this.drinkRepository.findDrinksByTitleContaining(title);
    }

    public List<Drink> findDrinksByBar(UUID bar) {
        return this.drinkRepository.findDrinksByBar(bar);
    }
    public List<Drink> findDrinksByBarAndTitleContaining(UUID bar, String title) {
        return this.drinkRepository.findDrinksByBarAndTitleContaining(bar, title);
    }

    public Drink findByTitle(String title) {
        return this.drinkRepository.findByTitle(title);
    }

    public Drink addDrink(Drink drink) {
        return this.drinkRepository.save(drink);
    }

    public Drink updateAmount(String title, int amount) {
        Drink drink = drinkRepository.findByTitle(title);
        drink.setAmount(amount);
        return drinkRepository.save(drink);
    }

    public void deleteDrink(Drink drink) {
        this.drinkRepository.delete(drink);
    }
}
