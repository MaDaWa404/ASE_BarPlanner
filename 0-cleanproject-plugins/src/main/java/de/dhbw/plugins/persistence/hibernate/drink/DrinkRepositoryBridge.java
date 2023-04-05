package de.dhbw.plugins.persistence.hibernate.drink;

import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.drink.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DrinkRepositoryBridge implements DrinkRepository {

    private final SpringDataDrinkRepository springDataDrinkRepository;

    @Autowired
    public DrinkRepositoryBridge(final SpringDataDrinkRepository springDataDrinkRepository) {
        this.springDataDrinkRepository = springDataDrinkRepository;
    }

    @Override
    public List<Drink> findAllDrinks() {
        return this.springDataDrinkRepository.findAll();
    }

    @Override
    public List<Drink> findDrinksByTitleContaining(final String title) {
        return this.springDataDrinkRepository.findAllByTitleContaining(title);
    }

    @Override
    public Drink findByTitle(String title) {
        return this.springDataDrinkRepository.findByTitle(title);
    }

    @Override
    public Drink save(final Drink drink) {
        return this.springDataDrinkRepository.save(drink);
    }
}
