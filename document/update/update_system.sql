INSERT INTO tb_program (CODE, flows, hasFlow, NAME, parent, ishow, sort)
VALUES ('plan1', NULL, NULL, '计划列表详情', 15, 1, 3);

UPDATE TB_PARAMETER SET CODE = 'A' WHERE PARENT = 31 AND CODE = '1';
UPDATE TB_PARAMETER SET CODE = 'B' WHERE PARENT = 31 AND CODE = '2';
UPDATE TB_PARAMETER SET CODE = 'C' WHERE PARENT = 31 AND CODE = '3';
UPDATE TB_PARAMETER SET CODE = 'D' WHERE PARENT = 31 AND CODE = '4';
UPDATE TB_PARAMETER SET CODE = 'E' WHERE PARENT = 31 AND CODE = '5';
UPDATE TB_PARAMETER SET CODE = 'F' WHERE PARENT = 31 AND CODE = '6';
UPDATE TB_PARAMETER SET CODE = 'G' WHERE PARENT = 31 AND CODE = '7';

UPDATE TB_SECTOR SET TYPE = 'A' WHERE TYPE = 1;
UPDATE TB_SECTOR SET TYPE = 'B' WHERE TYPE = 2;
UPDATE TB_SECTOR SET TYPE = 'C' WHERE TYPE = 3;
UPDATE TB_SECTOR SET TYPE = 'D' WHERE TYPE = 4;
UPDATE TB_SECTOR SET TYPE = 'E' WHERE TYPE = 5;
UPDATE TB_SECTOR SET TYPE = 'F' WHERE TYPE = 6;
UPDATE TB_SECTOR SET TYPE = 'G' WHERE TYPE = 7;

