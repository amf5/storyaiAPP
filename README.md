
# Story AI 🎬🤖

Story AI is an AI-powered platform that transforms user-written stories into animated cartoon videos using machine learning models.

---

## 🚀 Features

- Convert text stories into animated videos
- AI-powered cartoon generation
- Secure authentication with JWT
- Real-time progress updates using WebSocket
- Asynchronous notifications using Kafka
- Cloud-based media storage
- Automated expiration handling with Scheduler

---

## 🛠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- Spring WebFlux
- Spring AOP

### Database
- MySQL
- JPA
- Hibernate

### AI & Integration
- FastAPI
- Machine Learning Model Integration

### Messaging & Real-Time
- Apache Kafka
- WebSocket

### Cloud & Media
- Cloudinary

### Security
- JWT Authentication

---

## 🏗 Architecture

The system is designed using modular layered architecture:

- Authentication Layer
- API Layer
- Service Layer
- AI Integration Layer
- Messaging Layer
- Persistence Layer

The architecture improves:
- Scalability
- Maintainability
- Performance
- Testability

---

## 🔐 Authentication & Security

- JWT-based authentication
- Spring Security authorization
- Secure API access

---

## ⚡ Core Functionalities

### AI Story Processing
- User submits a story
- Backend sends request to ML model via FastAPI
- AI generates cartoon scenes and videos

### Non-Blocking Communication
- WebFlux used for asynchronous communication with AI services

### Real-Time Updates
- WebSocket used to notify users about video generation progress

### Media Management
- Images and generated videos stored in Cloudinary

### Notifications
- Kafka used for asynchronous notifications

### Logging & Monitoring
- Spring AOP used for:
  - Logging
  - Error monitoring
  - Performance tracing

### Scheduled Tasks
- Automatic temporary code expiration using Spring Scheduler

---

## 🗄 Database

- MySQL relational database
- JPA & Hibernate for ORM
- Structured relational schema design

---

## 📦 Future Improvements

- Voice generation
- Multi-language support
- AI character customization
- Mobile application
- Video editing dashboard

---

## 👨‍💻 Author

Ahmed Walid Amin
