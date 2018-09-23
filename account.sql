CREATE DATABASE IF NOT EXISTS `account`;
USE `account`;

/* Table structure for table `users` */
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `registration` date,
  PRIMARY KEY (id)
);

INSERT INTO `users` (login, password, firstName, lastName, email, registration) VALUES ('tomek', 'tomek', 'tomek', 'tomek', 'tomek@blabla.com', NOW());
INSERT INTO `users` (login, password, firstName, lastName, email, registration) VALUES ('aras2', 'as', 'roberto', 'asd', 'as.dd@blabla', NOW());
INSERT INTO `users` (login, password, firstName, lastName, email, registration) VALUES ('aras3', 'asss', 'asdss', 'asd', 'as.dd@blabla', NOW());

