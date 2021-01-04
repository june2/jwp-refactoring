package kitchenpos.common;

import java.math.BigDecimal;
import java.util.List;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.Product;
import kitchenpos.domain.TableGroup;

public class TestDataUtil {

	public static Product createProduct(String name, Integer price) {
		Product product = new Product();
		product.setName(name);
		if (price != null) {
			product.setPrice(BigDecimal.valueOf(price));
		}
		return product;
	}

	public static Product createProductById(long productId) {
		Product product = new Product();
		product.setId(productId);
		return product;
	}

	public static OrderTable createOrderTable() {
		OrderTable orderTable = new OrderTable();
		orderTable.setEmpty(true);
		return orderTable;
	}

	public static OrderTable createOrderTableById(long id) {
		OrderTable orderTable = new OrderTable();
		orderTable.setId(id);
		orderTable.setEmpty(true);
		return orderTable;
	}

	public static MenuGroup createMenuGroup(String name) {
		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setName(name);
		return menuGroup;
	}

	public static MenuProduct createMenuProduct(long productId, int quantity) {
		MenuProduct menuProduct = new MenuProduct();
		menuProduct.setProductId(productId);
		menuProduct.setQuantity(quantity);
		return menuProduct;
	}

	public static Menu createMenu(String name, Integer price, Long menuGroupId, List<MenuProduct> menuProducts) {
		Menu menu = new Menu();
		menu.setName(name);
		if (price != null) {
			menu.setPrice(BigDecimal.valueOf(price));
		}
		menu.setMenuGroupId(menuGroupId);
		menu.setMenuProducts(menuProducts);
		return menu;
	}

	public static TableGroup createTableGroup(List<OrderTable> orderTables) {
		TableGroup tableGroup = new TableGroup();
		tableGroup.setOrderTables(orderTables);
		return tableGroup;
	}

	public static Order createOrder(Long tableId, List<OrderLineItem> orderLineItems) {
		Order order = new Order();
		order.setOrderTableId(tableId);
		order.setOrderLineItems(orderLineItems);
		return order;
	}

	public static Order createOrderByIdAndStatus(long id, OrderStatus orderStatus) {
		Order order = new Order();
		order.setId(id);
		order.setOrderStatus(orderStatus.name());
		return order;
	}

	public static OrderLineItem createOrderLineItem(Long menuId, long quantity) {
		OrderLineItem item = new OrderLineItem();
		item.setMenuId(menuId);
		item.setQuantity(quantity);
		return item;
	}
}