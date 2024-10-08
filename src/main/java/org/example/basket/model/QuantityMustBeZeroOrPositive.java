package org.example.basket.model;

public class QuantityMustBeZeroOrPositive extends RuntimeException {

    public QuantityMustBeZeroOrPositive(int value) {
        super("Quantity must be 0 or positive: " + value);
    }

}
