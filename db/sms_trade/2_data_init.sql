INSERT INTO `tk_account_type` VALUES ('1', '100', '行业账户');
INSERT INTO `tk_account_type` VALUES ('2', '200', '金融营销');
INSERT INTO `tk_account_type` VALUES ('3', '300', '会员营销');

INSERT INTO `tk_applay_status` VALUES ('1', '100', '已上传待处理');
INSERT INTO `tk_applay_status` VALUES ('2', '101', '上传成功待审核');
INSERT INTO `tk_applay_status` VALUES ('3', '200', '审核通过');
INSERT INTO `tk_applay_status` VALUES ('4', '300', '审核拒绝');
INSERT INTO `tk_applay_status` VALUES ('5', '400', '数据处理超时');
INSERT INTO `tk_applay_status` VALUES ('6', '600', '数据已处理');

INSERT INTO `tk_batch_type` VALUES ('1', '100', '普通批量发送');
INSERT INTO `tk_batch_type` VALUES ('2', '200', '高级批量发送');

INSERT INTO `tk_channel_status` VALUES ('1', '100', '使用');
INSERT INTO `tk_channel_status` VALUES ('2', '200', '禁用');

INSERT INTO `tk_channel_type` VALUES ('1', '100', '短信');
INSERT INTO `tk_channel_type` VALUES ('2', '200', '彩信');
INSERT INTO `tk_channel_type` VALUES ('3', '300', '语音');

INSERT INTO `tk_merchant_account_status` VALUES ('1', '100', '正常');
INSERT INTO `tk_merchant_account_status` VALUES ('2', '200', '停用');
INSERT INTO `tk_merchant_account_status` VALUES ('3', '300', '注销');
INSERT INTO `tk_merchant_account_status` VALUES ('4', '400', '冻结');
INSERT INTO `tk_merchant_account_status` VALUES ('5', '500', '欠费');

INSERT INTO `tk_order_flag` VALUES ('1', '100', '否');
INSERT INTO `tk_order_flag` VALUES ('2', '200', '是');

INSERT INTO `tk_pay_method` VALUES ('1', '100', '预付费');
INSERT INTO `tk_pay_method` VALUES ('2', '200', '后付费');

INSERT INTO `tk_support_operator` VALUES ('1', '100', '电信');
INSERT INTO `tk_support_operator` VALUES ('2', '200', '联通');
INSERT INTO `tk_support_operator` VALUES ('3', '300', '移动');
INSERT INTO `tk_support_operator` VALUES ('4', '400', '全网通');
INSERT INTO `tk_support_operator` VALUES ('5', '500', '第三方');
INSERT INTO `tk_support_operator` VALUES ('6', '600', '其它');

INSERT INTO `tk_support_operators` VALUES ('1', '100', '电信');
INSERT INTO `tk_support_operators` VALUES ('2', '200', '联通');
INSERT INTO `tk_support_operators` VALUES ('3', '300', '移动');
INSERT INTO `tk_support_operators` VALUES ('4', '400', '全网通');
INSERT INTO `tk_support_operators` VALUES ('5', '500', '第三方');
INSERT INTO `tk_support_operators` VALUES ('6', '600', '其它');

INSERT INTO `tk_channel_attribute` VALUES ('1', '100', '行业类');
INSERT INTO `tk_channel_attribute` VALUES ('2', '200', '金融营销');
INSERT INTO `tk_channel_attribute` VALUES ('3', '300', '会员营销');
INSERT INTO `tk_channel_attribute` VALUES ('4', '400', '106催收');
INSERT INTO `tk_channel_attribute` VALUES ('5', '500', '025催收');