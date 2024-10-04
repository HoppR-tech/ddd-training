package org.example.basket;

public interface StockSPI {
    void handle(ItemAdded occurredEvent);
}
