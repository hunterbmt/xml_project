CREATE TABLE `tbl_category` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `category_name` varchar(50) NOT NULL,
    `description` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);	
CREATE TABLE `tbl_Product` (
    `product_id` int(10) NOT NULL AUTO_INCREMENT,
    `product_name` varchar(100) NOT NULL,
    `category_id` int(10) NOT NULL,
    `description` varchar(400) NOT NULL,
    `image` text NOT NULL,
    `status` TINYINT(2) NOT NULL,
    `min_price` DOUBLE NOT NULL,
    `max_price` DOUBLE NOT NULL,
    `last_update` DATETIME DEFAULT NULL,
    `is_active` TINYINT(1) NOT NULL,
    PRIMARY KEY (`product_id`),
    CONSTRAINT `product_fkey` FOREIGN KEY (`category_id`)
        REFERENCES `tbl_category` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_tags` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `tbl_tags_product` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `tags_id` int(10) NOT NULL,
    `product_id` int(10) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `tags_fkey` FOREIGN KEY (`tags_id`)
        REFERENCES `tbl_tags` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `product_tags_fkey` FOREIGN KEY (`product_id`)
        REFERENCES `tbl_product` (`product_id`)
        ON DELETE CASCADE ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_bids` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `last_userid` int(10) NOT NULL,
    `product_id` int(10) NOT NULL,
    `current_price` double NOT NULL,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME DEFAULT NULL,
    `last_edit` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `product_fkey` FOREIGN KEY (`product_id`)
        REFERENCES `tbl_products` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_bid_history` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `user_id` int(10) NOT NULL,
    `bid_id` int(10) NOT NULL,
    `price` double NOT NULL,
    bid_time DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `user_fkey` FOREIGN KEY (`user_id`)
        REFERENCES `tbl_users` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `bid_fkey` FOREIGN KEY (`bid_id`)
        REFERENCES `tbl_bids` (`id`)
        ON DELETE CASCADE ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tbl_users(
  user_id int(10) NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (uuid)
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tbl_user_payments(
   payment_id int(10) NOT NULL AUTO_INCREMENT,
   fullname  varchar(255) NOT NULL,
    birthday DATETIME NOT NULL,
    address varchar(255) NOT NULL,
   phone varchar(255) NOT NULL,
   user_id int(10) NOT NULL,
  PRIMARY KEY (_payment_id),
  CONSTRAINT info_fkey FOREIGN KEY (user_id) REFERENCES tbl_users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE tbl_user_payment(
   user_payment_id int(10) NOT NULL AUTO_INCREMENT,
   user_id int(10) NOT NULL,
  payment_day DATETIME NOT NULL,
   card_code text NOT NULL,
   amount int(10) NOT NULL,
  PRIMARY KEY (user_payment_id),
  CONSTRAINT payment_fkey FOREIGN KEY (user_id) REFERENCES tbl_users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tbl_order_history(
   order_history_id int(10) NOT NULL AUTO_INCREMENT,
   user_id int(10) NOT NULL,
   product_id int(10) NOT NULL,
   order_day DATETIME NOT NULL,
   address varchar(255) NOT NULL,
   amount int(10) NOT NULL,
  PRIMARY KEY (order_history_id),
  CONSTRAINT order_user_fkey FOREIGN KEY (user_id) REFERENCES tbl_users(user_id) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT order_product_fkey FOREIGN KEY (product_id) REFERENCES  tbl_Product (product_id) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;