create table carts(
    id int primary key auto_increment,
    user_id char(36) not null,
    total float not null,
    price float not null,
    date_cart date not null,
    status varchar(255) not null
);

create table cart_products(
    id int primary key auto_increment,
    cart_id int not null,
    product_id int not null,
    quantity int not null
);

create table categories(
    id int primary key auto_increment,
    name varchar(255) not null
);

create table products(
    id int primary key auto_increment,
    name varchar(255) not null,
    price double not null,
    description varchar(255) not null,
    stock int not null,
    style varchar(255),
    image varchar(255),
    category_id int not null
);

create table users(
    id char(36) primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    phone varchar(255) not null,
    address varchar(255) not null,
    role varchar(255) not null
);

create table sesions(
    id char(36) primary key,
    token_value varchar(255) not null unique,
    user_id char(36) not null,
    created_at datetime not null
);