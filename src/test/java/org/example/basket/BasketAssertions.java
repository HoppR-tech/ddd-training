package org.example.basket;

import org.assertj.core.api.Assertions;
import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.QuantityChanged;
import org.example.basket.model.Basket;
import org.example.basket.model.ProductRef;
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

        public BasketAssert quantityOf(ProductRef productRef, Quantity quantity) {
            Assertions.assertThat(basket.quantityOf(productRef)).isEqualTo(quantity);
            return this;
        }

        public BasketAssert quantityOf(ProductRef productRef, int quantity) {
            return quantityOf(productRef, Quantity.of(quantity));
        }

    }

    public static final class ItemAddedAssert {

        private final ItemAdded event;

        public ItemAddedAssert(ItemAdded event) {
            this.event = event;
        }

        public ItemAddedAssert hasRef(ProductRef productRef) {
            Assertions.assertThat(event.productRef()).isEqualTo(productRef);
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

        public QuantityChangedAssert hasRef(ProductRef productRef) {
            Assertions.assertThat(event.productRef()).isEqualTo(productRef);
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
