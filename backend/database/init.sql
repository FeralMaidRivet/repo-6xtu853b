CREATE DATABASE IF NOT EXISTS yiwu_clothing_donation
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE yiwu_clothing_donation;

-- 表结构由 Spring JPA 自动创建（ddl-auto=update）
-- 以下为参考结构说明

-- 用户表
-- CREATE TABLE users (
--   id BIGINT AUTO_INCREMENT PRIMARY KEY,
--   username VARCHAR(50) NOT NULL UNIQUE,
--   password VARCHAR(100) NOT NULL,
--   real_name VARCHAR(50) NOT NULL,
--   phone VARCHAR(30),
--   email VARCHAR(100),
--   role VARCHAR(20) NOT NULL,
--   address VARCHAR(200),
--   enabled BOOLEAN DEFAULT TRUE,
--   created_at DATETIME
-- );

-- 捐赠表
-- CREATE TABLE donations (
--   id BIGINT AUTO_INCREMENT PRIMARY KEY,
--   certificate_no VARCHAR(40) NOT NULL UNIQUE,
--   donor_name VARCHAR(50) NOT NULL,
--   phone VARCHAR(30) NOT NULL,
--   address VARCHAR(300) NOT NULL,
--   category VARCHAR(80) NOT NULL,
--   weight_kg DECIMAL(10,2) NOT NULL,
--   appointment_time DATETIME,
--   note VARCHAR(500),
--   status VARCHAR(32) NOT NULL,
--   recycler_name VARCHAR(50),
--   institution_name VARCHAR(100),
--   flow_direction VARCHAR(100),
--   created_at DATETIME
-- );

-- 捐赠跟踪记录表
-- CREATE TABLE donation_tracking_records (
--   donation_id BIGINT NOT NULL,
--   record_order INT NOT NULL,
--   status VARCHAR(32) NOT NULL,
--   operator VARCHAR(50),
--   description VARCHAR(500),
--   created_at DATETIME,
--   PRIMARY KEY (donation_id, record_order),
--   FOREIGN KEY (donation_id) REFERENCES donations(id)
-- );

-- 合作机构表
-- CREATE TABLE institutions (
--   id BIGINT AUTO_INCREMENT PRIMARY KEY,
--   name VARCHAR(100) NOT NULL,
--   type VARCHAR(50),
--   contact VARCHAR(50),
--   region VARCHAR(100),
--   demand VARCHAR(300),
--   address VARCHAR(200),
--   enabled BOOLEAN DEFAULT TRUE,
--   created_at DATETIME
-- );
