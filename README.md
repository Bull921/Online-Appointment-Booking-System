Healthcare Online Appointment Booking System – Microservices Architecture

A scalable Healthcare Management System built using Spring Boot microservices, designed to manage doctor scheduling, patient appointments, prescriptions, and notifications efficiently. The system ensures secure, fault-tolerant, and event-driven communication between services.

🚀 Tech Stack

Backend: Spring Boot, Spring Cloud (Eureka, Gateway, Config)

Security: Spring Security, JWT

Messaging: Apache Kafka

Resilience: Resilience4j (Circuit Breaker, Retry)

Database: JPA, MySQL

Deployment: Docker, Docker Compose

Documentation: Swagger / OpenAPI

📌 Microservices Included

Patient Service – Manages user registration, authentication, roles

Doctor Service – Handles doctor profiles, availability, scheduling

Appointment Service – Books and manages patient appointments

Prescription Service – Manages prescriptions linked to appointments

Notification Service – Sends notifications using Kafka events

📘 Project Overview (Short Description)

A distributed Healthcare Management System enabling appointment booking, doctor scheduling, and prescription management using Spring Boot microservices with Kafka-based asynchronous communication, JWT security, and resilient API interactions.

🔄 Architecture Highlights

Eureka Server for service discovery

API Gateway for centralized routing + security

Kafka Topics for event-driven communication

Config Server for centralized property management

Dockerized services for easy deployment

🏆 Key Features

Secure login with JWT Authentication

Real-time notifications via Kafka

Circuit breaker & retries using Resilience4j

Fully documented APIs using Swagger

Optimized database design with JPA mappings

📂 How to Run (Local Setup)

Clone the repository

Start Eureka, Config Server, Kafka/Zookeeper

Start each microservice

Access APIs via Gateway:

http://localhost:6000/
