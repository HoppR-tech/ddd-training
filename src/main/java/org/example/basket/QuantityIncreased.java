package org.example.basket;

public record QuantityIncreased(BasketId basketId, ItemId itemId, Quantity previous, Quantity actual) implements QuantityChanged {
}
