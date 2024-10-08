package org.example.basket.add_item;

import org.example.basket.model.BasketId;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;

public record AddItem(BasketId basketId, ItemId itemId, Quantity quantity) {

    public AddItem(BasketId basketId, ItemId itemId) {
        this(basketId, itemId, Quantity.ONE);
    }

}
