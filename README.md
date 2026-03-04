# Student Nexus System

A **Java backend system** simulating a university student portal similar to systems used in real universities (ITS, Banner, PeopleSoft).

This project demonstrates **clean backend architecture**, **database design**, and **secure authentication** using Java and MySQL.

---

# Features

### Student System

* Student application workflow
* Student login with secure PIN
* Student profile management
* Department transfer requests

### Admin System

* Admin authentication
* Approve or reject student applications
* Deactivate student accounts
* Approve or reject transfer requests
* View students by faculty and department

### Security

* BCrypt password hashing
* PreparedStatement protection against SQL injection

---

# System Architecture

The project follows a **layered backend architecture**:

```
Console UI (StudentManagementSystem)
        ↓
Service Layer
        ↓
DAO Layer
        ↓
MySQL Database
```

### Packages

```
student.nexus.main      → Application entry point
student.nexus.service   → Business logic
student.nexus.dao       → Database operations
student.nexus.model     → Entity classes
student.nexus.util      → Utility classes
```

---

# Database Design

Tables used:

* users
* students
* faculties
* departments
* transfer_requests
* notifications

Example relationships:

```
Faculty
   ↓
Department
   ↓
Student
```

---

# Security

* BCrypt hashing for passwords
* PreparedStatements for SQL injection prevention
* Role-based authentication

---

# Student Lifecycle

```
PENDING → ACTIVE → DEACTIVATED / REJECTED
```

---

# Example Console Output

```
=== PUBLIC MENU ===
1. Create Admin
2. Admin Login
3. Student Application
4. Student Login
5. Exit
```

---

# Technologies

* Java
* Maven
* JDBC
* MySQL
* BCrypt
* IntelliJ IDEA

---

# Author

**Alpha Badibanga**
Information Technology Student
Tshwane University of Technology


System Architecture Diagram
              +-----------------------+
              |   StudentManagement   |
              |        System         |
              +-----------+-----------+
                          |
                          v
              +-----------------------+
              |      Service Layer    |
              |  Student / User /     |
              |      FacultyService   |
              +-----------+-----------+
                          |
                          v
              +-----------------------+
              |        DAO Layer      |
              | StudentDAO / UserDAO  |
              | TransferRequestDAO    |
              +-----------+-----------+
                          |
                          v
              +-----------------------+
              |      MySQL Database   |
              +-----------------------+


