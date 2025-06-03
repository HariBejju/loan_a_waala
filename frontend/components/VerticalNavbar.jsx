import React, { useState } from "react" 
import { useNavigate } from "react-router-dom" 
import { fetchNotificationCount } from "../api/NotificationCountapi"; 
import "../styles/VerticleNavbar.css" 
 
/** 
 *  VerticleNavbar Component 
 * This component renders a **vertical navigation bar** with links to different pages. 
 * It also provides a logout option that clears local storage and redirects to the login page. 
 */ 
function VerticleNavbar() { 
  // Handles navigation within the app 
  const navigate = useNavigate(); 
  const [notificationCount, setNotificationCount] = useState(); 
 
  const getCount = async () => { 
    const count = await fetchNotificationCount(localStorage.getItem('userId')) 
    setNotificationCount(count); 
  } 
  getCount(); // This will make sure the count is set 
 
  const click = (e) => { 
    e.preventDefault(); 
    // Show confirmation popup 
    const confirmLogout = window.confirm("Are you sure you want to logout?"); 
 
    if (confirmLogout) { 
      localStorage.clear(); 
      sessionStorage.clear(); 
      navigate("/login"); 
    } else { 
      console.log("Logout canceled."); 
    } 
  }; 
 
  return ( 
    <div className="h-auto d-inline-block"> 
      <div class="vertical-navbar"> 
        <ul class="nav flex-column"> 
          <li class="nav-item"> 
            <a class="nav-link" href="/dashboard">Dashboard</a> 
          </li> 
          <li class="nav-item"> 
            <a class="nav-link" href="/add-loan">Add Loan</a> 
          </li> 
          <li class="nav-item"> 
            <a class="nav-link" href="/profile">Profile</a> 
          </li> 
          <li class="nav-item"> 
            <a class="nav-link" href="/history">Action History</a> 
          </li> 
          <li className="nav-item"> 
            <a className="nav-link" href="/notifications">Notifications ({notificationCount})</a> 
          </li> 
          <li class="nav-item"> 
            <a class="nav-link" onClick={click}>Logout</a> 
          </li> 
        </ul> 
      </div> 
    </div> 
  ) 
} 
 
export default VerticleNavbar; 
Front-end Packages 
- antd 
- axios 
- bootstrap 
- chart.js 
- date-fns 
- html2canvas 
- jspdf 
- jspdf-autotable 
- react 
- react-bootstrap-icons 
- react-chartjs-2 
- react-dom 
- react-router-dom 
- zxcvbn