ALTER TABLE g_publish_record ADD COLUMN plan_date date;

alter table g_ad_platform_site_relation add index idx_plat_site(ad_platform_id, site_id);
alter table g_position add index idx_plat_site(ad_platform_id, site_id);
alter table g_position add index idx_index_str(index_str);
alter table g_position add UNIQUE INDEX unique_number(position_number);

INSERT INTO `g_modify_table_info` (table_name,table_field,field_name,local) VALUES 
( 'Customer', 'attachment', '资质文件',  'zh_CN'),
( 'Customer', 'attachment', 'attachment',  'en_US'),
('Attachment', 'type', '资质文件类型',  'zh_CN'),
('Attachment', 'type', 'AttachmentType', 'en_US');

INSERT into g_agent_regional(name) VALUES ('港澳台新');
INSERT into g_agent_regional VALUES (99,'全球');

INSERT INTO g_i18n (key_name,key_value,locale) VALUES 
('g_agent_regional.7','港澳台新','zh_CN'),('g_agent_regional.99','全球','zh_CN'),
('g_agent_regional.7','HK, MAC,TWN  and SG','en_US'),('g_agent_regional.99','Global','en_US');

INSERT INTO g_country VALUES (999,'全球','WW');

INSERT INTO g_i18n (key_name,key_value,locale) VALUES
('g_country.999','全球','zh_CN'),('g_country.999','Global','en_US');

INSERT into  g_agenct_country(regional_id,country_id) values (99,999);

INSERT into  g_agenct_country(regional_id,country_id) values (7,198),(7,95),(7,148);

update g_agenct_country set regional_id =7 where id =74;

ALTER TABLE g_advertise_quotation MODIFY ratio_condition_desc VARCHAR(5000);

