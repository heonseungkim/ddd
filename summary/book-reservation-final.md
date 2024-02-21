* User
  * auto_increment 식별자
  * 이름, 휴대폰번호 정보를 갖는다.
  * 자신의 예약 현황을 조회할 수 있다.
  * 자신의 대출 현황을 조회할 수 있다.

* Book
  * auto_increment 식별자
  * 제목, 저자, ISBN, 출판사, 출판년월일 정보를 갖는다.
  * 해당 도서는 대여 상태를 갖는다. (대여 가능, 대여중, )

* Reservation
  * auto_increment 식별자
  * User, Book에 대한 참조값을 갖는다.
  * 예약 날짜를 갖는다.
  * 예약중, 대여완료, 취소됨 상태를 갖는다.