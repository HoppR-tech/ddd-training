package org.example.basket.add_item;

import org.example.basket.model.BasketEvent;
import org.example.basket.model.BasketId;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

public record ItemAdded(BasketId basketId, ProductRef productRef, Quantity quantity) implements BasketEvent {
}
