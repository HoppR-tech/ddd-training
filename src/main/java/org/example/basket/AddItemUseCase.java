package org.example.basket;

public class AddItemUseCase {

    private final BasketRepository basketRepository;
    private final StockSPI stockSPI;

    public AddItemUseCase(BasketRepository basketRepository, StockSPI stockSPI) {
        this.basketRepository = basketRepository;
        this.stockSPI = stockSPI;
    }

    public void accept(AddItem command) {
        Basket basket = basketRepository.findById(command.basketId());
        ItemAdded occurredEvent = basket.accept(command);
        basketRepository.save(basket);

        stockSPI.handle(occurredEvent);
    }

}
