package org.example.basket.change_quantity;

import org.example.basket.model.BasketEvent;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.QuantityIncreased;

public sealed interface QuantityChanged extends BasketEvent permits QuantityIncreased, QuantityDecreased {

    ItemId itemId();

    Quantity previous();

    Quantity actual();

}
