CREATE TABLE `g_adcontent_data_sample` (
    `id` int(10) NOT NULL  AUTO_INCREMENT,
    `ad_content_id` int(10) NOT NULL,
    `date` VARCHAR(16) NOT NULL,
    `impressions` int(10) NOT NULL comment '展现量',
    `clicks` int(10) NOT NULL comment '点击量',
    `uv` int(10) NOT NULL comment '独立IP展现量',
    `click_uv` int(10) NOT NULL comment '独立IP点击量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `g_position_data_sample` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `position_number` varchar(50) NOT NULL,
    `date` VARCHAR(16) NOT NULL,
    `impressions` int(10) NOT NULL comment '展现量',
    `clicks` int(10) NOT NULL comment '点击量',
    `uv` int(10) NOT NULL comment '独立IP展现量',
    `click_uv` int(10) NOT NULL comment '独立IP点击量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `g_publish_date_statistics` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `publish_number` varchar(20) NOT NULL,
    `ad_content_id` int(10) NOT NULL,
    `publish_date_id` int(10) NOT NULL,
    `impressions` int(10) NOT NULL DEFAULT '0' comment '展现量',
    `clicks` int(10) NOT NULL DEFAULT '0' comment '点击量',
    `uv` int(10) NOT NULL DEFAULT '0' comment '独立IP展现量',
    `click_uv` int(10) NOT NULL DEFAULT '0' comment '独立IP点击量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `g_file_read_record` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `filename` varchar(64) NOT NULL,
    `read_time` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table g_site add column time_zone_offset varchar(10);
update g_site set time_zone_offset='-4' where id=10;
update g_site set time_zone_offset='-6' where id=11;
update g_site set time_zone_offset='-11' where id=12;
update g_site set time_zone_offset='-8' where id=13;
update g_site set time_zone_offset='+1' where id=14;
update g_site set time_zone_offset='-5' where id=15;
update g_site set time_zone_offset='-1' where id=16;
update g_site set time_zone_offset='0' where id=17;
update g_site set time_zone_offset='-1' where id=18;
update g_site set time_zone_offset='-1' where id=19;