package org.example.basket;

public interface Quantity {

    Quantity ZERO = new Zero();
    Quantity ONE = of(1);

    int value();

    default Quantity increase(Quantity quantity) {
        return of(value() + quantity.value());
    }

    default Quantity decrease(Quantity quantity) {
        int actual = value() - quantity.value();
        return actual < 0 ? ZERO : of(actual);
    }

    static Quantity of(int value) {
        return switch (value) {
            case 0 -> ZERO;
            default -> Positive.from(value);
        };
    }

    record Positive(int value) implements Quantity {

        public Positive {
            if (value <= 0) {
                throw new QuantityMustBeZeroOrPositive(value);
            }
        }

        public static Positive from(int value) {
            return new Positive(value);
        }

    }

    final class Zero implements Quantity {

        @Override
        public int value() {
            return 0;
        }

    }
}
