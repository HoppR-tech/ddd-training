package org.example.basket.change_quantity.increase;

import org.example.basket.model.BasketId;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;
import org.example.basket.change_quantity.QuantityChanged;

public record QuantityIncreased(BasketId basketId, ItemId itemId, Quantity previous, Quantity actual) implements QuantityChanged {
}
