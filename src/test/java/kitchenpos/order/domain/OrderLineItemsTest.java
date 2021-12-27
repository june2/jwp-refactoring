package kitchenpos.order.domain;

import kitchenpos.common.domain.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 항목들 테스트")
class OrderLineItemsTest {

    @DisplayName("주문 항목들 생성 성공 테스트")
    @Test
    void instantiate_success() {
        // when
        Long menuId = 1L;
        OrderLineItem orderLineItem = OrderLineItem.of(menuId, Quantity.of(1));
        OrderLineItems orderLineItems = OrderLineItems.of(Arrays.asList(orderLineItem));

        // then
        assertAll(
                () -> assertThat(orderLineItems).isNotNull()
                , () -> assertThat(orderLineItems.getOrderLineItems()).isEqualTo(Arrays.asList(orderLineItem))
        );
    }

    @DisplayName("주문 항목들 생성 실패 테스트 - 주문 항목 없음")
    @Test
    void instantiate_failure() {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> OrderLineItems.of(Collections.emptyList()));
    }
}