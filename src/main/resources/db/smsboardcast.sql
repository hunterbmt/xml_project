CREATE TABLE `user` (
  `uuid` varchar(20) NOT NULL,
  `access_token` varchar(254) NOT NULL,
  PRIMARY KEY (`uuid`),
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `campaign` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `msg` text NOT NULL,
  `user_uuid` varchar(20) NOT NULL,
  `created_date_time` DATETIME NOT NULL,
  `start_date` DATETIME DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `send_numbers` text DEFAULT NULL,
  `number_of_receiver` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `owner_fkey` FOREIGN KEY (`user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `numberlist` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `numbers` text NOT NULL,
  `number_of_receiver` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `owner_list_fkey` FOREIGN KEY (`user_uuid`) REFERENCES `user` (`uuid`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `result` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `campaign_id` int(10) NOT NULL,
  `dest_number` varchar(20) NOT NULL,
  `is_success` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `camp_result_fkey` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
