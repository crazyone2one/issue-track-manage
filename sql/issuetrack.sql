/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50732
Source Host           : 127.0.0.1:3306
Source Database       : issuetrack

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2021-08-04 11:41:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for issue_item
-- ----------------------------
DROP TABLE IF EXISTS `issue_item`;
CREATE TABLE `issue_item` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `module` varchar(255) DEFAULT NULL COMMENT '所属模块',
  `function_desc` varchar(500) DEFAULT NULL COMMENT '功能点',
  `title_desc` varchar(500) DEFAULT NULL COMMENT '标题',
  `severity` varchar(10) DEFAULT NULL COMMENT '严重程度',
  `owner` varchar(20) DEFAULT NULL COMMENT '测试人员',
  `status` varchar(10) DEFAULT NULL COMMENT '状态',
  `data` varchar(255) DEFAULT NULL COMMENT '月份',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for issue_summary
-- ----------------------------
DROP TABLE IF EXISTS `issue_summary`;
CREATE TABLE `issue_summary` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `job_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `create_case_count` varchar(500) DEFAULT NULL COMMENT '编写用例数量',
  `execute_case_count` varchar(500) DEFAULT NULL COMMENT '执行测试用例数量',
  `bug_doc` varchar(255) DEFAULT NULL COMMENT 'bug文档',
  `report_doc` varchar(255) DEFAULT NULL COMMENT '测试报告',
  `has_doc` varchar(255) DEFAULT NULL COMMENT '需求文档',
  `job_status` varchar(255) DEFAULT NULL COMMENT '完成状态',
  `delivery_status` varchar(255) DEFAULT NULL COMMENT '交付状态',
  `owner` varchar(255) DEFAULT NULL COMMENT '负责人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for summary_item_ref
-- ----------------------------
DROP TABLE IF EXISTS `summary_item_ref`;
CREATE TABLE `summary_item_ref` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `summary_id` varchar(36) DEFAULT NULL COMMENT 'summary表主键id',
  `item_id` varchar(36) DEFAULT NULL COMMENT 'item表主键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for type_group
-- ----------------------------
DROP TABLE IF EXISTS `type_group`;
CREATE TABLE `type_group` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `type_group_code` varchar(50) DEFAULT NULL COMMENT '字典分组编码',
  `type_group_name` varchar(50) DEFAULT NULL COMMENT '字典分组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for type_item
-- ----------------------------
DROP TABLE IF EXISTS `type_item`;
CREATE TABLE `type_item` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `type_code` varchar(50) DEFAULT NULL COMMENT '字典编码',
  `type_name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `type_group_id` varchar(36) DEFAULT NULL COMMENT '字典组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
