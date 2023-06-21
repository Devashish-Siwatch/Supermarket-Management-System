create database supermarket;

-- Query 1
create table products(
product_name varchar(40) not null,
product_id int unsigned not null unique primary key auto_increment,
description varchar(200),
price decimal(10,2) not null check(price>0),
quantity int not null default '0' check(quantity>=0)
);

drop table products;

-- Query 2
create table suppliers(
supplier_name varchar(40) not null,
supplier_id int unsigned primary key not null auto_increment,
address varchar(100),
contact_info char(10)
);

drop table suppliers;

-- Query 3
create table customers(
customer_name varchar(40) not null,
customer_id int unsigned primary key not null auto_increment,
address varchar(100),
contact_info char(10)
);

drop table customers;

-- Query 4
create table employees(
employee_name varchar(40) not null,
employee_id int unsigned primary key not null auto_increment,
address varchar(100),
contact_info char(10),
job_position varchar(40)
);

drop table employees;

-- Query 5,7,8
create table sales(
date_of_sale date,
sales_id int unsigned not null primary key auto_increment,
customer_id int unsigned not null,
product_id int unsigned not null,
employee_id int unsigned not null,
quantity int unsigned not null check(quantity>=0),
FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
FOREIGN KEY (product_id) REFERENCES products(product_id),
FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

drop table sales;

-- Query 6,9,10
create table purchases(
date_of_purchase date,
purchases_id int unsigned not null primary key auto_increment,
supplier_id int unsigned not null,
product_id int unsigned not null,
quantity int unsigned not null check(quantity>=0),
FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
FOREIGN KEY (product_id) REFERENCES products(product_id)
);

drop table purchases;

-- Query 11
insert into products(product_name,description,price,quantity) values ('Bourbon','Biscuit',100,30);
insert into products(product_name,description,price,quantity) values ('Cadbury','Biscuit',100,40);
insert into products(product_name,description,price,quantity) values ('JimJam','Biscuit',130,10);
insert into products(product_name,description,price,quantity) values ('Bourbon','Drink',100,30);
insert into products(product_name,description,price,quantity) values ('Parle','Biscuit',100,30);
insert into products(product_name,description,price,quantity) values ('Fries','Eatables',155,30);
insert into products(product_name,description,price,quantity) values ('Banana','Fruit',10,50);
insert into products(product_name,description,price,quantity) values ('Frooti','Cold Drink',20,30);
insert into products(product_name,description,price,quantity) values ('Pepsi','Cold Drink',40,300);
insert into products(product_name,description,price,quantity) values ('Coke','Cold Drink',40,300);

-- Query 12
insert into suppliers(supplier_name,address,contact_info) values ('Nestle','Mumbai',5789345673);
insert into suppliers(supplier_name,address,contact_info) values ('Cocacola','Pune',7786345673);
insert into suppliers(supplier_name,address,contact_info) values ('Britanica','Delhi',1234567890);
insert into suppliers(supplier_name,address,contact_info) values ('Britanica','Mumbai',1234568790);
insert into suppliers(supplier_name,address,contact_info) values ('Parle','Mumbai',1234554321);
insert into suppliers(supplier_name,address,contact_info) values ('Parle','Ahemdabad',5789345673);
insert into suppliers(supplier_name,address,contact_info) values ('ITC','Mumbai',9415022732);
insert into suppliers(supplier_name,address,contact_info) values ('Grend','Lucknow',9020404673);
insert into suppliers(supplier_name,address,contact_info) values ('Yurpho','Hyderabad',9075643654);

-- Query 13
insert into customers (customer_name,address,contact_info) values('Kshitij','Lucknow',9335300496);
insert into customers (customer_name,address,contact_info) values('Firht','Pilani',9323302196);
insert into customers (customer_name,address,contact_info) values('Gerth','Kolkata',9335312345);
insert into customers (customer_name,address,contact_info) values('Rachel','Patna',8765432109);
insert into customers (customer_name,address,contact_info) values('Withf','Lopui',7685940321);
insert into customers (customer_name,address,contact_info) values('Erchit','Trench',8756940321);
insert into customers (customer_name,address,contact_info) values('Devashish','Ambala',9795668790);
insert into customers (customer_name,address,contact_info) values('Adarsh','Lko',9115300496);
insert into customers (customer_name,address,contact_info) values('Praneet','Delhi',9795338790);
insert into customers (customer_name,address,contact_info) values('Vedant','Chandigarh',9115378566);

-- Query 14
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('John Smith', '123 Main St, Springfield', '555-1234', 'Manager');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Jane Doe', '456 Elm St, New York', '555-5678', 'HR Specialist');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Michael Johnson', '789 Oak St, Los Angeles', '555-9876', 'Software Engineer');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Sarah Williams', '321 Pine St, Chicago', '555-5432', 'Data Analyst');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('David Lee', '567 Cedar St, San Francisco', '555-6789', 'Product Manager');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Emily Thompson', '890 Birch St, Miami', '555-3456', 'Marketing Coordinator');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Jason Brown', '432 Maple St, Dallas', '555-8765', 'Sales Associate');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Jessica Davis', '765 Willow St, Atlanta', '555-2345', 'Finance Manager');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Daniel Wilson', '987 Pine St, Boston', '555-6780', 'IT Specialist');
INSERT INTO employees (employee_name, address, contact_info, job_position) VALUES ('Olivia Turner', '210 Cedar St, Seattle', '555-4567', 'HR Generalist');