INSERT INTO tb_parameter(CODE,NAME) VALUES ('plan_type_c','系统健身卡类型');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','瘦身减重','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','健美增肌','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('C','运动康复','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('D','提高运动表现','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('E','其他','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='plan_type_c')
WHERE NAME IN ('瘦身减重','健美增肌','运动康复','提高运动表现','其他');

INSERT INTO tb_parameter(CODE,NAME) VALUES ('coach_type_c','系统健身教练类型');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','网络私人健身教练','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','传统私人健身教练','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='coach_type_c')
WHERE NAME IN ('网络私人健身教练','传统私人健身教练');

INSERT INTO tb_parameter(CODE,NAME) VALUES ('apply_object_c','适用对象');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','初级(从未健身)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','中级(6个月健身经历)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('C','高级(1年的健身经历)','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='apply_object_c')
WHERE NAME IN ('初级(从未健身)','中级(6个月健身经历)','高级(1年的健身经历)');

INSERT INTO tb_parameter(CODE,NAME) VALUES ('apply_scene_c','适用场景');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','办公室','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','健身房','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('C','家庭','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('D','户外','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='apply_scene_c') 
WHERE NAME IN ('办公室','健身房','家庭','户外');

INSERT INTO tb_parameter(CODE,NAME) VALUES ('plan_circle_c','计划周期');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','小于8天','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','8-31天','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('C','32-92天','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('D','大于92天','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='plan_circle_c')
WHERE NAME IN ('小于8天','8-31天','32-92天','大于92天');

INSERT INTO tb_parameter(CODE,NAME) VALUES ('card_type_c','健身卡种');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','圈存(计时)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','圈存(计次)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('C','圈存(储值)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('D','对赌(次数)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('E','对赌(频率)','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('F','预付卡','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='card_type_c')
WHERE NAME IN ('圈存(计时)','圈存(计次)','圈存(储值)','对赌(次数)','对赌(频率)','预付卡');


--系统自动生成计划表数据，内定
INSERT INTO tb_goods (NAME, price, summary, descr, type, impl_class)VALUES('卡库智能健身计划',78, '本系统是融合各种健身理论建立的专业健身计划工具。由运动健身、IT等多领域专家团队设计开发。可以为用户生成三个月的个性化健身方案。有心、肺、肝、 肾、神经疾病者不建议使用本系统。脱臼一年以上，骨折、肌肉、肌腱拉 伤愈后三个月以上可以使用。 ', '1', '', 'com.freegym.web.plan.gen.impl.SystemGeneratorImpl');
INSERT INTO tb_goods (NAME, price, summary, descr, type, impl_class)VALUES('王严健身专家系统',78, '王严老师是国内屈指可数的同时活跃在健身健美运动一线和教学理论 研究领域的专家。自己先后获得过5次北京健美冠军，并培训出世界 健美冠军、亚洲健美冠军、全国健美冠军多人。本系统根据王严老师 多年研究、实践成果开发。', '2', '', 'com.freegym.web.plan.gen.impl.WangyGeneratorImpl');

--参数表增加订单类型
INSERT INTO tb_parameter(id,CODE,NAME,parent,VALUE,viewtype) VALUES(21,'order_type_c','订单类型',NULL,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('1','健身卡订单',21,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('2','活动订单',21,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('3','健身计划订单',21,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('4','场地订单',21,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('5','课程订单',21,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('6','智能计划订单',21,NULL,NULL);

INSERT INTO tb_parameter(CODE,NAME) VALUES ('active_circle_c','挑战周期');
INSERT INTO tb_parameter(CODE,NAME) VALUES ('A','小于10天');
INSERT INTO tb_parameter(CODE,NAME) VALUES ('B','10-30天');
INSERT INTO tb_parameter(CODE,NAME) VALUES ('C','31-92天');
INSERT INTO tb_parameter(CODE,NAME) VALUES ('D','大于92天');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='active_circle_c')
WHERE NAME IN ('小于10天','11-30天','31-92天','大于92天');

DELETE FROM tb_parameter WHERE id = 65;

--INSERT INTO tb_parameter(id,CODE,NAME,parent,VALUE,viewtype) VALUES(108,'coach_experties_c','教练专长',NULL,NULL,NULL);
--INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('A','瘦身减重',108,NULL,NULL);
--INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('B','促进健康',108,NULL,NULL);
--INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('C','肌肉健美',108,NULL,NULL);
--INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('D','达到最佳运动状态',108,NULL,NULL);

INSERT INTO tb_parameter(id,CODE,NAME,parent,VALUE,viewtype) VALUES(116,'coach_servicetype_c','服务方式',NULL,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('A','网络线上指导',116,NULL,NULL);
INSERT INTO tb_parameter(CODE,NAME,parent,VALUE,viewtype) VALUES('B','传统线下服务',116,NULL,NULL);

ALTER TABLE `cardcolv3`.`tb_member_body`   
  CHANGE `conclusion` `conclusion` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL;

ALTER TABLE `cardcolv3`.`tb_member_setting`   
  CHANGE `bmiHigh` `bmiHigh` DOUBLE DEFAULT 85  NULL,
  CHANGE `bmiLow` `bmiLow` DOUBLE DEFAULT 55  NULL;

INSERT INTO tb_program (id, CODE, NAME, ishow, sort) VALUES (23, 'basic', '基础管理', '1', 2);
INSERT INTO tb_program (CODE, NAME, parent, ishow, sort) VALUES ('action1', '系统动作维护', 23, '1', 1);

ALTER TABLE tb_active_order MODIFY COLUMN payNo VARCHAR(28) ;
ALTER TABLE tb_courserelease_order MODIFY COLUMN payNo VARCHAR(28) ;
ALTER TABLE tb_factory_order MODIFY COLUMN payNo VARCHAR(28) ;
ALTER TABLE tb_goods_order MODIFY COLUMN payNo VARCHAR(28) ;
ALTER TABLE tb_planrelease_order MODIFY COLUMN payNo VARCHAR(28) ;
ALTER TABLE tb_product_order MODIFY COLUMN payNo VARCHAR(28) ;

INSERT INTO tb_member SET NAME = '卡库', nick='cardcol', role='S',PASSWORD='e10adc3949ba59abbe56e057f20f883e';
INSERT INTO tb_member SET NAME = '王严', nick='wangyan', role='S',PASSWORD='e10adc3949ba59abbe56e057f20f883e';

UPDATE tb_goods SET member = (SELECT id FROM tb_member WHERE nick = 'cardcol') WHERE TYPE = 1
UPDATE tb_goods SET member = (SELECT id FROM tb_member WHERE nick = 'wangyan') WHERE TYPE = 2


INSERT INTO tb_parameter(CODE,NAME) VALUES ('coach_type_c1','教练类型');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('A','私人教练','1');
INSERT INTO tb_parameter(CODE,NAME,viewType) VALUES ('B','团体教练','1');
UPDATE tb_parameter SET parent = (SELECT id FROM  (SELECT * FROM tb_parameter) AS b  WHERE CODE='coach_type_c1')
WHERE NAME IN ('私人教练','团体教练');


ALTER TABLE `cardcolv3`.`tb_course_info`  
  DROP FOREIGN KEY `FKF298CE0146075329`;

  
UPDATE tb_product SET audit = '1' WHERE audit = '2';
UPDATE tb_product SET audit = '0' WHERE audit = '3';
UPDATE tb_member SET mobilephone = '18912231234' WHERE id = 1;

ALTER TABLE `cardcolv3`.`tb_plan_release`   
  CHANGE `apply_scene` `apply_scene` VARCHAR(20) CHARSET utf8 COLLATE utf8_general_ci NULL;
  
ALTER TABLE tb_factory_order MODIFY COLUMN orderstarttime DATETIME;
ALTER TABLE tb_factory_order MODIFY COLUMN orderendtime DATETIME;

ALTER TABLE `cardcolv3`.`tb_complaint`  
  DROP FOREIGN KEY `FKE2F85FFA4D9E2451`,
  DROP FOREIGN KEY `FK_axbjf0qm4g3dk96aomsbytobo`,
  DROP FOREIGN KEY `FK_dw5cc562j7qk2ssxwqxlffgow`,
  DROP FOREIGN KEY `FK_fkqee37k4rxq5x0epjlhxjk28`;
  
  ALTER TABLE table tb_goods ADD isClose varchar(1);
  ALTER TABLE table tb_goods ADD topTime datetime;
  
  ALTER TABLE tb_goods ADD isClose VARCHAR(1);
  ALTER TABLE tb_goods ADD topTime DATETIME;
  
  ALTER TABLE tb_plan_release ADD isClose VARCHAR(1);
  ALTER TABLE tb_plan_release ADD topTime DATETIME;
  
  update tb_goods set plan_type='A,B,C,D', apply_scene='A', apply_object='A,B,C', apparatuses='各种力量训练器材', plan_circle='3个月', publish_time='2015/03/28' where type = '1';
  update tb_goods set plan_type='A,B,D', apply_scene='A', apply_object='A,B', apparatuses='各种力量训练器材', plan_circle='4周', publish_time='2015/03/28' where type = '2';
