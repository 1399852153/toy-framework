-- 用户表
DROP TABLE IF EXISTS `user`;
create table user(
	id varchar(32) not null COMMENT '主键 id',

	userName varchar(20) not null COMMENT '姓名',
	age int not null COMMENT '年龄',
    money int not null COMMENT '存款',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- customer 姓名索引
ALTER TABLE user ADD UNIQUE (userName);


-- 书籍表
DROP TABLE IF EXISTS `test_book`;
create table test_book(
	id varchar(32) not null COMMENT '主键 id',

	book_name varchar(20) not null COMMENT '书名',
	author_id varchar(32) not null COMMENT '作者id',
    price int not null COMMENT '价格',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 帐号表
DROP TABLE IF EXISTS `test_account`;
create table test_account(
	id varchar(32) not null COMMENT '主键 id',

	user_id varchar(20) not null COMMENT '用户id',
    balance int not null COMMENT '余额',
	primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



DROP TABLE IF EXISTS `result_map_test`;
create table result_map_test(
	id varchar(32) not null COMMENT '主键 id',

	userName varchar(20) not null COMMENT '姓名',
	age int not null COMMENT '年龄',
    money int not null COMMENT '存款',
    bookId varchar(32) not null COMMENT 'bookId',
    book_name varchar(20) not null COMMENT '书名',
	author_id varchar(32) not null COMMENT '作者id',
    price int not null COMMENT '价格'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


