package org.example.basket;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;

@Nested
class AddAnItemTest {

    @Test
    void item_is_added() {
        Basket basket = Basket.empty();
        ItemId itemId = ItemId.of(1);
        AddItem command = new AddItem(itemId);

        ItemAdded occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasQuantity(1);

        assertThat(basket)
                .quantityOf(itemId, 1);
    }

    @Test
    void item_is_added_with_a_quantity() {
        Basket basket = Basket.empty();
        Quantity quantity = Quantity.of(2);
        ItemId itemId = ItemId.of(1);
        AddItem command = new AddItem(itemId, quantity);

        ItemAdded occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasQuantity(2);

        assertThat(basket)
                .quantityOf(itemId, 2);
    }

}
