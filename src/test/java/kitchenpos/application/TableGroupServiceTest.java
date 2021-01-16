package kitchenpos.application;

import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("단체 지정 서비스에 관련한 기능")
@ExtendWith(value = MockitoExtension.class)
class TableGroupServiceTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderTableDao orderTableDao;
    @Mock
    private TableGroupDao tableGroupDao;
    @InjectMocks
    private TableGroupService tableGroupService;

    private OrderTable orderTable1;
    private OrderTable orderTable2;
    private TableGroup tableGroup;

    @BeforeEach
    void setUp() {
        orderTable1 = new OrderTable();
        orderTable1.setId(1L);
        orderTable1.setEmpty(true);
        orderTable1.setTableGroupId(null);

        orderTable2 = new OrderTable();
        orderTable2.setId(2L);
        orderTable2.setEmpty(true);
        orderTable2.setTableGroupId(null);

        tableGroup = new TableGroup();
        tableGroup.setId(1L);
        tableGroup.setOrderTables(Arrays.asList(orderTable1, orderTable2));
    }

    @DisplayName("`단체 지정`을 생성한다.")
    @Test
    void createTableGroup() {
        // Given
        given(orderTableDao.findAllByIdIn(Arrays.asList(orderTable1.getId(), orderTable2.getId())))
                .willReturn(Arrays.asList(orderTable1, orderTable2));
        given(tableGroupDao.save(tableGroup)).willReturn(tableGroup);
        // When
        TableGroup actual = tableGroupService.create(tableGroup);
        // Then
        assertAll(
                () -> assertEquals(tableGroup.getId(), actual.getId()),
                () -> assertEquals(tableGroup.getOrderTables(), actual.getOrderTables()),
                () -> assertNotNull(actual.getCreatedDate())
        );
    }

    @DisplayName("`단체 지정`으로 등록할 `주문 테이블`이 2개 미만이면 생성할 수 없다.")
    @Test
    void exceptionToCreateTableGroupWithZeroOrOneOrderTable() {
        // Given
        tableGroup.setOrderTables(Collections.singletonList(orderTable1));
        // When & Then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("`단체 지정`할 `주문 테이블`은 비어있어야한다.")
    @Test
    void exceptionToCreateTableGroupWithNonemptyOrderTable() {
        // Given
        orderTable1.setEmpty(false);
        given(orderTableDao.findAllByIdIn(Arrays.asList(orderTable1.getId(), orderTable2.getId())))
                .willReturn(Arrays.asList(orderTable1, orderTable2));
        // When & Then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("`단체 지정`할 `주문 테이블`이 `단체 지정`되어있으면 생성할 수 없다.")
    @Test
    void exceptionToCreateTableGroupWithRegisteredOrderTable() {
        // Given
        orderTable1.setTableGroupId(1L);
        tableGroup.setOrderTables(Arrays.asList(orderTable1, orderTable2));
        given(orderTableDao.findAllByIdIn(Arrays.asList(orderTable1.getId(), orderTable2.getId())))
                .willReturn(Arrays.asList(orderTable1, orderTable2));
        // When & Then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("`단체 지정`을 해제한다.")
    @Test
    void ungroupTableGroup() {
        // Given
        orderTable1.setTableGroupId(1L);
        orderTable2.setTableGroupId(1L);
        given(orderTableDao.findAllByTableGroupId(tableGroup.getId()))
                .willReturn(Arrays.asList(orderTable1, orderTable2));
        List<Long> orderTableIds = Arrays.asList(orderTable1.getId(), orderTable2.getId());
        List<String> orderStatuses = Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name());
        given(orderDao.existsByOrderTableIdInAndOrderStatusIn(orderTableIds, orderStatuses)).willReturn(false);
        // When
        tableGroupService.ungroup(tableGroup.getId());
        // Then
        assertAll(
                () -> assertNull(orderTable1.getTableGroupId()),
                () -> assertNull(orderTable2.getTableGroupId())
        );
    }

    @DisplayName("`단체 지정`된 `주문 테이블`에서 `주문 상태`가 'COOKING' 이나 'MEAL' 이면 해제할 수 없다.")
    @Test
    void exceptionToUn() {
        // Given
        orderTable1.setTableGroupId(1L);
        orderTable2.setTableGroupId(1L);
        given(orderTableDao.findAllByTableGroupId(tableGroup.getId()))
                .willReturn(Arrays.asList(orderTable1, orderTable2));
        List<Long> orderTableIds = Arrays.asList(orderTable1.getId(), orderTable2.getId());
        List<String> orderStatuses = Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name());
        given(orderDao.existsByOrderTableIdInAndOrderStatusIn(orderTableIds, orderStatuses)).willReturn(true);
        // When & Then
        assertThatThrownBy(() -> tableGroupService.ungroup(tableGroup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}