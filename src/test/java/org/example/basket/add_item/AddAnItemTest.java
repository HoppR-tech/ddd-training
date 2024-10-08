package org.example.basket.add_item;

import org.example.basket.model.Basket;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.PRODUCT_A;

@Nested
class AddAnItemTest {

    @Test
    void item_is_added() {
        Basket basket = Basket.empty(BASKET_ID);
        AddItem command = new AddItem(BASKET_ID, PRODUCT_A);

        ItemAdded occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasRef(PRODUCT_A)
                .hasQuantity(1);

        assertThat(basket)
                .quantityOf(PRODUCT_A, 1);
    }

    @Test
    void item_is_added_with_a_quantity() {
        Basket basket = Basket.empty(BASKET_ID);
        Quantity quantity = Quantity.of(2);
        AddItem command = new AddItem(BASKET_ID, PRODUCT_A, quantity);

        ItemAdded occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasRef(PRODUCT_A)
                .hasQuantity(2);

        assertThat(basket)
                .quantityOf(PRODUCT_A, 2);
    }

}
