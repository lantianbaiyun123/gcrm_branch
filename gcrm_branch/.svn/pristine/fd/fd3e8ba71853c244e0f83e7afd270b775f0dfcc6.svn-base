
CREATE TABLE IF NOT EXISTS `g_adcontent_data_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ad_content_id` bigint(20) NOT NULL,
  `platform_id` bigint(20) NOT NULL,
  `site_id` bigint(20) DEFAULT NULL,
  `channel_id` bigint(20) DEFAULT NULL,
  `area_id` bigint(20) DEFAULT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  `rotation_type` int(11) DEFAULT NULL,
  `position_number` varchar(50) DEFAULT NULL,
  `advertisers` varchar(128) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `industry` int(11) DEFAULT NULL,
  `customer_quote` double DEFAULT NULL,
  `ratio_mine` double DEFAULT NULL,
  `billing_model_id` bigint(20) DEFAULT NULL,
  `total_amount` bigint(20) DEFAULT NULL,
  `total_days` int(11) DEFAULT NULL,
  `accumulated_amount` bigint(20) DEFAULT 0,
  `accumulated_days` int(11) DEFAULT 0,
  `impressions` bigint(20) DEFAULT 0,
  `clicks` bigint(20) DEFAULT 0,
  `uv` bigint(20) DEFAULT 0,
  `click_uv` bigint(20) DEFAULT 0,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB;


ALTER TABLE g_advertise_material ADD COLUMN pic_width int(6);
ALTER TABLE g_advertise_material ADD COLUMN pic_height int(6);
ALTER TABLE g_advertise_material ADD COLUMN file_size int(6);

DROP TABLE IF EXISTS `g_notice`;
CREATE TABLE `g_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text,
  `scope` tinyint(4) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `receivers` text DEFAULT NULL,
  `sent_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_operator` bigint(20) NOT NULL,
  `create_operator_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `g_notice_receivers`;
CREATE TABLE `g_notice_receivers` (
  `notice_id` bigint(20) NOT NULL,
  `ucid` bigint(20) NOT NULL,
  `notice_title` varchar(50) DEFAULT NULL,
  `received` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE `g_notice` ADD INDEX `idx_notice_creator_ucid` (`create_operator`);
ALTER TABLE `g_notice_receivers` ADD INDEX `idx_notice_receiver_ucid` (`ucid`);

ALTER TABLE g_modify_record MODIFY old_value varchar(6000);
ALTER TABLE g_modify_record MODIFY new_value varchar(6000);

ALTER TABLE `g_advertise_solution` ADD COLUMN `advertise_type` INT(11) DEFAULT NULL;
update g_advertise_solution set advertise_type=0 where id >= 0;

ALTER TABLE `g_advertise_solution_content` CHANGE COLUMN `material_embed_code_content` `material_embed_code_content` VARCHAR(10000) NULL DEFAULT NULL;
ALTER TABLE `g_advertise_solution_content` ADD COLUMN `material_file_type` INT(11) NULL DEFAULT NULL;
ALTER TABLE `g_advertise_solution_content` ADD COLUMN `monitor_url` VARCHAR(256) NULL DEFAULT NULL;
ALTER TABLE `g_advertise_solution_content` CHANGE COLUMN `material_url` `material_url` VARCHAR(1024) NULL DEFAULT NULL ;
update g_advertise_solution_content gasc set material_file_type=0 where exists (select * from g_advertise_material gam where gam.advertise_solution_content_id=gasc.id) and id>=0;

ALTER TABLE `g_advertise_material_apply` ADD COLUMN `material_file_type` INT(11) NULL DEFAULT NULL;
ALTER TABLE `g_advertise_material_apply` ADD COLUMN `monitor_url` VARCHAR(256) NULL DEFAULT NULL;
ALTER TABLE `g_advertise_material_apply` ADD COLUMN `material_embed_code_content` VARCHAR(10000) NULL DEFAULT NULL;
ALTER TABLE `g_advertise_material_apply` CHANGE COLUMN `material_url` `material_url` VARCHAR(1024) NULL DEFAULT NULL ;
update g_advertise_material_apply gama set material_file_type=0 where exists (select * from g_advertise_material gam where gam.advertise_solution_content_id=gama.advertise_solution_content_id) and id>=0;

/* 初始化g_adcontent_data_statistics数据*/
insert into g_adcontent_data_statistics(ad_content_id,platform_id,site_id,channel_id,area_id,position_id,rotation_type,position_number,advertisers,number,description,industry,
	customer_quote,ratio_mine,billing_model_id,total_amount,total_days) select gasc.id,gasc.product_id,gasc.site_id,gasc.channel_id,gasc.area_id,gasc.position_id,
	gpos.rotation_type,gpos.position_number,gasc.advertisers,gasc.number,gasc.description,gc.industry,
	gaq.customer_quote,gaq.ratio_mine,gaq.billing_model_id,case gaq.billing_model_id when 4 then gaq.daily_amount*gasc.total_days*1000 when 5 then gasc.total_days when 1 then gaq.budget/gaq.customer_quote else null end total_amount,gasc.total_days from g_advertise_solution_content gasc left join g_customer gc on 
	gc.customer_number=gasc.advertiser_id,g_position gpos,g_advertise_quotation 
	gaq,g_publish gp where gpos.id=gasc.position_id and gaq.advertise_solution_content_id=gasc.id and gasc.id=gp.ad_content_id and gaq.billing_model_id is not null and gp.status<=1;

/* 初始化g_publish_date_statistics数据*/
insert into g_publish_date_statistics(ad_content_id, publish_date_id, publish_number) select p.ad_content_id, pd.id, p.number from g_publish p, g_publish_date pd
 	where p.number = pd.p_number and p.status<=1;
 	
ALTER TABLE g_position ADD COLUMN position_code VARCHAR(256);
INSERT INTO `g_modify_table_info` (table_name,table_field,field_name,local) VALUES 
( 'AdvertiseSolution', 'advertiseType', '投放类型',  'zh_CN'),
( 'AdvertiseSolution', 'advertiseType', 'advertise type',  'en_US');
INSERT INTO `g_modify_table_info` (table_name,table_field,field_name,local) VALUES 
( 'AdSolutionContent', 'monitorUrl', '监控地址',  'zh_CN'),
( 'AdSolutionContent', 'monitorUrl', 'monitor url',  'en_US'),
('AdSolutionContent', 'materialFileType', '物料文件类型',  'zh_CN'),
('AdSolutionContent', 'materialFileType', 'material file type', 'en_US');
INSERT INTO `g_modify_table_info` (table_name,table_field,field_name,local) VALUES 
( 'AdvertiseMaterialApply', 'monitorUrl', '监控地址',  'zh_CN'),
( 'AdvertiseMaterialApply', 'monitorUrl', 'monitor url',  'en_US'),
('AdvertiseMaterialApply', 'materialFileType', '物料文件类型',  'zh_CN'),
('AdvertiseMaterialApply', 'materialFileType', 'material file type', 'en_US'),
( 'AdvertiseMaterialApply', 'materialEmbedCodeContent', '代码内容',  'zh_CN'),
( 'AdvertiseMaterialApply', 'materialEmbedCodeContent', 'embed code content',  'en_US');
            