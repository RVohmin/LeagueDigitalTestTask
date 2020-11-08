drop table if exists prices;
drop table if exists products;

create table if not exists products
(
    id   serial primary key,
    name varchar(2000)
);

create table if not exists prices
(
    price_id     serial primary key,
    price        int     not null,
    price_date   date    not null default now(),
    price_status boolean not null default true,
    product_id   int references products (id)
);