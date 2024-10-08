package org.example.basket;

import org.example.basket.change_quantity.increase.IncreaseQuantity;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.Basket;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;

@Nested
class IncreaseQuantityTest {

    @Test
    void item_quantity_is_increased() {
        ItemId itemId = ItemId.of(1);
        IncreaseQuantity command = new IncreaseQuantity(BASKET_ID, itemId, Quantity.of(3));

        Basket basket = Basket.empty(BASKET_ID)
                .with(itemId, Quantity.of(2));

        QuantityIncreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasPreviousQuantityOf(2)
                .hasActualQuantityOf(5);

        assertThat(basket)
                .quantityOf(itemId, 5);
    }

    @Test
    void item_does_not_exist_so_it_is_implicitly_added() {
        ItemId itemId = ItemId.of(1);
        IncreaseQuantity command = new IncreaseQuantity(BASKET_ID, itemId, Quantity.of(3));

        Basket basket = Basket.empty(BASKET_ID);

        QuantityIncreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasPreviousQuantityOf(0)
                .hasActualQuantityOf(3);

        assertThat(basket)
                .quantityOf(itemId, 3);
    }
}
