-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 06 okt 2024 kl 22:51
-- Serverversion: 10.4.32-MariaDB
-- PHP-version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `it-sakerhet-java23`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellstruktur `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumpning av Data i tabell `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `address`) VALUES
(1, 'carsun', 'carl.sundberg@gritacademy.se', '$2a$10$xSDMo285.5PNrGL5w7S19OccBjhZxX9tEnz3SdMRSDbRJtib133w6', 'Lund'),
(60, 'admin', 'admin', '$2a$10$VRKLzBYEdHmYKwiuPl2P.ubcTkRIjfMrNuzePtRTCaNdyGmfHZrdW', 'adminvile'),
(64, 'test', 'test@test.com', '$2a$10$RAW8Vx8nexJZj5kyDeYue.HfoZCBwlcetaeonGjhp0IzZcoBlVj0u', 'testvile');

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `authorities`
--
ALTER TABLE `authorities`
  ADD KEY `username` (`username`);

--
-- Index för tabell `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- Restriktioner för dumpade tabeller
--

--
-- Restriktioner för tabell `authorities`
--
ALTER TABLE `authorities`
  ADD CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
