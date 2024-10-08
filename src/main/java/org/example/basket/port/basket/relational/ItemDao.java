package org.example.basket.port.basket.relational;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "BasketItem")
@Table(name = "basket_items")
@IdClass(ItemDao.CompositeKey.class)
@Getter
@Setter
public class ItemDao {

    @Id
    private String basketId;
    @Id
    private String productRef;
    @Column
    private int quantity;

    @NoArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class CompositeKey {
        private String basketId;
        private String productRef;
    }

}