select * from employees;
select * from suppliers;
select * from customers;
select * from sales;
select * from purchases;
select * from products;

-- Handling queries 17,18 and 19 respectively
update products set quantity=60 where product_id=1;
update suppliers set contact_info=9415678901 where suppliers.supplier_id=1;
update employees set job_position='Director' where employee_id=1;

-- Procedure to insert sales (Query 15)
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertSales`(IN date_of_sale date,
IN cid int unsigned,
IN pid int unsigned,
IN eid int unsigned,
IN quan int unsigned)
 READS SQL DATA
 DETERMINISTIC
 SQL SECURITY INVOKER
 COMMENT 'To handle insertion of a sale'
BEGIN
    DECLARE quan1 int unsigned;
    select quantity from products where product_id=pid into quan1;
    if(quan1>=quan) then
    begin
    insert into sales(date_of_sale,customer_id,employee_id,product_id,quantity) values (date_of_sale,cid,pid,eid,quan);
    end;
    end if;
    update products set quantity=quan1-quan where product_id=pid;
END$$
DELIMITER ;
CALL InsertSales('2023-04-06', 1, 1, 1, 2);
CALL InsertSales('2023-04-05', 2, 3, 4, 3);
CALL InsertSales('2023-04-04', 3, 2, 1, 5);
CALL InsertSales('2023-04-03', 4, 4, 3, 1);
CALL InsertSales('2023-04-02', 5, 1, 2, 4);
CALL InsertSales('2023-04-01', 1, 3, 5, 2);
CALL InsertSales('2023-03-31', 2, 2, 4, 3);
CALL InsertSales('2023-03-30', 3, 4, 3, 1);
CALL InsertSales('2023-03-29', 4, 1, 1, 5);
CALL InsertSales('2023-03-28', 5, 3, 2, 2);

-- Procedure to insert new purchase (Query 16)
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertPurchases`(IN date_of_purchase date,
IN sid int unsigned,
IN pid int unsigned,
IN quan int unsigned)
 READS SQL DATA
 DETERMINISTIC
 SQL SECURITY INVOKER
 COMMENT 'To handle insertion of a purchase'
BEGIN
    DECLARE quan1 int unsigned;
    select quantity from products where product_id=pid into quan1;
    insert into purchases(date_of_purchase,supplier_id,product_id,quantity) values (date_of_purchase,sid,pid,quan);
    update products set quantity=quan1+quan where product_id=pid;
END$$
DELIMITER ;
CALL InsertPurchases('2023-04-06', 1, 1, 2);
CALL InsertPurchases('2023-04-05', 2, 3, 5);
CALL InsertPurchases('2023-04-04', 3, 2, 3);
CALL InsertPurchases('2023-04-03', 4, 4, 1);
CALL InsertPurchases('2023-04-02', 5, 1, 4);
CALL InsertPurchases('2023-04-01', 1, 3, 2);
CALL InsertPurchases('2023-03-31', 2, 2, 3);
CALL InsertPurchases('2023-03-30', 3, 4, 1);
CALL InsertPurchases('2023-03-29', 4, 1, 5);
CALL InsertPurchases('2023-03-28', 5, 3, 2);

-- Procedure to delete sales (Query 20)
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteSales`(IN id INT unsigned)
 READS SQL DATA
 DETERMINISTIC
 SQL SECURITY INVOKER
 COMMENT 'To handle delete of a sale'
BEGIN
	DECLARE pid int unsigned;
    DECLARE quan int unsigned;
    DECLARE quan1 int unsigned;
    select product_id, quantity from sales where sales_id=id into pid,quan;
    delete from sales where sales_id=id;
    select quantity from products where product_id=pid into quan1;
    update products set quantity=quan+quan1 where product_id=pid;
END$$
DELIMITER ;
drop procedure DeleteSales;
CALL DeleteSales(1);

-- To generate inventory report (Given in initial problem statement)
Select * from products;

-- To generate sales report (Given in initial problem statement)
Select sales_id, date_of_sale, customer_name, product_name,employee_name,sales.quantity, (sales.quantity*products.price) as TotalAmount from (((employees join sales on employees.employee_id=sales.employee_id) join products on products.product_id=sales.product_id) join customers on customers.customer_id=sales.customer_id);

-- To generate employee report (Given in initial problem statement) 
Select employees.employee_id ,employees.employee_name,employees.address,employees.contact_info,employees.job_position,sum(sales.quantity*products.price) as total_sales from ((products join sales on products.product_id=sales.product_id) right join employees on employees.employee_id=sales.employee_id) group by employees.employee_id;