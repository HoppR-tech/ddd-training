package org.example.basket.model;

import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.QuantityChanged;

public sealed interface BasketEvent permits ItemAdded, QuantityChanged {

    BasketId basketId();

}
