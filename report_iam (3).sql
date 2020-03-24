-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 24 Mar 2020 pada 11.51
-- Versi Server: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `report_iam`
--

DELIMITER $$
--
-- Prosedur
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `countDisable` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT COUNT(*) AS count, last_update FROM `user_updates` where is_update=1 and enable=0 and upd_success=1 and last_update between begin_date AND end_date GROUP by last_update;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countOnboard` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT COUNT(*) AS count, last_update FROM `user_updates` where is_onboard=1 and last_update between begin_date AND end_date GROUP by last_update;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `countUpdate` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT COUNT(*) AS count, last_update FROM `user_updates` where is_update=1 and last_update between begin_date AND end_date GROUP by last_update;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllUser` ()  BEGIN
    SELECT *  FROM user;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDisableByDate` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT * FROM `user_updates` WHERE is_update=1 and last_update BETWEEN begin_date and end_date and enable=false;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSummaryReport` (IN `begin_date` DATE, IN `end_date` DATE)  SELECT
    COUNT(CASE WHEN `is_update` = 1 and last_update between begin_date and end_date THEN 1 END) AS countUpdate,
    COUNT(CASE WHEN `is_onboard` = 1 and last_update between begin_date and end_date THEN 1 END) AS countOnboard,
    COUNT(CASE WHEN `enable` = 0 and `is_update`=1 and `upd_success`=1 and last_update between begin_date and end_date THEN 1 END) AS countDisabled,
    COUNT(CASE WHEN `is_update` = 1 and `upd_success`=1 and `updated_attr`LIKE '%mobile_phone%' and last_update between begin_date and end_date THEN 1 END) AS phone,
    COUNT(CASE WHEN `is_update` = 1 and `upd_success`=1 and `updated_attr`LIKE '%alternate_email%' and last_update between begin_date and end_date THEN 1 END) AS email,
    COUNT(CASE WHEN `is_update` = 1 and `upd_success`=1 and `updated_attr` LIKE '%full_name%' and last_update between begin_date and end_date THEN 1 END) AS name
