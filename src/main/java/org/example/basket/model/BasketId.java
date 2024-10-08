package org.example.basket.model;

public record BasketId(String value) {

    public static BasketId of(String value) {
        return new BasketId(value);
    }

}
