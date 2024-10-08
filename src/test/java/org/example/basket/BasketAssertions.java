package org.example.basket;

import org.assertj.core.api.Assertions;
import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.QuantityChanged;
import org.example.basket.model.Basket;
import org.example.basket.model.ItemId;
import org.example.basket.model.Quantity;

public class BasketAssertions {

    public static BasketAssert assertThat(Basket basket) {
        return new BasketAssert(basket);
    }

    public static ItemAddedAssert assertThat(ItemAdded event) {
        return new ItemAddedAssert(event);
    }

    public static QuantityChangedAssert assertThat(QuantityChanged event) {
        return new QuantityChangedAssert(event);
    }

    public static class BasketAssert {

        private final Basket basket;

        public BasketAssert(Basket basket) {
            this.basket = basket;
        }

        public BasketAssert quantityOf(ItemId itemId, int quantity) {
            Assertions.assertThat(basket.quantityOf(itemId)).isEqualTo(Quantity.of(quantity));
            return this;
        }

    }

    public static final class ItemAddedAssert {

        private final ItemAdded event;

        public ItemAddedAssert(ItemAdded event) {
            this.event = event;
        }

        public ItemAddedAssert hasId(ItemId itemId) {
            Assertions.assertThat(event.itemId()).isEqualTo(itemId);
            return this;
        }

        public ItemAddedAssert hasQuantity(int quantity) {
            Assertions.assertThat(event.quantity().value()).isEqualTo(quantity);
            return this;
        }

    }

    public static final class QuantityChangedAssert {

        private final QuantityChanged event;

        public QuantityChangedAssert(QuantityChanged event) {
            this.event = event;
        }

        public QuantityChangedAssert hasId(ItemId itemId) {
            Assertions.assertThat(event.itemId()).isEqualTo(itemId);
            return this;
        }

        public QuantityChangedAssert hasPreviousQuantityOf(int quantity) {
            Assertions.assertThat(event.previous().value()).isEqualTo(quantity);
            return this;
        }

        public QuantityChangedAssert hasActualQuantityOf(int quantity) {
            Assertions.assertThat(event.actual().value()).isEqualTo(quantity);
            return this;
        }

    }
}
