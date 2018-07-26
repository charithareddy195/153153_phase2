
create table customer(
mobileNo varchar2(10) ,
name varchar2(30),
balance number(10,2),
primary key(mobileNo)
);

create table Transactions(
mobileNo varchar2(10),
transactionType varchar2(30),
amount number(10,2),
transactionDate varchar2(30),
transactionStatus varchar2(15),
foreign key(mobileNo) references customer(mobileNo));