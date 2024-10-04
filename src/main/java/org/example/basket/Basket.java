package org.example.basket;

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

    public void apply(BasketEvent event) {
        switch (event) {
            case ItemAdded e -> itemsPerId.put(e.itemId(), new Item(e.itemId(), e.quantity()));
            case QuantityIncreased e -> itemsPerId.put(e.itemId(), new Item(e.itemId(), e.actual()));
            case QuantityDecreased e -> itemsPerId.put(e.itemId(), new Item(e.itemId(), e.actual()));
        }
    }

    public static Basket replay(BasketId basketId, EventStream.History history) {
        return new Basket(basketId, history);
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
}
