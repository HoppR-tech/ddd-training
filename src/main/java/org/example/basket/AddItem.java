package org.example.basket;

public record AddItem(ItemId itemId, Quantity quantity) {

    public AddItem(ItemId itemId) {
        this(itemId, Quantity.ONE);
    }

}
