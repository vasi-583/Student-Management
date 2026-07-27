# 🎓 Student Management System (REST API)

A robust **Spring Boot RESTful API** for managing student records, built with **JWT (JSON Web Token) Authentication**, **Spring Security**, and **MySQL** database persistence.

---

## ✨ Features

- 🔐 **Authentication & Authorization**:
  - User Registration (`/api/auth/signup`)
  - Secure Login with BCrypt password hashing & JWT generation (`/api/auth/login`)
  - Stateless authentication via custom Spring Security `JwtFilter`
- 📚 **Student Management (CRUD)**:
  - Retrieve all students
  - Fetch student by ID
  - Create new student records
  - Update existing student information
  - Delete student records
- 🗄️ **Database Persistence**: MySQL integration using Spring Data JPA / Hibernate.

---

## 🛠️ Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.2.5
- **Security:** Spring Security 3.4.3 & JJWT 0.12.5
- **ORM / Database:** Spring Data JPA, MySQL Connector 9.1.0
- **Build Tool:** Apache Maven

---

## 📂 Project Structure

```text
com.vasi
├── StudentManagementApplication.java  # Main Application Entry Point
├── config/
│   └── SecurityConfig.java            # Security rules & CORS configuration
├── security/
│   ├── JwtUtil.java                   # Token generation & validation utility
│   └── JwtFilter.java                 # HTTP request interceptor for JWT authorization
├── controller/
│   ├── AuthController.java            # Signup and Login endpoints
│   └── StudentController.java         # Student CRUD API endpoints
├── model/
│   ├── User.java                      # User Entity (id, username, password, role)
│   └── Student.java                   # Student Entity (id, name, course, email)
└── repository/
    ├── UserRepository.java            # JPA Repository for Users
    └── StudentRepository.java         # JPA Repository for Students
```

---

## 🚀 API Endpoints

### 🔑 Authentication Endpoints (`/api/auth`)

| Method | Endpoint          | Access | Description                |
| :----- | :---------------- | :----- | :------------------------- |
| `POST` | `/api/auth/signup` | Public | Register a new user        |
| `POST` | `/api/auth/login`  | Public | Authenticate user & get JWT token |

#### Example Login Request Body:
```json
{
  "username": "john_doe",
  "password": "secretpassword"
}
```

---

### 👨‍🎓 Student Endpoints (`/api/students`)

> Note: Include `Authorization: Bearer <your_jwt_token>` header for protected endpoints.

| Method   | Endpoint             | Description               |
| :------- | :------------------- | :------------------------ |
| `GET`    | `/api/students`      | Get all students          |
| `GET`    | `/api/students/{id}` | Get student details by ID |
| `POST`   | `/api/students`      | Add a new student         |
| `PUT`    | `/api/students/{id}` | Update student details    |
| `DELETE` | `/api/students/{id}` | Delete a student by ID    |

#### Example Student JSON Body:
```json
{
  "name": "Alex Smith",
  "course": "Computer Science",
  "email": "alex.smith@example.com"
}
```

---

## ⚙️ Getting Started & Setup

### Prerequisites
- **JDK 17** or higher installed
- **Maven** installed
- **MySQL Database** server running

### 1. Database Configuration
Make sure your MySQL database is created (e.g., `student_db`), and update your `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 2. Run the Application
In your terminal / Git Bash, navigate to the project directory and run:

```bash
mvn spring-boot:run
```

The application will start on **`http://localhost:8080`**.

---

## 📝 License
This project is open-source and available under the MIT License.
