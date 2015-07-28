
CREATE TABLE IF NOT EXISTS `g_country` (
  `cid` char(3) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT '1',
  `time_zone` tinyint(4) NOT NULL DEFAULT '1',
  `phone_code` int(11) DEFAULT NULL,
  `opt_date` datetime NOT NULL,
  `opt_user_id` bigint(20) NOT NULL DEFAULT '0',
  `remark` varchar(200) DEFAULT '',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB ;



CREATE TABLE IF NOT EXISTS `g_customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL DEFAULT '',
  `full_name` varchar(50) NOT NULL DEFAULT '',
  `opt_date` datetime NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT '1' COMMENT '用户状态',
  `opt_user_id` bigint(20) NOT NULL DEFAULT '0',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB ;



CREATE TABLE IF NOT EXISTS `g_i18n` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(255) NOT NULL,
  `key_value` varchar(255) NOT NULL,
  `locale` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;



CREATE TABLE IF NOT EXISTS `g_logs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL DEFAULT '',
  `content` varchar(2048) NOT NULL DEFAULT '',
  `ip` varchar(50) NOT NULL DEFAULT '0',
  `operate_time` datetime NOT NULL ,
  PRIMARY KEY (`id`),
  KEY `name` (`user_name`)
) ENGINE=InnoDB ;



CREATE TABLE IF NOT EXISTS `g_offer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `offer_id` varchar(80) NOT NULL DEFAULT '0',
  `customer_id` bigint(20) unsigned NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `country` char(4) NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `position_id` char(50) NOT NULL DEFAULT '',
  `content` varchar(60) DEFAULT '',
  `offer_url` varchar(100) NOT NULL DEFAULT '',
  `opt_date` datetime NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT '1',
  `opt_user_id` bigint(20) NOT NULL DEFAULT '0',
  `remark` varchar(300) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `uid` (`customer_id`),
  KEY `country` (`country`)
) ENGINE=InnoDB ;


CREATE TABLE IF NOT EXISTS `g_position` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `status` varchar(50) NOT NULL DEFAULT '1',
  `opt_user_id` bigint(20) NOT NULL DEFAULT '0',
  `opt_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB ;



CREATE TABLE IF NOT EXISTS `g_subscribe` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(200) NOT NULL DEFAULT '',
  `country` char(4) NOT NULL DEFAULT '',
  `offer_type` varchar(50) NOT NULL DEFAULT '0',
  `sub_type` varchar(50) NOT NULL DEFAULT '0',
  `remark` varchar(2048) DEFAULT '',
  `status` varchar(50) NOT NULL DEFAULT '1',
  `create_date` datetime NOT NULL,
  `create_user_id` bigint(20) NOT NULL,
  `last_update_date` datetime NOT NULL,
  `last_update_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_country` (`email`,`country`)
) ENGINE=InnoDB ;


