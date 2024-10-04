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

    private final Map<ItemId, Item> itemsPerId = new HashMap<>();

    private Basket() {}

    public Basket(Collection<Item> items) {
        Map<ItemId, Item> itemsPerId = items.stream()
                .collect(Collectors.toMap(Item::id, Function.identity()));

        this.itemsPerId.putAll(itemsPerId);
    }

    public boolean containsItem(ItemId itemId) {
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

        this.itemsPerId.put(itemId, new Item(itemId, quantity));
        return new ItemAdded(itemId, quantity);
    }

    public QuantityIncreased accept(IncreaseQuantity command) {
        ItemId itemId = command.itemId();
        Quantity previousQuantity = quantityOf(itemId);
        Quantity actualQuantity = previousQuantity.increase(command.quantity());
        itemsPerId.put(itemId, new Item(itemId, actualQuantity));

        return new QuantityIncreased(command.itemId(), previousQuantity, actualQuantity);
    }

    public QuantityDecreased accept(DecreaseQuantity command) {
        ItemId itemId = command.itemId();

        if (!containsItem(command.itemId())) {
            throw new ItemNotFound(itemId);
        }

        Quantity previousQuantity = quantityOf(itemId);
        Quantity actualQuantity = previousQuantity.decrease(command.quantity());
        itemsPerId.put(itemId, new Item(itemId, actualQuantity));

        return new QuantityDecreased(command.itemId(), previousQuantity, actualQuantity);
    }

    public Basket with(ItemId itemId, Quantity quantity) {
        List<Item> items = new ArrayList<>(itemsPerId.values());
        items.add(new Item(itemId, quantity));
        return new Basket(items);
    }

    public static Basket empty() {
        return new Basket();
    }
}
