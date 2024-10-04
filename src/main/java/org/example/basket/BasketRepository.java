package org.example.basket;

public interface BasketRepository {

    Basket findById(BasketId basketId);

    void save(Basket basket);
}
