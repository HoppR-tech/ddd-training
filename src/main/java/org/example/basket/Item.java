package org.example.basket;

public class Item {

    private final ItemId id;
    private final Quantity quantity;

    public Item(ItemId id, Quantity quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public ItemId id() {
        return id;
    }

    public Quantity quantity() {
        return quantity;
    }
}
