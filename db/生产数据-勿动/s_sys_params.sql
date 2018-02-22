/*
Navicat MySQL Data Transfer

Source Server         : 118.190.153.242
Source Server Version : 50719
Source Host           : 118.190.153.242:3306
Source Database       : sms_trade

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-12-13 15:37:44
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='参数配置表';

-- ----------------------------
-- Records of s_sys_params
-- ----------------------------
INSERT INTO `s_sys_params` VALUES ('1', 'chuangrui_name', 'nuoxin', '创锐账号');
INSERT INTO `s_sys_params` VALUES ('2', 'chuangrui_pwd', 'DE2E1DC27FC4B20A933FDE586CB2', '创锐密码');
INSERT INTO `s_sys_params` VALUES ('3', 'meilian_encode', 'UTF-8', '美联编码');
INSERT INTO `s_sys_params` VALUES ('4', 'meilian_name', 'juhe', '美联行业账户');
INSERT INTO `s_sys_params` VALUES ('5', 'meilian_pwd', 'u2izjres', '美联行业密码');
INSERT INTO `s_sys_params` VALUES ('6', 'meilian_apikey', 'e9b3c913a31deefc3e53f76613037c99', '美联密钥');
INSERT INTO `s_sys_params` VALUES ('7', 'net263_name', 'SD-rfdx', 'net263账号');
INSERT INTO `s_sys_params` VALUES ('8', 'net263_prodid', '1580', 'net263产品ID');
INSERT INTO `s_sys_params` VALUES ('9', 'net263_pwd', 'Net263hy', 'net263密码');
INSERT INTO `s_sys_params` VALUES ('10', 'juxin_encode', 'GBK', '聚信编码');
INSERT INTO `s_sys_params` VALUES ('11', 'juxin_name', 'shequdianshang', '聚信账户');
INSERT INTO `s_sys_params` VALUES ('12', 'juxin_pwd', 'PaU33Ywk', '聚信密码');
INSERT INTO `s_sys_params` VALUES ('13', 'fenghuo_name', 'rfwyhy', '烽火账号移动');
INSERT INTO `s_sys_params` VALUES ('14', 'fenghuo_pwd', '0h1we528', '烽火密码移动');
INSERT INTO `s_sys_params` VALUES ('15', 'juxin_uri', 'http://api.app2e.com/smsBigSend.api.php', '聚信提交地址');
INSERT INTO `s_sys_params` VALUES ('16', 'meilian_yx_name', 'juheyx', '美联营销账号');
INSERT INTO `s_sys_params` VALUES ('17', 'meilian_yx_pwd', 'asdf1234', '美联营销密码');
INSERT INTO `s_sys_params` VALUES ('18', 'meilian_yx_apikey', 'f34be17e4e4e8a78d028c7d03c8b4e8d', '美联营销key');
INSERT INTO `s_sys_params` VALUES ('19', 'chuangrui_url', 'http://web.cr6868.com/asmx/smsservice.aspx', '创锐url');
INSERT INTO `s_sys_params` VALUES ('20', 'meilian_url', 'https://m.5c.com.cn/api/send/index.php', '美联url');
INSERT INTO `s_sys_params` VALUES ('21', 'net263_url', 'http://120.27.244.164/msg/HttpBatchSendSM', 'net263url');
INSERT INTO `s_sys_params` VALUES ('22', 'fenghuo_url', 'http://apisms.kyong.net:8080/eums/sms/send.do', '烽火url');
INSERT INTO `s_sys_params` VALUES ('23', 'fenghuo_name_un', 'rfldhy', '烽火账号联通');
INSERT INTO `s_sys_params` VALUES ('24', 'fenghuo_pwd_un', 'l76ly0', '烽火密码联通');
INSERT INTO `s_sys_params` VALUES ('25', 'fenghuo_name_cn', 'rfdxhy', '烽火账号电信');
INSERT INTO `s_sys_params` VALUES ('26', 'fenghuo_pwd_cn', '9pkz9v', '烽火密码电信');
INSERT INTO `s_sys_params` VALUES ('27', 'fenghuo_yx_name', 'rfwyyx', '烽火营销账号');
INSERT INTO `s_sys_params` VALUES ('28', 'fenghuo_yx_pwd', 'fe3cs5', '烽火营销密码');
INSERT INTO `s_sys_params` VALUES ('29', 'lexin_name_cmcc', 'dllilun1', '乐信移动账号');
INSERT INTO `s_sys_params` VALUES ('30', 'lexin_pwd_cmcc', 'asdf1234', '乐信移动密码');
INSERT INTO `s_sys_params` VALUES ('31', 'lexin_name_un', 'dllilun2', '乐信联通账号');
INSERT INTO `s_sys_params` VALUES ('32', 'lexin_pwd_un', 'asdf1234', '乐信联通密码');
INSERT INTO `s_sys_params` VALUES ('33', 'lexin_name_cn', 'dllilun3', '乐信电信账号');
INSERT INTO `s_sys_params` VALUES ('34', 'lexin_pwd_cn', 'asdf1234', '乐信电信密码');
INSERT INTO `s_sys_params` VALUES ('35', 'lexin_product_id', '1012888', '乐信产品编号');
INSERT INTO `s_sys_params` VALUES ('36', 'juxin_yx_name', 'bjrfwy', '聚信营销账号');
INSERT INTO `s_sys_params` VALUES ('37', 'juxin_yx_pwd', 'q6sTolHU', '聚信营销密码');
INSERT INTO `s_sys_params` VALUES ('38', 'JUXIN_USR_CMCC_100', 'zqsydhy', '聚信行业-移动-用户名');
INSERT INTO `s_sys_params` VALUES ('39', 'JUXIN_PWD_MD5_CMCC_100', '2227bd95e87bac272f5d3bc82e1eb77f', '聚信行业-移动-密码');
INSERT INTO `s_sys_params` VALUES ('40', 'JUXIN_USR_CN_100', 'zqslthy', '聚信行业-联通-用户名');
INSERT INTO `s_sys_params` VALUES ('41', 'JUXIN_PWD_MD5_CN_100', 'e2ff2c4422c396405f1d6ac2ff429a5f', '聚信行业-联通-密码');
INSERT INTO `s_sys_params` VALUES ('42', 'JUXIN_USR_CT_100', 'zqsdxhy', '聚信行业-电信-用户名');
INSERT INTO `s_sys_params` VALUES ('43', 'JUXIN_PWD_MD5_CT_100', '570842c552e05564e6dff42cd0bbe090', '聚信行业-电信-密码');
INSERT INTO `s_sys_params` VALUES ('44', 'JUXIN_USR_CMCC_300', 'zqsydyx', '聚信会员营销-移动-用户名');
INSERT INTO `s_sys_params` VALUES ('45', 'JUXIN_PWD_MD5_CMCC_300', '662d57ccfa0c400ed370e9e7887ed7b5', '聚信会员营销-移动-密码');
INSERT INTO `s_sys_params` VALUES ('46', 'JUXIN_USR_CN_300', 'zqsltyx', '聚信会员营销-联通-用户名');
INSERT INTO `s_sys_params` VALUES ('47', 'JUXIN_PWD_MD5_CN_300', '4b965501d41ef3ad2bb05fa3a654e81a', '聚信会员营销-联通-密码');
INSERT INTO `s_sys_params` VALUES ('48', 'JUXIN_USR_CT_300', 'zqsdxyx', '聚信会员营销-电信-用户名');
INSERT INTO `s_sys_params` VALUES ('49', 'JUXIN_PWD_MD5_CT_300', '2bafa12156ad72641fcc306cdf7cb0f5', '聚信会员营销-电信-密码');
INSERT INTO `s_sys_params` VALUES ('50', 'JUXIN_USR_CT_200', 'bjrfwydx', '聚信营销-电信-用户名');
INSERT INTO `s_sys_params` VALUES ('51', 'JUXIN_PWD_MD5_CT_200', '43694e2114eff88fce13aef546b74755', '聚信营销-电信-密码');
INSERT INTO `s_sys_params` VALUES ('64', 'JUMENG_URI_CMCC_100', 'http://58.253.87.82:8718/smsgwhttp/sms/submit', '聚梦行业-移动-URL');
INSERT INTO `s_sys_params` VALUES ('65', 'JUMENG_AC_CMCC_100', '10691031', '聚梦行业-移动-AC');
INSERT INTO `s_sys_params` VALUES ('66', 'JUMENG_SPID_CMCC_100', '910047', '聚梦行业-移动-SPID');
INSERT INTO `s_sys_params` VALUES ('67', 'JUMENG_PWD_CMCC_100', 'DASfds35r', '聚梦行业-移动-PWD');
INSERT INTO `s_sys_params` VALUES ('68', 'JUMENG_URI_CT_100', 'http://58.253.87.82:8718/smsgwhttp/sms/submit', '聚梦行业-电信-URL');
INSERT INTO `s_sys_params` VALUES ('69', 'JUMENG_AC_CT_100', '106909120037', '聚梦行业-电信-AC');
INSERT INTO `s_sys_params` VALUES ('70', 'JUMENG_SPID_CT_100', '200037', '聚梦行业-电信-SPID');
INSERT INTO `s_sys_params` VALUES ('71', 'JUMENG_PWD_CT_100', 'DSADWQWE', '聚梦行业-电信-PWD');
INSERT INTO `s_sys_params` VALUES ('72', 'JUMENG_URI_CU_100', 'http://112.90.92.218:8718/smsgwhttp/sms/submit', '聚梦行业-联通-URL');
INSERT INTO `s_sys_params` VALUES ('73', 'JUMENG_AC_CU_100', '106905563347', '聚梦行业-联通-AC');
INSERT INTO `s_sys_params` VALUES ('74', 'JUMENG_SPID_CU_100', '100031', '聚梦行业-联通-SPID');
INSERT INTO `s_sys_params` VALUES ('75', 'JUMENG_PWD_CU_100', 'da57eDRA87G', '聚梦行业-联通-PWD');
INSERT INTO `s_sys_params` VALUES ('76', 'ChunagRui_AccessKey_400', 'oB0mUQumZBONFvHQ', '创瑞3.0-金融营销-全网通-AccessKey');
INSERT INTO `s_sys_params` VALUES ('77', 'ChunagRui_AccessSecret_400', 'eqUIIPa3U52RUXHakuMM8At3AIVWKksc', '创瑞3.0-金融营销-全网通-AccessSecret');
INSERT INTO `s_sys_params` VALUES ('78', 'ChunagRui_Uri_400', 'http://api.1cloudsp.com/api/v2/send', '创瑞3.0-金融营销-全网通-URL');
INSERT INTO `s_sys_params` VALUES ('79', 'MEILIAN_NAME_100_100', 'rfhyct', '美联行业-电信用户名');
INSERT INTO `s_sys_params` VALUES ('80', 'MEILIAN_PWD_100_100', '1adbb3178591fd5bb0c248518f39bf6d', '美联行业-电信密码');
INSERT INTO `s_sys_params` VALUES ('81', 'MEILIAN_APIKEY_100_100', 'b47dfbc0293267e4a69ff45253eea928', '美联行业-电信APIKEY');
INSERT INTO `s_sys_params` VALUES ('82', 'MEILIAN_NAME_100_200', 'rfhycu', '美联行业-联通用户名');
INSERT INTO `s_sys_params` VALUES ('83', 'MEILIAN_PWD_100_200', '1adbb3178591fd5bb0c248518f39bf6d', '美联行业-联通密码');
INSERT INTO `s_sys_params` VALUES ('84', 'MEILIAN_APIKEY_100_200', '901fe25dc03bf816cc6c9a6984ccb312', '美联行业-联通APIKEY');
INSERT INTO `s_sys_params` VALUES ('85', 'MEILIAN_PWD_100_300', 'c11a2146eb366ace4633747adf4bb70d', '美联行业-移动密码');
INSERT INTO `s_sys_params` VALUES ('86', 'MEILIAN_APIKEY_100_300', 'e9b3c913a31deefc3e53f76613037c99', '美联行业-移动APIKEY');
INSERT INTO `s_sys_params` VALUES ('87', 'MEILIAN_NAME_100_300', 'juhe', '美联行业-移动用户名');
INSERT INTO `s_sys_params` VALUES ('88', 'MEILIAN_NAME_300_100', 'rfyxct', '美联会员营销-电信用户名');
INSERT INTO `s_sys_params` VALUES ('89', 'MEILIAN_PWD_300_100', '1adbb3178591fd5bb0c248518f39bf6d', '美联会员营销-电信密码');
INSERT INTO `s_sys_params` VALUES ('90', 'MEILIAN_APIKEY_300_100', 'a2d26b086687d1f3b61fc6e2f0df8a95', '美联会员营销-电信APIKEY');
INSERT INTO `s_sys_params` VALUES ('91', 'MEILIAN_NAME_300_200', 'rfyxcu', '美联会员营销-联通用户名');
INSERT INTO `s_sys_params` VALUES ('92', 'MEILIAN_PWD_300_200', '1adbb3178591fd5bb0c248518f39bf6d', '美联会员营销-联通密码');
INSERT INTO `s_sys_params` VALUES ('93', 'MEILIAN_APIKEY_300_200', 'b3a59539eccc32ad80a40d6af2f164ba', '美联会员营销-联通PIKEY');
INSERT INTO `s_sys_params` VALUES ('94', 'MEILIAN_NAME_500_400', 'rfcscm', '美联025催收-三网用户名');
INSERT INTO `s_sys_params` VALUES ('95', 'MEILIAN_PWD_500_400', '1adbb3178591fd5bb0c248518f39bf6d', '美联025催收-三网密码');
INSERT INTO `s_sys_params` VALUES ('96', 'MEILIAN_APIKEY_500_400', 'b3fcfc7666ea86c04cb4ee5c81e774d7', '美联025催收-三网APIKEY');
INSERT INTO `s_sys_params` VALUES ('97', 'MEILIAN_NAME_400_200', 'rfcjcm', '美联106催收-联通用户名');
INSERT INTO `s_sys_params` VALUES ('98', 'MEILIAN_PWD_400_200', '1adbb3178591fd5bb0c248518f39bf6d', '美联106催收-联通密码');
INSERT INTO `s_sys_params` VALUES ('99', 'MEILIAN_APIKEY_400_200', '2368beec9d662de9f85530aeafdc7b2a', '美联106催收-联通APIKEY');
INSERT INTO `s_sys_params` VALUES ('100', 'MEILIAN_NAME_400_300', 'rfcjcm', '美联106催收-移动用户名');
INSERT INTO `s_sys_params` VALUES ('101', 'MEILIAN_PWD_400_300', '1adbb3178591fd5bb0c248518f39bf6d', '美联106催收-移动密码');
INSERT INTO `s_sys_params` VALUES ('102', 'MEILIAN_APIKEY_400_300', '2368beec9d662de9f85530aeafdc7b2a', '美联106催收-移动APIKEY');
INSERT INTO `s_sys_params` VALUES ('103', 'MEILIAN_NAME_300_300', 'juheyx', '美联会员营销-移动用户名');
INSERT INTO `s_sys_params` VALUES ('104', 'MEILIAN_PWD_300_300', '1adbb3178591fd5bb0c248518f39bf6d', '美联会员营销-移动密码');
INSERT INTO `s_sys_params` VALUES ('105', 'MEILIAN_APIKEY_300_300', 'f34be17e4e4e8a78d028c7d03c8b4e8d', '美联会员营销-移动APIKEY');
INSERT INTO `s_sys_params` VALUES ('106', 'XUNQI_REQ_URI', 'http://47.92.96.185/smsJson.aspx', '讯奇科技URL');
INSERT INTO `s_sys_params` VALUES ('107', 'XUNQI_USERID_100_100', 'Hd2800003', '讯奇科技用户ID-电信-行业');
INSERT INTO `s_sys_params` VALUES ('108', 'XUNQI_ACCOUNT_100_100', 'Hd2800003', '讯奇科技Account-电信-行业');
INSERT INTO `s_sys_params` VALUES ('109', 'XUNQI_PWD_100_100', '800003456', '讯奇科技password-电信-行业');
INSERT INTO `s_sys_params` VALUES ('110', 'XUNQI_USERID_200_100', 'Hl2800002', '讯奇科技用户ID-联通-行业');
INSERT INTO `s_sys_params` VALUES ('111', 'XUNQI_ACCOUNT_200_100', 'Hl2800002', '讯奇科技Account-联通-行业');
INSERT INTO `s_sys_params` VALUES ('112', 'XUNQI_PWD_200_100', '800002456', '讯奇科技password-联通-行业');
INSERT INTO `s_sys_params` VALUES ('113', 'XUNQI_USERID_300_100', 'H2800001', '讯奇科技用户ID-移动-行业');
INSERT INTO `s_sys_params` VALUES ('114', 'XUNQI_ACCOUNT_300_100', 'H2800001', '讯奇科技Account-移动-行业');
INSERT INTO `s_sys_params` VALUES ('115', 'XUNQI_PWD_300_100', '800001456', '讯奇科技password-移动-行业');
INSERT INTO `s_sys_params` VALUES ('116', 'XUNQI_USERID_100_200', 'Yd502600003', '讯奇科技用户ID-电信-金融营销');
INSERT INTO `s_sys_params` VALUES ('117', 'XUNQI_ACCOUNT_100_200', 'Yd502600003', '讯奇科技Account-电信-金融营销');
INSERT INTO `s_sys_params` VALUES ('118', 'XUNQI_PWD_100_200', '600003456', '讯奇科技password-电信-金融营销');
INSERT INTO `s_sys_params` VALUES ('119', 'XUNQI_USERID_200_200', 'Yl502600002', '讯奇科技用户ID-联通-金融营销');
INSERT INTO `s_sys_params` VALUES ('120', 'XUNQI_ACCOUNT_200_200', 'Yl502600002', '讯奇科技Account-联通-金融营销');
INSERT INTO `s_sys_params` VALUES ('121', 'XUNQI_PWD_200_200', '600002456', '讯奇科技password-联通-金融营销');
INSERT INTO `s_sys_params` VALUES ('122', 'XUNQI_USERID_300_200', 'Y502600001', '讯奇科技用户ID-移动-金融营销');
INSERT INTO `s_sys_params` VALUES ('123', 'XUNQI_ACCOUNT_300_200', 'Y502600001', '讯奇科技Account-移动-金融营销');
INSERT INTO `s_sys_params` VALUES ('124', 'XUNQI_PWD_300_200', '600001456', '讯奇科技password-移动-金融营销');
