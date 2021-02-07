/*
Navicat MySQL Data Transfer

Source Server         : 192.144.230.48_hzz
Source Server Version : 50650
Source Host           : 192.144.230.48:3306
Source Database       : hzzdev

Target Server Type    : MYSQL
Target Server Version : 50650
File Encoding         : 65001

Date: 2021-02-05 21:35:00
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='轮播信息';

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户地址信息';

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
) ENGINE=InnoDB AUTO_INCREMENT=10020 DEFAULT CHARSET=utf8 COMMENT='用户基础信息';

-- ----------------------------
-- Table structure for customerbillrelated
-- ----------------------------
DROP TABLE IF EXISTS `customerbillrelated`;
CREATE TABLE `customerbillrelated` (
                                       `cbr_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                       `cbi_id` bigint(20) NOT NULL COMMENT '用户ID',
                                       `cbrorderid` varchar(50) NOT NULL COMMENT '传入支付ID/交易id/提现id',
                                       `cbr_money` double NOT NULL COMMENT '金额',
                                       `cbr_type` int(11) NOT NULL DEFAULT '1' COMMENT '1收入  2支出',
                                       `cbr_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `cbr_updatetime` timestamp NULL DEFAULT NULL,
                                       PRIMARY KEY (`cbr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='客户账单流水对应表';

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
                                       `ccoi_account` varchar(250) DEFAULT NULL COMMENT '提现账户信息',
                                       `ccoi_type` int(11) DEFAULT NULL COMMENT '账户类型 1支付宝 2微信 3银行卡',
                                       `ccoi_amount` double DEFAULT NULL COMMENT '提现金额',
                                       `ccoi_desc` varchar(100) DEFAULT NULL COMMENT '描述',
                                       `ccoi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1待审核2已审核3已拒绝4审核通过5已打款 6打款失败',
                                       `ccoi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `ccoi_updatetime` timestamp NULL DEFAULT NULL,
                                       `ccoi_finishtime` timestamp NULL DEFAULT NULL,
                                       `ccoi_operator` varchar(50) DEFAULT NULL COMMENT '审核人',
                                       PRIMARY KEY (`ccoi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户提现表';

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
                                        `cgr_forzentime` timestamp NULL DEFAULT NULL COMMENT '解冻时间',
                                        PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='客户商品物料对应表';

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户身份证信息';

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
                                   `cpi_channel` int(11) DEFAULT '0' COMMENT '充值渠道1.weixin, 2.Alipay_online 3Alipay_offline',
                                   `cpi_channelorderid` varchar(100) DEFAULT NULL COMMENT '充值平台返回的订单号',
                                   `cpi_operator` varchar(50) DEFAULT NULL COMMENT '审核人',
                                   PRIMARY KEY (`cpi_orderid`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='用户充值信息';

-- ----------------------------
-- Table structure for customerpickupinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerpickupinfo`;
CREATE TABLE `customerpickupinfo` (
                                      `cpui_orderid` bigint(20) NOT NULL COMMENT '提货单号',
                                      `cbi_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
                                      `gbi_id` bigint(20) DEFAULT NULL COMMENT '例如:某品牌价格红酒',
                                      `cai_id` int(11) DEFAULT NULL COMMENT '用户地址ID',
                                      `cpui_num` bigint(20) DEFAULT NULL COMMENT '商品数量',
                                      `cpui_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `cpui_updatetime` timestamp NULL DEFAULT NULL,
                                      `cpui_finishtime` timestamp NULL DEFAULT NULL,
                                      `cpui_status` int(11) DEFAULT '0' COMMENT '0待审核 1已审批(已发货) 2拒绝',
                                      `cpui_trackingnumber` varchar(100) DEFAULT NULL COMMENT '快递单号',
                                      `cpui_tracktype` varchar(100) DEFAULT NULL COMMENT '快递名称/类型',
                                      PRIMARY KEY (`cpui_orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户提货表';


DROP TABLE IF EXISTS `customerpickupdetail`;
CREATE TABLE `customerpickupdetail` (
                                        `cpui_orderid` bigint(20) NOT NULL COMMENT '提货单号',
                                        `gii_id` bigint(20) DEFAULT NULL COMMENT '物料id',
                                        PRIMARY KEY (`cpui_orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户提货明细表';


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
-- Table structure for dimday
-- ----------------------------
DROP TABLE IF EXISTS `dimday`;
CREATE TABLE `dimday` (
                          `dayid` varchar(10) NOT NULL,
                          `daydesc` varchar(14) DEFAULT NULL,
                          `weekid` varchar(100) DEFAULT NULL,
                          `weekshortdesc` varchar(100) DEFAULT NULL,
                          `weeklongdesc` varchar(100) DEFAULT NULL,
                          `monthid` varchar(100) DEFAULT NULL,
                          `monthdesc` varchar(100) DEFAULT NULL,
                          `quarterid` varchar(100) DEFAULT NULL,
                          `quarterdesc` varchar(100) DEFAULT NULL,
                          `yearid` varchar(100) DEFAULT NULL,
                          `yeardesc` varchar(100) DEFAULT NULL,
                          PRIMARY KEY (`dayid`),
                          KEY `daydesc` (`daydesc`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日期维度';

-- ----------------------------
-- Table structure for goodsbaseinfo
-- ----------------------------
DROP TABLE IF EXISTS `goodsbaseinfo`;
CREATE TABLE `goodsbaseinfo` (
                                 `gbi_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品名称ID 例如:某品牌价格红酒',
                                 `gbi_name` varchar(100) DEFAULT NULL COMMENT '产品名称',
                                 `gbi_cid` int(11) DEFAULT '1' COMMENT '1酒类2茶水 3电子设备',
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
  `gbi_frozendays` int(11) DEFAULT NULL COMMENT '售卖冻结天数',
  PRIMARY KEY (`gbi_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='商品基础信息-用于展示';

-- ----------------------------
-- Table structure for goodscategrey
-- ----------------------------
DROP TABLE IF EXISTS `goodscategrey`;
CREATE TABLE `goodscategrey` (
                                 `gc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `gc_cate` varchar(50) NOT NULL COMMENT '所属类型',
                                 `gc_createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `gc_updatetime` timestamp NULL DEFAULT NULL,
                                 PRIMARY KEY (`gc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='商品类型表';

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
) ENGINE=InnoDB AUTO_INCREMENT=1737 DEFAULT CHARSET=utf8 COMMENT='商品物料信息-最细颗粒度';

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
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8 COMMENT='图片信息与商品关联(goodsbaseinfo)';

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='收款二维码';

-- ----------------------------
-- Table structure for reporttradedate
-- ----------------------------
DROP TABLE IF EXISTS `reporttradedate`;
CREATE TABLE `reporttradedate` (
                                   `id` int(10) NOT NULL AUTO_INCREMENT,
                                   `rti_year` int(11) DEFAULT NULL COMMENT '年',
                                   `rti_month` int(11) DEFAULT NULL COMMENT '月',
                                   `rti_week` int(11) DEFAULT NULL COMMENT '周',
                                   `rti_date` int(11) DEFAULT NULL COMMENT '日期',
                                   `rti_hour` int(11) DEFAULT NULL COMMENT '0-24',
                                   `rti_gbid` bigint(20) DEFAULT NULL COMMENT '物品id',
                                   `rit_gbiname` varchar(100) DEFAULT NULL COMMENT '物品名称',
                                   `rti_num` int(150) DEFAULT NULL COMMENT '交易量',
                                   `rti_money` double(50,0) DEFAULT NULL COMMENT '交易金额',
  `rti_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rti_updatetime` timestamp NULL DEFAULT NULL,
  KEY `idx_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1505 DEFAULT CHARSET=utf8 COMMENT='交易行情日期';

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
                            `ti_type` int(11) NOT NULL DEFAULT '1' COMMENT '1购买任务2提货任务3特价商品4任务完成',
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
                               `tc_rate` double DEFAULT NULL COMMENT '费率，任务目标数量',
                               `tc_type` int(11) DEFAULT NULL COMMENT '1.提现服务费2.购买任务数量3.提货任务数量4.特价商品数量5买入手续费，6卖出手续费',
                               `tc_valid` int(11) NOT NULL DEFAULT '1' COMMENT '0不可用  1可用',
                               `tc_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `tc_updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                               `tc_operator` varchar(50) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='交易配置信息 如费率等';

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
) ENGINE=InnoDB AUTO_INCREMENT=1712 DEFAULT CHARSET=utf8 COMMENT='交易市场卖方预卖表';

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
                                  `toi_type` int(11) DEFAULT NULL COMMENT '1.正常商品2新手商品3特价',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `toi_orderid` (`toi_orderid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易市场成交订单表';

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
                                    `tpi_status` int(11) NOT NULL DEFAULT '1' COMMENT '1已委托,初始状态 2 已取消 3部分成功 4交易成功 5交易失败 6部分成功已取消',
                                    `tpi_createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `tpi_buytime` timestamp NULL DEFAULT NULL COMMENT '预购买时间',
                                    `tpi_finishtime` timestamp NULL DEFAULT NULL COMMENT '预购完成时间',
                                    `tpi_tradetime` timestamp NULL DEFAULT NULL COMMENT '撮合交易完成时间',
                                    `tpi_updatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
                                    `tpi_servicefee` double DEFAULT NULL COMMENT '预购服务费金额',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `tpi_id` (`tpi_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='交易市场买方预买表';

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='交易时间配置';

-- ----------------------------
-- Procedure structure for f_dimday
-- ----------------------------
DROP PROCEDURE IF EXISTS `f_dimday`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `f_dimday`(in start_date VARCHAR(20),in end_date VARCHAR(20))
begin
declare i int;
set i=0;
DELETE from dimday;
while DATE_FORMAT(start_date,'%Y-%m-%d %H:%i:%s') < DATE_FORMAT(end_date,'%Y-%m-%d %H:%i:%s') do
INSERT into dimday
(dayid,daydesc,weekid,weekshortdesc,weeklongdesc,monthid,monthdesc,quarterid,quarterdesc,yearid,yeardesc)
SELECT
    REPLACE(start_date,'-','') dayid,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y-%m-%d') daydesc,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y%u') weekid,
    case DAYOFWEEK(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'))  when 1 then 'sunday' when 2 then 'monday' when 3 then 'tuesday' when 4 then 'wednesday' when 5 then 'thursday' when 6 then 'friday' when 7 then 'saturday' end weekshortdesc,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y年第%u周') weeklongdesc,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y%m') monthid,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y-%m') monthdesc,
    CONCAT(DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y'),quarter(STR_TO_DATE( start_date,'%Y-%m-%d %H:%i:%s'))) quarterid,
    CONCAT(DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y'),'年第',quarter(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s')),'季度') quarterdesc,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y') yearid,
    DATE_FORMAT(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),'%Y年') yeardesc
from dual;
set i=i+1;
set start_date = DATE_FORMAT(date_add(STR_TO_DATE(start_date,'%Y-%m-%d %H:%i:%s'),interval 1 day),'%Y-%m-%d');
end while;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for test_report
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_report`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `test_report`( )
BEGIN
DECLARE
i INT DEFAULT 0;
WHILE
i < 100 DO
INSERT INTO `hzzdev`.`reporttradedate` ( `rti_year`, `rti_month`, `rti_week`, `rti_date`, `rti_hour`, `rti_gbid`, `rit_gbiname`, `rti_num`, `rti_money`, `rti_createtime`, `rti_updatetime` )
VALUES
	( 2021, 202101, 202101, 20210103,  FLOOR(Rand()*23) ,(SELECT
	gbi_id
FROM
	goodsbaseinfo
WHERE
	gbi_valid = 1 ORDER BY RAND() LIMIT 1), 'test1',FLOOR(RAND( ) * 100 ),
 FLOOR( RAND( ) * 1000 )
, now(), NULL );
SET i = i + 1;

END WHILE;
update reporttradedate r inner join goodsbaseinfo g
on r.rti_gbid=g.gbi_id
    set r.rit_gbiname=g.gbi_name, r.rti_money=r.rti_num*g.gbi_price;

delete from reporttradedate
where
        (rti_date, rti_gbid,rit_gbiname,rti_hour) IN (
        SELECT
            t.rti_date,t.rti_gbid,t.rit_gbiname,
            t.rti_hour
        FROM
            (
                SELECT
                    rti_date,					rti_gbid,
                    rit_gbiname,

                    rti_hour
                FROM
                    reporttradedate
                GROUP BY
                    rti_date,					rti_gbid,
                    rit_gbiname,

                    rti_hour
                HAVING
                        count(1) > 1
            ) t
    )
  AND
        id not in ( select id from (
                                       select min(id) id from reporttradedate
                                       GROUP BY
                                           `rti_date`, `rti_hour`, `rti_gbid`, `rit_gbiname`   HAVING
                                               count(*) > 1     -- 根据关键字查询出重复数据其中最小的id 数据 保留不删除，其他重复数据删除
                                   )tt
    );
END
;;
DELIMITER ;
