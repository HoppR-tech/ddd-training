package org.example.basket;

import org.example.basket.change_quantity.decrease.DecreaseQuantity;
import org.example.basket.change_quantity.decrease.ItemNotFound;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.model.Basket;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;

@Nested
class DecreaseQuantityTest {

    @Test
    void item_quantity_is_decreased() {
        ItemId itemId = ItemId.of(1);
        DecreaseQuantity command = new DecreaseQuantity(BASKET_ID, itemId, Quantity.of(2));

        Basket basket = Basket.empty(BASKET_ID)
                .with(itemId, Quantity.of(3));

        QuantityDecreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasPreviousQuantityOf(3)
                .hasActualQuantityOf(1);

        assertThat(basket)
                .quantityOf(itemId, 1);
    }

    @Test
    void item_quantity_cannot_be_decreased_more_than_zero() {
        ItemId itemId = ItemId.of(1);
        DecreaseQuantity command = new DecreaseQuantity(BASKET_ID, itemId, Quantity.of(3));

        Basket basket = Basket.empty(BASKET_ID)
                .with(itemId, Quantity.of(1));

        QuantityDecreased occurredEvent = basket.accept(command);

        assertThat(occurredEvent)
                .hasId(itemId)
                .hasPreviousQuantityOf(1)
                .hasActualQuantityOf(0);

        assertThat(basket)
                .quantityOf(itemId, 0);
    }

    @Test
    void item_is_not_present_so_it_can_not_be_decreased() {
        ItemId itemId = ItemId.of(1);
        DecreaseQuantity command = new DecreaseQuantity(BASKET_ID, itemId, Quantity.of(3));

        Basket basket = Basket.empty(BASKET_ID);

        assertThatExceptionOfType(ItemNotFound.class)
                .isThrownBy(() -> basket.accept(command))
                .withMessage("Item '1' has not been found");
    }
}
