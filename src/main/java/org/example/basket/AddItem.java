package org.example.basket;

public record AddItem(BasketId basketId, ItemId itemId, Quantity quantity) {

    public AddItem(BasketId basketId, ItemId itemId) {
        this(basketId, itemId, Quantity.ONE);
    }

}
