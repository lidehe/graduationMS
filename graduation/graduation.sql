/*
SQLyog v10.2 
MySQL - 5.5.28 : Database - graduation_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`graduation_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `graduation_db`;

/*Table structure for table `tb_account` */

DROP TABLE IF EXISTS `tb_account`;

CREATE TABLE `tb_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_account` varchar(20) DEFAULT NULL COMMENT '账号',
  `va_password` varchar(20) DEFAULT NULL COMMENT '密码',
  `va_type` int(11) DEFAULT '1' COMMENT '身份，1学生0老师(管理员是教职工的一个角色)',
  PRIMARY KEY (`id`),
  KEY `accountIndex` (`va_account`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_account` */

insert  into `tb_account`(`id`,`va_account`,`va_password`,`va_type`) values (1,'1302443113','1302443113',1),(2,'1302443112','1302443112',1),(3,'1302443114','1302443114',1),(4,'B14041525','B14041525',0);

/*Table structure for table `tb_byfs` */

DROP TABLE IF EXISTS `tb_byfs`;

CREATE TABLE `tb_byfs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_name` varchar(30) DEFAULT NULL COMMENT '毕业方式名称，例如论文、专利',
  `va_yi` varchar(30) DEFAULT '空' COMMENT '该方式第一阶段工作名；指导老师申报课题',
  `va_er` varchar(30) DEFAULT '空' COMMENT '该方式第二阶段工作名；专业负责人审核课题',
  `va_san` varchar(30) DEFAULT '空' COMMENT '该方式第三阶段工作名；学生选择课题或申报自主课题',
  `va_si` varchar(30) DEFAULT '空' COMMENT '第四阶段工作名；专业负责人审核学生申报的课题',
  `va_wu` varchar(30) DEFAULT '空' COMMENT '第五阶段工作名；指导老师撰写任务书',
  `va_liu` varchar(30) DEFAULT '空' COMMENT '第六阶段工作名；学生填写开题报告',
  `va_qi` varchar(30) DEFAULT '空' COMMENT '第七阶段工作名；查看开题报告',
  `va_ba` varchar(30) DEFAULT '空' COMMENT '第八阶段工作名；提交阶段性成果',
  `va_jiu` varchar(30) DEFAULT '空' COMMENT '第九阶段工作名；老师填写线上指导日志',
  `va_shi` varchar(30) DEFAULT '空' COMMENT '第十阶段工作名；学生与老师交流',
  `va_shiyi` varchar(30) DEFAULT '空' COMMENT '十一阶段工作名；提交初稿',
  `va_shier` varchar(30) DEFAULT '空' COMMENT '十二阶段工作名；指导老师提出修改意见',
  `va_shisan` varchar(30) DEFAULT '空' COMMENT '十三阶段工作名；修改论文并交流',
  `va_shisi` varchar(30) DEFAULT '空' COMMENT '十四阶段工作名；提交最终结果',
  `va_isPass1` int(11) DEFAULT '0' COMMENT '1代表模块二被忽略，0没有',
  `va_isPass2` int(11) DEFAULT '0' COMMENT '同上，模块三',
  `va_isPass3` int(11) DEFAULT '0' COMMENT '同上，模块四',
  `va_start1` date DEFAULT NULL COMMENT '模块一适合的起始日期',
  `va_end1` date DEFAULT NULL COMMENT '模块一适合的截止日期',
  `va_start2` date DEFAULT NULL COMMENT '模块二适合的起始日期',
  `va_end2` date DEFAULT NULL COMMENT '模块二适合的截止日期',
  `va_start3` date DEFAULT NULL COMMENT '模块三适合的起始日期',
  `va_end3` date DEFAULT NULL COMMENT '模块三适合的截止日期',
  `va_start4` date DEFAULT NULL COMMENT '模块四适合的起始日期',
  `va_end4` date DEFAULT NULL COMMENT '模块四适合的截止日期',
  `va_start5` date DEFAULT NULL COMMENT '模块五适合的起始日期',
  `va_end5` date DEFAULT NULL COMMENT '模块五适合的截止日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_byfs` */

insert  into `tb_byfs`(`id`,`va_name`,`va_yi`,`va_er`,`va_san`,`va_si`,`va_wu`,`va_liu`,`va_qi`,`va_ba`,`va_jiu`,`va_shi`,`va_shiyi`,`va_shier`,`va_shisan`,`va_shisi`,`va_isPass1`,`va_isPass2`,`va_isPass3`,`va_start1`,`va_end1`,`va_start2`,`va_end2`,`va_start3`,`va_end3`,`va_start4`,`va_end4`,`va_start5`,`va_end5`) values (1,'普通毕业设计方式','选题阶段','选择自己想要做的课题','尚未选择课题','选择的课题是','开题阶段','查看指导老师下发的任务书，撰写开题报告','查看任务书：','阶段性成果','上传阶段性成果文件，并与老师交流','阶段性成果','初步成果','提交初步成果文件，并与老师交流','初步成果交流','最终成果',1,1,1,'2017-03-01','2017-03-31','2017-04-01','2017-04-30','2017-05-01','2017-05-31','2017-06-01','2017-06-08','2017-06-09','2017-06-30'),(2,'发明专利方式','填写专利名','审核专利','尚未选择专利','选择的专利是','填写专利概括','专利申请报告','空','提交专利材料','线上交流','中期交流','提交专利证书','空','空','提交最终的专利成果',1,0,0,'2017-03-01','2017-03-31','2017-04-01','2017-04-30','2017-05-01','2017-05-31','2017-06-01','2017-06-08','2017-06-09','2017-06-30');

/*Table structure for table `tb_cjbd` */

DROP TABLE IF EXISTS `tb_cjbd`;

CREATE TABLE `tb_cjbd` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '抽检绑定表',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '绑定的教师工号',
  `va_yx` int(11) DEFAULT NULL COMMENT '允许查看的院系',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_cjbd` */

insert  into `tb_cjbd`(`id`,`va_gonghao`,`va_yx`,`va_year`) values (1,'B14041525',1,2017);

/*Table structure for table `tb_cjk` */

DROP TABLE IF EXISTS `tb_cjk`;

CREATE TABLE `tb_cjk` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '抽检库表',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学号',
  `va_yx` int(11) DEFAULT NULL COMMENT '院系',
  `va_zy` int(11) DEFAULT NULL COMMENT '专业',
  `va_dj` int(11) DEFAULT NULL COMMENT '原来五分制的成绩',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_kt` int(11) DEFAULT NULL COMMENT '课题',
  PRIMARY KEY (`id`),
  KEY `va_xuehao` (`va_xuehao`,`va_yx`,`va_zy`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_cjk` */

/*Table structure for table `tb_cjrz` */

DROP TABLE IF EXISTS `tb_cjrz`;

CREATE TABLE `tb_cjrz` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '抽检记录表',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '教师工号',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_dj` int(11) DEFAULT NULL COMMENT '教师打的五分制成绩',
  `va_text` text COMMENT '评语',
  `va_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `va_xuehao` (`va_xuehao`,`va_dj`,`va_gonghao`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_cjrz` */

/*Table structure for table `tb_dbjsz` */

DROP TABLE IF EXISTS `tb_dbjsz`;

CREATE TABLE `tb_dbjsz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_zh` int(11) DEFAULT '0' COMMENT '组号',
  `va_jsgh` varchar(20) DEFAULT NULL COMMENT '教师工号',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一届',
  `va_yx` int(11) DEFAULT '0' COMMENT '对应哪个系的',
  `va_zz` int(11) DEFAULT '0' COMMENT '此人是否是组长，1是，0否',
  PRIMARY KEY (`id`),
  KEY `va_jsgh` (`va_jsgh`,`va_zh`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_dbjsz` */

insert  into `tb_dbjsz`(`id`,`va_zh`,`va_jsgh`,`va_year`,`va_yx`,`va_zz`) values (1,1,'B14041525',2017,1,1);

/*Table structure for table `tb_dbpsb` */

DROP TABLE IF EXISTS `tb_dbpsb`;

CREATE TABLE `tb_dbpsb` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答辩评审表成绩值表',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '工号',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学号',
  `va_dbpsbx` int(11) DEFAULT NULL COMMENT '哪一项',
  `va_fs` float DEFAULT NULL COMMENT '此老师此项打的分数',
  `va_text` text COMMENT '文本项的值',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_xuehao`,`va_dbpsbx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_dbpsb` */

/*Table structure for table `tb_dbpsbx` */

DROP TABLE IF EXISTS `tb_dbpsbx`;

CREATE TABLE `tb_dbpsbx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答辩评审项值表',
  `va_text` varchar(200) DEFAULT NULL COMMENT '项目名',
  `va_mf` int(11) DEFAULT '0' COMMENT '此项满分值',
  `va_status` int(11) DEFAULT '0' COMMENT '0是分数项，1文本项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_dbpsbx` */

insert  into `tb_dbpsbx`(`id`,`va_text`,`va_mf`,`va_status`) values (1,'态度认真，工作刻苦，严格遵守各项纪律，表现出色',15,0),(2,'按时、全面、独立地完成毕业设计（论文）的各项任务，表现出较强的综合分析问题和解决问题的能力完成课题任务的质量情况',20,0),(3,'立论正确，设计方案合理，分析透彻，解决问题方案恰当，结论正确，并且有一定创新性，有较大的实用价值',25,0),(4,'概念使用正确，语言表达准确，结构严谨，条理清楚，逻辑性强',15,0),(5,'书写工整，写作格式规范，符合有关规定，图表和图纸制作规范，能够执行国家有关标准',15,0),(6,'原始数据搜集得当，计算结论准确',10,0),(7,' 评语：',0,1);

/*Table structure for table `tb_dbzl` */

DROP TABLE IF EXISTS `tb_dbzl`;

CREATE TABLE `tb_dbzl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师答辩组表中的组号',
  `va_bz` varchar(40) DEFAULT NULL COMMENT '标识',
  `va_type` int(11) DEFAULT '0' COMMENT '答辩组的标识，默认是0，代表普通答辩组，1代表延迟答辩组',
  `va_start` date DEFAULT NULL COMMENT '起始时间',
  `va_end` date DEFAULT NULL COMMENT '截止时间',
  `va_ms` varchar(200) DEFAULT NULL COMMENT '答辩秘书的名字，分隔符隔开',
  `va_sj` varchar(300) DEFAULT NULL COMMENT '答辩秘书的联系方式，顺序对应，分隔符隔开',
  `va_yx` int(11) DEFAULT NULL,
  `va_zy` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `va_type` (`va_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_dbzl` */

insert  into `tb_dbzl`(`id`,`va_bz`,`va_type`,`va_start`,`va_end`,`va_ms`,`va_sj`,`va_yx`,`va_zy`) values (1,'答辩组1',0,'2017-07-01','2017-07-05','第一个答辩组，要树立好我们的形象','15295532007',1,1);

/*Table structure for table `tb_gzzj` */

DROP TABLE IF EXISTS `tb_gzzj`;

CREATE TABLE `tb_gzzj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '所写教师的工号',
  `va_yx` int(11) DEFAULT '0' COMMENT '所属院系id',
  `va_year` int(11) DEFAULT '0' COMMENT '哪一届',
  `va_time` datetime DEFAULT NULL COMMENT '时间',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原文件名',
  `va_url` varchar(30) DEFAULT NULL COMMENT '文件类型和路径',
  `va_status` int(11) DEFAULT '0' COMMENT '0文件未上传，1已上传',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_yx`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_gzzj` */

/*Table structure for table `tb_jdxcg` */

DROP TABLE IF EXISTS `tb_jdxcg`;

CREATE TABLE `tb_jdxcg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_xs` varchar(20) DEFAULT NULL COMMENT '所属学生学号',
  `va_status` int(11) DEFAULT '0' COMMENT '1表示文件已上传，0没有',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原文件名称',
  PRIMARY KEY (`id`),
  KEY `va_xs` (`va_xs`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_jdxcg` */

insert  into `tb_jdxcg`(`id`,`va_xs`,`va_status`,`va_filename`) values (1,'1302443112',1,'陆超_南京邮电大学_15295532376.pdf');

/*Table structure for table `tb_jdxcgs` */

DROP TABLE IF EXISTS `tb_jdxcgs`;

CREATE TABLE `tb_jdxcgs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '阶段性成果文件的id',
  `va_xs` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_jdxcg` int(11) DEFAULT NULL COMMENT '对应哪一条阶段性成果记录',
  `va_status` int(11) DEFAULT '0' COMMENT '0还未上传1，上传好了',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原来的文件名',
  `va_url` varchar(30) DEFAULT NULL COMMENT '存储的url，主要用来记录文件类型',
  PRIMARY KEY (`id`),
  KEY `va_jdxcg` (`va_jdxcg`,`va_xs`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_jdxcgs` */

insert  into `tb_jdxcgs`(`id`,`va_xs`,`va_jdxcg`,`va_status`,`va_filename`,`va_url`) values (1,'1302443112',1,1,'test.pdf','pdf'),(2,'1302443112',1,1,'test.pdf','pdf');

/*Table structure for table `tb_js` */

DROP TABLE IF EXISTS `tb_js`;

CREATE TABLE `tb_js` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_name` varchar(30) DEFAULT NULL COMMENT '角色（身份）名称（起到权限的作用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `tb_js` */

insert  into `tb_js`(`id`,`va_name`) values (1,'指导老师'),(2,'专业负责人'),(3,'教学秘书'),(4,'院系负责人'),(5,'管理员'),(6,'校级评优人员'),(7,'抽检人员'),(8,'答辩组成员'),(9,'校领导，督导'),(10,'院系评优人员');

/*Table structure for table `tb_jsgx` */

DROP TABLE IF EXISTS `tb_jsgx`;

CREATE TABLE `tb_jsgx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_number` varchar(20) DEFAULT NULL COMMENT '老师工号',
  `va_jsid` int(11) DEFAULT '0' COMMENT '对应的角色（身份）id',
  PRIMARY KEY (`id`),
  KEY `va_number` (`va_number`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `tb_jsgx` */

insert  into `tb_jsgx`(`id`,`va_number`,`va_jsid`) values (1,'B14041525',1),(2,'B14041525',2),(3,'B14041525',3),(4,'B14041525',4),(5,'B14041525',5),(6,'B14041525',6),(7,'B14041525',7),(8,'B14041525',8),(9,'B14041525',9),(10,'B14041525',10);

/*Table structure for table `tb_kt` */

DROP TABLE IF EXISTS `tb_kt`;

CREATE TABLE `tb_kt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_zyfzr` varchar(20) DEFAULT NULL COMMENT '专业负责人工号',
  `va_zdls` varchar(20) DEFAULT NULL COMMENT '指导老师工号',
  `va_fzrs` int(11) DEFAULT '0' COMMENT '专业负责人审核状态1过0否',
  `va_zdlss` int(11) DEFAULT '0' COMMENT '指导老师审核状态1过0否',
  `va_name` varchar(80) DEFAULT NULL COMMENT '课题名称',
  `va_ms` varchar(200) DEFAULT NULL COMMENT '课题描述',
  `va_sx` int(11) DEFAULT '0' COMMENT '课题属性，1老师申报0学生申报的',
  `va_yx` int(11) DEFAULT '0' COMMENT '院系id',
  `va_zy` int(11) DEFAULT '0' COMMENT '对应的专业id',
  `va_xzrs` int(11) DEFAULT '1' COMMENT '限制人数',
  `va_year` int(11) DEFAULT '0' COMMENT '哪一届',
  `va_bylx` int(11) DEFAULT '0' COMMENT '对应哪种毕业方式的',
  PRIMARY KEY (`id`),
  KEY `va_zyfzr` (`va_zyfzr`,`va_zdls`,`va_year`,`va_yx`,`va_zy`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_kt` */

insert  into `tb_kt`(`id`,`va_zyfzr`,`va_zdls`,`va_fzrs`,`va_zdlss`,`va_name`,`va_ms`,`va_sx`,`va_yx`,`va_zy`,`va_xzrs`,`va_year`,`va_bylx`) values (1,'B14041525','B14041525',1,1,'酒店预定系统','就是做一个酒店预定系统；对桌子，订单能够实时管理',1,1,1,2,2017,1),(2,'B14041525','B14041525',1,1,'图书管理系统','学校的图书馆流量大，藏书多；管理不方便。可以借用这样的系统高效地管理。',0,1,1,1,2017,1),(3,'B14041525','B14041525',2,1,'新的，测试一下','新的，测试一下新的，测试一下新的，测试一下新的，测试一下',1,1,1,2,2017,1),(4,'B14041525','B14041525',0,1,'超市收银web系统','现代化生活离不开计算机，计算机进入了生活的方方面面。以web方面技术为基础，做一个超市收银和仓管系统。',1,1,1,3,2017,1);

/*Table structure for table `tb_ktbg` */

DROP TABLE IF EXISTS `tb_ktbg`;

CREATE TABLE `tb_ktbg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_xs` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_zy` int(11) DEFAULT '0' COMMENT '学生的专业id',
  `va_kt` int(11) DEFAULT '0' COMMENT '对应课题的id',
  `va_ls` varchar(20) DEFAULT NULL COMMENT '对应指导老师的工号',
  `va_status` int(11) DEFAULT '0' COMMENT '1表示文件已经上传，0没有',
  `va_sh` int(11) DEFAULT '0' COMMENT '1审核通过',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原来的文件名',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一届',
  PRIMARY KEY (`id`),
  KEY `va_xs` (`va_xs`,`va_ls`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_ktbg` */

insert  into `tb_ktbg`(`id`,`va_xs`,`va_zy`,`va_kt`,`va_ls`,`va_status`,`va_sh`,`va_filename`,`va_year`) values (1,'1302443112',1,1,'B14041525',1,1,'test.pdf',2017);

/*Table structure for table `tb_lw` */

DROP TABLE IF EXISTS `tb_lw`;

CREATE TABLE `tb_lw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_xsxh` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_zdls` varchar(20) DEFAULT NULL COMMENT '学生的指导老师工号',
  `va_yx` int(11) DEFAULT '0' COMMENT '学生所属院系id',
  `va_zy` int(11) DEFAULT '0' COMMENT '学生所选专业',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一届',
  `va_status` int(11) DEFAULT '0' COMMENT '1表示已经文件，0未提交任何',
  `va_kt` int(11) DEFAULT '0' COMMENT '对应课题id',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原文件名',
  PRIMARY KEY (`id`),
  KEY `va_xsxh` (`va_xsxh`,`va_zdls`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_lw` */

insert  into `tb_lw`(`id`,`va_xsxh`,`va_zdls`,`va_yx`,`va_zy`,`va_year`,`va_status`,`va_kt`,`va_filename`) values (1,'1302443112','B14041525',1,1,2017,1,1,'test.pdf');

/*Table structure for table `tb_lwcg` */

DROP TABLE IF EXISTS `tb_lwcg`;

CREATE TABLE `tb_lwcg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `va_xsxh` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_zdls` varchar(20) DEFAULT NULL COMMENT '老师工号',
  `va_yx` int(11) DEFAULT NULL COMMENT '院系',
  `va_zy` int(11) DEFAULT NULL COMMENT '专业',
  `va_status` int(11) DEFAULT NULL COMMENT '0未上传，1已上传了',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原文件名',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_kt` int(11) DEFAULT NULL COMMENT '课题的id',
  PRIMARY KEY (`id`),
  KEY `va_xsxh` (`va_xsxh`,`va_zdls`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_lwcg` */

insert  into `tb_lwcg`(`id`,`va_xsxh`,`va_zdls`,`va_yx`,`va_zy`,`va_status`,`va_filename`,`va_year`,`va_kt`) values (1,'1302443112','B14041525',1,1,1,'陆超_南京邮电大学_15295532376.pdf',2017,1);

/*Table structure for table `tb_lws` */

DROP TABLE IF EXISTS `tb_lws`;

CREATE TABLE `tb_lws` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'lw附件的自增id',
  `va_lw` int(11) DEFAULT NULL COMMENT '对应的lwid',
  `va_status` int(11) DEFAULT NULL COMMENT '状态0未上传，1已上传',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '文件名',
  `va_url` varchar(30) DEFAULT NULL COMMENT '文件才路径，含有类型',
  `va_xuehao` varchar(20) DEFAULT NULL,
  `va_year` int(11) DEFAULT NULL COMMENT '年',
  PRIMARY KEY (`id`),
  KEY `va_xuehao` (`va_xuehao`,`va_lw`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_lws` */

insert  into `tb_lws`(`id`,`va_lw`,`va_status`,`va_filename`,`va_url`,`va_xuehao`,`va_year`) values (1,1,1,'陆超_南京邮电大学_15295532376.pdf','pdf','1302443112',2017),(2,1,1,'test.pdf','pdf','1302443112',2017);

/*Table structure for table `tb_notice` */

DROP TABLE IF EXISTS `tb_notice`;

CREATE TABLE `tb_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `va_title` varchar(80) DEFAULT NULL COMMENT '公告的标题',
  `va_text` text COMMENT '公告的内容',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '老师的工号',
  `va_yx` int(11) DEFAULT '0' COMMENT '如果是院系公告，院系id',
  `va_zy` int(11) DEFAULT '0' COMMENT '如果是专业公告，专业id',
  `va_type` int(11) DEFAULT '0' COMMENT '公告类型，0指导老师的，1专业的，2院系的，3学校的，4就是附件上传不成功的或者有问题的',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_time` datetime DEFAULT NULL COMMENT '发表的时间',
  PRIMARY KEY (`id`),
  KEY `va_type` (`va_type`,`va_yx`,`va_zy`,`va_gonghao`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `tb_notice` */

insert  into `tb_notice`(`id`,`va_title`,`va_text`,`va_gonghao`,`va_yx`,`va_zy`,`va_type`,`va_year`,`va_time`) values (1,'关于开展2017年度毕业设计的有关通知','<font size=\"5\"><span style=\"white-space:pre\">	</span>我在站内已经上传了一些课题，大家如果想做别的课题，可以直接在站内申请；我这边会根据实际情况审核的。</font><div><font size=\"5\"><span style=\"white-space:pre\">	</span>谢谢大家合作！</font></div>','B14041525',1,0,0,2017,'2017-10-21 22:01:09'),(4,'我们学校关于2017年XXXXXXXXXXXX的的的的的的','没啥，我就随便写写','B14041525',0,0,3,2017,'2017-10-21 22:01:09'),(5,'我们学校关于2017年XXXXXXXXXXXX的的的的的的','没啥，我就随便写写','B14041525',0,0,3,2017,'2017-10-21 22:01:09'),(6,'我们学校关于2017年XXXXXXXXXXXX的的的的的的','没啥，我就随便写写','B14041525',0,0,3,2017,'2017-10-21 22:01:09'),(7,'111111111','1111111111111','B14041525',1,0,3,2017,'2017-10-21 22:01:09'),(8,'学习的啊','<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">学习的啊学习的啊学习的啊学习的啊学习的啊学习的啊学习的啊学你妈臭逼</span></font>','B14041525',1,0,0,2017,'2017-10-21 22:01:09');

/*Table structure for table `tb_notices` */

DROP TABLE IF EXISTS `tb_notices`;

CREATE TABLE `tb_notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `va_notice` int(11) DEFAULT NULL COMMENT '对应哪个公告的id',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '文件的名称',
  `va_url` varchar(30) DEFAULT NULL COMMENT '保存的文件名，包括文件类型',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_status` int(11) DEFAULT NULL COMMENT '0未上传成果的，1上传成功的',
  PRIMARY KEY (`id`),
  KEY `va_notice` (`va_notice`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_notices` */

insert  into `tb_notices`(`id`,`va_notice`,`va_filename`,`va_url`,`va_year`,`va_status`) values (1,7,'test.pdf','pdf',2017,1),(2,7,'陆超_南京邮电大学_15295532376.pdf','pdf',2017,1);

/*Table structure for table `tb_pybd` */

DROP TABLE IF EXISTS `tb_pybd`;

CREATE TABLE `tb_pybd` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评优人员绑定表',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '工号',
  `va_yx` int(11) DEFAULT NULL COMMENT '可以查看的院系',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_pybd` */

insert  into `tb_pybd`(`id`,`va_gonghao`,`va_yx`,`va_year`) values (1,'B14041525',1,2017);

/*Table structure for table `tb_pypsb` */

DROP TABLE IF EXISTS `tb_pypsb`;

CREATE TABLE `tb_pypsb` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评阅教师评审成绩值表',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '工号',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学号',
  `va_pypsbx` int(11) DEFAULT NULL COMMENT '哪一项的',
  `va_fs` float DEFAULT NULL COMMENT '此老师此项打的分数',
  `va_text` text COMMENT '如果是文本项，写在这里',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_xuehao`,`va_pypsbx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_pypsb` */

/*Table structure for table `tb_pypsbx` */

DROP TABLE IF EXISTS `tb_pypsbx`;

CREATE TABLE `tb_pypsbx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评阅评审表项值表',
  `va_text` varchar(200) DEFAULT NULL COMMENT '项目名',
  `va_mf` int(11) DEFAULT '0' COMMENT '满分值',
  `va_status` int(11) DEFAULT '0' COMMENT '0代表成绩项，1是文本项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_pypsbx` */

insert  into `tb_pypsbx`(`id`,`va_text`,`va_mf`,`va_status`) values (1,'按时、全面、独立地完成毕业设计（论文）的各项任务，表现出较强的综合分析问题和解决问题的能力',20,0),(2,'立论正确，设计方案合理，分析透彻，解决问题方案恰当，结论正确。',25,0),(3,'具有一定的创新性，具有较大的实用价值和推广价值',15,0),(4,'概念使用正确，语言表达准确，结构严谨，条理清楚，逻辑性强',15,0),(5,'书写工整，写作格式规范，符合有关规定，图表和图纸制作规范，能够执行国家有关标准',15,0),(6,'原始数据搜集得当，计算结论准确',10,0),(7,'评语：',0,1);

/*Table structure for table `tb_pyrz` */

DROP TABLE IF EXISTS `tb_pyrz`;

CREATE TABLE `tb_pyrz` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评优记录表',
  `va_lw` int(11) DEFAULT NULL COMMENT '论文的id，如果是个人的话',
  `va_gx` int(11) DEFAULT '0' COMMENT '0代表个人，1，代表是小组的',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学号，如果有的话',
  `va_zuhao` int(11) DEFAULT '0' COMMENT '组号，如果有',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '打分的老师工号',
  `va_time` datetime DEFAULT NULL COMMENT '记录的时间',
  `va_text` text COMMENT '点评',
  `va_fs` float DEFAULT NULL COMMENT '分数',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_xy` int(11) DEFAULT '0' COMMENT '0代表院里的，1代表校里的记录',
  PRIMARY KEY (`id`),
  KEY `va_gx` (`va_gx`,`va_xuehao`,`va_zuhao`,`va_gonghao`,`va_xy`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_pyrz` */

/*Table structure for table `tb_rws` */

DROP TABLE IF EXISTS `tb_rws`;

CREATE TABLE `tb_rws` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_zdls` varchar(20) DEFAULT NULL COMMENT '指导老师工号',
  `va_kt` int(11) DEFAULT '0' COMMENT '对应的课题id',
  `va_zy` int(11) DEFAULT '0' COMMENT '对应的专业id',
  `va_status` int(11) DEFAULT '0' COMMENT '状态，保留字段',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原来的文件名',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一届',
  PRIMARY KEY (`id`),
  KEY `va_zdls` (`va_zdls`,`va_kt`,`va_zy`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_rws` */

insert  into `tb_rws`(`id`,`va_zdls`,`va_kt`,`va_zy`,`va_status`,`va_filename`,`va_year`) values (1,'B14041525',1,1,1,'酒店管理系统的任务书.pdf',2017);

/*Table structure for table `tb_ssjl` */

DROP TABLE IF EXISTS `tb_ssjl`;

CREATE TABLE `tb_ssjl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '师生交流表',
  `va_writer` varchar(20) DEFAULT NULL COMMENT '写信人的号，学号或工号',
  `va_reader` varchar(20) DEFAULT NULL COMMENT '收信人的号',
  `va_text` text COMMENT '交流内容',
  `va_time` datetime DEFAULT NULL COMMENT '时间',
  `va_status` int(11) DEFAULT '0' COMMENT '0学生写的已读，1未读，2，老师写的已读，3未读',
  PRIMARY KEY (`id`),
  KEY `va_writer` (`va_writer`,`va_reader`,`va_status`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `tb_ssjl` */

insert  into `tb_ssjl`(`id`,`va_writer`,`va_reader`,`va_text`,`va_time`,`va_status`) values (1,'B14041525','1302443112','test','2017-07-20 18:36:36',2),(2,'B14041525','1302443112','zailai一条','2017-07-20 19:01:09',2),(3,'B14041525','1302443112','gggg','2017-07-20 19:04:29',2),(4,'B14041525','1302443112','ccccccccccccc','2017-07-20 19:05:11',2),(5,'B14041525','1302443112','aaaaaaaaaaaa','2017-07-20 19:05:59',2),(6,'1302443112','B14041525','回复','2017-07-21 11:35:20',0),(7,'1302443112','B14041525','时间是多少啊？','2017-08-03 10:55:27',0),(8,'1302443112','B14041525','你说这个时间什么鬼','2017-08-03 10:58:47',0),(9,'1302443112','B14041525','再来看看，还有鬼吗','2017-08-03 11:01:13',0),(10,'1302443112','B14041525','终于知道什么鬼了','2017-08-03 11:03:52',0),(11,'B14041525','1302443112','哦，你知道就行','2017-08-03 11:04:15',2),(12,'B14041525','1302443113','hello 李德和','2017-08-04 15:27:17',2),(13,'1302443112','B14041525','没问题吧','2017-09-19 16:16:33',0);

/*Table structure for table `tb_student` */

DROP TABLE IF EXISTS `tb_student`;

CREATE TABLE `tb_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_number` varchar(20) DEFAULT NULL COMMENT '学号',
  `va_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `va_sex` int(11) DEFAULT '0' COMMENT '1男0女',
  `va_id` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `va_yxid` int(11) DEFAULT '0' COMMENT '对应的院系id',
  `va_zyid` int(11) DEFAULT '0' COMMENT '对应的专业id',
  `va_class` varchar(50) DEFAULT NULL COMMENT '所在班级名称',
  `va_telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `va_teacher` varchar(20) DEFAULT NULL COMMENT '指导老师的工号',
  `va_dbid` int(11) DEFAULT '0' COMMENT '所属答辩组的id',
  `va_group` int(11) DEFAULT '0' COMMENT '是否组队，1组队，0没组',
  `va_stage` int(11) DEFAULT '0' COMMENT '学生当前毕设工作的进度',
  `va_type` int(11) DEFAULT '-1' COMMENT '学生选择的毕设类型id',
  `va_ktid` int(11) DEFAULT '0' COMMENT '所选课题id',
  `va_ktbgid` int(11) DEFAULT '0' COMMENT '所写开题报告id',
  `va_jdxid` int(11) DEFAULT '0' COMMENT '所写阶段性成果id',
  `va_bsid` int(11) DEFAULT '0' COMMENT '所写毕设稿id（对应论文）',
  `va_answer` int(11) DEFAULT '0' COMMENT '答辩状态，0就代表正常还未参加初次答辩的，1不参加初次答辩的，2初次未过等待下次答辩的，3最终答辩也未通过，4答辩通过了',
  `va_year` int(11) DEFAULT '0' COMMENT '哪一届',
  PRIMARY KEY (`id`),
  KEY `va_number` (`va_number`,`va_zyid`,`va_yxid`,`va_class`,`va_teacher`,`va_dbid`,`va_stage`,`va_answer`,`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tb_student` */

insert  into `tb_student`(`id`,`va_number`,`va_name`,`va_sex`,`va_id`,`va_yxid`,`va_zyid`,`va_class`,`va_telephone`,`va_teacher`,`va_dbid`,`va_group`,`va_stage`,`va_type`,`va_ktid`,`va_ktbgid`,`va_jdxid`,`va_bsid`,`va_answer`,`va_year`) values (1,'1302443112','陆超',1,'321321199408152732',1,1,'软件1311','15295532376','B14041525',1,0,14,1,1,1,1,1,0,2017),(2,'1302443113','李德和',1,'321321199408152733',1,1,'软件1311','15295532277','B14041525',1,0,6,1,1,0,0,0,0,2017),(3,'1302443114','李友元',1,'321321199408152734',1,1,'软件1311','15295532378','B14041525',0,0,0,-1,0,0,0,0,0,2017);

/*Table structure for table `tb_sxcj` */

DROP TABLE IF EXISTS `tb_sxcj`;

CREATE TABLE `tb_sxcj` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '工号',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学号',
  `va_sxcjx` int(11) DEFAULT NULL COMMENT '哪一项的成绩',
  `va_fs` float DEFAULT NULL COMMENT '此老师打的这一项的分数',
  `va_text` text COMMENT '如果是文本项，写在这里',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_xuehao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_sxcj` */

/*Table structure for table `tb_sxcjx` */

DROP TABLE IF EXISTS `tb_sxcjx`;

CREATE TABLE `tb_sxcjx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '实习成绩项的id',
  `va_text` varchar(200) DEFAULT NULL COMMENT '成绩项名',
  `va_mf` int(11) DEFAULT '0' COMMENT '满分值',
  `va_status` int(11) DEFAULT '0' COMMENT '0代表是分数项，1代表文本项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sxcjx` */

insert  into `tb_sxcjx`(`id`,`va_text`,`va_mf`,`va_status`) values (1,'实习时间不少于6个月',10,0),(2,'态度认真，遵守实习纪律，主动与指导教师联系',20,0),(3,'实习单位评价',20,0),(4,'实习总结内容充实，真实反映了职业素质和岗位能力的提高情况',40,0),(5,'作业文件填写规范，提供的材料真实齐全',10,0);

/*Table structure for table `tb_teacher` */

DROP TABLE IF EXISTS `tb_teacher`;

CREATE TABLE `tb_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_number` varchar(20) DEFAULT NULL COMMENT '工号',
  `va_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `va_sex` int(11) DEFAULT '0' COMMENT '性别1男0女',
  `va_identity` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `va_yxid` int(11) DEFAULT '0' COMMENT '对应院系id',
  `va_telephone` varchar(20) DEFAULT NULL COMMENT '电话',
  `va_email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一年',
  `va_fzr` int(11) DEFAULT '-1' COMMENT '是否是专业负责人；-1代表不是；非-1就是对应专业id的负责人',
  PRIMARY KEY (`id`),
  KEY `va_number` (`va_number`,`va_yxid`,`va_year`,`va_fzr`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_teacher` */

insert  into `tb_teacher`(`id`,`va_number`,`va_name`,`va_sex`,`va_identity`,`va_yxid`,`va_telephone`,`va_email`,`va_year`,`va_fzr`) values (1,'B14041525','木子',1,'321321199408152',1,'15295532376','lc1416302550@163.com',2017,1);

/*Table structure for table `tb_xsz` */

DROP TABLE IF EXISTS `tb_xsz`;

CREATE TABLE `tb_xsz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_zh` int(11) DEFAULT '0' COMMENT '组号',
  `va_xsxh` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一届的',
  `va_yx` int(11) DEFAULT '0' COMMENT '对应院系id',
  PRIMARY KEY (`id`),
  KEY `va_zh` (`va_zh`,`va_xsxh`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_xsz` */

/*Table structure for table `tb_xszl` */

DROP TABLE IF EXISTS `tb_xszl`;

CREATE TABLE `tb_xszl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生组表中的组号',
  `va_bz` varchar(40) DEFAULT NULL COMMENT '标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_xszl` */

/*Table structure for table `tb_year` */

DROP TABLE IF EXISTS `tb_year`;

CREATE TABLE `tb_year` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_year` int(11) DEFAULT '0' COMMENT '年份值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_year` */

insert  into `tb_year`(`id`,`va_year`) values (1,2017);

/*Table structure for table `tb_yuanxi` */

DROP TABLE IF EXISTS `tb_yuanxi`;

CREATE TABLE `tb_yuanxi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_name` varchar(30) NOT NULL COMMENT '院系名称',
  `va_year` int(11) NOT NULL DEFAULT '0' COMMENT '对应哪一年',
  `va_status` int(11) DEFAULT '0' COMMENT '0没用，1正在使用',
  PRIMARY KEY (`id`),
  KEY `va_year` (`va_year`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_yuanxi` */

insert  into `tb_yuanxi`(`id`,`va_name`,`va_year`,`va_status`) values (1,'计算机学院软件学院',2017,1);

/*Table structure for table `tb_yxlw` */

DROP TABLE IF EXISTS `tb_yxlw`;

CREATE TABLE `tb_yxlw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优秀论文个人总表',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学生的学号',
  `va_lw` int(11) DEFAULT NULL COMMENT '学生论文 id',
  `va_yx` int(11) DEFAULT NULL COMMENT '院系',
  `va_zy` int(11) DEFAULT NULL COMMENT '专业',
  `va_status` int(11) DEFAULT NULL COMMENT '0代表只是院内，1代表推举到校，3代表校中选举出来的',
  `va_yfs` float DEFAULT NULL COMMENT '院内的分数',
  `va_xfs` float DEFAULT NULL COMMENT '校级的分数',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_pm` int(11) DEFAULT '0' COMMENT '院中给的顺序',
  PRIMARY KEY (`id`),
  KEY `va_xuehao` (`va_xuehao`,`va_yx`,`va_zy`,`va_status`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_yxlw` */

/*Table structure for table `tb_yxlwx` */

DROP TABLE IF EXISTS `tb_yxlwx`;

CREATE TABLE `tb_yxlwx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优秀论文限制表',
  `va_yx` int(11) DEFAULT NULL COMMENT '哪个院',
  `va_sl` int(11) DEFAULT NULL COMMENT '限制的数量',
  `va_gx` int(11) DEFAULT NULL COMMENT '0个人的类1小组的类',
  PRIMARY KEY (`id`),
  KEY `va_yx` (`va_yx`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_yxlwx` */

insert  into `tb_yxlwx`(`id`,`va_yx`,`va_sl`,`va_gx`) values (1,1,2,0),(2,1,1,1);

/*Table structure for table `tb_yxlwz` */

DROP TABLE IF EXISTS `tb_yxlwz`;

CREATE TABLE `tb_yxlwz` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小组优秀论文库',
  `va_zuhao` int(11) DEFAULT NULL COMMENT '小组号',
  `va_yx` int(11) DEFAULT NULL COMMENT '院系',
  `va_zy` int(11) DEFAULT NULL COMMENT '专业',
  `va_status` int(11) DEFAULT NULL COMMENT '0只是院里的，1校级的',
  `va_yfs` float DEFAULT NULL COMMENT '院里打得分',
  `va_xfs` float DEFAULT NULL COMMENT '校里打得分',
  `va_year` int(11) DEFAULT NULL COMMENT '年份',
  `va_pa` int(11) DEFAULT '0' COMMENT '院中给的顺序',
  `va_pm` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `va_zuhao` (`va_zuhao`,`va_yx`,`va_zy`,`va_status`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_yxlwz` */

/*Table structure for table `tb_zdlspsb` */

DROP TABLE IF EXISTS `tb_zdlspsb`;

CREATE TABLE `tb_zdlspsb` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '指导教师评审成绩值表',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '工号',
  `va_xuehao` varchar(20) DEFAULT NULL COMMENT '学号',
  `va_zdlspsbx` int(11) DEFAULT NULL COMMENT '哪一项的成绩',
  `va_fs` float DEFAULT '0' COMMENT '此老师这一项打的分数',
  `va_text` text COMMENT '如果是文本项，写在这里',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_xuehao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_zdlspsb` */

/*Table structure for table `tb_zdlspsbx` */

DROP TABLE IF EXISTS `tb_zdlspsbx`;

CREATE TABLE `tb_zdlspsbx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '指导老师评审表项值表',
  `va_text` varchar(200) DEFAULT NULL COMMENT '成绩项的名称',
  `va_status` int(11) DEFAULT '0' COMMENT '0 代表是分数项，1代表文本项',
  `va_mf` int(11) DEFAULT '0' COMMENT '满分的值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_zdlspsbx` */

insert  into `tb_zdlspsbx`(`id`,`va_text`,`va_status`,`va_mf`) values (1,'毕业设计（论文）立论正确，设计方案合理，结构严谨，条理清楚，分析透彻，逻辑性强，解决问题方案恰当，结论正确',0,30),(2,'书写工整，写作格式规范，图纸、图表制作规范，符合国家标准',0,20),(3,'毕业设计（论文）具有一定的创新性，研究结果具有实用和推广价值',0,10),(4,'分析问题和解决问题的能力强',0,10),(5,'论文答辩时，思路清晰，语言表述流畅，能简明、正确地叙述设计（论文）的主要内容',0,15),(6,'回答问题时，概念清晰，反应敏捷，能准确深入地回答主要问题',0,15),(7,'评语：',1,0);

/*Table structure for table `tb_zf` */

DROP TABLE IF EXISTS `tb_zf`;

CREATE TABLE `tb_zf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_xsxh` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `va_year` int(11) DEFAULT '0' COMMENT '哪一届',
  `va_yx` int(11) DEFAULT '0' COMMENT '所属院系id',
  `va_zy` int(11) DEFAULT '0' COMMENT '所学专业的id',
  `va_zf` float DEFAULT '0' COMMENT '总分值',
  `va_dj` int(11) DEFAULT '0' COMMENT '五分制等级，0不及格，1及格，2中等，3良好，4优秀',
  `va_lw` float DEFAULT '0' COMMENT '论文成绩',
  PRIMARY KEY (`id`),
  KEY `va_xsxh` (`va_xsxh`,`va_yx`,`va_zy`,`va_dj`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_zf` */

/*Table structure for table `tb_zhuanye` */

DROP TABLE IF EXISTS `tb_zhuanye`;

CREATE TABLE `tb_zhuanye` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_name` varchar(60) DEFAULT NULL COMMENT '专业名',
  `va_yx` int(11) DEFAULT '0' COMMENT '对应的院系id',
  `va_dm` varchar(30) DEFAULT NULL COMMENT '专业代码字段',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一年',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_zhuanye` */

insert  into `tb_zhuanye`(`id`,`va_name`,`va_yx`,`va_dm`,`va_year`) values (1,'软件技术',1,'201701011',2017),(2,'软件测试',1,'201701012',2017);

/*Table structure for table `tb_zpjl` */

DROP TABLE IF EXISTS `tb_zpjl`;

CREATE TABLE `tb_zpjl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '负责写的老师',
  `va_zy` int(11) DEFAULT '0' COMMENT '对应的专业id',
  `va_text` text COMMENT '总评，总结的内容',
  `va_year` int(11) DEFAULT '0' COMMENT '对应的哪一届',
  PRIMARY KEY (`id`),
  KEY `va_gonghao` (`va_gonghao`,`va_zy`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_zpjl` */

/*Table structure for table `tb_zqxj` */

DROP TABLE IF EXISTS `tb_zqxj`;

CREATE TABLE `tb_zqxj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `va_yx` int(11) DEFAULT '0' COMMENT '中期小结所属的学院',
  `va_gonghao` varchar(20) DEFAULT NULL COMMENT '填写中期小结老师的工号',
  `va_year` int(11) DEFAULT '0' COMMENT '对应哪一届',
  `va_time` datetime DEFAULT NULL COMMENT '时间',
  `va_filename` varchar(100) DEFAULT NULL COMMENT '原文件名',
  `va_url` varchar(30) DEFAULT NULL COMMENT '路径，主要记录类型',
  `va_status` int(11) DEFAULT '0' COMMENT '0文件未上传，1已上传',
  `va_text` text COMMENT '保留，如果中期小结不用文件形式；就用这个文本形式',
  PRIMARY KEY (`id`),
  KEY `va_yx` (`va_yx`,`va_gonghao`,`va_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_zqxj` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
