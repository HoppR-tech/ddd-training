package org.example.basket.port.basket;

import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.Basket;
import org.example.basket.model.EventStream;
import org.example.basket.model.Quantity;
import org.example.basket.port.basket.event_sourced.EventSourcedBasketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.PRODUCT_A;
import static org.example.basket.BasketFixtures.PRODUCT_B;

@SpringBootTest
@Transactional
class EventSourcedBasketRepositoryTest {

    @Autowired
    private EventSourcedBasketRepository repository;

    @Test
    void persist_basket_through_events() {
        prepareBasket();

        Basket actualBasket = repository.findById(BASKET_ID);

        assertThat(actualBasket)
                .quantityOf(PRODUCT_A, Quantity.ZERO)
                .quantityOf(PRODUCT_B, Quantity.of(11));
    }

    @Test
    void ensure_new_event_is_well_persisted() {
        prepareBasket();
        addPendingEvent();

        Basket actualBasket = repository.findById(BASKET_ID);

        assertThat(actualBasket)
                .quantityOf(PRODUCT_A, Quantity.ZERO)
                .quantityOf(PRODUCT_B, Quantity.of(9));
    }

    private void addPendingEvent() {
        Basket actualBasket = repository.findById(BASKET_ID);

        actualBasket.pendingEvents()
                .add(new QuantityDecreased(BASKET_ID, PRODUCT_B, Quantity.of(11), Quantity.of(9)));

        repository.save(actualBasket);
    }

    private void prepareBasket() {
        Basket givenBasket = Basket.create(BASKET_ID, EventStream.Pending.of(
                new ItemAdded(BASKET_ID, PRODUCT_A, Quantity.ONE),
                new ItemAdded(BASKET_ID, PRODUCT_B, Quantity.TEN),
                new QuantityIncreased(BASKET_ID, PRODUCT_B, Quantity.TEN, Quantity.of(11)),
                new QuantityDecreased(BASKET_ID, PRODUCT_A, Quantity.ONE, Quantity.of(0))
        ));

        repository.save(givenBasket);
    }
}