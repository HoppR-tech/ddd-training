package org.example.basket;

import org.example.basket.add_item.ItemAdded;

public interface StockSPI {
    void handle(ItemAdded occurredEvent);
}
