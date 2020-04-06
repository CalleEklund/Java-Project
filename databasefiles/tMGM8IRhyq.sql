-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Värd: localhost
-- Tid vid skapande: 06 apr 2020 kl 10:34
-- Serverversion: 8.0.13-4
-- PHP-version: 7.2.24-0ubuntu0.18.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `tMGM8IRhyq`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `loan`
--

CREATE TABLE `loan` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `intrest` decimal(10,0) NOT NULL,
  `amount` int(11) NOT NULL,
  `amortization` int(11) NOT NULL,
  `startdate` date NOT NULL,
  `enddate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumpning av Data i tabell `loan`
--

INSERT INTO `loan` (`id`, `user_id`, `title`, `description`, `intrest`, `amount`, `amortization`, `startdate`, `enddate`) VALUES
(3, 1, 'testdb', 'testdb', '10', 100, 1, '2000-10-14', '2020-03-11'),
(4, 1, 'testdb2', 'testdb', '10', 100, 1, '2000-10-14', '2020-03-11'),
(5, 8, 'testdb2', 'testdb', '10', 100, 1, '2000-10-14', '2020-03-11'),
(6, 10, 'Test', 'testdesc', '1000', 1000, 1000, '2020-02-17', '2020-02-24'),
(10, 1, 'Testprogg2', 'testdesc', '1000', 1000, 1000, '2020-02-18', '2020-02-19'),
(11, 10, 'Testprogg2', 'testdesc', '1000', 1000, 1000, '2020-02-18', '2020-02-19'),
(12, 1, 'Test3', 'testdesc', '1000', 1000, 1000, '2020-02-20', '2020-03-10'),
(13, 8, 'Test', 'testdesc', '1000', 1000, 1000, '2020-02-20', '2020-02-25'),
(14, 1, 'Test4456677', 'testdesc', '1000', 1000, 1000, '2020-02-24', '2020-02-25'),
(15, 8, 'Test213', 'testdesc', '1000', 1000, 1000, '2020-02-24', '2020-02-25'),
(16, 8, 'Testawdawd', 'testdesc', '1000', 1000, 1000, '2020-03-02', '2020-03-22');

-- --------------------------------------------------------

--
-- Tabellstruktur `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `userType` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumpning av Data i tabell `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `userType`) VALUES
(1, 'calle', 'test@gmail.com', 'testlosen', 0),
(8, 'calletest', 'no@gmail.com', 'nolosen', 0),
(10, 'db', 'db@gmail.com', 'dblosen', 0),
(11, NULL, 'admin@gmail.com', 'admin', 1),
(13, 'admintest', 'admintest@gmail.com', 'admintest', 1);

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `loan`
--
ALTER TABLE `loan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index för tabell `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `loan`
--
ALTER TABLE `loan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT för tabell `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restriktioner för dumpade tabeller
--

--
-- Restriktioner för tabell `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
