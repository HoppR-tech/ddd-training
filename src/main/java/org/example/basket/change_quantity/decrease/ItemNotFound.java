package org.example.basket.change_quantity.decrease;

import org.example.basket.model.ItemId;

public class ItemNotFound extends RuntimeException {

    public ItemNotFound(ItemId itemId) {
        super("Item '%s' has not been found".formatted(itemId.value()));
    }

}
