create table brands (
    id varchar(255) not null,
    title varchar(255),
    primary key (id)
);

create table buckets (
    id varchar(255) not null,
    user_id varchar(255),
    primary key (id)
);

create table buckets_products (
    bucket_id varchar(255) not null,
    product_id varchar(255) not null
);

create table categories (
    id varchar(255) not null,
    title varchar(255),
    primary key (id)
);

create table orders (
    id varchar(255) not null,
    address varchar(255),
    created timestamp,
    phone varchar(255),
    recipient varchar(255),
    status varchar(255),
    sum numeric(19, 2),
    updated timestamp,
    user_id varchar(255),
    primary key (id)
);

create table orders_details (
    id varchar(255) not null,
    amount numeric(19, 2),
    price numeric(19, 2),
    order_id varchar(255),
    product_id varchar(255),
    primary key (id)
);

create table products (
    id varchar(255) not null,
    available boolean, code varchar(255),
    description varchar(255),
    picture_code varchar(255),
    price float8,
    title varchar(255),
    brand_id varchar(255),
    category_id varchar(255),
    primary key (id)
 );

create table users (
    id varchar(255) not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    nick_name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);

alter table if exists buckets
    add constraint FKnl0ltaj67xhydcrfbq8401nvj
        foreign key (user_id) references users;

alter table if exists buckets_products
    add constraint FKloyxdc1uy11tayedf3dpu9lci
        foreign key (product_id) references products;

alter table if exists buckets_products
    add constraint FKc49ah45o66gy2f2f4c3os3149
        foreign key (bucket_id) references buckets;

alter table if exists orders
    add constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users;

alter table if exists orders_details
    add constraint FK5o977kj2vptwo70fu7w7so9fe
        foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint FKs0r9x49croribb4j6tah648gt
        foreign key (product_id) references products;

alter table if exists products
    add constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv
        foreign key (brand_id) references brands;

alter table if exists products
    add constraint FKog2rp4qthbtt2lfyhfo32lsw9
        foreign key (category_id) references categories;