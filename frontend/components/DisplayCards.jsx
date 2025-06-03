import { useState } from "react"; 
import { Alert } from "antd"; 
import "../styles/DisplayCards.css"; 
import * as Icons from 'react-bootstrap-icons'; 
 
/* 
 * This component renders a **card-style display** for loan-related information. 
 * It accepts props to dynamically show details like messages, icons, and values. 
 */ 
function DisplayCards({ msg, value, icon, color }) { 
  //  Manages potential error messages 
  const [error, setError] = useState(null); 
  //  Dynamically selects the correct icon component based on `icon` prop 
  const IconComponent = Icons[icon]; 
 
  return ( 
    <div className="pt-4"> 
      {/*  Card Display for Loan Information */} 
      <div className="card p-3 m-3" style={{ width: '300px', backgroundColor: color }}> 
        {/*  Show error alert if any issue occurs */} 
        {error && <Alert message={error} type="error" />} 
        {/*  Display the selected icon if available */} 
        {IconComponent ? <IconComponent size={53} /> : null} 
        {/*  Display value and description */} 
        <h4 style={{ paddingTop: "10px" }}>{value}</h4> 
        <p style={{ fontWeight: "bold" }}>{msg}</p> 
 
      </div> 
    </div> 
  ); 
} 
 
export default DisplayCards;