# 衣物捐赠系统

本项目为"题目16 衣物捐赠系统"的课程设计，采用 Vue 3 + Element Plus + Spring Boot + MySQL 实现废旧衣物回收、捐赠、再生一体化公益平台。

## 功能模块

- **捐赠者**：在线预约上门回收、填写捐赠信息、查看捐赠进度、获取捐赠证明。
- **回收人员**：查看接收任务、接单上门、分类打包、物流交接。
- **管理员**：管理捐赠数据、分拣分配、合作机构对接、物资流向跟踪、统计公示。
- **公示公开**：展示统计数据、捐赠再利用情况、公益发放、合作机构统计数据。

## 项目结构

```text
backend/    Spring Boot 后端接口（Spring Data JPA + MySQL）
frontend/   Vue 3 + Element Plus + Vue Router 前端页面
```

## 技术栈

- **前端**：Vue 3、Vue Router、Pinia、Element Plus、@element-plus/icons-vue、Axios、Vite
- **后端**：Spring Boot 3.2、Spring Data JPA、MySQL Connector
- **数据库**：MySQL 8.0+

## MySQL 数据库

默认数据库：`yiwu_clothing_donation`

如果 MySQL root 密码不是 `123456`，在启动后端前设置环境变量：

```powershell
$env:MYSQL_URL="jdbc:mysql://localhost:3306/yiwu_clothing_donation?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&createDatabaseIfNotExist=true"
$env:MYSQL_USERNAME="root"
$env:MYSQL_PASSWORD="你的MySQL密码"
```

也可手动创建数据库：

```sql
CREATE DATABASE IF NOT EXISTS yiwu_clothing_donation DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

后端启用了 `spring.jpa.hibernate.ddl-auto=update`，首次启动自动建表，并初始化示例捐赠和合作机构数据。

## 后端运行

```powershell
cd backend
./mvnw spring-boot:run
```

后端默认地址：`http://localhost:8080`

## 前端运行

```powershell
cd frontend
npm install
npm run dev
```

前端默认地址：`http://localhost:5173`

## 测试账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |
| collector1 | 123456 | 回收员 |
| collector2 | 123456 | 回收员 |
| donor1 | 123456 | 捐赠者 |

## 构建检查

```powershell
cd backend
./mvnw -q -DskipTests package

cd ../frontend
npm install
npm run build
```
