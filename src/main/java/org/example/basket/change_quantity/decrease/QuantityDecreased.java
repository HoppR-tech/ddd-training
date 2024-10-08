package org.example.basket.change_quantity.decrease;

import org.example.basket.model.BasketId;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;
import org.example.basket.change_quantity.QuantityChanged;

public record QuantityDecreased(BasketId basketId, ItemId itemId, Quantity previous, Quantity actual) implements QuantityChanged {
}
