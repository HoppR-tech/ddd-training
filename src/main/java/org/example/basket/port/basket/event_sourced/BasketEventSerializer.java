package org.example.basket.port.basket.event_sourced;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.QuantityIncreased;
import org.example.basket.model.BasketEvent;
import org.springframework.stereotype.Component;

@Component
public class BasketEventSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new EventPersistenceModule());

    public String serialize(BasketEvent event) {
        try {
            SerializedBasketEvent serializedEvent = new SerializedBasketEvent(event);
            return objectMapper.writeValueAsString(serializedEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public BasketEvent deserialize(String payload) {
        try {
            SerializedBasketEvent serializedEvent = objectMapper.readValue(payload, SerializedBasketEvent.class);
            return serializedEvent.event();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used in order not to bind directly jackson serialization to domain event.
     * The drawback is that it wraps each event into an "event" key.
     */
    public record SerializedBasketEvent(
            @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@name") @JsonSubTypes({
                    @JsonSubTypes.Type(value = ItemAdded.class, name = "item_added"),
                    @JsonSubTypes.Type(value = QuantityIncreased.class, name = "quantity_increased"),
                    @JsonSubTypes.Type(value = QuantityDecreased.class, name = "quantity_decreased"),
            })
            BasketEvent event
    ) {
    }

}
