package org.example.basket;

public sealed interface QuantityChanged extends BasketEvent permits QuantityIncreased, QuantityDecreased {

    ItemId itemId();

    Quantity previous();

    Quantity actual();

}
