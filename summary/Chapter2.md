# 아키텍처 개요

### 1. 네 개의 영역
* 일반적 아키텍쳐는 네 개의 영역으로 구성된다.
  * 표현
    * HTTP 요청을 응용 영역이 필요로 하는 형식으로 변환
    * 응용 영역의 응답을 HTTP 응답으로 변환
  * 응용
    * 시스템이 사용자에게 제공해야 할 기능을 구현
    * 도메인 영역의 도메인 모델을 사용한다.
    * 응용 서비스는 직접 로직을 수행하기보다는 도메인 모델에 로직 수행을 위임한다.
    * ```java
      public class CancelOrderService {
        @Transactional
        public void cancelOrder(String orderId) {
            Order order = findOrderById(orderId);
            if (order == null) throw new OrderNotFoundException(orderId);
            order.cancel(); // Order 객체에 취소 처리를 위임
        }
      }
      ```
  * 도메인
    * 도메인 모델의 구현
    * 도메인 핵심 로직의 구현
  * 인프라스트럭쳐
    * 구현 기술에 대한 영역
    * RDBMS 연동, 메시징 큐 송수신 등
    * 논리적 개념을 표현하기 보다는 실제 구현을 다룬다.

### 2. 계층 구조 아키텍처
* 계층 구조는 특성상 상위 계층에서 하위 계층으로의 의존만 존재한다.
* 엄격히는 상위 계층은 바로 아래 계층에만 의존을 가져야 하지만, 구현의 편리함을 위해 계층 구조를 유연히 적용한다.
  * ex) 표현 계층은 응용 계층에 의존하지만 인프라스트럭쳐 계층이 도메인에 의존하진 않는다.
* 응용, 도메인 영역이 인프라스트럭쳐에 종속적인 구조를 생각해보자.
* 응용 계층을 테스트 할 때, 인프라스트럭처가 완벽히 동작해야 하는 문제 등이 있다.
* 즉, 구현 변경과 테스트가 어렵다는 문제가 발생한다.

### 3. DIP (Dependency Inversion Principle)
* 의존관계 역전 원칙
  * 상위 계층이 하위 계층으로부터 독립적일 수 있게 해준다.
  * 대신 하위 계층이 상위 계층에 의존한다.
* 가격 할인을 계산하려는 상황
  * 가격 할인 계산 (CalculateDiscountService)
    * 고객의 정보를 구한다. (고수준)
      * RDBMS에서 JPA로 구현한다. (저수준)
    * 룰을 이용해 할인 금액을 구한다. (고수준)
      * 룰 적용 엔진을 통해 룰을 적용한다. (저수준)
  * 고수준 모듈이 저수준 모듈을 사용하면, 앞서 계층 구조 아키텍처에서 발생한 문제가 발생한다.
* DIP는 위와 같은 문제를 해결하기 위해 저수준 모듈이 고수준 모듈에 의존하도록 바꾼다.
  * 추상화한 인터페이스를 통해
  * 응용 계층 입장에서, 룰 적용 엔진이 무엇으로 구현되어 있는지 중요하지 않다.
  * 오직 _룰을 적용해서 할인 금액을 구한다._ 라는 것만 중요할 뿐
    * ```java
      public interface RuleDiscounter {
        Money applyRules(Customer customer, List<OrderLine> orderLines);
      }
      ```
  * 위 구현체는 어떤 룰 엔진을 사용하든 응용 계층 입장에서는 룰을 적용한다는 사실만 알 뿐이다.
  * 인프라스트럭처 계층에서는 위 인터페이스를 원하는 기술을 통해 구현하면 된다.
  * 구현 기술이 변경되더라도, 인터페이스는 동일하기 때문에 응용 계층을 수정할 필요가 없다.
  * 테스트 또한 대역 객체를 이용하여 가능하다. (구체적인 구현 클래스가 없어도 가능!)
* **_주의사항_**
  * DIP를 적용할 때 하위 기능을 추상화한 인터페이스는 고수준 모듈 관점에서 도출해야 한다.
  * CalculateDiscountService 입장에서 봤을 때, 할인 금액을 구하는 것이 
    * 룰 엔진을 사용하는가?
    * 직접 계산하는가?
  * 에 대한 고민은 중요하지 않다.
  * 단지 규칙에 따라 할인 금액을 계산한다는 것이 중요하고, 이를 위해 _할인 금액 계산_을 추상화한 인터페이스는 저수준 모듈이 아닌 고수준 모듈에 위치해야 한다.

### 4. 도메인 영역의 주요 구성요소
* 도메인 영역은 도메인의 핵심 모델을 구현한다.
  * 주요 개념을 표현하며 핵심 로직을 구현
