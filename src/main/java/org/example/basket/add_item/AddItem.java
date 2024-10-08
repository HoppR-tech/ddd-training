package org.example.basket.add_item;

import org.example.basket.model.BasketId;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

public record AddItem(BasketId basketId, ProductRef productRef, Quantity quantity) {

    public AddItem(BasketId basketId, ProductRef productRef) {
        this(basketId, productRef, Quantity.ONE);
    }

}
