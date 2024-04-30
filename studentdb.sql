-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: studentdb
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `course_info`
--

DROP TABLE IF EXISTS `course_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_info` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(45) NOT NULL,
  `course_category` varchar(45) NOT NULL,
  `course_credits` int NOT NULL,
  `course_level` varchar(11) NOT NULL,
  `course_delivery` enum('Full-time','Part-time','Online') NOT NULL DEFAULT 'Online',
  `course_duration` enum('3 Years','4 Years') NOT NULL,
  PRIMARY KEY (`course_id`),
  CONSTRAINT `chk_course_credits` CHECK ((`course_credits` > 0)),
  CONSTRAINT `chk_course_level` CHECK ((`course_level` between 1 and 10))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_info`
--

LOCK TABLES `course_info` WRITE;
/*!40000 ALTER TABLE `course_info` DISABLE KEYS */;
INSERT INTO `course_info` VALUES (1,'BA (Hons) in Accounting','Bussiness',180,'8','Full-time','3 Years'),(2,'Certificate in Electrical Principles','Engineering',90,'7','Part-time','3 Years'),(3,'BA (Hons) in Business','Bussiness',240,'8','Full-time','4 Years'),(4,'Certificate in IP Networks','Computing',120,'7','Part-time','4 Years'),(5,'BSc (Hons) in Computer Games ','Computing',240,'8','Full-time','4 Years'),(6,'BSc in Software Development','Computing',180,'7','Full-time','3 Years'),(7,'BSc (Hons) in Cyber Security','Computing',240,'8','Full-time','4 Years'),(8,'BSc (Hons) in Biopharmaceuticals','Science',240,'8','Full-time','4 Years'),(9,'Bachelor of Laws (Hons) in Law (LLB)','Law',180,'8','Full-time','3 Years'),(10,'Test','Business',240,'1','Part-time','4 Years'),(11,'Aircraft','Mechanichs',180,'8','Full-time','4 Years'),(12,'qqq','wwww',30,'1','Full-time','3 Years'),(13,'Last Test ','Test',30,'1','Full-time','3 Years'),(14,'Onemore Test','To do',30,'1','Full-time','3 Years');
/*!40000 ALTER TABLE `course_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `staff_name` varchar(45) DEFAULT NULL,
  `staff_email` varchar(45) DEFAULT NULL,
  `staff_password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`staff_id`),
  UNIQUE KEY `staff_email_UNIQUE` (`staff_email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'Robert De Niro','staff@email.com','123'),(2,'Al Pacino','staff@email.ie','1234');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_info`
--

DROP TABLE IF EXISTS `student_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_info` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `student_name` varchar(50) DEFAULT NULL,
  `student_email` varchar(40) DEFAULT NULL,
  `student_phone` varchar(25) DEFAULT NULL,
  `student_dob` date DEFAULT NULL,
  `student_address` varchar(100) DEFAULT NULL,
  `student_balance` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_info`
--

LOCK TABLES `student_info` WRITE;
/*!40000 ALTER TABLE `student_info` DISABLE KEYS */;
INSERT INTO `student_info` VALUES (1,'Sinéad O’Connor','matt@email.ie','0898763344',NULL,'Japan',67.00),(5,'Andy Murray','andy@email.ie','09776343375','1980-08-21','Dublin',200.00),(8,'Declan Donnelly','declan@email.ie','0090384978','1970-09-02','Dublin',300.00),(9,'Michael Collins','michael@email.ie','0773643437','1965-01-28','Dublin',120.00),(10,'James Kenney','james@email.ie','0348978543','2003-12-20','Malaga',2000.00),(15,'Anna Fitzpatrick','anna@email.ie','0934892457','1999-11-04','England',450.00),(16,'Kate Johnson','kate@email.com','0676326474','1994-05-15','Canada',2300.00),(17,'Barry Cahill','zalon@email.com','0987665443','2002-09-03','Caribe',1250.00),(18,'Mario Bros','mario@email.com','098717364','1994-03-02','Japan',30000.00),(20,'Leo Cullen','gere@email.com','0981234576','2003-03-04','Berlin',98765.00),(21,'Tommy Fin','tommy@email.com','09863646','2002-04-09','Finland',4300.00),(22,'Joseph O’Dwyer','zig@email.com','0789532643','1978-03-02','Jamaica',20000.00),(23,'Adam Sandler','adam@email.com','098764422','1998-06-07','Denmark',1200.00),(25,'Tommy Flanagan','enzo@email.com','08764434232','1980-01-01','Italy',97000.00),(26,'Mary Onell','mary@email.com','0987653334','1984-01-06','Ireland',67000.00),(27,'Tim Mansom','tim@gmail.com','08776651223','2000-08-04','1, Main Road, Dublin',2000.00),(28,'Christopher Gaffney','mazds-dev@email.com','003530877116789','2000-07-03','Rua: Doutor Mario Totta, número 2735, Bairro Camaqua, Porto Alegre Brasil, 91920-130',2000.00),(29,'Chris Canavan','paulo\'yahoo.com','00555499635678','2000-06-03','Rua Jacinto Filho de Menezes,Ipanem, Porto Alegre, Brasil',3455.00),(30,'Carl Shelby','shelby@email.com','098ew7477','2003-09-09','22, Second Road, R98CX12, Galway',1234.00),(31,'Brian Kelly','brian@email.com','098776553','1980-04-08','134, Fourth Avenue, Donegal, D09DR56',12222.00),(32,'Ryan Walsh','walsh@email.com','09865365237','1991-02-24','Dublin',2300.00),(33,'Arthur Conan Doyle','arthur@email.com','0987653355','1982-12-30','Belfast',1200.00),(34,'Naomi Campbell','naomi@email.com','098763452363','1987-11-19','Kilkenny',1823.00);
/*!40000 ALTER TABLE `student_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-30 14:35:04
