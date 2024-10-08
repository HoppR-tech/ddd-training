package org.example.basket;

import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.Basket;
import org.example.basket.model.EventStream;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.ITEM_A;
import static org.example.basket.BasketFixtures.ITEM_B;

public class EventSourcedBasketTest {

    @Test
    void replay_history_to_get_final_state() {
        Basket basket = Basket.replay(BASKET_ID, EventStream.History.of(
                new ItemAdded(BASKET_ID, ITEM_A, Quantity.TEN),
                new ItemAdded(BASKET_ID, ITEM_B, Quantity.ONE),
                new QuantityIncreased(BASKET_ID, ITEM_B, Quantity.ONE, Quantity.of(5)),
                new QuantityDecreased(BASKET_ID, ITEM_A, Quantity.TEN, Quantity.of(6))
        ));

        assertThat(basket)
                .quantityOf(ITEM_A, 6)
                .quantityOf(ITEM_B, 5);
    }
}
