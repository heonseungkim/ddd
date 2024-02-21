drop table if exists book cascade;
drop table if exists member cascade;
drop table if exists reservation cascade;

create table book (
    book_id bigint not null,
    title varchar(150) not null,
    author varchar(50) not null,
    isbn varchar(13) not null unique,
    publisher varchar(50) not null,
    published timestamp(6),
    status varchar(255) check (status in ('AVAILABLE','ON_LOAN')),
    primary key (book_id)
);

create table member (
    member_id bigint not null,
    name varchar(30) not null,
    tel varchar(13) not null unique,
    primary key (member_id)
);

create table reservation (
    reservation_id bigint not null,
    member_id bigint,
    book_id bigint,
    reserved timestamp(6) not null,
    status varchar(255) check (status in ('ON_RESERVE','ON_LOAN','CANCELED')),
    primary key (reservation_id)
);

alter table if exists reservation
    add constraint FK_RESERVATION_BOOK
        foreign key (book_id)
            references book;

alter table if exists reservation
    add constraint FK_RESERVATION_MEMBER
        foreign key (member_id)
            references member;
