-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2018 at 02:44 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `exam`
--
CREATE DATABASE IF NOT EXISTS `exam` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `exam`;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `Code` varchar(5) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Describtion` varchar(45) NOT NULL,
  `Exam time` time NOT NULL,
  `No of hours` double NOT NULL,
  `Semester` varchar(10) NOT NULL,
  `Level` varchar(45) NOT NULL,
  `Midterm` int(11) NOT NULL,
  `Oral` int(11) NOT NULL,
  `Final` int(11) NOT NULL,
  `Doctor_ID` int(11) NOT NULL,
  `is_active1` tinyint(1) NOT NULL,
  `is_active2` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`Code`, `Name`, `Describtion`, `Exam time`, `No of hours`, `Semester`, `Level`, `Midterm`, `Oral`, `Final`, `Doctor_ID`, `is_active1`, `is_active2`) VALUES
('201CS', 'Programming Language', 'C++ /OOP', '02:00:00', 20, '2', '2', 20, 5, 100, 2, 1, 0),
('305CS', 'Database', 'Database Essentials with MySQL', '03:00:00', 20, '2', '3', 20, 5, 75, 1, 0, 0),
('401CS', 'Programming Language', 'Java Programming', '03:00:00', 20, '1', '4', 20, 5, 75, 2, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `course_has_course`
--

DROP TABLE IF EXISTS `course_has_course`;
CREATE TABLE `course_has_course` (
  `Course_aquired` varchar(5) NOT NULL,
  `Course_required` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`ID`, `Name`) VALUES
(1, 'Math');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Phone` varchar(13) DEFAULT NULL,
  `Role` varchar(45) NOT NULL,
  `Department_ID` int(11) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`ID`, `Name`, `Email`, `Phone`, `Role`, `Department_ID`, `Password`) VALUES
(1, 'Wael Zakria', 'wael.zakaria@sci.asu.edu.eg', '01005648916', 'Doctor', 1, ' '),
(2, 'Mohamed Hashem', 'm_hashim123@yahoo.com', NULL, 'Doctor', 1, ' '),
(3, 'Ashraf Mostafa', 'ashrafbhery09@gmail.com', '01095110149', 'Doctor', 1, ' '),
(4, 'Ahmed Abdelfattah', 'ahabdelfattah@sci.asu.edu.eg', '01018866971', 'Doctor', 1, ' ');

-- --------------------------------------------------------

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation` (
  `ID` int(11) NOT NULL,
  `Course_Code` varchar(5) NOT NULL,
  `year` year(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `e_question`
--

DROP TABLE IF EXISTS `e_question`;
CREATE TABLE `e_question` (
  `ID` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `e_question`
--

INSERT INTO `e_question` (`ID`, `description`, `type`) VALUES
(1, 'تم توضيح أهداف المقرر فى نهاية الفصل الدراسى', 'before'),
(2, 'تحققت أهداف المقرر فى نهاية الفصل الدراسى', 'before'),
(3, 'استعين بالمحاضرة كمصدر اساسى فى الاستذكار', 'before'),
(4, 'المحاضرات و التدريبات العلمية ترتبط بأهداف المقرر', 'before');

-- --------------------------------------------------------

--
-- Table structure for table `e_question_has_evaluation`
--

DROP TABLE IF EXISTS `e_question_has_evaluation`;
CREATE TABLE `e_question_has_evaluation` (
  `E_Question_ID` int(11) NOT NULL,
  `Evaluation_ID` int(11) NOT NULL,
  `Answer` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
CREATE TABLE `program` (
  `Id` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL COMMENT 'Computer Section is dept or program !',
  `No. Of Hours` double NOT NULL,
  `Department_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `program`
--

INSERT INTO `program` (`Id`, `Name`, `No. Of Hours`, `Department_ID`) VALUES
(1, 'Statistics and Computer', 20, 1),
(2, 'Pure Math and Computer Science', 20, 1),
(3, 'Statistics and Pure Math', 20, 1);

-- --------------------------------------------------------

--
-- Table structure for table `program_has_course`
--

DROP TABLE IF EXISTS `program_has_course`;
CREATE TABLE `program_has_course` (
  `program_Id` int(11) NOT NULL,
  `course_Code` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `program_has_course`
--

INSERT INTO `program_has_course` (`program_Id`, `course_Code`) VALUES
(1, '201CS'),
(1, '305CS'),
(1, '401CS'),
(2, '201CS'),
(2, '305CS'),
(2, '401CS');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `question` varchar(70) NOT NULL,
  `first_choice` varchar(45) NOT NULL,
  `second_choice` varchar(45) NOT NULL,
  `third_choice` varchar(45) DEFAULT NULL,
  `forth_choice` varchar(45) DEFAULT NULL,
  `topic` varchar(45) NOT NULL,
  `level` varchar(45) NOT NULL,
  `correct_answer` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `question`, `first_choice`, `second_choice`, `third_choice`, `forth_choice`, `topic`, `level`, `correct_answer`) VALUES
