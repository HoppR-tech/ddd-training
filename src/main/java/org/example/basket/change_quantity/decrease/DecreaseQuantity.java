package org.example.basket.change_quantity.decrease;

import org.example.basket.model.BasketId;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

public record DecreaseQuantity(BasketId basketId, ProductRef productRef, Quantity quantity) {
}
