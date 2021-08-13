# ISSUE TRACKING
缺陷问题单管理系统  
![GitHub](https://img.shields.io/github/license/crazyone2one/issue-track-manage)
![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/crazyone2one/issue-track-manage?include_prereleases)
## 技术选型
* 后端技术栈
  > spring boot  
  > mybatis-plus
* 前端技术
  > thymeleaf  
  > bootstrap
* 数据库
  > mysql
## 使用说明
* 使用git clone本项目，或者下载源码
~~~
git clone git@github.com:crazyone2one/issue-track-manage.git
~~~
* 创建数据库，名称随意。后执行[issuetrack.sql](https://github.com/crazyone2one/issue-track-manage/blob/main/sql/issuetrack.sql) 脚本创建表  
* 在表type_item中添加相应的数据字段，如
~~~sql
INSERT INTO `type_item` (`id`, `type_code`, `type_name`, `type_group`) VALUES ('08b49f5aac40ee3d8d5f24cfe89cce0c', '1', '轻微', 'severity_level');
INSERT INTO `type_item` (`id`, `type_code`, `type_name`, `type_group`) VALUES ('2baec787d9ca53bdb25a7ffeed7abd01', '2', '一般', 'severity_level');
INSERT INTO `type_item` (`id`, `type_code`, `type_name`, `type_group`) VALUES ('403677375c877104018c90ba8255c38d', '3', '严重', 'severity_level');
INSERT INTO `type_item` (`id`, `type_code`, `type_name`, `type_group`) VALUES ('e88f78fd0b9121af7b2e359b5c257317', '4', '致命', 'severity_level');
~~~
* 修改配置信息：application.yml文件
> 1. 项目端口
  ~~~yaml
  # 项目默认端口为1024 ，修改为你想使用的端口
  server:
    port: 1024
  ~~~
> 2. 数据库连接信息
  ~~~yaml
  spring:
    datasource：
      url: jdbc:mysql://localhost:3306/issuetrack?useUnicode=true&characterEncoding=UTF-8
      driver-class-name: com.mysql.cj.jdbc.Driver
      username: root
      password: admin
  ~~~
* 启动项目，访问http://localhost:1024/ 即可访问系统
## 更新日志
~~~text
2021/8/5
 >  问题单页面列表
 >  问题单汇总页面列表

2021/8/13
 >  完成基本功能，可实现数据的录入及简单的查询
~~~
## 版权信息
该项目签署了MIT 授权许可，详情请参阅[LICENSE](https://github.com/crazyone2one/issue-track-manage/blob/main/LICENSE)
