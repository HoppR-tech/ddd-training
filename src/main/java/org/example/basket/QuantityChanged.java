package org.example.basket;

public sealed interface QuantityChanged permits QuantityIncreased, QuantityDecreased {

    ItemId itemId();

    Quantity previous();

    Quantity actual();

}
