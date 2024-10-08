package org.example.basket;

import org.example.basket.model.Quantity;
import org.example.basket.model.QuantityMustBeZeroOrPositive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class QuantityTest {

    @Test
    void zero_is_allowed() {
        Quantity quantity = Quantity.of(0);

        assertThat(quantity.value()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -10})
    void quantity_must_be_positive(int value) {
        assertThatExceptionOfType(QuantityMustBeZeroOrPositive.class)
                .isThrownBy(() -> Quantity.of(value))
                .withMessage("Quantity must be 0 or positive: %s", value);
    }
}