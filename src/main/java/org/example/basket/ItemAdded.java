package org.example.basket;

public record ItemAdded(BasketId basketId, ItemId itemId, Quantity quantity) {
}
