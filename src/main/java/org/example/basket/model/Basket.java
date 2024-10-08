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
import java.util.function.Function;
import java.util.stream.Collectors;

public class Basket {

    private final BasketId id;
    private final Map<ItemId, Item> itemsPerId = new HashMap<>();
    private final EventStream.Pending pendingEvents = EventStream.Pending.empty();

    private Basket(BasketId id) {
        this.id = id;
    }

    public Basket(BasketId id, Collection<Item> items) {
        Map<ItemId, Item> itemsPerId = items.stream()
                .collect(Collectors.toMap(Item::id, Function.identity()));

        this.id = id;
        this.itemsPerId.putAll(itemsPerId);
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
            case ItemAdded e -> itemsPerId.put(e.itemId(), new Item(e.itemId(), e.quantity()));
            case QuantityIncreased e -> itemsPerId.put(e.itemId(), new Item(e.itemId(), e.actual()));
            case QuantityDecreased e -> itemsPerId.put(e.itemId(), new Item(e.itemId(), e.actual()));
        }
    }

    public BasketId id() {
        return id;
    }

    private boolean containsItem(ItemId itemId) {
        return itemsPerId.containsKey(itemId);
    }

    public Quantity quantityOf(ItemId itemId) {
        return Optional.ofNullable(itemsPerId.get(itemId))
                .map(Item::quantity)
                .orElse(Quantity.ZERO);
    }

    public ItemAdded accept(AddItem command) {
        ItemId itemId = command.itemId();
        Quantity quantity = command.quantity();

        ItemAdded decision = new ItemAdded(id, itemId, quantity);
        pendingAndApply(decision);
        return decision;
    }

    public QuantityIncreased accept(IncreaseQuantity command) {
        ItemId itemId = command.itemId();
        Quantity previousQuantity = quantityOf(itemId);
        Quantity actualQuantity = previousQuantity.increase(command.quantity());

        QuantityIncreased decision = new QuantityIncreased(id, command.itemId(), previousQuantity, actualQuantity);
        pendingAndApply(decision);
        return decision;
    }

    public QuantityDecreased accept(DecreaseQuantity command) {
        ItemId itemId = command.itemId();

        if (!containsItem(command.itemId())) {
            throw new ItemNotFound(itemId);
        }

        Quantity previousQuantity = quantityOf(itemId);
        Quantity actualQuantity = previousQuantity.decrease(command.quantity());

        QuantityDecreased decision = new QuantityDecreased(id, command.itemId(), previousQuantity, actualQuantity);
        pendingAndApply(decision);
        return decision;
    }

    public Basket with(ItemId itemId, Quantity quantity) {
        List<Item> items = new ArrayList<>(itemsPerId.values());
        items.add(new Item(itemId, quantity));
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

    public static EventStream.Pending accept(AddItem command, EventStream.History history) {
        Basket basket = Basket.replay(command.basketId(), history);
        basket.accept(command);
        return basket.pendingEvents;
    }

    public static EventStream.Pending accept(IncreaseQuantity command, EventStream.History history) {
        Basket basket = Basket.replay(command.basketId(), history);
        basket.accept(command);
        return basket.pendingEvents;
    }
}
