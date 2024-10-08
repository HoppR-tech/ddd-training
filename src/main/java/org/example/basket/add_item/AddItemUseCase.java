package org.example.basket.add_item;

import lombok.RequiredArgsConstructor;
import org.example.basket.model.Basket;
import org.example.basket.model.ProductRef;
import org.example.basket.port.basket.BasketRepository;
import org.example.basket.port.product_catalog.ProductCatalogSPI;
import org.example.basket.port.stock.StockSPI;

@RequiredArgsConstructor
public class AddItemUseCase {

    private final BasketRepository basketRepository;
    private final ProductCatalogSPI productCatalogSPI;
    private final StockSPI stockSPI;

    public void accept(AddItem command) {
        ProductRef productRef = command.productRef();

        if (!productCatalogSPI.exist(productRef)) {
            throw new ProductNotFound(productRef);
        }

        Basket basket = basketRepository.findById(command.basketId());
        ItemAdded occurredEvent = basket.accept(command);
        basketRepository.save(basket);

        stockSPI.handle(occurredEvent);
    }

}
