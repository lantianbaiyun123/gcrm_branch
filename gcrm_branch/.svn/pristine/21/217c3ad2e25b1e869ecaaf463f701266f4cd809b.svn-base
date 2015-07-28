CREATE TABLE `g_stock` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`position_date_id` BIGINT NOT NULL,
	`total_stock` BIGINT,
	`occupied_stock` BIGINT,
	`real_occupied_stock` BIGINT,	
	`billing_model_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `unidx_posi_model_id` (`position_date_id`,`billing_model_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `g_position_date` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`position_id` BIGINT NOT NULL,
	`date` DATE NOT NULL,
	`status` INT NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY unique_key (position_id, date)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `g_schedules` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`number` VARCHAR(20) NOT NULL,
	`ad_content_id` BIGINT NOT NULL,
	`billing_model_id` BIGINT,
	`position_id` BIGINT NOT NULL,
	`period_description` VARCHAR(2000),
	`status` INT NOT NULL,
	`completed` INT,
	`create_time` DATETIME,
	`lock_time` DATETIME,
	`release_time` DATETIME,
	`create_operator` BIGINT,
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `g_schedules` (number, ad_content_id, position_id, period_description, status, completed, 
billing_model_id, create_time, lock_time, release_time, create_operator)
select number, ad_content_id, position_id, period_description, if(status = 0, status, status - 1), completed,  
billing_model_id, create_time, lock_time, release_time, create_operator from `g_schedule`;

update g_advertise_solution set approval_status=3 where approval_status in(4,5) and id>0;
update g_advertise_solution set task_info=null where approval_status=3 and id>0;
update g_advertise_solution_content set approval_status='approved' where approval_status in('unconfirmed','confirmed') and id>0;
update g_advertise_solution_content set task_info=null where approval_status='approved' and id>0;
/*执行内容状态迁移  /adsolution/content/initStatus*/
update g_advertise_solution set approval_status=5 where approval_status=6 and id>0;