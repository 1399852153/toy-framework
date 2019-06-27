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