(1, 'Your faculty', 'Science', 'Law', 'Commerce', 'engineering', 'A', 'easy', 'Science'),
(2, 'Your year', '1', '2', '3', '4', 'A', 'easy', '4'),
(3, 'DB Doctor', 'Ahmed', 'Wael', 'Zaky', 'Yasser', 'B', 'medium', 'Wael'),
(4, 'FS Doctor', 'Mona', 'Neven', 'Azza', 'Nesren', 'B', 'medium', 'Azza'),
(5, 'Year of Reg', '2013', '2014', '2015', '2016', 'C', 'hard', '2013'),
(6, 'Year of graduation', '2018', '2019', '2020', '2021', 'C', 'hard', '2018');

-- --------------------------------------------------------

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
CREATE TABLE `quiz` (
  `id` int(11) NOT NULL,
  `total_deg` int(11) NOT NULL,
  `quiz_generator_id` int(11) NOT NULL,
  `Course_Code` varchar(5) NOT NULL,
  `Registeration_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `quiz_generator`
--

DROP TABLE IF EXISTS `quiz_generator`;
CREATE TABLE `quiz_generator` (
  `id` int(11) NOT NULL,
  `is_current` tinyint(4) NOT NULL,
  `start_topic` varchar(20) NOT NULL,
  `end_topic` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `no_level1` int(11) NOT NULL,
  `no_level2` int(11) NOT NULL,
  `no_level3` int(11) NOT NULL,
  `Doctor_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `quiz_has_questions`
--

DROP TABLE IF EXISTS `quiz_has_questions`;
CREATE TABLE `quiz_has_questions` (
  `quiz_id` int(11) NOT NULL,
  `questions_id` int(11) NOT NULL,
  `student_choice` varchar(45) NOT NULL,
  `is_correct` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `registeration`
--

DROP TABLE IF EXISTS `registeration`;
CREATE TABLE `registeration` (
  `ID` int(11) NOT NULL,
  `Oral` int(11) NOT NULL,
  `Grade` int(11) NOT NULL,
  `Quiz1` int(11) DEFAULT NULL,
  `Quiz2` int(11) DEFAULT NULL,
  `IS_Evaluate1` tinyint(4) DEFAULT NULL,
  `IS_Evaluate2` tinyint(4) DEFAULT NULL,
  `Attendence_Percentage` double DEFAULT NULL,
  `Course_Code` varchar(5) NOT NULL,
  `Student_ID` varchar(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `registeration`
--

INSERT INTO `registeration` (`ID`, `Oral`, `Grade`, `Quiz1`, `Quiz2`, `IS_Evaluate1`, `IS_Evaluate2`, `Attendence_Percentage`, `Course_Code`, `Student_ID`) VALUES
(1, 5, 85, NULL, NULL, 0, 1, 68, '201CS', '20140918224532'),
(2, 5, 95, NULL, NULL, 0, 0, 0, '305CS', '20140918224532'),
(3, 4, 70, NULL, NULL, 0, 0, 65, '305CS', '20141128151759'),
(4, 5, 75, NULL, NULL, 0, 0, 76, '401CS', '20141128151759'),
(5, 5, 80, NULL, NULL, 0, 0, 80, '305CS', '20141128153448'),
(6, 3, 60, NULL, NULL, 0, 0, 75, '401CS', '20141128153448'),
(7, 4, 75, NULL, NULL, 0, 0, 60, '305CS', '20141128223836'),
(8, 4, 87, NULL, NULL, 0, 0, 90, '401CS', '20141128223836');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `ID` varchar(14) NOT NULL,
  `StartDate` date NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Level` int(11) NOT NULL,
  `GPA` double DEFAULT NULL,
  `AverageMaxHours` double DEFAULT NULL,
  `Program_Id` int(11) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`ID`, `StartDate`, `Name`, `Level`, `GPA`, `AverageMaxHours`, `Program_Id`, `Email`, `Password`) VALUES
('20140918224532', '2012-09-15', 'Medhat Mohamed Hassan Gad', 4, 3, 19, 1, 'medhat.mmh5@gmail.com', '$2y$10$GnzRhV6vMeBVh1G73/VGtuQUP1azNUBKE3J8iy35RWTf9oRgqSHLG'),
('20141128151759', '2014-09-20', 'Heba Emad Ali Emam Sallam', 4, 3.5, 19, 1, ' ', ' '),
('20141128153448', '2014-09-20', 'Omnia Ahmed Fouad Osman', 4, 3.5, 19, 1, 'omniafouad52@gmail.com', '$2y$10$Isbx3rNvELzqKawRhgpbxeqfI5TiNH6dEUWWeKKsUo8.vVTVsE0qq'),
('20141128223836', '2014-09-20', 'Asmaa Dawod Soliman Dahb', 4, 3.5, 19, 1, ' ', ' ');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`Code`),
  ADD KEY `fk_Course_Doctor1_idx` (`Doctor_ID`);

--
-- Indexes for table `course_has_course`
--
ALTER TABLE `course_has_course`
  ADD PRIMARY KEY (`Course_aquired`,`Course_required`),
  ADD KEY `fk_Course_has_Course_Course2_idx` (`Course_required`),
  ADD KEY `fk_Course_has_Course_Course1_idx` (`Course_aquired`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Doctor_Department1_idx` (`Department_ID`);

--
-- Indexes for table `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Evaluation_Course1_idx` (`Course_Code`);

--
-- Indexes for table `e_question`
--
ALTER TABLE `e_question`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `e_question_has_evaluation`
--
ALTER TABLE `e_question_has_evaluation`
  ADD PRIMARY KEY (`E_Question_ID`,`Evaluation_ID`),
  ADD KEY `fk_E_Question_has_Evaluation_Evaluation1_idx` (`Evaluation_ID`),
  ADD KEY `fk_E_Question_has_Evaluation_E_Question1_idx` (`E_Question_ID`);

--
-- Indexes for table `program`
--
ALTER TABLE `program`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `fk_Program_Department1_idx` (`Department_ID`);

--
-- Indexes for table `program_has_course`
--
ALTER TABLE `program_has_course`
  ADD PRIMARY KEY (`program_Id`,`course_Code`),
  ADD KEY `fk_program_has_course_course1_idx` (`course_Code`),
  ADD KEY `fk_program_has_course_program1_idx` (`program_Id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id`,`quiz_generator_id`),
  ADD KEY `fk_quiz_quiz_generator1_idx` (`quiz_generator_id`),
  ADD KEY `fk_quiz_Course1_idx` (`Course_Code`),
  ADD KEY `fk_quiz_Registeration1_idx` (`Registeration_ID`);

--
-- Indexes for table `quiz_generator`
--
ALTER TABLE `quiz_generator`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_quiz_generator_Doctor1_idx` (`Doctor_ID`);

--
-- Indexes for table `registeration`
--
ALTER TABLE `registeration`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Registeration_Course1_idx` (`Course_Code`),
  ADD KEY `fk_Registeration_Student1_idx` (`Student_ID`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `ID_UNIQUE` (`ID`),
  ADD KEY `fk_Student_Program1_idx` (`Program_Id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `fk_Course_Doctor1` FOREIGN KEY (`Doctor_ID`) REFERENCES `doctor` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `doctor`
--
ALTER TABLE `doctor`
  ADD CONSTRAINT `fk_Doctor_Department1` FOREIGN KEY (`Department_ID`) REFERENCES `department` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `fk_Evaluation_Course1` FOREIGN KEY (`Course_Code`) REFERENCES `course` (`Code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `program`
--
ALTER TABLE `program`
  ADD CONSTRAINT `fk_Program_Department1` FOREIGN KEY (`Department_ID`) REFERENCES `department` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `fk_quiz_Course1` FOREIGN KEY (`Course_Code`) REFERENCES `course` (`Code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_quiz_Registeration1` FOREIGN KEY (`Registeration_ID`) REFERENCES `registeration` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_quiz_quiz_generator1` FOREIGN KEY (`quiz_generator_id`) REFERENCES `quiz_generator` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `quiz_generator`
--
ALTER TABLE `quiz_generator`
  ADD CONSTRAINT `fk_quiz_generator_Doctor1` FOREIGN KEY (`Doctor_ID`) REFERENCES `doctor` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `registeration`
--
ALTER TABLE `registeration`
  ADD CONSTRAINT `fk_Registeration_Course1` FOREIGN KEY (`Course_Code`) REFERENCES `course` (`Code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Registeration_Student1` FOREIGN KEY (`Student_ID`) REFERENCES `student` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_Student_Program1` FOREIGN KEY (`Program_Id`) REFERENCES `program` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
