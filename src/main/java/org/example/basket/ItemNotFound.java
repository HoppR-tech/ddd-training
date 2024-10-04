package org.example.basket;

public class ItemNotFound extends RuntimeException {

    public ItemNotFound(ItemId itemId) {
        super("Item '%s' has not been found".formatted(itemId.value()));
    }

}
