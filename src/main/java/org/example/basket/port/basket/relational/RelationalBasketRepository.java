package org.example.basket.port.basket.relational;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.basket.model.Basket;
import org.example.basket.model.BasketId;
import org.example.basket.model.Item;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;
import org.example.basket.port.basket.BasketRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RelationalBasketRepository implements BasketRepository {

    private final EntityManager entityManager;

    @Override
    public Basket findById(BasketId basketId) {
        TypedQuery<ItemDao> query = entityManager.createQuery("""
                        SELECT i FROM BasketItem i
                        WHERE i.basketId = :basketId
                        """, ItemDao.class
                ).setParameter("basketId", basketId.value());

        Set<Item> items = query.getResultStream()
                .map(RelationalBasketRepository::toItem)
                .collect(Collectors.toSet());

        return new Basket(basketId, items);
    }

    @Override
    @Transactional
    public void save(Basket basket) {
        Set<ItemDao> itemsToSave = basket.items().stream()
                .map(item -> toItemDao(basket.id(), item))
                .collect(Collectors.toSet());

        itemsToSave.forEach(entityManager::persist);
        removeDetachedItems(basket, itemsToSave);
    }

    private static Item toItem(ItemDao itemDao) {
        return new Item(ProductRef.of(itemDao.getProductRef()), Quantity.of(itemDao.getQuantity()));
    }

    private static ItemDao toItemDao(BasketId basketId, Item item) {
        ItemDao itemDao = new ItemDao();
        itemDao.setBasketId(basketId.value());
        itemDao.setProductRef(item.productRef().value());
        itemDao.setQuantity(item.quantity().value());
        return itemDao;
    }

    private void removeDetachedItems(Basket basket, Set<ItemDao> itemsToSave) {
        entityManager.createQuery("""
                    DELETE FROM BasketItem i
                    WHERE i.basketId = :basketId
                    AND i.productRef NOT IN (:productRefs)
                """)
                .setParameter("basketId", basket.id().value())
                .setParameter("productRefs", itemsToSave.stream()
                        .map(ItemDao::getProductRef)
                        .collect(Collectors.toSet()))
                .executeUpdate();
    }

}
