CREATE TABLE `g_advertise_solution_history` (
    `id` BIGINT(20) NOT NULL,
    `customer_number` BIGINT(20) NULL DEFAULT NULL,
    `contract_number` VARCHAR(50) NULL DEFAULT NULL,
    `operator` BIGINT(20) NULL DEFAULT NULL,
    `approval_status` INT(11) NULL DEFAULT NULL,
    `contract_type` VARCHAR(64) NULL DEFAULT NULL,
    `contract_status` VARCHAR(64) NULL DEFAULT NULL,
    `type` INT(11) NULL DEFAULT NULL,
    `budget` FLOAT NULL DEFAULT NULL,
    `currency_type` INT(11) NULL DEFAULT NULL,
    `start_time` DATETIME NULL DEFAULT NULL,
    `end_time` DATETIME NULL DEFAULT NULL,
    `locked` INT(11) NULL DEFAULT NULL,
    `task_info` VARCHAR(512) NULL DEFAULT NULL,
    `create_time` DATETIME NULL DEFAULT NULL,
    `create_operator` BIGINT(20) NULL DEFAULT NULL,
    `last_update_time` DATETIME NULL DEFAULT NULL,
    `last_update_operator` BIGINT(20) NULL DEFAULT NULL,
    `number` VARCHAR(20) NULL DEFAULT NULL,
    `old_solution_id` BIGINT(20) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


CREATE TABLE `g_advertise_solution_content_history` (
    `id` BIGINT(20) NOT NULL,
    `advertise_solution_id` BIGINT(20) NULL DEFAULT NULL,
    `advertisers` VARCHAR(128) NULL DEFAULT NULL,
    `description` VARCHAR(512) NULL DEFAULT NULL,
    `approval_status` VARCHAR(64) NULL DEFAULT NULL,
    `site_id` BIGINT(20) NULL DEFAULT NULL,
    `product_id` INT(11) NULL DEFAULT NULL,
    `channel_id` BIGINT(20) NULL DEFAULT NULL,
    `area_id` BIGINT(20) NULL DEFAULT NULL,
    `position_id` BIGINT(20) NULL DEFAULT NULL,
    `position_guide_url` VARCHAR(256) NULL DEFAULT NULL,
    `period_description` VARCHAR(512) NULL DEFAULT NULL,
    `insert_period_description` VARCHAR(512) NULL DEFAULT NULL,
    `total_days` INT(11) NULL DEFAULT NULL,
    `allow_insert` INT(11) NULL DEFAULT NULL,
    `material_embed_code` INT(11) NULL DEFAULT NULL,
    `material_embed_code_content` VARCHAR(512) NULL DEFAULT NULL,
    `material_title` VARCHAR(256) NULL DEFAULT NULL,
    `material_url` VARCHAR(256) NULL DEFAULT NULL,
    `task_info` VARCHAR(512) NULL DEFAULT NULL,
    `create_time` DATETIME NULL DEFAULT NULL,
    `create_operator` BIGINT(20) NULL DEFAULT NULL,
    `last_update_time` DATETIME NULL DEFAULT NULL,
    `last_update_operator` BIGINT(20) NULL DEFAULT NULL,
    `allow_insert_description` VARCHAR(256) NULL DEFAULT NULL,
    `schedule_id` BIGINT(20) NULL DEFAULT NULL,
    `content_type` VARCHAR(64) NULL DEFAULT NULL,
    `old_content_id` BIGINT(20) NULL DEFAULT NULL,
    `number` VARCHAR(20) NULL DEFAULT NULL,
    `submit_time` DATETIME NULL DEFAULT NULL,
    `po_num` VARCHAR(64) NULL DEFAULT NULL,
    `modify_status` INT(11) NULL DEFAULT NULL,
    `approval_date` DATE NULL DEFAULT NULL,
    `advertiser_id` BIGINT(20) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `g_advertise_quotation_history` (
    `id` BIGINT(20) NOT NULL,
    `advertise_solution_content_id` BIGINT(20) NULL DEFAULT NULL,
    `price_type` INT(11) NULL DEFAULT NULL,
    `billing_model_id` BIGINT(20) NULL DEFAULT NULL,
    `currency_type` INT(11) NULL DEFAULT NULL,
    `publish_price` DOUBLE NULL DEFAULT NULL,
    `customer_quote` DOUBLE NULL DEFAULT NULL,
    `traffic_amount` BIGINT(20) NULL DEFAULT NULL,
    `click_amount` BIGINT(20) NULL DEFAULT NULL,
    `discount` DOUBLE NULL DEFAULT NULL,
    `budget` DOUBLE NULL DEFAULT NULL,
    `total_price` DOUBLE NULL DEFAULT NULL,
    `product_ratio_mine` DOUBLE NULL DEFAULT NULL,
    `product_ratio_customer` DOUBLE NULL DEFAULT NULL,
    `product_ratio_third` DOUBLE NULL DEFAULT NULL,
    `industry_type` INT(11) NULL DEFAULT NULL,
    `ratio_mine` DOUBLE NULL DEFAULT NULL,
    `ratio_customer` DOUBLE NULL DEFAULT NULL,
    `ratio_third` DOUBLE NULL DEFAULT NULL,
    `ratio_condition` VARCHAR(128) NULL DEFAULT NULL,
    `ratio_condition_desc` VARCHAR(5000) NULL DEFAULT NULL,
    `reach_estimate` INT(11) NULL DEFAULT NULL,
    `daily_amount` BIGINT(20) NULL DEFAULT NULL,
    `total_amount` BIGINT(20) NULL DEFAULT NULL,
    `create_time` DATETIME NULL DEFAULT NULL,
    `create_operator` BIGINT(20) NULL DEFAULT NULL,
    `last_update_time` DATETIME NULL DEFAULT NULL,
    `last_update_operator` BIGINT(20) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `g_advertise_material_history` (
    `id` BIGINT(20) NOT NULL,
    `advertise_solution_content_id` BIGINT(20) NULL DEFAULT NULL,
    `file_url` VARCHAR(512) NULL DEFAULT NULL,
    `upload_file_name` VARCHAR(128) NULL DEFAULT NULL,
    `download_file_name` VARCHAR(128) NULL DEFAULT NULL,
    `create_time` DATETIME NULL DEFAULT NULL,
    `create_operator` BIGINT(20) NULL DEFAULT NULL,
    `last_update_time` DATETIME NULL DEFAULT NULL,
    `last_update_operator` BIGINT(20) NULL DEFAULT NULL,
    `material_apply_id` BIGINT(20) NULL DEFAULT NULL,
    `material_file_type` TINYINT(2) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `g_adcontent_online_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `advertise_solution_id` bigint(20) NOT NULL,
  `advertise_solution_content_id` bigint(20) NOT NULL,
  `advertise_solution_content_number` varchar(20) DEFAULT NULL,
  `apply_reason` varchar(5120) DEFAULT NULL,
  `task_info` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `create_operator` bigint(20) DEFAULT NULL,
  `update_operator` bigint(20) DEFAULT NULL,
  `approval_status` varchar(64) DEFAULT NULL,
  `contract_number` varchar(50) DEFAULT NULL,
  `contract_type` varchar(50) DEFAULT NULL,
  `contract_state` varchar(50) DEFAULT NULL,
  `contract_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `g_adcontent_apply_approve_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ad_content_apply_id` bigint(20) DEFAULT NULL,
  `actdef_id` varchar(42) DEFAULT NULL,
  `activity_id` varchar(42) DEFAULT NULL,
  `approval_status` char(255) DEFAULT NULL,
  `approval_suggestion` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_operator` bigint(20) DEFAULT NULL,
  `process_id` varchar(42) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `g_attachment_modules` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_record_id` bigint(20) DEFAULT NULL,
  `upload_file_name` varchar(512) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `module_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT INTO `g_process_name_i18n` (`process_define_id`, `process_name`, `locale`) VALUES
('gcrm_pkg_506_prs7', 'Apply Online Approve', 'en_US'),
('gcrm_pkg_506_prs7', '提前上线申请', 'zh_CN');

INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act1', '提交申请单', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act1', 'Submit', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act2', '部门总监审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act2', 'Department Director Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act3', '财务审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act3', 'Finance Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act4', '资源审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs7', 'act4', 'Resource Approve', 'en_US');

INSERT INTO `g_process_activity_type` (process_define_id,type,sub_type,param_key) VALUES ('gcrm_pkg_506_prs7', 'approval', 'onlineApply', 'onlineApplyId');

INSERT INTO g_modify_table_info(table_name, table_field, field_name, local) VALUES('AdvertiseMaterialApply', 'image', '物料详细', 'zh_CN');
INSERT INTO g_modify_table_info(table_name, table_field, field_name, local) VALUES('AdvertiseMaterialApply', 'image', 'material detail file', 'en_US');
INSERT INTO g_modify_table_info(table_name, table_field, field_name, local) VALUES('AdvertiseMaterialApply', 'embed_code', '嵌入代码', 'zh_CN');
INSERT INTO g_modify_table_info(table_name, table_field, field_name, local) VALUES('AdvertiseMaterialApply', 'embed_code', 'code file', 'en_US');

ALTER TABLE g_advertise_material ADD COLUMN pic_width int(6);
ALTER TABLE g_advertise_material ADD COLUMN pic_height int(6);
ALTER TABLE g_advertise_material ADD COLUMN file_size int(6);>>>>>>> .merge-right.r12558
