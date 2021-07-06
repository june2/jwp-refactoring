# 키친포스

## 요구 사항
### 메뉴 (menu)
- [ ] 메뉴들을 조회할수 있다.
- [ ] 메뉴를 등록할수 있다.
	* 가격은 필수값이고, 가격이 0원 이상이어야 한다.
	* 메뉴 그룹이 존재하는 메뉴만 등록할 수 있다.
	* 상품에 등록된 메뉴만 등록할 수 있다.
	* 메뉴의 가격은 `[메뉴의 수량] X [상품의 가격]` 보다 비쌀 수 없다.

### 메뉴 그룹 (menu group)
- [x] 메뉴 그룹들을 조회할수 있다.
- [x] 메뉴 그룹을 등록할수 있다.

### 상품 (product)
- [x] 상품들을 조회할수 있다.
- [x] 상품을 등록할수 있다.
	* 가격은 필수값이고, 가격이 0원 이상이어야 한다.

### 주문 (order)
- [ ] 주문들을 조회할수 있다.
- [ ] 주문을 등록할수 있다.
	* 주문 등록시 주문 항목들이 존재해야 한다.
	* 주문한 메뉴가 존재하는 메뉴여야 한다.
	* 주문 테이블이 존재해야 한다.
	* 주문 테이블은 비어있지 않아야 한다.
	* 주문 생성시 `조리` 상태이다.
- [ ] 주문상태를 수정할수 있다.
	* 존재하는 주문만 상태를 변경 가능하다.
	* `계산 완료` 상태의 주문은 상태 변경이 불가하다.

### 주문 테이블 (order table)
- [ ] 주문 테이블들을 조회할수 있다.
- [ ] 주문 테이블을 등록할수 있다.
	* 주문 테이블 그룹의 초기값은 존재하지 않는다.
- [ ] 주문 테이블의 비어있는 상태값을 수정할수 있다.
    * 등록된 주문 테이블만 상태값을 수정할 수 있다.
	* 그룹 설정이 되어 있는 주문테이블은 상태를 바꿀 수 없다.
	* 주문테이블의 주문이 `조리` 상태, `식사` 상태이면 주문 테이블 상태를 바꿀 수 없다. 
- [ ] 주문 테이블의 손님수를 수정할수 있다.
	* 손님 수는 1명 이상만 가능하다.
	* 등록된 주문 테이블만 손님 수를 수정할 수 있다.
	* 비워있지 않은 주문 테이블만 손님 수를 수정할 수 있다.
	
### 주문 테이블 그룹 (order table)
- [ ] 주문 테이블 그룹을 등록할수 있다.
    * 2개 이상의 주문 테이블만 등록할수 있다.
    * 주문테이블들은 등록되어 있어야 한다.
    * 테이블 그룹이 존재해야한다.
- [ ] 주문 테이블 그룹을 삭제할수 있다.
    * 주문 테이블 그룹의 주문 테이블이 `조리` 상태이거나, `식사` 상태이면, 삭제할수 없다.

## 테이블 스키마
![ERD](src\main\resources\db\erd\erd.png)

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

### 주문 상태
조리(COOKING) ➜ 식사(MEAL) ➜ 계산 완료(COMPLETION)