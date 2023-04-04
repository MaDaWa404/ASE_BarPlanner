package de.dhbw.cleanproject.domain.drink;

import java.util.UUID;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;

@Entity
@Table(name = "drink")
public class Drink {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    protected Drink() {
        //default constructor for JPA
    }

    public Drink(final String title, final int price, final int amount) {
        Validate.notBlank(title);
        Validate.notNaN(price);
        Validate.notNaN(amount);

        this.id = UUID.randomUUID();
        this.title = title;
        this.price = price;
        this.amount = amount;
    }
    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }
}