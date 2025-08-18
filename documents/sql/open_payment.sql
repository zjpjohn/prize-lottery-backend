/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : open_payment

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2025-08-18 10:11:47
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
-- Table structure for charge_config
-- ----------------------------
DROP TABLE IF EXISTS `charge_config`;
CREATE TABLE `charge_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '充值金额名称',
  `amount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '充值金额',
  `gift` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '赠送金额',
  `priority` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '配置状态: 0-无效,1-已创建,2-使用中',
  `version` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '数据版本状态',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_no` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `type` int(11) unsigned NOT NULL COMMENT '订单类型:1-会员订单,2-充值订单',
  `std_price` bigint(20) NOT NULL COMMENT '标准价格',
  `real_price` bigint(20) unsigned NOT NULL COMMENT '实际价格',
  `quantity` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '商品数量',
  `amount` bigint(20) unsigned NOT NULL COMMENT '实际支付金额',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '支付渠道',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '订单描述/备注',
  `attach` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '订单附加信息',
  `content` json NOT NULL COMMENT '订单商品信息',
  `state` tinyint(4) unsigned NOT NULL COMMENT '订单状态:0-支付失败,1-已创建待支付，2-支付完，3-订单关闭',
  `settled` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '订单是否结算:0-否,1-是',
  `expire_time` datetime NOT NULL COMMENT '到期截止时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `close_time` datetime DEFAULT NULL COMMENT '关闭时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_uniq_idx` (`biz_no`) USING BTREE COMMENT '订单唯一索引',
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单信息';

-- ----------------------------
-- Table structure for order_statistics
-- ----------------------------
DROP TABLE IF EXISTS `order_statistics`;
CREATE TABLE `order_statistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `day` date NOT NULL COMMENT '统计日期',
  `success_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '支付成功总金额',
  `success_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支付成功总单数',
  `failure_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '支付失败总金额',
  `failure_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支付失败总单数',
  `closed_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '已关闭订单总金额',
  `closed_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '已关闭订单总数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_statistics` (`day`)
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
-- Table structure for pack_statistics
-- ----------------------------
DROP TABLE IF EXISTS `pack_statistics`;
CREATE TABLE `pack_statistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `day` date NOT NULL COMMENT '统计日期',
  `pack_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '套餐编号',
  `success_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '套餐支付成功总金额',
  `success_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支付成功套餐订单总单数',
  `closed_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '关闭套餐订单总金额',
  `closed_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '关闭套餐订单总单数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pack_statistics` (`day`,`pack_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for pay_channel
-- ----------------------------
DROP TABLE IF EXISTS `pay_channel`;
CREATE TABLE `pay_channel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '支付唯一编码',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '支付名称',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '支付渠道',
  `cover` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '图标图片',
  `icon` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '图标font值',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '支付渠道备注信息',
  `priority` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否优先使用：0-否,1-是',
  `pay` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否支付可用：0-不可用；1-可用',
  `withdraw` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否提现可用：0-不可用；1-可用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pay_config_idx` (`seq`),
  UNIQUE KEY `uk_pay_channel_idx` (`channel`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for transfer_audit
-- ----------------------------
DROP TABLE IF EXISTS `transfer_audit`;
CREATE TABLE `transfer_audit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `audit_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '审核单号',
  `trans_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现标识-提现编号',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提现申请状态: 1-处理中,2-审核通过,3-审核拒绝',
  `step` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '审核步骤：1-开始，2-提请上级审核,3-审核结束',
  `reason` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '审核备注原因',
  `audit_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '审核人员标识：0-系统，非0-操作员',
  `auditor` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '审核员名称',
  `last_no` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '上一次审核单号',
  `next_audit` bigint(20) unsigned DEFAULT NULL COMMENT '下一级审批员表示',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_audit_no_idx` (`audit_no`),
  KEY `audit_trans_no_idx` (`trans_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='提现申请';

-- ----------------------------
-- Table structure for transfer_batch
-- ----------------------------
DROP TABLE IF EXISTS `transfer_batch`;
CREATE TABLE `transfer_batch` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '发起转账的业务单号',
  `batch_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '批量标号',
  `batch_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '批量名称',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '批量备注说明',
  `scene` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '批量提现场景',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现通道',
  `total` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现总金额(单位-分)',
  `total_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '批量总条目数',
  `state` tinyint(3) unsigned NOT NULL COMMENT '批量提现状态:',
  `audit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '转账审核状态：0-不需要审核,1-审核中,2-审核通过,3-审核失败',
  `close_reason` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '转账批次关闭原因',
  `oper_id` bigint(20) unsigned NOT NULL COMMENT '批量创建操作员标识',
  `oper_type` tinyint(2) unsigned NOT NULL COMMENT '操作人员类型:1-用户,2-系统管理者',
  `success_amount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '成功总金额',
  `success_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '成功总条目数',
  `fail_amount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '失败总金额数',
  `fail_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '失败总条目数',
  `latest_time` datetime NOT NULL COMMENT '最新第三方变化时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trans_batch_idx` (`batch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for transfer_record
-- ----------------------------
DROP TABLE IF EXISTS `transfer_record`;
CREATE TABLE `transfer_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '发起提现的业务单号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `open_id` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '支付账户标识',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '提现通道',
  `scene` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现场景值',
  `batch_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '批量提现编号',
  `trans_no` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现编号',
  `amount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现金额(单位-分)',
  `state` tinyint(3) unsigned NOT NULL COMMENT '提现状态：1-待确认,2-处理中；3-成功；4-失败',
  `audit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '转账审核状态：0-不需要审核,1-审核中,2-审核通过,3-审核失败',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现说明',
  `fail_reason` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '失败时失败原因',
  `latest_time` datetime NOT NULL COMMENT '第三方服务变化时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for transfer_rule
-- ----------------------------
DROP TABLE IF EXISTS `transfer_rule`;
CREATE TABLE `transfer_rule` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scene` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现规则适用场景',
  `throttle` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '当非强制审核是，审核提现金额门槛',
  `force` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否强制审核：0-否,1-是',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提现规则状态: 0-无效,1-已创建,2-使用中',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提现规则备注描述',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_scene_rule_idx` (`scene`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for transfer_statistics
-- ----------------------------
DROP TABLE IF EXISTS `transfer_statistics`;
CREATE TABLE `transfer_statistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `day` date NOT NULL COMMENT '统计日期',
  `success_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现成功总金额',
  `success_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '提现成功总单数',
  `failure_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '提现失败总金额',
  `failure_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '提现失败总单数',
  `rejected_amt` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '审核未通过总金额',
  `rejected_cnt` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '审核未通过总单数',
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
