/*
Navicat MySQL Data Transfer

Source Server         : mysql5.7_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : open_operate

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2025-08-18 10:11:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '管理员账户名称',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '管理员登录密码',
  `phone` varchar(15) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '管理员登录手机号',
  `level` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT '管理员级别:1-超级管理员,2-一般管理员',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '管理员状态:0-无效,1-正常,2-冻结',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_adm_name_dx` (`name`),
  UNIQUE KEY `uk_adm_phone_idx` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for admin_login_info
-- ----------------------------
DROP TABLE IF EXISTS `admin_login_info`;
CREATE TABLE `admin_login_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_id` bigint(20) unsigned NOT NULL COMMENT '管理员标识',
  `token_id` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最新一次登录token标识',
  `expire_at` datetime NOT NULL COMMENT '登录token有效截止时间',
  `login_ip` int(11) unsigned NOT NULL COMMENT '登录ip地址',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登录状态:0-退出,1-登录',
  `last_ip` int(11) unsigned DEFAULT NULL COMMENT '上一次登录ip地址',
  `last_time` datetime DEFAULT NULL COMMENT '上一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_admin_login_idx` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_login_log`;
CREATE TABLE `admin_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_id` bigint(20) unsigned NOT NULL COMMENT '用户标识',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` int(11) unsigned NOT NULL COMMENT '登录ip地址',
  `ip_region` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登录ip地区',
  `expire_at` datetime NOT NULL COMMENT '登录截止过期时间',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  KEY `login_log_idx` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_assistant
-- ----------------------------
DROP TABLE IF EXISTS `app_assistant`;
CREATE TABLE `app_assistant` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `suit_ver` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '适用版本号',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '助手问题分类',
  `title` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '助手标题',
  `content` mediumtext COLLATE utf8mb4_bin NOT NULL COMMENT '帮助助手内容',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '助手问题状态:0-无效,1-创建待发布,2-已发布',
  `sort` int(11) unsigned NOT NULL COMMENT '排序标识',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注描述信息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_assistant_idx` (`title`),
  KEY `app_assistant_idx` (`app_no`,`suit_ver`,`type`,`state`,`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_banner
-- ----------------------------
DROP TABLE IF EXISTS `app_banner`;
CREATE TABLE `app_banner` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `page` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '所属页面',
  `banner` json NOT NULL COMMENT 'banner集合',
  `state` tinyint(3) unsigned NOT NULL COMMENT 'banner状态:0-无效,1-已创建待使用,2-使用中',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT 'banner备注描述',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_banner_idx` (`app_no`,`page`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='app页面banner配置信息';

-- ----------------------------
-- Table structure for app_comment
-- ----------------------------
DROP TABLE IF EXISTS `app_comment`;
CREATE TABLE `app_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '评论用户名称',
  `avatar` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '评论用户头像',
  `rate` tinyint(3) unsigned NOT NULL DEFAULT '5' COMMENT '应用评分:1-比较一般，2-一般，3-不错，4-优秀，5-非常好',
  `comment` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用评论内容',
  `cmt_time` datetime NOT NULL COMMENT '评论时间',
  `gmt_create` datetime NOT NULL COMMENT '评论创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_comment_idx` (`app_no`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_conf
-- ----------------------------
DROP TABLE IF EXISTS `app_conf`;
CREATE TABLE `app_conf` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `conf_key` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '配置key',
  `conf_val` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '配置值',
  `val_type` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '配置值类型:1-string,2-int,3-long,4-double',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '配置描述备注',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '配置状态:0-无效,1-创建,2-使用',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_conf_idx` (`app_no`,`conf_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_contact
-- ----------------------------
DROP TABLE IF EXISTS `app_contact`;
CREATE TABLE `app_contact` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '联系人名称',
  `qr_img` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '联系人微信二维码',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '说明备注',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态:0-无效,1-创建,2-使用中',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_contact_name` (`name`,`app_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_feedback
-- ----------------------------
DROP TABLE IF EXISTS `app_feedback`;
CREATE TABLE `app_feedback` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `app_version` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用版本信息',
  `device` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用设备信息',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '反馈类型',
  `content` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '反馈内容',
  `images` json DEFAULT NULL COMMENT '反馈图片集合',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '处理状态:0-关闭,1-创建,2-已处理,3-增强改进',
  `remark` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '后台备注',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `app_feedback_idx` (`app_no`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seq_no` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '应用编号',
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '应用名称',
  `logo` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '应用logo图片',
  `share_uri` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用分享链接',
  `open_install` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'open install分享key',
  `copyright` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '应用版权信息',
  `corp` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '应用公司名称',
  `telephone` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '联系电话',
  `address` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '地址信息',
  `record` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '备案信息',
  `remark` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '备注信息',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_seq_idx` (`seq_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_resource
-- ----------------------------
DROP TABLE IF EXISTS `app_resource`;
CREATE TABLE `app_resource` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '资源所属应用标识',
  `fe_no` varchar(30) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '前端标识',
  `name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '资源名称',
  `type` tinyint(2) unsigned NOT NULL COMMENT '资源类型:1-图标,2-背景图,3-其他',
  `uri` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最新使用的图片资源',
  `last_uri` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '上一期使用的图片资源(用于回滚)',
  `def_uri` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '默认图片资源uri',
  `specs` json NOT NULL COMMENT '图片规格尺寸',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '资源状态:0-已废弃,1-未上架,2-已上架',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '资源描述',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_resource_idx` (`app_no`,`fe_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='APP端资源配置';

-- ----------------------------
-- Table structure for app_verify
-- ----------------------------
DROP TABLE IF EXISTS `app_verify`;
CREATE TABLE `app_verify` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用标识',
  `app_pack` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用包名',
  `signature` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用一键登录包名签名',
  `auth_key` varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '移动一键登录密钥',
  `state` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '配置状态:0-无效,1-创建,2-使用中',
  `success` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '授权成功响应码',
  `cancel` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '授权取消响应码',
  `downgrades` varchar(300) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '降级错误码集合，用“,”分隔',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注说明',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_auth_idx` (`app_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'APP标识',
  `app_ver` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT 'app版本',
  `depiction` varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用描述信息',
  `upgrades` json NOT NULL COMMENT '版本升级描述',
  `images` json NOT NULL COMMENT '应用图片',
  `app_dir` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'app包oss存放文件路径',
  `app_unity` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '全量包名',
  `app_v7a` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'abi_v7a包名',
  `app_v8a` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'abi_v8a包名',
  `force` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '版本是否强制升级: 0-否,1-是',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '版本状态:0-下线,1-预发，2-上线,3-主推',
  `online` datetime DEFAULT NULL COMMENT '上线时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_version_idx` (`app_no`,`app_ver`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for feedback_type
-- ----------------------------
DROP TABLE IF EXISTS `feedback_type`;
CREATE TABLE `feedback_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_no` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用编号',
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '反馈类型',
  `suit_ver` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '使用最低版本',
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '说明描述信息',
  `sort` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序标识',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_feedback_type_idx` (`app_no`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='应用反馈类型';

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for module_info
-- ----------------------------
DROP TABLE IF EXISTS `module_info`;
CREATE TABLE `module_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for oss_store
-- ----------------------------
DROP TABLE IF EXISTS `oss_store`;
CREATE TABLE `oss_store` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
