package org.example.basket;

import org.example.basket.add_item.AddItem;
import org.example.basket.add_item.ItemAdded;
import org.example.basket.model.Basket;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;

@Nested
class AddAnItemTest {

    @Test
    void item_is_added() {
        Basket basket = Basket.empty(BASKET_ID);
        ItemId itemId = ItemId.of(1);
        AddItem command = new AddItem(BASKET_ID, itemId);

        ItemAdded occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasQuantity(1);

        assertThat(basket)
                .quantityOf(itemId, 1);
    }

    @Test
    void item_is_added_with_a_quantity() {
        Basket basket = Basket.empty(BASKET_ID);
        Quantity quantity = Quantity.of(2);
        ItemId itemId = ItemId.of(1);
        AddItem command = new AddItem(BASKET_ID, itemId, quantity);

        ItemAdded occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasQuantity(2);

        assertThat(basket)
                .quantityOf(itemId, 2);
    }

}
