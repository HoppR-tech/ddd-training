package org.example.basket.port.basket;

import org.example.basket.model.Basket;
import org.example.basket.model.BasketId;

public interface BasketRepository {

    Basket findById(BasketId basketId);

    void save(Basket basket);
}
