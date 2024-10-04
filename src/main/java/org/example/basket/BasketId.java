package org.example.basket;

public record BasketId(int value) {

    public static BasketId of(int value) {
        return new BasketId(value);
    }

}
