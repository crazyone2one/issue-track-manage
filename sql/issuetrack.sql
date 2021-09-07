/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50732
Source Host           : 127.0.0.1:3306
Source Database       : issuetrack

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2021-09-07 16:42:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for issue_item
-- ----------------------------
DROP TABLE IF EXISTS `issue_item`;
CREATE TABLE `issue_item` (
  `issue_id` varchar(36) NOT NULL COMMENT '主键id',
  `project_id` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `module` varchar(255) DEFAULT NULL COMMENT '所属模块',
  `function_desc` varchar(500) DEFAULT NULL COMMENT '功能点',
  `title_desc` varchar(500) DEFAULT NULL COMMENT '标题',
  `severity` varchar(10) DEFAULT NULL COMMENT '严重程度(1:轻微,2:一般,3:严重,4:致命)',
  `owner` varchar(20) DEFAULT NULL COMMENT '测试人员',
  `status` varchar(10) DEFAULT NULL COMMENT '状态',
  `issue_date` varchar(255) DEFAULT NULL COMMENT '月份',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`issue_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题单表';

-- ----------------------------
-- Table structure for issue_project
-- ----------------------------
DROP TABLE IF EXISTS `issue_project`;
CREATE TABLE `issue_project` (
  `project_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `project_code` varchar(255) DEFAULT NULL COMMENT '项目code',
  `create_data` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `module_id` varchar(255) DEFAULT NULL COMMENT '模块名称id',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题单对应的项目表';

-- ----------------------------
-- Table structure for issue_summary
-- ----------------------------
DROP TABLE IF EXISTS `issue_summary`;
CREATE TABLE `issue_summary` (
  `summary_id` varchar(36) NOT NULL COMMENT '主键id',
  `project_id` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `summary_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `create_case_count` int(255) DEFAULT NULL COMMENT '编写用例数量',
  `execute_case_count` int(255) DEFAULT NULL COMMENT '执行测试用例数量',
  `bug_doc` tinyint(4) DEFAULT '0' COMMENT 'bug文档',
  `report_doc` tinyint(4) DEFAULT '0' COMMENT '测试报告',
  `has_doc` tinyint(4) DEFAULT '0' COMMENT '需求文档',
  `job_status` varchar(255) DEFAULT '0' COMMENT '完成状态',
  `delivery_status` varchar(255) DEFAULT NULL COMMENT '交付状态',
  `owner` varchar(255) DEFAULT NULL COMMENT '负责人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `issue_date` varchar(20) DEFAULT NULL COMMENT '任务时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`summary_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题汇总表';

-- ----------------------------
-- Table structure for summary_case_ref
-- ----------------------------
DROP TABLE IF EXISTS `summary_case_ref`;
CREATE TABLE `summary_case_ref` (
  `id` int(36) NOT NULL,
  `summary_id` int(36) DEFAULT NULL,
  `case_id` int(36) DEFAULT NULL,
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
  `issue_date` varchar(36) DEFAULT NULL COMMENT '问题单时间',
  `create_bug_slight` int(128) DEFAULT '0' COMMENT '新建轻微级别问题单数量',
  `create_bug_ordinary` int(128) DEFAULT '0' COMMENT '新建一般级别问题单数量',
  `create_bug_severity` int(128) DEFAULT '0' COMMENT '新建严重级别问题单数量',
  `create_bug_deadly` int(128) DEFAULT '0' COMMENT '新建致命级别问题单数量',
  `review_bug_slight` int(128) DEFAULT '0' COMMENT '回测轻微级别问题单数量',
  `review_bug_ordinary` int(128) DEFAULT '0' COMMENT '回测一般级别问题单数量',
  `review_bug_severity` int(128) DEFAULT '0' COMMENT '回测严重级别问题单数量',
  `review_bug_deadly` int(128) DEFAULT '0' COMMENT '回测致命级别问题单数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关系表';

-- ----------------------------
-- Table structure for test_case
-- ----------------------------
DROP TABLE IF EXISTS `test_case`;
CREATE TABLE `test_case` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `case_project_id` varchar(36) DEFAULT NULL COMMENT '项目id',
  `case_suite_id` varchar(36) DEFAULT NULL COMMENT '用例集名称id',
  `case_title` varchar(50) DEFAULT NULL COMMENT '测试用例标题',
  `case_summary` varchar(100) DEFAULT NULL COMMENT '摘要',
  `case_precondition` varchar(100) DEFAULT NULL COMMENT '前提条件',
  `case_steps` varchar(500) DEFAULT NULL COMMENT '测试步骤',
  `case_expected_results` varchar(500) DEFAULT NULL COMMENT '预期结果',
  `case_run` tinyint(4) DEFAULT '0' COMMENT '是否执行',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for type_item
-- ----------------------------
DROP TABLE IF EXISTS `type_item`;
CREATE TABLE `type_item` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `type_code` varchar(15) DEFAULT NULL COMMENT '字典编码',
  `type_name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `type_group` varchar(36) DEFAULT NULL COMMENT '字典组ID',
  `type_group_name` varchar(12) DEFAULT NULL COMMENT 'group name',
  `delete_flag` varchar(12) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';
