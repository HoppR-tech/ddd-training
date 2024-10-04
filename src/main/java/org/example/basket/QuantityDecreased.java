package org.example.basket;

public record QuantityDecreased(BasketId basketId, ItemId itemId, Quantity previous, Quantity actual) implements QuantityChanged {
}
