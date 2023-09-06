-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: light
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `area_id` int NOT NULL AUTO_INCREMENT COMMENT '区域编号',
  `area_name` varchar(45) DEFAULT NULL COMMENT '区域名称',
  `parent_id` int DEFAULT NULL COMMENT '上级编号',
  `area_level` int DEFAULT NULL COMMENT '区域级别',
  `area_rank` int DEFAULT NULL COMMENT '所在级别位次',
  `area_serial` varchar(10) DEFAULT NULL COMMENT '区域序列号',
  `area_net` varchar(45) DEFAULT NULL,
  `area_lon` varchar(20) DEFAULT NULL,
  `area_lat` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `area_net_UNIQUE` (`area_net`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (0,'所有区域',-1,0,NULL,'0000000000',NULL,NULL,NULL),(1,'山东省',0,1,1,'0100000000',NULL,NULL,NULL),(2,'青岛市',1,2,1,'0101000000',NULL,NULL,NULL),(3,'城阳区',2,3,1,'0101010000',NULL,NULL,NULL),(4,'春阳路',3,4,1,'0101010001','0001',NULL,NULL),(5,'瑞阳路',3,4,2,'0101010002','0002',NULL,NULL),(6,'即墨区',2,3,2,'0101020000',NULL,NULL,NULL),(7,'墨城路',6,4,1,'0101020001','0003',NULL,NULL),(66,'正阳路',3,4,3,'0101010003','0005',NULL,NULL),(67,'荟城路',3,4,4,'0101010004','000B',NULL,NULL),(68,'陕西省',0,1,2,NULL,NULL,NULL,NULL),(69,'西安市',68,2,1,NULL,NULL,NULL,NULL),(70,'雁塔区',69,3,1,NULL,NULL,NULL,NULL),(71,'太白南路',70,4,1,NULL,'1101',NULL,NULL),(72,'良城路',3,4,5,NULL,'0007',NULL,NULL),(87,'山西省',0,1,3,NULL,NULL,NULL,NULL),(88,'甘肃省',0,1,4,NULL,NULL,NULL,NULL),(89,'天水市',88,2,1,NULL,NULL,NULL,NULL),(90,'济南市',1,2,2,NULL,NULL,NULL,NULL),(91,'甘谷县',89,3,1,NULL,NULL,NULL,NULL),(92,'大像山镇',91,4,1,'0401010100',NULL,NULL,NULL),(93,'长治市',87,2,1,'0302000000',NULL,NULL,NULL),(94,'武乡县',93,3,1,'0301020000',NULL,NULL,NULL),(95,'六峰镇',91,4,2,'0401010200',NULL,NULL,NULL),(96,'磐安镇',91,4,3,'0401010300',NULL,NULL,NULL),(97,'古坡镇',91,4,4,'0401010400',NULL,NULL,NULL),(98,'历城区',90,3,1,'0102010000',NULL,NULL,NULL),(99,'历下区',90,3,2,'0102020000',NULL,NULL,NULL),(100,'莲湖区',69,3,2,'0201020000',NULL,NULL,NULL),(101,'碑林区',69,3,3,'0201030000',NULL,NULL,NULL),(102,'长安区',69,3,4,'0201040000',NULL,NULL,NULL),(103,'烟台市',1,2,3,'0103000000',NULL,NULL,NULL),(104,'汉中市',68,2,2,'0202000000',NULL,NULL,NULL),(105,'贾豁乡',94,4,1,'0301010100',NULL,NULL,NULL),(106,'李沧区',2,3,3,'01010300',NULL,NULL,NULL);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-06 10:57:23
