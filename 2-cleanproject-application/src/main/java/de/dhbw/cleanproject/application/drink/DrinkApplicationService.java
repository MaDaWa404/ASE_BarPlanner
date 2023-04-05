package de.dhbw.cleanproject.application.drink;

import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.drink.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Drink> findDrinksWithTitle(String title) {
        return this.drinkRepository.findDrinksWithTitle(title);
    }

    public Drink addDrink(Drink drink) {
        return this.drinkRepository.save(drink);
    }
}
