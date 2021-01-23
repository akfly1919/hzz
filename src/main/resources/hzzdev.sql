/*
 Navicat Premium Data Transfer

 Source Server         : hzzdev
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : 192.144.230.48:3306
 Source Schema         : hzzdev

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 23/01/2021 16:00:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for broadcastnoteinfo
-- ----------------------------
DROP TABLE IF EXISTS `broadcastnoteinfo`;
CREATE TABLE `broadcastnoteinfo` (
                                   `bni_id` int(11) NOT NULL AUTO_INCREMENT,
                                   `bni_postion` int(11) DEFAULT NULL COMMENT '位置 1首页轮播 2交易大厅轮播 3 通知广播4.活动专区',
                                   `bni_picture` varchar(150) DEFAULT NULL COMMENT '轮播图片',
                                   `bni_title` varchar(50) DEFAULT NULL COMMENT '标题',
                                   `bni_desc` varchar(150) DEFAULT NULL COMMENT '描述',
                                   `bni_url` varchar(150) DEFAULT NULL COMMENT '链接',
                                   `bni_sort` int(11) DEFAULT '1' COMMENT '排序',
                                   `bni_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   `bni_updatetime` timestamp NULL DEFAULT NULL,
                                   `bni_valid` int(11) DEFAULT '1' COMMENT '0不可用 1可用',
                                   PRIMARY KEY (`bni_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='轮播信息';

-- ----------------------------
-- Records of broadcastnoteinfo
-- ----------------------------
BEGIN;
INSERT INTO `broadcastnoteinfo` VALUES (7, 4, 'repeatAdPic_1611150338.png', '广告4', NULL, 'https://raw.githubusercontent .com/EvilCult/iptv-m3u-maker/master/tv .m3u8', 1, '2021-01-20 19:16:58', '2021-01-20 20:09:10', 1);
INSERT INTO `broadcastnoteinfo` VALUES (8, 3, NULL, NULL, '大家好', NULL, 1, '2021-01-20 20:06:38', '2021-01-20 20:34:56', 1);
INSERT INTO `broadcastnoteinfo` VALUES (9, 1, 'repeatAdPic_1611150086.png', '测试1', NULL, '.com/EvilCult/iptv-m3u-maker/master/tv .m3u8', 1, '2021-01-20 20:09:27', NULL, 1);
INSERT INTO `broadcastnoteinfo` VALUES (11, 2, 'repeatAdPic_1611153468.png', '测试4', NULL, '.com/EvilCult/iptv-m3u-maker/master/tv .m3u8', 9, '2021-01-20 20:24:52', '2021-01-20 20:33:03', 1);
INSERT INTO `broadcastnoteinfo` VALUES (12, 1, 'repeatAdPic_1611154999.png', '测试8', NULL, '.com/EvilCult/iptv-m3u-maker/master/tv .m3u8', 5, '2021-01-20 20:36:46', NULL, 1);
INSERT INTO `broadcastnoteinfo` VALUES (13, 1, 'repeatAdPic_1611153545.png', '测试11', NULL, '111', 4, '2021-01-20 20:37:16', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for customeraddressinfo
-- ----------------------------
DROP TABLE IF EXISTS `customeraddressinfo`;
CREATE TABLE `customeraddressinfo` (
                                     `cai_id` int(11) NOT NULL AUTO_INCREMENT,
                                     `cbi_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                                     `cai_recipients` varchar(150) DEFAULT NULL,
                                     `cai_phonenum` varchar(150) DEFAULT NULL,
                                     `cai_province` varchar(150) DEFAULT NULL,
                                     `cai_city` varchar(150) DEFAULT NULL,
                                     `cai_area` varchar(150) DEFAULT NULL,
                                     `cai_street` varchar(150) DEFAULT NULL,
                                     `cai_sort` int(11) DEFAULT '1' COMMENT '排序',
                                     `cai_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `cai_updatetime` timestamp NULL DEFAULT NULL,
                                     `cai_valid` int(11) DEFAULT '1' COMMENT '0不可用 1可用',
                                     PRIMARY KEY (`cai_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址信息';

-- ----------------------------
-- Table structure for customerbaseinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerbaseinfo`;
CREATE TABLE `customerbaseinfo` (
                                  `cbi_id` bigint(11) NOT NULL AUTO_INCREMENT,
                                  `cbi_name` varchar(50) DEFAULT NULL,
                                  `cbi_idcard` varchar(50) DEFAULT NULL,
                                  `cbi_phonenum` varchar(20) DEFAULT NULL,
                                  `cbi_email` varchar(100) DEFAULT NULL,
                                  `cbi_sex` int(11) DEFAULT '0' COMMENT '0是男 1是女',
                                  `cbi_username` varchar(100) NOT NULL COMMENT '登录名',
                                  `cbi_password` varchar(255) NOT NULL COMMENT '登录密码',
                                  `cbi_valid` int(11) DEFAULT '1' COMMENT '0不可用 1可用',
                                  `cbi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `cbi_updatetime` timestamp NULL DEFAULT NULL,
                                  `cbi_total` double DEFAULT NULL COMMENT '总资产',
                                  `cbi_balance` double DEFAULT NULL COMMENT '余额',
                                  `cbi_frozen` double DEFAULT NULL COMMENT '冻结金额',
                                  `cbi_type` int(11) NOT NULL DEFAULT '2' COMMENT '1管理员2用户',
                                  `cbi_parentid` bigint(20) DEFAULT NULL COMMENT '用户推荐人',
                                  `cbi_isnew` int(11) DEFAULT '1' COMMENT '0不是新手1是新手',
                                  `cbi_goodsnum` int(11) DEFAULT NULL COMMENT '库存数量',
                                  `cbi_shareurl` varchar(100) DEFAULT NULL COMMENT '邀请链接',
                                  PRIMARY KEY (`cbi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8 COMMENT='用户基础信息';

-- ----------------------------
-- Records of customerbaseinfo
-- ----------------------------
BEGIN;
INSERT INTO `customerbaseinfo` VALUES (10000, 'test1', '130206196512322371', '15647122134', 'z1@163.com', 0, '15647122134', '123', 1, '2021-01-16 11:41:32', NULL, NULL, NULL, NULL, 2, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10001, 'test2', '230206196512322371', '18247122134', 'z2@163.com', 0, '18247122134', '123', 1, '2021-01-16 11:41:32', NULL, NULL, NULL, NULL, 2, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10002, 'test3', '330206196512322371', '17247122134', 'z2@163.com', 0, '17247122134', '123', 1, '2021-01-16 11:41:32', NULL, NULL, NULL, NULL, 2, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10003, 'wmy_test1', NULL, NULL, NULL, 0, 'wmy_test1', '123', 1, '2021-01-16 20:47:17', NULL, NULL, NULL, NULL, 1, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10004, 'zjy', NULL, NULL, NULL, 0, 'zjy', '123', 1, '2021-01-17 22:44:41', NULL, NULL, NULL, NULL, 1, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10006, '王梦雅', '362323199999999999', '13121111211', 'test1@qq.com', 1, 'wmy_test1', '123', 1, '2021-01-21 19:12:00', NULL, 100, 50, 50, 2, NULL, 1, 3, NULL);
INSERT INTO `customerbaseinfo` VALUES (10008, '18610828238', NULL, '18610828238', NULL, 0, '18610828238', '456', 1, '2021-01-22 18:02:30', NULL, NULL, NULL, NULL, 2, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10009, '18610828237', NULL, '18610828237', NULL, 0, '18610828237', '456', 1, '2021-01-22 18:13:26', NULL, NULL, NULL, NULL, 2, NULL, 1, NULL, NULL);
INSERT INTO `customerbaseinfo` VALUES (10010, '18610828236', NULL, '18610828236', NULL, 0, '18610828236', '456', 1, '2021-01-22 19:22:36', NULL, NULL, NULL, NULL, 2, NULL, 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for customercardinfo
-- ----------------------------
DROP TABLE IF EXISTS `customercardinfo`;
CREATE TABLE `customercardinfo` (
                                  `cci_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增账号id',
                                  `cbi_id` bigint(20) NOT NULL COMMENT '用户ID',
                                  `cci_total` double NOT NULL COMMENT '用户总资产',
                                  `cci_balance` double NOT NULL COMMENT '用户余额',
                                  `cci_pointreward` int(11) DEFAULT NULL COMMENT '会员积分',
                                  `cci_trainreword` double DEFAULT NULL COMMENT '提成金额',
                                  `cci_buynum` int(11) DEFAULT NULL COMMENT '购买次数',
                                  `cci_picknum` int(11) DEFAULT NULL COMMENT '提货次数',
                                  `cci_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `cci_updatetime` timestamp NULL DEFAULT NULL,
                                  `cci_type` int(11) NOT NULL DEFAULT '1' COMMENT '正常用户',
                                  PRIMARY KEY (`cci_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户信息';

-- ----------------------------
-- Table structure for customercashoutinfo
-- ----------------------------
DROP TABLE IF EXISTS `customercashoutinfo`;
CREATE TABLE `customercashoutinfo` (
                                     `ccoi_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '提货单号',
                                     `cbi_id` bigint(20) NOT NULL COMMENT '用户ID',
                                     `ccoi_account` varchar(50) DEFAULT NULL COMMENT '提现账户信息',
                                     `ccoi_type` int(11) DEFAULT NULL COMMENT '账户类型 1支付宝 2微信 3银行卡',
                                     `ccoi_amount` double DEFAULT NULL COMMENT '提现金额',
                                     `ccoi_desc` varchar(100) DEFAULT NULL COMMENT '描述',
                                     `ccoi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1待审核2已审核3已拒绝4审核通过5已打款 6打款失败',
                                     `ccoi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `ccoi_updatetime` timestamp NULL DEFAULT NULL,
                                     `ccoi_finishtime` timestamp NULL DEFAULT NULL,
                                     `ccoi_operator` varchar(50) DEFAULT NULL COMMENT '审核人',
                                     PRIMARY KEY (`ccoi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户提现表';

-- ----------------------------
-- Table structure for customergoodsrelated
-- ----------------------------
DROP TABLE IF EXISTS `customergoodsrelated`;
CREATE TABLE `customergoodsrelated` (
                                      `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增',
                                      `cbi_id` bigint(20) NOT NULL COMMENT '用户ID',
                                      `gii_id` bigint(20) NOT NULL COMMENT '物料id',
                                      `gbi_id` bigint(20) NOT NULL COMMENT '例如:某品牌价格红酒',
                                      `cgr_isown` int(11) NOT NULL DEFAULT '1' COMMENT '0不拥有  1拥有',
                                      `cgr_islock` int(11) NOT NULL DEFAULT '1' COMMENT '0未锁住 1锁住 当天物品获得变锁住 2冻结 商品在售卖表',
                                      `cgr_ispickup` int(11) NOT NULL DEFAULT '0' COMMENT '0未提货 1已提货',
                                      `cgr_buytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '可用来判断锁住,当日买的当日不能卖',
                                      `cgr_selltime` timestamp NULL DEFAULT NULL,
                                      `cgr_updatetime` timestamp NULL DEFAULT NULL,
                                      PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='客户商品物料对应表';

-- ----------------------------
-- Records of customergoodsrelated
-- ----------------------------
BEGIN;
INSERT INTO `customergoodsrelated` VALUES (1, 10000, 3, 2, 1, 0, 0, '2021-01-22 21:34:43', NULL, '2021-01-22 21:52:43');
INSERT INTO `customergoodsrelated` VALUES (2, 10000, 4, 2, 1, 0, 0, '2021-01-22 21:35:12', NULL, '2021-01-22 21:52:43');
COMMIT;

-- ----------------------------
-- Table structure for customeridcardinfo
-- ----------------------------
DROP TABLE IF EXISTS `customeridcardinfo`;
CREATE TABLE `customeridcardinfo` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `cbi_id` bigint(20) DEFAULT NULL,
                                    `cii_idcardfront` varchar(150) DEFAULT NULL,
                                    `cii_idcardback` varchar(150) DEFAULT NULL,
                                    `cii_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `cii_updatetime` timestamp NULL DEFAULT NULL,
                                    `cii_status` int(11) DEFAULT '1' COMMENT '1未审核 2审核',
                                    `cii_operator` varchar(150) DEFAULT NULL COMMENT '操作人',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户身份证信息';

-- ----------------------------
-- Records of customeridcardinfo
-- ----------------------------
BEGIN;
INSERT INTO `customeridcardinfo` VALUES (1, 10006, 'goodsMainPic_1611147937.png', 'goodsMainPic_1611147937.png', '2021-01-22 11:30:00', NULL, 2, 'wmy_test1');
INSERT INTO `customeridcardinfo` VALUES (2, 10002, 'goodsMainPic_1611147937.png', 'goodsMainPic_1611147937.png', '2021-01-22 11:30:00', NULL, 3, 'wmy_test1');
COMMIT;

-- ----------------------------
-- Table structure for customerpayinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerpayinfo`;
CREATE TABLE `customerpayinfo` (
                                 `cpi_orderid` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `cai_amount` int(11) DEFAULT NULL COMMENT '账户充值金额',
                                 `cbi_id` varchar(150) DEFAULT NULL COMMENT 'customerbaseinfo的cbi_id',
                                 `cpi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `cpi_updatetime` timestamp NULL DEFAULT NULL,
                                 `cpi_paytime` timestamp NULL DEFAULT NULL,
                                 `cpi_finishtime` timestamp NULL DEFAULT NULL,
                                 `cpi_paystatus` int(11) DEFAULT '0' COMMENT '充值状态0未付款 1已付款',
                                 `cpi_valid` int(11) DEFAULT '1' COMMENT '0无效 1有效',
                                 `cpi_channel` int(11) DEFAULT '0' COMMENT '充值渠道1.weixin, 2.Alipay',
                                 `cpi_channelorderid` varchar(100) DEFAULT NULL COMMENT '充值平台返回的订单号',
                                 `cpi_operator` varchar(50) DEFAULT NULL COMMENT '审核人',
                                 PRIMARY KEY (`cpi_orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户充值信息';

-- ----------------------------
-- Table structure for customerpickupinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerpickupinfo`;
CREATE TABLE `customerpickupinfo` (
                                    `cpui_orderid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '提货单号',
                                    `cbi_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
                                    `gii_id` bigint(20) DEFAULT NULL COMMENT '物料id',
                                    `gbi_id` bigint(20) DEFAULT NULL COMMENT '例如:某品牌价格红酒',
                                    `cai_id` int(11) DEFAULT NULL COMMENT '用户地址ID',
                                    `cpui_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `cpui_updatetime` timestamp NULL DEFAULT NULL,
                                    `cpui_finishtime` timestamp NULL DEFAULT NULL,
                                    `cpui_status` int(11) DEFAULT '1' COMMENT '0待审核 1已审核 2拒绝  3待发货,4已发货 ，5已收货(已提货)',
                                    PRIMARY KEY (`cpui_orderid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户提货表';

-- ----------------------------
-- Records of customerpickupinfo
-- ----------------------------
BEGIN;
INSERT INTO `customerpickupinfo` VALUES (1, 10006, 25, 5, 1, '2021-01-22 15:57:00', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for customerteaminfo
-- ----------------------------
DROP TABLE IF EXISTS `customerteaminfo`;
CREATE TABLE `customerteaminfo` (
                                  `cti_id` int(11) NOT NULL AUTO_INCREMENT,
                                  `cti_owner` bigint(20) DEFAULT NULL COMMENT 'cbi_ID',
                                  `cti_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `cti_updatetime` timestamp NULL DEFAULT NULL,
                                  `cti_scale` int(11) DEFAULT '1' COMMENT '1常规',
                                  `cti_valid` int(11) DEFAULT '1' COMMENT '0不可用 1可用',
                                  PRIMARY KEY (`cti_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户团队信息';

-- ----------------------------
-- Table structure for goodsbaseinfo
-- ----------------------------
DROP TABLE IF EXISTS `goodsbaseinfo`;
CREATE TABLE `goodsbaseinfo` (
                               `gbi_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品名称ID 例如:某品牌价格红酒',
                               `gbi_name` varchar(100) DEFAULT NULL COMMENT '产品名称',
                               `gbi_cid` int(11) DEFAULT '1' COMMENT '1酒类',
                               `gbi_title` varchar(100) DEFAULT NULL COMMENT '商品id',
                               `gbi_desc` varchar(255) DEFAULT NULL COMMENT '商品id',
                               `gbi_discountprice` double(20,0) DEFAULT NULL COMMENT '特惠价格',
                               `gbi_price` double NOT NULL COMMENT '产品价格',
                               `gbi_count` int(11) NOT NULL COMMENT '发行总数量',
                               `gbi_valid` int(11) NOT NULL DEFAULT '1' COMMENT '0不可以 1可用',
                               `gbi_type` int(11) NOT NULL COMMENT '1正常商品 2新手商品',
                               `gbi_saleable` int(11) NOT NULL DEFAULT '1' COMMENT '0未上架 1上架',
                               `gbi_createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                               `gbi_updatetime` timestamp NULL DEFAULT NULL,
                               `gbi_operator` varchar(20) DEFAULT NULL,
                               `gbi_limitperson` int(11) DEFAULT NULL COMMENT '每人限购',
                               `gbi_limitday` int(11) DEFAULT NULL COMMENT '每日限购',
                               `gbi_limitsalenum` int(11) DEFAULT NULL COMMENT '起卖份数',
                               `gbi_pricefloor` double DEFAULT NULL COMMENT '价格下限',
                               `gbi_priceceiling` double DEFAULT NULL COMMENT '价格上限',
                               `gbi_buyservicerate` double DEFAULT NULL COMMENT '商品买入服务费比例',
                               `gbi_sellservicerate` double DEFAULT NULL COMMENT '商品卖出服务比率',
                               `gbi_sort` int(11) DEFAULT NULL COMMENT '排序',
                               `gbi_picture` varchar(100) DEFAULT NULL COMMENT '商品主图',
                               `gbi_limitpickup` int(11) DEFAULT NULL COMMENT '提货最低限制',
                               PRIMARY KEY (`gbi_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品基础信息-用于展示';

-- ----------------------------
-- Records of goodsbaseinfo
-- ----------------------------
BEGIN;
INSERT INTO `goodsbaseinfo` VALUES (1, '长城红酒', 1, '测试3', '长城红酒', 10, 135, 10, 0, 1, 1, '2021-01-19 17:02:42', '2021-01-19 22:48:36', 'wmy_test1', 1, 1, 1, 70, 90, 5, NULL, 2, 'goodsMainPic_1611055126.png', NULL);
INSERT INTO `goodsbaseinfo` VALUES (2, '长城红酒', 1, '测试3', '长城红酒', NULL, 135, 2, 0, 1, 1, '2021-01-19 17:24:38', '2021-01-19 22:48:24', 'wmy_test1', NULL, NULL, NULL, NULL, NULL, 3, NULL, 5, 'goodsMainPic_1611057220.jpeg', NULL);
INSERT INTO `goodsbaseinfo` VALUES (3, '长城', 2, '测试344', '长城长城长城', 30, 135, 2, 0, 1, 1, '2021-01-19 22:50:11', '2021-01-19 22:54:55', 'wmy_test1', 5, 50, 5, 120, 200, 5, NULL, 5, 'goodsMainPic_1611072721.png', NULL);
INSERT INTO `goodsbaseinfo` VALUES (4, '长城红酒11', 1, '测试3', '长城红酒', NULL, 135, 2, 1, 2, 1, '2021-01-19 22:54:40', NULL, 'wmy_test1', NULL, NULL, NULL, NULL, NULL, 6, NULL, 2, 'goodsMainPic_1611077018.png', NULL);
INSERT INTO `goodsbaseinfo` VALUES (5, '长城红酒', 1, '测试3', '长城红酒', 90, 135, 9, 1, 1, 1, '2021-01-19 22:56:10', '2021-01-20 20:00:24', 'wmy_test1', 5, 5, 3, 30, 888, 3, NULL, 5, 'goodsMainPic_1611147937.png', NULL);
INSERT INTO `goodsbaseinfo` VALUES (6, '测试22', 2, '测试3', '测试5', 1455, 13500, 10, 0, 1, 1, '2021-01-21 15:48:17', '2021-01-21 17:43:40', 'wmy_test1', 2, 2, 2, 10000, 15000, 4, 7, 6, 'goodsMainPic_1611219224.png', 2);
INSERT INTO `goodsbaseinfo` VALUES (7, '测试1', 1, '测试344', '支付宝1', 1350, 1359, 4, 1, 2, 1, '2021-01-21 17:46:29', '2021-01-21 18:08:20', 'wmy_test1', 5, 5, 2, 1200, 1400, 2, 6, 4, 'goodsMainPic_1611223548.png', 2);
INSERT INTO `goodsbaseinfo` VALUES (8, '测试1', 1, '测试2', '支付宝1', 33, 135, 2, 1, 1, 1, '2021-01-22 17:49:33', NULL, 'wmy_test1', 2, 20, 2, 44, 888, 4, 4, 2, 'goodsMainPic_1611317190.png', 7);
COMMIT;

-- ----------------------------
-- Table structure for goodsiteminfo
-- ----------------------------
DROP TABLE IF EXISTS `goodsiteminfo`;
CREATE TABLE `goodsiteminfo` (
                               `gii_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物料id',
                               `gbi_id` bigint(20) DEFAULT NULL COMMENT '商品id',
                               `gii_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `gii_status` int(11) DEFAULT '1' COMMENT '0不可用 1可用',
                               `gii_ispickup` int(11) DEFAULT '0' COMMENT '0未提货 1已提货 2已发货 3已收货 提货后不可用',
                               `gii_pickuptime` timestamp NULL DEFAULT NULL COMMENT '提货时间',
                               `gii_receivetime` timestamp NULL DEFAULT NULL,
                               `gii_updatetime` timestamp NULL DEFAULT NULL,
                               PRIMARY KEY (`gii_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='商品物料信息-最细颗粒度';

-- ----------------------------
-- Records of goodsiteminfo
-- ----------------------------
BEGIN;
INSERT INTO `goodsiteminfo` VALUES (1, 1, '2021-01-19 17:02:42', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (2, 1, '2021-01-19 17:02:42', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (3, 2, '2021-01-19 17:24:38', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (4, 2, '2021-01-19 17:24:38', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (5, 1, '2021-01-19 17:47:40', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (6, 1, '2021-01-19 17:47:40', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (7, 1, '2021-01-19 18:32:27', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (8, 1, '2021-01-19 18:32:27', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (9, 1, '2021-01-19 18:32:27', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (10, 1, '2021-01-19 18:35:51', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (11, 1, '2021-01-19 18:35:51', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (12, 1, '2021-01-19 18:35:51', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (13, 3, '2021-01-19 22:50:11', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (14, 3, '2021-01-19 22:50:11', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (15, 4, '2021-01-19 22:54:40', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (16, 4, '2021-01-19 22:54:40', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (17, 5, '2021-01-19 22:56:10', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (18, 5, '2021-01-19 22:56:10', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (19, 5, '2021-01-19 22:56:37', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (20, 5, '2021-01-19 22:56:37', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (21, 5, '2021-01-19 22:57:29', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (22, 5, '2021-01-19 22:57:29', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (23, 5, '2021-01-19 22:57:29', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (24, 5, '2021-01-20 19:47:01', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (25, 5, '2021-01-20 19:47:01', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (26, 6, '2021-01-21 15:48:17', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (27, 6, '2021-01-21 15:48:17', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (28, 6, '2021-01-21 15:55:21', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (29, 6, '2021-01-21 15:55:21', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (30, 6, '2021-01-21 16:54:27', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (31, 6, '2021-01-21 16:59:17', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (32, 6, '2021-01-21 17:01:46', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (33, 6, '2021-01-21 17:05:14', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (34, 6, '2021-01-21 17:07:03', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (35, 6, '2021-01-21 17:32:29', 0, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (36, 7, '2021-01-21 17:46:29', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (37, 7, '2021-01-21 17:46:29', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (38, 7, '2021-01-21 18:04:58', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (39, 7, '2021-01-21 18:08:20', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (40, 8, '2021-01-22 17:49:33', 1, 0, NULL, NULL, NULL);
INSERT INTO `goodsiteminfo` VALUES (41, 8, '2021-01-22 17:49:33', 1, 0, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for pictureinfo
-- ----------------------------
DROP TABLE IF EXISTS `pictureinfo`;
CREATE TABLE `pictureinfo` (
                             `pi_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
                             `gbi_id` bigint(20) DEFAULT NULL COMMENT '产品名称ID 例如:某品牌价格红酒',
                             `pi_path` varchar(100) DEFAULT NULL COMMENT '图片地址',
                             `pi_desc` varchar(100) DEFAULT NULL COMMENT '图片描述',
                             `pi_type` int(11) NOT NULL DEFAULT '1' COMMENT '图片类型 1小图 2大图 3.详情',
                             `pi_valid` int(11) NOT NULL DEFAULT '1' COMMENT '0不可用 1可用',
                             `pi_sort` int(11) DEFAULT '1' COMMENT '图片排序',
                             `pi_createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             `pi_updatetime` timestamp NULL DEFAULT NULL,
                             `pi_operator` varchar(20) DEFAULT NULL,
                             PRIMARY KEY (`pi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='图片信息与商品关联(goodsbaseinfo)';

-- ----------------------------
-- Records of pictureinfo
-- ----------------------------
BEGIN;
INSERT INTO `pictureinfo` VALUES (1, 1, 'goodsMainpic_1610887358.png', NULL, 1, 0, 1, '2021-01-17 20:09:57', '2021-01-17 20:09:57', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (2, 1, 'goodsOtherpic_1610893445.jpeg', NULL, 1, 0, 2, '2021-01-17 20:09:57', '2021-01-17 20:09:57', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (3, 1, 'goodsOtherpic_1610887835.jpeg', NULL, 1, 0, 3, '2021-01-17 20:09:57', '2021-01-17 20:09:57', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (4, 1, 'goodsDetailpic_1610891616.jpeg', NULL, 3, 0, 1, '2021-01-17 20:09:57', '2021-01-17 20:09:57', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (10, 3, 'goodsMainpic_1610900440.jpeg', NULL, 1, 0, 1, '2021-01-17 22:42:39', '2021-01-17 22:42:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (11, 3, 'goodsOtherpic_1610903696.jpeg', NULL, 1, 0, 2, '2021-01-17 22:42:39', '2021-01-17 22:42:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (12, 3, 'goodsDetailpic_1610902217.jpeg', NULL, 3, 0, 1, '2021-01-17 22:42:39', '2021-01-17 22:42:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (13, 10004, 'goodsMainpic_1610903196.png', NULL, 1, 1, 1, '2021-01-17 23:11:37', '2021-01-17 23:11:37', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (14, 10004, 'goodsOtherpic_1610902458.jpeg', NULL, 1, 1, 2, '2021-01-17 23:11:37', '2021-01-17 23:11:37', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (15, 10004, 'goodsOtherpic_1610897381.png', NULL, 1, 1, 3, '2021-01-17 23:11:37', '2021-01-17 23:11:37', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (16, 10004, 'goodsOtherpic_1610898120.png', NULL, 1, 1, 4, '2021-01-17 23:11:37', '2021-01-17 23:11:37', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (17, 10004, 'goodsOtherpic_1610903379.png', NULL, 1, 1, 5, '2021-01-17 23:11:37', '2021-01-17 23:11:37', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (18, 10004, 'goodsDetailpic_1610902148.jpeg', NULL, 3, 1, 1, '2021-01-17 23:11:37', '2021-01-17 23:11:37', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (19, 2, 'goodsMainpic_1610906575.png', NULL, 1, 0, 1, '2021-01-17 23:24:41', '2021-01-17 23:24:41', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (20, 2, 'goodsOtherpic_1610902434.png', NULL, 1, 0, 2, '2021-01-17 23:25:04', '2021-01-17 23:25:04', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (21, 2, 'goodsOtherpic_1610902616.png', NULL, 1, 0, 3, '2021-01-17 23:25:04', '2021-01-17 23:25:04', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (22, 2, 'goodsOtherpic_1610900093.png', NULL, 1, 0, 4, '2021-01-17 23:25:04', '2021-01-17 23:25:04', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (23, 2, 'goodsDetailpic_1610899727.jpeg', NULL, 3, 0, 1, '2021-01-17 23:25:34', '2021-01-17 23:25:34', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (24, 10005, 'goodsMainpic_1610968336.png', NULL, 1, 1, 1, '2021-01-18 16:28:31', '2021-01-18 16:28:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (25, 10005, 'goodsOtherpic_1610961360.png', NULL, 1, 1, 2, '2021-01-18 16:28:31', '2021-01-18 16:28:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (26, 10005, 'goodsOtherpic_1610965992.png', NULL, 1, 1, 3, '2021-01-18 16:28:31', '2021-01-18 16:28:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (27, 10005, 'goodsDetailpic_1610967446.jpeg', NULL, 3, 0, 1, '2021-01-18 16:28:31', '2021-01-18 16:28:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (28, 10005, 'goodsDetailpic_1610962289.jpeg', NULL, 3, 1, 1, '2021-01-18 16:36:56', '2021-01-18 16:36:56', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (29, 10006, 'goodsMainpic_1610960975.png', NULL, 1, 0, 1, '2021-01-18 16:47:31', '2021-01-18 16:47:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (30, 10006, 'goodsOtherpic_1610966822.png', NULL, 1, 0, 2, '2021-01-18 16:47:31', '2021-01-18 16:47:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (31, 10006, 'goodsOtherpic_1610966970.png', NULL, 1, 0, 3, '2021-01-18 16:47:31', '2021-01-18 16:47:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (32, 10006, 'goodsDetailpic_1610966788.jpeg', NULL, 3, 0, 1, '2021-01-18 16:47:31', '2021-01-18 16:47:31', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (33, 10006, 'goodsDetailpic_1610964735.png', NULL, 3, 0, 1, '2021-01-18 16:48:35', '2021-01-18 16:48:35', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (34, 10009, 'goodsMainPic_1611040479.png', NULL, 1, 1, 1, '2021-01-19 14:50:24', '2021-01-19 14:50:24', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (35, 10009, 'goodsOtherPic_1611045631.jpeg', NULL, 1, 1, 2, '2021-01-19 14:50:24', '2021-01-19 14:50:24', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (36, 10009, 'goodsOtherPic_1611040595.jpeg', NULL, 1, 1, 3, '2021-01-19 14:50:24', '2021-01-19 14:50:24', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (37, 10009, 'goodsDetailPic_1611041671.jpeg', NULL, 3, 1, 1, '2021-01-19 14:50:24', '2021-01-19 14:50:24', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (38, 10010, 'goodsMainPic_1611041399.jpeg', NULL, 1, 1, 1, '2021-01-19 14:53:09', '2021-01-19 14:53:09', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (39, 10010, 'goodsOtherPic_1611043127.jpeg', NULL, 1, 1, 2, '2021-01-19 14:53:09', '2021-01-19 14:53:09', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (40, 10010, 'goodsOtherPic_1611040697.jpeg', NULL, 1, 1, 3, '2021-01-19 14:53:09', '2021-01-19 14:53:09', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (41, 10010, 'goodsDetailPic_1611044573.jpeg', NULL, 3, 1, 1, '2021-01-19 14:53:09', '2021-01-19 14:53:09', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (42, 10011, 'goodsMainPic_1611048158.png', NULL, 1, 0, 1, '2021-01-19 15:06:06', '2021-01-19 15:06:06', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (43, 10011, 'goodsOtherPic_1611046150.png', NULL, 1, 0, 2, '2021-01-19 15:06:06', '2021-01-19 15:06:06', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (44, 10011, 'goodsOtherPic_1611042009.png', NULL, 1, 0, 3, '2021-01-19 15:06:06', '2021-01-19 15:06:06', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (45, 10011, 'goodsDetailPic_1611046745.jpeg', NULL, 3, 0, 1, '2021-01-19 15:06:06', '2021-01-19 15:06:06', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (46, 10011, 'goodsMainPic_1611043414.png', NULL, 1, 1, 1, '2021-01-19 15:12:50', '2021-01-19 15:12:50', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (47, 10011, 'goodsOtherPic_1611046333.jpeg', NULL, 1, 1, 2, '2021-01-19 15:12:50', '2021-01-19 15:12:50', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (48, 10011, 'goodsDetailPic_1611045906.jpeg', NULL, 3, 1, 1, '2021-01-19 15:12:50', '2021-01-19 15:12:50', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (49, 10012, 'goodsMainPic_1611043997.jpeg', NULL, 1, 1, 1, '2021-01-19 15:17:59', '2021-01-19 15:17:59', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (50, 10012, 'goodsOtherPic_1611043383.jpeg', NULL, 1, 1, 2, '2021-01-19 15:17:59', '2021-01-19 15:17:59', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (51, 10012, 'goodsDetailPic_1611048895.jpeg', NULL, 3, 1, 1, '2021-01-19 15:17:59', '2021-01-19 15:17:59', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (52, 10013, 'goodsMainPic_1611051928.png', NULL, 1, 1, 1, '2021-01-19 15:41:39', '2021-01-19 15:41:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (53, 10013, 'goodsOtherPic_1611050530.jpeg', NULL, 1, 1, 2, '2021-01-19 15:41:39', '2021-01-19 15:41:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (54, 10013, 'goodsOtherPic_1611043220.png', NULL, 1, 1, 3, '2021-01-19 15:41:39', '2021-01-19 15:41:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (55, 10013, 'goodsDetailPic_1611046844.jpeg', NULL, 3, 1, 1, '2021-01-19 15:41:39', '2021-01-19 15:41:39', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (56, 1, 'goodsMainPic_1611048784.png', NULL, 1, 0, 1, '2021-01-19 17:02:42', '2021-01-19 17:02:42', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (57, 1, 'goodsOtherPic_1611049690.png', NULL, 2, 0, 2, '2021-01-19 17:02:42', '2021-01-19 17:02:42', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (58, 1, 'goodsOtherPic_1611054437.png', NULL, 2, 0, 3, '2021-01-19 17:02:42', '2021-01-19 17:02:42', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (59, 1, 'goodsDetailPic_1611055276.jpeg', NULL, 3, 0, 1, '2021-01-19 17:02:42', '2021-01-19 17:02:42', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (60, 2, 'goodsMainPic_1611056058.png', NULL, 1, 0, 1, '2021-01-19 17:24:38', '2021-01-19 17:24:38', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (61, 2, 'goodsOtherPic_1611057479.jpeg', NULL, 2, 0, 2, '2021-01-19 17:24:38', '2021-01-19 17:24:38', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (62, 2, 'goodsOtherPic_1611053018.jpeg', NULL, 2, 0, 3, '2021-01-19 17:24:38', '2021-01-19 17:24:38', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (63, 2, 'goodsDetailPic_1611056094.jpeg', NULL, 3, 0, 1, '2021-01-19 17:24:38', '2021-01-19 17:24:38', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (64, 1, 'goodsOtherPic_1611057933.jpeg', NULL, 2, 0, 2, '2021-01-19 18:32:27', '2021-01-19 18:32:27', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (65, 1, 'goodsOtherPic_1611059218.jpeg', NULL, 2, 0, 3, '2021-01-19 18:32:27', '2021-01-19 18:32:27', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (66, 1, 'goodsDetailPic_1611055658.jpeg', NULL, 3, 0, 1, '2021-01-19 18:32:27', '2021-01-19 18:32:27', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (67, 1, 'goodsMainPic_1611055126.png', NULL, 1, 0, 1, '2021-01-19 18:35:51', '2021-01-19 18:35:51', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (68, 1, 'goodsOtherPic_1611057554.jpeg', NULL, 2, 0, 2, '2021-01-19 18:35:51', '2021-01-19 18:35:51', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (69, 1, 'goodsOtherPic_1611061536.jpeg', NULL, 2, 0, 3, '2021-01-19 18:35:51', '2021-01-19 18:35:51', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (70, 1, 'goodsDetailPic_1611055527.jpeg', NULL, 3, 0, 1, '2021-01-19 18:35:51', '2021-01-19 18:35:51', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (71, 2, 'goodsMainPic_1611057220.jpeg', NULL, 1, 0, 1, '2021-01-19 18:38:30', '2021-01-19 18:38:30', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (72, 2, 'goodsOtherPic_1611056532.jpeg', NULL, 2, 0, 2, '2021-01-19 18:38:30', '2021-01-19 18:38:30', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (73, 2, 'goodsOtherPic_1611056150.jpeg', NULL, 2, 0, 3, '2021-01-19 18:38:30', '2021-01-19 18:38:30', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (74, 3, 'goodsMainPic_1611072721.png', NULL, 1, 0, 1, '2021-01-19 22:50:11', '2021-01-19 22:50:11', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (75, 3, 'goodsOtherPic_1611072980.png', NULL, 2, 0, 2, '2021-01-19 22:50:11', '2021-01-19 22:50:11', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (76, 3, 'goodsOtherPic_1611070427.png', NULL, 2, 0, 3, '2021-01-19 22:50:11', '2021-01-19 22:50:11', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (77, 3, 'goodsDetailPic_1611076636.jpeg', NULL, 3, 0, 1, '2021-01-19 22:50:11', '2021-01-19 22:50:11', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (78, 4, 'goodsMainPic_1611077018.png', NULL, 1, 1, 1, '2021-01-19 22:54:40', '2021-01-19 22:54:40', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (79, 4, 'goodsOtherPic_1611072787.jpeg', NULL, 2, 1, 2, '2021-01-19 22:54:40', '2021-01-19 22:54:40', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (80, 4, 'goodsOtherPic_1611074731.jpeg', NULL, 2, 1, 3, '2021-01-19 22:54:40', '2021-01-19 22:54:40', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (81, 4, 'goodsDetailPic_1611072073.jpeg', NULL, 3, 1, 1, '2021-01-19 22:54:40', '2021-01-19 22:54:40', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (82, 5, 'goodsMainPic_1611077494.jpeg', NULL, 1, 0, 1, '2021-01-19 22:56:10', '2021-01-19 22:56:10', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (83, 5, 'goodsOtherPic_1611072646.jpeg', NULL, 2, 0, 2, '2021-01-19 22:56:10', '2021-01-19 22:56:10', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (84, 5, 'goodsOtherPic_1611074194.jpeg', NULL, 2, 0, 3, '2021-01-19 22:56:10', '2021-01-19 22:56:10', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (85, 5, 'goodsDetailPic_1611072658.jpeg', NULL, 3, 0, 1, '2021-01-19 22:56:10', '2021-01-19 22:56:10', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (86, 5, 'goodsMainPic_1611071824.png', NULL, 1, 0, 1, '2021-01-19 22:57:29', '2021-01-19 22:57:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (87, 5, 'goodsOtherPic_1611077505.jpeg', NULL, 2, 1, 2, '2021-01-19 22:57:29', '2021-01-19 22:57:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (88, 5, 'goodsOtherPic_1611073618.jpeg', NULL, 2, 1, 3, '2021-01-19 22:57:29', '2021-01-19 22:57:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (89, 5, 'goodsDetailPic_1611077435.png', NULL, 3, 1, 1, '2021-01-19 22:57:29', '2021-01-19 22:57:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (90, 5, 'goodsMainPic_1611147937.png', NULL, 1, 1, 1, '2021-01-20 20:00:24', '2021-01-20 20:00:24', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (91, 6, 'goodsMainPic_1611219224.png', NULL, 1, 0, 1, '2021-01-21 15:48:17', '2021-01-21 15:48:17', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (92, 6, 'goodsOtherPic_1611224719.jpeg', NULL, 2, 0, 2, '2021-01-21 15:48:17', '2021-01-21 15:48:17', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (93, 6, 'goodsDetailPic_1611216545.jpeg', NULL, 3, 0, 1, '2021-01-21 15:48:17', '2021-01-21 15:48:17', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (94, 7, 'goodsMainPic_1611223548.png', NULL, 1, 1, 1, '2021-01-21 17:46:29', '2021-01-21 17:46:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (95, 7, 'goodsOtherPic_1611227745.png', NULL, 2, 1, 2, '2021-01-21 17:46:29', '2021-01-21 17:46:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (96, 7, 'goodsDetailPic_1611227419.png', NULL, 3, 1, 1, '2021-01-21 17:46:29', '2021-01-21 17:46:29', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (97, 8, 'goodsMainPic_1611317190.png', NULL, 1, 1, 1, '2021-01-22 17:49:33', '2021-01-22 17:49:33', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (98, 8, 'goodsOtherPic_1611311753.jpeg', NULL, 2, 1, 2, '2021-01-22 17:49:33', '2021-01-22 17:49:33', 'wmy_test1');
INSERT INTO `pictureinfo` VALUES (99, 8, 'goodsDetailPic_1611311041.jpeg', NULL, 3, 1, 1, '2021-01-22 17:49:33', '2021-01-22 17:49:33', 'wmy_test1');
COMMIT;

-- ----------------------------
-- Table structure for receiveinfo
-- ----------------------------
DROP TABLE IF EXISTS `receiveinfo`;
CREATE TABLE `receiveinfo` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `ri_picture` varchar(100) DEFAULT NULL COMMENT '支付二维码/图片',
                             `ri_type` int(11) DEFAULT NULL COMMENT '支付类型1支付宝2微信3银行卡',
                             `ri_des` varchar(100) DEFAULT NULL COMMENT '描述',
                             `ri_status` int(11) DEFAULT NULL COMMENT '0不可用 1可用',
                             `ri_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `ri_updatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
                             `ri_operator` varchar(50) DEFAULT NULL COMMENT '操作人',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskinfo
-- ----------------------------
DROP TABLE IF EXISTS `taskinfo`;
CREATE TABLE `taskinfo` (
                          `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                          `cbi_id` bigint(50) DEFAULT NULL COMMENT '用户ID',
                          `gbi_id` bigint(20) DEFAULT NULL COMMENT '商品id',
                          `ti_target` varchar(50) DEFAULT NULL COMMENT '描述',
                          `ti_count` int(11) DEFAULT NULL COMMENT '如每天购买次数10次同类产品;',
                          `ti_status` int(11) NOT NULL DEFAULT '1' COMMENT '0未完成 1已完成 2已生效',
                          `ti_type` int(11) NOT NULL DEFAULT '1' COMMENT '1购买任务2提货任务3特价商品',
                          `ti_starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '规则开始时间',
                          `ti_endtime` timestamp NULL DEFAULT NULL COMMENT '规则结束时间',
                          `ti_updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                          PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务信息';

-- ----------------------------
-- Table structure for tradeconfig
-- ----------------------------
DROP TABLE IF EXISTS `tradeconfig`;
CREATE TABLE `tradeconfig` (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `tc_rate` double DEFAULT NULL COMMENT '费率',
                             `tc_type` int(11) DEFAULT NULL COMMENT '1.提现服务费2.购买任务限制3.提货任务',
                             `tc_valid` int(11) NOT NULL DEFAULT '1' COMMENT '0不可用  1可用',
                             `tc_starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '规则开始时间',
                             `tc_endtime` timestamp NULL DEFAULT NULL COMMENT '规则结束时间',
                             `tc_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `tc_updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `tc_operator` varchar(50) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='交易配置信息 如费率等';

-- ----------------------------
-- Records of tradeconfig
-- ----------------------------
BEGIN;
INSERT INTO `tradeconfig` VALUES (1, 10, 2, 1, '2021-01-19 14:14:37', '2031-01-17 23:11:37', '2021-01-19 14:14:37', NULL, 'zjy');
INSERT INTO `tradeconfig` VALUES (2, 1, 3, 1, '2021-01-19 20:31:59', '2031-01-17 23:11:37', '2021-01-19 20:31:52', NULL, 'zjy');
COMMIT;

-- ----------------------------
-- Table structure for tradegoodsell
-- ----------------------------
DROP TABLE IF EXISTS `tradegoodsell`;
CREATE TABLE `tradegoodsell` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `tgs_id` varchar(50) NOT NULL COMMENT '交易市场卖货单id',
                               `gii_id` bigint(20) DEFAULT NULL COMMENT '物料id',
                               `gbi_id` bigint(20) DEFAULT NULL COMMENT '商品id 例如:某品牌价格红酒',
                               `tgs_sellerid` bigint(20) DEFAULT NULL COMMENT '售卖人cbi_id',
                               `tgs_price` double DEFAULT NULL COMMENT '售卖价格',
                               `tgs_status` int(11) NOT NULL DEFAULT '1' COMMENT '0未交易 1交易中 2交易成功 3已取消 4交易失败',
                               `tgs_saleable` int(11) NOT NULL DEFAULT '1' COMMENT '0未上架 1已上架',
                               `tgs_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `tgs_publishtime` timestamp NULL DEFAULT NULL COMMENT '商品发布时间',
                               `tgs_updatetime` timestamp NULL DEFAULT NULL,
                               `tgs_finshitime` timestamp NULL DEFAULT NULL COMMENT '售卖结束时间',
                               `tgs_type` int(11) DEFAULT NULL COMMENT '1正常商品2新手3特价商品',
                               `tgs_owntype` int(11) NOT NULL DEFAULT '0' COMMENT '1系统2用户',
                               `tgs_servicefee` double DEFAULT NULL COMMENT '卖货服务费金额',
                               `tgs_tradetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '成交时间',
                               `tgs_selltype` int(11) NOT NULL DEFAULT '2' COMMENT '1.委托卖出2正常卖出',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `tgs_id` (`tgs_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='交易市场卖货订';

-- ----------------------------
-- Records of tradegoodsell
-- ----------------------------
BEGIN;
INSERT INTO `tradegoodsell` VALUES (7, '1611048278409gii', 3, 2, 10000, NULL, 4, 1, '2021-01-19 17:24:38', '2021-01-19 17:24:38', '2021-01-22 21:52:43', '2029-12-31 00:00:00', 1, 2, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (8, '1611048278410gii', 4, 2, 10000, NULL, 4, 1, '2021-01-19 17:24:38', '2021-01-19 17:24:38', '2021-01-22 21:52:43', '2029-12-31 00:00:00', 1, 2, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (11, '1611052348368gii_3332', 1, 1, 10000, NULL, 4, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-22 21:52:43', '2031-12-31 00:00:00', 1, 2, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (12, '1611052348368gii_4934', 2, 1, 10000, NULL, 4, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-22 21:52:43', '2031-12-31 00:00:00', 1, 2, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (13, '1611052348368gii_3921', 5, 1, 10000, NULL, 0, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (14, '1611052348368gii_8593', 6, 1, 10000, NULL, 0, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (15, '1611052348368gii_3538', 7, 1, 10000, NULL, 0, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (16, '1611052348368gii_7641', 8, 1, 10000, NULL, 0, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (17, '1611052348368gii_6930', 9, 1, 10000, NULL, 0, 1, '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2021-01-19 18:32:28', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (18, '1611052551890gii_4385', 10, 1, 10000, NULL, 0, 1, '2021-01-19 18:35:51', '2021-01-19 18:35:51', '2021-01-19 18:35:51', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (19, '1611052551890gii_9296', 11, 1, 10000, NULL, 0, 1, '2021-01-19 18:35:51', '2021-01-19 18:35:51', '2021-01-19 18:35:51', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (20, '1611052551890gii_2664', 12, 1, 10000, NULL, 0, 1, '2021-01-19 18:35:51', '2021-01-19 18:35:51', '2021-01-19 18:35:51', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (21, '1611067811226gii_5982', 13, 3, 10003, NULL, 0, 1, '2021-01-19 22:50:11', '2021-01-19 22:50:11', '2021-01-19 22:50:11', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (22, '1611067811226gii_3362', 14, 3, 10003, NULL, 0, 1, '2021-01-19 22:50:11', '2021-01-19 22:50:11', '2021-01-19 22:50:11', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (23, '1611068080301gii_2126', 15, 4, 10003, NULL, 0, 1, '2021-01-19 22:54:40', '2021-01-19 22:54:40', '2021-01-19 22:54:40', '2031-12-31 00:00:00', 2, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (24, '1611068080301gii_3402', 16, 4, 10003, NULL, 0, 1, '2021-01-19 22:54:40', '2021-01-19 22:54:40', '2021-01-19 22:54:40', '2031-12-31 00:00:00', 2, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (25, '1611068170548gii_7542', 17, 5, 10003, NULL, 0, 1, '2021-01-19 22:56:10', '2021-01-19 22:56:10', '2021-01-19 22:56:10', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (26, '1611068170548gii_6904', 18, 5, 10003, NULL, 0, 1, '2021-01-19 22:56:10', '2021-01-19 22:56:10', '2021-01-19 22:56:10', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (27, '1611068197336gii_9035', 19, 5, 10003, NULL, 0, 1, '2021-01-19 22:56:37', '2021-01-19 22:56:37', '2021-01-19 22:56:37', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (28, '1611068197336gii_4706', 20, 5, 10003, NULL, 0, 1, '2021-01-19 22:56:37', '2021-01-19 22:56:37', '2021-01-19 22:56:37', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (29, '1611068249733gii_2193', 21, 5, 10003, NULL, 0, 1, '2021-01-19 22:57:29', '2021-01-19 22:57:29', '2021-01-19 22:57:29', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (30, '1611068249733gii_9279', 22, 5, 10003, NULL, 0, 1, '2021-01-19 22:57:29', '2021-01-19 22:57:29', '2021-01-19 22:57:29', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (31, '1611068249733gii_7164', 23, 5, 10003, NULL, 0, 1, '2021-01-19 22:57:29', '2021-01-19 22:57:29', '2021-01-19 22:57:29', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (32, '1611143222346gii_9994', 24, 5, 10003, NULL, 0, 1, '2021-01-20 19:47:02', '2021-01-20 19:47:02', '2021-01-20 19:47:02', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (33, '1611143222346gii_3561', 25, 5, 10003, NULL, 0, 1, '2021-01-20 19:47:02', '2021-01-20 19:47:02', '2021-01-20 19:47:02', '2031-12-31 00:00:00', 1, 1, NULL, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (34, '1611215297881gii_6815', 26, 6, 10003, NULL, 0, 1, '2021-01-21 15:48:17', '2021-01-21 15:48:17', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (35, '1611215297881gii_5420', 27, 6, 10003, NULL, 1, 1, '2021-01-21 15:48:17', '2021-01-21 15:48:17', '2021-01-21 17:31:57', '2031-12-31 00:00:00', 1, 1, 675, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (36, '1611215721710gii_6226', 28, 6, 10003, NULL, 2, 1, '2021-01-21 15:55:21', '2021-01-21 15:55:21', '2021-01-21 17:05:15', '2031-12-31 00:00:00', 1, 1, 1081, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (37, '1611215721710gii_6705', 29, 6, 10003, NULL, 0, 1, '2021-01-21 15:55:21', '2021-01-21 15:55:21', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (38, '1611219267690gii_8181', 30, 6, 10003, NULL, 0, 1, '2021-01-21 16:54:27', '2021-01-21 16:54:27', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (39, '1611219557989gii_7803', 31, 6, 10003, NULL, 0, 1, '2021-01-21 16:59:17', '2021-01-21 16:59:17', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (40, '1611219707209gii_6403', 32, 6, 10003, NULL, 0, 1, '2021-01-21 17:01:47', '2021-01-21 17:01:47', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (41, '1611219914883gii_9599', 33, 6, 10003, NULL, 0, 1, '2021-01-21 17:05:14', '2021-01-21 17:05:14', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (42, '1611220023885gii_4629', 34, 6, 10003, NULL, 0, 1, '2021-01-21 17:07:03', '2021-01-21 17:07:03', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (43, '1611221550463gii_8583', 35, 6, 10003, NULL, 0, 1, '2021-01-21 17:32:30', '2021-01-21 17:32:30', '2021-01-21 17:32:30', '2031-12-31 00:00:00', 1, 1, 945, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (44, '1611222389355gii_9568', 36, 7, 10003, NULL, 0, 1, '2021-01-21 17:46:29', '2021-01-21 17:46:29', '2021-01-21 18:08:20', '2031-12-31 00:00:00', 2, 1, 81.54, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (45, '1611222389355gii_4857', 37, 7, 10003, NULL, 1, 1, '2021-01-21 17:46:29', '2021-01-21 17:46:29', '2021-01-21 18:08:20', '2031-12-31 00:00:00', 2, 1, 81.54, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (46, '1611223498417gii_5181', 38, 7, 10003, NULL, 2, 1, '2021-01-21 18:04:58', '2021-01-21 18:04:58', '2021-01-21 18:04:58', '2031-12-31 00:00:00', 2, 1, 54.36, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (47, '1611223700777gii_5049', 39, 7, 10003, NULL, 0, 1, '2021-01-21 18:08:20', '2021-01-21 18:08:20', '2021-01-21 18:08:20', '2031-12-31 00:00:00', 2, 1, 81.54, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (48, '161130897378340', 40, 8, 10003, NULL, 0, 1, '2021-01-22 17:49:33', '2021-01-22 17:49:33', '2021-01-22 17:49:33', '2031-12-31 00:00:00', 1, 1, 5.4, '0000-00-00 00:00:00', 2);
INSERT INTO `tradegoodsell` VALUES (49, '161130897378341', 41, 8, 10003, NULL, 0, 1, '2021-01-22 17:49:33', '2021-01-22 17:49:33', '2021-01-22 17:49:33', '2031-12-31 00:00:00', 1, 1, 5.4, '0000-00-00 00:00:00', 2);
COMMIT;

-- ----------------------------
-- Table structure for tradeorderinfo
-- ----------------------------
DROP TABLE IF EXISTS `tradeorderinfo`;
CREATE TABLE `tradeorderinfo` (
                                `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `toi_orderid` varchar(50) NOT NULL COMMENT '交易市场订单id',
                                `tgs_id` varchar(50) NOT NULL COMMENT '交易市场卖货单id',
                                `gii_id` bigint(20) DEFAULT NULL COMMENT '物料id',
                                `gbi_id` bigint(20) DEFAULT NULL COMMENT '商品id 例如:某品牌价格红酒',
                                `tgs_buyerid` bigint(20) DEFAULT NULL COMMENT '买货人cbi_id',
                                `toi_sellerid` bigint(20) DEFAULT NULL COMMENT '售卖人cbi_id',
                                `toi_price` double NOT NULL COMMENT '售卖价格',
                                `toi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1未付款 2已付款 3交易完成 4.交易失败',
                                `toi_tradetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `toi_updatetime` timestamp NULL DEFAULT NULL,
                                `toi_buyservicefee` double DEFAULT NULL COMMENT '买方服务费金额',
                                `toi_sellservicefee` double DEFAULT NULL COMMENT '卖货服务费金额',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `toi_orderid` (`toi_orderid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易市场买货订单';

-- ----------------------------
-- Table structure for tradepredictinfo
-- ----------------------------
DROP TABLE IF EXISTS `tradepredictinfo`;
CREATE TABLE `tradepredictinfo` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                  `tpi_id` varchar(50) NOT NULL COMMENT '预购单ID',
                                  `gbi_id` bigint(20) DEFAULT NULL COMMENT '商品id 例如:某品牌价格红酒',
                                  `tpi_buyerid` bigint(20) DEFAULT NULL COMMENT '买货人cbi_id',
                                  `tpi_price` double NOT NULL COMMENT '预购价格',
                                  `tpi_num` int(11) DEFAULT NULL COMMENT '购买数量',
                                  `tpi_sucessnum` int(11) DEFAULT NULL COMMENT '成功购买数量',
                                  `tpi_type` int(11) NOT NULL DEFAULT '1' COMMENT '1委托买入 2正常买入',
                                  `tpi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1已委托 2 已取消 3部分成功 4交易成功 5交易失败',
                                  `tpi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `tpi_buytime` timestamp NULL DEFAULT NULL COMMENT '预购买时间',
                                  `tpi_finishtime` timestamp NULL DEFAULT NULL COMMENT '预购完成时间',
                                  `tpi_tradetime` timestamp NULL DEFAULT NULL COMMENT '撮合交易完成时间',
                                  `tpi_updatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
                                  `tpi_servicefee` double DEFAULT NULL COMMENT '预购服务费金额',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `tpi_id` (`tpi_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易预购买表';

-- ----------------------------
-- Table structure for tradetime
-- ----------------------------
DROP TABLE IF EXISTS `tradetime`;
CREATE TABLE `tradetime` (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                           `tt_time_am_start` varchar(11) DEFAULT NULL COMMENT '上午交易开始时间',
                           `tt_time_am_end` varchar(20) DEFAULT NULL COMMENT '上午交易结束时间',
                           `tt_time_pm_start` varchar(11) DEFAULT NULL COMMENT '下午交易开始时间',
                           `tt_time_pm_end` varchar(20) DEFAULT NULL COMMENT '下午交易结束时间',
                           `tt_status` int(11) NOT NULL DEFAULT '1' COMMENT '0不可用  1可用',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='交易时间配置';

-- ----------------------------
-- Records of tradetime
-- ----------------------------
BEGIN;
INSERT INTO `tradetime` VALUES (2, '10:00:00', '12:00:00', '13:00:00', '16:00:00', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
