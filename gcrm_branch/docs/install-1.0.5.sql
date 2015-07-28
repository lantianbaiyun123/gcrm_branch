CREATE TABLE IF NOT EXISTS `g_advertise_material_menu` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `material_apply_id` BIGINT(20) NOT NULL,
  `material_title` VARCHAR(256) DEFAULT NULL,
  `material_url` VARCHAR(1024) DEFAULT NULL,
  `file_url` VARCHAR(512) DEFAULT NULL,
  `upload_file_name` VARCHAR(128) DEFAULT NULL,
  `download_file_name` VARCHAR(128) DEFAULT NULL,
  `pic_width` INT(6) DEFAULT NULL,
  `pic_height` INT(6) DEFAULT NULL,
  `file_size` INT(6) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `create_operator` BIGINT(20) DEFAULT NULL,
  `last_update_time` DATETIME DEFAULT NULL,
  `last_update_operator` BIGINT(20) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=INNODB;


ALTER TABLE `g_advertise_material_apply` ADD COLUMN `period_description` VARCHAR(512) DEFAULT NULL;
ALTER TABLE `g_position` ADD COLUMN `rotation_order` INT(11) NULL DEFAULT null AFTER `rotation_type`;
update g_position set rotation_order=1 where rotation_type=1 and id>0;
