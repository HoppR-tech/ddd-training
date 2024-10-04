package org.example.basket;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;

@Nested
class IncreaseQuantityTest {

    @Test
    void item_quantity_is_increased() {
        ItemId itemId = ItemId.of(1);
        IncreaseQuantity command = new IncreaseQuantity(itemId, Quantity.of(3));

        Basket basket = Basket.empty()
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
        IncreaseQuantity command = new IncreaseQuantity(itemId, Quantity.of(3));

        Basket basket = Basket.empty();

        QuantityIncreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasPreviousQuantityOf(0)
                .hasActualQuantityOf(3);

        assertThat(basket)
                .quantityOf(itemId, 3);
    }
}
