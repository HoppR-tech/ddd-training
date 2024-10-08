package org.example.basket.port.basket;

import org.example.basket.model.Basket;
import org.example.basket.model.Quantity;
import org.example.basket.port.basket.relational.RelationalBasketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.example.basket.BasketAssertions.assertThat;
import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.PRODUCT_A;
import static org.example.basket.BasketFixtures.PRODUCT_B;
import static org.example.basket.BasketFixtures.PRODUCT_C;

@SpringBootTest
@Transactional
class RelationalBasketRepositoryTest {

    @Autowired
    private RelationalBasketRepository repository;

    @Test
    void persist_a_new_basket() {
        prepareBasket();

        Basket actualBasket = repository.findById(BASKET_ID);

        assertThat(actualBasket)
                .quantityOf(PRODUCT_A, Quantity.ONE)
                .quantityOf(PRODUCT_B, Quantity.TEN);
    }

    @Test
    void detached_items_must_be_removed_from_database() {
        prepareBasket();

        Basket givenBasket = Basket.empty(BASKET_ID)
                .with(PRODUCT_C, Quantity.ONE);

        repository.save(givenBasket);

        Basket actualBasket = repository.findById(BASKET_ID);

        assertThat(actualBasket)
                .quantityOf(PRODUCT_A, Quantity.ZERO)
                .quantityOf(PRODUCT_B, Quantity.ZERO)
                .quantityOf(PRODUCT_C, Quantity.ONE);
    }

    private void prepareBasket() {
        Basket givenBasket = Basket.empty(BASKET_ID)
                .with(PRODUCT_A, Quantity.ONE)
                .with(PRODUCT_B, Quantity.TEN);

        repository.save(givenBasket);
    }

}