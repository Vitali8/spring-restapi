-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Хост: mariadb:3306
-- Час створення: Жов 27 2019 р., 19:28
-- Версія сервера: 10.3.12-MariaDB-1:10.3.12+maria~bionic
-- Версія PHP: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `example-db`
--
CREATE DATABASE IF NOT EXISTS `example-db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `example-db`;

-- --------------------------------------------------------

--
-- Структура таблиці `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `msg` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Очистити таблицю перед вставкою `messages`
--

TRUNCATE TABLE `messages`;
--
-- Дамп даних таблиці `messages`
--

INSERT INTO `messages` (`id`, `title`, `msg`, `createdAt`, `updatedAt`) VALUES
(1, 'Brunch this weekend?', 'I`ll be in your neighborhood doing errands this weekend. Do you want to grab brunch?', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, 'Summer BBQ', 'Wish I could come, but I`m out of town this weekend.', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(3, 'Oui oui', 'Do you have Paris recommendations? Have you ever been?', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(4, 'Birdthday gift', 'Do you have any ideas what we can get Heidi for her birthday? How about a pony?', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(5, 'Recipe to try', 'We should eat this: grated squash. Corn and tomatillo tacos.', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(6, 'Brendan Lim', 'I`ll be in your neighborhood doing errands this weekend. Do you want to grab brunch?', '0000-00-00 00:00:00', '0000-00-00 00:00:00');

--
-- Індекси збережених таблиць
--

--
-- Індекси таблиці `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для збережених таблиць
--

--
-- AUTO_INCREMENT для таблиці `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
