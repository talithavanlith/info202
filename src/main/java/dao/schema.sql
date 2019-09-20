/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  vanta342
 * Created: 6/08/2018
 */
drop table Product;

create table Product (

    Product_ID integer,
    Name varchar(50) not null,
    Description varchar(200),
    Category varchar(20) not null,
    ListPrice decimal(10, 2) not null,
    QuantityInStock decimal not null,

    constraint Product_PK primary key (Product_ID)

);

drop table Customer;

create table Customer (

    Person_ID integer AUTO_INCREMENT,
    Firstname varchar(50) not null,
    Username varchar(20) not null,
    Surname varchar(50) not null,
    Password varchar(50) not null,
    Email varchar(100) not null,
    ShippingAddress varchar(200) not null,
    CreditCardDetails varchar(100) not null,

    unique(username),
    constraint Person_PK primary key (Person_ID)

);

merge into Customer (Person_ID, Username, Firstname, Surname, Password, Email, ShippingAddress, CreditCardDetails) values (2,'talitha','Talitha','van Lith','hello','talitha@gmail.com','20 Grey St','2898918819919')

drop table Sale;

create table Sale (

    Person_ID Integer not null,
    Sale_ID Integer AUTO_INCREMENT (1000),
    Date timestamp not null,
    Status varchar(100),

    constraint Sale_Customer foreign key (Person_ID) references Customer,
    constraint Sale_PK primary key (Sale_ID)

);

drop table SaleItem;

create table SaleItem (

    Sale_ID Integer not null,
    Product_ID Integer not null,
    QuantityPurchased decimal not null,
    SalePrice decimal not null,

    constraint SaleItem_Sale foreign key (Sale_ID) references Sale,
    constraint SaleItem_Product foreign key (Product_ID) references Product,
    constraint SaleItem_PK primary key (Sale_ID, Product_ID)

);