package de.dhbw.cleanproject.application.purchase;

import de.dhbw.cleanproject.application.exceptions.MyErrorCode;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.drink.DrinkRepository;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.cleanproject.domain.purchase.Purchase;
import de.dhbw.cleanproject.domain.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final PurchaseRepository purchaseRepository;
    private final DrinkRepository drinkRepository;

    @Autowired
    public OrderService(PurchaseRepository purchaseRepository, DrinkRepository drinkRepository) {
        this.purchaseRepository = purchaseRepository;
        this.drinkRepository = drinkRepository;
    }

    public void createOrder(Drink drink, Person person) throws MyException {
        if (drink.getAmount() < 1) throw new MyException(MyErrorCode.NO_DRINKS_LEFT);
        Purchase p = new Purchase(drink.getId(), person.getId());
        purchaseRepository.save(p);
        drink.setAmount(drink.getAmount() - 1);
        drinkRepository.save(drink);

    }
}