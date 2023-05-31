package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.drink.DrinkApplicationService;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.application.purchase.OrderService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/purchases")
public class PurchasesController {

    private final DrinkApplicationService drinkApplicationService;
    private final OrderService orderService;
    private final BarService barService;

    private final PersonService personService;

    @Autowired
    public PurchasesController(DrinkApplicationService drinkApplicationService, OrderService orderService, BarService barService, PersonService personService) {
        this.drinkApplicationService = drinkApplicationService;
        this.orderService = orderService;
        this.barService = barService;
        this.personService = personService;
    }

    @PostMapping()
    public ResponseEntity<Object> order(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Person p;
        Drink d;
        try {
            p = personService.getPersonFromId(Helper.getIdFromRequest(request));
            Bar b = barService.getBarByTitle(map.get("selectedBar"));
            d = drinkApplicationService.findDrinkByBarAndTitle(b.getId(), map.get("title"));
            orderService.createOrder(d, p);

        } catch (MyException e) {
            return new ResponseEntity<>(e.getCode(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
