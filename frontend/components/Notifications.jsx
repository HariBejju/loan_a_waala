import { useEffect, useState } from "react"; 
import { format } from "date-fns"; 
import axios from "axios"; 
import Header from "./Header"; 
import VerticleNavbar from "./VerticleNavbar"; 
 
/* 
 * Notifications Component 
 * This component fetches and displays notifications for the logged-in user. 
 * Users can dismiss notifications, and a modal shows feedback upon dismissal. 
 */ 
function Notifications() { 
    // State variables for managing notifications and UI interactions 
    const [notifications, setNotifications] = useState([]); 
    const [error, setError] = useState(null); 
    const [showModal, setShowModal] = useState(false); 
    const [modalMessage, setModalMessage] = useState(''); 
 
    // Retrieve user details from local storage 
    const userId = localStorage.getItem("userId"); 
    const token = localStorage.getItem("token"); 
 
    /* 
     * Fetches notifications from the backend for the logged-in user. 
     */ 
    const fetchNotifications = async () => { 
        try { 
            const response = await axios.get(`http://localhost:8090/api/notifications/${userId}`, { 
                headers: { Authorization: `Bearer ${token}` } 
            }); 
            console.log("Fetched notifications:", response.data); 
            setNotifications(response.data); 
        } catch (error) { 
            setError("Failed to fetch notifications."); 
            console.error("Fetch error:", error); 
        } 
    }; 
 
 
 
    // Dismisses a notification by sending a delete request to the backend. 
    // Updates the UI state after successful deletion. 
 
    const dismissNotification = async (notificationId) => { 
        try { 
            const response = await axios.delete(`http://localhost:8090/api/notifications/${notificationId}`, { 
                headers: { Authorization: `Bearer ${token}` } 
            }); 
            console.log("Notification deleted:", response.data); 
 
            // Update the state by removing the dismissed notification 
            setNotifications(notifications.filter(notification => notification.notificationId !== notificationId)); 
            setModalMessage('Notification removed');  // Set success message 
            setShowModal(true);  // Show modal 
            setTimeout(() => setShowModal(false), 1000);  // Hide modal after 3 seconds 
        } catch (error) { 
            setError("Failed to dismiss notification."); 
            setModalMessage("Error while removing notification");  // Set error message 
            setShowModal(true);  // Show modal 
            setTimeout(() => setShowModal(false), 1000);  // Hide modal after 3 seconds 
            console.error("Dismiss error:", error); 
        } 
    }; 
 
    // Fetch notifications when the component mounts 
    useEffect(() => { 
        fetchNotifications(); 
    }, []); 
 
    return ( 
        <div> 
            <Header /> 
            <div className='d-flex flex-row w-100'> 
                <VerticleNavbar /> 
                <div className="container-fluid p-5" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)', backgroundColor: '#f1f5f9' }}> 
                    <h4>Notifications</h4> 
                    {/* Display error message if fetching fails */} 
                    {error && <p style={{ color: 'red' }}>{error}</p>} 
 
                    {/* Render notification list */} 
                    <div className="list-group mt-5"> 
                        {notifications.length > 0 ? ( 
                            notifications.map((note) => ( 
                                <div key={note.notificationId} className="list-group-item d-flex justify-content-between align-items-center"> 
                                    <div> 
                                        <b>{format(note.notificationTimestamp, "yyyy/MM/dd, hh:mm a")}</b> 
                                        <p>{note.notificationMessage}</p> 
                                    </div> 
                                    <div> 
                                        <button className="btn btn-primary" onClick={() => dismissNotification(note.notificationId)}>Dismiss</button> 
                                    </div> 
                                </div> 
                            )) 
                        ) : ( 
                            <p>No notifications yet.</p> 
                        )} 
                    </div> 
                </div> 
            </div> 
 
            {/* Modal for success/failure message */} 
            {showModal && ( 
                <div className="modal" style={{ display: 'block', position: 'fixed', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', backgroundColor: 'rgba(0,0,0,0.5)', padding: '20px', borderRadius: '10px' }}> 
                    <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '10px' }}> 
                        <p>{modalMessage}</p> 
                    </div> 
                </div> 
            )} 
        </div> 
    ); 
} 
 
export default Notifications;