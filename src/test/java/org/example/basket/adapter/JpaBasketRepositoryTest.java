package org.example.basket.adapter;

import org.example.basket.BasketRepository;
import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.Basket;
import org.example.basket.model.EventStream;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.ITEM_A;
import static org.example.basket.BasketFixtures.ITEM_B;

@SpringBootTest
class JpaBasketRepositoryTest {

    @Autowired
    private BasketRepository baskets;

    @Test
    void persist_events() {
        Basket basket = Basket.create(BASKET_ID, EventStream.Pending.of(
                new ItemAdded(BASKET_ID, ITEM_A, Quantity.ONE),
                new ItemAdded(BASKET_ID, ITEM_B, Quantity.TEN),
                new QuantityIncreased(BASKET_ID, ITEM_B, Quantity.TEN, Quantity.of(11)),
                new QuantityDecreased(BASKET_ID, ITEM_A, Quantity.ONE, Quantity.of(0))
        ));

        baskets.save(basket);
    }
}