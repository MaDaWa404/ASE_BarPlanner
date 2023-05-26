package de.dhbw.cleanproject.domain.purchase;

import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "drink")
    private UUID drink;

    @Column(name = "person")
    private UUID person;

    protected Purchase() {
        //default constructor for JPA
    }


    public Purchase(UUID drink, UUID person) {
        Validate.notNull(person);
        Validate.notNull(drink);
        this.id = UUID.randomUUID();
        this.person = person;
        this.drink = drink;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDrink() {
        return drink;
    }

    public UUID getPerson() {
        return person;
    }
}
