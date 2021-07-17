package kitchenpos.menu.domain.value;

import javax.persistence.Embeddable;
import kitchenpos.menu.exception.QuantityNotNegativeNumberException;

@Embeddable
public class Quantity {

    private long quantity;

    public Quantity() {
    }

    public Quantity(long quantity) {
        this.quantity = quantity;
    }

    public static Quantity of(long quantity) {
        validateQuantity(quantity);
        return new Quantity(quantity);
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new QuantityNotNegativeNumberException();
        }
    }

    public long getValue() {
        return this.quantity;
    }
}