package kitchenpos.ui;

import kitchenpos.application.OrderService;
import kitchenpos.domain.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/api/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> create(@RequestBody final Order order) {
        final Order created = orderService.create(order);
        final URI uri = URI.create("/api/orders/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created);
    }

    @GetMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> list() {
        return ResponseEntity.ok()
                .body(orderService.list());
    }

    @PutMapping(value = "/api/orders/{orderId}/order-status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> changeOrderStatus(@PathVariable final Long orderId, @RequestBody final Order order) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, order));
    }
}
