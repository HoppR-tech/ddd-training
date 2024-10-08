package org.example.basket.add_item;

import org.example.basket.model.BasketEvent;
import org.example.basket.model.BasketId;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;

public record ItemAdded(BasketId basketId, ItemId itemId, Quantity quantity) implements BasketEvent {
}
