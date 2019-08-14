/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : toy_framework

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2019-08-14 21:10:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_account
-- ----------------------------
DROP TABLE IF EXISTS `test_account`;
CREATE TABLE `test_account` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键 id' ,
`user_id`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id' ,
`password`  int(11) NOT NULL COMMENT '余额' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Records of test_account
-- ----------------------------
BEGIN;
INSERT INTO `test_account` VALUES ('12312', 'aaaaa', '123456'), ('321312', 'bbbbb', '123456');
COMMIT;

-- ----------------------------
-- Table structure for test_book
-- ----------------------------
DROP TABLE IF EXISTS `test_book`;
CREATE TABLE `test_book` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键 id' ,
`book_name`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书名' ,
`author_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作者id' ,
`price`  int(11) NOT NULL COMMENT '价格' ,
`library_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属图书馆id' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Records of test_book
-- ----------------------------
BEGIN;
INSERT INTO `test_book` VALUES ('111111', 'aaaaa书籍1', 'aaaaa', '100', 'library1'), ('222222', 'aaaaa书籍2', 'aaaaa', '200', 'library2'), ('333333', 'bbbbb书籍1', 'bbbbb', '100', 'library1'), ('555555', 'bbbbb书籍2', 'bbbbb', '200', 'library2');
COMMIT;

-- ----------------------------
-- Table structure for test_library
-- ----------------------------
DROP TABLE IF EXISTS `test_library`;
CREATE TABLE `test_library` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键 id' ,
`address`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Records of test_library
-- ----------------------------
BEGIN;
INSERT INTO `test_library` VALUES ('library1', '一栋一号'), ('library2', '二栋一号');
COMMIT;

-- ----------------------------
-- Table structure for test_user
-- ----------------------------
DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user` (
`id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键 id' ,
`userName`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名' ,
`age`  int(11) NOT NULL COMMENT '年龄' ,
`money`  int(11) NOT NULL COMMENT '存款' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci

;

-- ----------------------------
-- Records of test_user
-- ----------------------------
BEGIN;
INSERT INTO `test_user` VALUES ('aaaaa', '打扫打扫打扫', '12', '32131'), ('bbbbb', '打扫打扫大', '13', '32131');
COMMIT;
