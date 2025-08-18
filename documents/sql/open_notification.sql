/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : open_notification

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2025-08-18 10:11:16
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
-- Table structure for mc_announce
-- ----------------------------
DROP TABLE IF EXISTS `mc_announce`;
CREATE TABLE `mc_announce` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '公告标题',
  `content` json NOT NULL COMMENT '公告内容',
  `obj_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '目标对象标识',
  `obj_type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '公告目标对象类型',
  `obj_action` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '公告操作动作',
  `type` tinyint(3) unsigned NOT NULL COMMENT '公告类型:1-text,2-链接,3-卡片',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '公告所属渠道',
  `mode` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '公告创建方式:1-自动,2-手动',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='消息中心-平台通告';

-- ----------------------------
-- Table structure for mc_announce_mailbox
-- ----------------------------
DROP TABLE IF EXISTS `mc_announce_mailbox`;
CREATE TABLE `mc_announce_mailbox` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `announce_id` bigint(20) unsigned NOT NULL COMMENT '最新查阅公告标识',
  `receiver_id` bigint(20) unsigned NOT NULL COMMENT '接收者标识',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '公告渠道标识',
  `latest_read` datetime NOT NULL COMMENT '最新查阅时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_announce_mailbox_idx` (`receiver_id`,`channel`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='消息中心-公告用户收件箱';

