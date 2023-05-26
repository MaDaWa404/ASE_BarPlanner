package de.dhbw.cleanproject.application.purchase;

import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.drink.DrinkRepository;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.cleanproject.domain.purchase.Purchase;
import de.dhbw.cleanproject.domain.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final DrinkRepository drinkRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, DrinkRepository drinkRepository) {
        this.purchaseRepository = purchaseRepository;
        this.drinkRepository = drinkRepository;
    }

    public void order(Drink drink, Person person) {
        Purchase p = new Purchase(drink.getId(), person.getId());
        purchaseRepository.save(p);
        drink.setAmount(drink.getAmount() - 1);
        drinkRepository.save(drink);

    }
}