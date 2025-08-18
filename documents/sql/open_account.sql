/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : open_account

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2025-08-18 10:10:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity_chance
-- ----------------------------
DROP TABLE IF EXISTS `activity_chance`;
CREATE TABLE `activity_chance` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `draw_id` bigint(20) unsigned NOT NULL COMMENT '用户活动标识',
  `code` json NOT NULL COMMENT '机会对应的号码',
  `type` tinyint(3) unsigned NOT NULL COMMENT '机会类型: 1-系统赠送,2-看视频获得',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '机会状态: 0-未中奖,1-待抽奖,2-已中奖',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='抽会员一次抽奖机会';

-- ----------------------------
-- Table structure for activity_draw
-- ----------------------------
DROP TABLE IF EXISTS `activity_draw`;
CREATE TABLE `activity_draw` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `activity_id` bigint(20) unsigned NOT NULL COMMENT '活动id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `day` date NOT NULL COMMENT '用户参与活动日期',
  `code` json DEFAULT NULL COMMENT '用户本次抽奖开奖号，抽奖时开出',
  `times` int(11) unsigned NOT NULL COMMENT '抽奖机会次数',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '抽奖状态：0-未中奖,1-待抽奖,,2-已中奖',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_user` (`activity_id`,`user_id`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='抽会员活动用户记录信息';

-- ----------------------------
-- Table structure for activity_member
-- ----------------------------
DROP TABLE IF EXISTS `activity_member`;
CREATE TABLE `activity_member` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '抽奖活动名称',
  `duration` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '会员有效时间,单位-天',
  `minimum` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '单次抽奖机会最低次数',
  `throttle` int(11) unsigned NOT NULL DEFAULT '20' COMMENT '单次抽奖机会达到x次必中奖；多次抽奖为连续未中奖达到x次后必中奖',
  `remark` json NOT NULL COMMENT '抽奖活动描述',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态:0-无效,1-已创建,2-使用中',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='抽会员活动信息';

-- ----------------------------
-- Table structure for activity_user
-- ----------------------------
DROP TABLE IF EXISTS `activity_user`;
CREATE TABLE `activity_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_id` bigint(20) unsigned NOT NULL COMMENT '活动标识',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `loss` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户连续未抽中会员机会次数',
  `success` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '成功抽中次数',
  `duration` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计获得会员天数',
  `last_time` datetime DEFAULT NULL COMMENT '最近一次抽奖时间',
  `last_draw` bigint(20) unsigned DEFAULT NULL COMMENT '最近一次抽奖标识',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_user` (`activity_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1402339706594656333 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='抽会员活动用户信息';

-- ----------------------------
-- Table structure for agent_apply
-- ----------------------------
DROP TABLE IF EXISTS `agent_apply`;
CREATE TABLE `agent_apply` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '用户代理申请状态: 0-取消,1-申请,2-通过,3-未通过',
  `remark` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注信息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `agent_apply_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for agent_income
-- ----------------------------
DROP TABLE IF EXISTS `agent_income`;
CREATE TABLE `agent_income` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '收益流水号',
  `inv_uid` bigint(20) unsigned NOT NULL COMMENT '流量主标识',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '被邀请者标识',
  `amount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收益数量',
  `ratio` double unsigned NOT NULL DEFAULT '0' COMMENT '收益分成比例',
  `channel` tinyint(4) unsigned NOT NULL COMMENT '收益来源渠道',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_income_idx` (`seq_no`),
  KEY `agent_reward_idx` (`inv_uid`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for agent_metrics
-- ----------------------------
DROP TABLE IF EXISTS `agent_metrics`;
CREATE TABLE `agent_metrics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `day` date NOT NULL COMMENT '统计时间',
  `users` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '来源收益用户人数',
  `amount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收益数量',
  `invites` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '邀请人数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_income_idx` (`user_id`,`day`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for agent_rule
-- ----------------------------
DROP TABLE IF EXISTS `agent_rule`;
CREATE TABLE `agent_rule` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `agent` tinyint(3) unsigned NOT NULL COMMENT '代理类型:0-普通,1-一级代理,2-二级代理，3-三级代理',
  `profited` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否分润:0-否,1-是',
  `ratio` double(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '分润比例:0~1.0之间',
  `reward` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '金币收益',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '规则状态:0-无效,1-已创建,2-预启用,3-使用中',
  `start_time` datetime DEFAULT NULL COMMENT '启用时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `agent_rule_idx` (`agent`,`profited`,`ratio`,`reward`,`state`,`start_time`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for agent_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `agent_withdraw`;
CREATE TABLE `agent_withdraw` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现流水号',
  `trans_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付系统流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `withdraw` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现账户金币数量',
  `money` bigint(20) unsigned NOT NULL COMMENT '提现折线人民币数量',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现支付渠道',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提现状态: 0-提现审核未通过,1-发起提现请求,2-提现审核中，3-提现失败,4-提现成功',
  `message` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现备注消息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_withdraw_idx` (`seq_no`),
  KEY `withdraw_idx` (`user_id`,`state`,`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_device
-- ----------------------------
DROP TABLE IF EXISTS `app_device`;
CREATE TABLE `app_device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备标识',
  `brand` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备品牌',
  `manufacturer` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备制造商',
  `sdk_int` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'android api版本',
  `release` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'android版本信息',
  `hardware` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '硬件类型',
  `from_code` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '来源邀请码',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_device_idx` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_launcher
-- ----------------------------
DROP TABLE IF EXISTS `app_launcher`;
CREATE TABLE `app_launcher` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备标识',
  `launch_date` date NOT NULL COMMENT '启动日期',
  `launches` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '启动次数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_launcher_idx` (`device_id`,`launch_date`)
) ENGINE=InnoDB AUTO_INCREMENT=565 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_launcher_log
-- ----------------------------
DROP TABLE IF EXISTS `app_launcher_log`;
CREATE TABLE `app_launcher_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备唯一标识',
  `launch_ip` int(11) unsigned NOT NULL COMMENT '应用启动ip地址',
  `launch_date` date NOT NULL COMMENT '启动日期',
  `launch_time` time NOT NULL COMMENT '启动时间',
  `launch_version` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用启动版本',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_launch_log_idx` (`device_id`,`launch_date`,`launch_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1431 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

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
-- Table structure for expert_acct
-- ----------------------------
DROP TABLE IF EXISTS `expert_acct`;
CREATE TABLE `expert_acct` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键，用户标识',
  `master_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家标识',
  `acct_pwd` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现校验码',
  `income` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '专家收益金币余额',
  `withdraw` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计提现金币收益',
  `with_rmb` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计提现人民币总额',
  `with_latest` date DEFAULT NULL COMMENT '最新提现日期',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '专家用户状态:0-无效,1-申请中,2-通过审核,3-冻结',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_expert_master_idx` (`master_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for expert_income
-- ----------------------------
DROP TABLE IF EXISTS `expert_income`;
CREATE TABLE `expert_income` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '收益流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '专家用户标识',
  `pay_id` bigint(20) NOT NULL COMMENT '支付金币用户标识',
  `period` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '彩票类型',
  `profit` int(11) unsigned NOT NULL COMMENT '金币收益',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_income_idx` (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for expert_metrics
-- ----------------------------
DROP TABLE IF EXISTS `expert_metrics`;
CREATE TABLE `expert_metrics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `day` date NOT NULL COMMENT '统计日期',
  `users` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '付费用户数量',
  `amount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '收益数量',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_expert_metrics_idx` (`user_id`,`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for expert_rule
-- ----------------------------
DROP TABLE IF EXISTS `expert_rule`;
CREATE TABLE `expert_rule` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for expert_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `expert_withdraw`;
CREATE TABLE `expert_withdraw` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现业务流水号',
  `trans_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付系统流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '专家用户标识',
  `withdraw` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现金币数量',
  `money` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现换算后人民币金额',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现支付渠道',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提现状态: 0-提现审核未通过,1-发起提现请求,2-提现审核中，3-提现失败,4-提现成功',
  `message` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现备注描述',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_withdraw_idx` (`seq_no`),
  KEY `withdraw_idx` (`user_id`,`state`,`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pack_info
-- ----------------------------
DROP TABLE IF EXISTS `pack_info`;
CREATE TABLE `pack_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '套餐标识',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '套餐名称',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '套餐备注描述',
  `price` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '套餐价格',
  `discount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '折扣价格',
  `time_unit` tinyint(3) unsigned NOT NULL COMMENT '套餐有效期时间单位:0-周,1-月,2-季度,3-半年,4-年,5-永久',
  `priority` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '套餐优先级：0-一般;1-优先推荐',
  `on_trial` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否为试用套餐: 0-否,1-是',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态：0-无效,1-已创建,2-使用中',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pack_no_idx` (`seq_no`),
  UNIQUE KEY `uk_pack_name_idx` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='套餐内容';

-- ----------------------------
-- Table structure for pack_privilege
-- ----------------------------
DROP TABLE IF EXISTS `pack_privilege`;
CREATE TABLE `pack_privilege` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '套餐名称',
  `icon` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '特权图标:16进制图标',
  `content` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '特权内容',
  `sorted` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序编号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='套餐特权';

-- ----------------------------
-- Table structure for put_channel
-- ----------------------------
DROP TABLE IF EXISTS `put_channel`;
CREATE TABLE `put_channel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `biz_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '渠道标识',
  `type` tinyint(3) unsigned NOT NULL COMMENT '渠道类型:1-微信群,2-qq群,3-百家号,4-抖音,5-快手,6-小红书,7-彩票站,8-其他',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '渠道状态:0-无效,1-已创建,2-使用中',
  `target_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '目标渠道人数，如-qq群人数,微信群人数',
  `third_id` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '第三方标识:qq群标识,微信群标识',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '渠道备注说明',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_channel_idx` (`biz_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='投放渠道信息';

-- ----------------------------
-- Table structure for put_record
-- ----------------------------
DROP TABLE IF EXISTS `put_record`;
CREATE TABLE `put_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '渠道标识',
  `code` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '投放码',
  `inv_uri` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '投放下载链接',
  `expect_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '预期消费金额(单位：分)',
  `user_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计邀请用户数量',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '投放记录状态：0-无效,1-已创建,2-投放中,3-已下线',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '投放备注说明',
  `put_time` datetime DEFAULT NULL COMMENT '投放时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_record_idx` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='渠道投放记录';

-- ----------------------------
-- Table structure for user_balance
-- ----------------------------
DROP TABLE IF EXISTS `user_balance`;
CREATE TABLE `user_balance` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键，用户标识',
  `balance` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '账户浏览广告获得金币',
  `surplus` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '签到、邀请、积分兑换获得的金币等，不可提现',
  `invite` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '邀请累计获得金币奖励',
  `coupon` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '账户签到积分',
  `voucher` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '账户代金券余额',
  `withdraw` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计提现金币数量',
  `with_rmb` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计换算成人民币数量',
  `with_latest` date DEFAULT NULL COMMENT '最近一次提现日期',
  `can_with` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许提现: 0-不允许,1-允许',
  `can_profit` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许分润: 0-不允许,1-允许',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_balance_log
-- ----------------------------
DROP TABLE IF EXISTS `user_balance_log`;
CREATE TABLE `user_balance_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `direct` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '账户余额变动方向:1-增加，2-消耗',
  `balance` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '可提现金币变动',
  `surplus` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '不可提现金币变动',
  `voucher` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '代金券金额变动数量',
  `source` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '余额账户日志变动来源数量(积分、代金券、邀请以及查看预测等)',
  `action` tinyint(4) unsigned NOT NULL COMMENT '变动操作',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态：0-回退,1-正常',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注说明',
  `gmt_create` datetime NOT NULL COMMENT '操作日志',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `balance_log_idx` (`user_id`,`direct`,`balance`,`surplus`)
) ENGINE=InnoDB AUTO_INCREMENT=1250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `device_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_device_idx` (`user_id`,`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `nickname` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户别名',
  `phone` varchar(15) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '账户登录密码',
  `wx_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '微信账户open_id',
  `ali_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付宝账户标识',
  `email` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱',
  `avatar` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '头像',
  `expert` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是 专家：0-否,1-是',
  `channel` tinyint(2) unsigned NOT NULL DEFAULT '3' COMMENT '用户来源渠道:1-彩票站,2-用户邀请,3-直接下载',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态:0-无效,1-正常,2-锁定',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '版本号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_phone_idx` (`phone`),
  KEY `user_gmt_idx` (`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_invite
-- ----------------------------
DROP TABLE IF EXISTS `user_invite`;
CREATE TABLE `user_invite` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键，用户标识',
  `agent` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '代理类型:0-普通,1-一级代理,2-二级代理，3-三级代理',
  `code` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '邀请码',
  `inv_uri` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '邀请链接',
  `invites` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计邀请人数',
  `rewards` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计邀请获得代金券数据量',
  `income` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '流量主账户奖励金余额',
  `user_amt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计收益用户人次',
  `withdraw` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '流量主累计提现奖励金数量',
  `with_rmb` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '流量主累计提现人民币总额',
  `with_latest` date DEFAULT NULL COMMENT '最新提现日期',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态:0-无效,1-正常,2-冻结',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_invite_code_idx` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_invite_log
-- ----------------------------
DROP TABLE IF EXISTS `user_invite_log`;
CREATE TABLE `user_invite_log` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键，被邀请用户标识',
  `inv_uid` bigint(20) unsigned NOT NULL COMMENT '邀请主标识',
  `inv_agent` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '邀请主的流量级别',
  `inv_reward` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '邀请奖励',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  KEY `inv_log_idx` (`inv_uid`,`inv_agent`,`inv_reward`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_login_info
-- ----------------------------
DROP TABLE IF EXISTS `user_login_info`;
CREATE TABLE `user_login_info` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键，用户标识',
  `token_id` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最新一次登录生成token的标识',
  `expire_at` datetime NOT NULL COMMENT '生成token的过期截止时间',
  `login_ip` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '最新一次登录的IP地址',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '当前登录状态:0-退出,1登录',
  `last_ip` int(11) unsigned DEFAULT '0' COMMENT '上一次登录ip地址',
  `last_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` int(11) unsigned NOT NULL COMMENT '登录ip地址',
  `ip_region` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登录ip地区',
  `expire_at` datetime NOT NULL COMMENT '登录到期时间',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  KEY `login_log_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=648 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_member
-- ----------------------------
DROP TABLE IF EXISTS `user_member`;
CREATE TABLE `user_member` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `times` int(11) unsigned NOT NULL COMMENT '成为会员次数',
  `expire_at` datetime NOT NULL COMMENT '会员截止过期时间',
  `last_expire` datetime DEFAULT NULL COMMENT '会员上一次截止过期时间',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT 'vip账户状态: 0-无效,1-正常,2-过期',
  `renew_time` datetime NOT NULL COMMENT '最新续费时间',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_member_log
-- ----------------------------
DROP TABLE IF EXISTS `user_member_log`;
CREATE TABLE `user_member_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '订单编号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `pack_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '套餐标识',
  `pack_name` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '套餐名称',
  `time_unit` tinyint(3) unsigned NOT NULL COMMENT '套餐时间单位',
  `payed` bigint(20) unsigned NOT NULL COMMENT '实际支付金额(单位：分)',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '会员开通类型:1-在线支付,2-手动创建',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付方式',
  `expire_start` datetime NOT NULL COMMENT '有效期开始时间',
  `expire_end` datetime NOT NULL COMMENT '有效期截止时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_reward_log
-- ----------------------------
DROP TABLE IF EXISTS `user_reward_log`;
CREATE TABLE `user_reward_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trans_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '激励视频流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `amount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '奖励金额',
  `bounty` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '非提现金额',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_reward_log_idx` (`trans_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_sign
-- ----------------------------
DROP TABLE IF EXISTS `user_sign`;
CREATE TABLE `user_sign` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '主键，用户标识',
  `series` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '连续签到次数',
  `times` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计签到总次数',
  `coupon` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计签到总积分',
  `last_date` date NOT NULL COMMENT '上一签到日期',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_sign_log
-- ----------------------------
DROP TABLE IF EXISTS `user_sign_log`;
CREATE TABLE `user_sign_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `type` tinyint(2) unsigned NOT NULL COMMENT '签到类型:1-每日签到,2-累计签到',
  `award` int(11) unsigned NOT NULL COMMENT '签到奖励',
  `sign_time` datetime NOT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_statistics
-- ----------------------------
DROP TABLE IF EXISTS `user_statistics`;
CREATE TABLE `user_statistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `day` date NOT NULL COMMENT '统计日期',
  `register` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '日新增注册用户数',
  `active` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '日活量',
  `launch` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '日启动总次数',
  `launch_avg` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '日平均启动次数',
  `invite` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '日邀请人数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_statistics_idx` (`day`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_voucher
-- ----------------------------
DROP TABLE IF EXISTS `user_voucher`;
CREATE TABLE `user_voucher` (
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `all_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计领取代金券总额',
  `used_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计已使用代金券总额',
  `expired_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计过期代金券总额',
  `total` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '领取代金券总数',
  `used` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '已使用数量',
  `expired` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '过期总数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_voucher_log
-- ----------------------------
DROP TABLE IF EXISTS `user_voucher_log`;
CREATE TABLE `user_voucher_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `biz_no` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '领券标识',
  `voucher` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '获取的代金券金额',
  `used` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '已使用的金额',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户代金券状态:0-未使用,1-部分使用,2-全部使用',
  `expired` tinyint(3) unsigned NOT NULL COMMENT '过期标志:0-无过期,1-有效期内,2-已过期计算',
  `expire_at` datetime DEFAULT NULL COMMENT '过期截止时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_voucher_idx` (`user_id`,`biz_no`,`expired`,`state`,`gmt_create`,`voucher`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `user_withdraw`;
CREATE TABLE `user_withdraw` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '记录流水号',
  `trans_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付系统流水号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `withdraw` bigint(20) unsigned NOT NULL COMMENT '提现奖励金数量',
  `money` bigint(20) unsigned NOT NULL COMMENT '提现折现后人民币数量',
  `channel` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现支付渠道',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提现状态:状态: 0-提现审核未通过,1-发起提现请求,2-提现审核中，3-提现失败,4-提现成功',
  `message` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现备注消息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_withdraw_idx` (`seq_no`),
  KEY `user_withdraw_idx` (`user_id`,`state`,`gmt_create`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for voucher_info
-- ----------------------------
DROP TABLE IF EXISTS `voucher_info`;
CREATE TABLE `voucher_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '代金券编号',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '代金券名称',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '代金券备注描述',
  `voucher` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '代金券金额(与金币等值)',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '代金券状态: 0-无效下线,1-已创建,2-投放中,3-已下架',
  `disposable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否为一次性：0-否,1-是',
  `interval` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '非一次性，多久间隔可领取(单位天)',
  `expire` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '代金券有效期(单位：天):0-永久,大于0-有效天',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_voucher_no_idx` (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for withdraw_level
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_level`;
CREATE TABLE `withdraw_level` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scene` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现场景',
  `levels` json NOT NULL COMMENT '提现等级集合',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提现等级状态:0-无效,1-创建待发布,2-已发布',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现等级描述说明',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `withdraw_level_idx` (`scene`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for withdraw_rule
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_rule`;
CREATE TABLE `withdraw_rule` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scene` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现场景标识',
  `throttle` bigint(20) unsigned NOT NULL COMMENT '提现金额门槛',
  `maximum` bigint(20) unsigned NOT NULL COMMENT '每次提现最大金额',
  `interval` int(11) unsigned NOT NULL COMMENT '提现间隔天数',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注描述',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '提现规则状态:0-无效,1-创建待发布,2-预发布,3-使用中',
  `start_time` datetime DEFAULT NULL COMMENT '启用时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `with_rule_idx` (`scene`,`state`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
