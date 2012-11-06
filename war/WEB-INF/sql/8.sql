CREATE TABLE `shop_message` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `replyTo` bigint(10) DEFAULT NULL,
  `user_id` bigint(10) DEFAULT NULL,
  `replied` tinyint(1) DEFAULT NULL,
  `replyBy` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `replyTo` (`id`)
) ;