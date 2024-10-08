package org.example.basket.change_quantity.decrease;

import org.example.basket.model.ProductRef;

public class ItemNotFound extends RuntimeException {

    public ItemNotFound(ProductRef productRef) {
        super("Item not found for product '%s'".formatted(productRef.value()));
    }

}
