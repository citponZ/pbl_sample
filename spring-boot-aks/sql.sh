#dockerでsqlサーバーを立ち上げる
docker run -e ACCEPT_EULA=Y -e SA_PASSWORD=password123@ -p 1460:1433 --name my_sql_server -d microsoft/mssql-server-linux

#SQLサーバーにログイン
sqlcmd -S 172.20.10.9,1460 -U SA -P password123@ << END_OF_INPUT

create database testdb;
go

use testdb;
go


create table Users(
id varchar(20) primary key,
name varchar(20),
password varchar(20),
userGroup varchar(20)
);
go

create table Words(
id int,
name varchar(50),
userID varchar(50),
content varchar(50),
createDate timestamp,
updatedDate varchar(50)
);
go

insert into Users
values
('administrator','kannrisya','unyobe-ta0731','admin')
;
go
END_OF_INPUT
