drop table if exists book cascade;
drop table if exists loan cascade;
drop table if exists member cascade;
drop table if exists reservation cascade;

create table book (
    book_id bigint not null,
    published timestamp(6),
    isbn varchar(13) not null unique,
    author varchar(50) not null,
    publisher varchar(50) not null,
    title varchar(150) not null,
    status varchar(255) check (status in ('AVAILABLE','ON_LOAN')),
    primary key (book_id)
);

create table loan (
    loan_id bigint not null,
    book_id bigint,
    member_id bigint,
    loaned timestamp(6),
    returned timestamp(6) default NULL,
    primary key (loan_id)
);

create table member (
    member_id bigint not null,
    tel varchar(13) not null unique,
    name varchar(30) not null,
    primary key (member_id)
);

create table reservation (
    book_id bigint,
    member_id bigint,
    reservation_id bigint not null,
    reserved timestamp(6) not null,
    status varchar(255) check (status in ('ON_RESERVATION','ON_LOAN','CANCELED')),
    primary key (reservation_id)
);

alter table if exists loan
    add constraint FK_LOAN_BOOK
    foreign key (book_id)
    references book;

alter table if exists loan
    add constraint FK_LOAN_MEMBER
    foreign key (member_id)
    references member;

alter table if exists reservation
    add constraint FK_RESERVATION_BOOK
    foreign key (book_id)
    references book;

alter table if exists reservation
    add constraint FK_RESERVATION_MEMBER
    foreign key (member_id)
    references member;
