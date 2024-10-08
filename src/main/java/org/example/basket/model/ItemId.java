package org.example.basket.model;

public record ItemId(int value) {

    public static ItemId of(int value) {
        return new ItemId(value);
    }

}
