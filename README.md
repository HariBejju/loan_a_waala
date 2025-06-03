# Loanawala - Loan Management System

Loanawala is a full-stack web application built to manage and track loans efficiently. Developed during my internship, this project simulates a complete loan management workflow — from adding loans, calculating repayments, tracking history, to managing users. Built with a focus on usability, real-world logic, and secure architecture.

---

## 🚀 Features

### Frontend

* **Dashboard** for loan summary and quick stats
* **Add Loan** form with validation
* **EMI Calculator** for monthly repayment estimates
* **Chart Section** to visualize repayment trends
* **Action History** to track user operations

### Backend

* RESTful API built with Node.js/Express
* JWT-based authentication
* MongoDB for storing loan and user data
* Routes for loans, authentication, and actions
* Validations and error handling

---

## 🧑‍💻 Tech Stack

### Frontend

* React.js (JSX components)
* CSS3 (custom styling)
* Chart.js or similar for graphs

### Backend

* Node.js
* Express.js
* MongoDB (via Mongoose)
* JWT for Auth

---

## 📂 Project Structure

```
Loanawala/
├── frontend/
│   ├── components/       # React components (Dashboard, AddLoan, etc.)
│   ├── styles/           # CSS files
│   └── assets/           # Static files/images
├── backend/
│   ├── routes/           # API routes
│   ├── models/           # Mongoose models
│   ├── controllers/      # Business logic
│   ├── services/         # Utility functions
│   ├── middlewares/      # Auth & error handling
│   └── utils/
```

---

## 📦 How to Run

### Prerequisites

* Node.js and npm
* MongoDB installed locally or via cloud (MongoDB Atlas)

### Backend

```bash
cd backend
npm install
npm start
```

### Frontend

```bash
cd frontend
npm install
npm start
```

Visit `http://localhost:3000` to view the app.

---

## 🧠 Future Improvements

* Email notifications for due dates
* Role-based user access
* Dark mode / UI enhancements

---

---

## 📄 License

This project is for educational and demo purposes. Do not redistribute proprietary code.
