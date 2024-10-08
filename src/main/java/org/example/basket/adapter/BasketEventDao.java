package org.example.basket.adapter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "BasketEvent")
@Table(name = "basket_events")
public class BasketEventDao {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer basketId;

    @Column
    private Long sequence;

    @Column
    private String payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBasketId(Integer basketId) {
        this.basketId = basketId;
    }

    public Integer getBasketId() {
        return basketId;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setPayload(String event) {
        this.payload = event;
    }

    public String getPayload() {
        return payload;
    }
}
