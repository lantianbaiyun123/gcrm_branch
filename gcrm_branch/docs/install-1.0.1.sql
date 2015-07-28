delete  from g_process_name_i18n;
INSERT INTO `g_process_name_i18n` (`process_define_id`, `process_name`, `locale`) VALUES
('gcrm_pkg_506_prs1', 'Advertise Solution Approve', 'en_US'),
('gcrm_pkg_506_prs1', '方案审核', 'zh_CN'),
('gcrm_pkg_506_prs2', 'Bidding', 'en_US'),
('gcrm_pkg_506_prs2', '竞价排期', 'zh_CN'),
('gcrm_pkg_506_prs3', 'Quote Approve', 'en_US'),
('gcrm_pkg_506_prs3', '标杆价审核', 'zh_CN'),
('gcrm_pkg_506_prs4', 'Material Approve', 'en_US'),
('gcrm_pkg_506_prs4', '物料审核', 'zh_CN'),
('gcrm_pkg_506_prs6', 'Customer Approve', 'en_US'),
('gcrm_pkg_506_prs6', '客户审核', 'zh_CN');


delete from g_activity_name_i18n where process_define_id='gcrm_pkg_506_prs1';
INSERT INTO `g_activity_name_i18n` (`process_define_id`, `activity_id`, `activity_name`, `locale`) VALUES
('gcrm_pkg_506_prs1', 'act1', 'Submit', 'en_US'),
('gcrm_pkg_506_prs1', 'act1', '提交方案', 'zh_CN'),
('gcrm_pkg_506_prs1', 'act2', 'PM Approve', 'en_US'),
('gcrm_pkg_506_prs1', 'act2', 'PM审核', 'zh_CN'), 
('gcrm_pkg_506_prs1', 'act3', 'Monetization  Supervisor Approve', 'en_US '),
('gcrm_pkg_506_prs1', 'act3', '变现主管审核', 'zh_CN'),
('gcrm_pkg_506_prs1', 'act5', 'Department director  Approve', 'en_US'),
('gcrm_pkg_506_prs1', 'act5', '部门总监审核', 'zh_CN'), 
('gcrm_pkg_506_prs1', 'act7', 'Legal Approve', 'en_US'),
('gcrm_pkg_506_prs1', 'act7', '法务审核', 'zh_CN'), 
('gcrm_pkg_506_prs1', 'act9', 'GBU CFO Approve', 'en_US'),
('gcrm_pkg_506_prs1', 'act9', '国际化CFO审核', 'zh_CN'),
('gcrm_pkg_506_prs1', 'act13', 'GBU CFO Approve', 'en_US'),
('gcrm_pkg_506_prs1', 'act13', '国际化CFO审核', 'zh_CN'),
('gcrm_pkg_506_prs1', 'act17', 'Superior Approve', 'en_US'),
('gcrm_pkg_506_prs1', 'act17', '上级审核', 'zh_CN');


delete  from g_process_activity_type where process_define_id='gcrm_pkg_506_prs6';
INSERT INTO `g_process_activity_type` (process_define_id,type,sub_type,param_key) VALUES ('gcrm_pkg_506_prs6', 'approval', 'customer', 'customerId');


delete  from g_activity_name_i18n where process_define_id='gcrm_pkg_506_prs6';
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act1', '提交客户', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act1', 'Submit', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale)  VALUES ('gcrm_pkg_506_prs6', 'act2', '直属上级审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act2', 'Superior Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act3', '部门总监审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act3', 'Department director  Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act4', '国际化执行总监审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs6', 'act4', 'Executive director  Approve', 'en_US');

delete  from g_activity_name_i18n where process_define_id='gcrm_pkg_506_prs4';
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs4', 'act1', '提交物料', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs4', 'act1', 'Submit', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs4', 'act2', 'PM 审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs4', 'act2', 'PM Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs4', 'act3', '直属上级审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs4', 'act3', 'Superior Approve', 'en_US');


delete  from g_activity_name_i18n where process_define_id='gcrm_pkg_506_prs3';
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act1', '提交标杆价', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act1', 'Submit', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act2', '变现主管审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act2', 'Monetization  Supervisor Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act3', '销售主管审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act3', 'Sales Supervisor  Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act4', '部门总监审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act4', 'Department director  Approve', 'en_US');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act5', '国际化CFO审核', 'zh_CN');
INSERT INTO `g_activity_name_i18n` (process_define_id,activity_id,activity_name,locale) VALUES ('gcrm_pkg_506_prs3', 'act5', 'GBU CFO Approve', 'en_US');


INSERT INTO g_code(code_type,code_order,code_value,code_desc) VALUES ('quotationMain.industry',3,'3','行业类型 电商');

ALTER TABLE g_code ADD COLUMN code_parent VARCHAR(32);
update g_code set code_parent='3' where code_type='quotationMain.industry' and code_value='0';
update g_code set code_parent='3' where code_type='quotationMain.industry' and code_value='2';


INSERT INTO g_i18n (key_name,key_value,locale) VALUES('quotationMain.industry.3','电商类','zh_CN');
INSERT INTO g_i18n (key_name,key_value,locale) VALUES('quotationMain.industry.3','Ecom','en_US');

UPDATE g_i18n SET key_value='IT' WHERE key_name='quotationMain.industry.0' AND locale='zh_CN';
UPDATE g_i18n SET key_value='IT' WHERE key_name='quotationMain.industry.0' AND locale='en_US';
UPDATE g_i18n SET key_value='其他' WHERE key_name='quotationMain.industry.2' AND locale='zh_CN';
UPDATE g_i18n SET key_value='Other' WHERE key_name='quotationMain.industry.2' AND locale='en_US';