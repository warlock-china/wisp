DROP SCHEMA IF EXISTS `test`;
CREATE SCHEMA `test`;
DROP TABLE IF EXISTS `test`.`user`;
CREATE TABLE `test`.`user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255)  NOT NULL DEFAULT '',
  `phone` varchar(11)  NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
);

DROP SCHEMA IF EXISTS `test2`;
CREATE SCHEMA `test2`;
DROP TABLE IF EXISTS `test2``store`;
CREATE TABLE `test2`.`store` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `products` int(11) NOT NULL DEFAULT '0',
  `nv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);