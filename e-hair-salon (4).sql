-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 15, 2024 at 07:16 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e-hair-salon`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbladmin`
--

CREATE TABLE `tbladmin` (
  `ID` int(10) NOT NULL,
  `AdminName` char(50) DEFAULT NULL,
  `UserName` char(50) DEFAULT NULL,
  `MobileNumber` bigint(10) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  `Password` varchar(200) DEFAULT NULL,
  `AdminRegdate` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbladmin`
--

INSERT INTO `tbladmin` (`ID`, `AdminName`, `UserName`, `MobileNumber`, `Email`, `Password`, `AdminRegdate`) VALUES
(1, 'Admin', 'admin', 1234567890, 'tester1@gmail.com', '123', '2019-07-25 06:21:50');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointment`
--

CREATE TABLE `tbl_appointment` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `appointment_book_date` date NOT NULL,
  `appointment_time` varchar(10) NOT NULL,
  `total_amount` varchar(5) NOT NULL,
  `owner_txt` text DEFAULT NULL,
  `appointment_status` varchar(20) DEFAULT NULL,
  `appointment_date` datetime NOT NULL,
  `flag` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_appointment`
--

INSERT INTO `tbl_appointment` (`id`, `user_id`, `appointment_book_date`, `appointment_time`, `total_amount`, `owner_txt`, `appointment_status`, `appointment_date`, `flag`) VALUES
(1, 1, '2024-03-04', '⏱ 1:00 PM', '200', NULL, 'Selected', '2024-03-02 04:19:32', 1),
(2, 1, '2024-03-05', '⏱ 1:30 PM', '420', NULL, 'Rejected', '2024-03-02 04:53:37', 1),
(4, 1, '2024-03-05', '⏱ 4:00 PM', '330', NULL, 'Selected', '2024-03-04 04:31:11', 1),
(5, 1, '2024-03-12', '⏱ 1:00 PM', '60', NULL, 'Selected', '2024-03-11 04:18:27', 1),
(7, 1, '2024-03-13', '⏱ 10:30 AM', '60', NULL, 'Selected', '2024-03-11 04:23:55', 1),
(8, 1, '2024-03-16', '⏱ 12:30 PM', '300', NULL, 'Selected', '2024-03-14 09:50:59', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointment_detail`
--

CREATE TABLE `tbl_appointment_detail` (
  `id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `flag` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_appointment_detail`
--

INSERT INTO `tbl_appointment_detail` (`id`, `appointment_id`, `service_id`, `flag`) VALUES
(1, 1, 10, 1),
(2, 2, 11, 1),
(3, 2, 10, 1),
(5, 4, 11, 1),
(6, 4, 8, 1),
(7, 5, 1, 1),
(9, 7, 1, 1),
(10, 8, 14, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_service`
--

CREATE TABLE `tbl_service` (
  `id` int(11) NOT NULL,
  `service_name` text NOT NULL,
  `service_time` varchar(3) NOT NULL,
  `service_price` varchar(4) NOT NULL,
  `service_discount` varchar(4) NOT NULL,
  `package_type` int(1) NOT NULL DEFAULT 0,
  `img_url` text DEFAULT '',
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `flag` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_service`
--

INSERT INTO `tbl_service` (`id`, `service_name`, `service_time`, `service_price`, `service_discount`, `package_type`, `img_url`, `created_date`, `flag`) VALUES
(1, 'Hair cut', '20', '60', '0', 0, 'service_img/logo2.png', '2024-02-25 17:09:50', 1),
(2, 'Hair Color', '25', '150', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:11:27', 1),
(3, 'Hair Wash', '20', '80', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:15:31', 1),
(4, 'Shaving', '15', '80', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:16:08', 1),
(5, 'Beard Trim', '15', '80', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:16:42', 1),
(6, 'Beard Styling', '20', '100', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:17:31', 1),
(7, 'Beard Color', '25', '120', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:18:18', 1),
(8, 'Head Shave', '30', '110', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:23:00', 1),
(9, 'Clean Up', '35', '200', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:23:37', 1),
(10, 'Bleach', '40', '200', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:25:31', 1),
(11, 'Scrub', '40', '220', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:26:04', 1),
(12, 'Facial', '45', '250', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:27:45', 1),
(13, 'Child Hair Cut', '15', '50', '0', 0, 'service_img/hair_cut.png', '2024-02-26 16:30:02', 1),
(14, 'Combo Pack (Hair cut, Hair Color, Bread Trim)', '60', '300', '0', 1, 'service_img/hair_cut.png', '2024-03-11 10:35:03', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `mobile_no` varchar(15) NOT NULL,
  `email_id` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `created_date` datetime NOT NULL,
  `flag` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `name`, `mobile_no`, `email_id`, `username`, `password`, `created_date`, `flag`) VALUES
(1, 'Rocky', '9978133917', 'admin@gmail.com', 'viru', '123', '2024-02-25 15:47:04', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbladmin`
--
ALTER TABLE `tbladmin`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_appointment`
--
ALTER TABLE `tbl_appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `tbl_appointment_detail`
--
ALTER TABLE `tbl_appointment_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `appointment_id` (`appointment_id`),
  ADD KEY `service_id` (`service_id`);

--
-- Indexes for table `tbl_service`
--
ALTER TABLE `tbl_service`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbladmin`
--
ALTER TABLE `tbladmin`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_appointment`
--
ALTER TABLE `tbl_appointment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tbl_appointment_detail`
--
ALTER TABLE `tbl_appointment_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tbl_service`
--
ALTER TABLE `tbl_service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_appointment`
--
ALTER TABLE `tbl_appointment`
  ADD CONSTRAINT `tbl_appointment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`);

--
-- Constraints for table `tbl_appointment_detail`
--
ALTER TABLE `tbl_appointment_detail`
  ADD CONSTRAINT `tbl_appointment_detail_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `tbl_appointment` (`id`),
  ADD CONSTRAINT `tbl_appointment_detail_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `tbl_service` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
