package org.example.basket;

public sealed interface BasketEvent permits ItemAdded, QuantityChanged {

    BasketId basketId();

}
