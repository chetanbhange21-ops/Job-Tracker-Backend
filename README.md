# 📋 Job Application Tracker API
**Backend Project by Chetan Bhange**  
Built with: Java 17 | Spring Boot 3.2 | MySQL | JWT Authentication | REST APIs

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- MySQL running locally
- Maven

### Steps
```bash
# 1. Clone / open the project in IntelliJ IDEA

# 2. Create MySQL database
CREATE DATABASE job_tracker;

# 3. Update application.properties with your MySQL password
spring.datasource.password=your_mysql_password

# 4. Run the app
mvn spring-boot:run

# App starts at: http://localhost:8080
```

---

## 🔗 API Endpoints

### 🔐 Auth (No token needed)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login and get JWT token |

### 📝 Job Applications (JWT token required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/applications | Add new job application |
| GET | /api/applications | Get all applications |
| GET | /api/applications?status=APPLIED | Filter by status |
| GET | /api/applications?company=Google | Filter by company |
| GET | /api/applications/{id} | Get single application |
| PUT | /api/applications/{id} | Update application |
| DELETE | /api/applications/{id} | Delete application |
| GET | /api/applications/stats | Get stats & success rate |

---

## 📬 Postman Examples

### 1. Register
```
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "name": "Chetan Bhange",
  "email": "chetan@example.com",
  "password": "password123"
}
```

### 2. Login → Copy the token from response!
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "chetan@example.com",
  "password": "password123"
}
```

### 3. Add Application (add token in Authorization: Bearer <token>)
```
POST http://localhost:8080/api/applications
Authorization: Bearer eyJhbGci...
Content-Type: application/json

{
  "companyName": "Google",
  "jobRole": "Backend Developer",
  "status": "APPLIED",
  "location": "Bangalore",
  "jobUrl": "https://careers.google.com/jobs/123",
  "notes": "Applied via LinkedIn",
  "appliedDate": "2026-06-19"
}
```

### 4. Get Stats
```
GET http://localhost:8080/api/applications/stats
Authorization: Bearer eyJhbGci...
```

**Response:**
```json
{
  "totalApplications": 10,
  "applied": 5,
  "interviewScheduled": 2,
  "interviewDone": 1,
  "offerReceived": 1,
  "rejected": 1,
  "withdrawn": 0,
  "successRate": 10.0
}
```

---

## 📊 Application Status Flow

```
APPLIED → INTERVIEW_SCHEDULED → INTERVIEW_DONE → OFFER_RECEIVED
                                              ↘ REJECTED
```

---

## 🛠 Tech Stack (for your resume)
- **Language:** Java 17
- **Framework:** Spring Boot 3.2
- **Database:** MySQL with Spring Data JPA
- **Security:** Spring Security + JWT Authentication
- **Build Tool:** Maven
- **Testing:** Postman

---

## 📝 Resume Description (copy this!)

**Job Application Tracker API** | Java, Spring Boot, MySQL, JWT | Jun 2026
- Built a REST API to track job applications with full CRUD operations using Spring Boot and MySQL
- Implemented JWT-based authentication with Spring Security for secure, stateless user sessions
- Designed relational database schema with JPA/Hibernate for User and JobApplication entities
- Added filtering by company/status and a statistics endpoint showing application success rate
- Tested all endpoints with Postman; application auto-creates MySQL schema on startup
