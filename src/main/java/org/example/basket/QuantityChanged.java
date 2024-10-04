package org.example.basket;

public sealed interface QuantityChanged permits QuantityIncreased, QuantityDecreased {

    BasketId basketId();

    ItemId itemId();

    Quantity previous();

    Quantity actual();

}
