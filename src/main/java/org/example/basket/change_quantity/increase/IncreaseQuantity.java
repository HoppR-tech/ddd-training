package org.example.basket.change_quantity.increase;

import org.example.basket.model.BasketId;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

public record IncreaseQuantity(BasketId basketId, ProductRef productRef, Quantity quantity) {
}
