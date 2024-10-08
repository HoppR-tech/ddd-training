package org.example.basket.model;

/**
 * The product reference from the product catalog
 */
public record ProductRef(String value) {

    public static ProductRef of(String value) {
        return new ProductRef(value);
    }

}
