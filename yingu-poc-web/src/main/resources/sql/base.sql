/*
Navicat MySQL Data Transfer

Source Server         : omp-qa
Source Server Version : 50173
Source Host           : 10.0.128.241:3306
Source Database       : yg-omp-test

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-10-11 15:22:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `deduct_water`
-- ----------------------------
DROP TABLE IF EXISTS `deduct_water`;
CREATE TABLE `deduct_water` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `global_id` varchar(20) NOT NULL,
  `correlation_id` varchar(20) NOT NULL,
  `order_num` varchar(50) DEFAULT NULL COMMENT '订单号',
  `deduct_status` int(1) DEFAULT NULL COMMENT '代扣状态',
  `payment_type` varchar(20) DEFAULT NULL COMMENT '渠道',
  `deduct_time` datetime DEFAULT NULL COMMENT '代扣时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34046 DEFAULT CHARSET=utf8 COMMENT='代扣流水';


-- ----------------------------
-- Table structure for `monthly_repayment`
-- ----------------------------
DROP TABLE IF EXISTS `monthly_repayment`;
CREATE TABLE `monthly_repayment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `re_id` varchar(20) NOT NULL COMMENT '运营系统ID',
  `correlation_id` varchar(20) DEFAULT NULL,
  `order_num` varchar(50) DEFAULT NULL COMMENT '订单号',
  `id_type` varchar(10) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `user_card` varchar(20) DEFAULT NULL,
  `user_mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `bank_code` varchar(50) DEFAULT NULL COMMENT '银行编码',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `bank_detail` varchar(50) DEFAULT NULL,
  `bank_card` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `plantform_type` int(1) DEFAULT NULL COMMENT '支付渠道',
  `trans_type` int(1) DEFAULT NULL COMMENT '操作类型',
  `pay_amounts` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `repay_date` date DEFAULT NULL COMMENT '还款日期',
  `deduct_status` int(1) DEFAULT '1' COMMENT '代扣状态(1:未处理，2：处理中，3：成功，4，失败)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deduct_time` datetime DEFAULT NULL COMMENT '扣款时间',
  `global_id` varchar(10) DEFAULT NULL,
  `loan_deadline` varchar(255) DEFAULT NULL,
  `transaction_Status_Msg` varchar(255) DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `store_city` varchar(255) DEFAULT NULL,
  `store_area` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `re_id` (`re_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=416352 DEFAULT CHARSET=utf8 COMMENT='月还表';


-- ----------------------------
-- Table structure for `om_log`
-- ----------------------------
DROP TABLE IF EXISTS `om_log`;
CREATE TABLE `om_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` blob NOT NULL,
  `desc` varchar(50) NOT NULL,
  `status` int(1) NOT NULL,
  `oper_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of om_log
-- ----------------------------