* 도메인 영역의 구성요소
  * 엔티티
    * 고유 식별자를 갖는 객체
    * 도메인 모델의 데이터를 포함하며 데이터와 관련된 기능을 제공
  * 밸류
    * 고유 식별자를 갖지 않는 객체
    * 개념적으로 하나인 값을 표현
  * 애그리거트
    * 연관된 엔티티와 밸류 객체를 개념적으로 하나로 묶은 것
  * 리포지터리
    * 도메인 모델의 영속성을 처리
    * DBMS 테이블에서 엔티티 객체를 로딩하거나 자정하는 등의 기능
  * 도메인 서비스
    * 특정 엔티티에 속하지 않은 도메인 로직을 제공
    * 여러 엔티티와 밸류를 필요로 하는 도메인 로직이 있는 경우 도메인 서비스에서 구현
* 엔티티와 밸류
  * 엔티티
    * 도메인 엔티티와 DB 엔티티
      * 도메인 모델의 엔티티는 데이터와 함께 도메인 기능을 함께 제공한다.
      * 도메인 모델의 엔티티는 두 개 이상의 데이터가 개념적으로 하나인 경우 밸류 타입을 이용해서 표현할 수 있다.
  * 밸류
    * 불변으로 구현하는 것이 권장
    * 엔티티의 밸류 타입 데이터를 변경할 때는 객체 자체를 완전히 교체
* 애그리거트
  * 관련 객체를 하나로 묶은 군집으로 전체 구조를 이해하는데 도움이 되는 것을 뜻한다.
  * 도메인이 커질수록 도메인 모델도 커지며, 많은 엔티티와 밸류가 출현한다.
  * 큰 수준에서 모델을 이해하지 못해 모델의 관리가 어려워진다.
* 리포지터리
  * 도메인 객체를 지속적으로 사용하기 위해 물리적인 저장소에 도메인 객체를 보관하는데, 이를 위한 도메인 모델을 리포지터리라 한다.
  * 리포지터리는 애그리거트 단위로 도메인 객체를 저장하고 조회하는 기능을 정의한다.
  * 응용 서비스와 밀접한 연관이 있다.
    * 응용 서비스는 필요한 도메인 객체를 구하거나 저장하기 위해 리포지터리를 사용
    * 응용 서비스는 트랜잭션 관리를 하는데, 트랜잭션 처리는 리포지터리 구현 기술의 영향을 받음
    * 따라서 리포지터리는 애그리거트를 저장하는 메서드, 애그리거트 루트 식별자로 애그리거트를 조회하는 메서드 등을 제공해야 한다.

### 5. 요청 처리 흐름
* 표현 영역
  * 사용자의 요청을 처음으로 받음
  * 사용자 요청을 검증하고, 문제가 없다면 응용 영역에서 요구하는 형식으로 변환
* 응용 영역
  * 도메인 모델을 이용해 기능을 구현
* 도메인 영역
  * 도메인 객체를 리포지터리에서 가져오거나 / 신규 도메인 객체를 생성해서 리포지터리에 저장

### 6. 인프라스트럭처 개요
* 인프라스트럭처는 표현/응용/도메인 영역을 지원한다.
* [DIP] 도메인/응용 영역에서 정의한 인터페이스를 인프라스트럭처 영역에서 구현하는 것이 시스템을 더 유연하고 테스트하기 쉽게 만든다.
  * 반드시 그럴 필요는 없음
    * Spring의 ```@Table``` 어노테이션은 인프라스트럭처에 대한 의존을 일부 도메인 모델에 넣어야 한다.

### 7. 모듈 구성
* 아키텍처의 각 영역은 별도 패키지에 위치한다.
  * 도메인이 크지 않은 경우
    * ```shell
      # com.myshop
      ui -> application -> domain <- infrastructure
      ```
  * 도메인이 큰 경우
    * ```shell
      # com.myshop
      order
        ui -> application -> domain <- infrastructure
      member
        ui -> application -> domain <- infrastructure
      catalog
        ui -> application -> domain <- infrastructure
      
      catalog <- order -> member
      ```
* 도메인 모듈은 애그리거트를 기준으로 다시 패키지를 구성한다.
  * 카탈로그 하위 도메인이 상품 애그리거트와 카테고리 애그리거트로 구성되는 경우
    * ```shell
      # com.myshop
      catalog
        ui -> application -> domain           <- infrastructure
                             product category
      
      ```
  * 애그리거트, 모델, 리포지터리는 같은 패키지에 위치시킨다.