import React, { useState } from "react"; 
import { useNavigate } from "react-router-dom"; 
import axios from "axios"; 
import Logo from "../assets/Logo.jsx"; 
import "../styles/Login.css"; 
 
/* 
    Login Component 
    This React component provides a login form for users to authenticate using their email  
    and password. It interacts with an API to verify credentials, stores user information  
    in localStorage, and navigates to the dashboard upon successful login. 
*/ 
 
function Login() { 
    const [errorMessage, setErrorMessage] = useState(""); 
    const [successMessage, setSuccessMessage] = useState(""); 
    const navigate = useNavigate(); 
 
    const goBack = () => { 
        navigate('/home'); // Navigates to the previous page 
    }; 
 
    //  Handles user login by sending email and password to the backend for validation 
    const click = async (event) => { 
        event.preventDefault(); 
        try { 
            // Get input values 
            const email = document.getElementById("username").value; 
            document.getElementById("username").value = ""; 
            const passwordHash = document.getElementById("password").value; 
            document.getElementById("password").value = ""; 
            // Send login request to backend 
            const response = await axios.post("http://localhost:8090/auth/login", { email, passwordHash }); 
 
            // Extract user data from response 
            const { userId, firstName, lastName, token, panCard } = response.data; 
 
            // Store user details in local storage 
            localStorage.setItem("userId", userId); 
            localStorage.setItem("firstName", firstName); 
            localStorage.setItem("lastName", lastName); 
            localStorage.setItem("token", token); 
            localStorage.setItem("panCard", panCard); 
            localStorage.setItem("emailId", email); 
 
            // Global date format 
            localStorage.setItem("dateFormat", "yyyy/MM/dd"); 
 
            // Show success message and redirect to dashboard 
            setSuccessMessage("Login successful! Redirecting..."); 
            setTimeout(() => { 
                navigate("/dashboard"); 
            }, 3000); 
        } catch (err) { 
            // Handle different error responses 
            if (err.response) { 
                if (err.response.status === 401) { 
                    setErrorMessage("Invalid credentials. Please try again."); 
                } else if (err.response.status === 500) { 
                    setErrorMessage("An unexpected error occurred. Please try again later."); 
                } else { 
                    setErrorMessage("An error occurred. Please try again."); 
                } 
            } else { 
                setErrorMessage("An error occurred. Please try again."); 
            } 
 
            // Clear error message after 3 seconds 
            setTimeout(() => { 
                setErrorMessage(""); 
            }, 3000); 
        } 
    }; 
 
    return ( 
        <div className="login-page"> 
            {/*  Left section: Displays company logo and tagline */} 
            <div className="left-section"> 
                <h3 className="company-quote p-5">"Empowering dreams, simplifying loans—Loan-A-Wala is your trusted companion in financial management."</h3> 
                <Logo color="#000" width="400" height="400" /> 
                <h2 className="company-name">LOAN-A-WALA</h2> 
            </div> 
 
            {/*  Right section: Login form */} 
            <div className="right-section"> 
 
                <div className="login-container"> 
 
                    {/* Display error or success messages */} 
                    {errorMessage && <p className="error-message">{errorMessage}</p>} 
                    {successMessage && <p className="success-message">{successMessage}</p>} 
                    <h2>Login</h2> 
                    <form action="/login" method="post" onSubmit={click}> 
                        {/*  Email input field */} 
                        <div className="mb-3"> 
                            <label className="form-label" htmlFor="username">Email</label> 
                            <input className="form-control" type="text" id="username" name="username" required /> 
                        </div> 
                        {/*  Password input field */} 
                        <div className="mb-3"> 
                            <label className="form-label" htmlFor="password">Password</label> 
                            <input className="form-control" type="password" id="password" name="password" required /> 
                        </div> 
                        {/*  Submit button */} 
                        <button type="submit" className="mb-3">Login</button> 
                        {/*  Link to registration page for new users */} 
                        <div className="d-flex justify-content-center"> 
                            New user?&nbsp;<a href="/signup">Register</a> 
                        </div> 
                    </form> 
                </div> 
            </div> 
        </div> 
    ); 
} 
 
export default Login;