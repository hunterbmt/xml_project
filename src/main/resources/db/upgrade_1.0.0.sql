use xml_project;
alter table tbl_users add column `balance` int default 5;
alter table tbl_bids add column `cost` int default 1;
alter table tbl_product add column `bid_id` int NULL;
alter table tbl_search_cache charset=utf8;
alter table tbl_order_history add column `status` TINYINT(2) NOT NULL;