package org.example.basket.add_item;

import org.assertj.core.api.ThrowableAssert;
import org.example.basket.port.basket.BasketRepository;
import org.example.basket.model.Basket;
import org.example.basket.model.Quantity;
import org.example.basket.port.product_catalog.ProductCatalogSPI;
import org.example.basket.port.stock.StockSPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.example.basket.BasketFixtures.BASKET_ID;
import static org.example.basket.BasketFixtures.PRODUCT_A;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddItemUseCaseTest {

    @Mock
    BasketRepository basketRepository;
    @Mock
    ProductCatalogSPI productCatalogSPI;
    @Mock
    StockSPI stockSPI;
    @InjectMocks
    AddItemUseCase useCase;

    @Test
    void throw_an_error_when_product_does_not_exist() {
        when(productCatalogSPI.exist(any()))
                .thenReturn(false);

        ThrowableAssert.ThrowingCallable callable = () -> useCase.accept(new AddItem(BASKET_ID, PRODUCT_A, Quantity.ONE));

        assertThatExceptionOfType(ProductNotFound.class)
                .isThrownBy(callable)
                .withMessage("Product 'F100' does not exist");
    }

    @Test
    void notify_stock_that_item_has_been_added_to_basket() {
        when(productCatalogSPI.exist(any()))
                .thenReturn(true);
        when(basketRepository.findById(BASKET_ID))
                .thenReturn(Basket.empty(BASKET_ID));

        useCase.accept(new AddItem(BASKET_ID, PRODUCT_A, Quantity.ONE));

        verify(stockSPI, times(1)).handle(new ItemAdded(BASKET_ID, PRODUCT_A, Quantity.ONE));
    }

}