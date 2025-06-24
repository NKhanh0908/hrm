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
  `role` enum('ADMIN','EMPLOYEE','HR','MANAGER','SUPERVISOR') NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `employees_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtl8b0eq1pfw9nm5omaywo7mai` (`employees_id`),
  CONSTRAINT `FKswklbxvh98gh6kfd4pikn2ll6` FOREIGN KEY (`employees_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

/*!40000 ALTER TABLE `account` DISABLE KEYS */;
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
  `status` enum('APPROVED','PAID','PENDING','REJECTED') NOT NULL,
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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int NOT NULL,
  `attendance_date` datetime(6) DEFAULT NULL,
  `check_in` datetime(6) DEFAULT NULL,
  `check_out` datetime(6) DEFAULT NULL,
  `other_time` float DEFAULT NULL,
  `regular_time` float DEFAULT NULL,
  `shift_type` enum('FLEX','HOLIDAY','NIGHT','OVERTIME','REGULAR','REMOTE','WEEKEND') NOT NULL,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb48lmkou5j4rvde9sr88bqgjw` (`employee_id`),
  CONSTRAINT `FKb48lmkou5j4rvde9sr88bqgjw` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `day_off` ENABLE KEYS */;

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
  `feedback_provider_id` int DEFAULT NULL,
  `performance_review_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbko1jaxococdravb55gpwa7a7` (`feedback_provider_id`),
  KEY `FKptn0iq653w5uubays743eqvqh` (`performance_review_id`),
  CONSTRAINT `FKbko1jaxococdravb55gpwa7a7` FOREIGN KEY (`feedback_provider_id`) REFERENCES `employees` (`id`),
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
  `status` enum('CLOSED','OPEN') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_periods`
--

/*!40000 ALTER TABLE `pay_periods` DISABLE KEYS */;
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
  `type` enum('DEDUCTION','SUDSIDY') NOT NULL,
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
  `status` enum('APPROVED','PAID','PENDING','REJECTED') NOT NULL,
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
  `review_date` datetime(6) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `performance_review_id` int NOT NULL,
  `reviewer_id` int NOT NULL,
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
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

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
  `target_role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjy7ae1vp4bjr5k3bbk1mxj9tt` (`create_by_id`),
  KEY `FK1v25jq3ccfr8m6gqwurthou5h` (`target_role_id`),
  CONSTRAINT `FK1v25jq3ccfr8m6gqwurthou5h` FOREIGN KEY (`target_role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKjy7ae1vp4bjr5k3bbk1mxj9tt` FOREIGN KEY (`create_by_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_program`
--

/*!40000 ALTER TABLE `training_program` DISABLE KEYS */;
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

-- Dump completed on 2025-06-24 21:04:27
