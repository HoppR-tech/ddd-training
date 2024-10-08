package org.example.basket.quantity_change.increase;

import org.example.basket.change_quantity.increase.IncreaseQuantity;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.Basket;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.PRODUCT_A;

@Nested
class IncreaseQuantityTest {

    @Test
    void item_quantity_is_increased() {
        IncreaseQuantity command = new IncreaseQuantity(BASKET_ID, PRODUCT_A, Quantity.of(3));

        Basket basket = Basket.empty(BASKET_ID)
                .with(PRODUCT_A, Quantity.of(2));

        QuantityIncreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasRef(PRODUCT_A)
                .hasPreviousQuantityOf(2)
                .hasActualQuantityOf(5);

        assertThat(basket)
                .quantityOf(PRODUCT_A, 5);
    }

    @Test
    void item_does_not_exist_so_it_is_implicitly_added() {
        IncreaseQuantity command = new IncreaseQuantity(BASKET_ID, PRODUCT_A, Quantity.of(3));

        Basket basket = Basket.empty(BASKET_ID);

        QuantityIncreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasRef(PRODUCT_A)
                .hasPreviousQuantityOf(0)
                .hasActualQuantityOf(3);

        assertThat(basket)
                .quantityOf(PRODUCT_A, 3);
    }
}
