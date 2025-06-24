-- MySQL dump 10.13  Distrib 9.0.1, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hrm
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `employees_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `role` enum('ADMIN','EMPLOYEE','HR','MANAGER','SUPERVISOR') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtl8b0eq1pfw9nm5omaywo7mai` (`employees_id`),
  KEY `FKd4vb66o896tay3yy52oqxr9w0` (`role_id`),
  CONSTRAINT `FKd4vb66o896tay3yy52oqxr9w0` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKswklbxvh98gh6kfd4pikn2ll6` FOREIGN KEY (`employees_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2024-01-15 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'hr_manager',1,1,'ADMIN'),(2,'2024-01-15 08:30:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'hr_specialist',2,2,'ADMIN'),(3,'2024-01-15 09:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'recruiter',3,3,'ADMIN'),(4,'2024-01-16 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'it_manager',4,4,'ADMIN'),(5,'2024-01-16 08:30:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'senior_dev',5,5,'ADMIN'),(6,'2024-01-16 09:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'junior_dev',6,6,'ADMIN'),(7,'2024-01-17 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'devops',7,7,'ADMIN'),(8,'2024-01-17 08:30:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'qa_engineer',8,8,'ADMIN'),(9,'2024-01-18 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'marketing_mgr',9,9,'ADMIN'),(10,'2024-01-18 08:30:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'digital_mkt',10,10,'ADMIN'),(11,'2024-01-19 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'content_creator',11,11,'ADMIN'),(12,'2024-01-19 08:30:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'finance_mgr',12,12,'ADMIN'),(13,'2024-01-20 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'accountant',13,13,'ADMIN'),(14,'2024-01-20 08:30:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'financial_analyst',14,14,'ADMIN'),(15,'2024-01-21 08:00:00.000000','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',0x01,'ops_manager',15,15,'ADMIN'),(664355682,'2025-06-19 04:18:42.491318','$2a$10$CyLJQ2j2.ZuXNhzH0y0EI.lGxqfMJwVQV.encxr1Eda40E87BJcm2',0x01,'k123456',16,16,'ADMIN');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;

--
-- Table structure for table `apply`
--

DROP TABLE IF EXISTS `apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply` (
  `id` int NOT NULL,
  `apply_at` datetime(6) DEFAULT NULL,
  `apply_status` enum('HIRED','INTERVIEW','REJECTED','SUBMITTED') NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `candidate_profile_id` int DEFAULT NULL,
  `recruitment_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsxrv7lx97nsthij8wiv1awew3` (`candidate_profile_id`),
  KEY `FKals0v15xmm35mijw737goffbt` (`recruitment_id`),
  CONSTRAINT `FKals0v15xmm35mijw737goffbt` FOREIGN KEY (`recruitment_id`) REFERENCES `recruitment` (`id`),
  CONSTRAINT `FKsxrv7lx97nsthij8wiv1awew3` FOREIGN KEY (`candidate_profile_id`) REFERENCES `candidate_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply`
--

/*!40000 ALTER TABLE `apply` DISABLE KEYS */;
INSERT INTO `apply` VALUES (1,'2024-02-17 09:00:00.000000','INTERVIEW','Java Developer',1,1),(2,'2024-02-18 10:00:00.000000','SUBMITTED','Java Developer',6,1),(3,'2024-02-19 11:00:00.000000','HIRED','Java Developer',11,1),(4,'2024-02-22 09:00:00.000000','INTERVIEW','Frontend Developer',2,2),(5,'2024-02-23 10:00:00.000000','REJECTED','Frontend Developer',6,2),(6,'2024-02-27 09:00:00.000000','INTERVIEW','Marketing Specialist',3,3),(7,'2024-02-28 10:00:00.000000','SUBMITTED','Marketing Specialist',12,3),(8,'2024-03-07 09:00:00.000000','SUBMITTED','Financial Analyst',5,4),(9,'2024-03-08 10:00:00.000000','INTERVIEW','Financial Analyst',13,4),(10,'2024-03-12 09:00:00.000000','HIRED','QA Engineer',9,5),(11,'2024-03-13 10:00:00.000000','SUBMITTED','QA Engineer',14,5),(12,'2024-03-17 09:00:00.000000','INTERVIEW','Sales Executive',7,6),(13,'2024-03-18 10:00:00.000000','SUBMITTED','Sales Executive',8,6),(14,'2024-03-27 09:00:00.000000','SUBMITTED','Content Creator',12,7),(15,'2024-03-28 10:00:00.000000','INTERVIEW','Content Creator',15,7),(16,'2024-04-01 09:00:00.000000','SUBMITTED','Operations Specialist',8,8),(17,'2024-04-02 10:00:00.000000','INTERVIEW','Operations Specialist',14,8),(337594178,'2025-06-19 04:51:49.383676','HIRED','1',-1368178519,-588624674);
/*!40000 ALTER TABLE `apply` ENABLE KEYS */;

--
-- Table structure for table `approvals`
--

DROP TABLE IF EXISTS `approvals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `approvals` (
  `id` int NOT NULL,
  `approval_date` datetime(6) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `status` enum('Approved','Paid','Pending','Rejected') NOT NULL,
  `employee_review_id` int DEFAULT NULL,
  `payroll_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKs0uvsrd7s86af3nkxod19u5nu` (`payroll_id`),
  KEY `FKc3ekifxo96di4ugs76jvjinoy` (`employee_review_id`),
  CONSTRAINT `FKc3ekifxo96di4ugs76jvjinoy` FOREIGN KEY (`employee_review_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKtr328epcmpky7cd8eyh6uifra` FOREIGN KEY (`payroll_id`) REFERENCES `payrolls` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approvals`
--

/*!40000 ALTER TABLE `approvals` DISABLE KEYS */;
INSERT INTO `approvals` VALUES (1,'2025-06-01 10:00:00.000000','Looks good.','Approved',1,1),(3,'2025-06-23 13:43:26.201000','string3','Pending',3,3),(4,'2025-06-04 14:20:00.000000','Approved after revision.','Approved',4,4),(5,'2025-06-05 08:15:00.000000','Awaiting HR review.','Pending',5,5),(6,'2025-06-06 13:00:00.000000','All clear.','Paid',6,6),(7,'2025-06-07 10:10:00.000000','Missing bonus entry.','Rejected',7,7),(8,'2025-06-08 15:45:00.000000','Approved by finance.','Approved',8,8),(9,'2025-06-09 12:00:00.000000','Waiting for signature.','Pending',9,9),(10,'2025-06-10 09:30:00.000000','Finalized and paid.','Paid',10,10),(11,'2025-06-11 11:00:00.000000','Reviewed and approved.','Approved',1,11),(12,'2025-06-12 14:10:00.000000','Needs correction.','Rejected',2,12),(13,'2025-06-13 08:50:00.000000','Approved.','Approved',3,13),(14,'2025-06-14 10:25:00.000000','Still under review.','Pending',4,14),(15,'2025-06-15 13:40:00.000000','Paid on time.','Paid',5,15),(16,'2025-06-16 09:05:00.000000','Bonus miscalculated.','Rejected',6,16),(17,'2025-06-17 15:15:00.000000','Approved after adjustment.','Approved',7,17),(18,'2025-06-18 10:50:00.000000','Waiting for approval.','Pending',8,18),(19,'2025-06-19 12:30:00.000000','Paid with delay.','Paid',9,19),(20,'2025-06-20 11:20:00.000000','All good.','Approved',10,20),(977420859,'2025-06-23 13:43:06.347000','string','Pending',3,2);
/*!40000 ALTER TABLE `approvals` ENABLE KEYS */;

--
-- Table structure for table `assigned_work_person`
--

DROP TABLE IF EXISTS `assigned_work_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assigned_work_person` (
  `id` int NOT NULL,
  `completed_date` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `measurable_outcome` varchar(255) DEFAULT NULL,
  `progress_notes` varchar(255) DEFAULT NULL,
  `progress_percentage` int DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `target_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `assigned_by_id` int DEFAULT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdp7ovl0a23ysn9ki6f9bpts5h` (`assigned_by_id`),
  KEY `FKskh5ws9ruvdd1d064427ugscf` (`employee_id`),
  CONSTRAINT `FKdp7ovl0a23ysn9ki6f9bpts5h` FOREIGN KEY (`assigned_by_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKskh5ws9ruvdd1d064427ugscf` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assigned_work_person`
--

/*!40000 ALTER TABLE `assigned_work_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `assigned_work_person` ENABLE KEYS */;

--
-- Table structure for table `attendence`
--

DROP TABLE IF EXISTS `attendence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendence` (
  `id` int NOT NULL,
  `attendence_date` datetime(6) DEFAULT NULL,
  `check_in` datetime(6) DEFAULT NULL,
  `check_out` datetime(6) DEFAULT NULL,
  `other_time` float DEFAULT NULL,
  `regular_time` float DEFAULT NULL,
  `shift_type` enum('FLEX','HOLIDAY','NIGHT','OVERTIME','REGULAR','REMOTE','WEEKEND') NOT NULL,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1pfpgsotyedijx93qmd3c25io` (`employee_id`),
  CONSTRAINT `FK1pfpgsotyedijx93qmd3c25io` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendence`
--

/*!40000 ALTER TABLE `attendence` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendence` ENABLE KEYS */;

--
-- Table structure for table `candidate_profile`
--

DROP TABLE IF EXISTS `candidate_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate_profile` (
  `id` int NOT NULL,
  `create_profile_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `linkcv` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `skills` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate_profile`
--

/*!40000 ALTER TABLE `candidate_profile` DISABLE KEYS */;
INSERT INTO `candidate_profile` VALUES (-1368178519,'2025-06-19 04:51:49.369681','kn26066@gmail.com','1 year in QA','https://drive.google.com/cv9','Khanh','+67 56068037820','Process Improvement, Logistics, Supply Chain'),(1,'2024-02-01 09:00:00.000000','candidate1@gmail.com','3 years in Java development','https://drive.google.com/cv1','Nguyen Van X','0987654321','Java, Spring Boot, MySQL, Git'),(2,'2024-02-02 10:00:00.000000','candidate2@gmail.com','2 years in Frontend development','https://drive.google.com/cv2','Tran Thi Y','0987654322','React, JavaScript, HTML, CSS'),(3,'2024-02-03 11:00:00.000000','candidate3@gmail.com','5 years in Marketing','https://drive.google.com/cv3','Le Van Z','0987654323','Digital Marketing, SEO, SEM, Analytics'),(4,'2024-02-04 12:00:00.000000','candidate4@gmail.com','1 year in HR','https://drive.google.com/cv4','Pham Thi A','0987654324','Recruitment, Employee Relations, HRIS'),(5,'2024-02-05 13:00:00.000000','candidate5@gmail.com','4 years in Finance','https://drive.google.com/cv5','Hoang Van B','0987654325','Financial Analysis, Excel, SAP, Accounting'),(6,'2024-02-06 14:00:00.000000','candidate6@gmail.com','6 months internship','https://drive.google.com/cv6','Vu Thi C','0987654326','Python, Data Analysis, SQL'),(7,'2024-02-07 15:00:00.000000','candidate7@gmail.com','3 years in Sales','https://drive.google.com/cv7','Dang Van D','0987654327','B2B Sales, CRM, Negotiation'),(8,'2024-02-08 16:00:00.000000','candidate8@gmail.com','2 years in Operations','https://drive.google.com/cv8','Ngo Thi E','0987654328','Process Improvement, Logistics, Supply Chain'),(9,'2024-02-09 17:00:00.000000','candidate9@gmail.com','1 year in QA','https://drive.google.com/cv9','Bui Van F','0987654329','Manual Testing, Automation, Selenium'),(10,'2024-02-10 18:00:00.000000','candidate10@gmail.com','5 years in DevOps','https://drive.google.com/cv10','Do Thi G','0987654330','AWS, Docker, Kubernetes, CI/CD'),(11,'2024-02-11 09:00:00.000000','candidate11@gmail.com','Fresh graduate','https://drive.google.com/cv11','Truong Van H','0987654331','C#, .NET, SQL Server'),(12,'2024-02-12 10:00:00.000000','candidate12@gmail.com','2 years in Content Creation','https://drive.google.com/cv12','Ly Thi I','0987654332','Content Writing, Social Media, Photoshop'),(13,'2024-02-13 11:00:00.000000','candidate13@gmail.com','4 years in Accounting','https://drive.google.com/cv13','Mai Van J','0987654333','Accounting, Tax, ERP, Financial Reporting'),(14,'2024-02-14 12:00:00.000000','candidate14@gmail.com','3 years in Project Management','https://drive.google.com/cv14','Phan Thi K','0987654334','PMP, Agile, Scrum, Risk Management'),(15,'2024-02-15 13:00:00.000000','candidate15@gmail.com','1 year in UI/UX Design','https://drive.google.com/cv15','Vo Van L','0987654335','Figma, Adobe XD, User Research, Prototyping');
/*!40000 ALTER TABLE `candidate_profile` ENABLE KEYS */;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracts` (
  `id` int NOT NULL,
  `base_salary` double DEFAULT NULL,
  `contract_signing_date` datetime(6) DEFAULT NULL,
  `contract_status` enum('ACTIVE','CANCELLED','EXPIRED','SIGNED','SUSPENDED','TERMINATED') NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5c9xgkxh0n28hbhsgo5rkq58` (`employee_id`),
  KEY `FK7t4w023oae2vk90wrjra645o1` (`role_id`),
  CONSTRAINT `FK7t4w023oae2vk90wrjra645o1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKf5c9xgkxh0n28hbhsgo5rkq58` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (-255309529,NULL,NULL,'SIGNED','Temporary contract after candidate has passed the interview round',NULL,NULL,'Virtual Contract',-1735766110,7),(1,25000000,'2024-01-15 09:00:00.000000','ACTIVE','Hợp đồng quản lý nhân sự cao cấp','2025-01-15 09:00:00.000000','2024-01-15 09:00:00.000000','Contract HR Manager',1,1),(2,18000000,'2024-01-15 10:00:00.000000','ACTIVE','Hợp đồng chuyên viên nhân sự','2025-01-15 10:00:00.000000','2024-01-15 10:00:00.000000','Contract HR Specialist',2,2),(3,15000000,'2024-01-15 11:00:00.000000','ACTIVE','Hợp đồng tuyển dụng','2025-01-15 11:00:00.000000','2024-01-15 11:00:00.000000','Contract Recruiter',3,3),(4,30000000,'2024-01-16 09:00:00.000000','ACTIVE','Hợp đồng quản lý IT','2025-01-16 09:00:00.000000','2024-01-16 09:00:00.000000','Contract IT Manager',4,4),(5,22000000,'2024-01-16 10:00:00.000000','ACTIVE','Hợp đồng lập trình viên cao cấp','2025-01-16 10:00:00.000000','2024-01-16 10:00:00.000000','Contract Senior Developer',5,5),(6,12000000,'2024-01-16 11:00:00.000000','ACTIVE','Hợp đồng lập trình viên junior','2025-01-16 11:00:00.000000','2024-01-16 11:00:00.000000','Contract Junior Developer',6,6),(7,20000000,'2024-01-17 09:00:00.000000','ACTIVE','Hợp đồng DevOps Engineer','2025-01-17 09:00:00.000000','2024-01-17 09:00:00.000000','Contract DevOps Engineer',7,7),(8,16000000,'2024-01-17 10:00:00.000000','ACTIVE','Hợp đồng QA Engineer','2025-01-17 10:00:00.000000','2024-01-17 10:00:00.000000','Contract QA Engineer',8,8),(9,23000000,'2024-01-18 09:00:00.000000','ACTIVE','Hợp đồng quản lý marketing','2025-01-18 09:00:00.000000','2024-01-18 09:00:00.000000','Contract Marketing Manager',9,9),(10,17000000,'2024-01-18 10:00:00.000000','ACTIVE','Hợp đồng chuyên viên marketing số','2025-01-18 10:00:00.000000','2024-01-18 10:00:00.000000','Contract Digital Marketing',10,10),(11,13000000,'2024-01-19 09:00:00.000000','ACTIVE','Hợp đồng sáng tạo nội dung','2025-01-19 09:00:00.000000','2024-01-19 09:00:00.000000','Contract Content Creator',11,11),(12,26000000,'2024-01-19 10:00:00.000000','ACTIVE','Hợp đồng quản lý tài chính','2025-01-19 10:00:00.000000','2024-01-19 10:00:00.000000','Contract Finance Manager',12,12),(13,19000000,'2024-01-20 09:00:00.000000','ACTIVE','Hợp đồng kế toán','2025-01-20 09:00:00.000000','2024-01-20 09:00:00.000000','Contract Accountant',13,13),(14,21000000,'2024-01-20 10:00:00.000000','ACTIVE','Hợp đồng phân tích tài chính','2025-01-20 10:00:00.000000','2024-01-20 10:00:00.000000','Contract Financial Analyst',14,14),(15,24000000,'2024-01-21 09:00:00.000000','ACTIVE','Hợp đồng quản lý vận hành','2025-01-21 09:00:00.000000','2024-01-21 09:00:00.000000','Contract Operations Manager',15,15),(16,16000000,'2024-01-21 10:00:00.000000','ACTIVE','Hợp đồng chuyên viên vận hành','2025-01-21 10:00:00.000000','2024-01-21 10:00:00.000000','Contract Operations Specialist',16,16),(17,27000000,'2024-01-22 09:00:00.000000','ACTIVE','Hợp đồng quản lý bán hàng','2025-01-22 09:00:00.000000','2024-01-22 09:00:00.000000','Contract Sales Manager',17,17),(18,14000000,'2024-01-22 10:00:00.000000','ACTIVE','Hợp đồng nhân viên bán hàng','2025-01-22 10:00:00.000000','2024-01-22 10:00:00.000000','Contract Sales Executive',18,18),(19,18000000,'2024-01-23 09:00:00.000000','ACTIVE','Hợp đồng quản lý tài khoản','2025-01-23 09:00:00.000000','2024-01-23 09:00:00.000000','Contract Account Manager',19,19),(20,14000000,'2024-01-23 10:00:00.000000','SIGNED','Hợp đồng nhân viên bán hàng thử việc','2025-01-23 10:00:00.000000','2024-01-23 10:00:00.000000','Contract Sales Executive',20,18);
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;

--
-- Table structure for table `day_off`
--

DROP TABLE IF EXISTS `day_off`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `day_off` (
  `id` int NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `request_day` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrcu6uh65barsx51ccgnobglw4` (`employee_id`),
  CONSTRAINT `FKrcu6uh65barsx51ccgnobglw4` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `day_off`
--

/*!40000 ALTER TABLE `day_off` DISABLE KEYS */;
INSERT INTO `day_off` VALUES (-734968626,'2025-06-23 10:54:01.355000','string','2025-06-23 10:54:01.355000','2025-06-23 10:54:01.355000','PENDING',1),(1,'2025-06-23 10:56:57.998000','hihihihihihihihihih','2025-06-23 10:56:57.998000','2025-06-23 10:56:57.998000','string',1),(2,'2025-06-16 00:00:00.000000','Medical checkup','2025-06-03 14:30:00.000000','2025-06-15 00:00:00.000000','PENDING',2),(3,'2025-06-22 00:00:00.000000','Personal matters','2025-06-05 08:45:00.000000','2025-06-20 00:00:00.000000','REJECTED',3),(4,'2025-06-26 00:00:00.000000','Child’s school event','2025-06-06 10:00:00.000000','2025-06-25 00:00:00.000000','APPROVED',4),(5,'2025-07-03 00:00:00.000000','Family emergency','2025-06-07 11:15:00.000000','2025-07-01 00:00:00.000000','APPROVED',5),(6,'2025-07-06 00:00:00.000000','Mental health day','2025-06-08 09:30:00.000000','2025-07-05 00:00:00.000000','PENDING',6),(7,'2025-07-12 00:00:00.000000','Travel','2025-06-09 13:00:00.000000','2025-07-10 00:00:00.000000','APPROVED',7),(8,'2025-07-16 00:00:00.000000','Moving house','2025-06-10 15:45:00.000000','2025-07-15 00:00:00.000000','REJECTED',8),(9,'2025-07-21 00:00:00.000000','Wedding','2025-06-11 08:00:00.000000','2025-07-20 00:00:00.000000','APPROVED',9),(11,'2025-08-02 00:00:00.000000','Sick leave','2025-06-13 09:15:00.000000','2025-08-01 00:00:00.000000','APPROVED',1),(12,'2025-08-06 00:00:00.000000','Family visit','2025-06-14 14:00:00.000000','2025-08-05 00:00:00.000000','APPROVED',2),(13,'2025-08-11 00:00:00.000000','Religious holiday','2025-06-15 11:45:00.000000','2025-08-10 00:00:00.000000','PENDING',3),(14,'2025-08-16 00:00:00.000000','Volunteer work','2025-06-16 13:30:00.000000','2025-08-15 00:00:00.000000','APPROVED',4),(15,'2025-08-21 00:00:00.000000','Conference','2025-06-17 10:00:00.000000','2025-08-20 00:00:00.000000','APPROVED',5);
/*!40000 ALTER TABLE `day_off` ENABLE KEYS */;

--
-- Table structure for table `deduction`
--

DROP TABLE IF EXISTS `deduction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deduction` (
  `id` int NOT NULL,
  `amount` double DEFAULT NULL,
  `type_deduction` varchar(255) DEFAULT NULL,
  `salary_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1snthcbhy5l95w7wg2f4ogppr` (`salary_id`),
  CONSTRAINT `FK1snthcbhy5l95w7wg2f4ogppr` FOREIGN KEY (`salary_id`) REFERENCES `salary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deduction`
--

/*!40000 ALTER TABLE `deduction` DISABLE KEYS */;
INSERT INTO `deduction` VALUES (1,2500000,'BHXH',1),(2,1250000,'BHYT',1),(3,500000,'BHTN',1),(4,2000000,'Thuế TNCN',1),(5,1800000,'BHXH',6),(6,900000,'BHYT',6),(7,360000,'BHTN',6),(8,1200000,'Thuế TNCN',6),(9,1500000,'BHXH',11),(10,750000,'BHYT',11),(11,300000,'BHTN',11),(12,800000,'Thuế TNCN',11),(13,3000000,'BHXH',16),(14,1500000,'BHYT',16),(15,600000,'BHTN',16),(16,2500000,'Thuế TNCN',16),(17,2200000,'BHXH',21),(18,1100000,'BHYT',21),(19,440000,'BHTN',21),(20,1500000,'Thuế TNCN',21),(21,1200000,'BHXH',26),(22,600000,'BHYT',26),(23,240000,'BHTN',26),(24,600000,'Thuế TNCN',26),(25,2000000,'BHXH',31),(26,1000000,'BHYT',31),(27,400000,'BHTN',31),(28,1300000,'Thuế TNCN',31),(29,1600000,'BHXH',36),(30,800000,'BHYT',36),(31,320000,'BHTN',36),(32,1000000,'Thuế TNCN',36),(33,2300000,'BHXH',41),(34,1150000,'BHYT',41),(35,460000,'BHTN',41),(36,1600000,'Thuế TNCN',41),(37,1700000,'BHXH',46),(38,850000,'BHYT',46),(39,340000,'BHTN',46),(40,1100000,'Thuế TNCN',46),(41,1300000,'BHXH',51),(42,650000,'BHYT',51),(43,260000,'BHTN',51),(44,700000,'Thuế TNCN',51),(45,2600000,'BHXH',56),(46,1300000,'BHYT',56),(47,520000,'BHTN',56),(48,2100000,'Thuế TNCN',56),(49,1900000,'BHXH',61),(50,950000,'BHYT',61),(51,380000,'BHTN',61),(52,1300000,'Thuế TNCN',61),(53,2100000,'BHXH',66),(54,1050000,'BHYT',66),(55,420000,'BHTN',66),(56,1400000,'Thuế TNCN',66),(57,2400000,'BHXH',71),(58,1200000,'BHYT',71),(59,480000,'BHTN',71),(60,1800000,'Thuế TNCN',71),(61,1600000,'BHXH',76),(62,800000,'BHYT',76),(63,320000,'BHTN',76),(64,1000000,'Thuế TNCN',76),(65,2700000,'BHXH',81),(66,1350000,'BHYT',81),(67,540000,'BHTN',81),(68,2200000,'Thuế TNCN',81),(69,1400000,'BHXH',86),(70,700000,'BHYT',86),(71,280000,'BHTN',86),(72,800000,'Thuế TNCN',86),(73,1800000,'BHXH',91),(74,900000,'BHYT',91),(75,360000,'BHTN',91),(76,1200000,'Thuế TNCN',91),(77,1400000,'BHXH',96),(78,700000,'BHYT',96),(79,280000,'BHTN',96),(80,800000,'Thuế TNCN',96);
/*!40000 ALTER TABLE `deduction` ENABLE KEYS */;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES (-1638513540,'789 Dong Khoi, Q1, TP.HCM','Warehouse','Phòng kiểm kê lưu trữ hàng hóa','warehouse.hrm@hrm.com','028-123-1231'),(1,'123 Nguyen Hue, Q1, TP.HCM','Human Resources','Phòng Nhân sự quản lý tuyển dụng và phúc lợi nhân viên','hr@company.com','028-1234-5678'),(2,'456 Le Loi, Q1, TP.HCM','Information Technology','Phòng Công nghệ thông tin phát triển và bảo trì hệ thống','it@company.com','028-2345-6789'),(3,'789 Dong Khoi, Q1, TP.HCM','Marketing','Phòng Marketing và truyền thông','marketing@company.com','028-3456-7890'),(4,'321 Ham Nghi, Q1, TP.HCM','Finance','Phòng Tài chính và kế toán','finance@company.com','028-4567-8901'),(5,'654 Pasteur, Q1, TP.HCM','Operations','Phòng Vận hành và sản xuất','operations@company.com','028-5678-9012'),(6,'987 Tran Hung Dao, Q1, TP.HCM','Sales','Phòng Kinh doanh và bán hàng','sales@company.com','028-6789-0123');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;

--
-- Table structure for table `dependent`
--

DROP TABLE IF EXISTS `dependent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dependent` (
  `id` int NOT NULL,
  `birth_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `relationship` varchar(255) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp385qsjb4xvwa3wj3ngtp3kr` (`employee_id`),
  CONSTRAINT `FKp385qsjb4xvwa3wj3ngtp3kr` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependent`
--

/*!40000 ALTER TABLE `dependent` DISABLE KEYS */;
INSERT INTO `dependent` VALUES (2,'2015-07-22 00:00:00.000000','Nguyễn Văn Bảo','Child',1),(3,'1962-11-05 00:00:00.000000','Trần Thị Hạnh','Parent',2),(4,'2012-04-10 00:00:00.000000','Phạm Minh Khoa','Child',3),(5,'1989-09-30 00:00:00.000000','Đỗ Thị Lan','Spouse',4),(6,'1997-06-18 00:00:00.000000','Hoàng Văn Dũng','Sibling',5),(7,'2016-01-25 00:00:00.000000','Ngô Thị Mai','Child',6),(8,'1958-12-12 00:00:00.000000','Vũ Văn Hùng','Parent',7),(9,'1990-08-08 00:00:00.000000','Bùi Thị Yến','Spouse',8),(10,'2013-05-03 00:00:00.000000','Lý Văn Phúc','Child',9),(1581335420,'2025-06-23 07:30:40.538000','Hoaaaa','Con trai cưng',1);
/*!40000 ALTER TABLE `dependent` ENABLE KEYS */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `citizen_identification_card` varchar(255) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE','PROBATION','SUSPENDED','TERMINATED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (-1735766110,NULL,NULL,NULL,'kn26066@gmail.com','Khanh',NULL,NULL,NULL,'+67 56068037820',NULL,'ACTIVE'),(-1320205614,'avf','34546346346547','1994-04-14','a@gmail.com','ABV','male',NULL,'DDDD','0015545574','sf','ACTIVE'),(-344176996,'avf','34546346346547','1994-04-14','a12324@gmail.com','CCCCCCC','male',NULL,'FFFFFFFF','0015545574','sf','ACTIVE'),(1,'123 ABC Street, District 1, HCMC','99999999999','1985-03-15','nguyen.van.a@company.com','Nguyen Van','Male','employee_1.jpg','A','0901234567','HR Manager','ACTIVE'),(2,'456 DEF Street, District 2, HCMC','079234567890','1990-07-22','tran.thi.b@company.com','Tran Thi','Female','employee_2.jpg','B','0912345678','HR Specialist','ACTIVE'),(3,'789 GHI Street, District 3, HCMC','079345678901','1992-11-08','le.van.c@company.com','Le Van','Male','employee_3.jpg','C','0923456789','Recruiter','ACTIVE'),(4,'321 JKL Street, District 7, HCMC','079456789012','1980-01-12','pham.van.d@company.com','Pham Van','Male','employee_4.jpg','D','0934567890','IT Manager','ACTIVE'),(5,'654 MNO Street, District 4, HCMC','079567890123','1988-05-25','hoang.thi.e@company.com','Hoang Thi','Female','employee_5.jpg','E','0945678901','Senior Developer','ACTIVE'),(6,'987 PQR Street, District 5, HCMC','079678901234','1993-09-18','vu.van.f@company.com','Vu Van','Male','employee_6.jpg','F','0956789012','Junior Developer','ACTIVE'),(7,'159 STU Street, District 6, HCMC','079789012345','1991-12-03','dang.van.g@company.com','Dang Van','Male','employee_7.jpg','G','0967890123','DevOps Engineer','ACTIVE'),(8,'753 VWX Street, District 8, HCMC','079890123456','1994-04-14','ngo.thi.h@company.com','Ngo Thi','Female','employee_8.jpg','H','0978901234','QA Engineer','ACTIVE'),(9,'357 YZA Street, District 9, HCMC','079901234567','1987-08-27','bui.van.i@company.com','Bui Van','Male','employee_9.jpg','I','0989012345','Marketing Manager','ACTIVE'),(10,'951 BCD Street, District 10, HCMC','079012345678','1995-02-09','do.thi.j@company.com','Do Thi','Female','employee_10.jpg','J','0990123456','Digital Marketing Specialist','ACTIVE'),(11,'147 EFG Street, District 11, HCMC','079123456780','1996-06-16','truong.van.k@company.com','Truong Van','Male','employee_11.jpg','K','0901234560','Content Creator','ACTIVE'),(12,'258 HIJ Street, District 12, HCMC','079234567801','1983-10-31','ly.thi.l@company.com','Ly Thi','Female','employee_12.jpg','L','0912345601','Finance Manager','ACTIVE'),(13,'369 KLM Street, Thu Duc, HCMC','079345678012','1989-01-19','mai.van.m@company.com','Mai Van','Male','employee_13.jpg','M','0923456012','Accountant','ACTIVE'),(14,'470 NOP Street, Binh Thanh, HCMC','079456789123','1992-05-07','phan.thi.n@company.com','Phan Thi','Female','employee_14.jpg','N','0934567123','Financial Analyst','ACTIVE'),(15,'581 QRS Street, Tan Binh, HCMC','079567890234','1986-09-23','vo.van.o@company.com','Vo Van','Male','employee_15.jpg','O','0945678234','Operations Manager','ACTIVE'),(16,'692 TUV Street, Go Vap, HCMC','079678901345','1993-03-11','tran.thi.p@company.com','Tran Thi','Female','employee_16.jpg','P','0956789345','Operations Specialist','ACTIVE'),(17,'703 WXY Street, Phu Nhuan, HCMC','079789012456','1984-07-04','nguyen.van.q@company.com','Nguyen Van','Male','employee_17.jpg','Q','0967890456','Sales Manager','ACTIVE'),(18,'814 ZAB Street, Tan Phu, HCMC','079890123567','1990-11-28','le.thi.r@company.com','Le Thi','Female','employee_18.jpg','R','0978901567','Sales Executive','ACTIVE'),(19,'925 CDE Street, District 1, HCMC','079901234678','1991-04-15','pham.van.s@company.com','Pham Van','Male','employee_19.jpg','S','0989012678','Account Manager','ACTIVE'),(20,'036 FGH Street, District 2, HCMC','079012345789','1988-12-20','hoang.thi.t@company.com','Hoang Thi','Female','employee_20.jpg','T','0990123789','Sales Executive','PROBATION'),(954615631,'987 PQR Street, District 5, HCMC','079012345678','1993-09-18','ntntnt@gmail.com','Nguyen','Male',NULL,'Thong','0897654345','Digital Marketing Specialist','ACTIVE');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;

--
-- Table structure for table `evaluate`
--

DROP TABLE IF EXISTS `evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluate` (
  `id` int NOT NULL,
  `evaluate` varchar(255) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `feedback_at` datetime(6) DEFAULT NULL,
  `candidate_profile_id` int DEFAULT NULL,
  `create_by_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9qw12m6753vn2ep6lmo9ly368` (`candidate_profile_id`),
  KEY `FKog38wx0yb0gdirrwt36wqdbft` (`create_by_id`),
  CONSTRAINT `FK9qw12m6753vn2ep6lmo9ly368` FOREIGN KEY (`candidate_profile_id`) REFERENCES `candidate_profile` (`id`),
  CONSTRAINT `FKog38wx0yb0gdirrwt36wqdbft` FOREIGN KEY (`create_by_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluate`
--

/*!40000 ALTER TABLE `evaluate` DISABLE KEYS */;
INSERT INTO `evaluate` VALUES (1,'GOOD','Ứng viên có kinh nghiệm tốt với Java, cần cải thiện kỹ năng giao tiếp','2024-02-20 14:00:00.000000',1,3),(2,'EXCELLENT','Ứng viên xuất sắc, phù hợp với vị trí Junior Developer','2024-02-25 15:00:00.000000',11,3),(3,'GOOD','Kỹ năng React tốt, có thể làm việc độc lập','2024-02-26 14:00:00.000000',2,3),(4,'AVERAGE','Thiếu kinh nghiệm thực tế, cần đào tạo thêm','2024-02-27 16:00:00.000000',6,3),(5,'GOOD','Hiểu biết tốt về marketing digital, có ý tưởng sáng tạo','2024-03-01 14:00:00.000000',3,3),(6,'EXCELLENT','Kỹ năng phân tích tài chính mạnh, kinh nghiệm phong phú','2024-03-10 15:00:00.000000',13,3),(7,'GOOD','Kỹ năng testing tốt, có thể học hỏi automation','2024-03-15 14:00:00.000000',9,3),(8,'AVERAGE','Cần cải thiện kỹ năng bán hàng và giao tiếp','2024-03-20 16:00:00.000000',7,3),(9,'GOOD','Kỹ năng viết content tốt, hiểu về social media','2024-03-30 14:00:00.000000',12,3),(10,'EXCELLENT','Ứng viên có khả năng UI/UX tốt, phù hợp với Content Creator','2024-04-01 15:00:00.000000',15,3);
/*!40000 ALTER TABLE `evaluate` ENABLE KEYS */;

--
-- Table structure for table `feedback_employee`
--

DROP TABLE IF EXISTS `feedback_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_employee` (
  `id` int NOT NULL,
  `areas_for_improvement` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `feedback_content` varchar(255) DEFAULT NULL,
  `feedback_type` enum('MANAGER_REVIEW','PEER_REVIEW','SELF_ASSESSMENT','SUBORDINATE_REVIEW') NOT NULL,
  `is_anonymous` bit(1) DEFAULT NULL,
  `strengths` varchar(255) DEFAULT NULL,
  `suggestions` varchar(255) DEFAULT NULL,
  `employee_id` int NOT NULL,
  `feedback_provider_id` int DEFAULT NULL,
  `performance_review_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnw2ny26hb0qkxub227lnn3t0o` (`employee_id`),
  KEY `FKbko1jaxococdravb55gpwa7a7` (`feedback_provider_id`),
  KEY `FKptn0iq653w5uubays743eqvqh` (`performance_review_id`),
  CONSTRAINT `FKbko1jaxococdravb55gpwa7a7` FOREIGN KEY (`feedback_provider_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKnw2ny26hb0qkxub227lnn3t0o` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKptn0iq653w5uubays743eqvqh` FOREIGN KEY (`performance_review_id`) REFERENCES `performance_review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_employee`
--

/*!40000 ALTER TABLE `feedback_employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback_employee` ENABLE KEYS */;

--
-- Table structure for table `pay_periods`
--

DROP TABLE IF EXISTS `pay_periods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_periods` (
  `id` int NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `pay_period_code` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` enum('Closed','Open') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_periods`
--

/*!40000 ALTER TABLE `pay_periods` DISABLE KEYS */;
INSERT INTO `pay_periods` VALUES (-1259191496,'2025-06-23 05:07:59.626000','Lương tháng 6','2025-06-23 05:07:59.626000','Open'),(1,'2025-01-31 23:59:59.000000','PP2025-01','2025-01-01 00:00:00.000000','Closed'),(2,'2025-02-28 23:59:59.000000','PP2025-02','2025-02-01 00:00:00.000000','Closed'),(3,'2025-03-31 23:59:59.000000','PP2025-03','2025-03-01 00:00:00.000000','Closed'),(4,'2025-04-30 23:59:59.000000','PP2025-04','2025-04-01 00:00:00.000000','Closed'),(5,'2025-05-31 23:59:59.000000','PP2025-05','2025-05-01 00:00:00.000000','Closed'),(6,'2025-06-30 23:59:59.000000','PP2025-06','2025-06-01 00:00:00.000000','Open'),(7,'2025-07-31 23:59:59.000000','PP2025-07','2025-07-01 00:00:00.000000','Open'),(8,'2025-08-31 23:59:59.000000','PP2025-08','2025-08-01 00:00:00.000000','Open'),(9,'2025-09-30 23:59:59.000000','PP2025-09','2025-09-01 00:00:00.000000','Open'),(10,'2025-10-31 23:59:59.000000','PP2025-10','2025-10-01 00:00:00.000000','Open'),(11,'2025-11-30 23:59:59.000000','PP2025-11','2025-11-01 00:00:00.000000','Open'),(12,'2025-12-31 23:59:59.000000','PP2025-12','2025-12-01 00:00:00.000000','Open'),(13,'2024-11-30 23:59:59.000000','PP2024-11','2024-11-01 00:00:00.000000','Closed'),(14,'2024-12-31 23:59:59.000000','PP2024-12','2024-12-01 00:00:00.000000','Closed'),(15,'2026-01-31 23:59:59.000000','PP2026-01','2026-01-01 00:00:00.000000','Open'),(16,'2026-02-28 23:59:59.000000','PP2026-02','2026-02-01 00:00:00.000000','Open'),(17,'2026-03-31 23:59:59.000000','PP2026-03','2026-03-01 00:00:00.000000','Open'),(18,'2026-04-30 23:59:59.000000','PP2026-04','2026-04-01 00:00:00.000000','Open'),(19,'2026-05-31 23:59:59.000000','PP2026-05','2026-05-01 00:00:00.000000','Open'),(20,'2026-06-30 23:59:59.000000','PP2026-06','2026-06-01 00:00:00.000000','Open'),(932790467,'2025-06-23 05:07:59.626000','Lương tháng 4','2025-06-23 05:07:59.626000','Closed'),(956711442,'2025-06-23 05:07:59.626000','Lương tháng 7','2025-06-23 05:07:59.626000','Closed'),(1287736253,'2025-06-23 05:07:59.626000','Lương tháng 3','2025-06-23 05:07:59.626000','Closed');
/*!40000 ALTER TABLE `pay_periods` ENABLE KEYS */;

--
-- Table structure for table `payroll_components`
--

DROP TABLE IF EXISTS `payroll_components`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payroll_components` (
  `id` int NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percentage` float DEFAULT NULL,
  `type` enum('Deduction','Subsidy') NOT NULL,
  `regulation_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKptwk815cryvlmun26w7ktjxm6` (`regulation_id`),
  CONSTRAINT `FKptwk815cryvlmun26w7ktjxm6` FOREIGN KEY (`regulation_id`) REFERENCES `regulations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payroll_components`
--

/*!40000 ALTER TABLE `payroll_components` DISABLE KEYS */;
INSERT INTO `payroll_components` VALUES (1,200000.00,'Bảo hiểm xã hội',8,'Deduction',1),(2,300000.00,'Bảo hiểm y tế',1.5,'Deduction',2),(3,300000.00,'Phụ cấp chuyên cần',5,'Subsidy',3),(4,150000.00,'Trợ cấp xăng xe',NULL,'Subsidy',5),(5,100000.00,'Khấu trừ trễ giờ',NULL,'Deduction',NULL),(489767723,0.00,'hihihi',0,'Subsidy',1),(1371882658,0.00,'string2',7.5,'Subsidy',2);
/*!40000 ALTER TABLE `payroll_components` ENABLE KEYS */;

--
-- Table structure for table `payroll_details`
--

DROP TABLE IF EXISTS `payroll_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payroll_details` (
  `id` int NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `is_percentage` bit(1) DEFAULT NULL,
  `percentage` float DEFAULT NULL,
  `payroll_id` int DEFAULT NULL,
  `payroll_component_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKlfrg0dy6vdqlf1sqtkeehie2p` (`payroll_component_id`),
  KEY `FKm9bx5xl3wqr1f5rif5yt25oc5` (`payroll_id`),
  CONSTRAINT `FK7jhfsumbll8qr6ew3cm8oxluo` FOREIGN KEY (`payroll_component_id`) REFERENCES `payroll_components` (`id`),
  CONSTRAINT `FKm9bx5xl3wqr1f5rif5yt25oc5` FOREIGN KEY (`payroll_id`) REFERENCES `payrolls` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payroll_details`
--

/*!40000 ALTER TABLE `payroll_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `payroll_details` ENABLE KEYS */;

--
-- Table structure for table `payrolls`
--

DROP TABLE IF EXISTS `payrolls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payrolls` (
  `id` int NOT NULL,
  `net_salary` decimal(38,2) DEFAULT NULL,
  `status` enum('Approved','Paid','Pending','Rejected') NOT NULL,
  `total_deduction` decimal(38,2) DEFAULT NULL,
  `total_income` decimal(38,2) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `pay_period_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiyfp8uysuhgfkod3xcdhjm7qf` (`employee_id`),
  KEY `FKl6uiggfecq5509urtl9jans9i` (`pay_period_id`),
  CONSTRAINT `FKiyfp8uysuhgfkod3xcdhjm7qf` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKl6uiggfecq5509urtl9jans9i` FOREIGN KEY (`pay_period_id`) REFERENCES `pay_periods` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payrolls`
--

/*!40000 ALTER TABLE `payrolls` DISABLE KEYS */;
INSERT INTO `payrolls` VALUES (1,23000000.00,'Paid',2000000.00,25000000.00,1,1),(2,25500000.00,'Paid',1500000.00,27000000.00,2,2),(3,21000000.00,'Approved',1000000.00,22000000.00,3,3),(4,22200000.00,'Paid',1800000.00,24000000.00,4,4),(5,27500000.00,'Paid',2500000.00,30000000.00,5,5),(6,24800000.00,'Approved',1200000.00,26000000.00,6,1),(7,25000000.00,'Rejected',3000000.00,28000000.00,7,2),(8,21300000.00,'Paid',1700000.00,23000000.00,8,3),(9,23500000.00,'Pending',2000000.00,25500000.00,9,4),(10,23000000.00,'Paid',1500000.00,24500000.00,10,5),(11,23000000.00,'Paid',2000000.00,25000000.00,1,1),(12,25500000.00,'Approved',1500000.00,27000000.00,2,2),(13,21000000.00,'Paid',1000000.00,22000000.00,3,3),(14,22200000.00,'Rejected',1800000.00,24000000.00,4,4),(15,27500000.00,'Paid',2500000.00,30000000.00,5,5),(16,24800000.00,'Paid',1200000.00,26000000.00,6,1),(17,25000000.00,'Approved',3000000.00,28000000.00,7,2),(18,21300000.00,'Paid',1700000.00,23000000.00,8,3),(19,23500000.00,'Paid',2000000.00,25500000.00,9,4),(20,23000000.00,'Pending',1500000.00,24500000.00,10,5);
/*!40000 ALTER TABLE `payrolls` ENABLE KEYS */;

--
-- Table structure for table `performance_review`
--

DROP TABLE IF EXISTS `performance_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performance_review` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `overall_comment` varchar(255) DEFAULT NULL,
  `overall_score` float DEFAULT NULL,
  `review_cycle` enum('ANNUAL','PROBATION','PROJECT_BASED','QUARTERLY','SEMI_ANNUAL') NOT NULL,
  `review_end_date` datetime(6) DEFAULT NULL,
  `review_start_date` datetime(6) DEFAULT NULL,
  `status` enum('CANCELLED','COMPLETED','IN_PROGRESS','OVERDUE','PENDING_APPROVAL','SCHEDULED') NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `approver_id` int DEFAULT NULL,
  `employee_id` int NOT NULL,
  `employee_request_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5gk112og7tuxqau7i7jkmwaum` (`approver_id`),
  KEY `FKgly627h3g3cacds5jl46wduiy` (`employee_id`),
  KEY `FKbr1l055ake4k0ruayqjag4i74` (`employee_request_id`),
  CONSTRAINT `FK5gk112og7tuxqau7i7jkmwaum` FOREIGN KEY (`approver_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKbr1l055ake4k0ruayqjag4i74` FOREIGN KEY (`employee_request_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKgly627h3g3cacds5jl46wduiy` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance_review`
--

/*!40000 ALTER TABLE `performance_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `performance_review` ENABLE KEYS */;

--
-- Table structure for table `performance_review_detail`
--

DROP TABLE IF EXISTS `performance_review_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performance_review_detail` (
  `id` int NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `criteria_description` varchar(255) DEFAULT NULL,
  `criteria_name` varchar(255) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `performance_review_id` int NOT NULL,
  `reviewer_id` int NOT NULL,
  `review_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpluxypgcdvcd9da6jkglh93sv` (`performance_review_id`),
  KEY `FKefseldnn8kf0druxr3yrbxpns` (`reviewer_id`),
  CONSTRAINT `FKefseldnn8kf0druxr3yrbxpns` FOREIGN KEY (`reviewer_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKpluxypgcdvcd9da6jkglh93sv` FOREIGN KEY (`performance_review_id`) REFERENCES `performance_review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance_review_detail`
--

/*!40000 ALTER TABLE `performance_review_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `performance_review_detail` ENABLE KEYS */;

--
-- Table structure for table `recruitment`
--

DROP TABLE IF EXISTS `recruitment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recruitment` (
  `id` int NOT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `deadline` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job_description` varchar(255) DEFAULT NULL,
  `status` enum('ARCHIVED','CLOSED','EXPIRED','OPEN') NOT NULL,
  `on_approve` int DEFAULT NULL,
  `recruitment_requirements_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj9a51vouus6ejwkbnljsa2rc8` (`recruitment_requirements_id`),
  KEY `FKmw33dyv1ggc2qedn2vmjcdny9` (`on_approve`),
  CONSTRAINT `FK6gkr45px6ukqohvc5k5gvl8ov` FOREIGN KEY (`recruitment_requirements_id`) REFERENCES `recruitment_requirements` (`id`),
  CONSTRAINT `FKmw33dyv1ggc2qedn2vmjcdny9` FOREIGN KEY (`on_approve`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruitment`
--

/*!40000 ALTER TABLE `recruitment` DISABLE KEYS */;
INSERT INTO `recruitment` VALUES (-588624674,'028-1234-5678','2025-06-19 04:48:27.046778','2025-06-18 21:46:02.438000','hr@company.com','Chuyeen vien IT','OPEN',16,750470719),(1,'028-1234-5678','2024-02-16 08:00:00.000000','2024-03-16 23:59:59.000000','hr@company.com','Phát triển ứng dụng Java, làm việc trong môi trường Agile','OPEN',1,1),(2,'028-1234-5678','2024-02-21 08:00:00.000000','2024-03-21 23:59:59.000000','hr@company.com','Phát triển giao diện người dùng với React','OPEN',1,2),(3,'028-1234-5678','2024-02-26 08:00:00.000000','2024-03-26 23:59:59.000000','hr@company.com','Thực hiện các chiến dịch marketing digital','OPEN',1,3),(4,'028-1234-5678','2024-03-06 08:00:00.000000','2024-04-06 23:59:59.000000','hr@company.com','Phân tích dữ liệu tài chính và lập báo cáo','OPEN',1,5),(5,'028-1234-5678','2024-03-11 08:00:00.000000','2024-04-11 23:59:59.000000','hr@company.com','Kiểm thử phần mềm và đảm bảo chất lượng','OPEN',1,6),(6,'028-1234-5678','2024-03-16 08:00:00.000000','2024-04-16 23:59:59.000000','hr@company.com','Bán hàng B2B và chăm sóc khách hàng','OPEN',1,7),(7,'028-1234-5678','2024-03-26 08:00:00.000000','2024-04-26 23:59:59.000000','hr@company.com','Tạo và quản lý nội dung trên các nền tảng','OPEN',1,9),(8,'028-1234-5678','2024-03-31 08:00:00.000000','2024-04-30 23:59:59.000000','hr@company.com','Quản lý và tối ưu quy trình vận hành','OPEN',1,10);
/*!40000 ALTER TABLE `recruitment` ENABLE KEYS */;

--
-- Table structure for table `recruitment_requirements`
--

DROP TABLE IF EXISTS `recruitment_requirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recruitment_requirements` (
  `id` int NOT NULL,
  `date_required` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `expected_salary` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` enum('APPROVED','FILLED','PENDING','REJECTED') NOT NULL,
  `on_upload` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtlea0lwqoim8vx7bom2icn58r` (`on_upload`),
  KEY `FKvr6k2wtk8rpv6tp9sn57xley` (`role_id`),
  CONSTRAINT `FKtlea0lwqoim8vx7bom2icn58r` FOREIGN KEY (`on_upload`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKvr6k2wtk8rpv6tp9sn57xley` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruitment_requirements`
--

/*!40000 ALTER TABLE `recruitment_requirements` DISABLE KEYS */;
INSERT INTO `recruitment_requirements` VALUES (-1597815653,'2025-06-19 04:38:59.257708','Tuyển QA Engineer','12-16 triệu',3,'PENDING',16,5),(-481606493,'2025-06-19 04:43:29.808214','Tuyển QA Engineer','12-16 triệu',5,'PENDING',16,7),(1,'2024-02-15 00:00:00.000000','Cần tuyển Java Developer có kinh nghiệm 2+ năm','15-20 triệu',2,'APPROVED',4,6),(2,'2024-02-20 00:00:00.000000','Tuyển Frontend Developer React','12-18 triệu',1,'APPROVED',4,6),(3,'2024-02-25 00:00:00.000000','Tuyển Marketing Specialist','12-15 triệu',1,'APPROVED',9,10),(4,'2024-03-01 00:00:00.000000','Tuyển HR Specialist','10-15 triệu',1,'PENDING',1,2),(5,'2024-03-05 00:00:00.000000','Tuyển Financial Analyst','15-20 triệu',1,'APPROVED',12,14),(6,'2024-03-10 00:00:00.000000','Tuyển QA Engineer','12-16 triệu',1,'APPROVED',4,8),(7,'2024-03-15 00:00:00.000000','Tuyển Sales Executive','10-15 triệu + hoa hồng',2,'APPROVED',17,18),(8,'2024-03-20 00:00:00.000000','Tuyển DevOps Engineer','20-25 triệu',1,'PENDING',4,7),(9,'2024-03-25 00:00:00.000000','Tuyển Content Creator','8-12 triệu',1,'APPROVED',9,11),(10,'2024-03-30 00:00:00.000000','Tuyển Operations Specialist','12-16 triệu',1,'APPROVED',15,16),(750470719,'2025-06-19 04:40:29.135050','Tuyển QA Engineer','12-16 triệu',3,'APPROVED',16,7);
/*!40000 ALTER TABLE `recruitment_requirements` ENABLE KEYS */;

--
-- Table structure for table `regulations`
--

DROP TABLE IF EXISTS `regulations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regulations` (
  `id` int NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `applicable_salary` decimal(38,2) DEFAULT NULL,
  `effective_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percentage` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regulations`
--

/*!40000 ALTER TABLE `regulations` DISABLE KEYS */;
INSERT INTO `regulations` VALUES (1,1000000.00,5000000.00,'2024-01-01 00:00:00.000000','Quy định thưởng Tết',10.5),(2,750000.00,4500000.00,'2024-06-01 00:00:00.000000','Phụ cấp trách nhiệm',5),(3,1500000.00,7000000.00,'2025-01-01 00:00:00.000000','Thưởng năng suất',12),(4,NULL,3000000.00,'2023-10-01 00:00:00.000000','Hỗ trợ đào tạo',NULL),(5,500000.00,NULL,'2024-03-15 00:00:00.000000','Trợ cấp đi lại',2.5);
/*!40000 ALTER TABLE `regulations` ENABLE KEYS */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `departments_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKblyq6dhaqraa1wvtbekpg232f` (`departments_id`),
  CONSTRAINT `FKblyq6dhaqraa1wvtbekpg232f` FOREIGN KEY (`departments_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'HR Manager',1),(2,'HR Specialist',1),(3,'Recruiter',1),(4,'IT Manager',2),(5,'Senior Developer',2),(6,'Junior Developer',2),(7,'DevOps Engineer',2),(8,'QA Engineer',2),(9,'Marketing Manager',3),(10,'Digital Marketing Specialist',3),(11,'Content Creator',3),(12,'Finance Manager',4),(13,'Accountant',4),(14,'Financial Analyst',4),(15,'Operations Manager',5),(16,'Operations Specialist',5),(17,'Sales Manager',6),(18,'Sales Executive',6),(19,'Account Manager',6);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

--
-- Table structure for table `salary`
--

DROP TABLE IF EXISTS `salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salary` (
  `id` int NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8shr4to2ct7gci2vauwolmrlk` (`employee_id`),
  CONSTRAINT `FK8shr4to2ct7gci2vauwolmrlk` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary`
--

/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
INSERT INTO `salary` VALUES (1,'2024-01-31 23:59:59.000000',1),(2,'2024-02-29 23:59:59.000000',1),(3,'2024-03-31 23:59:59.000000',1),(4,'2024-04-30 23:59:59.000000',1),(5,'2024-05-31 23:59:59.000000',1),(6,'2024-01-31 23:59:59.000000',2),(7,'2024-02-29 23:59:59.000000',2),(8,'2024-03-31 23:59:59.000000',2),(9,'2024-04-30 23:59:59.000000',2),(10,'2024-05-31 23:59:59.000000',2),(11,'2024-01-31 23:59:59.000000',3),(12,'2024-02-29 23:59:59.000000',3),(13,'2024-03-31 23:59:59.000000',3),(14,'2024-04-30 23:59:59.000000',3),(15,'2024-05-31 23:59:59.000000',3),(16,'2024-01-31 23:59:59.000000',4),(17,'2024-02-29 23:59:59.000000',4),(18,'2024-03-31 23:59:59.000000',4),(19,'2024-04-30 23:59:59.000000',4),(20,'2024-05-31 23:59:59.000000',4),(21,'2024-01-31 23:59:59.000000',5),(22,'2024-02-29 23:59:59.000000',5),(23,'2024-03-31 23:59:59.000000',5),(24,'2024-04-30 23:59:59.000000',5),(25,'2024-05-31 23:59:59.000000',5),(26,'2024-01-31 23:59:59.000000',6),(27,'2024-02-29 23:59:59.000000',6),(28,'2024-03-31 23:59:59.000000',6),(29,'2024-04-30 23:59:59.000000',6),(30,'2024-05-31 23:59:59.000000',6),(31,'2024-01-31 23:59:59.000000',7),(32,'2024-02-29 23:59:59.000000',7),(33,'2024-03-31 23:59:59.000000',7),(34,'2024-04-30 23:59:59.000000',7),(35,'2024-05-31 23:59:59.000000',7),(36,'2024-01-31 23:59:59.000000',8),(37,'2024-02-29 23:59:59.000000',8),(38,'2024-03-31 23:59:59.000000',8),(39,'2024-04-30 23:59:59.000000',8),(40,'2024-05-31 23:59:59.000000',8),(41,'2024-01-31 23:59:59.000000',9),(42,'2024-02-29 23:59:59.000000',9),(43,'2024-03-31 23:59:59.000000',9),(44,'2024-04-30 23:59:59.000000',9),(45,'2024-05-31 23:59:59.000000',9),(46,'2024-01-31 23:59:59.000000',10),(47,'2024-02-29 23:59:59.000000',10),(48,'2024-03-31 23:59:59.000000',10),(49,'2024-04-30 23:59:59.000000',10),(50,'2024-05-31 23:59:59.000000',10),(51,'2024-01-31 23:59:59.000000',11),(52,'2024-02-29 23:59:59.000000',11),(53,'2024-03-31 23:59:59.000000',11),(54,'2024-04-30 23:59:59.000000',11),(55,'2024-05-31 23:59:59.000000',11),(56,'2024-01-31 23:59:59.000000',12),(57,'2024-02-29 23:59:59.000000',12),(58,'2024-03-31 23:59:59.000000',12),(59,'2024-04-30 23:59:59.000000',12),(60,'2024-05-31 23:59:59.000000',12),(61,'2024-01-31 23:59:59.000000',13),(62,'2024-02-29 23:59:59.000000',13),(63,'2024-03-31 23:59:59.000000',13),(64,'2024-04-30 23:59:59.000000',13),(65,'2024-05-31 23:59:59.000000',13),(66,'2024-01-31 23:59:59.000000',14),(67,'2024-02-29 23:59:59.000000',14),(68,'2024-03-31 23:59:59.000000',14),(69,'2024-04-30 23:59:59.000000',14),(70,'2024-05-31 23:59:59.000000',14),(71,'2024-01-31 23:59:59.000000',15),(72,'2024-02-29 23:59:59.000000',15),(73,'2024-03-31 23:59:59.000000',15),(74,'2024-04-30 23:59:59.000000',15),(75,'2024-05-31 23:59:59.000000',15),(76,'2024-01-31 23:59:59.000000',16),(77,'2024-02-29 23:59:59.000000',16),(78,'2024-03-31 23:59:59.000000',16),(79,'2024-04-30 23:59:59.000000',16),(80,'2024-05-31 23:59:59.000000',16),(81,'2024-01-31 23:59:59.000000',17),(82,'2024-02-29 23:59:59.000000',17),(83,'2024-03-31 23:59:59.000000',17),(84,'2024-04-30 23:59:59.000000',17),(85,'2024-05-31 23:59:59.000000',17),(86,'2024-01-31 23:59:59.000000',18),(87,'2024-02-29 23:59:59.000000',18),(88,'2024-03-31 23:59:59.000000',18),(89,'2024-04-30 23:59:59.000000',18),(90,'2024-05-31 23:59:59.000000',18),(91,'2024-01-31 23:59:59.000000',19),(92,'2024-02-29 23:59:59.000000',19),(93,'2024-03-31 23:59:59.000000',19),(94,'2024-04-30 23:59:59.000000',19),(95,'2024-05-31 23:59:59.000000',19),(96,'2024-01-31 23:59:59.000000',20),(97,'2024-02-29 23:59:59.000000',20),(98,'2024-03-31 23:59:59.000000',20),(99,'2024-04-30 23:59:59.000000',20),(100,'2024-05-31 23:59:59.000000',20);
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;

--
-- Table structure for table `subsidy`
--

DROP TABLE IF EXISTS `subsidy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subsidy` (
  `id` int NOT NULL,
  `amount` double DEFAULT NULL,
  `type_subsidy` varchar(255) DEFAULT NULL,
  `salary_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoex0mqjp7h0r4i8c8as2n7hpf` (`salary_id`),
  CONSTRAINT `FKoex0mqjp7h0r4i8c8as2n7hpf` FOREIGN KEY (`salary_id`) REFERENCES `salary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subsidy`
--

/*!40000 ALTER TABLE `subsidy` DISABLE KEYS */;
INSERT INTO `subsidy` VALUES (1,2000000,'Phụ cấp xăng xe',1),(2,1500000,'Phụ cấp ăn trưa',1),(3,500000,'Phụ cấp điện thoại',1),(4,1000000,'Phụ cấp xăng xe',6),(5,1500000,'Phụ cấp ăn trưa',6),(6,500000,'Phụ cấp điện thoại',6),(7,800000,'Phụ cấp xăng xe',11),(8,1500000,'Phụ cấp ăn trưa',11),(9,500000,'Phụ cấp điện thoại',11),(10,2500000,'Phụ cấp xăng xe',16),(11,1500000,'Phụ cấp ăn trưa',16),(12,800000,'Phụ cấp điện thoại',16),(13,1800000,'Phụ cấp xăng xe',21),(14,1500000,'Phụ cấp ăn trưa',21),(15,600000,'Phụ cấp điện thoại',21),(16,1000000,'Phụ cấp xăng xe',26),(17,1500000,'Phụ cấp ăn trưa',26),(18,500000,'Phụ cấp điện thoại',26),(19,1600000,'Phụ cấp xăng xe',31),(20,1500000,'Phụ cấp ăn trưa',31),(21,500000,'Phụ cấp điện thoại',31),(22,1200000,'Phụ cấp xăng xe',36),(23,1500000,'Phụ cấp ăn trưa',36),(24,500000,'Phụ cấp điện thoại',36),(25,2000000,'Phụ cấp xăng xe',41),(26,1500000,'Phụ cấp ăn trưa',41),(27,700000,'Phụ cấp điện thoại',41),(28,1400000,'Phụ cấp xăng xe',46),(29,1500000,'Phụ cấp ăn trưa',46),(30,500000,'Phụ cấp điện thoại',46),(31,1000000,'Phụ cấp xăng xe',51),(32,1500000,'Phụ cấp ăn trưa',51),(33,500000,'Phụ cấp điện thoại',51),(34,2200000,'Phụ cấp xăng xe',56),(35,1500000,'Phụ cấp ăn trưa',56),(36,800000,'Phụ cấp điện thoại',56),(37,1600000,'Phụ cấp xăng xe',61),(38,1500000,'Phụ cấp ăn trưa',61),(39,600000,'Phụ cấp điện thoại',61),(40,1800000,'Phụ cấp xăng xe',66),(41,1500000,'Phụ cấp ăn trưa',66),(42,600000,'Phụ cấp điện thoại',66),(43,2000000,'Phụ cấp xăng xe',71),(44,1500000,'Phụ cấp ăn trưa',71),(45,700000,'Phụ cấp điện thoại',71),(46,1200000,'Phụ cấp xăng xe',76),(47,1500000,'Phụ cấp ăn trưa',76),(48,500000,'Phụ cấp điện thoại',76),(49,2300000,'Phụ cấp xăng xe',81),(50,1500000,'Phụ cấp ăn trưa',81),(51,800000,'Phụ cấp điện thoại',81),(52,1000000,'Phụ cấp xăng xe',86),(53,1500000,'Phụ cấp ăn trưa',86),(54,500000,'Phụ cấp điện thoại',86),(55,1500000,'Phụ cấp xăng xe',91),(56,1500000,'Phụ cấp ăn trưa',91),(57,600000,'Phụ cấp điện thoại',91),(58,1000000,'Phụ cấp xăng xe',96),(59,1500000,'Phụ cấp ăn trưa',96),(60,500000,'Phụ cấp điện thoại',96);
/*!40000 ALTER TABLE `subsidy` ENABLE KEYS */;

--
-- Table structure for table `training_enrollment`
--

DROP TABLE IF EXISTS `training_enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_enrollment` (
  `id` int NOT NULL,
  `attendance_rate` double DEFAULT NULL,
  `completion_date` datetime(6) DEFAULT NULL,
  `enrollment_date` datetime(6) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `status` enum('COMPLETED','DROPPED','ENROLLED','FAILED','IN_PROGRESS') DEFAULT NULL,
  `test_score` double DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `training_request_id` int DEFAULT NULL,
  `training_session_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhel5kkvahl4hbbyh58x9e7lqw` (`employee_id`),
  KEY `FKld61jddla2turnbwcmuej4jyf` (`training_request_id`),
  KEY `FKnryflikwl8fdlc57i7h1df72a` (`training_session_id`),
  CONSTRAINT `FKhel5kkvahl4hbbyh58x9e7lqw` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKld61jddla2turnbwcmuej4jyf` FOREIGN KEY (`training_request_id`) REFERENCES `training_request` (`id`),
  CONSTRAINT `FKnryflikwl8fdlc57i7h1df72a` FOREIGN KEY (`training_session_id`) REFERENCES `training_session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_enrollment`
--

/*!40000 ALTER TABLE `training_enrollment` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_enrollment` ENABLE KEYS */;

--
-- Table structure for table `training_program`
--

DROP TABLE IF EXISTS `training_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_program` (
  `id` int NOT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_mandatory` bit(1) DEFAULT NULL,
  `materials` varchar(255) DEFAULT NULL,
  `prerequisites` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `create_by_id` int DEFAULT NULL,
  `departments_id` int DEFAULT NULL,
  `target_role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjy7ae1vp4bjr5k3bbk1mxj9tt` (`create_by_id`),
  KEY `FKmrw0jfof514hoh2fh6u3fw5me` (`departments_id`),
  KEY `FK1v25jq3ccfr8m6gqwurthou5h` (`target_role_id`),
  CONSTRAINT `FK1v25jq3ccfr8m6gqwurthou5h` FOREIGN KEY (`target_role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKjy7ae1vp4bjr5k3bbk1mxj9tt` FOREIGN KEY (`create_by_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKmrw0jfof514hoh2fh6u3fw5me` FOREIGN KEY (`departments_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_program`
--

/*!40000 ALTER TABLE `training_program` DISABLE KEYS */;
INSERT INTO `training_program` VALUES (1,'2024-01-20 08:00:00.000000','Đào tạo quy trình tuyển dụng và phỏng vấn hiệu quả',0x01,'Handbook HR, Video training, Case studies','Kinh nghiệm HR cơ bản','HR Best Practices',1,1,2),(2,'2024-01-25 08:00:00.000000','Khóa học Java Spring Boot từ cơ bản đến nâng cao',0x00,'Documentation, Source code examples, Online platform','Kiến thức Java cơ bản','Java Spring Boot Advanced',4,2,5),(3,'2024-02-01 08:00:00.000000','Đào tạo Digital Marketing và SEO',0x00,'Google Analytics, SEO tools, Case studies','Kiến thức marketing cơ bản','Digital Marketing Mastery',9,3,10),(4,'2024-02-05 08:00:00.000000','Khóa học phân tích tài chính và Excel nâng cao',0x01,'Excel templates, Financial models, Practice datasets','Kiến thức Excel cơ bản','Financial Analysis Pro',12,4,14),(5,'2024-02-10 08:00:00.000000','Đào tạo kỹ năng bán hàng và chăm sóc khách hàng',0x01,'Sales scripts, CRM training, Role-play scenarios','Kinh nghiệm bán hàng cơ bản','Sales Excellence',17,6,18),(6,'2024-02-15 08:00:00.000000','Automation Testing với Selenium',0x00,'Selenium documentation, Practice projects, Test frameworks','Kiến thức testing manual','Test Automation',4,2,8),(7,'2024-02-20 08:00:00.000000','Leadership và quản lý nhóm',0x00,'Leadership books, Management tools, Case studies','Kinh nghiệm làm việc 3+ năm','Leadership Development',1,1,1),(8,'2024-02-25 08:00:00.000000','DevOps và CI/CD Pipeline',0x00,'Docker, Kubernetes docs, CI/CD tools','Kiến thức Linux và networking','DevOps Fundamentals',4,2,7),(9,'2024-03-01 08:00:00.000000','Content Marketing và Social Media',0x00,'Content calendar templates, Design tools, Analytics','Kỹ năng viết cơ bản','Content Creation Pro',9,3,11);
/*!40000 ALTER TABLE `training_program` ENABLE KEYS */;

--
-- Table structure for table `training_request`
--

DROP TABLE IF EXISTS `training_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_request` (
  `id` int NOT NULL,
  `approved_date` datetime(6) DEFAULT NULL,
  `expected_outcome` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `request_date` datetime(6) DEFAULT NULL,
  `status` enum('APPROVED','FULFILLED','PENDING','REJECTED') DEFAULT NULL,
  `approved_by_id` int DEFAULT NULL,
  `requested_by_id` int DEFAULT NULL,
  `requested_program_id` int DEFAULT NULL,
  `target_employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc3i0b6f5b1vgylc6ojt6ski7k` (`approved_by_id`),
  KEY `FKnhafo00crhmu8hodf2g8wxf7x` (`requested_by_id`),
  KEY `FK7db0klick3h31ehx6va29xxsk` (`requested_program_id`),
  KEY `FKmm2y9y0ns6ecvs6yvjpyqbj06` (`target_employee_id`),
  CONSTRAINT `FK7db0klick3h31ehx6va29xxsk` FOREIGN KEY (`requested_program_id`) REFERENCES `training_program` (`id`),
  CONSTRAINT `FKc3i0b6f5b1vgylc6ojt6ski7k` FOREIGN KEY (`approved_by_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKmm2y9y0ns6ecvs6yvjpyqbj06` FOREIGN KEY (`target_employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKnhafo00crhmu8hodf2g8wxf7x` FOREIGN KEY (`requested_by_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_request`
--

/*!40000 ALTER TABLE `training_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_request` ENABLE KEYS */;

--
-- Table structure for table `training_session`
--

DROP TABLE IF EXISTS `training_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_session` (
  `id` int NOT NULL,
  `cost` double DEFAULT NULL,
  `current_participants` int DEFAULT NULL,
  `duration_hours` int DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `max_participants` int DEFAULT NULL,
  `session_name` varchar(255) DEFAULT NULL,
  `coordinator` int DEFAULT NULL,
  `training_program_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiruuxafcpltdd3jkqu8q9d3te` (`coordinator`),
  KEY `FKoi8feicaq9hhnyin7bnfi3fm3` (`training_program_id`),
  CONSTRAINT `FKiruuxafcpltdd3jkqu8q9d3te` FOREIGN KEY (`coordinator`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKoi8feicaq9hhnyin7bnfi3fm3` FOREIGN KEY (`training_program_id`) REFERENCES `training_program` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_session`
--

/*!40000 ALTER TABLE `training_session` DISABLE KEYS */;
INSERT INTO `training_session` VALUES (333529296,0,0,24,'string',3,'Cơ bản về Spring boot',1,2);
/*!40000 ALTER TABLE `training_session` ENABLE KEYS */;

--
-- Dumping routines for database 'hrm'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-24 21:02:42
