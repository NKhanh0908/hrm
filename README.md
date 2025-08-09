# HRM - Human Resource Management System

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](#)

## 📋 Mục lục
- [Giới thiệu](#-giới-thiệu)
- [Tính năng](#-tính-năng)
- [Screenshots](#-screenshots)
- [Công nghệ sử dụng](#️-công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Cài đặt](#-cài-đặt)
- [Cấu hình](#️-cấu-hình)
- [API Documentation](#-api-documentation)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Bảo mật](#-bảo-mật)
- [Monitoring](#-monitoring)
- [Đóng góp](#-đóng-góp)
- [Changelog](#-changelog)
- [Liên hệ](#-liên-hệ)
- [Giấy phép](#-giấy-phép)

## 🚀 Giới thiệu

HRM (Human Resource Management) là một hệ thống quản lý nhân sự toàn diện được phát triển bằng Java Spring Boot. Hệ thống cung cấp đầy đủ các tính năng cần thiết cho việc quản lý nhân sự trong doanh nghiệp hiện đại.

### 🎯 Mục tiêu dự án
- Số hóa quy trình quản lý nhân sự
- Tự động hóa các tác vụ thường xuyên
- Cung cấp báo cáo và thống kê realtime
- Tăng hiệu quả công việc HR

## ✨ Tính năng

### 👥 Quản lý Nhân viên
- **Thông tin cá nhân**: CRUD nhân viên với đầy đủ thông tin
- **Người phụ thuộc**: Quản lý thông tin người thân để tính thuế
- **Hợp đồng lao động**: 
  - Tạo, gia hạn, chấm dứt hợp đồng
  - Tự động cập nhật trạng thái (ACTIVE/EXPIRED)
  - Xuất báo cáo hợp đồng PDF
- **Chấm công**: 
  - Check in/out với GPS location
  - Tính toán giờ làm việc, overtime
  - Thống kê theo tháng/quý/năm
- **Nghỉ phép**: Đăng ký và phê duyệt nghỉ phép

### 🏢 Quản lý Tổ chức
- **Phòng ban**: Cấu trúc tổ chức phân cấp
- **Chức vụ & Vai trò**: Phân quyền chi tiết
- **Orgchart**: Sơ đồ tổ chức trực quan

### 📋 Tuyển dụng
- **Yêu cầu tuyển dụng**: Tạo và quản lý job posting
- **Hồ sơ ứng viên**: Lưu trữ và đánh giá CV
- **Quy trình phỏng vấn**: 
  - Lịch phỏng vấn
  - Biên bản đánh giá
  - Thông báo kết quả qua email

### 💰 Tính lương
- **Chu kỳ lương**: Thiết lập kỳ lương linh hoạt
- **Thành phần lương**: 
  - Lương cơ bản, phụ cấp, thưởng
  - Khấu trừ thuế, bảo hiểm
- **Khen thưởng & Kỷ luật**: Theo dõi lịch sử
- **Báo cáo**: Bảng lương chi tiết, thống kê chi phí

### 🎓 Đào tạo
- **Chương trình đào tạo**: Quản lý khóa học
- **Đăng ký tham gia**: Online enrollment
- **Yêu cầu đào tạo**: Đề xuất từ nhân viên/manager
- **Chứng chỉ**: Theo dõi và gia hạn

### 📄 Quản lý Tài liệu
- **Lưu trữ tài liệu**: Cloud storage integration
- **Phân quyền truy cập**: Role-based access
- **Quy trình phê duyệt**: Workflow engine
- **Version control**: Theo dõi lịch sử thay đổi

### 🔔 Thông báo
- **Real-time notification**: WebSocket
- **Email notification**: Template system
- **Mobile push**: PWA support

### 📈 Thống kê & Báo cáo
- **Dashboard**: Tổng quan KPI
- **Custom reports**: Báo cáo tùy chỉnh
- **Data export**: Excel, PDF, CSV
- **Analytics**: Xu hướng và dự báo

### Quản lý tài khoản
- **Sign up**: Tạo tài khoản nhân viên
- **Sign in**: Đăng nhập
- **Forgot password**: Quên mật khẩu
- **Brute Force Attack**: Ngăn chặn spam dò mật khẩu 

## 📸 Screenshots

| Module | Screenshot |
|--------|-----------|
| Employee Management | ![Employee](ScreenShot/hrm-v3-module_employee_manager.png) |
| Department Structure | ![Department](ScreenShot/hrm-v3-module_department_manager.png) |
| Recruitment Process | ![Recruitment](ScreenShot/hrm-v2-module_recruitment.png) |
| Payroll System | ![Payroll](ScreenShot/hrm-v2-module_payroll.png) |
| Training Management | ![Training](ScreenShot/hrm-v2-module_training_employee.png) |


## Cấu hình hệ thống

```
|__ common/
│   ├── configuration/    
│   │    ├── EmailAsyncConfig.java      # Config Async send Email
│   │    ├── JasperReportsConfig.java   # Config export reports
│   │    ├── RateLimitFilter.java       # Rate Limiting API
│   │    ├── RateLimitService.java
│   │    ├── SwaggerConfig.java         # Config Swagger
│   │    └── WebConfig.java             # Config Cors Mappings
│   ├── exceptions/                     # Handle Exception
│   ├── logging/                        # Logging 
│   ├── redis/                          # Config Redis template and redis key
│   ├── response/                       # Response Api
│   ├── security/                       # Security 
│   ├── service/                        # Service 
│   │    ├── impl/
│   │    ├── FileService.java           # Upload file
│   │    ├── MailService.java           # Send Email
│   │    └── RedisService.java          # Rate Service
│   └── utils/                          # Utils
└── ../
```


## 🛠️ Công nghệ sử dụng

### Backend
- **Java 21** - Latest LTS version
- **Spring Boot 3.x** - Application framework
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework
- **Maven** - Dependency management

### Database & Cache
- **MySQL 8.0** - Primary database
- **Redis** - Caching & session storage
- **JasperReports** - Report generation

### Cloud Services
- **Cloudinary** - Image & file storage
- **SMTP** - Email delivery

### Documentation & Testing
- **Swagger/OpenAPI 3** - API documentation
- **JUnit 5** - Unit testing
- **Testcontainers** - Integration testing

## 📋 Yêu cầu hệ thống

### Phát triển
- **Java**: 21 hoặc cao hơn
- **Maven**: 3.8+
- **MySQL**: 8.0+
- **Redis**: 6.0+ (optional)
- **IDE**: IntelliJ IDEA / Eclipse / VS Code

### Production
- **RAM**: Tối thiểu 2GB, khuyến nghị 4GB+
- **CPU**: 2 cores+
- **Storage**: 20GB+ available space
- **OS**: Linux (Ubuntu 20.04+), Windows Server, macOS


## 🔧 Cài đặt

### Bước 1: Clone repository
```bash
git clone https://github.com/NKhanh0908/hrm.git
cd hrm
```

### Bước 2: Cấu hình database
Tạo database trong MySQL:
```sql
CREATE DATABASE hrm;
```

### Bước 3: Cấu hình ứng dụng
Copy file cấu hình mẫu:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

### Bước 4: Cài đặt dependencies
```bash
mvn clean install
```

### Bước 5: Chạy ứng dụng
```bash
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## ⚙️ Cấu hình

### application.properties
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/hrm
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Cloudinary Configuration (for image storage)
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=
spring.data.redis.database=0
```

### Biến môi trường
Có thể sử dụng biến môi trường cho production:
```bash
export DB_URL=jdbc:mysql://localhost:3306/hrm_db
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export MAIL_USERNAME=your_email@gmail.com
export MAIL_PASSWORD=your_app_password
```

## 📚 API Documentation

### Swagger UI
Sau khi khởi động ứng dụng, truy cập:
```
http://localhost:8080/api/v1/swagger-ui/index.html
```

### Các endpoint chính

#### 🔐 Authentication
```http
POST /auth/signin          # Đăng nhập
POST /auth/signup          # Đăng ký
POST /auth/forgot-password # Quên mật khẩu
POST /auth/reset-password  # Đặt lại mật khẩu
POST /auth/refresh-token   # Làm mới token
```

#### 👤 Employee Management
```http
GET    /employees                    # Danh sách nhân viên
POST   /employees                    # Tạo nhân viên mới
GET    /employees/{id}               # Chi tiết nhân viên
PUT    /employees/{id}               # Cập nhật nhân viên
DELETE /employees/{id}               # Xóa nhân viên
GET    /employees/{id}/contracts     # Hợp đồng của nhân viên
GET    /employees/{id}/attendance    # Lịch sử chấm công
```

#### ⏰ Attendance
```http
POST /attendance/checkin    # Chấm công vào
POST /attendance/checkout   # Chấm công ra
GET  /attendance/today      # Chấm công hôm nay
GET  /attendance/summary    # Tổng hợp chấm công
```

#### 💰 Payroll
```http
GET  /payroll/periods           # Danh sách kỳ lương
POST /payroll/calculate         # Tính lương
GET  /payroll/{id}              # Chi tiết bảng lương
POST /payroll/{id}/approve      # Phê duyệt lương
```


## 📁 Cấu trúc dự án

```
hrm/
├── 📁 src/main/java/com/project/hrm/
│   ├── 🔐 auth/                    # Authentication & Authorization
│   │   ├── configuration/          # Security config, JWT filter
│   │   ├── controller/            # Auth endpoints
│   │   ├── dto/                   # Auth DTOs
│   │   ├── entity/                # Account entity
│   │   ├── service/               # Auth services
│   │   └── util/                  # JWT utils, OTP service
│   │
│   ├── 🌐 common/                 # Shared components
│   │   ├── configuration/         # Global configs
│   │   ├── exception/             # Exception handlers
│   │   ├── response/              # Response wrappers
│   │   ├── security/              # Security utilities
│   │   ├── service/               # Common services
│   │   └── utils/                 # Utility classes
│   │
│   ├── 👥 employee/               # Employee Management
│   │   ├── controller/            # Employee endpoints
│   │   ├── dto/                   # Employee DTOs
│   │   ├── entity/                # Employee entities
│   │   │   ├── Employee.java
│   │   │   ├── Contract.java
│   │   │   ├── Attendance.java
│   │   │   ├── Dependent.java
│   │   │   └── DayOff.java
│   │   ├── service/               # Business logic
│   │   └── repository/            # Data access
│   │
│   ├── 🏢 department/             # Department Management
│   ├── 📋 recruitment/            # Recruitment Process
│   ├── 💰 payroll/                # Payroll System
│   ├── 🎓 training/               # Training Management
│   ├── 📄 document/               # Document Management
│   ├── 🔔 notification/           # Notification System
│   └── 📈 statistics/             # Statistics & Reports
│
├── 📁 src/main/resources/
│   ├── 📁 static/                 # Static files
│   ├── 📁 templates/              # Email templates
│   ├── 📁 reports/                # JasperReport templates
│   ├── 📁 fonts/                  # Custom fonts
│   ├── application.properties     # Main config
│   └── logback-spring.xml         # Logging config
│
├── 📁 src/test/                   # Test files
├── 📁 docs/                       # Documentation
├── 📁 docker/                     # Docker configs
├── 📄 pom.xml                     # Maven config
├── 📄 Dockerfile                  # Docker image
├── 📄 docker-compose.yml          # Docker compose
└── 📄 README.md                   # This file
```


## 🔒 Bảo mật

### Authentication & Authorization
- **JWT Tokens**: Stateless authentication
- **BCrypt**: Password hashing
- **Role-based Access Control**: Fine-grained permissions
- **Multi-factor Authentication**: Optional 2FA

### Security Features
- **Input Validation**: Comprehensive validation
- **SQL Injection Prevention**: Parameterized queries
- **XSS Protection**: Output encoding
- **CSRF Protection**: CSRF tokens
- **Rate Limiting**: API throttling
- **Brute Force Protection**: Account lockout

## 📝 Logging

Ứng dụng sử dụng SLF4J với Logback:
- Log level có thể cấu hình trong `application.properties` và `logback-spring.xml`
- Log files được lưu trong thư mục `logs/`
- Rotation logs theo ngày

## 🧪 Testing
...

## 🚀 Deployment
...

### Docker

- Docker file tại nhánh deloy/docker

#### Mục lục

0. Chuẩn bị
- Cài Docker Desktop (Windows/macOS) hoặc Docker Engine + Docker Compose (Linux). Kiểm tra:

```
    docker --version
    docker-compose --version
```

1. Build image app
- Di chuyển đến thư mục D:\your_path\hrm\hrm
```
    docker build -t hrm-app .
```
+ Nếu muốn tag phiên bản:
```
    docker build -t hrm-app:0.0.1 .
```

2. Chạy đơn lẻ bằng docker run (với --link) — demo / không khuyến nghị cho production
- Trước tiên cần phải tạo thư viện .jar. Trong IntellJi:
    + Chọn Maven -> Folder dự án (ở đây là hrm) -> Lifecycle -> clean (để dọn sạch các file đã chạy)
    + Tiếp tục chọn install. IntellJi sẽ tự động tạo thư viện .jar theo version trong pom.xml 

- Pull images cần thiết
```
    docker pull mysql
    docker pull redis-server
```

- Lưu ý: --link đã deprecated. Dùng được để quick-demo, nhưng tốt hơn nên dùng Docker network hoặc docker-compose.
- Cách dùng --link để kết nối app tới MySQL và Redis đã chạy:
```
    # MySQL container
    docker run -d --name mysql-hrm-app -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=hrm-app -p 3307:3306 mysql:8.0

    # Redis container
    docker run -d --name redis-hrm-app -p 6379:6379 redis:latest
```

- link:
```
    docker run -it \
    --link mysql-hrm-app:mysql \
    --link redis-hrm-app:redis \
    -p 8080:8080 \
    hrm-app
```
3. Chạy toàn bộ (MySQL + Redis + App) bằng docker-compose — khuyến nghị
- Ví dụ docker-compose.yml đầy đủ, phù hợp dev, có volume cho MySQL, network chung, và healthcheck để giảm rủi ro app kết nối trước DB (sử dụng một script wait-for hoặc Spring Retry để đảm bảo app chờ DB chuẩn):
```
version: "3.9"

services:
  mysql-hrm-app:
    image: mysql:8.0
    container_name: mysql-hrm-app
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: hrm-app
      # MYSQL_USER / MYSQL_PASSWORD nếu muốn user non-root
    ports:
      - "3307:3306"      # host:container (host dùng 3307 để tránh trùng local mysql)
    volumes:
      - todo-mysql-data:/var/lib/mysql
    networks:
      - hrm-net
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "127.0.0.1", "-uroot", "-proot"]
      interval: 5s
      timeout: 5s
      retries: 10

  redis-hrm-app:
    image: redis:latest
    container_name: redis-hrm-app
    command: redis-server --save "" --maxmemory 128mb --maxmemory-policy allkeys-lru
    ports:
      - "6379:6379"
    networks:
      - hrm-net
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 5s
      timeout: 3s
      retries: 5

  server:
    build: .
    image: hrm-app
    container_name: server-hrm-app
    restart: on-failure
    ports:
      - "8080:8080"
    depends_on:
      - mysql-hrm-app
      - redis-hrm-app
    environment:
      # Trong docker-compose, app sẽ kết nối bằng tên service: mysql-hrm-app, redis-hrm-app
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-hrm-app:3306/hrm-app?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_REDIS_HOST: redis-hrm-app
      SPRING_REDIS_PORT: 6379
    networks:
      - hrm-net

volumes:
  todo-mysql-data:

networks:
  hrm-net:
    driver: bridge
```

- Sau khi đã chuẩn bị chạy 
```
    docker-compose up --build
```

4. Lệnh kiểm tra & debug
```
    # Xem container đang chạy
    docker ps

    # Xem logs (theo dõi realtime)
    docker-compose logs -f
    docker logs -f server-hrm-app

    # Vào shell container
    docker exec -it server-hrm-app sh  # hoặc bash nếu image có bash

    # Vào mysql cli trong container mysql
    docker exec -it mysql-hrm-app mysql -u root -p
    # (nhập mật khẩu root)

    # Kiểm tra redis từ container app (nếu redis-cli có sẵn)
    redis-cli -h redis-hrm-app ping   # trả về PONG nếu ok
```

## 📊 Monitoring

Khuyến nghị sử dụng:
- Spring Boot Actuator cho health checks
- Micrometer với Prometheus cho metrics
- ELK Stack cho log aggregation

## 🤝 Đóng góp

### Quy trình đóng góp
1. Fork repository
2. Tạo feature branch
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. Commit changes
   ```bash
   git commit -m 'feat: add amazing feature'
   ```
4. Push to branch
   ```bash
   git push origin feature/amazing-feature
   ```
5. Tạo Pull Request

### Commit Convention
Sử dụng [Conventional Commits](https://conventionalcommits.org/):
```
feat: add new feature
fix: bug fix
docs: documentation changes
style: formatting changes
refactor: code refactoring
test: add tests
chore: maintenance tasks
```

### Code Style
- **Java**: Google Java Style Guide
- **Formatting**: IntelliJ default formatter
- **Documentation**: Javadoc for public APIs
- **Testing**: Unit tests for new features

### Review Process
- Tối thiểu 2 reviewers
- Automated tests phải pass
- Code coverage >= 80%
- Documentation cập nhật

### Coding Standards
- Sử dụng Java Code Style chuẩn
- Viết unit tests cho các tính năng mới
- Tuân thủ nguyên tắc SOLID
- Viết javadoc cho public methods


## 📋 Changelog

### [v1.2.0] - 2024-03-01
#### Added
- Multi-tenant support
- Advanced reporting system
- Mobile app API
- SSO integration

#### Fixed
- Performance issues in large datasets
- Memory leaks in file upload
- Time zone handling

#### Changed
- Updated to Spring Boot 3.2
- Improved UI/UX
- Enhanced security

### [v1.1.0] - 2024-02-01
#### Added
- Training management module
- Document management system
- Real-time notifications

#### Fixed
- Payroll calculation bugs
- Email delivery issues

### [v1.0.0] - 2024-01-15
#### Added
- Initial release
- Employee management
- Attendance tracking
- Payroll processing
- Recruitment system

## 🐛 Issues & Support

### Bug Reports
Tạo issue với thông tin:
- **Bug description**: Mô tả chi tiết
- **Steps to reproduce**: Các bước tái tạo
- **Expected behavior**: Kết quả mong đợi
- **Environment**: OS, Java version, browser
- **Screenshots**: Ảnh chụp màn hình (nếu có)

### Feature Requests
- Mô tả tính năng đề xuất
- Use cases cụ thể
- Mockups (nếu có)

### Support Channels
- **GitHub Issues**: Bug reports & feature requests
- **Discussions**: General questions
- **Email**: Urgent support requests

## 📞 Liên hệ

- **Email**: support@hrm-project.com
- **Website**: https://hrm-project.com
- **Documentation**: https://docs.hrm-project.com
- **Demo**: https://demo.hrm-project.com

### Team
- **Lead Developer**: [Your Name](mailto:developer@hrm-project.com)
- **Product Manager**: [PM Name](mailto:pm@hrm-project.com)
- **DevOps Engineer**: [DevOps Name](mailto:devops@hrm-project.com)

## 📄 Giấy phép

Dự án này được phát hành dưới [MIT License](LICENSE).

```
MIT License

Copyright (c) 2024 HRM Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

<div align="center">
  <p><strong>⭐ Nếu project hữu ích, hãy cho chúng tôi một star nhé! ⭐</strong></p>
  <p>Được phát triển với ❤️ bởi <strong>HRM Team</strong></p>
</div>