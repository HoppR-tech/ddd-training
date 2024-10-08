package org.example.basket.add_item;

import org.example.basket.model.ProductRef;

public class ProductNotFound extends RuntimeException {

    public ProductNotFound(ProductRef productRef) {
        super("Product '%s' does not exist".formatted(productRef.value()));
    }
}
