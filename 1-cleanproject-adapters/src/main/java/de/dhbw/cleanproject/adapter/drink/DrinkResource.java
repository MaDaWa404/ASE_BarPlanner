package de.dhbw.cleanproject.adapter.drink;

public class DrinkResource {
    private final String title;

    private final Integer price;

    private final Integer amount;

    public DrinkResource(final String title, final Integer price, final Integer amount) {
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
}
