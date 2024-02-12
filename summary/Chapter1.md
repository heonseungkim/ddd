# 도메인 모델 시작하기

### 1. 도메인이란?
* 소프트웨어로 해결하고자 하는 문제 영역
* ex) 온라인 서점
  * 회원, 혜택, 주문, 결제, ...
  * 일부 도메인은 외부 시스템과의 연동

### 2. 도메인 전문가와 개발자 간 지식 공유
* 도메인 전문가
  * 도메인에 대한 지식과 경험을 바탕으로 원하는 기능 개발 요구
    * ex) AS 기사는 고개에게 보내는 문자 메시지를 빠르게 입력할 수 있는 탬플릿 추천 기능을 요구
* 개발자
  * 요구사항을 분석하고 설계, 코드 작성, 테스트, 배포
* 요구사항을 올바르게 이해하는 것이 중요함
  * 개발자와 전문가의 소통을 통해

### 3. 도메인 모델
* 도메인을 개념적으로 표현한 것
* 여러 관계자들이 동일한 모습으로 도메인을 이해하고 지식을 공유하는데 도움이 된다.
* ERD, 상태 다이어그램 등을 통해 표현할 수 있다.

### 4. 도메인 모델 패턴
* 도메인 규칙을 객체 지향 기법으로 구현하는 패턴
* 일반적 아키텍쳐는 네 개의 영역으로 구성된다.
  * 표현
    * 사용자 요청 처리, 정보를 보여줌
    * 사용자는 외부 시스템이 될 수도 있다.
  * 응용
    * 사용자가 요청한 기능을 수행
    * 업무 로직을 직접 구현하지 않고 도메인 계층을 조합하여 기능 수행
  * **_도메인_**
    * 시스템이 제공할 도메인 규칙을 구현
    * ex) 주문
      * 출고 전 배송지 변경 가능
      * 주문 취소는 배송 전에만 가능
  * 인프라스트럭쳐
    * 데이터베이스나 메시징 시스템 등 외부 시스템과의 연동

### 5. 도메인 모델 도출
* 도메인의 모델링은 요구사항에서 출발한다.
* ex) 주문 도메인에서 다음 요구사항이 있다고 하자.
  * 출고를 하면 배송지를 변경할 수 없다.
  * 출고 전에 주문을 취소할 수 있다.
  * 고객이 결제를 완료하기 전에는 상품을 준비하지 않는다.
  * 주문은 _출고 상태로 변경하기_, _배송지 정보 변경하기_, _주문 취소하기_, _결제 완료하기_ 기능을 제공해야 한다.
    * ```java
      public class Order {
          public void changeShipped() {...}
          public void changeShippingInfo(ShippingInfo newShipping) {...}
          public void cancel() {...}
          public void completePayment() {...}
      }
      ```
  * 한 상품을 한 개 이상 주문할 수 있다.
  * 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.
    * ```java
      public class OrderLine { // 한 상품을 얼마에 몇개 살지
        private Product product;
        private int price;
        private int quantity;
        private int amounts;
        
        public OrderLine(Product product, int price, int quantity) {
            this.product = product;
            this.price = price;
            this.quantity = quantity;
            this.amounts = calculateAmounts();
        }
      
        private int calculateAmounts() {
            return price * quantity;
        }
      
        ... 
      }
      
      public class Order {
        ...
        private List<OrderLine> orderLines;
        private Money totalAmounts;
      
        public Order(List<OrderLine> orderLines) {
            setOrderLines(orderLines);
        }
      
        private void setOrderLines(List<OrderLine> orderLines) {
            verifyAtLeastOneOrMoreOrderLines(orderLines);
            this.orderLines = orderLines;
            calculateTotalAmouts();
        }
        
        private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
            if (orderLines == null || orderLines.isEmpty()) {
                throw new IllegalArgumentException("No OrderLine");
            }
        }
      
        private void calculateTotalAmouts() {
            int sum = orderLines.stream()
                                .mapToInt(x -> x.getAmounts())
                                .sum();
            this.totalAmounts = new Money(sum);
        }
        ...
      }
      ```
      
