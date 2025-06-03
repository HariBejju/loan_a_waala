import React, { useEffect, useState } from 'react'; 
import { useNavigate } from "react-router-dom"; 
import Logo from '../assets/Logo'; 
import * as Icons from 'react-bootstrap-icons'; 
import 'bootstrap/dist/js/bootstrap.bundle.min.js' 
 
//  Header Component 
//  Displays the navigation bar with branding and user details 
//  Shows user profile info and enables profile navigation 
//  Dynamically updates user details when local storage changes 
 
function Header() { 
  //  Retrieve user details from local storage 
  const [userId, setUserId] = useState(localStorage.getItem('userId')); 
  const [firstName, setFirstName] = useState(localStorage.getItem('firstName')); 
  const [lastName, setLastName] = useState(localStorage.getItem('lastName')); 
 
  const navigate = useNavigate(); 
 
  //  Effect to update user details when local storage changes 
  useEffect(() => { 
    const handleStorageChange = () => { 
      setFirstName(localStorage.getItem('firstName')); 
      setLastName(localStorage.getItem('lastName')); 
    }; 
 
    window.addEventListener('storage', handleStorageChange); 
 
    return () => { 
      window.removeEventListener('storage', handleStorageChange); 
    }; 
  }, []); 
  return ( 
    <div className="sticky-top"> 
      {/*  Navbar component */} 
      <nav className="navbar navbar-expand-lg bg-body-tertiary p-3"> 
        <div className=" container-fluid "> 
 
          {/*  Branding */} 
          <a className="navbar-brand" href="/home"><Logo color='black' />LOAN-A-WALA</a> 
 
          {/*  Responsive navbar toggle button */} 
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"> 
            <span className="navbar-toggler-icon"></span> 
          </button> 
 
          {/*  User Profile Dropdown */} 
          <div className='nav-item dropdown ms-auto'> 
            <div 
              className="d-flex flex-row align-items-center nav-link" 
              style={{ backgroundImage: 'none', cursor: 'pointer' }} 
              onClick={() => navigate("/profile")} //  Navigate to profile on click 
            > 
              <Icons.Person size="2.5em" /> 
              <div style={{ paddingInlineStart: '0.5em' }}> 
                <p style={{ margin: '0' }}>Hello, {firstName + " " + lastName}</p> 
                <p style={{ margin: '0' }}>{userId}</p> 
              </div> 
            </div> 
          </div> 
        </div> 
      </nav> 
    </div> 
  ); 
} 
 
export default Header;