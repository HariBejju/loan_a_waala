# Loanawala - Loan Management System

Loanawala is a full-stack loan management system developed using **Java (Spring Boot)** for the backend, **MySQL** for database management, and **React.js** for the frontend. This project was built during my internship and is designed to help users manage loans efficiently — from adding loans and calculating EMI, to tracking repayment history and generating reports.

---

## 🚀 Features

### 🔐 Authentication & User Management

* Secure login using JWT
* LocalStorage-based session management on frontend

### 🧾 Loan Management

* Add new loans using PAN number
* View, edit, and manage loan details
* EMI calculation with interest and principal breakdown
* Repayment tracking and status updates

### 📊 Dashboard & Visualization

* Summary cards: Active loans, total pending amount, next due date
* Filter & sort loans by due date, interest, principal
* Visualize monthly payments using Pie and Bar charts
* Notification alerts for due dates

### 📁 Repayment History & Schedule

* Auto-generated EMI schedule using formula-based calculation
* Track each repayment with breakdown
* Export schedule & charts as downloadable PDF

### 🧠 Tech Stack

#### Backend

* Java 17
* Spring Boot
* Spring Security (JWT-based Auth)
* MySQL
* Hibernate (JPA)

#### Frontend

* React.js
* JSX, CSS Modules
* Chart.js
* Axios for HTTP calls

---



## ⚙️ How to Run

### Backend

```bash
cd backend
mvn clean install
java -jar target/Loanawala-0.0.1-SNAPSHOT.jar
```

* Ensure MySQL is running
* DB config can be changed in `application.properties`

### Frontend

```bash
cd frontend
npm install
npm start
```

Access the app at `http://localhost:3000`

---

## 🔐 Credentials & Environment Setup

* Configure `application.properties` with your DB credentials
* Set JWT\_SECRET and other sensitive data using environment variables
* Frontend communicates via `http://localhost:8090`

---

## 🧠 Learning & Challenges

* Dynamic EMI calculations based on changing balances
* Managing dual states: React local + persistent storage
* Modular architecture separating UI/API/Auth logic cleanly

---


---

## 📄 License

This repository contains only a **demo version** of the Loanawala app, rebuilt using my own logic and open-source stack. No proprietary code or sensitive business data is included.
