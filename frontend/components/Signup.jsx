import React, { useState, useRef } from "react"; 
import { useNavigate } from "react-router-dom"; 
import axios from "axios"; 
import zxcvbn from "zxcvbn" 
import "bootstrap/dist/css/bootstrap.min.css";  
import "../styles/Signup.css" 
 
/* 
    Signup Component 
    This React component provides a user registration form, allowing users to enter personal  
    details, contact information, identity proof, and address details. It validates inputs,  
    handles form submission to the backend, and navigates to the login page upon successful signup. 
*/ 
 
function Signup() { 
  const [formData, setFormData] = useState({ 
    firstName: "", 
    lastName: "", 
    userId: "", 
    email: "", 
    passwordHash: "", 
    phoneNumber: "", 
    alternatePhoneNumber: "", 
    alternateEmail: "", 
    dateOfBirth: "", 
    panCard: "", 
    aadharCard: "", 
    residentialAddress: "", 
    permanentAddress: "", 
    city: "", 
    state: "", 
    pincode: "" 
  }); 
 
  const [strengthError, setStrengthError] = useState("") 
  const [errorMessage, setErrorMessage] = useState(""); 
  const [isWarnings, setWarnings] = useState(false) 
  const [emailError, setEmailError] = useState(""); 
  const [phoneError, setPhoneError] = useState(""); 
  const [alternatePhoneError, setAlternatePhoneError] = useState(""); 
  const [passwordError, setPasswordError] = useState(""); 
  const [altEmailError, setAltEmailError] = useState(""); 
  const [panError, setPanError] = useState(""); 
  const [aadharcardError, setAadharCardError] = useState(""); 
  const [isSameAddress, setIsSameAddress] = useState(false); 
  const [permanentAddress, setPermanentAddress] = useState(''); 
  const [residentialAddress, setResidentialAddress] = useState(''); 
  const [sameAddress, setSameAddress] = useState(false); 
  const [isReadonly, setIsReadonly] = useState(false); 
 
  const phoneRef = useRef(null); 
  const passwordRef = useRef(null); 
  const altEmailRef = useRef(null); 
  const navigate = useNavigate(); 
 
  // Handle input change 
  const handleChange = (event) => { 
    const { name, value } = event.target; 
    setFormData({ ...formData, [name]: value }); 
  }; 
 
  // Validate email format 
  const validateEmail = (email) => { 
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; 
    return emailRegex.test(email); 
  }; 
 
  //check whether email and alternate email are same 
  const checkEmail = () => { 
    if (formData.email == formData.alternateEmail) { 
      setAltEmailError("Alternate email should be different") 
      setWarnings(true) 
    } else { 
      setAltEmailError("") 
      setWarnings(false) 
    } 
  } 
 
  //validate aadhar format 
  const validateAadhar = () => { 
    const regex = /^[2-9]{1}[0-9]{11}$/; 
    if (!regex.test(formData.aadharCard)) { 
      setAadharCardError("Invalid Aadhar Card format") 
      setWarnings(true) 
    } else { 
      setAadharCardError("") 
      setWarnings(false) 
    } 
  } 
 
  //permanent address 
  const handlePermanentAddressChange = (e) => { 
    const val = document.getElementById("residentialAddress").value 
    setPermanentAddress(val) 
    formData.permanentAddress = val 
    setIsSameAddress(e.target.checked); 
    document.getElementById("permanentAddress").readOnly = e.target.checked; 
 
  }; 
 
  //checkbox for permanent address 
  const handleCheckboxChange = () => { 
    setSameAddress(!sameAddress); 
    if (!sameAddress) { 
      setResidentialAddress(permanentAddress); 
    } else { 
      setResidentialAddress(''); 
    } 
  }; 
 
  // Validate phone number length 
  const validatePhoneNumber = () => { 
 
    if (formData.phoneNumber.length != 10) { 
      setPhoneError("Phone number should be 10 digits") 
      setWarnings(true) 
      phoneRef.current.focus(); 
    } else { 
      setPhoneError("") 
      setWarnings(false) 
    } 
  }; 
 
  //validate alternate phone number 
  const validateAlternatePhoneNumber = () => { 
    if (formData.alternatePhoneNumber.length != 10) { 
      setAlternatePhoneError("Phone number should be 10 digits") 
      setWarnings(true) 
    } else { 
      setAlternatePhoneError("") 
      setWarnings(false) 
    } 
    if (formData.phoneNumber == formData.alternatePhoneNumber) { 
      setAlternatePhoneError("Alternate phone number and phone number should not be same") 
      setWarnings(true) 
    } else { 
      setAlternatePhoneError("") 
      setWarnings(false) 
    } 
  }; 
 
  // Validate password with confirm password 
  const validatePassword = () => { 
    let cnfrm_pwd = document.getElementById("cnfrm_pwd").value 
    if (formData.passwordHash != cnfrm_pwd) { 
      setPasswordError("Passwords do not match."); 
      setWarnings(true) 
    } else { 
      setPasswordError("") 
      setWarnings(false) 
    } 
  } 
 
  //check for password strength 
  const validateStrength = () => { 
    const result = zxcvbn(formData.passwordHash); 
    if (result.score != null && result.score < 3) { 
      setWarnings(true) 
      setStrengthError("Password should be strong"); 
    } else { 
      setStrengthError("") 
      setWarnings(false) 
    } 
  } 
 
  // Validate Pan card number 
  const validatePan = () => { 
    const regex = /^[A-Z]{5}[0-9]{4}[A-Z]$/; 
    if (!regex.test(formData.panCard)) { 
      setPanError("Invalid PAN card format") 
      setWarnings(true) 
    } else { 
      setPanError("") 
      setWarnings(false) 
    } 
  } 
 
  // Handle form submission 
  const handleSubmit = async (event) => { 
    event.preventDefault(); 
    if (isWarnings) { 
      setErrorMessage(" Error while registering") 
    } else { 
      try { 
        const response = await axios.post("http://localhost:8090/auth/register", formData); 
        console.log(response.data); 
        navigate("/login"); 
      } catch (err) { 
        if (err.response) { 
          const responseMessage = err.response.data; 
          if (responseMessage === "User Already Exist") { 
            setErrorMessage("User already exists. Please use a different email."); 
          } else if (responseMessage === "Username Already Exist") { 
            setErrorMessage("Username already exists. Please choose a different username."); 
          } else { 
            setErrorMessage("An error occurred. Please try again."); 
          } 
          setTimeout(() => setErrorMessage(""), 3000); 
        } 
      } 
    }; 
  } 
 
 
  return ( 
    <div className="container my-5 d-flex justify-content-center"> 
      <div className="card p-4 shadow-lg" style={{ minWidth: "30em" }}> 
        {errorMessage && <p className="text-danger text-center">{errorMessage}</p>} 
        <h2 className="text-center mb-3">Signup</h2> 
        <form onSubmit={handleSubmit}> 
          {/* Basic Details */} 
          <div className="mb-3"> 
            <label className="form-label">First Name</label> 
            <input type="text" name="firstName" className="form-control" onChange={handleChange} required /> 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Last Name</label> 
            <input type="text" name="lastName" className="form-control" onChange={handleChange} required /> 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Username</label> 
            <input type="text" name="userId" className="form-control" onChange={handleChange} required /> 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Email</label> 
            <input type="email" name="email" className="form-control" onChange={handleChange} required /> 
            {emailError && <p className="text-danger">{emailError}</p>} 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Alternate Email</label> 
            <input type="email" name="alternateEmail" className="form-control" id="alt_email" onChange={handleChange} onBlur={checkEmail} /> 
            {altEmailError && <p className="text-danger">{altEmailError}</p>} 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Password</label> 
            <input type="password" name="passwordHash" className="form-control" onChange={handleChange} onBlur={validateStrength} required /> 
            {strengthError && <p className="text-danger">{strengthError}</p>} 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Confirm Password</label> 
            <input type="password" name="cnfrm-password" id="cnfrm_pwd" className="form-control" onChange={handleChange} onBlur={validatePassword} required /> 
            {passwordError && <p className="text-danger">{passwordError}</p>} 
          </div> 
 
          {/* Contact Information */} 
          <div className="mb-3"> 
            <label className="form-label">Phone Number</label> 
            <input type="tel" name="phoneNumber" className="form-control" onChange={handleChange} required onBlur={validatePhoneNumber} /> 
            {phoneError && <p className="text-danger">{phoneError}</p>} 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Alternate Mobile Number</label> 
            <input type="tel" name="alternatePhoneNumber" className="form-control" onChange={handleChange} onBlur={validateAlternatePhoneNumber} /> 
            {alternatePhoneError && <p className="text-danger">{alternatePhoneError}</p>} 
          </div> 
 
 
          {/* Identity Details */} 
          <div className="mb-3"> 
            <label className="form-label">Date of Birth</label> 
            <input type="date" name="dateOfBirth" className="form-control" onChange={handleChange} required /> 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">PAN Card</label> 
            <input type="text" name="panCard" className="form-control" onChange={handleChange} onBlur={validatePan} required /> 
            {panError && <p className="text-danger">{panError}</p>} 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Aadhar Card</label> 
            <input type="text" name="aadharCard" className="form-control" onChange={handleChange} onBlur={validateAadhar} required /> 
            {aadharcardError && <p className="text-danger">{aadharcardError}</p>} 
          </div> 
 
          {/* Address Details */} 
          <div className="mb-3"> 
            <label className="form-label">Residential Address</label> 
            <input type="text" name="residentialAddress" id="residentialAddress" className="form-control" onChange={handleChange} required /> 
          </div> 
          <div className="mb-3"> 
            <label className="form-label">Permanent Address</label> 
            <input type="text" name="permanentAddress" id="permanentAddress" className="form-control" onChange={(e) => setPermanentAddress(e.target.value)} value={permanentAddress} required /> 
            <div className="checkbox-container"> 
            <input 
                type="checkbox" 
                id="sameAddress" 
                onChange={handlePermanentAddressChange} 
                readOnly={isReadonly} 
              /> 
              <p>Same as Residential Address</p> 
          </div> 
          </div> 
 
          <button type="submit" className="btn btn-primary w-100 mt-3">Signup</button> 
          <div className="text-center mt-2"> 
            <p>Existing user? <a href="/">Login</a></p> 
          </div> 
        </form> 
      </div> 
    </div> 
  ); 
} 
 
export default Signup;