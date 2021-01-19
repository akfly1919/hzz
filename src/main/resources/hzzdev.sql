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

 Date: 19/01/2021 10:36:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for broadcastnoteinfo
-- ----------------------------
DROP TABLE IF EXISTS `broadcastnoteinfo`;
CREATE TABLE `broadcastnoteinfo` (
  `bni_id` int(11) NOT NULL AUTO_INCREMENT,
  `bni_postion` int(11) DEFAULT NULL COMMENT '位置 1首页轮播 2交易大厅轮播 3 通知广播',
  `bni_picture` varchar(150) DEFAULT NULL COMMENT '轮播图片',
  `bni_title` varchar(50) DEFAULT NULL COMMENT '标题',
  `bni_desc` varchar(150) DEFAULT NULL COMMENT '描述',
  `bni_url` varchar(150) DEFAULT NULL COMMENT '链接',
  `bni_sort` int(11) DEFAULT '1' COMMENT '排序',
  `bni_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bni_updatetime` timestamp NULL DEFAULT NULL,
  `bni_valid` int(11) DEFAULT '1' COMMENT '0不可用 1可用',
  PRIMARY KEY (`bni_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮播信息';

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
  PRIMARY KEY (`cbi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8 COMMENT='用户基础信息';

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
  `cgr_islock` int(11) NOT NULL DEFAULT '1' COMMENT '0未锁住 1锁住 放入售卖表变成锁住',
  `cgr_ispickup` int(11) NOT NULL DEFAULT '0' COMMENT '0未提货 1已提货',
  `cgr_buytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cgr_selltime` timestamp NULL DEFAULT NULL,
  `cgr_updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户商品物料对应表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户身份证信息';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户提货表';

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
  PRIMARY KEY (`gbi_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10007 DEFAULT CHARSET=utf8 COMMENT='商品基础信息-用于展示';

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
) ENGINE=InnoDB AUTO_INCREMENT=11035 DEFAULT CHARSET=utf8 COMMENT='商品物料信息-最细颗粒度';

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='图片信息与商品关联(goodsbaseinfo)';

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
  `ti_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
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
  `tc_type` int(11) DEFAULT NULL COMMENT '1.服务费',
  `tc_valid` int(11) NOT NULL DEFAULT '1' COMMENT '0不可用  1可用',
  `tc_startime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '规则开始时间',
  `tc_endtime` timestamp NULL DEFAULT NULL COMMENT '规则结束时间',
  `tc_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tc_updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `tc_operator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易配置信息 如费率等';

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
  `tgs_status` int(11) NOT NULL DEFAULT '1' COMMENT '0未交易 1交易中 2交易成功 3已取消',
  `tgs_saleable` int(11) NOT NULL DEFAULT '1' COMMENT '0未上架 1已上架',
  `tgs_createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tgs_publishtime` timestamp NULL DEFAULT NULL COMMENT '商品发布时间',
  `tgs_updatetime` timestamp NULL DEFAULT NULL,
  `tgs_finshitime` timestamp NULL DEFAULT NULL COMMENT '售卖结束时间',
  `tgs_type` int(11) DEFAULT NULL COMMENT '1正常商品2新手3特价商品',
  `tgs_owntype` int(11) DEFAULT NULL COMMENT '1系统2用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tgs_id` (`tgs_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易市场卖货订';

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
  `toi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1未付款 2已付款 3 取消交易',
  `toi_buytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
  `toi_updatetime` timestamp NULL DEFAULT NULL,
  `toi_finishtime` timestamp NULL DEFAULT NULL COMMENT '完成交易时间',
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
  `tgs_id` varchar(50) NOT NULL COMMENT '交易市场卖货单id',
  `gbi_id` bigint(20) DEFAULT NULL COMMENT '商品id 例如:某品牌价格红酒',
  `tpi_buyerid` bigint(20) DEFAULT NULL COMMENT '买货人cbi_id',
  `tpi_price` double NOT NULL COMMENT '售卖价格',
  `tpi_num` int(11) DEFAULT NULL COMMENT '购买数量',
  `tpi_type` int(11) NOT NULL DEFAULT '1' COMMENT '1委托买入 2正常买入',
  `tpi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1已委托 2 已取消 3交易成功 4交易失败',
  `tpi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tpi_buytime` timestamp NULL DEFAULT NULL COMMENT '预购买时间',
  `tpi_updatetime` timestamp NULL DEFAULT NULL,
  `tpi_finishtime` timestamp NULL DEFAULT NULL COMMENT '完成交易时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tpi_id` (`tpi_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易预购买表';

-- ----------------------------
-- Table structure for tradetime
-- ----------------------------
DROP TABLE IF EXISTS `tradetime`;
CREATE TABLE `tradetime` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tt_starttime` timestamp NULL DEFAULT NULL COMMENT '交易开始时间,截取此表时分秒对比',
  `tt_endtime` timestamp NULL DEFAULT NULL COMMENT '交易结束时间, 截取此表时分秒对比',
  `tt_status` int(11) NOT NULL DEFAULT '1' COMMENT '0不可用  1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易时间配置';

SET FOREIGN_KEY_CHECKS = 1;
