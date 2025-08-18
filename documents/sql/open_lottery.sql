/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : open_lottery

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2025-08-18 10:10:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for arch_biz_log
-- ----------------------------
DROP TABLE IF EXISTS `arch_biz_log`;
CREATE TABLE `arch_biz_log` (
  `id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `app` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `tenant` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '租户标识',
  `biz_group` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '业务分组标识',
  `biz_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '业务编号',
  `operator_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '操作员编号',
  `operator` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '操作员名称',
  `op_action` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '操作动作',
  `fail` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '成功或失败标识：0-成功,1-失败',
  `detail` tinytext COLLATE utf8mb4_bin NOT NULL COMMENT '详细内容',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='业务日志记录表';

-- ----------------------------
-- Table structure for arch_event
-- ----------------------------
DROP TABLE IF EXISTS `arch_event`;
CREATE TABLE `arch_event` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '事件名称',
  `filter` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '事件过滤标识',
  `delay` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '时间延迟时间',
  `event` text COLLATE utf8mb4_bin NOT NULL COMMENT '事件内容',
  `shard_key` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '分库分表情况下,分库分表key',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '事件状态:0-初始,1-投递成功,2-投递失败',
  `version` tinyint(5) unsigned NOT NULL DEFAULT '1' COMMENT '版本标识',
  `gmt_create` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `publish_time` bigint(20) unsigned DEFAULT '0' COMMENT '投递时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='事件发布记录';

-- ----------------------------
-- Table structure for arch_event_compen
-- ----------------------------
DROP TABLE IF EXISTS `arch_event_compen`;
CREATE TABLE `arch_event_compen` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `event_id` bigint(20) unsigned NOT NULL COMMENT '事件标识',
  `shard_key` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '分片键：默认为空',
  `start_time` bigint(20) NOT NULL COMMENT '补偿开始时间',
  `taken` bigint(20) unsigned NOT NULL COMMENT '耗时时间',
  `fail_msg` tinytext COLLATE utf8mb4_bin NOT NULL COMMENT '错误日志',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='补偿事件记录';

-- ----------------------------
-- Table structure for arch_event_idempot
-- ----------------------------
DROP TABLE IF EXISTS `arch_event_idempot`;
CREATE TABLE `arch_event_idempot` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '事件名称',
  `filter` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '事件过滤标识',
  `event_key` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '事件标识',
  `shard_key` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '分库分表情况下，分库分表key',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_idempotent_idx` (`event_key`,`name`,`filter`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='事件消费幂等表';

-- ----------------------------
-- Table structure for arch_idempotent
-- ----------------------------
DROP TABLE IF EXISTS `arch_idempotent`;
CREATE TABLE `arch_idempotent` (
  `lock_key` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '幂等key',
  `sharding` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '分片键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`lock_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for arch_mutex
-- ----------------------------
DROP TABLE IF EXISTS `arch_mutex`;
CREATE TABLE `arch_mutex` (
  `mutex` varchar(66) COLLATE utf8mb4_bin NOT NULL COMMENT '互斥资源标识',
  `acquired_at` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '获取锁时间',
  `ttl_at` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '锁过期时间',
  `transition_at` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '锁过渡期时间',
  `owner_id` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '锁持有者标识',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`mutex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='互斥锁记录表';

-- ----------------------------
-- Table structure for arch_tx_log
-- ----------------------------
DROP TABLE IF EXISTS `arch_tx_log`;
CREATE TABLE `arch_tx_log` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `async_key` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '异步任务标识',
  `shard_key` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '分片键',
  `data` mediumtext COLLATE utf8mb4_bin NOT NULL COMMENT '任务内容',
  `version` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '事务日志版本',
  `state` tinyint(3) unsigned NOT NULL COMMENT '任务状态:1-ready,2-running,3-success,4-fail,5-dead',
  `max_retry` int(11) unsigned NOT NULL COMMENT '最大重试次数',
  `retry_interval` bigint(20) unsigned NOT NULL COMMENT '重试间隔时间基数(单位:秒)',
  `retries` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '当前重试次数',
  `next_time` datetime DEFAULT NULL COMMENT '下一次重试时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='异步事物日志表';

-- ----------------------------
-- Table structure for avatar_info
-- ----------------------------
DROP TABLE IF EXISTS `avatar_info`;
CREATE TABLE `avatar_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'avatar key',
  `uri` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'avatar uri',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_avatar_idx` (`uri`)
) ENGINE=InnoDB AUTO_INCREMENT=7219 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='头像信息';

-- ----------------------------
-- Table structure for dlt_census
-- ----------------------------
DROP TABLE IF EXISTS `dlt_census`;
CREATE TABLE `dlt_census` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` int(10) NOT NULL COMMENT '统计类型',
  `level` int(10) unsigned NOT NULL COMMENT '统计级别',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '统计数据来源',
  `census` json NOT NULL COMMENT '统计字符串',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_census_idx` (`period`,`type`,`level`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=609 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for dlt_early_warning
-- ----------------------------
DROP TABLE IF EXISTS `dlt_early_warning`;
CREATE TABLE `dlt_early_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预警时间',
  `type` tinyint(4) unsigned NOT NULL COMMENT '预警类型',
  `warn` json NOT NULL COMMENT '预警值',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_warn_idx` (`period`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='大乐透号码预警';

-- ----------------------------
-- Table structure for dlt_home_master
-- ----------------------------
DROP TABLE IF EXISTS `dlt_home_master`;
CREATE TABLE `dlt_home_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据指标字段',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hit_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '命中率字符串',
  `hit_rate` double unsigned NOT NULL COMMENT '命中率',
  `hit_series` int(10) unsigned NOT NULL COMMENT '连续命中次数',
  `rank` int(10) unsigned NOT NULL COMMENT '排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_home_master_idx` (`period`,`type`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for dlt_icai
-- ----------------------------
DROP TABLE IF EXISTS `dlt_icai`;
CREATE TABLE `dlt_icai` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '当前期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家id',
  `r1` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '红球独胆',
  `hit_r1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '命中数据',
  `r1_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '是否命中',
  `r2` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '红球双胆',
  `hit_r2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球双胆命中数据',
  `r2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '双胆是否命中',
  `r3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球三胆数据',
  `hit_r3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆命中数据',
  `r3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '三胆是否命中',
  `r10` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球10码数据',
  `hit_r10` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球10码命中数据',
  `r10_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '红球10码是否命中',
  `r20` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `hit_r20` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球20码命中数据',
  `r20_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '红球20码是否命中',
  `b1` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球独胆',
  `hit_b1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球独胆命中数据',
  `b1_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '篮球独胆是否命中',
  `b2` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球双胆数据',
  `hit_b2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球双胆命中数据',
  `b2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '篮球双胆是否命中',
  `b6` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球定6数据',
  `hit_b6` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球定6命中数据',
  `b6_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '篮球定6是否命中',
  `rk3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀3码',
  `hit_rk3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀三码命中数据',
  `rk3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '红球杀三码是否命中',
  `rk6` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀六码数据',
  `hit_rk6` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀六码命中数据',
  `rk6_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '红球杀六码是否命中',
  `bk` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球杀三码',
  `hit_bk` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀三码命中数据',
  `bk_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '篮球杀三码是否命中',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_icai_idx` (`master_id`,`period`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=117988 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='大乐透预测数据';

-- ----------------------------
-- Table structure for dlt_master_rank
-- ----------------------------
DROP TABLE IF EXISTS `dlt_master_rank`;
CREATE TABLE `dlt_master_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hot` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '热门专家:0-否,1-是',
  `is_vip` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为vip专家:0-否,1-是',
  `weight` double(10,3) unsigned NOT NULL COMMENT '综合权重',
  `rank` int(10) unsigned NOT NULL COMMENT '综合排序权重',
  `r1_wgt` double(10,3) unsigned NOT NULL COMMENT '独胆权重',
  `r1_rank` int(10) unsigned NOT NULL COMMENT '红球独胆排序',
  `r2_wgt` double(10,3) unsigned NOT NULL COMMENT '双胆权重',
  `r2_rank` int(10) unsigned NOT NULL COMMENT '红球双胆排序',
  `r3_wgt` double(10,3) unsigned NOT NULL COMMENT '三胆权重',
  `r3_rank` int(10) unsigned NOT NULL COMMENT '红球三码排名',
  `r10_wgt` double(10,3) unsigned NOT NULL COMMENT '10码权重',
  `r10_rank` int(10) unsigned NOT NULL COMMENT '红球10码权重',
  `r20_wgt` double(10,3) unsigned NOT NULL COMMENT '20码权重',
  `r20_rank` int(10) unsigned NOT NULL COMMENT '红球20码排名',
  `rk3_wgt` double(10,3) unsigned NOT NULL COMMENT '杀三码权重',
  `rk3_rank` int(10) unsigned NOT NULL COMMENT '红球杀三码排名',
  `rk6_wgt` double(10,3) unsigned NOT NULL COMMENT '杀六码权重',
  `rk6_rank` int(10) unsigned NOT NULL COMMENT '红球杀六码排名',
  `b1_wgt` double(10,3) unsigned NOT NULL COMMENT '蓝球独胆权重',
  `b1_rank` int(10) unsigned NOT NULL COMMENT '蓝球独胆排名',
  `b2_wgt` double(10,3) unsigned NOT NULL COMMENT '蓝球双胆权重',
  `b2_rank` int(10) unsigned NOT NULL COMMENT '蓝球双胆排名',
  `b6_wgt` double(10,3) unsigned NOT NULL COMMENT '蓝球6码权重',
  `b6_rank` int(10) unsigned NOT NULL COMMENT '蓝球六码排名',
  `bk_wgt` double(10,3) unsigned NOT NULL COMMENT '蓝球杀码权重',
  `bk_rank` int(10) unsigned NOT NULL COMMENT '篮球杀码排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_master_rank_idx` (`period`,`master_id`),
  KEY `dlt_master_rank_idx` (`period`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=117988 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for dlt_master_rate
-- ----------------------------
DROP TABLE IF EXISTS `dlt_master_rate`;
CREATE TABLE `dlt_master_rate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `r1_ne_hit` int(10) unsigned NOT NULL COMMENT '独胆最近7期命中次数',
  `r1_ne_rate` double unsigned NOT NULL COMMENT '独胆最近7期命中率',
  `r1_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近7期字符串形式命中次数',
  `r1_me_hit` int(10) unsigned NOT NULL COMMENT '独胆最近15期命中次数',
  `r1_me_rate` double unsigned NOT NULL COMMENT '独胆最近15期命中率',
  `r1_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近15期字符串形式命中次数',
  `r1_hi_hit` int(10) unsigned NOT NULL COMMENT '独胆最近30期命中次数',
  `r1_hi_rate` double unsigned NOT NULL COMMENT '独胆最近30期命中率',
  `r1_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近30期字符串形式命中次数',
  `r2_ne_hit` int(10) unsigned NOT NULL COMMENT '双胆最近7期命中次数',
  `r2_ne_rate` double unsigned NOT NULL COMMENT '双胆最近7期命中率',
  `r2_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近7期字符串形式命中次数',
  `r2_me_hit` int(10) unsigned NOT NULL COMMENT '双胆最近15期命中次数',
  `r2_me_rate` double unsigned NOT NULL COMMENT '双胆最近15期命中率',
  `r2_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近15期字符串形式命中次数',
  `r2_hi_hit` int(10) unsigned NOT NULL COMMENT '双胆最近30期命中次数',
  `r2_hi_rate` double unsigned NOT NULL COMMENT '双胆最近30期命中率',
  `r2_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近30期字符串形式命中次数',
  `r3_ne_hit` int(10) unsigned NOT NULL COMMENT '三胆最近7期命中次数',
  `r3_ne_rate` double unsigned NOT NULL COMMENT '三胆最近7期命中率',
  `r3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近7期字符串形式命中次数',
  `r3_me_hit` int(10) unsigned NOT NULL COMMENT '三胆最近15期命中次数',
  `r3_me_rate` double unsigned NOT NULL COMMENT '三胆最近15期命中率',
  `r3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近15期字符串形式命中次数',
  `r3_hi_hit` int(10) unsigned NOT NULL COMMENT '三胆最近30期命中次数',
  `r3_hi_rate` double unsigned NOT NULL COMMENT '三胆最近30期命中率',
  `r3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近30期字符串形式命中次数',
  `r10_ne_hit` int(10) unsigned NOT NULL COMMENT '10码最近7期命中次数',
  `r10_ne_rate` double unsigned NOT NULL COMMENT '10码最近7期命中率',
  `r10_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '10码最近7期字符串形式命中次数',
  `r10_me_hit` int(10) unsigned NOT NULL COMMENT '10码最近15期命中次数',
  `r10_me_rate` double unsigned NOT NULL COMMENT '10码最近15期命中率',
  `r10_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '10码最近15期字符串形式命中次数',
  `r10_hi_hit` int(10) unsigned NOT NULL COMMENT '10码最近30期命中次数',
  `r10_hi_rate` double unsigned NOT NULL COMMENT '10码最近30期命中率',
  `r10_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '10码最近30期字符串形式命中次数',
  `r20_ne_hit` int(10) unsigned NOT NULL COMMENT '20码最近7期命中次数',
  `r20_ne_rate` double unsigned NOT NULL COMMENT '20码最近7期命中率',
  `r20_ne_full_rate` double unsigned NOT NULL COMMENT '20码全中最近7期命中率',
  `r20_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近7期字符串形式命中次数',
  `r20_me_hit` int(10) unsigned NOT NULL COMMENT '20码最近15期命中次数',
  `r20_me_rate` double unsigned NOT NULL COMMENT '20码最近15期命中率',
  `r20_me_full_rate` double unsigned NOT NULL COMMENT '20码全中最近15期命中率',
  `r20_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近15期字符串形式命中次数',
  `r20_hi_hit` int(10) unsigned NOT NULL COMMENT '20码最近30期命中次数',
  `r20_hi_rate` double unsigned NOT NULL COMMENT '20码最近30期命中率',
  `r20_hi_full_rate` double unsigned NOT NULL COMMENT '20码全中最近30期命中率',
  `r20_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近30期字符串形式命中次数',
  `rk3_ne_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近7期命中次数',
  `rk3_ne_rate` double unsigned NOT NULL COMMENT '杀3码最近7期命中率',
  `rk3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近7期字符串形式命中次数',
  `rk3_me_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近15期命中次数',
  `rk3_me_rate` double unsigned NOT NULL COMMENT '杀3码最近15期命中率',
  `rk3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近15期字符串形式命中次数',
  `rk3_hi_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近30期命中次数',
  `rk3_hi_rate` double unsigned NOT NULL COMMENT '杀3码最近30期命中率',
  `rk3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近30期字符串形式命中次数',
  `rk6_ne_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近7期命中次数',
  `rk6_ne_rate` double unsigned NOT NULL COMMENT '杀3码最近7期命中率',
  `rk6_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近7期字符串形式命中次数',
  `rk6_me_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近15期命中次数',
  `rk6_me_rate` double unsigned NOT NULL COMMENT '杀3码最近15期命中率',
  `rk6_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近15期字符串形式命中次数',
  `rk6_hi_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近30期命中次数',
  `rk6_hi_rate` double unsigned NOT NULL COMMENT '杀3码最近30期命中率',
  `rk6_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近30期字符串形式命中次数',
  `b1_ne_hit` int(10) unsigned NOT NULL COMMENT '蓝1码最近7期命中次数',
  `b1_ne_rate` double unsigned NOT NULL COMMENT '蓝1码最近7期命中率',
  `b1_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝1码最近7期字符串形式命中次数',
  `b1_me_hit` int(10) unsigned NOT NULL COMMENT '蓝1码最近15期命中次数',
  `b1_me_rate` double unsigned NOT NULL COMMENT '蓝1码最近15期命中率',
  `b1_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝1码最近15期字符串形式命中次数',
  `b1_hi_hit` int(10) unsigned NOT NULL COMMENT '蓝1码最近30期命中次数',
  `b1_hi_rate` double unsigned NOT NULL COMMENT '蓝1码最近30期命中率',
  `b1_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝1码最近30期字符串形式命中次数',
  `b2_ne_hit` int(10) unsigned NOT NULL COMMENT '蓝2码最近7期命中次数',
  `b2_ne_rate` double unsigned NOT NULL COMMENT '蓝2码最近7期命中率',
  `b2_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝2码最近7期字符串形式命中次数',
  `b2_me_hit` int(10) unsigned NOT NULL COMMENT '蓝2码最近15期命中次数',
  `b2_me_rate` double unsigned NOT NULL COMMENT '蓝2码最近15期命中率',
  `b2_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝2码最近15期字符串形式命中次数',
  `b2_hi_hit` int(10) unsigned NOT NULL COMMENT '蓝2码最近30期命中次数',
  `b2_hi_rate` double unsigned NOT NULL COMMENT '蓝2码最近30期命中率',
  `b2_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝2码最近30期字符串形式命中次数',
  `b6_ne_hit` int(10) unsigned NOT NULL COMMENT '蓝6码最近7期命中次数',
  `b6_ne_rate` double unsigned NOT NULL COMMENT '蓝6码最近7期命中率',
  `b6_ne_full_rate` double unsigned NOT NULL COMMENT '蓝6码全中最近7期命中率',
  `b6_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝6码最近7期字符串形式命中次数',
  `b6_me_hit` int(10) unsigned NOT NULL COMMENT '蓝6码最近15期命中次数',
  `b6_me_rate` double unsigned NOT NULL COMMENT '蓝6码最近15期命中率',
  `b6_me_full_rate` double unsigned NOT NULL COMMENT '蓝6码全中最近15期命中率',
  `b6_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝6码最近15期字符串形式命中次数',
  `b6_hi_hit` int(10) unsigned NOT NULL COMMENT '蓝6码最近30期命中次数',
  `b6_hi_rate` double unsigned NOT NULL COMMENT '蓝6码最近30期命中率',
  `b6_hi_full_rate` double unsigned NOT NULL COMMENT '蓝6码全中最近30期命中率',
  `b6_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝6码最近30期字符串形式命中次数',
  `bk_ne_hit` int(10) unsigned NOT NULL COMMENT '杀蓝最近7期命中次数',
  `bk_ne_rate` double unsigned NOT NULL COMMENT '杀蓝最近7期命中率',
  `bk_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀蓝最近7期字符串形式命中次数',
  `bk_me_hit` int(10) unsigned NOT NULL COMMENT '杀蓝最近15期命中次数',
  `bk_me_rate` double unsigned NOT NULL COMMENT '杀蓝最近15期命中率',
  `bk_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀蓝最近15期字符串形式命中次数',
  `bk_hi_hit` int(10) unsigned NOT NULL COMMENT '杀蓝最近30期命中次数',
  `bk_hi_rate` double unsigned NOT NULL COMMENT '杀蓝最近30期命中率',
  `bk_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀蓝最近30期字符串形式命中次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_master_rate_idx` (`period`,`master_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=117988 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for dlt_recommend
-- ----------------------------
DROP TABLE IF EXISTS `dlt_recommend`;
CREATE TABLE `dlt_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '当前期号',
  `r1` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球三码推荐',
  `hit_r1` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球三胆命中个数',
  `r1_hit` tinyint(4) DEFAULT NULL COMMENT '红球三胆计算后数据',
  `r2` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球8码推荐',
  `hit_r2` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球8码命中个数',
  `r2_hit` tinyint(4) DEFAULT NULL COMMENT '红球8码计算后数据',
  `r3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球三码',
  `hit_r3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三码计算后数据',
  `r3_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '三码命中信息',
  `r10` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球12码推荐',
  `hit_r10` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球12码命中个数',
  `r10_hit` tinyint(4) DEFAULT NULL COMMENT '红球12码计算后数据',
  `r20` varchar(150) COLLATE utf8mb4_bin NOT NULL COMMENT '红球18码推荐',
  `hit_r20` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球18码命中个数',
  `r20_hit` tinyint(4) DEFAULT NULL COMMENT '红球18码计算后数据',
  `rk3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀三码',
  `hit_rk3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀三码命中个数',
  `rk3_hit` tinyint(4) DEFAULT NULL COMMENT '红球杀三码计算后数据',
  `rk6` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀六码',
  `hit_rk6` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀六码命中个数',
  `rk6_hit` tinyint(4) DEFAULT NULL COMMENT '红球杀六码计算后数据',
  `b1` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝球独胆',
  `hit_b1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球独胆命中',
  `b1_hit` tinyint(4) DEFAULT NULL,
  `b2` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球三码',
  `hit_b2` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球三码命中个数',
  `b2_hit` tinyint(4) DEFAULT NULL COMMENT '篮球三码计算后数据',
  `b6` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球六码推荐',
  `hit_b6` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球六码命中个数',
  `b6_hit` tinyint(4) DEFAULT NULL COMMENT '篮球六码计算后数据',
  `bk` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球杀三码',
  `hit_bk` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球杀三码命中个数',
  `bk_hit` tinyint(4) DEFAULT NULL COMMENT '篮球杀三码计算后数据',
  `calc_time` datetime DEFAULT NULL COMMENT '开奖计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dlt_recommend_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='大乐透预测推荐';

-- ----------------------------
-- Table structure for dlt_trace_master
-- ----------------------------
DROP TABLE IF EXISTS `dlt_trace_master`;
CREATE TABLE `dlt_trace_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪专家表示',
  `type` tinyint(2) unsigned NOT NULL COMMENT '追踪专家类型: 0-红球,1-蓝球',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开始追踪期号',
  `rank` int(11) unsigned NOT NULL COMMENT '开始追踪专家排名',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪专家数据渠道字段',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trace_master_idx` (`master_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for dlt_trace_result
-- ----------------------------
DROP TABLE IF EXISTS `dlt_trace_result`;
CREATE TABLE `dlt_trace_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `red` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球组合追踪',
  `hit_red` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球追踪计算结果',
  `red_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '红球追踪命中情况',
  `rk` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀码追踪',
  `hit_rk` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球杀码追踪计算结果',
  `rk_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '红球追踪杀码命中情况',
  `blue` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球追踪结果',
  `hit_blue` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球追踪计算结果',
  `blue_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '追踪蓝球命中情况',
  `bk` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球杀码追踪结果',
  `hit_bk` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪蓝球杀码计算命中',
  `bk_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '追踪蓝球杀码命中情况',
  `calc_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '追踪计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_trace_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_census
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_census`;
CREATE TABLE `fc3d_census` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` int(10) unsigned NOT NULL COMMENT '类型',
  `level` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '统计级别:排名多少之前的专家统计,0-不使用排名',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '数据指标渠道:独胆,双胆...',
  `census` json NOT NULL COMMENT '单类型统计:独胆,...,杀一码',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_census_idx` (`period`,`type`,`level`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30024 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_com_recommend
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_com_recommend`;
CREATE TABLE `fc3d_com_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测期号',
  `zu6` json NOT NULL COMMENT '组六推荐',
  `zu3` json NOT NULL COMMENT '组三推荐',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '组选推荐状态:1-已创建,2-已发布待开奖,3-已开奖',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '计算后开奖类型:1-豹子,2-组三,3-组六',
  `hit` tinyint(1) unsigned DEFAULT NULL COMMENT '计算后是否预测命中:0-否,1-是',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_com_recommend_idx` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='福彩3D组选推荐';

-- ----------------------------
-- Table structure for fc3d_early_warning
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_early_warning`;
CREATE TABLE `fc3d_early_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预警期号',
  `type` tinyint(4) unsigned NOT NULL COMMENT '预警类型',
  `warn` json DEFAULT NULL COMMENT '整数预警信息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_wraning_idx` (`period`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='福彩3D号码预警';

-- ----------------------------
-- Table structure for fc3d_home_master
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_home_master`;
CREATE TABLE `fc3d_home_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据指标字段',
  `hit_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家命中率字符串',
  `hit_rate` double unsigned NOT NULL COMMENT '专家命中率',
  `hit_series` int(10) unsigned NOT NULL COMMENT '连续命中次数',
  `rank` int(10) unsigned NOT NULL COMMENT '专家排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_home_master_idx` (`period`,`master_id`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=8161 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_icai
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_icai`;
CREATE TABLE `fc3d_icai` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识 ',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `d1` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '独胆',
  `hit_d1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '独胆开奖计算结果',
  `d1_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '独胆命中情况',
  `d2` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '双胆数据',
  `hit_d2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '双胆开奖命中结果',
  `d2_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '双胆命中情况',
  `d3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆数据',
  `hit_d3` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆数据命中结算果',
  `d3_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '三胆数据命中情况',
  `k1` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '杀一码数据',
  `hit_k1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀一码数据命中计算',
  `k1_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '杀一码数据命中情况',
  `k2` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀二码数据',
  `hit_k2` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀二码数据命中计算',
  `k2_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '杀二码数据命中情况',
  `c5` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '组合五码数据',
  `hit_c5` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合五码数据命中计算',
  `c5_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '组合五码s数据命中计算',
  `c6` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '组合六码数据',
  `hit_c6` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合六码数据命中结算',
  `c6_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '组合六码数据命中情况',
  `c7` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合七码数据',
  `hit_c7` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合七码数据命中计算',
  `c7_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '组合七码数据命中情况',
  `cb3` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '定位三码数据',
  `hit_cb3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定位三码预测数据计算',
  `cb3_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '定位三码数据命中情况',
  `cb4` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '定位四码数据',
  `hit_cb4` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定位四码预测数据计算',
  `cb4_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '定位四码数据命中情况',
  `cb5` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '定位五码预测数据',
  `hit_cb5` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定位五码数据命中计算',
  `cb5_hit` tinyint(3) unsigned DEFAULT '0' COMMENT '定位五码数据命中情况',
  `mark` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '数据判断标记：0-未标记,1-已标记',
  `calc_time` datetime DEFAULT NULL COMMENT '数据计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_icai_idx` (`master_id`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=421043 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='福彩3D预测数据';

-- ----------------------------
-- Table structure for fc3d_master_rank
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_master_rank`;
CREATE TABLE `fc3d_master_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hot` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为热门专家:0-否,1-是',
  `is_vip` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否是vip专家:0-否,1-是',
  `weight` double(10,3) NOT NULL,
  `rank` int(10) unsigned NOT NULL COMMENT '综合权重',
  `d1_wgt` double(10,3) unsigned NOT NULL COMMENT '独胆排名得分',
  `d1_rank` int(10) unsigned NOT NULL COMMENT '独胆权重',
  `d2_wgt` double(10,0) unsigned NOT NULL COMMENT '双胆排名得分',
  `d2_rank` int(10) unsigned NOT NULL COMMENT '双胆权重',
  `d3_wgt` double(10,3) unsigned NOT NULL COMMENT '三胆排名得分',
  `d3_rank` int(10) unsigned NOT NULL COMMENT '三胆权重',
  `c5_wgt` double(10,3) unsigned NOT NULL COMMENT '组合五码排名得分',
  `c5_rank` int(10) unsigned NOT NULL COMMENT '组合五码权重',
  `c6_wgt` double(10,3) unsigned NOT NULL COMMENT '组合六码排名得分',
  `c6_rank` int(10) unsigned NOT NULL COMMENT '组合六码权重',
  `c7_wgt` double(10,3) unsigned NOT NULL COMMENT '组合七码排名得分',
  `c7_rank` int(10) unsigned NOT NULL COMMENT '组合七码权重',
  `k1_wgt` double(10,3) unsigned NOT NULL COMMENT '杀一码排名得分',
  `k1_rank` int(10) unsigned NOT NULL COMMENT '杀一码权重',
  `k2_wgt` double(10,3) unsigned NOT NULL COMMENT '杀二码排名得分',
  `k2_rank` int(10) unsigned NOT NULL COMMENT '杀二码权重',
  `cb3_wgt` double(10,3) unsigned NOT NULL COMMENT '定位三码排名得分',
  `cb3_rank` int(10) unsigned NOT NULL COMMENT '定位三权重',
  `cb4_wgt` double(10,3) unsigned NOT NULL COMMENT '定位四码排名得分',
  `cb4_rank` int(10) unsigned NOT NULL COMMENT '定位四码权重',
  `cb5_wgt` double(10,3) unsigned NOT NULL COMMENT '定位五码排名得分',
  `cb5_rank` int(10) unsigned NOT NULL COMMENT '定位五码权重',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_master_rank_idx` (`period`,`master_id`),
  KEY `fc3d_master_rank_idx` (`period`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=960928 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_master_rate
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_master_rate`;
CREATE TABLE `fc3d_master_rate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `d1_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d1_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d1_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d1_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d1_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d1_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d1_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d1_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d1_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d2_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d2_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d2_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d2_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d2_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d2_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d2_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d2_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d2_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d3_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d3_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d3_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d3_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d3_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d3_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d3_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d3_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d3_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `c5_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `c5_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `c5_ne_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c5_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `c5_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `c5_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `c5_me_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c5_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `c5_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `c5_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `c5_hi_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c5_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `c6_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `c6_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `c6_ne_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c6_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `c6_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `c6_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `c6_me_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c6_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `c6_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `c6_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `c6_hi_full_rate` double(10,0) unsigned NOT NULL COMMENT '全命中率',
  `c6_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `c7_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `c7_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `c7_ne_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c7_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `c7_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `c7_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `c7_me_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c7_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `c7_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `c7_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `c7_hi_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c7_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k1_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k1_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k1_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k1_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k1_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k1_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k1_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k1_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k1_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k2_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k2_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k2_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k2_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k2_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k2_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k2_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k2_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k2_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `cb3_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `cb3_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `cb3_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `cb3_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `cb3_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `cb3_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `cb3_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `cb3_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `cb3_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `cb4_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `cb4_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `cb4_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `cb4_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `cb4_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `cb4_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `cb4_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `cb4_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `cb4_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `cb5_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `cb5_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `cb5_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `cb5_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `cb5_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `cb5_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `cb5_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `cb5_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `cb5_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_master_rate` (`period`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=449832 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_recommend
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_recommend`;
CREATE TABLE `fc3d_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `d1` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '独胆数据',
  `hit_d1` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '独胆命中数据',
  `d1_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '独胆命中数',
  `d2` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '双胆数据',
  `hit_d2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '双胆预测命中数据',
  `d2_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '双胆命中数',
  `d3` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '三胆预测数据',
  `hit_d3` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆预测命中数据',
  `d3_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '三胆命中数',
  `c5` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合五码数据',
  `hit_c5` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合五码命中数据',
  `c5_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '组合五码命中情况',
  `c6` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合六码数据',
  `hit_c6` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合六码命中数据',
  `c6_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '组合六码命中数',
  `c7` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合七码数据',
  `hit_c7` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合七码命中计算数据',
  `c7_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '组合七码命中情况',
  `k1` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀一码',
  `hit_k1` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀一码命中数据',
  `k1_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '杀一码命中数',
  `k2` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀二码',
  `hit_k2` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀二码命中数据',
  `k2_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '杀二码命中数据',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_recommend_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_today_pivot
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_today_pivot`;
CREATE TABLE `fc3d_today_pivot` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测期号',
  `best` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '第一胆码',
  `best_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '是否命中:0-否,>0-是',
  `hit_best` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '计算命中后数据',
  `second` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '第二胆码',
  `second_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '是否命中: 0-否,>0-是',
  `hit_second` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '第二胆码命中后数据',
  `quality` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '优质选号',
  `quality_hit` tinyint(2) unsigned DEFAULT NULL COMMENT '优质选号命中:0-否,>0-命中个数',
  `hit_quality` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '优质选号计算后命中数据',
  `master` json NOT NULL COMMENT '优质专家推荐',
  `edits` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '编辑次数',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `version` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '数据计算版本',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_pivot` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_trace_master
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_trace_master`;
CREATE TABLE `fc3d_trace_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家表示',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '追踪类型: 0-杀码，福彩3D只做杀码追踪',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开始追踪期号',
  `rank` int(11) unsigned NOT NULL COMMENT '开始追踪专家排名',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪字段渠道',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trace_master` (`master_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for fc3d_trace_result
-- ----------------------------
DROP TABLE IF EXISTS `fc3d_trace_result`;
CREATE TABLE `fc3d_trace_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `com` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合码追踪',
  `hit_com` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合码追踪计算命中信息',
  `com_hit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '组合码追踪命中情况',
  `kill` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀码追踪结果',
  `hit_kill` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀码追踪计算结果',
  `kill_hit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '杀码追踪命中情况',
  `calc_time` datetime DEFAULT NULL COMMENT '计算命中时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_fc3d_trace_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_around
-- ----------------------------
DROP TABLE IF EXISTS `lottery_around`;
CREATE TABLE `lottery_around` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(3) unsigned NOT NULL COMMENT '绕胆图类型：1-开奖号,2-试机号,3-开机号',
  `lotto` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型: fc3d-福彩3D,pl3-排列三',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `around` json NOT NULL COMMENT '绕胆图',
  `result` json DEFAULT NULL COMMENT '命中结果',
  `lotto_date` date NOT NULL COMMENT '开奖日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_around` (`lotto`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_award
-- ----------------------------
DROP TABLE IF EXISTS `lottery_award`;
CREATE TABLE `lottery_award` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '彩票类型:fc3d,pl3,ssq,dlt,qlc',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '开奖期号',
  `sales` bigint(20) unsigned NOT NULL COMMENT '本期销售额',
  `award` bigint(20) unsigned NOT NULL COMMENT '本期中奖金额',
  `pool` bigint(20) unsigned NOT NULL COMMENT '下期奖池余额',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_award_idx` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=18143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_code
-- ----------------------------
DROP TABLE IF EXISTS `lottery_code`;
CREATE TABLE `lottery_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lotto` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '开奖期号',
  `type` tinyint(3) unsigned NOT NULL COMMENT '万能码类型:1-万能四码,2-万能五码,3-万能六码,4-万能七码',
  `position` json NOT NULL COMMENT '万能码位置',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lotto_code_idx` (`lotto`,`period`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1879 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_dan
-- ----------------------------
DROP TABLE IF EXISTS `lottery_dan`;
CREATE TABLE `lottery_dan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `index1` json NOT NULL COMMENT '指数1',
  `index2` json NOT NULL COMMENT '指数2',
  `index3` json NOT NULL COMMENT '指数3',
  `index4` json NOT NULL COMMENT '指数4',
  `index5` json NOT NULL COMMENT '指数5',
  `index6` json NOT NULL COMMENT '指数6',
  `index7` json NOT NULL COMMENT '指数7',
  `index8` json NOT NULL COMMENT '指数8',
  `index9` json NOT NULL COMMENT '指数9',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lotto_dan` (`type`,`period`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1575 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_fair_trial
-- ----------------------------
DROP TABLE IF EXISTS `lottery_fair_trial`;
CREATE TABLE `lottery_fair_trial` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '奖号类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `ball` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '试机号奖号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_n3_trial_idx` (`type`,`period`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_honey
-- ----------------------------
DROP TABLE IF EXISTS `lottery_honey`;
CREATE TABLE `lottery_honey` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `honey` json NOT NULL COMMENT '蜂巢配胆图',
  `lotto_date` date NOT NULL COMMENT '开奖日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_honey` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_index
-- ----------------------------
DROP TABLE IF EXISTS `lottery_index`;
CREATE TABLE `lottery_index` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lottery` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测期号',
  `type` int(10) unsigned NOT NULL COMMENT '指数类型:1-全民专家;2-付费专家',
  `red_ball` json NOT NULL COMMENT '红球指数',
  `blue_ball` json DEFAULT NULL COMMENT '蓝球指数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_index_idx` (`lottery`,`period`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1710 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='彩票预测指数';

-- ----------------------------
-- Table structure for lottery_info
-- ----------------------------
DROP TABLE IF EXISTS `lottery_info`;
CREATE TABLE `lottery_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型:fc3d,pl2,ssq,dlt,qlc',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `red` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球区',
  `blue` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球区',
  `com` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '选三组选号码',
  `last` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '选三上一期同号期号',
  `shi` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '试机号',
  `kai` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开机号',
  `trial_time` datetime DEFAULT NULL COMMENT '试机号时间',
  `lot_date` date DEFAULT NULL COMMENT '开奖日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_info_idx` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=25058 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_item_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_item_omit`;
CREATE TABLE `lottery_item_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `cb1_amp` json NOT NULL COMMENT '百位振幅',
  `cb1_aod` json NOT NULL COMMENT '百位升平降',
  `cb1_bos` json NOT NULL COMMENT '百位大小',
  `cb1_oe` json NOT NULL COMMENT '奇偶遗漏',
  `cb2_amp` json NOT NULL COMMENT '十位振幅',
  `cb2_aod` json NOT NULL COMMENT '十位升平降',
  `cb2_bos` json NOT NULL COMMENT '十位大小',
  `cb2_oe` json NOT NULL COMMENT '奇偶遗漏',
  `cb3_amp` json NOT NULL COMMENT '个位振幅',
  `cb3_aod` json NOT NULL COMMENT '个位升平降',
  `cb3_bos` json NOT NULL COMMENT '个位大小',
  `cb3_oe` json NOT NULL COMMENT '奇偶遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_item_omit_idx` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=1087 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='分位振幅遗漏数据';

-- ----------------------------
-- Table structure for lottery_kl8_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_kl8_omit`;
CREATE TABLE `lottery_kl8_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '快乐8开奖期号',
  `ball_omit` json NOT NULL COMMENT '基础遗漏统计',
  `bs_omit` json NOT NULL COMMENT '大小遗漏统计',
  `oe_omit` json NOT NULL COMMENT '奇偶遗漏',
  `kua_omit` json NOT NULL COMMENT '跨度遗漏统计',
  `sum_omit` json NOT NULL COMMENT '和值遗漏统计',
  `tail_omit` json NOT NULL COMMENT '尾数遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_kl8_omit_idx` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=840 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_kua_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_kua_omit`;
CREATE TABLE `lottery_kua_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `base_omit` json NOT NULL COMMENT '基础走势',
  `amp_omit` json NOT NULL COMMENT '振幅走势',
  `amp_amp` json NOT NULL COMMENT '振幅的振幅遗漏',
  `ott_omit` json NOT NULL COMMENT '012路遗漏',
  `bms_omit` json NOT NULL COMMENT '大中小遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_kua_omit_idx` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=3657 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='跨度遗漏信息';

-- ----------------------------
-- Table structure for lottery_level
-- ----------------------------
DROP TABLE IF EXISTS `lottery_level`;
CREATE TABLE `lottery_level` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型:fc3d,pl3,ssq,dlt,qlc',
  `level` int(10) unsigned NOT NULL COMMENT '中奖等级',
  `extra` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否追加',
  `quantity` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '中奖注数',
  `bonus` double(10,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '单注奖金',
  `amount` double(10,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '派奖总金额',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_level_idx` (`type`,`period`,`level`,`extra`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=127181 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_match_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_match_omit`;
CREATE TABLE `lottery_match_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `code_omit` json NOT NULL COMMENT '对码出现遗漏',
  `all_omit` json NOT NULL COMMENT '总计遗漏',
  `com_omit` json NOT NULL COMMENT '组选号码对码组遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_match_omit` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=1087 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_news
-- ----------------------------
DROP TABLE IF EXISTS `lottery_news`;
CREATE TABLE `lottery_news` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '唯一标识',
  `sha` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '文章md5标识',
  `title` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '新闻标题',
  `source` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '新闻来源',
  `header` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'header图片',
  `type` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '新闻类型',
  `author` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '文章作者',
  `summary` varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '新闻摘要',
  `content` json NOT NULL COMMENT '新闻内容',
  `browse` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '新闻阅读数量',
  `homed` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否首页显示:1-是,0-否',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '新闻状态:0-无效,1-正常,2-热门',
  `gmt_create` datetime NOT NULL COMMENT '新闻时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_news_idx` (`seq`) USING BTREE COMMENT '唯一索引',
  UNIQUE KEY `uk_news_sha_idx` (`sha`)
) ENGINE=InnoDB AUTO_INCREMENT=790 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='新闻信息';

-- ----------------------------
-- Table structure for lottery_news_browse
-- ----------------------------
DROP TABLE IF EXISTS `lottery_news_browse`;
CREATE TABLE `lottery_news_browse` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '新闻标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_omit`;
CREATE TABLE `lottery_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `red` json DEFAULT NULL COMMENT '红球遗漏',
  `blue` json DEFAULT NULL COMMENT '蓝球遗漏',
  `cb1` json DEFAULT NULL COMMENT '百位遗漏',
  `cb2` json DEFAULT NULL COMMENT '十位遗漏',
  `cb3` json DEFAULT NULL COMMENT '个位遗漏',
  `cb4` json DEFAULT NULL COMMENT '排五第四位遗漏',
  `cb5` json DEFAULT NULL COMMENT '排五第五位遗漏',
  `extra` json DEFAULT NULL COMMENT '额外遗漏数据',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_omit_idx` (`type`,`period`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7905 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_ott
-- ----------------------------
DROP TABLE IF EXISTS `lottery_ott`;
CREATE TABLE `lottery_ott` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `bott` json NOT NULL COMMENT '百位012路',
  `sott` json NOT NULL COMMENT '十位012路',
  `gott` json NOT NULL COMMENT '个位012路',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_ott` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_pian_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_pian_omit`;
CREATE TABLE `lottery_pian_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '偏态数据期号',
  `level` int(11) unsigned NOT NULL COMMENT '偏态类型',
  `omit` json DEFAULT NULL COMMENT '偏态遗漏数据',
  `cb1` json DEFAULT NULL COMMENT '百位偏态遗漏',
  `cb2` json DEFAULT NULL COMMENT '十位偏态遗漏',
  `cb3` json DEFAULT NULL COMMENT '个位偏态遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pian_omit_idx` (`type`,`period`,`level`)
) ENGINE=InnoDB AUTO_INCREMENT=28951 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_pick
-- ----------------------------
DROP TABLE IF EXISTS `lottery_pick`;
CREATE TABLE `lottery_pick` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lottery` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '选号期号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `red_ball` json NOT NULL COMMENT '红球选号',
  `blue_ball` json DEFAULT NULL COMMENT '蓝球选号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_pick_idx` (`lottery`,`period`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户选号记录';

-- ----------------------------
-- Table structure for lottery_pl5_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_pl5_omit`;
CREATE TABLE `lottery_pl5_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `cb1_amp` json NOT NULL COMMENT '百位振幅',
  `cb1_aod` json NOT NULL COMMENT '百位升平降',
  `cb1_bos` json NOT NULL COMMENT '百位大小',
  `cb1_oe` json NOT NULL COMMENT '奇偶遗漏',
  `cb2_amp` json NOT NULL COMMENT '十位振幅',
  `cb2_aod` json NOT NULL COMMENT '十位升平降',
  `cb2_bos` json NOT NULL COMMENT '十位大小',
  `cb2_oe` json NOT NULL COMMENT '奇偶遗漏',
  `cb3_amp` json NOT NULL COMMENT '个位振幅',
  `cb3_aod` json NOT NULL COMMENT '个位升平降',
  `cb3_bos` json NOT NULL COMMENT '个位大小',
  `cb3_oe` json NOT NULL COMMENT '奇偶遗漏',
  `cb4_amp` json NOT NULL COMMENT '个位振幅',
  `cb4_aod` json NOT NULL COMMENT '个位升平降',
  `cb4_bos` json NOT NULL COMMENT '个位大小',
  `cb4_oe` json NOT NULL COMMENT '奇偶遗漏',
  `cb5_amp` json NOT NULL COMMENT '个位振幅',
  `cb5_aod` json NOT NULL COMMENT '个位升平降',
  `cb5_bos` json NOT NULL COMMENT '个位大小',
  `cb5_oe` json NOT NULL COMMENT '奇偶遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl5_omit_idx` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=495 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='排列五分位振幅遗漏数据';

-- ----------------------------
-- Table structure for lottery_skill
-- ----------------------------
DROP TABLE IF EXISTS `lottery_skill`;
CREATE TABLE `lottery_skill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sha` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '来源md5唯一标识(去重使用）',
  `seq` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '唯一编号',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '彩票类型',
  `title` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '使用技巧文章标题',
  `header` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'header图片',
  `summary` varchar(500) COLLATE utf8mb4_bin NOT NULL COMMENT '文章摘要',
  `content` json NOT NULL COMMENT '内容',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态：0-无效,1-正常,2-热门',
  `browse` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lottery_skill_idx` (`seq`),
  UNIQUE KEY `uk_skill_sha_idx` (`sha`)
) ENGINE=InnoDB AUTO_INCREMENT=2492 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='彩票选号实用技巧';

-- ----------------------------
-- Table structure for lottery_skill_browse
-- ----------------------------
DROP TABLE IF EXISTS `lottery_skill_browse`;
CREATE TABLE `lottery_skill_browse` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '技能标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_sum_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_sum_omit`;
CREATE TABLE `lottery_sum_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `base_omit` json NOT NULL COMMENT '和值走势',
  `ott_omit` json NOT NULL COMMENT '012路遗漏',
  `tail_omit` json NOT NULL COMMENT '和尾遗漏',
  `tail_amp` json NOT NULL COMMENT '和尾振幅遗漏',
  `bms_omit` json NOT NULL COMMENT '大中小遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sum_omit_idx` (`type`,`period`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2595 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='和值遗漏信息';

-- ----------------------------
-- Table structure for lottery_trend_omit
-- ----------------------------
DROP TABLE IF EXISTS `lottery_trend_omit`;
CREATE TABLE `lottery_trend_omit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '开奖期号',
  `form_omit` json NOT NULL COMMENT '形态遗漏',
  `ott_omit` json NOT NULL COMMENT '012路形态遗漏',
  `mode_omit` json NOT NULL COMMENT '升降造型遗漏',
  `bs_omit` json NOT NULL COMMENT '大小形态遗漏',
  `oe_omit` json NOT NULL COMMENT '奇偶形态遗漏',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_omit_idx` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=3247 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lottery_user_count
-- ----------------------------
DROP TABLE IF EXISTS `lottery_user_count`;
CREATE TABLE `lottery_user_count` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `news` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户新闻浏览量',
  `skill` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '选号技能浏览量',
  `master` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '专家浏览量',
  `focus` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '专家关注量',
  `subscribe` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '专家订阅量',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_battle
-- ----------------------------
DROP TABLE IF EXISTS `master_battle`;
CREATE TABLE `master_battle` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT 'PK状态:0-移除,1-正常',
  `sort` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '排序标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_battle_idx` (`type`,`user_id`,`master_id`,`period`) USING BTREE COMMENT '专家PK唯一标识'
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_browse
-- ----------------------------
DROP TABLE IF EXISTS `master_browse`;
CREATE TABLE `master_browse` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '浏览流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家类型:fc3d,pl3,ssq,dlt,qlc',
  `source` tinyint(2) NOT NULL COMMENT '浏览类型:1-预测数据,2-统计分析,3-批量比较',
  `source_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '来源标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_browse_idx` (`user_id`,`period`,`type`,`source`,`source_id`) USING BTREE,
  KEY `master_browse_idx` (`user_id`,`period`,`type`,`source`,`source_id`,`gmt_create`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_evict
-- ----------------------------
DROP TABLE IF EXISTS `master_evict`;
CREATE TABLE `master_evict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_evict` (`type`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2750 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='淘汰专家';

-- ----------------------------
-- Table structure for master_feeds
-- ----------------------------
DROP TABLE IF EXISTS `master_feeds`;
CREATE TABLE `master_feeds` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `field` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测数据字段类型',
  `feed_type` tinyint(2) unsigned NOT NULL COMMENT 'feed类型:1-胆码,2-杀码,3-组码,4-蓝球',
  `rate_text` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '命中率文案',
  `hit_text` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '命中信息文案',
  `field_hit` tinyint(4) unsigned NOT NULL COMMENT '字段命中数',
  `field_rate` double(10,2) unsigned NOT NULL COMMENT '字段命中率',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'feed流期号',
  `renew` tinyint(1) unsigned NOT NULL COMMENT '是否已经更预测数据:0-否,1-是',
  `renew_period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '更新期号',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '信息流状态:0-无效,1-正常',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_feed_type_master_idx` (`type`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_focus
-- ----------------------------
DROP TABLE IF EXISTS `master_focus`;
CREATE TABLE `master_focus` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `gmt_create` datetime NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_focus_idx` (`user_id`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_glad_info
-- ----------------------------
DROP TABLE IF EXISTS `master_glad_info`;
CREATE TABLE `master_glad_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `lottery` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测期号',
  `content` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '喜讯内容',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '喜讯类型:1-中奖,2-高命中率',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_glad_idx` (`master_id`,`lottery`,`period`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_info
-- ----------------------------
DROP TABLE IF EXISTS `master_info`;
CREATE TABLE `master_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家唯一标识',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '名称',
  `phone` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `address` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '地址',
  `avatar` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家头像',
  `source` tinyint(3) unsigned NOT NULL COMMENT '专家来源:1-icai，2-平台自有',
  `source_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家来源标识:icai-标识,自有-seq标识',
  `description` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家描述',
  `browse` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `subscribe` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订阅次数',
  `searches` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '搜索次数',
  `enable` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '开启预测彩种集合',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '专家状态:0-无效,1-创建,2-审核通过,3-冻结',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_info_idx` (`seq`),
  UNIQUE KEY `uk_source_id_idx` (`source_id`),
  KEY `master_phone_idx` (`phone`) USING BTREE,
  KEY `name_seq_idx` (`name`,`seq`,`source`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9810 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_lottery
-- ----------------------------
DROP TABLE IF EXISTS `master_lottery`;
CREATE TABLE `master_lottery` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型:fc3d,pl3,ssq,dlt,qlc',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `source` tinyint(4) NOT NULL COMMENT '来源来类型:1-icai，2-自有',
  `source_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '来源标识',
  `level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '专家级别：1,2,3,4,5',
  `latest` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最新更新数据期数',
  `achieve` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '累计获奖总金额',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_lottery_idx` (`master_id`,`type`),
  UNIQUE KEY `uk_master_source_idx` (`source_id`,`type`) USING BTREE,
  KEY `master_lottery_idx` (`type`,`master_id`,`source`,`source_id`,`latest`)
) ENGINE=InnoDB AUTO_INCREMENT=18194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_num3_count
-- ----------------------------
DROP TABLE IF EXISTS `master_num3_count`;
CREATE TABLE `master_num3_count` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家表示',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型: fc3d,pl3',
  `d1_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '独胆总命中次数',
  `d2_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '双旦总命中次数',
  `d3_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '三胆总命中次数',
  `c5_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '5码总命中次数',
  `c6_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '六码总命中次数',
  `c7_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '七码总命中次数',
  `k1_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '杀一码总命中次数',
  `k2_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '杀二码总命中次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_count` (`master_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for master_subscribe
-- ----------------------------
DROP TABLE IF EXISTS `master_subscribe`;
CREATE TABLE `master_subscribe` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `trace` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '关注专家追踪字段',
  `trace_zh` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪字段中文名称',
  `special` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否特别关注',
  `gmt_create` datetime NOT NULL COMMENT '收藏时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_subscribe_idx` (`user_id`,`master_id`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='专家订阅收藏';

-- ----------------------------
-- Table structure for num3_com_warning
-- ----------------------------
DROP TABLE IF EXISTS `num3_com_warning`;
CREATE TABLE `num3_com_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩票类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `dan` json NOT NULL COMMENT '胆码预警',
  `kill` json NOT NULL COMMENT '杀码组合',
  `two_ma` json NOT NULL COMMENT '两码组合预警',
  `zu6` json NOT NULL COMMENT '组六推荐',
  `zu3` json NOT NULL COMMENT '组三推荐',
  `kua_list` json NOT NULL COMMENT '跨度推荐',
  `sum_list` json NOT NULL COMMENT '和值推荐',
  `hit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否命中:0-未开奖,1-豹子,2-组三,3-组六,4-未命中',
  `version` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本',
  `edits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '修改次数',
  `edit_time` datetime DEFAULT NULL COMMENT '最新编辑时间',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_com_warning` (`type`,`period`)
) ENGINE=InnoDB AUTO_INCREMENT=1428573795513270312 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for num3_layer_filter
-- ----------------------------
DROP TABLE IF EXISTS `num3_layer_filter`;
CREATE TABLE `num3_layer_filter` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测期号',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '彩种类型',
  `layer1` json NOT NULL COMMENT '过滤层1',
  `layer2` json NOT NULL COMMENT '过滤层2',
  `layer3` json NOT NULL COMMENT '过滤层3',
  `layer4` json DEFAULT NULL COMMENT '过滤层4',
  `layer5` json DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL COMMENT '最新编辑时间',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `edits` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '编辑次数',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_layer_filter` (`period`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for num3_lotto_index
-- ----------------------------
DROP TABLE IF EXISTS `num3_lotto_index`;
CREATE TABLE `num3_lotto_index` (
  `id` bigint(20) NOT NULL,
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '彩种类型',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '统计期号',
  `dan_index` json NOT NULL COMMENT '胆码指数',
  `kill_index` json NOT NULL COMMENT '杀码指数',
  `com_index` json NOT NULL COMMENT '组选号码指数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lotto_index` (`type`,`period`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='选三型预测数据统计指数';

-- ----------------------------
-- Table structure for pl3_census
-- ----------------------------
DROP TABLE IF EXISTS `pl3_census`;
CREATE TABLE `pl3_census` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` int(10) unsigned NOT NULL COMMENT '类型',
  `level` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '统计级别:排名多少之前的专家统计,0-不使用排名',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '数据指标渠道:独胆,双胆...',
  `census` json NOT NULL COMMENT '单类型统计:独胆,...,杀一码',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_census_idx` (`period`,`type`,`level`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=149445 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_com_recommend
-- ----------------------------
DROP TABLE IF EXISTS `pl3_com_recommend`;
CREATE TABLE `pl3_com_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测推荐期号',
  `zu6` json NOT NULL COMMENT '组选六码推荐',
  `zu3` json NOT NULL COMMENT '组选三码推荐',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '组合推荐状态:1-已创建,2-已发布待开奖,3-已开奖',
  `hit` tinyint(3) unsigned DEFAULT NULL COMMENT '是否命中:0-否,1-是',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '开奖类型:1-豹子,2-组三,3-组六',
  `calc_time` datetime DEFAULT NULL COMMENT '开奖计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_com_recommend_idx` (`period`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_early_warning
-- ----------------------------
DROP TABLE IF EXISTS `pl3_early_warning`;
CREATE TABLE `pl3_early_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预警期号',
  `type` tinyint(3) unsigned NOT NULL COMMENT '预警类型',
  `warn` json NOT NULL COMMENT '和值预警',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_warning_idx` (`period`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=901 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='排列三选号预警';

-- ----------------------------
-- Table structure for pl3_home_master
-- ----------------------------
DROP TABLE IF EXISTS `pl3_home_master`;
CREATE TABLE `pl3_home_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据指标字段',
  `hit_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家命中率字符串',
  `hit_rate` double unsigned NOT NULL COMMENT '专家命中率',
  `hit_series` int(10) unsigned NOT NULL COMMENT '连续命中次数',
  `rank` int(10) unsigned NOT NULL COMMENT '专家排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_home_master_idx` (`period`,`master_id`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=10129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_icai
-- ----------------------------
DROP TABLE IF EXISTS `pl3_icai`;
CREATE TABLE `pl3_icai` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `d1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆数据',
  `hit_d1` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '独胆计算命中数据',
  `d1_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '独胆命中数',
  `d2` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆数据',
  `hit_d2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '双胆数据命中',
  `d2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '双胆命中数',
  `d3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆数据',
  `hit_d3` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆数据命中',
  `d3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '三胆命中数',
  `c5` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '五码数据',
  `hit_c5` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '五码数据命中',
  `c5_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '五码命中数',
  `c6` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '六码数据',
  `hit_c6` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '六码命中数据',
  `c6_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '六码命中数据',
  `c7` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '七码数据',
  `hit_c7` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '七码命中数据',
  `c7_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '七码命中数',
  `k1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '杀一码',
  `hit_k1` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀一码命中数据',
  `k1_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '杀一码命中数',
  `k2` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '杀二码数据',
  `hit_k2` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀二码命中数据',
  `k2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '杀二码命中数',
  `cb3` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '定位三码数据',
  `hit_cb3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定位三码命中数据',
  `cb3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '定位三码命中数据',
  `cb4` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '定位四码数据',
  `hit_cb4` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定位四码数据',
  `cb4_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '定位四码命中数据',
  `cb5` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '定位五码数据',
  `hit_cb5` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '定位五码命中数据',
  `cb5_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '定位五码命中数',
  `mark` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '数据判断标记：0-未标记,1-已标记',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_icai_idx` (`period`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=400464 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_master_rank
-- ----------------------------
DROP TABLE IF EXISTS `pl3_master_rank`;
CREATE TABLE `pl3_master_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hot` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为热门专家:0-否,1-是',
  `is_vip` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否是vip专家:0-否,1-是',
  `weight` double(10,3) NOT NULL,
  `rank` int(10) unsigned NOT NULL COMMENT '综合权重',
  `d1_wgt` double(10,3) unsigned NOT NULL COMMENT '独胆排名得分',
  `d1_rank` int(10) unsigned NOT NULL COMMENT '独胆权重',
  `d2_wgt` double(10,0) unsigned NOT NULL COMMENT '双胆排名得分',
  `d2_rank` int(10) unsigned NOT NULL COMMENT '双胆权重',
  `d3_wgt` double(10,3) unsigned NOT NULL COMMENT '三胆排名得分',
  `d3_rank` int(10) unsigned NOT NULL COMMENT '三胆权重',
  `c5_wgt` double(10,3) unsigned NOT NULL COMMENT '组合五码排名得分',
  `c5_rank` int(10) unsigned NOT NULL COMMENT '组合五码权重',
  `c6_wgt` double(10,3) unsigned NOT NULL COMMENT '组合六码排名得分',
  `c6_rank` int(10) unsigned NOT NULL COMMENT '组合六码权重',
  `c7_wgt` double(10,3) unsigned NOT NULL COMMENT '组合七码排名得分',
  `c7_rank` int(10) unsigned NOT NULL COMMENT '组合七码权重',
  `k1_wgt` double(10,3) unsigned NOT NULL COMMENT '杀一码排名得分',
  `k1_rank` int(10) unsigned NOT NULL COMMENT '杀一码权重',
  `k2_wgt` double(10,3) unsigned NOT NULL COMMENT '杀二码排名得分',
  `k2_rank` int(10) unsigned NOT NULL COMMENT '杀二码权重',
  `cb3_wgt` double(10,3) unsigned NOT NULL COMMENT '定位三码排名得分',
  `cb3_rank` int(10) unsigned NOT NULL COMMENT '定位三权重',
  `cb4_wgt` double(10,3) unsigned NOT NULL COMMENT '定位四码排名得分',
  `cb4_rank` int(10) unsigned NOT NULL COMMENT '定位四码权重',
  `cb5_wgt` double(10,3) unsigned NOT NULL COMMENT '定位五码排名得分',
  `cb5_rank` int(10) unsigned NOT NULL COMMENT '定位五码权重',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_master_rank_idx` (`period`,`master_id`),
  KEY `pl3_master_rank_idx` (`period`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=670286 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_master_rate
-- ----------------------------
DROP TABLE IF EXISTS `pl3_master_rate`;
CREATE TABLE `pl3_master_rate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `d1_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d1_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d1_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d1_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d1_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d1_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d1_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d1_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d1_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d2_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d2_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d2_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d2_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d2_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d2_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d2_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d2_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d2_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d3_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d3_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d3_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d3_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d3_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d3_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d3_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d3_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d3_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `c5_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `c5_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `c5_ne_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c5_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `c5_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `c5_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `c5_me_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c5_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `c5_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `c5_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `c5_hi_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c5_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `c6_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `c6_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `c6_ne_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c6_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `c6_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `c6_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `c6_me_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c6_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `c6_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `c6_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `c6_hi_full_rate` double(10,0) unsigned NOT NULL COMMENT '全命中率',
  `c6_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `c7_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `c7_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `c7_ne_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c7_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `c7_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `c7_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `c7_me_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c7_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `c7_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `c7_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `c7_hi_full_rate` double(10,2) unsigned NOT NULL COMMENT '全命中率',
  `c7_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k1_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k1_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k1_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k1_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k1_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k1_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k1_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k1_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k1_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k2_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k2_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k2_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k2_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k2_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k2_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k2_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k2_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k2_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `cb3_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `cb3_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `cb3_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `cb3_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `cb3_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `cb3_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `cb3_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `cb3_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `cb3_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `cb4_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `cb4_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `cb4_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `cb4_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `cb4_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `cb4_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `cb4_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `cb4_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `cb4_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `cb5_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `cb5_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `cb5_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `cb5_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `cb5_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `cb5_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `cb5_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `cb5_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `cb5_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_master_rate` (`period`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=568280 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_recommend
-- ----------------------------
DROP TABLE IF EXISTS `pl3_recommend`;
CREATE TABLE `pl3_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `d1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆数据',
  `hit_d1` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '独胆命中数据',
  `d1_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '独胆命中数',
  `d2` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆数据',
  `hit_d2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '双胆预测命中数据',
  `d2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '双胆命中数',
  `d3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆预测数据',
  `hit_d3` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆预测命中数据',
  `d3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '三胆命中数',
  `c5` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '组合五码数据',
  `hit_c5` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合五码命中数据',
  `c5_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '组合五码命中情况',
  `c6` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '组合六码数据',
  `hit_c6` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合六码命中数据',
  `c6_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '组合六码命中数',
  `c7` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '组合七码数据',
  `hit_c7` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组合七码命中计算数据',
  `c7_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '组合七码命中个数',
  `k1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '杀一码',
  `hit_k1` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀一码命中数据',
  `k1_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '杀一码命中数',
  `k2` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '杀二码',
  `hit_k2` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀二码命中数据',
  `k2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '杀二码命中数据',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_recommend_idx` (`period`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_today_pivot
-- ----------------------------
DROP TABLE IF EXISTS `pl3_today_pivot`;
CREATE TABLE `pl3_today_pivot` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '预测期号',
  `best` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '第一胆码',
  `best_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '是否命中:0-否,>0-是',
  `hit_best` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '计算命中后数据',
  `second` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '第二胆码',
  `second_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '是否命中: 0-否,>0-是',
  `hit_second` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '第二胆码命中后数据',
  `quality` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '优质选号',
  `quality_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '优质选号命中:0-否,>0-命中个数',
  `hit_quality` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '优质选号计算后命中数据',
  `master` json NOT NULL COMMENT '优质专家推荐',
  `edits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '编辑次数',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `version` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '数据计算版本',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_pivot` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_trace_master
-- ----------------------------
DROP TABLE IF EXISTS `pl3_trace_master`;
CREATE TABLE `pl3_trace_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家表示',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '追踪类型：0-杀码，排列三只做杀码追踪',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开始追踪期号',
  `rank` int(11) unsigned NOT NULL COMMENT '开始追踪专家排名',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪字段渠道',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trace_master` (`master_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pl3_trace_result
-- ----------------------------
DROP TABLE IF EXISTS `pl3_trace_result`;
CREATE TABLE `pl3_trace_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `com` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合码追踪',
  `hit_com` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组合码追踪计算命中信息',
  `com_hit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '组合码追踪命中情况',
  `kill` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀码追踪结果',
  `hit_kill` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀码追踪计算结果',
  `kill_hit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '杀码追踪命中情况',
  `calc_time` datetime DEFAULT NULL COMMENT '计算命中时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pl3_trace_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for qlc_census
-- ----------------------------
DROP TABLE IF EXISTS `qlc_census`;
CREATE TABLE `qlc_census` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期号',
  `type` int(10) unsigned NOT NULL COMMENT '类型',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据指标字段渠道',
  `level` int(10) unsigned NOT NULL COMMENT '统计级别',
  `census` json NOT NULL COMMENT '统计数据',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_census_idx` (`period`,`type`,`channel`,`level`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=513 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for qlc_early_warning
-- ----------------------------
DROP TABLE IF EXISTS `qlc_early_warning`;
CREATE TABLE `qlc_early_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预警期号',
  `type` tinyint(4) unsigned NOT NULL COMMENT '预警类型',
  `warn` json NOT NULL COMMENT '预警值',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_warn_idx` (`period`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='七乐彩号码预警';

-- ----------------------------
-- Table structure for qlc_home_master
-- ----------------------------
DROP TABLE IF EXISTS `qlc_home_master`;
CREATE TABLE `qlc_home_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据指标类型',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hit_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '命中率字符串',
  `hit_rate` double unsigned NOT NULL COMMENT '命中率',
  `hit_series` int(10) unsigned NOT NULL COMMENT '连续命中次数',
  `rank` int(10) unsigned NOT NULL COMMENT '排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_home_master_idx` (`period`,`type`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for qlc_icai
-- ----------------------------
DROP TABLE IF EXISTS `qlc_icai`;
CREATE TABLE `qlc_icai` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '当前预测期',
  `master_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '专家id',
  `r1` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆',
  `hit_r1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '独胆命中数据',
  `r1_hit` tinyint(1) unsigned DEFAULT NULL COMMENT '独胆是否命中',
  `r2` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆',
  `hit_r2` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '双胆命中数据',
  `r2_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '双胆是否命中',
  `r3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆',
  `hit_r3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆命中数据',
  `r3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '三胆是否命中',
  `r12` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '十二码',
  `hit_r12` varchar(120) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球12码命中数据',
  `r12_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '红球12码是否命中',
  `r18` varchar(250) COLLATE utf8mb4_bin NOT NULL COMMENT '18码数据',
  `hit_r18` varchar(250) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球18码命中数据',
  `r18_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '18码是否命中',
  `r22` varchar(250) COLLATE utf8mb4_bin NOT NULL COMMENT '22码数据',
  `hit_r22` varchar(250) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '22码命中数据',
  `r22_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '22码是否命中',
  `k3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀三码数据',
  `hit_k3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀3码命中数据',
  `k3_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '杀三码是否命中',
  `k6` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '杀6码数据',
  `hit_k6` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀六码是否命中',
  `k6_hit` tinyint(3) unsigned DEFAULT NULL COMMENT '杀六码是否命中',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_uniq_idx` (`master_id`,`period`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=36992 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for qlc_master_rank
-- ----------------------------
DROP TABLE IF EXISTS `qlc_master_rank`;
CREATE TABLE `qlc_master_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hot` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否为热门专家:0-否,1-是',
  `is_vip` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是vip专家:0-否,1-是',
  `weight` double(10,3) unsigned NOT NULL COMMENT '综合权重得分',
  `rank` int(10) unsigned NOT NULL COMMENT '综合排名权重',
  `r1_wgt` double(10,3) unsigned NOT NULL COMMENT '独胆权重',
  `r1_rank` int(10) unsigned NOT NULL COMMENT '独胆排名',
  `r2_wgt` double(10,3) unsigned NOT NULL COMMENT '双胆权重',
  `r2_rank` int(10) unsigned NOT NULL COMMENT '双胆排名',
  `r3_wgt` double(10,3) unsigned NOT NULL COMMENT '三胆权重',
  `r3_rank` int(10) unsigned NOT NULL COMMENT '三胆排名',
  `r12_wgt` double(10,3) unsigned NOT NULL COMMENT '12码权重',
  `r12_rank` int(10) unsigned NOT NULL COMMENT '12码排名',
  `r18_wgt` double(10,3) unsigned NOT NULL COMMENT '18码等分权重',
  `r18_rank` int(10) unsigned NOT NULL COMMENT '18码排名',
  `r22_wgt` double(10,3) unsigned NOT NULL COMMENT '22码排名权重',
  `r22_rank` int(10) unsigned NOT NULL COMMENT '22码排名',
  `k3_wgt` double(10,3) unsigned NOT NULL COMMENT '杀三码权重得分',
  `k3_rank` int(10) unsigned NOT NULL COMMENT '杀三码排名',
  `k6_wgt` double(10,3) unsigned NOT NULL COMMENT '杀六码排名得分',
  `k6_rank` int(10) unsigned NOT NULL COMMENT '杀六码排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_master_rank_idx` (`period`,`master_id`),
  KEY `qlc_master_rank_idx` (`period`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=36992 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for qlc_master_rate
-- ----------------------------
DROP TABLE IF EXISTS `qlc_master_rate`;
CREATE TABLE `qlc_master_rate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `r1_ne_hit` int(11) unsigned NOT NULL COMMENT '独胆最近7期命中次数',
  `r1_ne_rate` double unsigned NOT NULL COMMENT '独胆最近7期命中率',
  `r1_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近7期字符串形式命中次数',
  `r1_me_hit` int(11) unsigned NOT NULL COMMENT '独胆最近15期命中次数',
  `r1_me_rate` double unsigned NOT NULL COMMENT '独胆最近15期命中率',
  `r1_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近15期字符串形式命中次数',
  `r1_hi_hit` int(11) unsigned NOT NULL COMMENT '独胆最近30期命中次数',
  `r1_hi_rate` double unsigned NOT NULL COMMENT '独胆最近30期命中率',
  `r1_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近30期字符串形式命中次数',
  `r2_ne_hit` int(11) unsigned NOT NULL COMMENT '双胆最近7期命中次数',
  `r2_ne_rate` double unsigned NOT NULL COMMENT '双胆最近7期命中率',
  `r2_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近7期字符串形式命中次数',
  `r2_me_hit` int(11) unsigned NOT NULL COMMENT '双胆最近15期命中次数',
  `r2_me_rate` double unsigned NOT NULL COMMENT '双胆最近15期命中率',
  `r2_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近15期字符串形式命中次数',
  `r2_hi_hit` int(11) unsigned NOT NULL COMMENT '双胆最近30期命中次数',
  `r2_hi_rate` double unsigned NOT NULL COMMENT '双胆最近30期命中率',
  `r2_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近30期字符串形式命中次数',
  `r3_ne_hit` int(11) unsigned NOT NULL COMMENT '三胆最近7期命中次数',
  `r3_ne_rate` double unsigned NOT NULL COMMENT '三胆最近7期命中率',
  `r3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近7期字符串形式命中次数',
  `r3_me_hit` int(11) unsigned NOT NULL COMMENT '三胆最近15期命中次数',
  `r3_me_rate` double unsigned NOT NULL COMMENT '三胆最近15期命中率',
  `r3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近15期字符串形式命中次数',
  `r3_hi_hit` int(11) unsigned NOT NULL COMMENT '三胆最近30期命中次数',
  `r3_hi_rate` double unsigned NOT NULL COMMENT '三胆最近30期命中率',
  `r3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近30期字符串形式命中次数',
  `r12_ne_hit` int(11) unsigned NOT NULL COMMENT '10码最近7期命中次数',
  `r12_ne_rate` double unsigned NOT NULL COMMENT '10码最近7期命中率',
  `r12_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '10码最近7期字符串形式命中次数',
  `r12_me_hit` int(11) unsigned NOT NULL COMMENT '10码最近15期命中次数',
  `r12_me_rate` double unsigned NOT NULL DEFAULT '0' COMMENT '10码最近15期命中率',
  `r12_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '10码最近15期字符串形式命中次数',
  `r12_hi_hit` int(11) unsigned NOT NULL COMMENT '10码最近30期命中次数',
  `r12_hi_rate` double unsigned NOT NULL DEFAULT '0' COMMENT '12码30期命中率',
  `r12_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '10码最近30期字符串形式命中次数',
  `r18_ne_hit` int(11) unsigned NOT NULL COMMENT '20码最近7期命中次数',
  `r18_ne_full_rate` double unsigned NOT NULL COMMENT '20码全中最近7期命中率',
  `r18_ne_rate` double unsigned NOT NULL COMMENT '20码最近7期命中率',
  `r18_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近7期字符串形式命中次数',
  `r18_me_hit` int(11) unsigned NOT NULL COMMENT '20码最近15期命中次数',
  `r18_me_rate` double unsigned NOT NULL COMMENT '20码最近15期命中率',
  `r18_me_full_rate` double unsigned NOT NULL COMMENT '20码全中最近15期命中率',
  `r18_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近15期字符串形式命中次数',
  `r18_hi_hit` int(11) unsigned NOT NULL COMMENT '20码最近30期命中次数',
  `r18_hi_rate` double unsigned NOT NULL COMMENT '20码最近30期命中率',
  `r18_hi_full_rate` double unsigned NOT NULL COMMENT '20码全中最近30期命中率',
  `r18_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近30期字符串形式命中次数',
  `r22_ne_hit` int(11) unsigned NOT NULL COMMENT '20码最近7期命中次数',
  `r22_ne_full_rate` double unsigned NOT NULL COMMENT '20码全中最近7期命中率',
  `r22_ne_rate` double unsigned NOT NULL COMMENT '20码最近7期命中率',
  `r22_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近7期字符串形式命中次数',
  `r22_me_hit` int(11) unsigned NOT NULL COMMENT '20码最近15期命中次数',
  `r22_me_rate` double unsigned NOT NULL COMMENT '20码最近15期命中率',
  `r22_me_full_rate` double unsigned NOT NULL COMMENT '20码全中最近15期命中率',
  `r22_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近15期字符串形式命中次数',
  `r22_hi_hit` int(11) unsigned NOT NULL COMMENT '20码最近30期命中次数',
  `r22_hi_rate` double unsigned NOT NULL COMMENT '20码最近30期命中率',
  `r22_hi_full_rate` double unsigned NOT NULL COMMENT '20码全中最近30期命中率',
  `r22_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近30期字符串形式命中次数',
  `k3_ne_hit` int(11) unsigned NOT NULL COMMENT '杀3码最近7期命中次数',
  `k3_ne_rate` double unsigned NOT NULL COMMENT '杀3码最近7期命中率',
  `k3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近7期字符串形式命中次数',
  `k3_me_hit` int(11) unsigned NOT NULL COMMENT '杀3码最近15期命中次数',
  `k3_me_rate` double unsigned NOT NULL COMMENT '杀3码最近15期命中率',
  `k3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近15期字符串形式命中次数',
  `k3_hi_hit` int(11) unsigned NOT NULL COMMENT '杀3码最近30期命中次数',
  `k3_hi_rate` double unsigned NOT NULL COMMENT '杀3码最近30期命中率',
  `k3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近30期字符串形式命中次数',
  `k6_ne_hit` int(11) unsigned NOT NULL COMMENT '杀3码最近7期命中次数',
  `k6_ne_rate` double unsigned NOT NULL COMMENT '杀3码最近7期命中率',
  `k6_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近7期字符串形式命中次数',
  `k6_me_hit` int(11) unsigned NOT NULL COMMENT '杀3码最近15期命中次数',
  `k6_me_rate` double unsigned NOT NULL COMMENT '杀3码最近15期命中率',
  `k6_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近15期字符串形式命中次数',
  `k6_hi_hit` int(11) unsigned NOT NULL COMMENT '杀3码最近30期命中次数',
  `k6_hi_rate` double unsigned NOT NULL COMMENT '杀3码最近30期命中率',
  `k6_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近30期字符串形式命中次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_master_rate_idx` (`period`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36992 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for qlc_recommend
-- ----------------------------
DROP TABLE IF EXISTS `qlc_recommend`;
CREATE TABLE `qlc_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '当前期号',
  `r1` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆',
  `hit_r1` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆命中数',
  `r1_hit` tinyint(4) DEFAULT NULL COMMENT '三胆计算后命中数据',
  `r2` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '双胆',
  `hit_r2` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '9码命中数',
  `r2_hit` tinyint(4) DEFAULT NULL COMMENT '9码计算后数据',
  `r3` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '三胆',
  `hit_r3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '三胆',
  `r3_hit` tinyint(4) DEFAULT NULL,
  `r12` varchar(150) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '12码数据',
  `hit_r12` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '12码命中数',
  `r12_hit` tinyint(4) DEFAULT NULL COMMENT '12码计算后数据',
  `r18` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '18码数据',
  `hit_r18` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '18码命中数',
  `r18_hit` tinyint(4) DEFAULT NULL COMMENT '18码计算后数据',
  `r22` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '22码数据',
  `hit_r22` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '22码数据命中信息',
  `r22_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '22码命中',
  `k3` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀三码数据',
  `hit_k3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀码命中数',
  `k3_hit` tinyint(4) DEFAULT NULL COMMENT '杀三码计算后数据',
  `k6` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '杀六码数据',
  `hit_k6` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '杀六码命中数',
  `k6_hit` tinyint(4) DEFAULT NULL COMMENT '杀六码计算后数据',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_qlc_recommend_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='七乐彩推荐预测';

-- ----------------------------
-- Table structure for ssq_census
-- ----------------------------
DROP TABLE IF EXISTS `ssq_census`;
CREATE TABLE `ssq_census` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `type` tinyint(3) NOT NULL COMMENT '数据指标字段',
  `level` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '统计级别:排名多少之前的专家统计,0-不使用排名',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '统计数据来源',
  `census` json NOT NULL COMMENT '统计数据',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_census_idx` (`period`,`type`,`level`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=485 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ssq_early_warning
-- ----------------------------
DROP TABLE IF EXISTS `ssq_early_warning`;
CREATE TABLE `ssq_early_warning` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预警期号',
  `type` tinyint(4) unsigned NOT NULL COMMENT '类型',
  `warn` json NOT NULL COMMENT '预警值',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_warn_idx` (`period`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='双色球号码预警';

-- ----------------------------
-- Table structure for ssq_home_master
-- ----------------------------
DROP TABLE IF EXISTS `ssq_home_master`;
CREATE TABLE `ssq_home_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `type` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据指标字段',
  `hit_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '命中率字符串',
  `hit_rate` double unsigned NOT NULL COMMENT '命中率',
  `hit_series` int(10) unsigned NOT NULL COMMENT '连续命中次数',
  `rank` int(10) unsigned NOT NULL COMMENT '排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_home_master_idx` (`period`,`type`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3895 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ssq_icai
-- ----------------------------
DROP TABLE IF EXISTS `ssq_icai`;
CREATE TABLE `ssq_icai` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期数',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '大师id',
  `b3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球3码推荐',
  `hit_b3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球3码命中数',
  `b3_hit` tinyint(3) unsigned DEFAULT NULL,
  `b5` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球5码推荐',
  `hit_b5` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球5码命中数',
  `b5_hit` tinyint(3) unsigned DEFAULT NULL,
  `bk` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球杀5码',
  `hit_bk` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球杀5命中数',
  `bk_hit` tinyint(3) unsigned DEFAULT NULL,
  `r1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '红球独胆',
  `hit_r1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球1码命中数',
  `r1_hit` tinyint(3) unsigned DEFAULT NULL,
  `r2` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '红旗2码',
  `hit_r2` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球2码命中数',
  `r2_hit` tinyint(3) unsigned DEFAULT NULL,
  `r3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '红球3码',
  `hit_r3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球3码命中数',
  `r3_hit` tinyint(3) unsigned DEFAULT NULL,
  `r12` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '红区12码',
  `hit_r12` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球12码命中数',
  `r12_hit` tinyint(3) unsigned DEFAULT NULL,
  `r20` varchar(500) COLLATE utf8mb4_bin NOT NULL COMMENT '红区20码',
  `hit_r20` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球20码命中数',
  `r20_hit` tinyint(3) unsigned DEFAULT NULL,
  `r25` varchar(500) COLLATE utf8mb4_bin NOT NULL COMMENT '红球25码',
  `hit_r25` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球25码命中数',
  `r25_hit` tinyint(3) unsigned DEFAULT NULL,
  `rk3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀3',
  `hit_rk3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀3命中数',
  `rk3_hit` tinyint(3) unsigned DEFAULT NULL,
  `rk6` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀6',
  `hit_rk6` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀6命中数',
  `rk6_hit` tinyint(3) unsigned DEFAULT NULL,
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_icai_idx` (`master_id`,`period`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=133030 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='双色球数据';

-- ----------------------------
-- Table structure for ssq_master_rank
-- ----------------------------
DROP TABLE IF EXISTS `ssq_master_rank`;
CREATE TABLE `ssq_master_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家编号',
  `hot` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为热门专家：0-否,1-是',
  `is_vip` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为vip专家:0-否,1-是',
  `weight` double(10,3) unsigned NOT NULL COMMENT '综合权重',
  `rank` int(10) unsigned NOT NULL COMMENT '总排名权重',
  `r1_wgt` double(10,3) unsigned NOT NULL COMMENT '独胆权重',
  `r1_rank` int(10) unsigned NOT NULL COMMENT '独胆排名',
  `r2_wgt` double(10,3) unsigned NOT NULL COMMENT '双胆权重',
  `r2_rank` int(10) unsigned NOT NULL COMMENT '双胆排名',
  `r3_wgt` double(10,3) unsigned NOT NULL COMMENT '三胆权重',
  `r3_rank` int(10) unsigned NOT NULL COMMENT '三胆排名',
  `r12_wgt` double(10,3) unsigned NOT NULL COMMENT '12码权重',
  `r12_rank` int(10) unsigned NOT NULL COMMENT '红球12码排名',
  `r20_wgt` double(10,3) unsigned NOT NULL COMMENT '20码权重',
  `r20_rank` int(10) unsigned NOT NULL COMMENT '红球20码排名',
  `r25_wgt` double(10,3) unsigned NOT NULL COMMENT '25码权重',
  `r25_rank` int(10) unsigned NOT NULL COMMENT '红球25码排名',
  `rk3_wgt` double(10,3) unsigned NOT NULL COMMENT '杀三码权重',
  `rk3_rank` int(10) unsigned NOT NULL COMMENT '红球杀三码排名',
  `rk6_wgt` double(10,3) unsigned NOT NULL COMMENT '杀六码权重',
  `rk6_rank` int(10) unsigned NOT NULL COMMENT '红球杀六码排名',
  `b3_wgt` double(10,3) unsigned NOT NULL COMMENT '蓝球三码权重',
  `b3_rank` int(10) unsigned NOT NULL COMMENT '蓝球3码排名',
  `b5_wgt` double(10,3) unsigned NOT NULL COMMENT '篮球五码权重',
  `b5_rank` int(10) unsigned NOT NULL COMMENT '蓝球5码排名权重',
  `bk_wgt` double(10,3) unsigned NOT NULL COMMENT '蓝球杀码权重',
  `bk_rank` int(10) unsigned NOT NULL COMMENT '蓝球杀码排名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_master_rank_idx` (`period`,`master_id`),
  KEY `ssq_master_rank_idx` (`period`,`rank`)
) ENGINE=InnoDB AUTO_INCREMENT=245492 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ssq_master_rate
-- ----------------------------
DROP TABLE IF EXISTS `ssq_master_rate`;
CREATE TABLE `ssq_master_rate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `r1_ne_hit` int(10) unsigned NOT NULL COMMENT '独胆最近7期命中次数',
  `r1_ne_rate` double unsigned NOT NULL COMMENT '独胆最近7期命中率',
  `r1_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近7期字符串形式命中次数',
  `r1_me_hit` int(10) unsigned NOT NULL COMMENT '独胆最近15期命中次数',
  `r1_me_rate` double unsigned NOT NULL COMMENT '独胆最近15期命中率',
  `r1_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近15期字符串形式命中次数',
  `r1_hi_hit` int(10) unsigned NOT NULL COMMENT '独胆最近30期命中次数',
  `r1_hi_rate` double unsigned NOT NULL COMMENT '独胆最近30期命中率',
  `r1_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '独胆最近30期字符串形式命中次数',
  `r2_ne_hit` int(10) unsigned NOT NULL COMMENT '双胆最近7期命中次数',
  `r2_ne_rate` double unsigned NOT NULL COMMENT '双胆最近7期命中率',
  `r2_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近7期字符串形式命中次数',
  `r2_me_hit` int(10) unsigned NOT NULL COMMENT '双胆最近15期命中次数',
  `r2_me_rate` double unsigned NOT NULL COMMENT '双胆最近15期命中率',
  `r2_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近15期字符串形式命中次数',
  `r2_hi_hit` int(10) unsigned NOT NULL COMMENT '双胆最近30期命中次数',
  `r2_hi_rate` double unsigned NOT NULL COMMENT '双胆最近30期命中率',
  `r2_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '双胆最近30期字符串形式命中次数',
  `r3_ne_hit` int(10) unsigned NOT NULL COMMENT '三胆最近7期命中次数',
  `r3_ne_rate` double unsigned NOT NULL COMMENT '三胆最近7期命中率',
  `r3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近7期字符串形式命中次数',
  `r3_me_hit` int(10) unsigned NOT NULL COMMENT '三胆最近15期命中次数',
  `r3_me_rate` double unsigned NOT NULL COMMENT '三胆最近15期命中率',
  `r3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近15期字符串形式命中次数',
  `r3_hi_hit` int(10) unsigned NOT NULL COMMENT '三胆最近30期命中次数',
  `r3_hi_rate` double unsigned NOT NULL COMMENT '三胆最近30期命中率',
  `r3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '三胆最近30期字符串形式命中次数',
  `r12_ne_hit` int(10) unsigned NOT NULL COMMENT '12码最近7期命中次数',
  `r12_ne_rate` double unsigned NOT NULL COMMENT '12码最近7期命中率',
  `r12_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '12码最近7期字符串形式命中次数',
  `r12_me_hit` int(10) unsigned NOT NULL COMMENT '12码最近15期命中次数',
  `r12_me_rate` double unsigned NOT NULL COMMENT '12码最近15期命中率',
  `r12_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '12码最近15期字符串形式命中次数',
  `r12_hi_hit` int(10) unsigned NOT NULL COMMENT '12码最近30期命中次数',
  `r12_hi_rate` double unsigned NOT NULL COMMENT '12码最近30期命中率',
  `r12_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '12码最近30期字符串形式命中次数',
  `r20_ne_hit` int(10) unsigned NOT NULL COMMENT '20码最近7期命中次数',
  `r20_ne_full_rate` double unsigned NOT NULL COMMENT '20码全中最近7期命中率',
  `r20_ne_rate` double unsigned NOT NULL COMMENT '20码最近7期命中率',
  `r20_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近7期字符串形式命中次数',
  `r20_me_hit` int(10) unsigned NOT NULL COMMENT '20码最近15期命中次数',
  `r20_me_rate` double unsigned NOT NULL COMMENT '20码最近15期命中率',
  `r20_me_full_rate` double unsigned NOT NULL COMMENT '20码全中最近15期命中率',
  `r20_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近15期字符串形式命中次数',
  `r20_hi_hit` int(10) unsigned NOT NULL COMMENT '20码最近30期命中次数',
  `r20_hi_rate` double unsigned NOT NULL COMMENT '20码最近30期命中率',
  `r20_hi_full_rate` double unsigned NOT NULL COMMENT '20码全中最近30期命中率',
  `r20_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近30期字符串形式命中次数',
  `r25_ne_hit` int(10) unsigned NOT NULL COMMENT '20码最近7期命中次数',
  `r25_ne_full_rate` double unsigned NOT NULL COMMENT '20码全中最近7期命中率',
  `r25_ne_rate` double unsigned NOT NULL COMMENT '20码最近7期命中率',
  `r25_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近7期字符串形式命中次数',
  `r25_me_hit` int(10) unsigned NOT NULL COMMENT '20码最近15期命中次数',
  `r25_me_rate` double unsigned NOT NULL COMMENT '20码最近15期命中率',
  `r25_me_full_rate` double unsigned NOT NULL COMMENT '20码全中最近15期命中率',
  `r25_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近15期字符串形式命中次数',
  `r25_hi_hit` int(10) unsigned NOT NULL COMMENT '20码最近30期命中次数',
  `r25_hi_rate` double unsigned NOT NULL COMMENT '20码最近30期命中率',
  `r25_hi_full_rate` double unsigned NOT NULL COMMENT '20码全中最近30期命中率',
  `r25_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码最近30期字符串形式命中次数',
  `r25_hi_full_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '20码全中最近30期字符串形式命中次数',
  `rk3_ne_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近7期命中次数',
  `rk3_ne_rate` double unsigned NOT NULL COMMENT '杀3码最近7期命中率',
  `rk3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近7期字符串形式命中次数',
  `rk3_me_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近15期命中次数',
  `rk3_me_rate` double unsigned NOT NULL COMMENT '杀3码最近15期命中率',
  `rk3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近15期字符串形式命中次数',
  `rk3_hi_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近30期命中次数',
  `rk3_hi_rate` double unsigned NOT NULL COMMENT '杀3码最近30期命中率',
  `rk3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近30期字符串形式命中次数',
  `rk6_ne_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近7期命中次数',
  `rk6_ne_rate` double unsigned NOT NULL COMMENT '杀3码最近7期命中率',
  `rk6_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近7期字符串形式命中次数',
  `rk6_me_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近15期命中次数',
  `rk6_me_rate` double unsigned NOT NULL COMMENT '杀3码最近15期命中率',
  `rk6_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近15期字符串形式命中次数',
  `rk6_hi_hit` int(10) unsigned NOT NULL COMMENT '杀3码最近30期命中次数',
  `rk6_hi_rate` double unsigned NOT NULL COMMENT '杀3码最近30期命中率',
  `rk6_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3码最近30期字符串形式命中次数',
  `b3_ne_hit` int(10) unsigned NOT NULL COMMENT '蓝3码最近7期命中次数',
  `b3_ne_rate` double unsigned NOT NULL COMMENT '蓝3码最近7期命中率',
  `b3_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝3码最近7期字符串形式命中次数',
  `b3_me_hit` int(10) unsigned NOT NULL COMMENT '蓝3码最近15期命中次数',
  `b3_me_rate` double unsigned NOT NULL COMMENT '蓝3码最近15期命中率',
  `b3_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝3码最近15期字符串形式命中次数',
  `b3_hi_hit` int(10) unsigned NOT NULL COMMENT '蓝3码最近30期命中次数',
  `b3_hi_rate` double unsigned NOT NULL COMMENT '蓝3码最近30期命中率',
  `b3_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝3码最近30期字符串形式命中次数',
  `b5_ne_hit` int(10) unsigned NOT NULL COMMENT '蓝5码最近7期命中次数',
  `b5_ne_rate` double unsigned NOT NULL COMMENT '蓝5码最近7期命中率',
  `b5_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝5码最近7期字符串形式命中次数',
  `b5_me_hit` int(10) unsigned NOT NULL COMMENT '蓝5码最近15期命中次数',
  `b5_me_rate` double unsigned NOT NULL COMMENT '蓝5码最近15期命中率',
  `b5_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝5码最近15期字符串形式命中次数',
  `b5_hi_hit` int(10) unsigned NOT NULL COMMENT '蓝5码最近30期命中次数',
  `b5_hi_rate` double unsigned NOT NULL COMMENT '蓝5码最近30期命中率',
  `b5_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '蓝5码最近30期字符串形式命中次数',
  `bk_ne_hit` int(10) unsigned NOT NULL COMMENT '杀蓝最近7期命中次数',
  `bk_ne_rate` double unsigned NOT NULL COMMENT '杀蓝最近7期命中率',
  `bk_ne_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀蓝最近7期字符串形式命中次数',
  `bk_me_hit` int(10) unsigned NOT NULL COMMENT '杀蓝最近15期命中次数',
  `bk_me_rate` double unsigned NOT NULL COMMENT '杀蓝最近15期命中率',
  `bk_me_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀蓝最近15期字符串形式命中次数',
  `bk_hi_hit` int(10) unsigned NOT NULL COMMENT '杀蓝最近30期命中次数',
  `bk_hi_rate` double unsigned NOT NULL COMMENT '杀蓝最近30期命中率',
  `bk_hi_count` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀蓝最近30期字符串形式命中次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_master_rate_idx` (`period`,`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=173347 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ssq_recommend
-- ----------------------------
DROP TABLE IF EXISTS `ssq_recommend`;
CREATE TABLE `ssq_recommend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期数',
  `r1` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '红球独胆',
  `hit_r1` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球独胆命中信息',
  `r1_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '红球独单命中',
  `r2` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '红球双胆',
  `hit_r2` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球双胆命中信息',
  `r2_hit` tinyint(4) unsigned DEFAULT NULL COMMENT '红球双胆命中',
  `r3` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '红球三码',
  `hit_r3` tinyint(4) unsigned DEFAULT NULL COMMENT '红球命中数',
  `r3_hit` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球计算后数据',
  `r12` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球8码推荐数据',
  `hit_r12` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球命中数',
  `r12_hit` tinyint(4) DEFAULT NULL COMMENT '红球计算后命中数',
  `r20` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球12码推荐数据',
  `hit_r20` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球命中数',
  `r20_hit` tinyint(4) DEFAULT NULL COMMENT '红球计算后数据',
  `r25` varchar(200) COLLATE utf8mb4_bin NOT NULL COMMENT '红球18码推荐数据',
  `hit_r25` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球命中数据',
  `r25_hit` tinyint(4) DEFAULT NULL COMMENT '红球命中数据',
  `rk3` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀三码推荐数据',
  `hit_rk3` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀三码是否命中',
  `rk3_hit` tinyint(4) DEFAULT NULL COMMENT '红球杀码计算后数据',
  `rk6` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀六码',
  `hit_rk6` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '红球杀六码是否命中',
  `rk6_hit` tinyint(4) DEFAULT NULL COMMENT '红球杀码计算后数据',
  `b3` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球三码推荐数据',
  `hit_b3` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球命中数',
  `b3_hit` tinyint(4) DEFAULT NULL COMMENT '篮球计算后数据',
  `b5` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球五码推荐数据',
  `hit_b5` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球五码是否命中',
  `b5_hit` tinyint(4) DEFAULT NULL COMMENT '篮球五码计算后数据',
  `bk` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '篮球杀三码数据',
  `hit_bk` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '篮球杀五码是否命中',
  `bk_hit` tinyint(4) DEFAULT NULL COMMENT '篮球杀五码计算后数据',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态:0-无效或取消,1-审核中，2-投放',
  `calc_time` datetime NOT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_recommend_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='双色球推荐信息';

-- ----------------------------
-- Table structure for ssq_trace_master
-- ----------------------------
DROP TABLE IF EXISTS `ssq_trace_master`;
CREATE TABLE `ssq_trace_master` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `type` tinyint(2) unsigned NOT NULL COMMENT '追踪类型: 0-红球,1-篮球',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开始追踪期号',
  `rank` int(11) unsigned NOT NULL COMMENT '开始追踪专家排名',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪专家字段渠道',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_trace_idx` (`master_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ssq_trace_result
-- ----------------------------
DROP TABLE IF EXISTS `ssq_trace_result`;
CREATE TABLE `ssq_trace_result` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '开奖期号',
  `red` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球组合追踪',
  `hit_red` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球追踪计算结果',
  `red_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '红球追踪命中情况',
  `rk` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '红球杀码追踪',
  `hit_rk` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '红球杀码追踪计算结果',
  `rk_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '红球追踪杀码命中情况',
  `blue` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球追踪结果',
  `hit_blue` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球追踪计算结果',
  `blue_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '追踪蓝球命中情况',
  `bk` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '蓝球杀码追踪结果',
  `hit_bk` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '追踪蓝球杀码计算命中',
  `bk_hit` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '追踪蓝球杀码命中情况',
  `calc_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '追踪计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ssq_trace_idx` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for web_icai_kl8_info
-- ----------------------------
DROP TABLE IF EXISTS `web_icai_kl8_info`;
CREATE TABLE `web_icai_kl8_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期号',
  `d1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '选一推荐',
  `hit_d1` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选一命中计算后数据',
  `d1_hit` int(11) unsigned DEFAULT '0' COMMENT '选一命中个数',
  `d2` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '选二推荐',
  `hit_d2` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选二命中计算后数据',
  `d2_hit` int(11) unsigned DEFAULT '0' COMMENT '选二命中个数',
  `d3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '选三推荐',
  `hit_d3` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选三命中计算后数据',
  `d3_hit` int(11) unsigned DEFAULT '0' COMMENT '选三命中个数',
  `d4` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '选四推荐',
  `hit_d4` varchar(30) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选四命中计算后数据',
  `d4_hit` int(11) unsigned DEFAULT '0' COMMENT '选五命中个数',
  `d5` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '选五推荐',
  `hit_d5` varchar(30) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选五命中计算后数据',
  `d5_hit` int(11) unsigned DEFAULT '0' COMMENT '选五命中个数',
  `d6` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '选六推荐',
  `hit_d6` varchar(30) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选六命中计算后数据',
  `d6_hit` int(11) unsigned DEFAULT '0' COMMENT '选六命中个数',
  `d7` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '选七推荐',
  `hit_d7` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选七命中计算后数据',
  `d7_hit` int(11) unsigned DEFAULT '0' COMMENT '选七命中个数',
  `d8` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '选八推荐',
  `hit_d8` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选八命中计算后数据',
  `d8_hit` int(11) unsigned DEFAULT '0' COMMENT '选八命中个数',
  `d9` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '选九推荐',
  `hit_d9` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选九命中计算后数据',
  `d9_hit` int(11) unsigned DEFAULT '0' COMMENT '选九命中个数',
  `d10` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '选十推荐',
  `hit_d10` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选十命中计算后数据',
  `d10_hit` int(11) unsigned DEFAULT '0' COMMENT '选十命中个数',
  `d11` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '选11推荐',
  `hit_d11` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选11命中计算后数据',
  `d11_hit` int(11) unsigned DEFAULT '0' COMMENT '选11命中个数',
  `d12` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '选12推荐',
  `hit_d12` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选12命中计算后数据',
  `d12_hit` int(11) unsigned DEFAULT '0' COMMENT '选12命中个数',
  `d13` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '选13推荐',
  `hit_d13` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选13命中计算后数据',
  `d13_hit` int(11) unsigned DEFAULT '0' COMMENT '选13命中个数',
  `d14` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '选14推荐',
  `hit_d14` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选14命中计算后数据',
  `d14_hit` int(11) unsigned DEFAULT '0' COMMENT '选14命中个数',
  `d15` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '选15推荐',
  `hit_d15` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选15命中计算后数据',
  `d15_hit` int(11) unsigned DEFAULT '0' COMMENT '选15命中个数',
  `d20` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '选20推荐',
  `hit_d20` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '选20命中计算后数据',
  `d20_hit` int(11) unsigned DEFAULT '0' COMMENT '选20命中个数',
  `k1` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '杀1推荐',
  `hit_k1` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '杀1命中计算后数据',
  `k1_hit` int(11) unsigned DEFAULT '0' COMMENT '杀1命中个数',
  `k2` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '杀2推荐',
  `hit_k2` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '杀2命中计算后数据',
  `k2_hit` int(11) unsigned DEFAULT '0' COMMENT '杀2命中个数',
  `k3` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '杀3推荐',
  `hit_k3` varchar(30) COLLATE utf8mb4_bin DEFAULT '' COMMENT '杀3命中计算后数据',
  `k3_hit` int(11) unsigned DEFAULT '0' COMMENT '杀3命中个数',
  `k4` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '杀4推荐',
  `hit_k4` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '杀4命中计算后数据',
  `k4_hit` int(11) unsigned DEFAULT '0' COMMENT '杀4命中个数',
  `k5` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '杀5推荐',
  `hit_k5` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '杀5命中计算后数据',
  `k5_hit` int(11) unsigned DEFAULT '0' COMMENT '杀5命中个数',
  `calc_time` datetime DEFAULT NULL COMMENT '计算时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_kl8_info` (`master_id`,`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='网络快乐8推荐数据';

-- ----------------------------
-- Table structure for web_kl8_census
-- ----------------------------
DROP TABLE IF EXISTS `web_kl8_census`;
CREATE TABLE `web_kl8_census` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期号',
  `type` int(11) unsigned NOT NULL COMMENT '统计类型',
  `level` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '统计级别',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '数据分类：统计字段类型',
  `census` json NOT NULL COMMENT '统计数据',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_kl8_census` (`period`,`type`,`channel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for web_kl8_master_info
-- ----------------------------
DROP TABLE IF EXISTS `web_kl8_master_info`;
CREATE TABLE `web_kl8_master_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家名称',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '专家名称',
  `logo` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家头像',
  `third_id` bigint(20) unsigned NOT NULL COMMENT '第三方来源id',
  `third_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '第三方来源名称',
  `latest` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最新预测期号',
  `browse` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_master_id` (`master_id`) USING BTREE,
  UNIQUE KEY `uk_third_id` (`third_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for web_kl8_master_rank
-- ----------------------------
DROP TABLE IF EXISTS `web_kl8_master_rank`;
CREATE TABLE `web_kl8_master_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `hot` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否为热门专家: 0-否,1-是',
  `is_vip` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否为vip专家:0-否,1-是',
  `weight` double(10,3) unsigned NOT NULL COMMENT '综合权重',
  `rank` int(10) unsigned NOT NULL COMMENT '综合排序权重',
  `d1_wgt` double(10,3) unsigned NOT NULL COMMENT '选一权重',
  `d1_rank` int(10) unsigned NOT NULL COMMENT '选一排序',
  `d2_wgt` double(10,3) unsigned NOT NULL COMMENT '选二权重',
  `d2_rank` int(10) unsigned NOT NULL COMMENT '选二排序',
  `d3_wgt` double(10,3) unsigned NOT NULL COMMENT '选三权重',
  `d3_rank` int(10) unsigned NOT NULL COMMENT '选三排名',
  `d4_wgt` double(10,3) unsigned NOT NULL COMMENT '选四权重',
  `d4_rank` int(10) unsigned NOT NULL COMMENT '选四排序',
  `d5_wgt` double(10,3) unsigned NOT NULL COMMENT '选五权重',
  `d5_rank` int(10) unsigned NOT NULL COMMENT '选五排序',
  `d6_wgt` double(10,3) unsigned NOT NULL COMMENT '选六权重',
  `d6_rank` int(10) unsigned NOT NULL COMMENT '选六排名',
  `d7_wgt` double(10,3) unsigned NOT NULL COMMENT '选七权重',
  `d7_rank` int(10) unsigned NOT NULL COMMENT '选七排序',
  `d8_wgt` double(10,3) unsigned NOT NULL COMMENT '选八权重',
  `d8_rank` int(10) unsigned NOT NULL COMMENT '选八排序',
  `d9_wgt` double(10,3) unsigned NOT NULL COMMENT '选九权重',
  `d9_rank` int(10) unsigned NOT NULL COMMENT '选九排名',
  `d10_wgt` double(10,3) unsigned NOT NULL COMMENT '选十权重',
  `d10_rank` int(10) unsigned NOT NULL COMMENT '选十排序',
  `d11_wgt` double(10,3) unsigned NOT NULL COMMENT '选11权重',
  `d11_rank` int(10) unsigned NOT NULL COMMENT '选11排序',
  `d12_wgt` double(10,3) unsigned NOT NULL COMMENT '选12权重',
  `d12_rank` int(10) unsigned NOT NULL COMMENT '选12排名',
  `d13_wgt` double(10,3) unsigned NOT NULL COMMENT '选13权重',
  `d13_rank` int(10) unsigned NOT NULL COMMENT '选13排序',
  `d14_wgt` double(10,3) unsigned NOT NULL COMMENT '选14权重',
  `d14_rank` int(10) unsigned NOT NULL COMMENT '选14排序',
  `d15_wgt` double(10,3) unsigned NOT NULL COMMENT '选15权重',
  `d15_rank` int(10) unsigned NOT NULL COMMENT '选15排名',
  `d20_wgt` double(10,3) unsigned NOT NULL COMMENT '选20权重',
  `d20_rank` int(10) unsigned NOT NULL COMMENT '选20排名',
  `k1_wgt` double(10,3) unsigned NOT NULL COMMENT '杀1权重',
  `k1_rank` int(10) unsigned NOT NULL COMMENT '杀1排序',
  `k2_wgt` double(10,3) unsigned NOT NULL COMMENT '杀2权重',
  `k2_rank` int(10) unsigned NOT NULL COMMENT '杀2排序',
  `k3_wgt` double(10,3) unsigned NOT NULL COMMENT '杀3权重',
  `k3_rank` int(10) unsigned NOT NULL COMMENT '杀3排名',
  `k4_wgt` double(10,3) unsigned NOT NULL COMMENT '杀4权重',
  `k4_rank` int(10) unsigned NOT NULL COMMENT '杀4排序',
  `k5_wgt` double(10,3) unsigned NOT NULL COMMENT '杀5权重',
  `k5_rank` int(10) unsigned NOT NULL COMMENT '杀5排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_kl8_master_rank` (`period`,`master_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for web_kl8_master_rate
-- ----------------------------
DROP TABLE IF EXISTS `web_kl8_master_rate`;
CREATE TABLE `web_kl8_master_rate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '预测期号',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '专家标识',
  `d1_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d1_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d1_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d1_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d1_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d1_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d1_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d1_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d1_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d2_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d2_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d2_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d2_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d2_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d2_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d2_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d2_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d2_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d3_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d3_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d3_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d3_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d3_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d3_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d3_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d3_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d3_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d4_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d4_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d4_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d4_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d4_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d4_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d4_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d4_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d4_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d5_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d5_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d5_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d5_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d5_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d5_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d5_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d5_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d5_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d6_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d6_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d6_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d6_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d6_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d6_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d6_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d6_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d6_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d7_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d7_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d7_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d7_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d7_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d7_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d7_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d7_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d7_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d8_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d8_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d8_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d8_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d8_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d8_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d8_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d8_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d8_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d9_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d9_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d9_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d9_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d9_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d9_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d9_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d9_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d9_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d10_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d10_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d10_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d10_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d10_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d10_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d10_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d10_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d10_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d11_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d11_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d11_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d11_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d11_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d11_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d11_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d11_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d11_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d12_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d12_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d12_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d12_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d12_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d12_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d12_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d12_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d12_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d13_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d13_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d13_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d13_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d13_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d13_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d13_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d13_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d13_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d14_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d14_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d14_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d14_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d14_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d14_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d14_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d14_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d14_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d15_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d15_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d15_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d15_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d15_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d15_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d15_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d15_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d15_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `d20_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `d20_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `d20_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `d20_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `d20_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `d20_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `d20_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `d20_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `d20_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k1_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k1_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k1_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k1_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k1_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k1_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k1_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k1_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k1_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k2_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k2_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k2_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k2_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k2_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k2_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k2_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k2_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k2_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k3_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k3_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k3_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k3_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k3_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k3_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k3_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k3_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k3_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k4_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k4_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k4_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k4_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k4_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k4_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k4_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k4_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k4_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `k5_ne_hit` int(10) unsigned NOT NULL COMMENT '最近7期连续命中次数',
  `k5_ne_rate` double(10,2) unsigned NOT NULL COMMENT '最近7期命中率',
  `k5_ne_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近7期命中率字符串',
  `k5_me_hit` int(10) unsigned NOT NULL COMMENT '最近15期连续命中次数',
  `k5_me_rate` double(10,2) unsigned NOT NULL COMMENT '最近15期命中率',
  `k5_me_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近15期命中率字符串',
  `k5_hi_hit` int(10) unsigned NOT NULL COMMENT '最近30期连续命中次数',
  `k5_hi_rate` double(10,2) unsigned NOT NULL COMMENT '最近30期命中率',
  `k5_hi_count` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最近30期命中率字符串',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_kl8_master_rate` (`period`,`master_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
