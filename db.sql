create table keys_tb(
    id int not null auto_increment primary key,
    block varchar(100) not null,
    room varchar(100) not null,
    number int not null,
    dateCreated datetime default current_timestamp
);

create table booking(
    id int not null auto_increment primary key,
    key_id int not null,
    student_index varchar(20) not null ,
    kay_name varchar(50) not null ,
    status tinyint not null default 1,
    booking_date datetime default current_timestamp,
    foreign key (key_id) references keys_tb(id) on DELETE cascade on update cascade
);