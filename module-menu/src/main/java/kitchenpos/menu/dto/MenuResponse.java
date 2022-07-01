package kitchenpos.menu.dto;

import kitchenpos.menu.domain.Menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MenuResponse {
    private Long id;

    private String name;

    private BigDecimal price;

    private Long menuGroupId;

    private List<MenuProductResponse> menuProducts;

    protected MenuResponse() {
    }

    private MenuResponse(Long id, String name, BigDecimal price, Long menuGroupId,
                         List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(menu.getId(), menu.getName().getName(), menu.getPrice().getValue(),
                menu.getMenuGroupId(),
                menu.getMenuProducts()
                        .getValues()
                        .stream()
                        .map(MenuProductResponse::from)
                        .collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return menuProducts;
    }
}