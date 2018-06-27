DELIMITER $$

USE `cardcolv3`$$

DROP PROCEDURE IF EXISTS `proc_split_factory`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_split_factory`(IN keyId BIGINT)
    BEGIN
	DECLARE sTime DATETIME;
	DECLARE s_tempTime DATETIME;
	DECLARE eTime DATETIME;
	DECLARE maxCnt INT DEFAULT 0;  
    DECLARE i INT DEFAULT 0;
	DECLARE tempStartTime DATETIME;
	DECLARE tempEndTime DATETIME;
	DECLARE tempId BIGINT;
	DECLARE flag BOOLEAN DEFAULT FALSE;
	
	SELECT
	CAST(CONCAT(planDate,' ',starttime,':00') AS DATETIME) AS startTime , 
	CAST(CONCAT(planDate,' ',endTime,':00') AS DATETIME) AS endTime	
	INTO
	sTime, eTime
	FROM TB_MEMBER_FACTORY_COSTS WHERE id = keyId;
	
	
	CREATE TEMPORARY TABLE IF NOT EXISTS tmpTable_factory
    (  
       tp_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	   tp_starttime VARCHAR(255) DEFAULT NULL,
	   tp_endtime VARCHAR(255) DEFAULT NULL,
	   PRIMARY KEY (`tp_id`)
     );
        
    CREATE TEMPORARY TABLE IF NOT EXISTS tmpTable_factory_split
    (  
	   sp_starttime VARCHAR(255) DEFAULT NULL,
	   sp_endtime VARCHAR(255) DEFAULT NULL
    );
         
    TRUNCATE TABLE tmpTable_factory;
    TRUNCATE TABLE tmpTable_factory_split;
         
    INSERT INTO tmpTable_factory(tp_starttime,tp_endtime)       
    SELECT CAST(CONCAT(starttime,':00') AS DATETIME) AS starttime,
	CAST(CONCAT(endtime,':00') AS DATETIME) AS endtime
    FROM tb_factory_order WHERE factoryCosts = keyId AND STATUS = '1' ORDER BY starttime;
             
	 
	SELECT MIN(tp_id) INTO i FROM tmpTable_factory;  
	SELECT MAX(tp_id) INTO maxCnt FROM tmpTable_factory; 
	 
	WHILE i <= maxCnt DO
	    SET flag = TRUE;
	    SELECT tp_id,tp_starttime,tp_endtime INTO tempId,tempStartTime,tempEndTime FROM tmpTable_factory WHERE tp_id = i;

            IF sTime <= tempStartTime THEN                  
		       IF  sTime = tempStartTime THEN
				   SET sTime = tempEndTime;
			   ELSE 
			       SET s_tempTime = sTime;
			       WHILE flag DO 
    			     SET sTime = DATE_ADD(sTime,INTERVAL "30" MINUTE);
			         IF sTime = tempStartTime THEN
			            INSERT INTO tmpTable_factory_split VALUES(s_tempTime,tempStartTime);
			            SET sTime = tempEndTime;
			            SET flag = FALSE;			      
			         END IF;
			       END WHILE;
		        END IF; 
		    
		       IF i = maxCnt THEN
			      IF eTime != tempEndTime THEN
			         INSERT INTO tmpTable_factory_split VALUES(tempEndTime,eTime);
				  END IF;
		       END IF;		    
	        ELSE
	           SET i = maxCnt;
	        END IF;
		    
	       SET i = i + 1;             
            
   END WHILE ;

       SELECT sp_starttime,sp_endtime FROM tmpTable_factory_split ORDER BY sp_starttime;
	 
       DROP TABLE tmpTable_factory;
       DROP TABLE tmpTable_factory_split;
END$$ 