create table cart(
    id int primary key auto_increment,
    user_id int not null,
    total float not null,
    price float not null,
    date_cart date not null,
    status varchar(255) not null
);

create table cart_product(
    id int primary key auto_increment,
    cart_id int not null,
    product_id int not null,
    quantity int not null
);

create table category(
    id int primary key auto_increment,
    name varchar(255) not null
);

create table product(
    id int primary key auto_increment,
    name varchar(255) not null,
    price double not null,
    description varchar(255) not null,
    stock int not null,
    style varchar(255),
    category_id int not null
);

create table user(
    id int primary key auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    phone varchar(255) not null,
    address varchar(255) not null,
    role varchar(255) not null
);