package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {
    @Column(name = "quantity")
    private long quantity;

    protected Quantity() {
    }

    private Quantity(long quantity) {
        this.quantity = quantity;
    }

    public static Quantity of(long quantity) {
        return new Quantity(quantity);
    }

    public long getValue() {
        return quantity;
    }
}