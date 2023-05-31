package de.dhbw.tests;

import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.drink.Drink;
import de.dhbw.cleanproject.domain.person.Person;
import de.dhbw.cleanproject.domain.purchase.Purchase;
import de.dhbw.plugins.persistence.hibernate.bar.SpringDataBarRepository;
import de.dhbw.plugins.persistence.hibernate.drink.SpringDataDrinkRepository;
import de.dhbw.plugins.persistence.hibernate.person.SpringDataPersonRepository;
import de.dhbw.plugins.persistence.hibernate.purchase.SpringDataPurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataJpaTest
class RepositoryTests {

    private final SpringDataDrinkRepository springDataDrinkRepository;
    private final SpringDataBarRepository springDataBarRepository;
    private final SpringDataPersonRepository springDataPersonRepository;
    private final SpringDataPurchaseRepository springDataPurchaseRepository;
    //private final DrinkApplicationService drinkApplicationService;

    Person person = new Person("MaDaWa", "password", "Wagner", "Marcel");
    Bar bar = new Bar("Marcels Bar", person.getId(), 77833, "Ottersweier", "Am Laufbach", 3);
    Drink drink = new Drink("Coca-Cola", 300, 70, bar.getId());
    Purchase purchase = new Purchase(drink.getId(), person.getId());


    @Autowired
    public RepositoryTests(
            SpringDataDrinkRepository springDataDrinkRepository, SpringDataBarRepository springDataBarRepository, SpringDataPersonRepository springDataPersonRepository, SpringDataPurchaseRepository springDataPurchaseRepository) {
        this.springDataDrinkRepository = springDataDrinkRepository;
        this.springDataBarRepository = springDataBarRepository;
        this.springDataPersonRepository = springDataPersonRepository;
        this.springDataPurchaseRepository = springDataPurchaseRepository;
    }

    @BeforeEach
    public void setup() {
        person = springDataPersonRepository.save(person);
        bar = springDataBarRepository.save(bar);
        drink = springDataDrinkRepository.save(drink);
        purchase = springDataPurchaseRepository.save(purchase);
    }

    @Test
    void loginTest() {
        Person person1 = springDataPersonRepository.findPersonByUsername("MaDaWa");
        assertThat(person1.getPasswordHash()).isEqualTo(person.getPasswordHash());
    }

    @Test
    void registerTest() {
        Person person1 = new Person("MusterMax", "1234", "Mustermann", "Max");
        springDataPersonRepository.save(person1);
        Person person2 = springDataPersonRepository.findPersonByUsername("MusterMax");
        assertThat(person2.getUsername()).isEqualTo(person1.getUsername());
        assertThat(person2.getPasswordHash()).isEqualTo(person1.getPasswordHash());
    }

    @Test
    void createBarTest() {
        Person person1 = new Person("MusterMax", "1234", "Mustermann", "Max");
        Bar bar1 = new Bar("TestBar", person1.getId(), 11111, "Berlingo", "Teststraße", 69);
        springDataPersonRepository.save(person1);
        springDataBarRepository.save(bar1);
        Bar bar2 = springDataBarRepository.findBarByAdministrator(person1.getId());
        assertThat(bar2.getTitle()).isEqualTo(bar1.getTitle());
    }

    @Test
    void deleteBarTest() {
        springDataBarRepository.delete(bar);
        Bar bar1 = springDataBarRepository.findBarByTitle(bar.getTitle());
        Drink drink1 = springDataDrinkRepository.findDrinkByBarAndTitle(bar.getId(), drink.getTitle());
        assertThat(bar1).isNull();
        assertThat(drink1).isNull();
    }

    @Test
    void getDrinksTest() {
        List<Drink> drinks = springDataDrinkRepository.findAll();
        assertThat(drinks).isNotEmpty();
    }

    @Test
    void getDrinksForBarTest() {
        List<Drink> drinks = springDataDrinkRepository.findAllByBar(bar.getId());
        assertThat(drinks).isNotEmpty();
    }

    @Test
    void addDrinkTest() {
        Drink d = springDataDrinkRepository.save(new Drink("Fanta", 300, 70, bar.getId()));
        assertThat(d).isNotNull();
        assertThat(d.getId()).isNotNull();
    }

    @Test
    void deleteDrinksTest() {
        springDataDrinkRepository.delete(drink);
        Drink drink1 = springDataDrinkRepository.findDrinkByBarAndTitle(bar.getId(), drink.getTitle());
        assertThat(drink1).isNull();
    }

    @Test
    void getOrderTest() {
        List<Purchase> list = springDataPurchaseRepository.findPurchasesByPerson(person.getId());
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getDrink()).isEqualTo(purchase.getDrink());
        assertThat(list.get(0).getPerson()).isEqualTo(purchase.getPerson());
    }

    @Test
    void createOrderTest() {
        Person person1 = new Person("MusterMax", "1234", "Mustermann", "Max");
        person1 = springDataPersonRepository.save(person1);
        Purchase purchase1 = new Purchase(drink.getId(), person1.getId());
        springDataPurchaseRepository.save(purchase1);
        List<Purchase> list = springDataPurchaseRepository.findPurchasesByPerson(person1.getId());
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getDrink()).isEqualTo(purchase1.getDrink());
        assertThat(list.get(0).getPerson()).isEqualTo(purchase1.getPerson());
    }
}

