package org.example.basket.port.basket.event_sourced;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "BasketEvent")
@Table(name = "basket_events")
@Getter
@Setter
@ToString
public class BasketEventDao {
    @Id
    private String id;
    @Column
    private String basketId;
    @Column
    private Long sequence;
    @Column
    private String payload;
}
