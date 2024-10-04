package org.example.basket;

public record DecreaseQuantity(BasketId basketId, ItemId itemId, Quantity quantity) {
}
