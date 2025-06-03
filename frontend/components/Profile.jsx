import React, { useEffect, useState } from 'react'; 
import { fetchUserProfile } from "../api/Profileapi"; 
import { format } from 'date-fns'; 
import Header from './Header'; 
import VerticleNavbar from './VerticleNavbar'; 
import axios from 'axios'; 
import "bootstrap/dist/css/bootstrap.min.css"; 
 
 
/* 
 * Profile Component 
 * This component displays and allows editing of the user's profile information. 
 * It fetches user data from the backend, displays personal details, and provides edit functionality. 
 */ 
 
function Profile() { 
  // State variables for user details, error handling, and UI state 
  const [user, setUser] = useState(null); 
  const [error, setError] = useState(null); 
  const [displayError, setDisplayError] = useState(null); 
  const [loading, setLoading] = useState(true); 
  const [isEditing, setIsEditing] = useState(false); 
  const [phoneError, setPhoneError] = useState(""); 
  const [alternatePhoneError, setAlternatePhoneError] = useState(""); 
  const [pincodeError, setPincodError] = useState("") 
  const [isWarnings, setIsWarnings] = useState(false) 
 
  // Retrieve user information from local storage 
  const userId = localStorage.getItem("userId"); 
  const firstName = localStorage.getItem('firstName') 
  const lastName = localStorage.getItem('lastName') 
  const token = localStorage.getItem('token') 
 
  //Fetch user profile data from the backend when the component mounts. 
  useEffect(() => { 
    if (!userId) { 
      setError("User not logged in."); 
      setLoading(false); 
      return; 
    } 
 
    const getUserData = async () => { 
      try { 
        const data = await fetchUserProfile(userId); 
        setUser(data); 
      } catch (err) { 
        setError(err.message); 
      } finally { 
        setLoading(false); 
      } 
    }; 
 
    getUserData(); 
  }, [userId]); // Adding userId ensures refetching if it changes 
 
 
  if (loading) return <p>Loading user data...</p>; 
  if (error) return <p className="text-danger">{error}</p>; 
  if (!user) return <p>No user data available.</p>; 
 
  /* 
   * Handles input field changes when editing the profile. 
   * Updates the `user` state with new values. 
   */ 
  const handleChange = (event) => { 
    const { name, value } = event.target; 
    setUser({ ...user, [name]: value }); 
  }; 
 
  /* 
     * Saves updated user details to the backend. 
     * Updates local storage and notifies other components about the changes. 
     */ 
  const handleSave = async () => { 
    if (!isWarnings) { 
      try { 
        await axios.put(`http://localhost:8090/api/users/${userId}`, user, { 
          headers: { Authorization: `Bearer ${token}` } 
        }); 
        alert("Profile updated successfully!"); 
 
        // Update sessionStorage 
        localStorage.setItem('firstName', user.firstName); 
        localStorage.setItem('lastName', user.lastName); 
 
        // Notify other components of the update 
        window.dispatchEvent(new Event("storage")); 
 
        setIsEditing(false); 
      } catch (err) { 
        alert("Error updating profile. Please try again."); 
      } 
    } else { 
      setDisplayError("Error while updating profile details") 
    } 
 
  }; 
 
  const handleDelete = async () => { 
    if (!isWarnings) { 
      try { 
        if (confirm("Are you sure you want to PERMANENTLY DELETE your account?")) { 
          await axios.delete(`http://localhost:8090/api/users/${userId}`, user, { 
            headers: { Authorization: `Bearer ${token}` } 
          }); 
          alert(`Account for userId: ${userId} has been permanently deleted`); 
        } 
      } 
      catch (error) { 
        alert(`Error deleting account for userId: ${userId}\nPlease try again later`); 
      } 
    } 
    else { 
      setDisplayError("Error while deleting profile"); 
    } 
  } 
 
  //validate the phone number  
  const validatePhoneNumber = () => { 
    if (user.phoneNumber.length != 10) { 
      setPhoneError("Phone number should be 10 digits") 
      setIsWarnings(true) 
    } else { 
      setPhoneError("") 
      setIsWarnings(false) 
    } 
  }; 
 
  //validation for alternate phone number 
  const validateAlternatePhoneNumber = () => { 
    const isTenDigits = /^\d{10}$/.test(user.alternatePhoneNumber); 
    if (!isTenDigits) { 
      setAlternatePhoneError("Phone number should be exactly 10 digits (only numbers allowed)"); 
      setIsWarnings(true); 
    } 
    else if (user.phoneNumber == user.alternatePhoneNumber) { 
      setAlternatePhoneError("Alternate phone number and phone number should not be same") 
      setIsWarnings(true) 
    } else { 
      setAlternatePhoneError("") 
      setIsWarnings(false) 
    } 
  }; 
 
  //validation for pinCode 
  const validatePincode = () => { 
    if (!/^\d+$/.test(user.pincode)) { 
      setPincodError("Pincode must contain only digits"); 
      setIsWarnings(true) 
    } else if (user.pincode.length !== 6) { 
      setPincodError("Pincode must be exactly 6 digits long"); 
      setIsWarnings(true) 
    } else { 
      setPincodError(""); 
      setIsWarnings(false) 
    } 
  } 
 
  return ( 
    <div> 
      <Header /> 
      <div className="d-flex flex-row w-100"> 
        <VerticleNavbar /> 
 
        <div className=" outer-box w-100" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)' }}> 
 
          <div className="d-flex justify-content-center align-items-center p-5"> 
            <div className="card p-4 shadow-lg" style={{ width: "400px" }}> 
              <div className="text-center"> 
              </div> 
              {displayError && <p className="text-danger">{displayError}</p>} 
 
              {/* Edit Mode */} 
              {isEditing ? ( 
                <div className=''> 
                  <label>FirstName</label> 
                  <input type="text" name="firstName" className="form-control mb-2" value={user.firstName} onChange={handleChange} /> 
                  <label>LastName</label> 
                  <input type="text" name="lastName" className="form-control mb-2" value={user.lastName} onChange={handleChange} /> 
                  <label>Email</label> 
                  <input type="email" name="email" className="form-control mb-2" value={user.email} onChange={handleChange} /> 
                  <label>PhoneNumber</label> 
                  <input type="tel" name="phoneNumber" className="form-control mb-2" value={user.phoneNumber} onChange={handleChange} onBlur={validatePhoneNumber} /> 
                  {phoneError && <p className="text-danger">{phoneError}</p>} 
                  <label>Alternate Phone number</label> 
                  <input type="text" name="alternatePhoneNumber" className="form-control mb-2" value={user.alternatePhoneNumber} onChange={handleChange} onBlur={validateAlternatePhoneNumber} /> 
                  {alternatePhoneError && <p className="text-danger">{alternatePhoneError}</p>} 
                  <label>Date Of Birth</label> 
                  <input type="date" placeholder="YYYY/MM/DD" name="dateOfBirth" className="form-control mb-2" value={user.dateOfBirth} onChange={handleChange} /> 
                  <label>PanCard</label> 
                  <input type="text" name="panCard" className="form-control mb-2" value={user.panCard} readOnly /> 
                  <label>AadharCard</label> 
                  <input type="text" name="aadharCard" className="form-control mb-2" value={user.aadharCard} readOnly /> 
                  <label>Residential Address</label> 
                  <input type="text" name="residentialAddress" className="form-control mb-2" value={user.residentialAddress} onChange={handleChange} /> 
                  <label>Permanent Address</label> 
                  <input type="text" name="permanentAddress" className="form-control mb-2" value={user.permanentAddress} onChange={handleChange} /> 
                  <div className='d-flex'> 
                    <button className="btn btn-success w-100 mt-2 me-3" onClick={handleSave}>Save</button> 
                    <button className="btn btn-secondary w-100 mt-2" onClick={() => setIsEditing(false)}>Cancel</button> 
                  </div> 
                </div> 
              ) : ( 
                // View Mode: Displays user details 
                <> 
                  <h2>{user.firstName} {user.lastName}</h2> 
                  <h3><strong>UserId:</strong> {user.userId}</h3> 
                  <p className="text-muted">{user.email}</p> 
                  <hr /> 
                  <p><strong> Phone:</strong> {user.phoneNumber}</p> 
                  <p><strong> Alternate Phone Number:</strong> {user.alternatePhoneNumber}</p> 
                  <p><strong> Alternate Email:</strong> {user.alternateEmail}</p> 
                  <p><strong> Date Of Birth</strong> {format(user.dateOfBirth, localStorage.getItem("dateFormat"))}</p> 
                  <p><strong> Aadhar Card :</strong> {user.aadharCard}</p> 
                  <p><strong> PanCard :</strong> {user.panCard}</p> 
                  <p><strong> Residential Address</strong> {user.residentialAddress}</p> 
                  <p><strong> Permanent Address:</strong> {user.permanentAddress}</p> 
                  <button className="btn btn-primary w-100 mt-3" onClick={() => setIsEditing(true)}>Edit Profile</button> 
                  <button className="btn btn-danger w-100 mt-3" onClick={handleDelete}>Delete Profile</button> 
                </> 
              )} 
            </div> 
          </div> 
        </div> 
      </div> 
    </div> 
  ); 
} 
 
export default Profile;