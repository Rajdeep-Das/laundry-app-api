---- Product Table----
create table product
(id bigint identity not null,
created_by bigint,
created_on datetime2,
gender varchar(255),
is_active bit,
is_deleted bit,
name varchar(255),
rank int,
updated_by bigint,
updated_on datetime2,
primary key (id))

alter table product add icon varchar(255)

----- CartItem-----
create table cart_item
(id bigint identity not null, cart_id bigint, price double precision not null,
product_id bigint, quantity int not null, service_id bigint,
total_price double precision not null, primary key (id))
----- Cart---------
