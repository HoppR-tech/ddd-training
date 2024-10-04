package org.example.basket;

public record QuantityDecreased(ItemId itemId, Quantity previous, Quantity actual) implements QuantityChanged {
}
