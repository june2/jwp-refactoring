package kitchenpos.table.domain;

import kitchenpos.common.exception.InvalidOrderStatusException;
import kitchenpos.common.exception.IsEmptyTableException;
import kitchenpos.common.exception.IsNotNullTableGroupException;
import kitchenpos.order.domain.OrderStatus;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "order_table")
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_group_id")
    private TableGroup tableGroup;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Embedded
    private EmptyStatus empty;

    protected OrderTable() {
    }

    public OrderTable(Long id, TableGroup tableGroup, int numberOfGuests, boolean empty) {
        this(tableGroup, numberOfGuests, empty);
        this.id = id;
    }

    public OrderTable(TableGroup tableGroup, int numberOfGuests, boolean empty) {
        this(numberOfGuests, empty);
        this.tableGroup = tableGroup;
    }

    public OrderTable(int numberOfGuests, boolean empty) {
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = new EmptyStatus(empty);
    }

    public static OrderTable ofEmptyTable() {
        return new OrderTable(0, true);
    }

    public Long getId() {
        return id;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public void assignTableGroup(final TableGroup tableGroup) {
        this.tableGroup = tableGroup;
    }

    public int getNumberOfGuests() {
        return numberOfGuests.getNumber();
    }

    public void changeNumberOfGuests(final NumberOfGuests numberOfGuests) {
        checkEmptyOrderTable();
        this.numberOfGuests = numberOfGuests;
    }

    private void checkEmptyOrderTable() {
        if (isEmpty()) {
            throw new IsEmptyTableException();
        }
    }

    public boolean isEmpty() {
        return empty.getStatus();
    }

    public void changeEmpty(final boolean empty) {
        checkTableGroupIsNull();
        this.empty = new EmptyStatus(empty);
    }

    private void checkTableGroupIsNull() {
        if (isNotNullTableGroup()) {
            throw new IsNotNullTableGroupException();
        }
    }

    public boolean isNotNullTableGroup() {
        return !Objects.isNull(tableGroup);
    }

    public void initTableGroup(TableGroup tableGroup) {
        assignTableGroup(tableGroup);
        this.empty = EmptyStatus.ofFalse();
    }

    public void unTableGroup() {
        tableGroup = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id)
                && Objects.equals(tableGroup, that.tableGroup)
                && Objects.equals(numberOfGuests, that.numberOfGuests)
                && Objects.equals(empty, that.empty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableGroup, numberOfGuests, empty);
    }
}