### 6. 엔티티와 밸류
* 도출한 모델은 크게 엔티티와 밸류로 구분할 수 있다.
* **엔티티**
  * 식별자를 갖는 모델
    * 식별자는 도메인의 특징과 사용하는 기술에 따라 다르게 구현한다.
      * 특정 규칙에 따라 생성
      * UUID, NanoID 와 같은 고유 식별자 생성기 사용
      * 값을 직접 입력
      * 일렬번호 사용 (시퀀스나 DB 자동 증가 컬럼 등)
  * 주문의 경우 주문 번호가 식별자가 된다.
  * 식별자가 같으면 두 엔티티는 같다고 판단한다.
    * equals(), hashCode() 메서드를 통해 구현한다.
* **밸류**
  * 개념적으로 완전한 하나를 표현하는 모델
  * 의미를 명확히 하기 위해 사용하며, 코드의 의미를 더 잘 이해할 수 있도록 도와준다.
  * 밸류 타입은 불변으로 구현한다.
    * 가장 중요한 이유는 안전한 코드를 작성할 수 있다는 데 있다.
    * 데이터를 함부로 바꿀 수 없기 때문에 안전한 사용이 가능하다.
    * 불변객체는 참조 투명성과 스레드 안전한 특징을 갖는다.
  * 두 밸류 객체를 비교할 때 모든 속성이 같은지 비교한다.
  * 엔티티의 식별자로 사용도 가능하다.
    * ex) 주문 번호
    * ```java
      public class Order {
        private OrderNo id;
      }
      ```
  * ex) 주소 밸류
    * ```java
      public class Address {
          private String address1;
          private String address2;
          private String zipcode;
    
          public Address(String address1, String address2, String zipcode) {
              this.address1 = address1;
              this.address2 = address2;
              this.zipcode = zipcode;
          }
          ... getters
      }
      ```
  * 밸류 타입이 꼭 두개 이상의 데이터를 가져야 하는 것은 아니다.
  * ex) 돈 밸류
    * ```java
      public class Money {
        private int value;
      
        public Money(int value) {
            this.value = value;
        }
      
        public int getValue() {
            return this.value;
        }
      
        // 밸류타입을 위한 기능 추가
        public Money add(Money money) {
            return new Money(this.value + money.value)
        }
        public Money multiply(int multiplier) {
            return new Money(value * multiplier);
        }
      }
      ```
* 도메인 모델에 setter 두지 않기
  * 도메인 모델에 getter/setter를 무조건 추가하는 것은 좋지 않다.
  * 특히, setter는 도메인의 핵심 개념이나 의도를 코드에서 사라지게 한다.
  * 또한 setter를 두면, 도메인 객체 생성 시 온전하지 않은 상태일 수 있다.

### 7. 도메인 용어와 유비쿼터스 언어
* 코드 작성시 도메인에서 사용하는 용어에 대한 이야기
* 유비쿼터스 언어란, 전문가, 관계자, 개발자가 도메인 관련된 공통 언어를 만들어 이를 모든 곳에서 같은 용어로 사용하는 것을 말한다.
* 이 과정에서 개발자는 도메인과 코드 사이에서 모호함을 줄이고 불필요한 해석과정을 줄일 수 있다.
* 주문 상태를 다음과 같이 코딩한다면..
  * ```java
    public enum OrderState {
        STEP1, STEP2, STEP3, STEP4, STEP5, STEP6;
        // 결제 대기중, 상품 준비중, 출고됨, 배송중, 배송완료, 주문취소
    }
    
    // 아래처럼 변경한다면?
    public enum OrderState {
        PAMENT_WAITING, PREPAIRING, SHIPPED, DELIVERING, DELIVERY_COMPLETED;
    }
    ```