-- ----------------------------
-- Table structure for mc_channel
-- ----------------------------
DROP TABLE IF EXISTS `mc_channel`;
CREATE TABLE `mc_channel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '渠道名称',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '渠道编码值',
  `cover` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '渠道header图片',
  `remark` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '渠道说明',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '渠道类型: 0-公告,1-提醒',
  `scope` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '渠道可见范围: 0-全部,1-用户,2-专家,3-代理商',
  `remind` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否开启客户端红点提醒: 0-否,1-是',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '渠道状态: 0-无效,1-已创建,2-使用中',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_channel_idx` (`channel`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='消息中心-渠道信息';

-- ----------------------------
-- Table structure for mc_remind
-- ----------------------------
DROP TABLE IF EXISTS `mc_remind`;
CREATE TABLE `mc_remind` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receiver_id` bigint(20) unsigned NOT NULL COMMENT '接收者标识',
  `from_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '发送者标识',
  `from_name` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '发送者名称',
  `obj_id` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '目标对象标识',
  `obj_type` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '目标对象类型',
  `obj_action` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '发送者动作',
  `title` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提醒内容标识',
  `content` json NOT NULL COMMENT '提醒内容',
  `type` tinyint(3) unsigned NOT NULL COMMENT '消息类型:1-text,2-link,3-card',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '消息所属渠道',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for mc_remind_mailbox
-- ----------------------------
DROP TABLE IF EXISTS `mc_remind_mailbox`;
CREATE TABLE `mc_remind_mailbox` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receiver_id` bigint(20) unsigned NOT NULL COMMENT '接收者标识',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提醒渠道信息',
  `remind_id` bigint(20) unsigned NOT NULL COMMENT '提醒消息id',
  `latest_read` datetime NOT NULL COMMENT '最新查询时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_remind_mailbox_idx` (`receiver_id`,`channel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='消息中心-提醒用户收件箱';

-- ----------------------------
-- Table structure for metrics_device
-- ----------------------------
DROP TABLE IF EXISTS `metrics_device`;
CREATE TABLE `metrics_device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `devices` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '设备总数量数量',
  `increases` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '新增设备数量',
  `metrics_date` date NOT NULL COMMENT '统计日期',
  `latest_time` datetime NOT NULL COMMENT '最新一次统计时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_metrics_device_idx` (`app_key`,`metrics_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for metrics_notify
-- ----------------------------
DROP TABLE IF EXISTS `metrics_notify`;
CREATE TABLE `metrics_notify` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `sent` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '服务端实际发出数量',
  `accept` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '服务端接收到推送条目数',
  `receive` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '实际接受设备数量',
  `opened` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '通知在设备上点击数量',
  `deleted` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '通知在设备上清除数量',
  `metrics_date` date NOT NULL COMMENT '统计日期',
  `latest_time` datetime NOT NULL COMMENT '最新一次更新时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_metrics_notify_idx` (`app_key`,`metrics_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_alias
-- ----------------------------
DROP TABLE IF EXISTS `notify_alias`;
CREATE TABLE `notify_alias` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_alias_bind
-- ----------------------------
DROP TABLE IF EXISTS `notify_alias_bind`;
CREATE TABLE `notify_alias_bind` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_app
-- ----------------------------
DROP TABLE IF EXISTS `notify_app`;
CREATE TABLE `notify_app` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `app_name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用名称',
  `app_key` bigint(20) NOT NULL COMMENT '阿里云应用key',
  `platform` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用所述平台:IOS;ANDROID',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用备注描述信息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_no_idx` (`app_no`),
  UNIQUE KEY `uk_app_key_idx` (`app_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_device
-- ----------------------------
DROP TABLE IF EXISTS `notify_device`;
CREATE TABLE `notify_device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `device_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备标识',
  `device_type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备类型:IOS,ANDROID',
  `enable` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许推送: 0-否,1-是',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户标识',
  `phone` varchar(11) COLLATE utf8mb4_bin DEFAULT '' COMMENT '绑定手机号码',
  `online_time` datetime DEFAULT NULL COMMENT '上一次在线状态',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_device_id_idx` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_info
-- ----------------------------
DROP TABLE IF EXISTS `notify_info`;
CREATE TABLE `notify_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `group_id` bigint(20) unsigned NOT NULL COMMENT '标签分组',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '推送类型:1-消息,2-通知',
  `hour` int(11) unsigned NOT NULL COMMENT '指定每天指定小时推送',
  `title` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '推送标题',
  `body` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '推送内容',
  `notice` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提醒方式:0-NONE,1-BOTH(震动和声音),2-VIBRATE(震动),3-SOUND(声音)',
  `bar_type` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '通知栏样式:1-100',
  `bar_priority` tinyint(3) NOT NULL DEFAULT '2' COMMENT '通知栏排序优先级:-2,-1,0,1,2',
  `ext_params` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '通知扩展属性：map json格式',
  `open_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '提醒打开方式: 0-NONE（无跳转）,1-APPLICAITON(应用) ,2-ACTIVITY,3-URL打开链接',
  `open_url` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '打开方式为url时，跳转的url',
  `open_activity` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '打开方式为Activity时，Activity的类全路径',
  `channel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '提醒通道:j解决android8+收不到提醒',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '推送内容状态:0-无效,1-已创建,2-使用中',
  `online` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否只推送在线设备:1-是,2-否（离线保存，下次上线会收到）',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '推送备注描述说明',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_tag
-- ----------------------------
DROP TABLE IF EXISTS `notify_tag`;
CREATE TABLE `notify_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `group_id` tinyint(20) unsigned NOT NULL COMMENT '分组标识',
  `tag_name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'tag名称',
  `binds` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '当前标签已绑定设备数量',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_idx` (`app_key`,`group_id`,`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_tag_bind
-- ----------------------------
DROP TABLE IF EXISTS `notify_tag_bind`;
CREATE TABLE `notify_tag_bind` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `group_id` bigint(20) unsigned NOT NULL COMMENT '标签分组标识',
  `tag_id` bigint(20) unsigned NOT NULL COMMENT 'tag标识',
  `device_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_bind_idx` (`app_key`,`group_id`,`device_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_tag_group
-- ----------------------------
DROP TABLE IF EXISTS `notify_tag_group`;
CREATE TABLE `notify_tag_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标签分组名称',
  `tag_prefix` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '标签名称前缀',
  `binds` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '绑定总设备数',
  `tags` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '分组下标签数量',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注描述信息',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '标签分组状态:0-无效,1-正常,2-使用中',
  `upper_bound` int(11) unsigned NOT NULL DEFAULT '50000' COMMENT '标签分组下单个标签绑定设备上限：50000',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_group_idx` (`app_key`,`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for notify_task
-- ----------------------------
DROP TABLE IF EXISTS `notify_task`;
CREATE TABLE `notify_task` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_key` bigint(20) unsigned NOT NULL COMMENT '应用key',
  `group_id` bigint(20) unsigned NOT NULL COMMENT '分组标识',
  `notify_id` bigint(20) unsigned NOT NULL COMMENT '推送信息id',
  `platform` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '待推送任务指定平台:IOS,Android',
  `tags` int(11) unsigned NOT NULL COMMENT '推送标签数量',
  `tag_list` mediumtext COLLATE utf8mb4_bin NOT NULL COMMENT '推送标签值集合,逗号分隔各个标签',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '推送状态:0-发往推送服务失败,1-已创建发往push server，2-waiting等待中，3-sent 已推送，4-canceled已取消',
  `expect_time` datetime NOT NULL COMMENT '预计推送时间',
  `push_time` datetime DEFAULT NULL COMMENT '实际推送时间',
  `message_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '推送服务器后返回的消息标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
