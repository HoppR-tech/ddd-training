package org.example.basket;

public record ItemId(int value) {

    public static ItemId of(int value) {
        return new ItemId(value);
    }

}
