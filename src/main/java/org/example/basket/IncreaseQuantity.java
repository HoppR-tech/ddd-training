package org.example.basket;

public record IncreaseQuantity(BasketId basketId, ItemId itemId, Quantity quantity) {
}
