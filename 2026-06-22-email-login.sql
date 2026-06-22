-- 邮箱注册/登录改造：为 user 表新增 email、password 字段，并允许 open_id 为空
-- （邮箱注册的用户没有微信 openId）

ALTER TABLE `user`
    ADD COLUMN `email` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱（邮箱注册/登录使用）' AFTER `open_id`,
    ADD COLUMN `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码（BCrypt 加密后存储）' AFTER `email`;

-- open_id 原为 NOT NULL，邮箱用户没有 openId，这里改为可空
ALTER TABLE `user`
    MODIFY COLUMN `open_id` char(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信openid，邮箱注册用户为空';

-- 邮箱唯一索引（MySQL 唯一索引允许多个 NULL，微信用户 email 为空互不冲突）
ALTER TABLE `user`
    ADD UNIQUE KEY `uniq_email` (`email`) USING BTREE;
