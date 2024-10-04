package org.example.basket;

public record QuantityIncreased(ItemId itemId, Quantity previous, Quantity actual) implements QuantityChanged {
}
