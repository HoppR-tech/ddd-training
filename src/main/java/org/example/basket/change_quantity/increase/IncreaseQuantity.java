package org.example.basket.change_quantity.increase;

import org.example.basket.model.BasketId;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;

public record IncreaseQuantity(BasketId basketId, ItemId itemId, Quantity quantity) {
}
