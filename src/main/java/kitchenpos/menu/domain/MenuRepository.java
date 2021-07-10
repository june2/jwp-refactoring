package kitchenpos.menu.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

  long countByIdIn(Iterable<Long> menuIds);
}