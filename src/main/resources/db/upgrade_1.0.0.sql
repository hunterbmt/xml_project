use xml_project;
alter table tbl_users add column `balance` int default 5;
alter table tbl_bids add column `cost` int default 1;
alter table tbl_product add column `bid_id` int NULL;
alter table tbl_search_cache charset=utf8;