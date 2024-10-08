package org.example.basket.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.basket.BasketRepository;
import org.example.basket.model.Basket;
import org.example.basket.model.BasketEvent;
import org.example.basket.model.BasketId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class JpaBasketRepository implements BasketRepository {

    private final EntityManager entityManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JpaBasketRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Basket findById(BasketId basketId) {
        TypedQuery<BasketEventDao> query = entityManager.createQuery(
                """
                        SELECT event FROM BasketEvent event
                        WHERE event.basketId = :basketId
                        ORDER BY event.sequence ASC
                    """, BasketEventDao.class);

        query.setParameter("basketId", basketId.value());
        query.getResultList().forEach(System.out::println);

        return null;
    }

    @Override
    public void save(Basket basket) {
        TypedQuery<Long> query = entityManager.createQuery(
                """
                        SELECT payload.sequence FROM BasketEvent event
                        WHERE payload.basketId = :basketId
                        ORDER BY payload.sequence DESC
                    """, Long.class);

        query.setParameter("basketId", basket.id().value());

        AtomicLong actualSequence = Optional.ofNullable(query.getSingleResult())
                .map(AtomicLong::new)
                .orElseGet(() -> new AtomicLong(0L));

        basket.pendingEvents().forEach(event -> {
            BasketEventDao dao = new BasketEventDao();
            dao.setBasketId(event.basketId().value());
            dao.setSequence(actualSequence.incrementAndGet());
            dao.setPayload(serialize(event));
            entityManager.persist(dao);
        });
    }

    private String serialize(BasketEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