FROM `user_updates`
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSumOnboard` (IN `last_date` DATE)  BEGIN
SELECT COUNT(*) FROM `user_updates` where is_onboard=1 and last_update=last_date;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSumUpdate` (IN `last_date` DATE)  BEGIN
SELECT COUNT(*) FROM `user_updates` WHERE is_update=1 and last_update=last_date;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUpdateByDate` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT * FROM `user_updates` WHERE is_update=1 and last_update BETWEEN begin_date and end_date;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUpdateById` (IN `user_id` VARCHAR(255), IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT * FROM user_updates WHERE (user_id = user_id) AND (is_update=1) AND (last_update BETWEEN begin_date and end_date);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUpdByStatus` (IN `begin_date` DATE, IN `end_date` DATE, IN `status` INT)  BEGIN
SELECT * FROM user_updates WHERE is_update=1 and (last_update BETWEEN begin_date and end_date) and upd_success=status;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserDisabled` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT * FROM `user_updates` where is_update=1 and enable=0 and upd_success=1 and last_update between begin_date AND end_date GROUP by last_update;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserOnboard` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT * FROM `user_updates` WHERE is_onboard=1 and last_update BETWEEN begin_date and end_date;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserUpdates` (IN `begin_date` DATE, IN `end_date` DATE)  BEGIN
SELECT * FROM `user_updates` WHERE is_update=1 and last_update BETWEEN begin_date and end_date;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUser` (IN `full_name` VARCHAR(255), `user_id` VARCHAR(255), `login_id` VARCHAR(255), `first_name` VARCHAR(100), IN `middle_name` VARCHAR(100), IN `last_name` VARCHAR(100), IN `activation_date` DATE, IN `STRING_05` VARCHAR(100), IN `expiration_date` DATE, IN `employee_status` VARCHAR(50), IN `manager` VARCHAR(50), IN `manager_employee_number` VARCHAR(50), IN `INTEGER_04` VARCHAR(50), IN `alternate_email` VARCHAR(100), IN `mobile_phone` VARCHAR(30), IN `STRING_00` VARCHAR(50), IN `STRING_01` VARCHAR(50), IN `department` VARCHAR(50), IN `STRING_02` VARCHAR(50), IN `CASE_EXACT_STRING04` VARCHAR(50), IN `cost_center` VARCHAR(100), IN `CASE_EXACT_STRING03` VARCHAR(50), IN `STRING_03` VARCHAR(50), IN `CASE_EXACT_STRING02` VARCHAR(50), IN `STRING_04` VARCHAR(50), IN `CASE_EXACT_STRING01` VARCHAR(50), IN `STRING_08` VARCHAR(50), IN `CASE_EXACT_STRING00` VARCHAR(50), IN `STRING_09` VARCHAR(50), IN `STRING_06` VARCHAR(50), IN `STRING_07` VARCHAR(50), IN `employee_type` VARCHAR(50), IN `enable` TINYINT(1), IN `last_update` DATE)  BEGIN
 INSERT INTO user_iam VALUES(full_name ,user_id ,login_id ,first_name ,middle_name ,last_name ,activation_date,STRING_05,expiration_date,employee_status ,manager ,manager_employee_number ,INTEGER_04 ,alternate_email ,mobile_phone,STRING_00,STRING_01 ,department ,STRING_02 ,CASE_EXACT_STRING04 ,cost_center ,CASE_EXACT_STRING03 ,STRING_03 ,CASE_EXACT_STRING02 ,STRING_04 ,CASE_EXACT_STRING01 ,STRING_08 ,CASE_EXACT_STRING00,STRING_09,STRING_06,STRING_07,employee_type,enable,last_update);
 END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUserFormat` (IN `full_name` VARCHAR(255), IN `user_id` VARCHAR(255), IN `login_id` VARCHAR(255), IN `first_name` VARCHAR(100), IN `middle_name` VARCHAR(100), IN `last_name` VARCHAR(100), IN `activation_date` DATE, IN `STRING_05` VARCHAR(100), IN `expiration_date` DATE, IN `employee_status` VARCHAR(50), IN `manager` VARCHAR(50), IN `manager_employee_number` VARCHAR(50), IN `INTEGER_04` VARCHAR(50), IN `alternate_email` VARCHAR(100), IN `mobile_phone` VARCHAR(30), IN `STRING_00` VARCHAR(50), IN `STRING_01` VARCHAR(50), IN `department` VARCHAR(50), IN `STRING_02` VARCHAR(50), IN `CASE_EXACT_STRING04` VARCHAR(50), IN `cost_center` VARCHAR(100), IN `CASE_EXACT_STRING03` VARCHAR(50), IN `STRING_03` VARCHAR(50), IN `CASE_EXACT_STRING02` VARCHAR(50), IN `STRING_04` VARCHAR(50), IN `CASE_EXACT_STRING01` VARCHAR(50), IN `STRING_08` VARCHAR(50), IN `CASE_EXACT_STRING00` VARCHAR(50), IN `STRING_09` VARCHAR(50), IN `STRING_06` VARCHAR(50), IN `STRING_07` VARCHAR(50), IN `employee_type` VARCHAR(50), IN `enable` TINYINT(1), IN `last_update` DATE)  BEGIN
 INSERT INTO user_iam VALUES(full_name ,user_id ,login_id ,first_name ,middle_name ,last_name ,cast(activation_date as date),STRING_05,cast(expiration_date as date),employee_status ,manager ,manager_employee_number ,INTEGER_04 ,alternate_email ,mobile_phone,STRING_00,STRING_01 ,department ,STRING_02 ,CASE_EXACT_STRING04 ,cost_center ,CASE_EXACT_STRING03 ,STRING_03 ,CASE_EXACT_STRING02 ,STRING_04 ,CASE_EXACT_STRING01 ,STRING_08 ,CASE_EXACT_STRING00,STRING_09,STRING_06,STRING_07,employee_type,enable,cast(last_update as date));
 END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `full_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `login_id` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `activation_date` date DEFAULT NULL,
  `STRING_05` varchar(100) DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `employee_status` varchar(50) DEFAULT NULL,
  `manager` varchar(50) DEFAULT NULL,
  `manager_employee_number` varchar(50) DEFAULT NULL,
  `INTEGER_04` varchar(50) DEFAULT NULL,
  `alternate_email` varchar(100) DEFAULT NULL,
  `mobile_phone` varchar(30) DEFAULT NULL,
  `STRING_01` varchar(50) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `STRING_02` varchar(50) DEFAULT NULL,
  `CASE_EXACT_STRING04` varchar(50) DEFAULT NULL,
  `cost_center` varchar(100) DEFAULT NULL,
  `CASE_EXACT_STRING03` varchar(50) DEFAULT NULL,
  `STRING_03` varchar(50) DEFAULT NULL,
  `CASE_EXACT_STRING02` varchar(50) DEFAULT NULL,
  `STRING_04` varchar(50) DEFAULT NULL,
  `CASE_EXACT_STRING01` varchar(50) DEFAULT NULL,
  `STRING_08` varchar(50) DEFAULT NULL,
  `CASE_EXACT_STRING00` varchar(50) DEFAULT NULL,
  `STRING_09` varchar(50) DEFAULT NULL,
  `STRING_06` varchar(50) DEFAULT NULL,
  `STRING_07` varchar(50) DEFAULT NULL,
  `employee_type` varchar(50) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `last_update` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`full_name`, `user_id`, `login_id`, `first_name`, `middle_name`, `last_name`, `activation_date`, `STRING_05`, `expiration_date`, `employee_status`, `manager`, `manager_employee_number`, `INTEGER_04`, `alternate_email`, `mobile_phone`, `STRING_01`, `department`, `STRING_02`, `CASE_EXACT_STRING04`, `cost_center`, `CASE_EXACT_STRING03`, `STRING_03`, `CASE_EXACT_STRING02`, `STRING_04`, `CASE_EXACT_STRING01`, `STRING_08`, `CASE_EXACT_STRING00`, `STRING_09`, `STRING_06`, `STRING_07`, `employee_type`, `enable`, `last_update`) VALUES
('WIWIN HASDIANTI', '1085381358', '1085381358', 'WIWIN', '', 'HASDIANTI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(NULL, NULL, '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_iam`
--

CREATE TABLE `user_iam` (
  `full_name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `login_id` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `activation_date` date NOT NULL,
  `STRING_05` varchar(100) NOT NULL,
  `expiration_date` date NOT NULL,
  `employee_status` varchar(50) NOT NULL,
  `manager` varchar(50) NOT NULL,
  `manager_employee_number` varchar(50) NOT NULL,
  `INTEGER_04` varchar(50) NOT NULL,
  `alternate_email` varchar(100) NOT NULL,
  `mobile_phone` varchar(30) NOT NULL,
  `STRING_00` varchar(50) NOT NULL,
  `STRING_01` varchar(50) NOT NULL,
  `department` varchar(50) NOT NULL,
  `STRING_02` varchar(50) NOT NULL,
  `CASE_EXACT_STRING04` varchar(50) NOT NULL,
  `cost_center` varchar(100) NOT NULL,
  `CASE_EXACT_STRING03` varchar(50) NOT NULL,
  `STRING_03` varchar(50) NOT NULL,
  `CASE_EXACT_STRING02` varchar(50) NOT NULL,
  `STRING_04` varchar(50) NOT NULL,
  `CASE_EXACT_STRING01` varchar(50) NOT NULL,
  `STRING_08` varchar(50) NOT NULL,
  `CASE_EXACT_STRING00` varchar(50) NOT NULL,
  `STRING_09` varchar(50) NOT NULL,
  `STRING_06` varchar(50) NOT NULL,
  `STRING_07` varchar(50) NOT NULL,
  `employee_type` varchar(50) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  `last_update` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user_iam`
--

INSERT INTO `user_iam` (`full_name`, `user_id`, `login_id`, `first_name`, `middle_name`, `last_name`, `activation_date`, `STRING_05`, `expiration_date`, `employee_status`, `manager`, `manager_employee_number`, `INTEGER_04`, `alternate_email`, `mobile_phone`, `STRING_00`, `STRING_01`, `department`, `STRING_02`, `CASE_EXACT_STRING04`, `cost_center`, `CASE_EXACT_STRING03`, `STRING_03`, `CASE_EXACT_STRING02`, `STRING_04`, `CASE_EXACT_STRING01`, `STRING_08`, `CASE_EXACT_STRING00`, `STRING_09`, `STRING_06`, `STRING_07`, `employee_type`, `enable`, `last_update`) VALUES
('WIWIN HASDIANTI', '1085381358', '1085381358', 'WIWIN', '', 'HASDIANTI', '2010-06-18', '2018-02-01', '0000-00-00', 'Active', '9973070287', '1085381358', '', 'WIWINHASDIANTI@GMAIL.COM', '08128470542', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA GREEN VILL', '16500N', 'KC Jakarta Green Ville', '50451627', 'BOM', '50457634', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'wiwin.hasdianti', 0, '2020-03-18'),
('RINA SUHARTINI', '1187408513', '1187408513', 'RINA', '', 'SUHARTINI', '2011-07-11', '2018-03-15', '0000-00-00', 'Active', '0582268389', '1187408513', '', 'RINA.SUHARTINI87@GMAIL.COM', '085312376894', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'CONSUMER LOAN', '16500V', 'Cons.Loan  Jkt Green Ville', '50510359', 'CONSUMER LOAN', '50484635', 'CUST RLTNSHIP MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'rina.suhartini', 0, '0000-00-00'),
('FITRI PUSPITASARI', '0682290572', '0682290572', 'FITRI', '', 'PUSPITASARI', '2006-10-02', '2017-01-09', '0000-00-00', 'Active', '9972070387', '0682290572', '', 'PUSPITASARIFITRI1176@GMAIL.COM', '0817110782', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BUSS SUPP', '16500W', 'Area Operations Jakarta Green Ville', '50450226', 'BUSS SUPP', '50458362', 'SQ OFF', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '98', 'First Senior Manager', 'fitri.puspitasari', 0, '2020-03-18'),
('ASRI RAHAYU', '1087372926', '1087372926', 'ASRI', '', 'RAHAYU', '2010-03-24', '2019-01-12', '0000-00-00', 'Active', '9972068688', '1087372926', '', 'ASRI_RAHAYU87@YAHOO.COM', '085779899908', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA SLIPI JAYA', '16503N', 'KCP Jakarta Slipi Jaya', '50453292', 'BOM', '50470995', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8412', 'Jk Slipi Jaya', '17', 'Manager', 'asri.rahayu', 0, '2020-03-18'),
('VIVI GUSRY', '0883316861', '0883316861', 'VIVI', '', 'GUSRY', '2008-04-24', '2019-12-15', '0000-00-00', 'Active', '0279184576', '0883316861', '', 'VIVI.GUSRY@GMAIL.COM', '082122582434', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA MAL TAMAN', '16512N', 'KCP Jakarta Mal Taman Anggrek', '50453289', 'BOM', '50470865', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8409', 'Jk Mal TA', '17', 'Manager', 'vivi.gusry', 0, '2020-03-28'),
('AMELIA ANDREMICA', '1188393433', '1188393433', 'AMELIA', '', 'ANDREMICA', '2011-01-13', '2015-12-28', '0000-00-00', 'Active', '9966027072', '1188393433', '', 'AMELIA.ANDREMICA@GMAIL.COM', '081382621989', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA KEMANGGISA', '16504N', 'KCP Jakarta Kemanggisan', '50453288', 'BOM', '50470864', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8413', 'Jk Kemanggisan', '17', 'Manager', 'amelia.andremica', 0, '2020-03-28'),
('DESSY TRIANA LESTARI', '1085381184', '1085381184', 'DESSY', 'TRIANA', 'LESTARI', '2010-06-04', '2018-01-02', '0000-00-00', 'Active', '9975051558', '1085381184', '', 'DESSYTL85@GMAIL.COM', '082110344166', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA RAWABELONG', '16505N', 'KCP Jakarta Rawa Belong', '50453291', 'BOM', '50521452', 'CUST SERVICE OFF', '1003', 'Wilayah III / Jakarta Kota', '8414', 'Jk Rw Belong', '17', 'Manager', 'dessy.lestari', 0, '2020-03-28'),
('LIES HENDRAWAN KRISNAWATI', '0478261571', '0478261571', 'LIES', 'HENDRAWAN', 'KRISNAWATI', '2004-07-24', '2017-03-01', '0000-00-00', 'Active', '9972105829', '0478261571', '', 'empty@empty.empty', '081219623486', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA TAMAN KEBO', '16506N', 'KCP Jakarta Taman Kebun Jeruk', '50453293', 'BOM', '50470996', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8419', 'Jk Tm Kbn Jeruk', '98', 'First Senior Manager', 'lies.krisnawati', 0, '2020-03-18'),
('AGUSTIRA RAMADHANA', '0279182844', '0279182844', 'AGUSTIRA', '', 'RAMADHANA', '2002-04-16', '2019-01-03', '0000-00-00', 'Active', '9970076436', '0279182844', '', 'TIRA_RAMADHANA@YAHOO.COM', '0819870002', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA KEBON JERU', '16507N', 'KCP Jakarta Kebun Jeruk Perjuangan', '50453283', 'BOM', '50490183', 'CUST SERVICE OFF', '1003', 'Wilayah III / Jakarta Kota', '8416', 'Jk Kbn Jeruk P', '16', 'Assistant Manager', 'agustira.ramadhana', 0, '2020-03-18'),
('JASMIN ANGGITHIA BASRI', '0887328896', '0887328896', 'JASMIN', 'ANGGITHIA', 'BASRI', '2008-10-11', '2015-02-01', '0000-00-00', 'Active', '9968101774', '0887328896', '', 'JASMINANGGITHIA@YAHOO.COM', '081212228467', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA PLUIT SELATA', 'BRANCH JAKARTA MITRA BAHA', '16802N', 'KCP Jakarta Mitra Bahari', '50453398', 'BOM', '50471130', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8303', 'Jk Mitra Bahari', '17', 'Manager', 'jasmin.basri', 0, '2020-03-18');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_updates`
--

CREATE TABLE `user_updates` (
  `full_name` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `login_id` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `activation_date` date NOT NULL,
  `STRING_05` varchar(100) NOT NULL,
  `expiration_date` date NOT NULL,
  `employee_status` varchar(50) NOT NULL,
  `manager` varchar(50) NOT NULL,
  `manager_employee_number` varchar(50) NOT NULL,
  `INTEGER_04` varchar(50) NOT NULL,
  `alternate_email` varchar(100) NOT NULL,
  `mobile_phone` varchar(30) NOT NULL,
  `STRING_00` varchar(50) NOT NULL,
  `STRING_01` varchar(50) NOT NULL,
  `department` varchar(50) NOT NULL,
  `STRING_02` varchar(50) NOT NULL,
  `CASE_EXACT_STRING04` varchar(50) NOT NULL,
  `cost_center` varchar(100) NOT NULL,
  `CASE_EXACT_STRING03` varchar(50) NOT NULL,
  `STRING_03` varchar(50) NOT NULL,
  `CASE_EXACT_STRING02` varchar(50) NOT NULL,
  `STRING_04` varchar(50) NOT NULL,
  `CASE_EXACT_STRING01` varchar(50) NOT NULL,
  `STRING_08` varchar(50) NOT NULL,
  `CASE_EXACT_STRING00` varchar(50) NOT NULL,
  `STRING_09` varchar(50) NOT NULL,
  `STRING_06` varchar(50) NOT NULL,
  `STRING_07` varchar(50) NOT NULL,
  `employee_type` varchar(50) NOT NULL,
  `enable` tinyint(1) NOT NULL,
  `last_update` date NOT NULL,
  `is_onboard` int(1) DEFAULT NULL,
  `is_update` int(1) DEFAULT NULL,
  `updated_attr` varchar(30) DEFAULT NULL,
  `upd_success` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user_updates`
--

INSERT INTO `user_updates` (`full_name`, `user_id`, `login_id`, `first_name`, `middle_name`, `last_name`, `activation_date`, `STRING_05`, `expiration_date`, `employee_status`, `manager`, `manager_employee_number`, `INTEGER_04`, `alternate_email`, `mobile_phone`, `STRING_00`, `STRING_01`, `department`, `STRING_02`, `CASE_EXACT_STRING04`, `cost_center`, `CASE_EXACT_STRING03`, `STRING_03`, `CASE_EXACT_STRING02`, `STRING_04`, `CASE_EXACT_STRING01`, `STRING_08`, `CASE_EXACT_STRING00`, `STRING_09`, `STRING_06`, `STRING_07`, `employee_type`, `enable`, `last_update`, `is_onboard`, `is_update`, `updated_attr`, `upd_success`) VALUES
('WIWIN HASDIANTI', '1085381358', '1085381358', 'WIWIN', '', 'HASDIANTI', '2010-06-18', '2018-02-01', '0000-00-00', 'Active', '9973070287', '1085381358', '', 'WIWINHASDIANTI@GMAIL.COM', '08128470542', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA GREEN VILL', '16500N', 'KC Jakarta Green Ville', '50451627', 'BOM', '50457634', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'wiwin.hasdianti', 1, '2020-03-18', 1, 0, '-', 1),
('RINA SUHARTINI', '1187408513', '1187408513', 'RINA', '', 'SUHARTINI', '2011-07-11', '2018-03-15', '0000-00-00', 'Active', '0582268389', '1187408513', '', 'RINA.SUHARTINI87@GMAIL.COM', '085312376894', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'CONSUMER LOAN', '16500V', 'Cons.Loan  Jkt Green Ville', '50510359', 'CONSUMER LOAN', '50484635', 'CUST RLTNSHIP MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'rina.suhartini', 1, '2020-03-18', 1, 0, '-', 1),
('FITRI PUSPITASARI', '0682290572', '0682290572', 'FITRI', '', 'PUSPITASARI', '2006-10-02', '2017-01-09', '0000-00-00', 'Active', '9972070387', '0682290572', '', 'PUSPITASARIFITRI1176@GMAIL.COM', '0817110782', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BUSS SUPP', '16500W', 'Area Operations Jakarta Green Ville', '50450226', 'BUSS SUPP', '50458362', 'SQ OFF', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '98', 'First Senior Manager', 'fitri.puspitasari', 1, '2020-03-18', 1, 0, '-', 1),
('ASRI RAHAYU', '1087372926', '1087372926', 'ASRI', '', 'RAHAYU', '2010-03-24', '2019-01-12', '0000-00-00', 'Active', '9972068688', '1087372926', '', 'ASRI_RAHAYU87@YAHOO.COM', '085779899908', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA SLIPI JAYA', '16503N', 'KCP Jakarta Slipi Jaya', '50453292', 'BOM', '50470995', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8412', 'Jk Slipi Jaya', '17', 'Manager', 'asri.rahayu', 1, '2020-03-18', 1, 0, '-', 1),
('VIVI GUSRY', '0883316861', '0883316861', 'VIVI', '', 'GUSRY', '2008-04-24', '2019-12-15', '0000-00-00', 'Active', '0279184576', '0883316861', '', 'VIVI.GUSRY@GMAIL.COM', '082122582434', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA MAL TAMAN', '16512N', 'KCP Jakarta Mal Taman Anggrek', '50453289', 'BOM', '50470865', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8409', 'Jk Mal TA', '17', 'Manager', 'vivi.gusry', 1, '2020-03-18', 1, 0, '-', 1),
('AMELIA ANDREMICA', '1188393433', '1188393433', 'AMELIA', '', 'ANDREMICA', '2011-01-13', '2015-12-28', '0000-00-00', 'Active', '9966027072', '1188393433', '', 'AMELIA.ANDREMICA@GMAIL.COM', '081382621989', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA KEMANGGISA', '16504N', 'KCP Jakarta Kemanggisan', '50453288', 'BOM', '50470864', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8413', 'Jk Kemanggisan', '17', 'Manager', 'amelia.andremica', 1, '2020-03-18', 1, 0, '-', 1),
('DESSY TRIANA LESTARI', '1085381184', '1085381184', 'DESSY', 'TRIANA', 'LESTARI', '2010-06-04', '2018-01-02', '0000-00-00', 'Active', '9975051558', '1085381184', '', 'DESSYTL85@GMAIL.COM', '082110344166', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA RAWABELONG', '16505N', 'KCP Jakarta Rawa Belong', '50453291', 'BOM', '50521452', 'CUST SERVICE OFF', '1003', 'Wilayah III / Jakarta Kota', '8414', 'Jk Rw Belong', '17', 'Manager', 'dessy.lestari', 1, '2020-03-18', 1, 0, '-', 1),
('LIES HENDRAWAN KRISNAWATI', '0478261571', '0478261571', 'LIES', 'HENDRAWAN', 'KRISNAWATI', '2004-07-24', '2017-03-01', '0000-00-00', 'Active', '9972105829', '0478261571', '', 'empty@empty.empty', '081219623486', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA TAMAN KEBO', '16506N', 'KCP Jakarta Taman Kebun Jeruk', '50453293', 'BOM', '50470996', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8419', 'Jk Tm Kbn Jeruk', '98', 'First Senior Manager', 'lies.krisnawati', 1, '2020-03-18', 1, 0, '-', 1),
('AGUSTIRA RAMADHANA', '0279182844', '0279182844', 'AGUSTIRA', '', 'RAMADHANA', '2002-04-16', '2019-01-03', '0000-00-00', 'Active', '9970076436', '0279182844', '', 'TIRA_RAMADHANA@YAHOO.COM', '0819870002', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA KEBON JERU', '16507N', 'KCP Jakarta Kebun Jeruk Perjuangan', '50453283', 'BOM', '50490183', 'CUST SERVICE OFF', '1003', 'Wilayah III / Jakarta Kota', '8416', 'Jk Kbn Jeruk P', '16', 'Assistant Manager', 'agustira.ramadhana', 1, '2020-03-18', 1, 0, '-', 1),
('JASMIN ANGGITHIA BASRI', '0887328896', '0887328896', 'JASMIN', 'ANGGITHIA', 'BASRI', '2008-10-11', '2015-02-01', '0000-00-00', 'Active', '9968101774', '0887328896', '', 'JASMINANGGITHIA@YAHOO.COM', '081212228467', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA PLUIT SELATA', 'BRANCH JAKARTA MITRA BAHA', '16802N', 'KCP Jakarta Mitra Bahari', '50453398', 'BOM', '50471130', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8303', 'Jk Mitra Bahari', '17', 'Manager', 'jasmin.basri', 1, '2020-03-18', 1, 0, '-', 1),
('RINA SUHARTINI', '1187408513', '1187408513', 'RINA', '', 'SUHARTINI', '2011-07-11', '2018-03-15', '0000-00-00', 'Active', '0582268389', '1187408513', '', 'RINA.SUHARTINI87@GMAIL.COM', '0812564152712', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'CONSUMER LOAN', '16500V', 'Cons.Loan  Jkt Green Ville', '50510359', 'CONSUMER LOAN', '50484635', 'CUST RLTNSHIP MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'rina.suhartini', 1, '2020-03-19', 0, 1, 'mobile_phone', 1),
('AMELIA ANDREMICA', '1188393433', '1188393433', 'AMELIA', '', 'ANDREMICA', '2011-01-13', '2015-12-28', '0000-00-00', 'Active', '9966027072', '1188393433', '', 'AMELIA_ANDREMICA123@GMAIL.COM', '081382621989', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA KEMANGGISA', '16504N', 'KCP Jakarta Kemanggisan', '50453288', 'BOM', '50470864', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8413', 'Jk Kemanggisan', '17', 'Manager', 'amelia.andremica', 1, '2020-03-19', 0, 1, 'alternate_email', 1),
('WIWIN HASDIANTI', '1085381358', '1085381358', 'WIWIN', '', 'HASDIANTI', '2010-06-18', '2018-02-01', '0000-00-00', 'Active', '9973070287', '1085381358', '', 'WIWINHASDIANTI@GMAIL.COM', '08128470542', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA GREEN VILL', '16500N', 'KC Jakarta Green Ville', '50451627', 'BOM', '50457634', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'wiwin.hasdianti', 1, '2020-03-19', 0, 1, 'alternate_email', 0),
('FAUZIATI AMALIA', '1189411663', '1189411663', 'FAUZIATI', '', 'AMALIA', '2011-05-18', '2018-01-11', '0000-00-00', 'Active', '9968048215', '1189411663', '', 'FAUZIATIAMALIA@GMAIL.COM', '087881297810', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA PLUIT SELATA', 'AREA TRANSACTION & FUNDIN', '16803P', 'BMP Jakarta Pluit Kencana', '50485156', 'BMP JAKARTA PLUIT KENCANA', '50485158', 'RM PRIORITY BKG', '1003', 'Wilayah III / Jakarta Kota', '8300', 'Area Jk Plt Sel', '16', 'Assistant Manager', 'fauziati.amalia', 1, '2020-03-23', 1, 0, '-', 1),
('WIWIN HASDIANTI', '1085381358', '1085381358', 'WIWIN', '', 'HASDIANTI', '2010-06-18', '2018-02-01', '0000-00-00', 'Active', '9973070287', '1085381358', '', 'WIWINHASDIANTI@GMAIL.COM', '08128470542', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'BRANCH JAKARTA GREEN VILL', '16500N', 'KC Jakarta Green Ville', '50451627', 'BOM', '50457634', 'BRANCH OPS MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'wiwin.hasdianti', 0, '2020-03-23', 0, 1, '-', 1),
('RINA SUHARTINI', '1187408513', '1187408513', 'RINA', '', 'SUHARTINI', '2011-07-11', '2018-03-15', '0000-00-00', 'Active', '0582268389', '1187408513', '', 'RINA.SUHARTINI87@GMAIL.COM', '085312376894', 'BISNIS and JARINGAN', 'REGION III/JAKARTA 1', 'AREA JAKARTA GREEN VILLE', 'CONSUMER LOAN', '16500V', 'Cons.Loan  Jkt Green Ville', '50510359', 'CONSUMER LOAN', '50484635', 'CUST RLTNSHIP MGR', '1003', 'Wilayah III / Jakarta Kota', '8400', 'Jk Greenvil', '17', 'Manager', 'rina.suhartini', 0, '2020-03-24', 0, 1, '-', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
