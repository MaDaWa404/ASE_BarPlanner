package de.dhbw.cleanproject.domain.drink;

import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", title: " + this.title + ", price: " + this.price + ", amount: " + this.amount;
    }
}
