create database testdb;
go

use testdb;
go

DROP TABLE IF EXISTS Words;
CREATE TABLE Words (
id int PRIMARY KEY,
name varchar(100) unique not null,
userID int not null,
content text,
createdDate datetime not null,
updatedDate datetime);
go

DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
id int not null IDENTITY(1,1) PRIMARY KEY,
name varchar(100) not null,
password varchar(100) not null,
userGroup varchar(20) not null);
go

DROP TABLE IF EXISTS Tags;
CREATE TABLE Tags (
name varchar(100) not null
,wordID int not null
,PRIMARY KEY(name,wordID));
go

DROP TABLE IF EXISTS Abbreviations ;
CREATE TABLE Abbreviations (
name varchar(100) not null,
wordID int not null,
PRIMARY KEY(name,wordID));
go

DROP TABLE IF EXISTS History ;
CREATE TABLE History (
id int not null IDENTITY(1,1) PRIMARY KEY
,historyType  varchar(100)  not null
,userID int not null
,wordID int not null
,historyDate datetime not null);
go
