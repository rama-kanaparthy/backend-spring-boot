Backend SpringBoot Demo Application
===================================
## Overview
>A backend application built using **Spring Boot** for [Practice Spring boot]. The application provides a RESTful API to manage and serve resources for the frontend or external clients.

## Dependencies
- Spring Web
- Spring Data JPA
- H2 Database (for an in-memory database)
- Spring Security
- Json Web Token
- Liquibase
- Lombok
- RabbitMQ
- Spring Cache
- WebFlux

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
>- **http://localhost:8080/h2-console**

### Run RabbitMQ with Docker
>- **docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management**
   
### Cache
>- **Spring boot starter cache**

### GraphQL
>- **Install graphql plugin**
### Get a Single Payment

To fetch a specific payment by `paymentId`, use the following query:
```
#graphql
query {
  getPayment(paymentId: "1") {
    paymentId
    amount
    currency
    status
  }
}
### Get All Payments

To fetch a list of all payments, use the following query:

graphql
query {
  getAllPayments {
    paymentId
    amount
    currency
    status
  }
}

### Process Payment

To create a new payment, use the following mutation:

graphql
mutation {
  processPayment(input: { amount: 100.0, currency: "USD", description: "Payment for order #123" }) {
    success
    message
    payment {
      paymentId
      amount
      currency
      status
    }
  }
}
```

## Run this command from the directory containing the Dockerfile:
```
docker build -t spring-boot-app .
This command will create a Docker image named spring-boot-app.

Start a container using the image:
docker run -p 8080:8080 spring-boot-app
```