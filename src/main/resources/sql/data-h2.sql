insert into book
    (`book_id`, `title`, `author`, `isbn`, `publisher`, `published`, `status`) values
    (1, '1번 책', '1번 저자', '1번 ISBN', '1번 출판사', '2001-01-01 01:01:01', 'AVAILABLE'),
    (2, '2번 책', '2번 저자', '2번 ISBN', '2번 출판사', '2002-02-02 02:02:02', 'ON_LOAN'),
    (3, '3번 책', '3번 저자', '3번 ISBN', '1번 출판사', '2003-03-03 03:03:03', 'AVAILABLE'),
    (4, '4번 책', '4번 저자', '4번 ISBN', '4번 출판사', '2004-04-04 04:04:04', 'AVAILABLE'),
    (5, '5번 책', '5번 저자', '5번 ISBN', '5번 출판사', '2005-05-05 05:05:05', 'AVAILABLE');

insert into member
    (`member_id`, `name`, `tel`) values
    (1, '김헌승', '010-1111-1111'),
    (2, '박산희', '010-2222-2222'),
    (3, '이나리', '010-3333-3333');

insert into reservation
    (`reservation_id`, `member_id`, `book_id`, `reserved`, `status`) values
    (1, 2, 2, '2024-02-19 00:00:00', 'CANCELED'),
    (2, 3, 2, '2024-02-20 00:00:00', 'ON_RESERVE');
