package org.example.basket.model;

import org.example.basket.add_item.AddItem;
import org.example.basket.add_item.ItemAdded;
import org.example.basket.change_quantity.decrease.DecreaseQuantity;
import org.example.basket.change_quantity.decrease.ItemNotFound;
import org.example.basket.change_quantity.decrease.QuantityDecreased;
import org.example.basket.change_quantity.increase.IncreaseQuantity;
import org.example.basket.change_quantity.increase.QuantityIncreased;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Basket {

    private final BasketId id;
    private final Map<ProductRef, Item> itemsPerProductRef = new HashMap<>();
    private final EventStream.Pending pendingEvents = EventStream.Pending.empty();

    private Basket(BasketId id) {
        this.id = id;
    }

    public Basket(BasketId id, Collection<Item> items) {
        Map<ProductRef, Item> itemsPerId = items.stream()
                .collect(Collectors.toMap(Item::productRef, Function.identity()));

        this.id = id;
        this.itemsPerProductRef.putAll(itemsPerId);
    }

    private Basket(BasketId id, EventStream.History history) {
        this.id = id;
        history.forEach(this::apply);
    }

    private Basket(BasketId id, EventStream.Pending pendingEvents) {
        this.id = id;
        this.pendingEvents.addAll(pendingEvents);
        this.pendingEvents.forEach(this::apply);
    }

    public void apply(BasketEvent event) {
        switch (event) {
            case ItemAdded e -> itemsPerProductRef.put(e.productRef(), new Item(e.productRef(), e.quantity()));
            case QuantityIncreased e -> itemsPerProductRef.put(e.productRef(), new Item(e.productRef(), e.actual()));
            case QuantityDecreased e -> itemsPerProductRef.put(e.productRef(), new Item(e.productRef(), e.actual()));
        }
    }

    public BasketId id() {
        return id;
    }

    public Collection<Item> items() {
        return Set.copyOf(itemsPerProductRef.values());
    }

    private boolean containsItem(ProductRef productRef) {
        return itemsPerProductRef.containsKey(productRef);
    }

    public Quantity quantityOf(ProductRef productRef) {
        return Optional.ofNullable(itemsPerProductRef.get(productRef))
                .map(Item::quantity)
                .orElse(Quantity.ZERO);
    }

    public ItemAdded accept(AddItem command) {
        ProductRef productRef = command.productRef();
        Quantity quantity = command.quantity();

        ItemAdded decision = new ItemAdded(id, productRef, quantity);
        pendingAndApply(decision);
        return decision;
    }

    public QuantityIncreased accept(IncreaseQuantity command) {
        ProductRef productRef = command.productRef();
        Quantity previousQuantity = quantityOf(productRef);
        Quantity actualQuantity = previousQuantity.increase(command.quantity());

        QuantityIncreased decision = new QuantityIncreased(id, command.productRef(), previousQuantity, actualQuantity);
        pendingAndApply(decision);
        return decision;
    }

    public QuantityDecreased accept(DecreaseQuantity command) {
        ProductRef itemId = command.productRef();

        if (!containsItem(command.productRef())) {
            throw new ItemNotFound(itemId);
        }

        Quantity previousQuantity = quantityOf(itemId);
        Quantity actualQuantity = previousQuantity.decrease(command.quantity());

        QuantityDecreased decision = new QuantityDecreased(id, command.productRef(), previousQuantity, actualQuantity);
        pendingAndApply(decision);
        return decision;
    }

    public Basket with(ProductRef productRef, Quantity quantity) {
        List<Item> items = new ArrayList<>(itemsPerProductRef.values());
        items.add(new Item(productRef, quantity));
        return new Basket(id, items);
    }

    private void pendingAndApply(BasketEvent event) {
        this.pendingEvents.add(event);
        apply(event);
    }

    public static Basket empty(BasketId id) {
        return new Basket(id);
    }

    public EventStream.Pending pendingEvents() {
        return pendingEvents;
    }

    public static Basket create(BasketId basketId, EventStream.Pending pendingEvents) {
        return new Basket(basketId, pendingEvents);
    }

    public static Basket replay(BasketId basketId, EventStream.History history) {
        return new Basket(basketId, history);
    }
}
