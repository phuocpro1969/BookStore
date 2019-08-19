-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 19, 2019 at 07:48 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` bigint(20) NOT NULL,
  `authors` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ok` int(11) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `price` bigint(20) NOT NULL,
  `published_year` int(11) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uploaded_date` date DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `publisher_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `authors`, `description`, `domain`, `ok`, `picture`, `price`, `published_year`, `title`, `uploaded_date`, `person_id`, `publisher_id`) VALUES
(1, 'Rick Riordan', 'US Hay', 'US', 0, '1_Ke-cap-tia-chop-Rick-Riordan-195x300.jpg', 100000, 2000, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 1: Kẻ Cắp Tia Chớp', '2019-08-19', 1, 6),
(2, 'Rick Riordan', 'US Hay', 'US', 0, '2_Bien-quai-vat-1-215x300.jpg', 100000, 2000, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 2: Biển Quái Vật', '2019-08-19', 2, 6),
(3, 'Rick Riordan', 'US Hay', 'US', 0, '3_Loi-nguyen-cua-than-titan-Rick-Riordan-196x300.jpg', 100000, 2000, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 3: Lời Nguyền Của Thần Titan', '2019-08-19', 2, 6),
(4, 'Rick Riordan', '', 'US', 0, '4_Ho-so-a-than-Rick-Riordan-214x300.jpg', 100000, 2000, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 4,5: Hồ sơ á thần', '2019-08-19', 2, 6),
(5, '', 'US Hay', 'US', 0, '5_Vi-than-cuoi-cung-Rick-Riordan-200x300.jpg', 100000, 2000, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 5: Vị Thần Cuối Cùng', '2019-08-19', 2, 6),
(6, '', '', 'bắc', 0, '6_choc-tuc-vo-yeu-mua-mot-tang-mot.jpg', 100000, 2000, 'Chọc Tức Vợ Yêu - Mua Một Tặng Một', '2019-08-19', 3, 4),
(7, '', '', 'bắc', 0, '7_toan9.jpg', 100000, 2000, 'Toán lớp 9', '2019-08-19', 3, 5);

-- --------------------------------------------------------

--
-- Table structure for table `book_category`
--

CREATE TABLE `book_category` (
  `book_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book_category`
--

INSERT INTO `book_category` (`book_id`, `category_id`) VALUES
(1, 5),
(2, 5),
(3, 5),
(4, 5),
(5, 5),
(6, 5),
(7, 2),
(7, 10);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `createdate` datetime DEFAULT NULL,
  `createdid` bigint(20) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `uploadid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `createdate`, `createdid`, `name`, `updatedate`, `uploadid`) VALUES
(1, '2019-08-19 11:07:26', 1, 'updatingCategory', '2019-08-19 11:07:26', 1),
(2, '2019-08-19 11:10:36', 1, 'Sách Giáo Khoa', '2019-08-19 11:10:36', 1),
(3, '2019-08-19 11:11:04', 1, 'Truyện Tranh', '2019-08-19 11:11:04', 1),
(4, '2019-08-19 11:11:18', 1, 'Trinh Thám', '2019-08-19 11:11:18', 1),
(5, '2019-08-19 11:11:46', 1, 'Tiểu Thuyết', '2019-08-19 11:11:46', 1),
(6, '2019-08-19 11:11:58', 1, 'Kinh Dị', '2019-08-19 11:11:58', 1),
(7, '2019-08-19 11:12:17', 1, 'Hành Động', '2019-08-19 11:12:17', 1),
(8, '2019-08-19 11:12:32', 1, 'Nấu Ăn', '2019-08-19 11:12:32', 1),
(9, '2019-08-19 11:12:46', 1, 'IT', '2019-08-19 11:12:46', 1),
(10, '2019-08-19 11:12:52', 1, 'Toán', '2019-08-19 11:12:52', 1),
(11, '2019-08-19 11:13:00', 1, 'Vật Lý', '2019-08-19 11:13:00', 1),
(12, '2019-08-19 11:13:14', 1, 'Hóa Học ', '2019-08-19 11:13:14', 1),
(13, '2019-08-19 11:13:43', 1, 'Anh Văn', '2019-08-19 11:13:43', 1),
(14, '2019-08-19 11:13:50', 1, 'Ngữ Văn', '2019-08-19 11:13:50', 1),
(15, '2019-08-19 11:29:05', 1, 'Sinh Học', '2019-08-19 11:29:05', 1);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(4),
(4);

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_token`
--

CREATE TABLE `password_reset_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `personid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `lastname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `power` int(11) NOT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `address`, `birthday`, `create_date`, `email`, `firstname`, `lastname`, `password`, `phone`, `power`, `sex`, `update_date`, `username`) VALUES
(1, 'BP', '1999-01-08', '2019-08-19 11:07:27', 'phuocpro1969@gmail.com', 'admin', 'admin', '$2a$10$u89it37VGOYgk5kYVr0ZfuCDUsnn0NxgHD6H9riI0KrHtxEpjkcxS', '0981415287', 2, 'Male', '2019-08-19 11:07:27', 'admin'),
(2, 'Bình Phước', '1999-01-08', '2019-08-19 11:34:57', '17520918@gm.uit.edu.vn', 'Phan Hoàng ', 'Phước', '$2a$10$kdDg9wKKhjAXz9Dub9ezveGDqy1sgyI/Y.jQY1xp/BGPzt8isjzeW', '0981415287', 2, 'Male', '2019-08-19 11:34:57', 'phuoc'),
(3, 'a', '1945-01-02', '2019-08-19 11:35:13', 'a@gmail.com', 'a', 'a', '$2a$10$dU5pmDHfzhxLVuXshEDBUOP1LTQZypinux5bCUhEAMvV0kYvaqJqm', '1231231231', 1, 'Female', '2019-08-19 11:35:41', 'd');

-- --------------------------------------------------------

--
-- Table structure for table `publishers`
--

CREATE TABLE `publishers` (
  `id` bigint(20) NOT NULL,
  `createddate` datetime DEFAULT NULL,
  `createdid` bigint(20) DEFAULT NULL,
  `publishinghouse` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `uploadid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `publishers`
--

INSERT INTO `publishers` (`id`, `createddate`, `createdid`, `publishinghouse`, `updatedate`, `uploadid`) VALUES
(1, '2019-08-19 11:07:27', 1, 'updatingPublisher', '2019-08-19 11:07:27', 1),
(2, '2019-08-19 11:22:53', 1, 'Kim Đồng', '2019-08-19 11:22:53', 1),
(3, '2019-08-19 11:23:06', 1, 'Tuổi Trẻ', '2019-08-19 11:23:06', 1),
(4, '2019-08-19 11:23:12', 1, 'Thanh Niên', '2019-08-19 11:23:12', 1),
(5, '2019-08-19 11:23:23', 1, 'Giáo Dục', '2019-08-19 11:23:23', 1),
(6, '2019-08-19 11:23:35', 1, 'Ngoại Quốc', '2019-08-19 11:23:35', 1),
(7, '2019-08-19 11:23:43', 1, 'Chính Trị Quốc Gia', '2019-08-19 11:23:43', 1),
(8, '2019-08-19 11:23:47', 1, 'Hội Nhà Văn', '2019-08-19 11:23:47', 1),
(9, '2019-08-19 11:23:58', 1, 'Tư pháp', '2019-08-19 11:23:58', 1),
(10, '2019-08-19 11:24:03', 1, 'Thông Tin Và Truyền Thông', '2019-08-19 11:24:03', 1),
(11, '2019-08-19 11:24:15', 1, 'Tổng hợp thành phố Hồ Chí Minh', '2019-08-19 11:24:15', 1),
(12, '2019-08-19 11:25:15', 1, 'Thái Hà Book', '2019-08-19 11:25:15', 1),
(13, '2019-08-19 11:25:24', 1, 'Phương Nam Book', '2019-08-19 11:25:24', 1),
(14, '2019-08-19 11:25:52', 1, 'Đông A Book', '2019-08-19 11:25:52', 1),
(15, '2019-08-19 11:26:15', 1, 'Alpha Book', '2019-08-19 11:26:15', 1),
(16, '2019-08-19 11:26:30', 1, 'First News', '2019-08-19 11:26:30', 1),
(17, '2019-08-19 11:26:42', 1, 'Đông Tây', '2019-08-19 11:26:42', 1);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_EMPLOYEE'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `role_person`
--

CREATE TABLE `role_person` (
  `personid` bigint(20) NOT NULL,
  `roleid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_person`
--

INSERT INTO `role_person` (`personid`, `roleid`) VALUES
(1, 2),
(2, 2),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `upload`
--

CREATE TABLE `upload` (
  `id` bigint(20) NOT NULL,
  `modified_file_name` varchar(255) DEFAULT NULL,
  `modified_file_path` varchar(255) DEFAULT NULL,
  `original_file_name` varchar(255) DEFAULT NULL,
  `uploaded_date` date DEFAULT NULL,
  `book_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `upload`
--

INSERT INTO `upload` (`id`, `modified_file_name`, `modified_file_path`, `original_file_name`, `uploaded_date`, `book_id`) VALUES
(1, '1.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.4927901754991345055.8090\\uploads\\1', 'Ke-cap-tia-chop-Rick-Riordan-195x300.jpg', '2019-08-19', 1),
(2, '2.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.4927901754991345055.8090\\uploads\\2', 'Bien-quai-vat-1-215x300.jpg', '2019-08-19', 2),
(3, '3.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.4927901754991345055.8090\\uploads\\3', 'Loi-nguyen-cua-than-titan-Rick-Riordan-196x300.jpg', '2019-08-19', 3),
(4, '4.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.4927901754991345055.8090\\uploads\\4', 'Ho-so-a-than-Rick-Riordan-214x300.jpg', '2019-08-19', 4),
(5, '5.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.4927901754991345055.8090\\uploads\\5', 'Vi-than-cuoi-cung-Rick-Riordan-200x300.jpg', '2019-08-19', 5),
(6, '6.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.9382873271535433288.8090\\uploads\\6', 'choc-tuc-vo-yeu-mua-mot-tang-mot.jpg', '2019-08-19', 6),
(7, '7.zip', 'C:\\Users\\laptop\\AppData\\Local\\Temp\\tomcat-docbase.9382873271535433288.8090\\uploads\\7', 'toan9.jpg', '2019-08-19', 7);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5w75tx731o2t3abhgesnobgih` (`person_id`),
  ADD KEY `FKayy5edfrqnegqj3882nce6qo8` (`publisher_id`);

--
-- Indexes for table `book_category`
--
ALTER TABLE `book_category`
  ADD PRIMARY KEY (`book_id`,`category_id`),
  ADD KEY `FKam8llderp40mvbbwceqpu6l2s` (`category_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_g0guo4k8krgpwuagos61oc06j` (`token`),
  ADD KEY `FKoyrnx3gaa02g366ipisgjsl0g` (`personid`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKfwmwi44u55bo4rvwsv0cln012` (`email`);

--
-- Indexes for table `publishers`
--
ALTER TABLE `publishers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role_person`
--
ALTER TABLE `role_person`
  ADD PRIMARY KEY (`personid`,`roleid`),
  ADD KEY `FKg008ovnpp41l4fbhqf1mcs1qy` (`roleid`);

--
-- Indexes for table `upload`
--
ALTER TABLE `upload`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1brmojlpdw5028ifj7r1qxo39` (`book_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `publishers`
--
ALTER TABLE `publishers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `upload`
--
ALTER TABLE `upload`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `FK5w75tx731o2t3abhgesnobgih` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKayy5edfrqnegqj3882nce6qo8` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id`);

--
-- Constraints for table `book_category`
--
ALTER TABLE `book_category`
  ADD CONSTRAINT `FK7k0c5mr0rx89i8jy5ges23jpe` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `FKam8llderp40mvbbwceqpu6l2s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Constraints for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD CONSTRAINT `FKoyrnx3gaa02g366ipisgjsl0g` FOREIGN KEY (`personid`) REFERENCES `person` (`id`);

--
-- Constraints for table `role_person`
--
ALTER TABLE `role_person`
  ADD CONSTRAINT `FKg008ovnpp41l4fbhqf1mcs1qy` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKiicpco8732q88w1klienv1sda` FOREIGN KEY (`personid`) REFERENCES `person` (`id`);

--
-- Constraints for table `upload`
--
ALTER TABLE `upload`
  ADD CONSTRAINT `FK1brmojlpdw5028ifj7r1qxo39` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
