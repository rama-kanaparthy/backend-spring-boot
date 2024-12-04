# Backend SpringBoot Demo Application

## Overview
A backend application built using **Spring Boot** for [Practice Spring boot]. The application provides a RESTful API to manage and serve resources for the frontend or external clients.

## Dependencies
- Spring Web
- Spring Data JPA
- H2 Database (for an in-memory database)

## Features
- User Authentication (JWT-based)
- CRUD Operations for Products
- Search functionality
- Integration with third-party APIs

## Technologies Planning to Use
- **Java** - Main programming language
- **Spring Boot** - Application framework
- **Spring Data JPA** - For database interactions
- **Spring Security** - For authentication and authorization
- **H2/MySQL/PostgreSQL/MongoDB** - Database
- **Swagger** - API documentation
- **JUnit & Mockito** - Testing framework

## Getting Started

### Prerequisites
- **Java 17** or higher
- **Maven 3.6+**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/rama-backend/backend-spring-boot.git
   cd backend-spring-boot

### H2
- **http://localhost:8080/h2-console**

### Run RabbitMQ with Docker
- **docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management**
   
### Cache
- **Spring boot starter cache**
