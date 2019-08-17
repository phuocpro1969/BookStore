-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 16, 2019 at 02:49 PM
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
-- Database: `bookstore2`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` bigint(20) NOT NULL,
  `authors` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ok` int(11) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `published_year` int(11) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uploaded_date` date DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `publisher_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `authors`, `domain`, `ok`, `picture`, `price`, `published_year`, `title`, `uploaded_date`, `person_id`, `publisher_id`) VALUES
(21, 'Rick Riordan', 'US', 0, 'Ke-cap-tia-chop-Rick-Riordan-195x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 1: Kẻ Cắp Tia Chớp', NULL, 1, 42),
(22, 'Rick Riordan', 'US', 0, 'Bien-quai-vat-1-215x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 2: Biển Quái Vật', NULL, 1, 42),
(23, 'Rick Riordan', 'US', 0, 'Loi-nguyen-cua-than-titan-Rick-Riordan-196x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 3: Lời Nguyền Của Thần Titan', NULL, 1, 42),
(24, 'Rick Riordan', 'US', 0, 'Cuoc-chien-chon-me-cung-1-214x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 4: Cuộc Chiến Chốn Mê Cung', NULL, 1, 42),
(25, 'Rick Riordan', 'US', 0, 'Ho-so-a-than-Rick-Riordan-214x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 4,5: Hồ sơ á thần', NULL, 1, 42),
(26, 'Rick Riordan', 'US', 0, 'Vi-than-cuoi-cung-Rick-Riordan-200x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 5: Vị Thần Cuối Cùng', NULL, 1, 42),
(27, 'Lương Hải Yến', 'bắc', 0, 'lam-am-giuong-cho-tong-giam-doc.jpg', 100000, 0, 'Làm Ấm Giường Cho Tổng Giám Đốc ', NULL, 10, 43),
(28, 'Quẫn Quẫn Hữu Yêu', 'bắc', 0, 'choc-tuc-vo-yeu-mua-mot-tang-mot.jpg', 100000, 0, 'CHỌC TỨC VỢ YÊU - MUA MỘT TẶNG MỘT', NULL, 10, 43),
(29, 'Quẫn Quẫn Hữu Yêu', 'bắc', 0, 'co-nguoi-quang-doi-con-lai-deu-ngot-poster-1561783857-220x330.jpg', 100000, 0, 'Cuộc Đời Ngọt Ngào Khi Có Em', NULL, 10, 43),
(33, 'Bộ Giáo Dục Và Đào Tạo', 'bắc', 0, 'vl10.jpg', 0, 0, 'Vật Lý 10', NULL, 19, 45),
(34, 'Bộ Giáo Dục Và Đào Tạo', 'bắc', 0, 'vl12.jpg', 0, 0, 'Vật Lý 12', NULL, 19, 45);

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
(21, 32),
(22, 32),
(23, 32),
(24, 32),
(25, 32),
(26, 32),
(27, 32),
(28, 32),
(29, 32),
(33, 29),
(33, 40),
(34, 29),
(34, 40);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `createdate` date DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `updatedate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `createdate`, `name`, `updatedate`) VALUES
(29, '2019-08-16', 'Sách giáo Khoa', '2019-08-16'),
(30, '2019-08-16', 'Truyện Tranh', '2019-08-16'),
(31, '2019-08-16', 'Trinh Thám', '2019-08-16'),
(32, '2019-08-16', 'Tiểu Thuyết ', '2019-08-16'),
(33, '2019-08-16', 'Kinh dị ', '2019-08-16'),
(34, '2019-08-16', 'Hành Động', '2019-08-16'),
(35, '2019-08-16', 'Nấu Ăn', '2019-08-16'),
(36, '2019-08-16', 'IT', '2019-08-16'),
(37, '2019-08-16', 'Tiếng Anh', '2019-08-16'),
(39, '2019-08-16', 'Hóa Học ', '2019-08-16'),
(40, '2019-08-16', 'Vật Lý', '2019-08-16');
(41, '2019-08-17', 'Self-Help', '2019-08-17');

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
(1, 'BP', '1999-01-08', '2019-08-14 18:01:16', 'phuocpro1969@gmail.com', 'admin', 'admin', '$2a$10$9gQwAySwfBeO0/vsY4Njj.IrU6vZ3mRmxb3QElXkbzCmRFpmsFH.e', '0981415287', 2, 'Male', '2019-08-15 11:17:12', 'admin'),
(10, 'BP', '1999-01-08', '2019-08-15 13:08:30', '17520918@gm.uit.edu.vn', 'Phan', 'Phước', '$2a$10$zKV33rC.y5okIJpv.5jlVePZJEAKQAHutEcZikDvza0BYwVnzkDvy', '091223123123', 1, 'Male', '2019-08-15 13:08:30', 'phuocpro1969'),
(19, 'a', '1945-01-01', '2019-08-16 13:40:32', 'a@gmail.com', 'a', 'a', '$2a$10$tTpqdecYegKp2e6enuqijOmNFtrgQelHuOd2xh7GxbW82V0AlUQ8S', '1231231231', 1, NULL, '2019-08-16 13:40:32', 'a');

-- --------------------------------------------------------

--
-- Table structure for table `publish`
--

CREATE TABLE `publish` (
  `id` bigint(20) NOT NULL,
  `createddate` varchar(255) DEFAULT NULL,
  `createduser` varchar(255) DEFAULT NULL,
  `publishinghouse` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `updatedate` varchar(255) DEFAULT NULL,
  `uploaduser` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `publish`
--

INSERT INTO `publish` (`id`, `createddate`, `createduser`, `publishinghouse`, `updatedate`, `uploaduser`) VALUES
(41, '', 'admin', 'Kim Đồng', '', 'admin'),
(42, '', 'admin', 'Ngoại Quốc', '', 'admin'),
(43, '', 'admin', 'Tuổi trẻ', '', 'admin'),
(44, '', 'admin', 'Thanh Niên', '', 'admin'),
(45, '', 'admin', 'Giáo Dục', '', 'admin'),
(46, '', 'admin', 'Tổng Hợp Thành Phố Hồ Chí Minh', '', 'admin'),
(47, '', 'admin', 'Chính Trị Quốc Gia', '', 'admin'),
(48, '', 'admin', 'Hội Nhà Văn', '', 'admin'),
(49, '', 'admin', 'Tư pháp', '', 'admin'),
(50, '', 'admin', 'Thông Tin Và Truyền Thông', '', 'admin');

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
(10, 1),
(19, 1);

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
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5w75tx731o2t3abhgesnobgih` (`person_id`),
  ADD KEY `FK71btqhk95xu3ktf863l90oqdd` (`publisher_id`);

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
-- Indexes for table `publish`
--
ALTER TABLE `publish`
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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `upload`
--
ALTER TABLE `upload`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `FK5w75tx731o2t3abhgesnobgih` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FK71btqhk95xu3ktf863l90oqdd` FOREIGN KEY (`publisher_id`) REFERENCES `publish` (`id`);

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
