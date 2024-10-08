package org.example.basket.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class EventStream {

    private final List<BasketEvent> events = new ArrayList<>();

    private EventStream(List<BasketEvent> events) {
        this.events.addAll(events);
    }

    public void add(BasketEvent event) {
        this.events.add(event);
    }

    public void forEach(Consumer<BasketEvent> consumer) {
        events.forEach(consumer);
    }

    public static final class History extends EventStream {

        private History(List<BasketEvent> events) {
            super(events);
        }

        public static History of(BasketEvent... events) {
            return new History(Arrays.asList(events));
        }

    }

    public static final class Pending extends EventStream {

        private Pending(List<BasketEvent> events) {
            super(events);
        }

        public static Pending of(BasketEvent... events) {
            return new Pending(Arrays.asList(events));
        }

        public static Pending empty() {
            return new Pending(List.of());
        }

        public void addAll(Pending pendingEvents) {
            pendingEvents.forEach(this::add);
        }
    }

}
