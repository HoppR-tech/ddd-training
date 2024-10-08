package org.example.basket.change_quantity;

import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.BasketEvent;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

public sealed interface QuantityChanged extends BasketEvent permits QuantityIncreased, QuantityDecreased {

    ProductRef productRef();

    Quantity previous();

    Quantity actual();

}
