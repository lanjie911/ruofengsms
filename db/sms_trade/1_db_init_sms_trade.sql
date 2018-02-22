/*
Navicat MySQL Data Transfer

Source Server         : 118.190.153.242
Source Server Version : 50719
Source Host           : 118.190.153.242:3306
Source Database       : sms_trade

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-11-28 15:55:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_auditing
-- ----------------------------
DROP TABLE IF EXISTS `s_auditing`;
CREATE TABLE `s_auditing` (
  `auditing_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户账户编号',
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 普通账户\r\n            300 - 全类型商户',
  `order_flag` int(5) DEFAULT NULL COMMENT '预约标识rn            100 -是           200 - 否',
  `reservation_datetime` datetime DEFAULT NULL COMMENT '预约发送时间',
  `batch_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '批次号',
  `mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `sms_content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '短信内容',
  `upload_content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '文件上传内容',
  `sms_count` int(5) DEFAULT NULL COMMENT '短信内容字数计数',
  `sms_unit_count` int(3) DEFAULT NULL COMMENT '短信内容计数单价\r\n            例如：70字为一条；超过70个字按照68个字算；',
  `sms_account_num` int(3) DEFAULT NULL COMMENT '短信内容核算条数',
  `auditing_status` int(5) DEFAULT NULL COMMENT '状态\r\n            100 - 待审核\r\n            200 - 审核通过\r\n            201 - 发送处理中\r\n            202 - 发送成功\r\n            203 - 发送失败\r\n            300 - 审核拒绝',
  `auditing_operator` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `registrars` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '录入操作员',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `auditing_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `sign_tip` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '签名',
  `cost_tip` int(11) DEFAULT NULL COMMENT '扣量标识 100-发送 200-不发送',
  PRIMARY KEY (`auditing_id`),
  KEY `FK_Reference_9` (`account_no`),
  KEY `index_auditing_batch_no` (`batch_no`),
  KEY `index_auditing_account_no` (`account_no`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`account_no`) REFERENCES `s_merchant_account` (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信发送审核';

-- ----------------------------
-- Table structure for s_channel
-- ----------------------------
DROP TABLE IF EXISTS `s_channel`;
CREATE TABLE `s_channel` (
  `channel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通道编号',
  `channel_name` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '通道名称',
  `channel_code` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '通道编码',
  `support_operators` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '支持运营商\r\n            100 - 电信  \r\n            200 - 联通  \r\n            300 - 移动  \r\n            400 - 全网通 \r\n            500-第三方 \r\n            600-其他',
  `channel_status` int(5) DEFAULT NULL COMMENT '通道状态\r\n            100 - 使用\r\n            200 - 禁用',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `channel_type` int(5) DEFAULT NULL COMMENT '通道类型\r\n            100-短信\r\n            200-彩信\r\n            300-语音',
  `pay_method` int(5) DEFAULT NULL COMMENT '付费方式\r\n            100-预付费\r\n            200-后付费',
  `unit_price` decimal(15,2) DEFAULT NULL COMMENT '单价',
  `priority_level` int(6) DEFAULT NULL COMMENT '优先级',
  `billing_wordsize` int(5) DEFAULT NULL COMMENT '计费字数',
  `support_longsms_flag` int(5) DEFAULT NULL COMMENT '是否支持长短信\r\n            100-是\r\n            200-否',
  `component_size` int(5) DEFAULT NULL COMMENT '分量包',
  `flow_size` int(5) DEFAULT NULL COMMENT '流量',
  `from_telephone` int(15) DEFAULT NULL COMMENT '下发号码',
  `flow_size_oneday` int(15) DEFAULT NULL COMMENT '日流量限制',
  `max_send_size` int(15) DEFAULT NULL COMMENT '最大并发数',
  `channel_attribute` int(5) DEFAULT NULL COMMENT '通道属性',
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信通道';

-- ----------------------------
-- Table structure for s_convince_ip
-- ----------------------------
DROP TABLE IF EXISTS `s_convince_ip`;
CREATE TABLE `s_convince_ip` (
  `ip_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `ip_addr` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`ip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='授信IP列表';

-- ----------------------------
-- Table structure for s_count_record
-- ----------------------------
DROP TABLE IF EXISTS `s_count_record`;
CREATE TABLE `s_count_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ref_id` bigint(20) DEFAULT NULL COMMENT '关联id',
  `column_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '报表显示列名',
  `column_value` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '报表显示列值(y)',
  `column_time` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '报表显示区间（x）',
  `column_type` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `data_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '数据类型',
  `categories` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'x轴显示',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for s_count_record_new
-- ----------------------------
DROP TABLE IF EXISTS `s_count_record_new`;
CREATE TABLE `s_count_record_new` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ref_id` bigint(20) DEFAULT NULL COMMENT '关联id',
  `sum_count` int(11) DEFAULT NULL COMMENT '总量',
  `suc_count` int(11) DEFAULT NULL COMMENT '成功量',
  `fail_count` int(11) DEFAULT NULL COMMENT '失败量',
  `column_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '图像类型',
  `column_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '列名',
  `count_time` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1668955 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for s_customer_manager
-- ----------------------------
DROP TABLE IF EXISTS `s_customer_manager`;
CREATE TABLE `s_customer_manager` (
  `cus_manager_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cus_manager_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理姓名',
  `cus_manager_mobile` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理手机号',
  `cus_manager_email` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '客户经理邮箱',
  PRIMARY KEY (`cus_manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for s_deposit
-- ----------------------------
DROP TABLE IF EXISTS `s_deposit`;
CREATE TABLE `s_deposit` (
  `deposit_id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户账户编号',
  `amount` decimal(15,2) DEFAULT NULL COMMENT '充值金额 单位：元',
  `deposit_num` bigint(20) DEFAULT NULL COMMENT '短信充值数量 单位：条',
  `unit_price` decimal(15,2) DEFAULT NULL COMMENT '充值短信单价 单位：元/条',
  `deposit_type` int(5) DEFAULT NULL COMMENT '充值类型\r\n            100 - 手动充值\r\n            200 - 调账',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `deposit_status` int(5) DEFAULT NULL COMMENT '充值状态\r\n            100 - 成功\r\n            200 - 失败',
  `remark` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `operator` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '操作员',
  `out_recharge_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '外部订单号',
  PRIMARY KEY (`deposit_id`),
  KEY `FK_Reference_4` (`account_no`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`account_no`) REFERENCES `s_merchant_account` (`account_no`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='充值记录';

-- ----------------------------
-- Table structure for s_merc_cus_manger
-- ----------------------------
DROP TABLE IF EXISTS `s_merc_cus_manger`;
CREATE TABLE `s_merc_cus_manger` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merc_id` bigint(20) DEFAULT NULL COMMENT '商户id',
  `cus_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理id',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商户客户经理关系表';

-- ----------------------------
-- Table structure for s_mercaccount_channel
-- ----------------------------
DROP TABLE IF EXISTS `s_mercaccount_channel`;
CREATE TABLE `s_mercaccount_channel` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merc_account_no` bigint(20) DEFAULT NULL COMMENT '商户账户号',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '通道编号',
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for s_merchant_account
-- ----------------------------
DROP TABLE IF EXISTS `s_merchant_account`;
CREATE TABLE `s_merchant_account` (
  `account_no` bigint(20) NOT NULL COMMENT '商户账户编号',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户',
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 普通账户\r\n            300 - 全类型商户',
  `payment_methods` int(5) DEFAULT NULL COMMENT '付费方式\r\n            100 - 预付费\r\n            200 - 后付费',
  `extend_no` int(6) DEFAULT NULL COMMENT '自定义扩展号码',
  `charging_methods` int(5) DEFAULT NULL COMMENT '扣费方式\r\n            100 - 提交量\r\n            200 - 成功量',
  `unit_price` decimal(15,2) DEFAULT NULL COMMENT '短信单价：元',
  `priority_level` int(6) DEFAULT NULL COMMENT '优先级别\r\n            必须为数字,可为负数,数字越多越高',
  `total_balance` bigint(20) DEFAULT '0' COMMENT '账户总额 单位：条rn            total_balance = free_balance + frozen_balance',
  `free_balance` bigint(20) DEFAULT '0' COMMENT '账户可用额 单位：条',
  `frozen_balance` bigint(20) DEFAULT '0' COMMENT '冻结金额 单位：条',
  `start_blacklist_flag` int(5) DEFAULT NULL COMMENT '是否启用黑名单\r\n            100 - 是\r\n            200 - 否',
  `send_audit_flag` int(5) DEFAULT NULL COMMENT '是否发送审核\r\n            100 - 是\r\n            200 - 否',
  `fail_to_reissue_flag` int(5) DEFAULT NULL COMMENT '是否失败补发\r\n            100 - 是\r\n            200 - 否',
  `sms_group_id` bigint(20) DEFAULT NULL COMMENT '短信通道组',
  `sms_group_desc` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '短信通道组描述',
  `signature_content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '签名内容',
  `binding_ip_flag` int(5) DEFAULT NULL COMMENT '是否绑定IP\r\n            100 - 是\r\n            200 - 否',
  `authorization_ip` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '授信请求IP',
  `remark` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `account_status` int(5) DEFAULT NULL COMMENT '状态  100 - 正常   200 - 停用   300 - 注销    400-冻结       500 - 欠费',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '操作员',
  `initial_valid_smscount` bigint(20) DEFAULT NULL COMMENT '初始可用条数',
  `onetime_min_limit` int(5) DEFAULT NULL COMMENT '单次提交最小限制',
  `onetime_max_limit` int(11) DEFAULT '10' COMMENT '单次提交最大限制',
  `valid_sign_method` int(5) DEFAULT NULL COMMENT '是否校验签名',
  `sense_word_flag` int(5) DEFAULT NULL COMMENT '是否进行敏感字校验',
  `template_match_flag` int(5) DEFAULT NULL COMMENT '是否进行模板匹配',
  `cost_quantity` decimal(6,2) DEFAULT NULL,
  `send_num_hours` int(8) DEFAULT NULL,
  `send_num_days` int(8) DEFAULT NULL,
  `cus_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理编号',
  `account_no_password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '账户密码',
  `password_salt` int(10) NOT NULL COMMENT '密码掩码',
  `account_pwd_clear_text` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`account_no`),
  KEY `FK_Reference_10` (`merchant_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`merchant_id`) REFERENCES `s_merchant_info` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商户资金账户';

-- ----------------------------
-- Table structure for s_merchant_account_ls
-- ----------------------------
DROP TABLE IF EXISTS `s_merchant_account_ls`;
CREATE TABLE `s_merchant_account_ls` (
  `ls_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '流水编号',
  `trans_type` int(11) DEFAULT NULL COMMENT '交易类型\r\n            100 - 冻结\r\n            200 - 解冻\r\n            300 - 冲正',
  `account_no` bigint(20) DEFAULT NULL COMMENT '账户编号',
  `amount` int(11) DEFAULT '0' COMMENT '单位：条数',
  `mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ls_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1862547 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账户流水表';

-- ----------------------------
-- Table structure for s_merchant_contacts
-- ----------------------------
DROP TABLE IF EXISTS `s_merchant_contacts`;
CREATE TABLE `s_merchant_contacts` (
  `merc_contacts_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merc_info_id` int(16) DEFAULT NULL COMMENT '联系人基本信息id',
  `contacts_name` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人姓名',
  `contact_address` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `telephone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `contact_qq` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '联系QQ',
  `fax` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `company_website` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '企业网址',
  `contact_mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人手机号',
  `email` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  PRIMARY KEY (`merc_contacts_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商户联系人相关信息';

-- ----------------------------
-- Table structure for s_merchant_info
-- ----------------------------
DROP TABLE IF EXISTS `s_merchant_info`;
CREATE TABLE `s_merchant_info` (
  `merchant_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商户',
  `merchant_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商户名称',
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `operating_address` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '经营地址',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `operator` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '操作员',
  `merchant_status` int(5) DEFAULT NULL COMMENT '商户状态 100 - 正常  200 - 停用  300 - 注销  400 - 冻结',
  `merchant_nature` int(5) DEFAULT NULL COMMENT '企业性质 100 - 法人企业  200 - 非法人企业  300 - 事业单位  400 - 个体工商户  500-其他',
  `operating_address_province` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地址所在省份',
  `operating_address_city` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地址所在城市',
  `operating_address_area` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地址所在区县',
  PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商户信息表';

-- ----------------------------
-- Table structure for s_phone
-- ----------------------------
DROP TABLE IF EXISTS `s_phone`;
CREATE TABLE `s_phone` (
  `pref` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8_bin NOT NULL,
  `province` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `isp` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `post_code` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `city_code` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `area_code` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `types` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for s_plain_send_record
-- ----------------------------
DROP TABLE IF EXISTS `s_plain_send_record`;
CREATE TABLE `s_plain_send_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `channel_id` int(11) DEFAULT NULL COMMENT '通道编号',
  `channel_name` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '通道名称',
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户账户编号',
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 普通账户\r\n            300 - 全类型商户',
  `failed_retransmission` int(5) DEFAULT NULL COMMENT '是否失败重发\r\n            100 - 是\r\n            200 - 否',
  `mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '短信内容',
  `send_status` int(5) DEFAULT NULL COMMENT '发送状态\r\n            100 - 待处理\r\n            101 - 处理中\r\n            200 - 发送成功\r\n            300 - 发送失败\r\n            ',
  `send_msg` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发送结果说明',
  `failed_num` int(5) DEFAULT NULL COMMENT '失败计数',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `resp_datetime` datetime DEFAULT NULL COMMENT '响应时间',
  `sum_num` int(5) DEFAULT '0' COMMENT '本批次发送总条数',
  `success_num` int(10) DEFAULT '0' COMMENT '成功计数',
  `blacklist_num` int(10) DEFAULT '0' COMMENT '黑名单计数',
  `message_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `sign_tip` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `reservation_datetime` datetime DEFAULT NULL,
  `province` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `isp` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `req_msg_id` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `merc_req_time` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  `batch_no` bigint(20) DEFAULT NULL COMMENT '批次号',
  PRIMARY KEY (`record_id`),
  KEY `FK_Reference_5` (`account_no`),
  KEY `FK_Reference_6` (`channel_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`account_no`) REFERENCES `s_merchant_account` (`account_no`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`channel_id`) REFERENCES `s_channel` (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4423767 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='普通发送记录';

-- ----------------------------
-- Table structure for s_reservation_send_record
-- ----------------------------
DROP TABLE IF EXISTS `s_reservation_send_record`;
CREATE TABLE `s_reservation_send_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `channel_id` int(11) DEFAULT NULL COMMENT '通道编号',
  `channel_name` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '通道名称',
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户账户编号',
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 普通账户\r\n            300 - 全类型商户',
  `failed_retransmission` int(5) DEFAULT NULL COMMENT '是否失败重发\r\n            100 - 是\r\n            200 - 否',
  `mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '短信内容',
  `reservation_datetime` datetime DEFAULT NULL COMMENT '预约发送时间',
  `send_status` int(5) DEFAULT NULL COMMENT '发送状态\r\n            100 - 待处理\r\n            101 - 处理中\r\n            200 - 发送成功\r\n            300 - 发送失败\r\n            ',
  `send_msg` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '发送结果说明',
  `failed_num` int(5) DEFAULT NULL COMMENT '失败计数',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `resp_datetime` datetime DEFAULT NULL COMMENT '响应时间',
  `sum_num` int(10) DEFAULT '0' COMMENT '发送总数',
  `success_num` int(10) DEFAULT '0' COMMENT '成功计数',
  `blacklist_num` int(10) DEFAULT '0' COMMENT '黑名单基数',
  `message_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `sign_tip` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`record_id`),
  KEY `FK_Reference_7` (`channel_id`),
  KEY `FK_Reference_8` (`account_no`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`channel_id`) REFERENCES `s_channel` (`channel_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`account_no`) REFERENCES `s_merchant_account` (`account_no`)
) ENGINE=InnoDB AUTO_INCREMENT=225014 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='预约短信发送记录';

-- ----------------------------
-- Table structure for s_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `s_sensitive_word`;
CREATE TABLE `s_sensitive_word` (
  `word_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关键字id',
  `word_content` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字内容',
  `word_status` int(5) DEFAULT NULL COMMENT '关键字状态',
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2602 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='敏感字表';

-- ----------------------------
-- Table structure for s_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `s_serial_number`;
CREATE TABLE `s_serial_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `serial_number` bigint(20) DEFAULT NULL COMMENT '对应序列号',
  `table_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '对应表名',
  `use_type` int(5) DEFAULT NULL COMMENT '使用状态\r\n            100-使用\r\n            200-禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='序列号生成表';

-- ----------------------------
-- Table structure for s_sms_inbox
-- ----------------------------
DROP TABLE IF EXISTS `s_sms_inbox`;
CREATE TABLE `s_sms_inbox` (
  `sms_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '内容',
  `status` int(5) DEFAULT '100' COMMENT '100 - 待处理\r\n            200 - 已处理\r\n            201 - 处理中\r\n            300 - 已失效',
  `types` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '100 - 退订\r\n            200 - 其他',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sms_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信收件箱';

-- ----------------------------
-- Table structure for s_sys_params
-- ----------------------------
DROP TABLE IF EXISTS `s_sys_params`;
CREATE TABLE `s_sys_params` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `param_code` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '标识',
  `param_value` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '值',
  `remark` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`param_id`,`param_code`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='参数配置表';

-- ----------------------------
-- Table structure for s_template
-- ----------------------------
DROP TABLE IF EXISTS `s_template`;
CREATE TABLE `s_template` (
  `template_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户账户编号',
  `merchant_name_abbreviation` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '商户简称',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 普通账户\r\n            300 - 全类型商户',
  `template_content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '模板内容',
  `operator` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '操作员',
  `status` int(5) DEFAULT NULL COMMENT '状态 \r\n            100 - 使用\r\n            200 - 禁用',
  `remark` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `participle` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `template_length` int(5) DEFAULT NULL COMMENT '短信模板长度',
  PRIMARY KEY (`template_id`),
  KEY `FK_Reference_3` (`account_no`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`account_no`) REFERENCES `s_merchant_account` (`account_no`)
) ENGINE=InnoDB AUTO_INCREMENT=352 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信模板报备';

-- ----------------------------
-- Table structure for s_unsubscribe
-- ----------------------------
DROP TABLE IF EXISTS `s_unsubscribe`;
CREATE TABLE `s_unsubscribe` (
  `unsubscribe_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '退订编号',
  `trans_type` char(10) COLLATE utf8_bin DEFAULT NULL COMMENT '交易类型\r\n            100 - 退订\r\n            200 - 黑名单',
  `unsubscribe_mobile` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '退订手机号',
  `unsubscribe_status` int(5) DEFAULT NULL COMMENT '退订状态rn            100 - 退订中(黑名单生效)rn            200 - 取消退订（黑名单不生效）',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`unsubscribe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='退订列表';

-- ----------------------------
-- Table structure for s_white_list
-- ----------------------------
DROP TABLE IF EXISTS `s_white_list`;
CREATE TABLE `s_white_list` (
  `white_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `status` int(5) DEFAULT NULL COMMENT '状态\r\n            100 - 启用\r\n            200 - 停用',
  `create_datetime` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_datetime` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`white_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='白名单表';

-- ----------------------------
-- Table structure for sms_applay
-- ----------------------------
DROP TABLE IF EXISTS `sms_applay`;
CREATE TABLE `sms_applay` (
  `sms_applay_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `batch_no` bigint(20) NOT NULL COMMENT '批次号',
  `batch_type` int(5) DEFAULT NULL COMMENT '短信发送类型\r\n            100 - 普通批量发送\r\n            200 - 高级批量发送',
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户号',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 营销账户\r\n            300 - 全类型商户',
  `account_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商户名称',
  `sms_content` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '短信内容',
  `mobile_count` int(11) DEFAULT NULL COMMENT '手机号总数',
  `outlier_count` int(11) DEFAULT NULL COMMENT '手机号异常总数',
  `repeat_count` int(11) DEFAULT NULL COMMENT '手机号重复数量',
  `succ_count` int(11) DEFAULT NULL COMMENT '需处理手机号数量',
  `order_flag` int(5) DEFAULT NULL COMMENT '是否预约\r\n            100 - 是\r\n            200 - 否',
  `appointment_time` datetime DEFAULT NULL COMMENT '预约发送时间',
  `applay_status` int(5) DEFAULT NULL COMMENT '批次状态\r\n            100 - 已上传待处理\r\n            101 - 上传成功待审核\r\n            200 - 审核通过\r\n            300 - 审核拒绝\r\n            400 - 数据处理超时\r\n            600 - 数据已处理',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '上传操作员',
  `sign_tip` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '签名',
  PRIMARY KEY (`sms_applay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信发送申请表';

-- ----------------------------
-- Table structure for sms_applay_detail
-- ----------------------------
DROP TABLE IF EXISTS `sms_applay_detail`;
CREATE TABLE `sms_applay_detail` (
  `applay_detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `batch_no` bigint(20) NOT NULL COMMENT '批次号',
  `batch_type` int(5) DEFAULT NULL COMMENT '短信发送类型\r\n            100 - 普通批量发送\r\n            200 - 高级批量发送\r\n            300 - 接口发送',
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户号',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 营销账户\r\n            300 - 全类型商户',
  `account_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商户名称',
  `mobiles_data` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号集合',
  `mobile_operator` int(11) DEFAULT NULL COMMENT '移动运营商\r\n            100 - 中国联通\r\n            200 - 中国移动\r\n            300 - 中国电信\r\n            \r\n            100 - 电信\r\n            200 - 联通\r\n            300 - 移动',
  `mobiles_count` int(4) DEFAULT NULL COMMENT '手机号数量',
  `detail_status` int(5) DEFAULT NULL COMMENT '状态\r\n            100 - 已上传待处理\r\n            101 - 上传成功待审核\r\n            200 - 审核通过\r\n            300 - 审核拒绝\r\n            400 - 处理中\r\n            403 - 处理失败\r\n            600 - 处理成功\r\n            ',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`applay_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=141534 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发送申请明细（内存表）';

-- ----------------------------
-- Table structure for sms_batch_upload
-- ----------------------------
DROP TABLE IF EXISTS `sms_batch_upload`;
CREATE TABLE `sms_batch_upload` (
  `sms_upload_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，批次号',
  `batch_type` int(5) DEFAULT NULL COMMENT '短信发送类型\r\n            100 - 普通批量发送\r\n            200 - 高级发送',
  `sms_content` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '短信内容',
  `mobile_count` int(11) DEFAULT NULL COMMENT '手机号总数',
  `account_no` bigint(20) DEFAULT NULL COMMENT '商户账户编号',
  `merchant_name_abbreviation` varchar(24) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户简称',
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 营销账户\r\n            300 - 全类型商户',
  `order_flag` int(5) DEFAULT NULL COMMENT '是否预约\r\n            100 - 是\r\n            200 - 否',
  `reservation_datetime` datetime DEFAULT NULL COMMENT '预约发送时间',
  `upload_status` int(5) DEFAULT NULL COMMENT '批量上传状态\r\n            100 - 待处理\r\n            200 - 上传成功\r\n            300 - 数据解析完毕\r\n            400 - 数据同步完毕',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(24) COLLATE utf8_bin DEFAULT NULL COMMENT '上传操作员',
  `sign_tip` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '签名',
  `batch_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '短信发送批次号',
  PRIMARY KEY (`sms_upload_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='批量发送，文件上传解析-临时数据表';

-- ----------------------------
-- Table structure for tk_account_type
-- ----------------------------
DROP TABLE IF EXISTS `tk_account_type`;
CREATE TABLE `tk_account_type` (
  `account_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_type` int(5) DEFAULT NULL COMMENT '账户类型\r\n            100 - 行业账户\r\n            200 - 营销账户\r\n            300 - 全类型商户',
  `account_type_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`account_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='账户类型\r\n100 - 行业账户\r\n200 - 营销账户\r\n300 - 全类型商户';

-- ----------------------------
-- Table structure for tk_applay_status
-- ----------------------------
DROP TABLE IF EXISTS `tk_applay_status`;
CREATE TABLE `tk_applay_status` (
  `applay_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `applay_status` int(5) DEFAULT NULL COMMENT '批次状态\r\n            100 - 已上传待处理\r\n            101 - 上传成功待审核\r\n            200 - 审核通过\r\n            300 - 审核拒绝\r\n            400 - 数据处理超时',
  `applay_status_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`applay_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='批次状态\r\n100 - 已上传待处理\r\n101 - 上传成功待审核\r\n200 - 审核通过';

-- ----------------------------
-- Table structure for tk_batch_type
-- ----------------------------
DROP TABLE IF EXISTS `tk_batch_type`;
CREATE TABLE `tk_batch_type` (
  `batch_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_type` int(5) DEFAULT NULL COMMENT '短信发送类型\r\n            100 - 普通批量发送\r\n            200 - 高级批量发送',
  `batch_type_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`batch_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信发送类型\r\n100 - 普通批量发送\r\n200 - 高级批量发送';

-- ----------------------------
-- Table structure for tk_channel_attribute
-- ----------------------------
DROP TABLE IF EXISTS `tk_channel_attribute`;
CREATE TABLE `tk_channel_attribute` (
  `channel_attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_attribute` int(5) DEFAULT NULL COMMENT '通道属性\r\n            100 - 行业类\r\n            200 - 金融营销\r\n            300 - 会员营销\r\n            400 - 106催收\r\n            500 - 025催收',
  `channel_attribute_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`channel_attribute_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通道属性';

-- ----------------------------
-- Table structure for tk_channel_status
-- ----------------------------
DROP TABLE IF EXISTS `tk_channel_status`;
CREATE TABLE `tk_channel_status` (
  `channel_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_status` int(5) DEFAULT NULL COMMENT '通道状态\r\n            100 - 使用\r\n            200 - 禁用',
  `channel_status_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`channel_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信通道状态枚举表';

-- ----------------------------
-- Table structure for tk_channel_type
-- ----------------------------
DROP TABLE IF EXISTS `tk_channel_type`;
CREATE TABLE `tk_channel_type` (
  `channel_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_type` int(5) DEFAULT NULL COMMENT '通道类型\r\n            100 - 短信\r\n            200 - 彩信\r\n            300 - 语音',
  `channel_type_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`channel_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通道类型\r\n100 - 短信\r\n200 - 彩信\r\n300 - 语音';

-- ----------------------------
-- Table structure for tk_merchant_account_status
-- ----------------------------
DROP TABLE IF EXISTS `tk_merchant_account_status`;
CREATE TABLE `tk_merchant_account_status` (
  `account_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_status` int(5) DEFAULT NULL COMMENT '状态\r\n            100 - 正常\r\n            200 - 停用\r\n            300 - 注销\r\n            400 - 冻结\r\n            500 - 欠费',
  `account_status_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`account_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='支持运营商';

-- ----------------------------
-- Table structure for tk_order_flag
-- ----------------------------
DROP TABLE IF EXISTS `tk_order_flag`;
CREATE TABLE `tk_order_flag` (
  `order_flag_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_flag` int(5) DEFAULT NULL COMMENT '是否预约\r\n            100 - 是\r\n            200 - 否',
  `order_flag_des` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`order_flag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='是否预约\r\n100 - 是\r\n200 - 否';

-- ----------------------------
-- Table structure for tk_pay_method
-- ----------------------------
DROP TABLE IF EXISTS `tk_pay_method`;
CREATE TABLE `tk_pay_method` (
  `pay_method_id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_method` int(5) DEFAULT NULL COMMENT '付费方式\r\n            100 - 预付费\r\n            200 - 后付费',
  `pay_method_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`pay_method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='付费方式\r\n100 - 预付费\r\n200 - 后付费';

-- ----------------------------
-- Table structure for tk_support_operator
-- ----------------------------
DROP TABLE IF EXISTS `tk_support_operator`;
CREATE TABLE `tk_support_operator` (
  `support_operator_id` int(11) NOT NULL AUTO_INCREMENT,
  `support_operators` int(5) DEFAULT NULL COMMENT '支持运营商\r\n            100 - 电信\r\n            200 - 联通\r\n            300 - 移动\r\n            400 - 全网通\r\n            500 - 第三方\r\n            600 - 其它',
  `support_operators_des` varchar(24) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`support_operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='支持运营商\r\n100 - 电信\r\n200 - 联通\r\n300 - 移动\r\n400';

-- ----------------------------
-- Table structure for tk_support_operators
-- ----------------------------
DROP TABLE IF EXISTS `tk_support_operators`;
CREATE TABLE `tk_support_operators` (
  `support_operators_id` int(11) NOT NULL AUTO_INCREMENT,
  `support_operators` int(5) DEFAULT NULL COMMENT '支持运营商\r\n            100 - 电信\r\n            200 - 联通\r\n            300 - 移动\r\n            400 - 全网通\r\n            500 - 第三方\r\n            600 - 其它',
  `support_operators_des` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`support_operators_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='支持运营商';
