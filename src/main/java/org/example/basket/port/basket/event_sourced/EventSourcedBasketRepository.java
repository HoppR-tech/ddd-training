package org.example.basket.port.basket.event_sourced;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.basket.model.Basket;
import org.example.basket.model.BasketEvent;
import org.example.basket.model.BasketId;
import org.example.basket.model.EventStream;
import org.example.basket.port.basket.BasketRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class EventSourcedBasketRepository implements BasketRepository {

    private final EntityManager entityManager;
    private final BasketEventSerializer eventSerializer;

    @Override
    public Basket findById(BasketId basketId) {
        TypedQuery<BasketEventDao> query = entityManager.createQuery(
                """
                        SELECT event FROM BasketEvent event
                        WHERE event.basketId = :basketId
                        ORDER BY event.sequence ASC
                    """, BasketEventDao.class);

        query.setParameter("basketId", basketId.value());

        List<BasketEvent> occurredEvents = query.getResultStream()
                .peek(System.out::println)
                .map(this::toBasketEvent)
                .toList();

        return Basket.replay(basketId, EventStream.History.of(occurredEvents));
    }

    @Override
    @Transactional
    public void save(Basket basket) {
        Long latestSequence = latestSequence(basket.id());

        AtomicLong actualSequence = new AtomicLong(latestSequence);

        basket.pendingEvents().forEach(event -> {
            BasketEventDao dao = new BasketEventDao();
            dao.setId(UlidCreator.getUlid().toLowerCase());
            dao.setBasketId(event.basketId().value());
            dao.setSequence(actualSequence.incrementAndGet());
            dao.setPayload(toPayload(event));
            entityManager.persist(dao);
        });
    }

    private Long latestSequence(BasketId basketId) {
        return entityManager.createQuery(
                """
                        SELECT event.sequence FROM BasketEvent event
                        WHERE event.basketId = :basketId
                        ORDER BY event.sequence DESC
                    """, Long.class)
                .setParameter("basketId", basketId.value())
                .getResultStream()
                .findAny()
                .orElse(0L);
    }

    private String toPayload(BasketEvent event) {
        return eventSerializer.serialize(event);
    }

    private BasketEvent toBasketEvent(BasketEventDao eventDao) {
        return eventSerializer.deserialize(eventDao.getPayload());
    }

}
