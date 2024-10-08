package org.example.basket.change_quantity.increase;

import org.example.basket.change_quantity.QuantityChanged;
import org.example.basket.model.BasketId;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

public record QuantityIncreased(BasketId basketId, ProductRef productRef, Quantity previous, Quantity actual) implements QuantityChanged {
}
