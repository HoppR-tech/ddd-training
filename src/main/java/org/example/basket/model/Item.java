package org.example.basket.model;

public class Item {

    private final ProductRef productRef;
    private final Quantity quantity;

    public Item(ProductRef productRef, Quantity quantity) {
        this.productRef = productRef;
        this.quantity = quantity;
    }

    public ProductRef productRef() {
        return productRef;
    }

    public Quantity quantity() {
        return quantity;
    }
}
