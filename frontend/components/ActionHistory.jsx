import React, { useEffect, useState } from "react"; 
import axios from "axios"; 
import Header from "./Header"; 
import VerticleNavbar from "./VerticleNavbar"; 
import { format } from "date-fns"; 
 
function ActionHistory() { 
    const [actions, setActions] = useState([]); 
    const [dataUserId, setDataUserId] = useState(""); // Keep blank for testing purposes 
    const [loading, setLoading] = useState(false); 
    const [error, setError] = useState(""); 
 
    const token = localStorage.getItem("token"); 
    const userId = localStorage.getItem("userId"); 
 
    const fetchActions = async () => { 
        setLoading(true); 
        setDataUserId(userId); 
        setError(""); 
 
        try { 
            const response = await axios.post(`http://localhost:8090/api/actions/user-actions`, 
                { userId }, 
                { headers: { "Content-Type": "application/json", Authorization: `Bearer ${token}` } } 
            ); 
            console.log(userId); 
            setActions(response.data); 
            console.log(response.data); 
        } catch (err) { 
            setError("Failed to fetch action history. Check the userId or backend."); 
        } 
 
        setLoading(false); 
    }; 
 
    useEffect(() => { 
        fetchActions(); 
    }, []); 
 
 
    return ( 
        <div> 
            <Header /> 
            <div className='d-flex flex-row w-100'> 
                <VerticleNavbar /> 
                <div className="container-fluid p-5" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)', backgroundColor: '#f1f5f9' }}> 
                    <h2 className="ms-5">User Logs</h2> 
 
                    {loading && <p>Loading Action History...</p>} 
                    {error && <p>{error}</p>} 
 
                    {/* Action History Table */} 
                    <div className="mx-5"> 
                        <table className="table table-bordered table-striped mt-2"> 
                            <thead> 
                                <tr> 
                                    <th className="p-1">Timestamp</th> 
                                    <th className="p-1">Event</th> 
                                    <th className="p-1">Loan ID</th> 
                                </tr> 
                            </thead> 
                            <tbody> 
                                {actions.length > 0 ? ( 
                                    actions.map((action) => ( 
                                        <tr key={action.actionId}> 
                                            <td className="p-1">{format(action.actionTimestamp, "yyyy/MM/dd, hh:mm a")}</td> 
                                            <td className="p-1">{action.actionEvent}</td> 
                                            <td className="p-1">{action.actionLoanId}</td> 
                                        </tr> 
                                    )) 
                                ) : ( 
                                    <tr> 
                                        <td colSpan="6"> 
                                            No Action History found. 
                                        </td> 
                                    </tr> 
                                )} 
                            </tbody> 
                        </table> 
                    </div> 
                </div> 
            </div> 
        </div> 
    ); 
}; 
 
export default ActionHistory;