package kitchenpos.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kitchenpos.exception.AlreadyOrderCompleteException;
import kitchenpos.exception.EmptyTableException;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_table_id")
	private OrderTable orderTable;
	private String orderStatus;
	private LocalDateTime orderedTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderLineItem> orderLineItems = new ArrayList<>();

	protected Order() {
	}

	private Order(OrderTable orderTable, List<Menu> menus, List<Long> quantities) {
		validateOrderTable(orderTable);
		addOrderLineItems(menus, quantities);
		this.orderTable = orderTable;
		this.orderStatus = OrderStatus.COOKING.name();
		this.orderedTime = LocalDateTime.now();
	}

	public static Order create(OrderTable orderTable, List<Menu> menus, List<Long> quantities) {
		return new Order(orderTable, menus, quantities);
	}

	public void changeOrderStatus(final String orderStatus) {
		validateBeforeChangeStatus();
		this.orderStatus = orderStatus;
	}

	private void addOrderLineItems(List<Menu> menus, List<Long> quantities) {
		for (int i = 0; i < menus.size(); i++) {
			orderLineItems.add(OrderLineItem.create(this, menus.get(i), quantities.get(i)));
		}
	}

	private void validateOrderTable(OrderTable orderTable) {
		if (orderTable.isEmpty()) {
			throw new EmptyTableException("빈 테이블일 경우 주문을 진행할 수 없습니다.");
		}
	}

	private void validateBeforeChangeStatus() {
		if (Objects.equals(OrderStatus.COMPLETION.name(), this.orderStatus)) {
			throw new AlreadyOrderCompleteException("이미 완료된 주문입니다.");
		}
	}

	public Long getId() {
		return id;
	}

	public OrderTable getOrderTable() {
		return orderTable;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public LocalDateTime getOrderedTime() {
		return orderedTime;
	}

	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}
}
