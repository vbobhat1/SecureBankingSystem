create table customer(cardno integer,pass varchar(300));
create table accts(cardno varchar(8),acctype varchar(2),curbal double);
create table trans(cardno varchar(8),acctype varchar(2),amt double,transdate varchar(12));