DROP SCHEMA IF EXISTS `test`;
CREATE SCHEMA `test`;
DROP TABLE IF EXISTS `test`.`t_demo`;
CREATE TABLE `test`.`t_demo` (
  `id` BIGINT (20) NOT NULL DEFAULT '0' COMMENT '主键',
  `demo_key`  VARCHAR(255)      NOT NULL DEFAULT '0' COMMENT '',
  `demo_value`  VARCHAR (255) NOT NULL DEFAULT '0' COMMENT '',
  PRIMARY KEY (`id`)
);