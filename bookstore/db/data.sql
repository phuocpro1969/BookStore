-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 16, 2019 at 11:36 AM
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

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `authors`, `domain`, `ok`, `picture`, `price`, `published_year`, `title`, `uploaded_date`, `person_id`, `publisher_id`) VALUES
(21, 'Rick Riordan', 'US', 0, 'Ke-cap-tia-chop-Rick-Riordan-195x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 1: Kẻ Cắp Tia Chớp', NULL, 1, NULL),
(22, 'Rick Riordan', 'US', 0, 'Bien-quai-vat-1-215x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 2: Biển Quái Vật', NULL, 1, NULL),
(23, 'Rick Riordan', 'US', 0, 'Loi-nguyen-cua-than-titan-Rick-Riordan-196x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 3: Lời Nguyền Của Thần Titan', NULL, 1, NULL),
(24, 'Rick Riordan', 'US', 0, 'Cuoc-chien-chon-me-cung-1-214x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 4: Cuộc Chiến Chốn Mê Cung', NULL, 1, NULL),
(25, 'Rick Riordan', 'US', 0, 'Ho-so-a-than-Rick-Riordan-214x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 4,5: Hồ sơ á thần', NULL, 1, NULL),
(26, 'Rick Riordan', 'US', 0, 'Vi-than-cuoi-cung-Rick-Riordan-200x300.jpg', 100000, 0, 'Percy Jackson Và Các Vị Thần Trên Đỉnh Olympus Phần 5: Vị Thần Cuối Cùng', NULL, 1, NULL),
(27, 'Lương Hải Yến', 'bắc', 0, 'lam-am-giuong-cho-tong-giam-doc.jpg', 100000, 0, 'Làm Ấm Giường Cho Tổng Giám Đốc ', NULL, 10, NULL),
(28, 'Quẫn Quẫn Hữu Yêu', 'bắc', 0, 'choc-tuc-vo-yeu-mua-mot-tang-mot.jpg', 100000, 0, 'CHỌC TỨC VỢ YÊU - MUA MỘT TẶNG MỘT', NULL, 10, NULL),
(29, 'Quẫn Quẫn Hữu Yêu', 'bắc', 0, 'co-nguoi-quang-doi-con-lai-deu-ngot-poster-1561783857-220x330.jpg', 100000, 0, 'Cuộc Đời Ngọt Ngào Khi Có Em', NULL, 10, NULL),
(30, '', '', 0, 'lam-am-giuong-cho-tong-giam-doc.jpg', 0, 0, '', NULL, 10, NULL),
(31, '', '', 0, '', 0, 0, 'a', NULL, 10, NULL);

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

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `address`, `birthday`, `create_date`, `email`, `firstname`, `lastname`, `password`, `phone`, `power`, `sex`, `update_date`, `username`) VALUES
(1, 'BP', '1999-01-08', '2019-08-14 18:01:16', 'phuocpro1969@gmail.com', 'admin', 'admin', '$2a$10$9gQwAySwfBeO0/vsY4Njj.IrU6vZ3mRmxb3QElXkbzCmRFpmsFH.e', '0981415287', 2, 'Male', '2019-08-15 11:17:12', 'admin'),
(9, 'b', '1945-01-01', '2019-08-15 12:32:02', 'b@gmail.com', 'b', 'b', '$2a$10$WpLNhoU8WmdffXFxIcRHHeCvE30qqEe/CL9hMCoJxq6D.Mwys4X7e', '1231231231', 1, NULL, '2019-08-15 12:32:02', 'b'),
(10, 'BP', '1999-01-08', '2019-08-15 13:08:30', '17520918@gm.uit.edu.vn', 'Phan', 'Phước', '$2a$10$zKV33rC.y5okIJpv.5jlVePZJEAKQAHutEcZikDvza0BYwVnzkDvy', '091223123123', 1, 'Male', '2019-08-15 13:08:30', 'phuocpro1969'),
(19, 'a', '1945-01-01', '2019-08-16 13:40:32', 'a@gmail.com', 'a', 'a', '$2a$10$tTpqdecYegKp2e6enuqijOmNFtrgQelHuOd2xh7GxbW82V0AlUQ8S', '1231231231', 1, NULL, '2019-08-16 13:40:32', 'a');

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

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_EMPLOYEE'),
(2, 'ROLE_ADMIN');

--
-- Dumping data for table `role_person`
--

INSERT INTO `role_person` (`personid`, `roleid`) VALUES
(1, 2),
(9, 1),
(10, 1),
(19, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
