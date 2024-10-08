package org.example.basket.port.basket.event_sourced;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.basket.model.BasketId;
import org.example.basket.model.ProductRef;
import org.example.basket.model.Quantity;

import java.io.IOException;

public class EventPersistenceModule extends SimpleModule {

    public EventPersistenceModule() {
        configureBasketId();
        configureProductRef();
        configureQuantity();
    }

    private void configureBasketId() {
        this.addSerializer(BasketId.class, new JsonSerializer<>() {
            @Override
            public void serialize(BasketId basketId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(basketId.value());
            }
        });
        this.addDeserializer(BasketId.class, new JsonDeserializer<>() {
            @Override
            public BasketId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                return BasketId.of(jsonParser.getText());
            }
        });
    }

    private void configureProductRef() {
        this.addSerializer(ProductRef.class, new JsonSerializer<>() {
            @Override
            public void serialize(ProductRef productRef, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(productRef.value());
            }
        });
        this.addDeserializer(ProductRef.class, new JsonDeserializer<>() {
            @Override
            public ProductRef deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                return ProductRef.of(jsonParser.getValueAsString());
            }
        });
    }

    private void configureQuantity() {
        this.addSerializer(Quantity.class, new JsonSerializer<>() {
            @Override
            public void serialize(Quantity quantity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNumber(quantity.value());
            }
        });
        this.addDeserializer(Quantity.class, new JsonDeserializer<>() {
            @Override
            public Quantity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return Quantity.of(jsonParser.getIntValue());
            }
        });
    }

}
