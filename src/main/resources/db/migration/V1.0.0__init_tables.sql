CREATE TABLE `accounts` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `account_status` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)